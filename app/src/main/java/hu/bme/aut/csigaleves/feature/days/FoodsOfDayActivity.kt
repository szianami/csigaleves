package hu.bme.aut.csigaleves.feature.days

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.csigaleves.R
import hu.bme.aut.csigaleves.feature.data.ConsumedFood
import hu.bme.aut.csigaleves.feature.data.ConsumedFoodDatabase
import kotlinx.android.synthetic.main.activity_foods_of_day.*
import kotlinx.android.synthetic.main.content_list_of_foods.*
import kotlin.concurrent.thread

class FoodsOfDayActivity : AppCompatActivity(), DayAdapter.FoodClickListener {
    private lateinit var adapter: DayAdapter
    private var date : String? = null
    private var sumKcal : Int = 0
    private var sumCh : Int = 0
    private var sumProtein : Int = 0
    private var sumFat : Int = 0
    private var sumFiber : Int = 0

    private lateinit var database: ConsumedFoodDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods_of_day)
        setDatabaseInstance()
        //loadItemsInBackground()
        setDateFromIntent(intent.extras)
        initRecyclerView()
    }

    private fun initFab() {

        val fab = findViewById<FloatingActionButton>(R.id.addFoodFab)
        addFoodFab.setOnClickListener {
            // todo : show actual day
            //  dialog
            // AddFoodDialogFragment().show(supportFragmentManager, AddFoodDialogFragment::class.java.simpleName)
        }
    }

    private fun loadNutrients(){
        val entries = listOf(
            PieEntry(sumProtein.toFloat(), "protein"),
            PieEntry(sumCh.toFloat(), "carbs"),
            PieEntry(sumFat.toFloat(), "fat"),
            PieEntry(sumFiber.toFloat(), "fiber")
        )

        val dataSet = PieDataSet(entries, "Nutrients")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)

        chartNutrients.data = data
        chartNutrients.invalidate()
    }

    private fun setDateFromIntent(bundle : Bundle?) {
        bundle?.let {
            bundle.apply {
                //Intent with data
                date = getString("date")
                DateOfDayTextView.text = date
            }
        }
    }

    private fun setDatabaseInstance() {
        database = Room.databaseBuilder(
            applicationContext,
            ConsumedFoodDatabase::class.java,
            "consumedfoods"
        ).enableMultiInstanceInvalidation().build()
    }

    private fun initRecyclerView() {
        adapter = DayAdapter(this)
        FoodRecyclerView.layoutManager = LinearLayoutManager(this)
        FoodRecyclerView.adapter = adapter
        loadItemsInBackground()

    }
    private fun loadItemsInBackground() {
        thread {
            if (date != null){
                val items = database.consumedFoodDao().loadFoodsByDate(date!!)
                 runOnUiThread {
                   adapter.update(items)
                }
                sumKcal = database.consumedFoodDao().getSumKcal(date!!)
                sumCh = database.consumedFoodDao().getSumCh(date!!)
                sumFat = database.consumedFoodDao().getSumFat(date!!)
                sumFiber = database.consumedFoodDao().getSumFiber(date!!)
                sumProtein= database.consumedFoodDao().getSumProtein(date!!)

                loadNutrients()
            }
        }
    }

    override fun onFoodSelected(food: ConsumedFood?) {

    }

    override fun onFoodRemoved(food: ConsumedFood?) {
        thread {
            if (food != null) {
                database.consumedFoodDao().deleteItem(food)
            }
            runOnUiThread {
                adapter.removeFood(food)
            }
        }

    }
}
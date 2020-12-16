package hu.bme.aut.csigaleves.feature.listOfDates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.csigaleves.R
import hu.bme.aut.csigaleves.feature.data.ConsumedFood
import hu.bme.aut.csigaleves.feature.data.ConsumedFoodDatabase
import hu.bme.aut.csigaleves.feature.days.FoodsOfDayActivity
import kotlinx.android.synthetic.main.content_list_of_days.*
import java.io.IOException
import kotlin.concurrent.thread

class ListOfDatesActivity : AppCompatActivity(), DateAdapter.DateClickListener, AddDateDialogFragment.AddDayDialogListener, ConfirmDialogFragment.ConfirmDialogListener {
// az activity, ami a kezdőképernyőnk és listázva látjuk rajta a napjainkat
    // van egy adapter osztálya, ami a tőle kapott napok menedzselését, hozzáadását végzi a recyclerviewnak
    private lateinit var adapter: DateAdapter
    private lateinit var database: ConsumedFoodDatabase

    private var dateToRemove: String? = null
    private lateinit var confirmDialog: ConfirmDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_dates)
        initFab()

        database = Room.databaseBuilder(
            applicationContext,
            ConsumedFoodDatabase::class.java,
            "consumedfoods"
        ). enableMultiInstanceInvalidation().build()

        initRecyclerView()
    }

    private fun initFab() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // todo : show actual day dialog
            AddDateDialogFragment().show(supportFragmentManager, AddDateDialogFragment::class.java.simpleName)
        }
    }

    private fun initRecyclerView() {

        adapter = DateAdapter(this)
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        MainRecyclerView.adapter = adapter

        loadItemsInBackground()

        val divider = DividerItemDecoration(
            MainRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL
        )
        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.list_item_divider)
        divider.setDrawable(dividerDrawable!!)
        MainRecyclerView.addItemDecoration(divider)
    }

    private fun loadItemsInBackground() {
        /*
        val newItem1 = ConsumedFood(null,"grillcsirke", 150, 100, 15, 20, 40, 1, "2020-12-10")
        val newItem2 = ConsumedFood(null,"fröccsöntött dinoszaurusz", 100, 900, 15, 20, 40, 1, "2020-12-10")
        val newItem3 = ConsumedFood(null,"nyúlhús", 350, 170, 85, 24, 40, 10, "2020-12-11")
        val newItem4 = ConsumedFood(null,"banán", 550, 870, 15, 240, 40, 20, "2020-12-11")
        val newItem5 = ConsumedFood(null,"kedves ételed", 50, 100, 154, 204, 40, 145, "2020-12-11")

        thread {
            val newId1 = database.consumedFoodDao().insert(newItem1)
            val newId2= database.consumedFoodDao().insert(newItem2)
            val newId3 = database.consumedFoodDao().insert(newItem3)
            val newId4 = database.consumedFoodDao().insert(newItem4)
            val newId5 = database.consumedFoodDao().insert(newItem5)
            runOnUiThread {
                adapter.addDate(newItem1.date)
            }
        }
        */

        thread {
            val items = database.consumedFoodDao().getDates()
            runOnUiThread {
                adapter.update(items)
            }
        }

    }

    override fun onDateSelected(date: String?) {
        val dayIntent = Intent(this, FoodsOfDayActivity::class.java)
        dayIntent.putExtra("date", date)
        startActivity(dayIntent)
    }

    override fun onDateAdded(date: String) {
        adapter.addDate(date)
    }

    override fun onDateRemoved(date: String?) {
        dateToRemove = date
        confirmDialog = ConfirmDialogFragment()
        confirmDialog.show(supportFragmentManager, "ConfirmDialogFragment")
        val dbItems = database.consumedFoodDao().getFoodsByDate(date!!)
        for (item in dbItems) database.consumedFoodDao().deleteItem(item)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        adapter.removeDate(dateToRemove)
        confirmDialog.dismiss()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        confirmDialog.dismiss()
    }
}
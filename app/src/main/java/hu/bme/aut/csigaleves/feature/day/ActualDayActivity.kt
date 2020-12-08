package hu.bme.aut.csigaleves.feature.day

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.csigaleves.R
import kotlinx.android.synthetic.main.content_actual_day.*
import java.text.SimpleDateFormat
import java.util.Date

class ActualDayActivity : AppCompatActivity(), DayAdapter.OnDaySelectedListener, AddDayDialogFragment.AddDayDialogListener {
// az activity, ami a kezdőképernyőnk és listázva látjuk rajta a napjainkat
    // van egy adapter osztálya, ami a tőle kapott napok menedzselését, hozzáadását végzi
    private lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_day)
        initFab()
        initRecyclerView()
    }

    private fun initFab() {

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // todo : show actual day dialog
            AddDayDialogFragment().show(supportFragmentManager, AddDayDialogFragment::class.java.simpleName)
        }
    }

    // feltölti a listánkat 3 mock adattal
    // ezt később úgy is lehet hogy a szerializált adatokból olvassa őket vissza

    private fun initRecyclerView() {
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DayAdapter(this)
        adapter.addDay("2020-12-08")
        adapter.addDay("2020-12-07")
        adapter.addDay("2020-12-06")
        adapter.addDay("2020-12-05")
        adapter.addDay("2020-12-04")
        adapter.addDay("2020-12-03")
        adapter.addDay("2020-12-02")
        adapter.addDay("2020-12-01")
        adapter.addDay("2020-12-00")
        MainRecyclerView.adapter = adapter
    }

    override fun onDaySelected(day: String?) {
        // Todo: Start DetailsActivity with the selected day
    }

    override fun onDayAdded(day: String) {
       // var formatter = new SimpleDateFormat ("yyyy-MM-dd");
       // nem kell neki date formában, majd azt az adapter kezeli
        adapter.addDay(day)
    }

    override fun onDayRemoved(day: String?) {
        // var formatter = new SimpleDateFormat ("yyyy-MM-dd");
        // nem kell neki date formában, majd azt az adapter kezeli
        adapter.removeDay(day)
    }
}
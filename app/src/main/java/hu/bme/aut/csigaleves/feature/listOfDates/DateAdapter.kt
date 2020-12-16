package hu.bme.aut.csigaleves.feature.listOfDates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.csigaleves.R
import hu.bme.aut.csigaleves.feature.data.ConsumedFood
import kotlinx.android.synthetic.main.list_of_dates_item.view.*

// A listát RecyclerView segítségével szeretnénk megjeleníteni, ezért az adapter a RecyclerView.Adapter osztályból származik.
// Az adapter a modell elemeket egy listában tárolja.
class DateAdapter internal constructor(private val listener: DateClickListener?) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {
    private val dates: MutableList<String>


    interface DateClickListener {
        fun onDateSelected(date: String?)
        fun onDateRemoved(date: String?)
    }

    //ViewHolder-eken keresztül érhetjük majd el a lista elemekhez tartozó View-kat.
    // Mivel a ViewHolder osztály példányai az Adapterhez lesznek csatolva (azért, hogy elérjék a belső változóit), inner class osztályként kell definiálni.

    // Az onCreateViewHolder()-ben hozzuk létre az adott lista elemet megjelenítő View-t és a hozzá tartozó ViewHolder-t
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_dates_item, parent, false)
        return DateViewHolder(view)
    }

    // Az onBindViewHolder()-ben kötjük hozzá a modell elemhez a nézetet,
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = dates[position]
        holder.dateTextView.text = dates[position]
        holder.item = item
    }

    // getItemCount() pedig a listában található (általános esetre fogalmazva a megjelenítendő) elemek számát kell, hogy visszaadja.
    override fun getItemCount(): Int {
        return dates.size
    }

    // addDate ne legyen később mikor már nem tesztelve van ?
    fun addDate(newDate: String) {
        if (!dates.contains(newDate)) {
            dates.add(newDate)
            notifyItemInserted(dates.size - 1)
        }
    }

    fun removeDate(item: String?) {
        val position = dates.indexOf(item)
        dates.removeAt(position)
        notifyItemRemoved(position)
        if (position < dates.size) {
            notifyItemRangeChanged(position, dates.size - position)
        }
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView = itemView.DateTextView
        val removeButton = itemView.DateRemoveButton
        var item: String? = null

        init {
            itemView.DateTextView.setOnClickListener { listener?.onDateSelected(item) }
            itemView.DateRemoveButton.setOnClickListener { listener?.onDateRemoved(item) }
        }
    }

    fun addItem(item: ConsumedFood) {
        dates.add(item.date)
        notifyItemInserted(dates.size - 1)
    }

    fun update(datesInDb: MutableList<String>) {
        dates.clear()
        dates.addAll(datesInDb)
        notifyDataSetChanged()
    }

    init {
        dates = ArrayList()
    }


}
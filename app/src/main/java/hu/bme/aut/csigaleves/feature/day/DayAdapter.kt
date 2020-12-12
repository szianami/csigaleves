package hu.bme.aut.csigaleves.feature.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.csigaleves.R
import kotlinx.android.synthetic.main.list_of_days_item.view.*

// A listát RecyclerView segítségével szeretnénk megjeleníteni, ezért az adapter a RecyclerView.Adapter osztályból származik.
// Az adapter a modell elemeket egy listában tárolja.
class DayAdapter internal constructor(private val listener: DayClickListener?) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private val days: MutableList<String>

    interface DayClickListener {
        fun onDaySelected(day: String?)
        fun onDayRemoved(day: String?)
    }

    //ViewHolder-eken keresztül érhetjük majd el a lista elemekhez tartozó View-kat.
    // Mivel a ViewHolder osztály példányai az Adapterhez lesznek csatolva (azért, hogy elérjék a belső változóit), inner class osztályként kell definiálni.

    // Az onCreateViewHolder()-ben hozzuk létre az adott lista elemet megjelenítő View-t és a hozzá tartozó ViewHolder-t
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_days_item, parent, false)
        return DayViewHolder(view)
    }

    // Az onBindViewHolder()-ben kötjük hozzá a modell elemhez a nézetet,
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = days[position]
        holder.nameTextView.text = days[position]
        holder.item = item
    }

    // getItemCount() pedig a listában található (általános esetre fogalmazva a megjelenítendő) elemek számát kell, hogy visszaadja.
    override fun getItemCount(): Int {
        return days.size
    }

    fun addDay(newDay: String) {
        if (!days.contains(newDay)) {
            days.add(newDay)
            notifyItemInserted(days.size - 1)
        }
    }

    fun removeDay(item: String?) {
        val position = days.indexOf(item)
        days.removeAt(position)
        notifyItemRemoved(position)
        if (position < days.size) {
            notifyItemRangeChanged(position, days.size - position)
        }
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.DayItemNameTextView
        val removeButton = itemView.DayItemRemoveButton
        var item: String? = null

        init {
            itemView.DayItemNameTextView.setOnClickListener { listener?.onDaySelected(item) }
            itemView.DayItemRemoveButton.setOnClickListener { listener?.onDayRemoved(item) }
        }
    }

    init {
        days = ArrayList()
    }

}
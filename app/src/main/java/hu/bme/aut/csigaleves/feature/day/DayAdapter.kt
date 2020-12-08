package hu.bme.aut.csigaleves.feature.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.bme.aut.csigaleves.R
import kotlinx.android.synthetic.main.item_day.view.*

class DayAdapter internal constructor(private val listener: OnDaySelectedListener?) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private val days: MutableList<String>

    interface OnDaySelectedListener {
        fun onDaySelected(day: String?)
    }

    interface OnDayRemovedListener {
        fun onDayRemoved(day: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = days[position]
        holder.nameTextView.text = days[position]
        holder.item = item
    }

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
            itemView.DayItemRemoveButton.setOnClickListener {
                removeDay(item)
            }
        }
    }

    init {
        days = ArrayList()
    }


}
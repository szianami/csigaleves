package hu.bme.aut.csigaleves.feature.days

// ??? erre listenel ugye? neeeeeem szerintem nem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.csigaleves.R
import hu.bme.aut.csigaleves.feature.data.ConsumedFood
import hu.bme.aut.csigaleves.feature.listOfDates.DateAdapter
import kotlinx.android.synthetic.main.list_of_foods_item.view.*

// A ShoppingAdapter-ben definiáltunk egy ShoppingItemClickListener nevű interfészt is,
// aminek a segítségével jelezhetjük az alkalmazás többi része felé, hogy esemény történt egy lista elemen.
// na ez nekünk asszem nem kell
class DayAdapter(private val listener: FoodClickListener?) : RecyclerView.Adapter<DayAdapter.FoodViewHolder>() {

    private val foods = mutableListOf<ConsumedFood>()

    interface FoodClickListener {
        fun onFoodSelected(food: ConsumedFood?)
        fun onFoodRemoved(food: ConsumedFood?)
    }

    // létrehozzuk az adott lista elemet megjelenítő View-t és a hozzá tartozó ViewHolder-t
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_of_foods_item, parent, false)
        return FoodViewHolder(itemView)
    }

    // kötjük hozzá a modell elemhez a nézetet,
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = foods[position]
        holder.foodName.text = foods[position].name
        holder.amount.text = foods[position].amount.toString()
        holder.kcal.text=foods[position].kcal.toString()
        holder.fat.text = foods[position].fat.toString()
        holder.ch.text = foods[position].ch.toString()
        holder.protein.text = foods[position].protein.toString()
        holder.fiber.text = foods[position].fiber.toString()

        holder.item = item
    }

    // ez mit csinál?
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName = itemView.foodNameTextView
        val amount = itemView.amountTextView
        val kcal = itemView.kcalTextView
        val fat = itemView.fatTextView
        val ch = itemView.chTextView
        val protein = itemView.proteinTextView
        val fiber = itemView.fiberTextView

        val removeButton = itemView.foodRemoveButton

        var item: ConsumedFood? = null

        init {
            itemView.foodNameTextView.setOnClickListener { listener?.onFoodSelected(item) }
            itemView.foodRemoveButton.setOnClickListener { listener?.onFoodRemoved(item) }
        }
    }

    fun update(foodsInDb: MutableList<ConsumedFood>) {
        foods.clear()
        foods.addAll(foodsInDb)
        notifyDataSetChanged()
    }

    fun removeFood(item: ConsumedFood?) {
        val position = foods.indexOf(item)
        foods.removeAt(position)
        notifyItemRemoved(position)
        if (position < foods.size) {
            notifyItemRangeChanged(position, foods.size - position)
        }
    }

    override fun getItemCount(): Int {
        return foods.size
    }


}
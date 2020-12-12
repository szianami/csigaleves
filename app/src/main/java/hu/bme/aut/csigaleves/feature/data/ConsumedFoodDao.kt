package hu.bme.aut.csigaleves.feature.data

import androidx.room.*

@Dao
interface ConsumedFoodDao {
    @Query("SELECT * FROM consumedfood")
    fun getAll(): List<ConsumedFood>

    // lekérjük az adott nap elfogyasztott ételeket
    @Query("SELECT * FROM consumedfood WHERE date LIKE :date")
    fun loadFoodsByDate(date: String): List<ConsumedFood>

    @Insert
    fun insert(shoppingItems: ConsumedFood): Long

    @Update
    fun update(shoppingItem: ConsumedFood)

    @Delete
    fun deleteItem(shoppingItem: ConsumedFood)
}
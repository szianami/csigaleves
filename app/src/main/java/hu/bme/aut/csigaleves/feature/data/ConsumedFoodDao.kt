package hu.bme.aut.csigaleves.feature.data

import androidx.room.*

@Dao
interface ConsumedFoodDao {
    @Query("SELECT * FROM consumedfood")
    fun getAll(): MutableList<ConsumedFood>

    @Query("SELECT DISTINCT date FROM consumedfood ORDER BY date DESC")
    fun getDates(): MutableList<String>

    // lekérjük az adott nap elfogyasztott ételeket
    @Query("SELECT * FROM consumedfood WHERE date LIKE :date")
    fun loadFoodsByDate(date: String): MutableList<ConsumedFood>

    @Query("SELECT SUM(kcal) FROM consumedfood where date LIKE :date")
    fun getSumKcal(date: String): Int

    @Query("SELECT SUM(ch) FROM consumedfood where date LIKE :date")
    fun getSumCh(date: String): Int

    @Query("SELECT SUM(fat) FROM consumedfood where date LIKE :date")
    fun getSumFat(date: String): Int

    @Query("SELECT SUM(protein) FROM consumedfood where date LIKE :date")
    fun getSumProtein(date: String): Int

    @Query("SELECT SUM(fiber) FROM consumedfood where date LIKE :date")
    fun getSumFiber(date: String): Int


    @Insert
    fun insert(shoppingItems: ConsumedFood): Long

    @Update
    fun update(shoppingItem: ConsumedFood)

    @Delete
    fun deleteItem(shoppingItem: ConsumedFood)
}
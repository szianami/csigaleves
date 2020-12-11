package hu.bme.aut.csigaleves.feature.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ConsumedFood::class], version = 1)
abstract class ConsumedFoodDatabase : RoomDatabase() {
    abstract fun ConsumedFoodDao(): ConsumedFoodDao
}
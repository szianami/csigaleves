package hu.bme.aut.csigaleves.feature.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConsumedFood::class], version = 1)
abstract class ConsumedFoodDatabase : RoomDatabase() {

    abstract fun consumedFoodDao(): ConsumedFoodDao

/*
    companion object {
        var instance : ConsumedFoodDatabase? = null;

        fun getInstance(context: Context) : ConsumedFoodDatabase? {
            if (instance == null) {
                synchronized(ConsumedFoodDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, ConsumedFoodDatabase::class.java, "consumedfoods").build()
                }
            }
            return instance
        }
    }
 */
}
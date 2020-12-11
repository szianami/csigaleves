package hu.bme.aut.csigaleves.feature.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consumedfood")
data class ConsumedFood(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "kcal") val kcal: Int,
    @ColumnInfo(name = "fat") val fat: Int,
    @ColumnInfo(name = "ch") val ch: Int,
    @ColumnInfo(name = "protein") val protein: Int,
    @ColumnInfo(name = "fiber") val fiber: Int,
    @ColumnInfo(name = "date") val date: String
) { }
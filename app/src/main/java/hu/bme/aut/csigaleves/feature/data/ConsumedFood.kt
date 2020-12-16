package hu.bme.aut.csigaleves.feature.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "consumedfood")
@Parcelize
data class ConsumedFood(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "name") val name: String = "food",
    @ColumnInfo(name = "amount") val amount: Int = 1,
    @ColumnInfo(name = "kcal") val kcal: Int = 1,
    @ColumnInfo(name = "fat") val fat: Int = 1,
    @ColumnInfo(name = "ch") val ch: Int = 1,
    @ColumnInfo(name = "protein") val protein: Int = 1,
    @ColumnInfo(name = "fiber") val fiber: Int = 1,
    @ColumnInfo(name = "date") val date: String = "9999-99-99"
)  : Parcelable { }
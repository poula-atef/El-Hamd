package com.example.el_hamd.pojo.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "item")
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    var name : String,
    var quantity : Int,
    var sellPrice : Double,
    var buyPrice : Double
): Parcelable

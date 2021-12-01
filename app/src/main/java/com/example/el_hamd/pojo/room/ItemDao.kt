package com.example.el_hamd.pojo.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert(onConflict = REPLACE)
    fun addItem(item: Item)

    @Query("select * from item")
    fun getAllItems(): List<Item>

    @Query("select * from item where id=:id")
    fun getItem(id: Long): Item

    @Query("delete from item where id=:id")
    fun deleteItem(id: Long)


}
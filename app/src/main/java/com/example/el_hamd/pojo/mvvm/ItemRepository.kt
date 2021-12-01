package com.example.el_hamd.pojo.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.el_hamd.pojo.room.Item
import com.example.el_hamd.pojo.room.ItemDao
import com.example.el_hamd.pojo.room.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ItemRepository {
    private lateinit var dao: ItemDao
    private lateinit var db : ItemDatabase
    suspend fun getAllItemsData(context: Context): List<Item> {
        return withContext(Dispatchers.IO) {
/*            if (!::db.isInitialized)
                db = ItemDatabase.getInstance(context)
            db.dao.getAllItems()*/
            ItemDatabase.getInstance(context).dao.getAllItems()
        }
    }

    private fun initDao(context: Context) {
        if (!::dao.isInitialized)
            dao = ItemDatabase.getInstance(context).dao
    }

    suspend fun getItem(id: Long, context: Context): Item {
        return withContext(Dispatchers.IO) {
            initDao(context)
            dao.getItem(id)
        }
    }

    suspend fun addItem(item: Item, context: Context) {
        withContext(Dispatchers.IO) {
            initDao(context)
            dao.addItem(item)
        }
    }

    suspend fun deleteItem(id: Long, context: Context) {
        withContext(Dispatchers.IO) {
            initDao(context)
            dao.deleteItem(id)
        }
    }

}
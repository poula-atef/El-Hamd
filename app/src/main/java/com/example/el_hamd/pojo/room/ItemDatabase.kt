package com.example.el_hamd.pojo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 3, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {

    abstract val dao: ItemDao

    companion object {
        private lateinit var INSTANCE: ItemDatabase
        fun getInstance(context: Context): ItemDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ItemDatabase::class.java,
                    "items_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE;
        }
    }

}
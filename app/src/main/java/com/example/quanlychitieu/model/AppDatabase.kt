package com.example.quanlychitieu.model

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao
//    val db = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    companion object {
    private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context) : AppDatabase {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
            } else {
                INSTANCE!!
            }

        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
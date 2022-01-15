package com.example.quanlychitieu.model

import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll() : List<Item>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)
    @Update
    suspend fun updateItem(item: Item)
    @Delete
    suspend fun deleteItem(item: Item)
    @Query("DELETE FROM Item")
    suspend fun deleteAll()
    
}
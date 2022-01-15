package com.example.quanlychitieu.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Item(
    @ColumnInfo(name = "_day")
    val day: String,
    @ColumnInfo(name = "_month")
    val month: String,
    @ColumnInfo(name = "_year")
    val year : String,
    @ColumnInfo(name = "_type")
    val type : String,
    @ColumnInfo(name = "_name_spending")
    val name : String,
    @ColumnInfo(name = "_money")
    val money : Int,
    @ColumnInfo(name = "_unit")
    val unit: String,
    @ColumnInfo(name = "_detail")
    val detail : String
) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}
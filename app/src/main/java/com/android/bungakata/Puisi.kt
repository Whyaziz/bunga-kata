package com.android.bungakata

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puisi_table")
data class Puisi (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "writer")
    val writer: String,
    @ColumnInfo(name = "content")
    val content: String,
)
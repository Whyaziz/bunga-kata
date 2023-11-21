package com.android.bungakata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PuisiDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(puisi: Puisi)

    @Update
    fun update(puisi: Puisi)

    @Delete
    fun delete(puisi: Puisi)

    @get:Query("SELECT * from puisi_table ORDER by id ASC")
    val allPuisi: LiveData<List<Puisi>>


}
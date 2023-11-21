package com.android.bungakata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Puisi::class], version = 1, exportSchema = false)
abstract class PuisiDatabase : RoomDatabase() {

    abstract fun puisiDao() : PuisiDao?

    companion object {
        @Volatile
        private  var INSTANCE: PuisiDatabase? = null
        fun getDatabase(context: Context): PuisiDatabase? {
            if (INSTANCE == null){
                synchronized(PuisiDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PuisiDatabase::class.java, "note_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}
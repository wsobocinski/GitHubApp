package com.wsobocinski.githubapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wsobocinski.githubapp.database.model.CommitsModel

@TypeConverters(Converters::class)
@Database(entities = [CommitsModel::class], version = 3, exportSchema = false)
abstract class CommitsDatabase : RoomDatabase() {
    abstract val commitsDatabaseDao: CommitsDao

    companion object {
        private var INSTANCE: CommitsDatabase? = null

        fun getInstance(context: Context): CommitsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommitsDatabase::class.java,
                        "commits_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
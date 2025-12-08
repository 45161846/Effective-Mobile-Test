package com.effectivemobiletask.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1,
    exportSchema = true,
    autoMigrations = [

    ]
)
abstract class CoursesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: CoursesDatabase? = null

        fun getInstance(context: Context): CoursesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoursesDatabase::class.java,
                    "courses_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
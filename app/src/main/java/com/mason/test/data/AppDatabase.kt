package com.mason.test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],  // 包含的 Entity
    version = 1,  // 資料庫版本
    exportSchema = false  // 是否匯出 schema
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    @Volatile  // 確保多執行緒安全
    private var INSTANCE: AppDatabase? = null

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.example.proyecto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto.data.dao.CartDao
import com.example.proyecto.data.dao.ProductDao
import com.example.proyecto.data.entity.CartItemEntity
import com.example.proyecto.data.entity.ProductEntity

@Database(entities = [ProductEntity::class, CartItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "urbansteps_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.pratik.chatapp.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pratik.chatapp.model.Chat
import com.pratik.chatapp.utils.Constants

@Database(entities = [Chat::class], version = 1, exportSchema = false)
abstract class Chat_db : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile
        private var INSTANCE: Chat_db? = null

        fun getDatabase(context: Context): Chat_db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Chat_db::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
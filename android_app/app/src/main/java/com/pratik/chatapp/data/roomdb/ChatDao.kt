package com.pratik.chatapp.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pratik.chatapp.model.Chat

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)

    @Query("SELECT * FROM chats ORDER BY timestamp ASC")
    fun getAllChats(): LiveData<List<Chat>>
}
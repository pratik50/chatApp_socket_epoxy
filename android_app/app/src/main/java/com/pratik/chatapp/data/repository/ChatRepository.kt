package com.pratik.chatapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.pratik.chatapp.model.Chat
import com.pratik.chatapp.data.roomdb.ChatDao
import com.pratik.chatapp.data.roomdb.Chat_db


class ChatRepository(application: Application) {

    private val chatDao: ChatDao
    val allChats: LiveData<List<Chat>>

    init {
        val database = Chat_db.getDatabase(application)
        chatDao = database.chatDao()
        allChats = chatDao.getAllChats()
    }

    suspend fun insertChat(chat: Chat) {
        chatDao.insertChat(chat)
    }

}
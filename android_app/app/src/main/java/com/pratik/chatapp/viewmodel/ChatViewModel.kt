package com.pratik.chatapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pratik.chatapp.model.Chat
import com.pratik.chatapp.data.socket.SocketHandler
import com.pratik.chatapp.data.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val socketHandler = SocketHandler()
    private val repository: ChatRepository
    val allChats: LiveData<List<Chat>>

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    init {
        //Initialize the repository
        repository = ChatRepository(application)

        // Observe new chats from SocketHandler
        socketHandler.onNewChat.observeForever { chat ->
            val chatWithSelfFlag = chat.copy(isSelf = chat.username == _userName.value)
            insertChat(chatWithSelfFlag)
        }

        // Observe chats from the database
        allChats = repository.allChats
    }

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun sendMessage(message: String) {
        val userName = _userName.value ?: return
        if (message.isNotEmpty()) {
            val chat = Chat(username = userName, text = message, isSelf = true)
            insertChat(chat)
            socketHandler.emitChat(chat)
        }
    }

    private fun insertChat(chat: Chat) {
        viewModelScope.launch {
            repository.insertChat(chat)
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketHandler.disconnectSocket()
    }
}
package com.pratik.chatapp.data.socket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pratik.chatapp.model.Chat
import com.pratik.chatapp.utils.Constants
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketHandler {

    private var socket: Socket? = null

    private val _onNewChat = MutableLiveData<Chat>()
    val onNewChat: LiveData<Chat> get() = _onNewChat

    init{
        try {
            socket = IO.socket(Constants.SOCKET_URL)
            socket?.connect()

            registerOnNewMessage()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun registerOnNewMessage() {
        socket?.on(Constants.BROADCAST) { args ->
            args.let { d ->
                if(d.isNotEmpty()){
                    val data = d[0]
                    if (data.toString().isNotEmpty()) {
                        val chat = Gson().fromJson(data.toString(), Chat::class.java)
                        _onNewChat.postValue(chat)
                    }
                }
            }
        }
    }

    fun disconnectSocket() {
        socket?.disconnect()
        socket?.off()
    }

    fun emitChat(chat: Chat){
        val jsonStr = Gson().toJson(chat, Chat::class.java)
        socket?.emit(Constants.NEW_MESSAGE, jsonStr)
    }

}
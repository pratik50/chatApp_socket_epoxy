package com.pratik.chatapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class Chat (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val text: String,
    val isSelf: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
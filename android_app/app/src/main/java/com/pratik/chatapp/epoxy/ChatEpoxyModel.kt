package com.pratik.chatapp.epoxy

import com.pratik.chatapp.R
import com.pratik.chatapp.databinding.ItemChatOtherBinding
import com.pratik.chatapp.databinding.ItemChatSelfBinding
import com.pratik.chatapp.model.Chat

data class SelfChatEpoxyModel (
    val chat: Chat
): ViewBindingKotlinModel<ItemChatSelfBinding>(R.layout.item_chat_self){

    override fun ItemChatSelfBinding.bind() {
        name.text = "you"
        msg.text = chat.text
    }
}

data class OtherChatEpoxyModel(
    val chat: Chat
) : ViewBindingKotlinModel<ItemChatOtherBinding>(R.layout.item_chat_other) {

    override fun ItemChatOtherBinding.bind() {
        name.text = chat.username
        msg.text = chat.text
    }
}
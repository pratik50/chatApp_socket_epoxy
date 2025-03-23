package com.pratik.chatapp.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.pratik.chatapp.model.Chat

class ChatEpoxyController: TypedEpoxyController<List<Chat>>() {

    override fun buildModels(data: List<Chat>?) {
        data?.forEachIndexed { index, chat ->
            if (chat.isSelf) {
                SelfChatEpoxyModel(chat)
                    .id(index)
                    .addTo(this)
            }else{
                OtherChatEpoxyModel(chat)
                    .id(index)
                    .addTo(this)
            }
        }
    }
}
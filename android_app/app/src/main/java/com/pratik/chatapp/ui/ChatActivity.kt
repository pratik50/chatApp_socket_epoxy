package com.pratik.chatapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pratik.chatapp.R
import com.pratik.chatapp.databinding.ActivityChatBinding
import com.pratik.chatapp.epoxy.ChatEpoxyController
import com.pratik.chatapp.utils.Constants
import com.pratik.chatapp.viewmodel.ChatViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val chatViewModel: ChatViewModel by viewModels(
        factoryProducer = { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
    )
   // private lateinit var chatAdapter: ChatAdapter
    private val epoxyController = ChatEpoxyController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val toolbar: Toolbar = findViewById(R.id.chat_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // RecyclerView
//        chatAdapter = ChatAdapter()
//        binding.rvChat.apply {
//            layoutManager = LinearLayoutManager(this@ChatActivity)
//            adapter = chatAdapter
//        }

        binding.rvChat.setController(epoxyController)

        // setting username we received
        val userName = intent.getStringExtra(Constants.USERNAME) ?: ""
        if (userName.isEmpty()) {
            finish()
        } else {
            chatViewModel.setUserName(userName)
        }

        // Observe chat list
        chatViewModel.allChats.observe(this, Observer { chats ->
            epoxyController.setData(chats )
            if (chats.isNotEmpty()) {
                binding.rvChat.post {
                    binding.rvChat.smoothScrollToPosition(chats.size - 1) // Scroll to the bottom
                }
            }
        })

        // Observe chat from the database
//        chatViewModel.allChats.observe(this, Observer { chats ->
//            chatAdapter.submitChat(chats)
//            if (chats.isNotEmpty()) {
//                binding.rvChat.smoothScrollToPosition(chats.size - 1) // Scroll to the bottom
//            }
//        })

        // Send message
//        binding.btnSend.setOnClickListener {
//            val message = binding.etMsg.text.toString()
//            chatViewModel.sendMessage(message)
//            binding.etMsg.setText("")
//        }

        binding.btnSend.setOnClickListener {
            val message = binding.etMsg.text.toString()
            chatViewModel.sendMessage(message)
            binding.etMsg.setText("")
        }
    }
}
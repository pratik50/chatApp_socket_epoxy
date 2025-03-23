package com.pratik.chatapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.pratik.chatapp.databinding.ActivityUserNameBinding
import com.pratik.chatapp.utils.Constants

class UserNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etUsername.doAfterTextChanged {
            val username = it.toString()
            binding.btnProceed.isEnabled = username.isNotEmpty()
        }

        binding.btnProceed.setOnClickListener {
            val username = binding.etUsername.text.toString()
            if (username.isNotEmpty()) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(Constants.USERNAME, username)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etUsername.requestFocus()
    }
}
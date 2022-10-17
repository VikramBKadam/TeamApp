package com.example.teamapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teamapp.R
import com.example.teamapp.databinding.ActivityInviteMemberBinding

class InviteMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInviteMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityInviteMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init(){
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, InviteMemberActivity::class.java)
        }
    }
}
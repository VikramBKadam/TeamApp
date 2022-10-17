package com.example.teamapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    private fun init() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.button.setOnClickListener { startActivity(InviteMemberActivity.newIntent(this)) }
    }
}
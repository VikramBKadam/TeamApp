package com.example.teamapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private var usecase: Int = 0 //we have 4 useCase to test

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
        binding.button.setOnClickListener {
            // not finishing this activity so as to test the useCases when come back and click on invite
            if (usecase == 4) {
                usecase = 0
            }
            usecase++
            InviteMemberActivity.startActivity(this, usecase.toString())
        }
    }
}
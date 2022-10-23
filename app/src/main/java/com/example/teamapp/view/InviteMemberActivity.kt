package com.example.teamapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teamapp.databinding.ActivityInviteMemberBinding
import com.example.teamapp.model.Response
import com.example.teamapp.model.TeamResponse
import com.example.teamapp.viewModel.InviteMemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InviteMemberActivity : AppCompatActivity() {
    private val inviteMemberViewModel: InviteMemberViewModel by viewModels()
    private lateinit var binding : ActivityInviteMemberBinding
    private val TAG = InviteMemberActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInviteMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        observeTeamResponse()
        getTeam()

    }

    private fun getTeam() {
        lifecycleScope.launch {
            inviteMemberViewModel.getTeam("abc")
        }
    }

    private fun observeTeamResponse() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                inviteMemberViewModel.teamResponse.collect { response ->
                    Log.d(TAG, "collect response")
                    when (response) {
                        is Response.Success -> {
                            updateUI(response.data)
                        }
                        else -> {//doing nothing as response is always success
                        }
                    }
                }
            }

        }
    }

    private fun updateUI(teamResponse: TeamResponse) {
        Log.d(TAG,"Update UI")
        binding.noCurretMember.text = teamResponse.members.members.toString()
        binding.noCurrentSupporter.text = teamResponse.members.supporters.toString()
        binding.noCurretMemberLimit.text = teamResponse.plan.memberLimit.toString()
        binding.noCurrentSupporterLimit.text = teamResponse.plan.supporterLimit.toString()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, InviteMemberActivity::class.java)
        }
    }
}
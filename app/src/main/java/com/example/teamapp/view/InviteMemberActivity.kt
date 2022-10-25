package com.example.teamapp.view


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teamapp.R
import com.example.teamapp.databinding.ActivityInviteMemberBinding
import com.example.teamapp.model.PermissionUtils
import com.example.teamapp.model.Response
import com.example.teamapp.model.Role
import com.example.teamapp.model.Team
import com.example.teamapp.viewModel.InviteMemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class InviteMemberActivity : AppCompatActivity(), View.OnClickListener {
    private val inviteMemberViewModel: InviteMemberViewModel by viewModels()
    private lateinit var binding: ActivityInviteMemberBinding
    private val TAG = InviteMemberActivity::class.java.simpleName
    private var TEAM_ID: String? = null
    private lateinit var spinnerArrayAdapter: SpinnerArrayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInviteMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.shareQrCode.setOnClickListener(this)
        binding.copyLink.setOnClickListener(this)
        observeTeamResponse()
        TEAM_ID = intent.getStringExtra(KEY_TEAM_ID)

        getTeam()

    }

    private fun getTeam() {
        lifecycleScope.launch {
            TEAM_ID?.let { inviteMemberViewModel.getTeam(it) }
        }
    }

    private fun getInvitationUrl(role: Role) {
        lifecycleScope.launch {
            TEAM_ID?.let { inviteMemberViewModel.getInvitationURL(it, role) }
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

    private fun updateUI(team: Team) {
        Log.d(TAG, "Update UI")
        binding.noCurretMember.text = team.members.members.toString()
        binding.noCurrentSupporter.text = team.members.supporters.toString()
        binding.noCurretMemberLimit.text = team.plan.memberLimit.toString()
        binding.noCurrentSupporterLimit.text = team.plan.supporterLimit.toString()
        if (team.plan.supporterLimit == PermissionUtils.MINIMUM_SUPPORTERS_LIMIT) {
            binding.currentSupporters.visibility = View.GONE
        }
        spinnerArrayAdapter = SpinnerArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            mutableListOf(
                PermissionUtils.PLAYER,
                PermissionUtils.COACH,
                PermissionUtils.PLAYER_COACH,
                PermissionUtils.SUPPORTER
            ) as List<CharSequence>, team

        )

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerArrayAdapter

    }

    companion object {
        private const val KEY_TEAM_ID = "teamID"
        fun startActivity(activity: Activity, teamId: String) {
            val intent = Intent(activity, InviteMemberActivity::class.java)
            intent.putExtra(KEY_TEAM_ID, teamId)
            activity.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.share_qr_code -> {
                //  binding.spinner.selectedItem.toString()
                getInvitationUrl(Role(binding.spinner.selectedItem.toString()))
                // do some work here
            }
            R.id.copy_link -> {
                // do some work here
            }
        }
    }
}
package com.example.teamapp.view


import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.teamapp.R
import com.example.teamapp.databinding.ActivityInviteMemberBinding
import com.example.teamapp.utils.PermissionUtils
import com.example.teamapp.model.Response
import com.example.teamapp.model.Role
import com.example.teamapp.model.Team
import com.example.teamapp.viewModel.InviteMemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class InviteMemberActivity : AppCompatActivity(), View.OnClickListener, OnItemSelectedListener {
    private val inviteMemberViewModel: InviteMemberViewModel by viewModels()
    private lateinit var binding: ActivityInviteMemberBinding
    private val TAG = InviteMemberActivity::class.java.simpleName
    private var teamId: String? = null
    private lateinit var spinnerArrayAdapter: SpinnerArrayAdapter
    private var invitationUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInviteMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.toolBar)
        binding.toolbar.toolBar.setNavigationOnClickListener { finish() }
        binding.shareQrCode.setOnClickListener(this)
        binding.copyLink.setOnClickListener(this)
        binding.toolbar.idTextBack.setOnClickListener(this)
        observeTeamResponse()
        observeInvitationUrl()
        teamId = intent.getStringExtra(KEY_TEAM_ID)
        getTeam()

    }

    private fun getTeam() {
        lifecycleScope.launch {
            teamId?.let { inviteMemberViewModel.getTeam(it) }
        }
    }

    private fun getInvitationUrl(role: Role) {
        lifecycleScope.launch {
            teamId?.let { inviteMemberViewModel.getInvitationURL(it, role) }
        }
    }

    private fun observeTeamResponse() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                inviteMemberViewModel.teamResponse.collect { response ->
                    Log.d(TAG, "collect response team$response")
                    when (response) {
                        is Response.Success -> {
                            updateUI(response.data)

                        }
                        else -> {
                            //doing nothing as response is always success
                        }
                    }
                }
            }

        }
    }

    private fun observeInvitationUrl() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                inviteMemberViewModel.invitationUrlResponse.collect { response ->
                    Log.d(TAG, "collect response invitationUrl $response")
                    when (response) {
                        is Response.Success -> {
                            invitationUrl = response.data.url
                        }
                        else -> {
                            //doing nothing as response is always success
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
        binding.spinner.onItemSelectedListener = this


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
                invitationUrl?.let { QrCodeActivity.startActivity(this, it) }
            }
            R.id.copy_link -> {
                copyLinkToClipboard()
            }
            R.id.id_text_back -> {
                finish()
            }
        }
    }

    private fun copyLinkToClipboard() {
        val clipboard: ClipboardManager =
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("invitationUrl", invitationUrl)
        Toast.makeText(this, "Invitation Url is copied ", Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clip)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getInvitationUrl(PermissionUtils.getRole(binding.spinner.selectedItem.toString()))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        getInvitationUrl(PermissionUtils.getRole(binding.spinner.selectedItem.toString()))
    }
}
package com.example.teamapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.teamapp.di.DefaultDispatcher
import com.example.teamapp.di.IODispatcher
import com.example.teamapp.di.repository.APIRepository
import com.example.teamapp.model.InvitationUrl
import com.example.teamapp.model.Response
import com.example.teamapp.model.Role
import com.example.teamapp.model.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext

import javax.inject.Inject

@HiltViewModel
class InviteMemberViewModel @Inject constructor(
    private val apiRepository: APIRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _teamResponse: MutableSharedFlow<Response<Team>> =
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    val teamResponse: SharedFlow<Response<Team>> =
        _teamResponse.asSharedFlow()

    private val _invitationUrlResponse: MutableSharedFlow<Response<InvitationUrl>> =
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    val invitationUrlResponse: SharedFlow<Response<InvitationUrl>> =
        _invitationUrlResponse.asSharedFlow()

    suspend fun getTeam(teamId: String) =
        withContext(ioDispatcher) {
            val response = apiRepository.getTeam(teamId)
            val responseBody = response.body()
            responseBody?.let { _teamResponse.emit(Response.Success(it)) }
        }

    suspend fun getInvitationURL(teamId: String, role: Role) {
        withContext(ioDispatcher) {
            val response = apiRepository.getInvitation(teamId, role)
            val responseBody = response.body()
            responseBody?.let { _invitationUrlResponse.emit(Response.Success(it)) }
        }
    }

}
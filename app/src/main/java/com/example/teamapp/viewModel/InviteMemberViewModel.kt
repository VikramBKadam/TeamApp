package com.example.teamapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.teamapp.di.DefaultDispatcher
import com.example.teamapp.di.IODispatcher
import com.example.teamapp.di.repository.APIRepository
import com.example.teamapp.model.Response
import com.example.teamapp.model.TeamResponse
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


    private val _teamResponse: MutableSharedFlow<Response<TeamResponse>> =
        MutableSharedFlow(
            replay = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    val teamResponse: SharedFlow<Response<TeamResponse>> =
        _teamResponse.asSharedFlow()

    suspend fun getTeam(teamId: String) =
        withContext(ioDispatcher) {
            Log.d("CheckFlow", "inside getTeam")
            val response = apiRepository.getTeam(teamId)
            val responseBody = response.body()
            Log.d("CheckFlow", "responseBody: " + responseBody.toString())
            responseBody?.let { _teamResponse.emit(Response.Success(it)) }
        }

}
package com.example.teamapp.di.repository

import com.example.teamapp.di.repository.retrofit.APIService
import com.example.teamapp.model.Role
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiService: APIService) {
    suspend fun getTeam(teamId: String) = apiService.getTeam(teamId)
    suspend fun getInvitation(teamId: String,role: Role) = apiService.getInvitationURL(teamId, role)


}
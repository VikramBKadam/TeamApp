package com.example.teamapp.di.repository

import com.example.teamapp.di.repository.retrofit.APIService
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiService: APIService) {
    suspend fun getTeam(teamId: String) = apiService.getTeam(teamId)
}
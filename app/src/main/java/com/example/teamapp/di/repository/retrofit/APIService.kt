package com.example.teamapp.di.repository.retrofit

import com.example.teamapp.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/teams/{teamId}")
    suspend fun getTeam(@Query("teamId") teamId: String): Response<TeamResponse>
}
package com.example.teamapp.di.repository.retrofit

import com.example.teamapp.model.InvitationUrl
import com.example.teamapp.model.Role
import com.example.teamapp.model.Team
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @GET("/teams/{teamId}")
    suspend fun getTeam(@Query("teamId") teamId: String): Response<Team>

    @POST("/teams/{teamId}/invites")
    suspend fun getInvitationURL(@Query("teamId") teamId: String, @Body role: Role): Response<InvitationUrl>

}
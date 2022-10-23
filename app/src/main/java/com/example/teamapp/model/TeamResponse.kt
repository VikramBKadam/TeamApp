package com.example.teamapp.model

data class TeamResponse(
    val id: String,
    val members: Members,
    val plan: Plan
)

data class Members(
    val total: Long,
    val administrators: Long,
    val managers: Long,
    val editors: Long,
    val members: Long,
    val supporters: Long
)

data class Plan(
    val memberLimit: Long,
    val supporterLimit: Long
)

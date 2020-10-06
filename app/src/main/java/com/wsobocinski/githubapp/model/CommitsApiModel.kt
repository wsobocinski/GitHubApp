package com.wsobocinski.githubapp.model

data class CommitsApiModel(
    val commit: Commit?,
    val sha: String?,
)
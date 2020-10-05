package com.wsobocinski.githubapp.model

data class RepositoryContents(
    val repositoryId: Long,
    val commits: List<SingleCommit>
)
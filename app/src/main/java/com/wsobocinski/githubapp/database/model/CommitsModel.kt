package com.wsobocinski.githubapp.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wsobocinski.githubapp.model.SingleCommit

@Entity(tableName = "commits")
data class CommitsModel (
    @PrimaryKey
    @ColumnInfo(name = "repository_id")
    val repositoryId: String,
    val commits: List<SingleCommit>?
)
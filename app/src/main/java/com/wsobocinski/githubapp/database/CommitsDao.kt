package com.wsobocinski.githubapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wsobocinski.githubapp.database.model.CommitsModel

@Dao
interface CommitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCommits(employee: CommitsModel)

    @Query("SELECT * from commits WHERE repository_id = :repositoryId")
    suspend fun getCommitsByRepositoryId(repositoryId: String): CommitsModel?


    @Query("SELECT * from commits")
    suspend fun getAllCommits(): List<CommitsModel>
}
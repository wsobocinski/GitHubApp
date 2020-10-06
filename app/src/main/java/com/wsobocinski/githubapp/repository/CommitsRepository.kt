package com.wsobocinski.githubapp.repository

import com.wsobocinski.githubapp.database.CommitsDao
import com.wsobocinski.githubapp.database.CommitsDatabase

import android.app.Application
import com.wsobocinski.githubapp.database.model.CommitsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommitsRepository private constructor(application: Application) {
    private val commitsDao: CommitsDao

    init {
        val commitsDatabase = CommitsDatabase.getInstance(application)
        commitsDao = commitsDatabase.commitsDatabaseDao

    }

    suspend fun addCommits(commits: CommitsModel) {
        withContext(Dispatchers.IO) {
            commitsDao.addCommits(commits)
        }
    }

    suspend fun getCommitsFromOwnerAndRepoNames(ownerName: String, repoName: String):CommitsModel? {
        return withContext(Dispatchers.IO) {
            commitsDao.getCommitsFromOwnerAndRepoNames(ownerName, repoName)
        }
    }

    companion object {
        private var INSTANCE: CommitsRepository? = null

        fun getInstance(application: Application): CommitsRepository {
            var instance = INSTANCE
            if (instance == null) {
                instance = CommitsRepository(application)
                INSTANCE = instance
            }
            return instance
        }
    }
}
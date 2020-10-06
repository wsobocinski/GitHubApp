package com.wsobocinski.githubapp.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wsobocinski.githubapp.database.model.CommitsModel
import com.wsobocinski.githubapp.model.SingleCommit
import com.wsobocinski.githubapp.network.GithubApi
import com.wsobocinski.githubapp.repository.CommitsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchViewModel(application: Application) : AndroidViewModel(application) {

    var commitsModel = MutableLiveData<CommitsModel>()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun getListOfCommits(owner: String, repo: String) {
        getGitHubResponse(owner, repo)
        uiScope.launch {
            if (commitsModel.value != null)
                CommitsRepository.getInstance(getApplication()).addCommits(commitsModel.value!!)

        }
    }

    private fun getGitHubResponse(owner:String, repository: String) {
        uiScope.launch {
            val getCommitsDeferred = GithubApi.retrofitService.getCommitsFromOwnersRepository(owner, repository)
            val getRepoIdDeferred = GithubApi.retrofitService.getOwnersRepository(owner, repository)
            try {
                val commitsResult = getCommitsDeferred.await()
                val repoIdResult = getRepoIdDeferred.await()

                val responseCommits = mutableListOf<SingleCommit>()
                commitsResult.forEach {
                    val shaValue = it.sha
                    val message = it.commit?.message
                    val author = it.commit?.author?.name
                    responseCommits.add(SingleCommit(message, shaValue, author))
                }
                commitsModel.postValue(CommitsModel(repoIdResult.id, owner, repository, responseCommits))
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "getFixerResponse: " + e.message)
            }
        }
    }
}
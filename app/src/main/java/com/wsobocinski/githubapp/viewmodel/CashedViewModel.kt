package com.wsobocinski.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wsobocinski.githubapp.database.model.CommitsModel
import com.wsobocinski.githubapp.repository.CommitsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CashedViewModel(application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var text = MutableLiveData<String>()

    val repositoryResponse = MutableLiveData<CommitsModel>()



    fun getCommitsForOwnerAndRepoNames(owner:String, repo:String) {
        uiScope.launch {
            repositoryResponse.postValue(CommitsRepository.getInstance(getApplication())
                .getCommitsFromOwnerAndRepoNames(owner, repo))
        }

    }

}
package com.wsobocinski.githubapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wsobocinski.githubapp.model.CommitModel
import com.wsobocinski.githubapp.model.RepositoryModel
import com.wsobocinski.githubapp.model.SingleCommit
import com.wsobocinski.githubapp.network.GithubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel : ViewModel() {

    var text: MutableLiveData<String> = MutableLiveData<String>()
    var listOfCommits = MutableLiveData<MutableList<SingleCommit>>()


    init {
        text.value = "NONE"
    }
    fun getRepositoryFromOwner(owner: String, repository: String) {
        val api = GithubApi.retrofitService.getOwnersRepository(owner, repository)
        api.enqueue(object: Callback<RepositoryModel> {
            override fun onResponse(
                call: Call<RepositoryModel>,
                response: Response<RepositoryModel>) {
                text.postValue(response.body()?.id)
            }

            override fun onFailure(call: Call<RepositoryModel>, t: Throwable) {
                text.value = t.message
            }
        })
    }
    fun getCommitsFromOwnersRepository(owner: String, repository: String){
        val api = GithubApi.retrofitService.getCommitsFromOwnersRepository(owner, repository)
        api.enqueue(object: Callback<List<CommitModel>> {
            override fun onResponse(call: Call<List<CommitModel>>,
                response: Response<List<CommitModel>>) {

                var responseCommits = mutableListOf<SingleCommit>()
                response.body()?.forEach {
                    val shaValue = it.sha
                    val message = it.commit?.message
                    val author = it.commit?.author?.name
                    responseCommits.add(SingleCommit(message, shaValue, author))
                }
                listOfCommits.postValue(responseCommits)
            }

            override fun onFailure(call: Call<List<CommitModel>>, t: Throwable) {
                text.value = t.message
            }
        })
    }

}
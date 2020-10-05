package com.wsobocinski.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wsobocinski.githubapp.model.UserModel
import com.wsobocinski.githubapp.network.GitHubService
import com.wsobocinski.githubapp.network.GithubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class HomeViewModel : ViewModel() {

    var text: MutableLiveData<String> = MutableLiveData<String>()

    init {
        text.value = "NOTHING"
    }


    fun testFunction(){
        val api = GithubApi.retrofitService.getUser("wsobocinski")
        api.enqueue(object: Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                text.value = response.body().toString()
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                text.value = t.message
            }
        })
    }

}
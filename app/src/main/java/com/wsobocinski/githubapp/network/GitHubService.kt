package com.wsobocinski.githubapp.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wsobocinski.githubapp.model.CommitsApiModel
import com.wsobocinski.githubapp.model.RepositoryModel
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path






interface GitHubService {
    @GET("/repos/{owner}/{repository}/commits")
    fun getCommitsFromOwnersRepository(@Path("owner") owner: String,
                                       @Path("repository") repository: String)
            : Deferred<List<CommitsApiModel>>

    @GET("/repos/{owner}/{repository}")
    fun getOwnersRepository(@Path("owner") owner: String,
                                       @Path("repository") repository: String)
            : Deferred<RepositoryModel>
}

object GithubApi {

    private const val BASE_URL = "https://api.github.com/"

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retrofitService: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }
}


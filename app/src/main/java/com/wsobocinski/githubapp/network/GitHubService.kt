package com.wsobocinski.githubapp.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wsobocinski.githubapp.model.CommitModel
import com.wsobocinski.githubapp.model.RepositoryModel
import com.wsobocinski.githubapp.model.UserModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://api.github.com/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()



interface GitHubService {
    @GET("/users/{user}")
    fun getUser(@Path("user") user: String?): Call<UserModel>

    @GET("/repos/{owner}/{repository}/commits")
    fun getCommitsFromOwnersRepository(@Path("owner") owner: String,
                                       @Path("repository") repository: String)
            : Call<List<CommitModel>>

    @GET("/repos/{owner}/{repository}")
    fun getOwnersRepository(@Path("owner") owner: String,
                                       @Path("repository") repository: String)
            : Call<RepositoryModel>
}

object GithubApi {
    val retrofitService: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }
}
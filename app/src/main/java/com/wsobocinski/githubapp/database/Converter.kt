package com.wsobocinski.githubapp.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wsobocinski.githubapp.model.SingleCommit

class Converters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val type = Types.newParameterizedType(List::class.java, SingleCommit::class.java)
    private val moshiAdapter =  moshi.adapter<List<SingleCommit>>(type)

    @TypeConverter
    fun fromAddresses(singleCommit: List<SingleCommit>?): String {
        return moshiAdapter.toJson(singleCommit)
    }

    @TypeConverter
    fun toAddresses(singleCommit: String?): List<SingleCommit>? {
        return moshiAdapter.fromJson(singleCommit)
    }
}
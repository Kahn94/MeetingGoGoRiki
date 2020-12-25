package ru.kahn.meetinggogoriki.application

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kahn.meetinggogoriki.interfaces.ApiInterface

class AppGoGoRiRiki : Application() {

    fun retrofitSettings(urlParty: String): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlParty)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(
            ApiInterface:: class.java)
    }
}

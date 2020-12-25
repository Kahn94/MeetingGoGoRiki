package ru.kahn.meetinggogoriki.interfaces

import retrofit2.Call
import retrofit2.http.GET
import ru.kahn.meetinggogoriki.model.ModelPartyMain

interface ApiInterface {
    @GET
    fun getResponce(): Call<ModelPartyMain>
}
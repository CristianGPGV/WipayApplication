package com.prueba.wipayapplication.marvelapi.io

import com.prueba.wipayapplication.marvelapi.model.MarvelRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("v1/public/characters")
     suspend fun getCharacter(@Query("nameStartsWith") nameCharacter: String) : retrofit2.Response<MarvelRequest>

    @GET("v1/public/characters/{idCharacter}/comics")
    fun getComics(@Path("idCharacter") idCharacter: String) : Call<MarvelRequest>


    companion object {

        private const val ts = "1000"
        private const val apikey = "7382da0172fd251f4a19b4691f73e3f7"
        private const val hash = "e92e19ea132e219681b24593d279506a"

        private val client = OkHttpClient.Builder()
            .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
            .build()

        private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url
            val newUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", ts)
                .addQueryParameter("apikey", apikey)
                .addQueryParameter("hash", hash).build()
            request.url(newUrl)
            return chain.proceed(request.build())
        }

        private var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }


}
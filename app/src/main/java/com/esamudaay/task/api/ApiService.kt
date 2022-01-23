package com.esamudaay.task.api

import com.esamudaay.task.data.Pet
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("animals")
    fun getPetsList(@Query("page") page: Int, @Query("limit") limit: Int,
                    @Query("sortBy") sortBy : String,@Query("order") order : String) : Call<List<Pet>>

    @GET("animals")
    fun searchPet(@Query("page") page: Int, @Query("limit") limit: Int,
                    @Query("search") search : String) : Call<List<Pet>>

    companion object {

        var retrofitService: ApiService? = null

        fun getInstance() : ApiService {

            //single instance

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://60d075407de0b20017108b89.mockapi.io/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }
    }
}
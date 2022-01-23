package com.esamudaay.task.repository

import com.esamudaay.task.api.ApiService

class MainRepository constructor(private val retrofitService: ApiService) {

    fun getPetsList(page : Int,limit : Int,sortBy : String,order : String) = retrofitService.getPetsList(page,limit,sortBy,order)
    fun searchPet(page : Int,limit : Int,input : String) = retrofitService.searchPet(page,limit,input)

}
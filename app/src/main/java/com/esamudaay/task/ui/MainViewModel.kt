package com.esamudaay.task.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esamudaay.task.data.Pet
import com.esamudaay.task.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {
    val petsList = MutableLiveData<List<Pet>>()
    val errorMessage = MutableLiveData<String>()

    //view model sents data to UI

    fun getAllPetsInfo(page : Int,limit : Int,sortBy : String,order : String) {
        val response = repository.getPetsList(page,limit,sortBy,order)
        getData(response)
    }

    fun searchPets(page : Int,limit : Int,input : String) {
        val response = repository.searchPet(page,limit,input)
        getData(response)
    }

    private fun getData(response: Call<List<Pet>>) {
        response.enqueue(object : Callback<List<Pet>> {
            override fun onResponse(call: Call<List<Pet>>, response: Response<List<Pet>>) {
                petsList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Pet>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}
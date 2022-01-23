package com.esamudaay.task

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esamudaay.task.api.ApiService
import com.esamudaay.task.data.MainViewModel
import com.esamudaay.task.data.Pet
import com.esamudaay.task.databinding.ActivityMainBinding
import com.esamudaay.task.repository.MainRepository
import com.esamudaay.task.ui.DrawableClickListener
import com.esamudaay.task.ui.DrawableClickListener.DrawablePosition
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = ApiService.getInstance()
    val adapter = PetsDisplayAdapter()
    var page = 1
    var limit = 10
    var isSearchResults = false
    var previousData : ArrayList<Pet> ? = null
    var order = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        previousData = ArrayList()

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )

        binding.recyclerview.adapter = adapter

        viewModel.petsList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            if (page==1){
                previousData = ArrayList()
            }
            previousData?.addAll(it)
            adapter.setPetsListData(previousData!!)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllPetsInfo(page, limit, "", "")

        btnFilter1.setOnClickListener{
            highlightBtn(btnFilter1)
            order = "desc"
            viewModel.getAllPetsInfo(page, limit, "bornAt", order)
        }

        btnFilter2.setOnClickListener{
            highlightBtn(btnFilter2)
            order = "asc"
            viewModel.getAllPetsInfo(page, limit, "bornAt", order)
        }

        setPagination()


        etPetSearch.setDrawableClickListener(DrawableClickListener { target ->
            when (target) {
                DrawablePosition.RIGHT -> {
                    var input = etPetSearch.text.toString().trim()
                    if (input.isEmpty()){
                        etPetSearch.setError("Please enter something")
                        return@DrawableClickListener
                    }
                    page = 1
                    limit = 10
                    order = ""
                    resetButtonStates()
                    isSearchResults = true
                    viewModel.searchPets(page, limit, input)
                }
                else -> {
                }
            }
        })

    }

    private fun setPagination(){
        binding.recyclerview!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var lastScrollPosition = 0
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null) {
                    lastScrollPosition = linearLayoutManager.findLastVisibleItemPosition()
                }
                Log.d("PAGINATION", "page: " + page + "  limit:" + limit + "  lastScrollPosition: " + lastScrollPosition);
                if (lastScrollPosition >= (page-1)*10 + 9) {
                    page = page + 1;
                    limit = 10;
                    Log.d("productsListInfo", "pagination")
                    if (!isSearchResults){
                        viewModel.getAllPetsInfo(page, limit, "bornAt", order)
                    }else{
                        viewModel.searchPets(page,limit,etPetSearch.text.toString().trim())
                    }

                }
            }
        })
    }

    private fun highlightBtn(btn: MaterialButton?) {
        resetButtonStates()
        btn?.strokeColor = ColorStateList.valueOf(getColor(R.color.colorAccent))
        etPetSearch.setText("")
        page =1
        limit = 10
        isSearchResults = false
    }

    private fun resetButtonStates() {
        btnFilter1.strokeColor = ColorStateList.valueOf(getColor(R.color.white))
        btnFilter2.strokeColor = ColorStateList.valueOf(getColor(R.color.white))
    }
}
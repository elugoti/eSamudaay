package com.esamudaay.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esamudaay.task.data.Pet
import com.esamudaay.task.databinding.ItemPetBinding

class PetsDisplayAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var petsList = mutableListOf<Pet>()

    fun setPetsListData(petsList: List<Pet>) {
        this.petsList = petsList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemPetBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val pet = petsList[position]
        var dateFormat = Utils.uTCToLocal(pet.bornAt)
        var daysDiff = Utils.getDaysDiff(dateFormat)
        var months = daysDiff/30 //assuming 30 days a month
        var years = daysDiff/365 //ignoring leap year
        if (years==0L&&months==12L){
            months = 0L
            years += 1
        }
        if (years!=0L){
            months %= 12
        }
        var txt = ""
        if (months==0L&&years==0L){
            txt = "Age: "+daysDiff+" days"
        }else if (months!=0L&&years==0L){
            txt = "Age: "+months+" months"
        }else if (months!=0L&&years!=0L){
            txt = "Age: "+years+" years "+months+" months"
        }else if (months==0L&&years!=0L){
            txt = "Age: "+years+" years "
        }
        holder.binding.tvPetName.text = pet.name
        holder.binding.tvPetAge.text = txt

    }

    override fun getItemCount(): Int {
        return petsList.size
    }
}

class MainViewHolder(val binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root) {

}
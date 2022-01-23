package com.esamudaay.task.data

import com.google.gson.annotations.SerializedName

class Pet {
    @SerializedName("id") val id : Int = 0
    @SerializedName("createdAt") val createdAt : String ? = ""
    @SerializedName("name") val name : String = ""
    @SerializedName("avatar") val avatar : String = ""
    @SerializedName("bornAt") val bornAt : String = ""
}
package com.example.animals.model

import com.google.gson.annotations.SerializedName

data class ApiKey(
    @SerializedName("message")
    val message: String?,

    @SerializedName("key")
    val key: String?
)

data class Animal(
    @SerializedName("name")
    val name: String?,

    @SerializedName("taxonomy")
    val taxonomy: Taxonomy?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("speed")
    val speed: Speed?,

    @SerializedName("diet")
    val diet: String?,

    @SerializedName("lifespan")
    val lifeSpan: String?,

    @SerializedName("image")
    val imageUrl: String?
)

data class Speed(
    @SerializedName("metric")
    val metric: String?,

    @SerializedName("imperial")
    val imperial: String?
)

data class Taxonomy(
    @SerializedName("imperial")
    val kingdom: String?,

    @SerializedName("order")
    val order: String?,

    @SerializedName("family")
    val family: String?
)

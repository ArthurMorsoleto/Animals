package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.model.Animal

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animalsList by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal(name = "Jacaré")
        val a2 = Animal(name = "Macaco")
        val a3 = Animal(name = "Gato")
        val a4 = Animal(name = "Cachorro")
        val a5 = Animal(name = "Elefante")

        val animalListHelper = arrayListOf(a1, a2, a3, a4, a5)

        animalsList.value = animalListHelper
        loadError.value = false
        loading.value = false
    }
}
package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.model.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.view.tvAnimalName.text = animal.name
    }

    class AnimalViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}
package com.example.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    lateinit var animal: Animal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animalArg
            initViewInfos(animal)
        }
    }

    private fun initViewInfos(animal: Animal) {
        setAnimalImage(animal)
        setTitle(animal)
        setupBackgroudColor(animal.imageUrl)

        tvAnimalNameDetail.text = animal.name
        tvAnimalDietDetail.text = animal.diet
        tvAnimalLocationDetail.text = animal.location
        tvAnimalLifeSpanDetail.text = animal.lifeSpan
    }

    private fun setTitle(animal: Animal) {
        activity?.let {
            it.actionBar?.title = animal.name
        }
    }

    private fun setAnimalImage(animal: Animal) {
        context?.let {
            ivAnimalDetail.loadImage(animal.imageUrl, getProgressDrawable(it))
        }
    }

    private fun setupBackgroudColor(imageUrl: String?) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            clAnimalDetail.setBackgroundColor(intColor)
                        }
                }
            })
    }

}

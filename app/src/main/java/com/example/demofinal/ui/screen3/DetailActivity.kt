package com.example.demofinal.ui.screen3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val character = intent.getSerializableExtra("key") as Character
        binding.apply {
            if (character != null) {
                Glide.with(img).load(character.image).into(img)
                txtNAME.text = character.name
                txtSpecies.text = "Species: " + character.species
                txtGender.text = "Gender: " + character.gender
                txtCreated.text = "Created: " + character.created
                txtName.text = "Name: "+ character.name

            }
        }
    }
}
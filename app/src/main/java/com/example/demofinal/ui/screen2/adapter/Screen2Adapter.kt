package com.example.demofinal.ui.screen2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.databinding.ItemRvBinding
import com.example.demofinal.ui.screen1.adapter.Screen1Adapter

class Screen2Adapter :
    PagingDataAdapter<Character, Screen2Adapter.MyViewHolder>(Screen1Adapter.DiffUtilCallBack()) {
    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                Glide.with(imageView).load(character.image).into(imageView)
                tvName.text = character.name
                tvSpecies.text = character.species
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }


}
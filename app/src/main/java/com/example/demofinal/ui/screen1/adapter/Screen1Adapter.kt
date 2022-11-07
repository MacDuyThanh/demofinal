package com.example.demofinal.ui.screen1.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demofinal.data.room.entities.Character as Character
import com.example.demofinal.databinding.ItemRvBinding

class Screen1Adapter : PagingDataAdapter<Character, Screen1Adapter.MyViewHolder>(DiffUtilCallBack()) {

    class DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                Glide.with(imageView).load(character.image).into(imageView)
                tvName.text = character.name
                tvSpecies.text = character.species
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRvBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }






}
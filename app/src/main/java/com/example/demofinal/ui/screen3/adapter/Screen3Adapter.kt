package com.example.demofinal.ui.screen3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demofinal.data.room.entities.Character
import com.example.demofinal.databinding.ItemRvBinding

class Screen3Adapter(
    private var mutableList: MutableList<Character>,
    val tmp: (character: Character) -> Unit
) : RecyclerView.Adapter<Screen3Adapter.MyViewHolder>() {

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
        val itemCharacters = mutableList[position]
        holder.binding.viewItem.setOnClickListener {
            tmp(mutableList[position])
        }
        holder.bind(itemCharacters)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}
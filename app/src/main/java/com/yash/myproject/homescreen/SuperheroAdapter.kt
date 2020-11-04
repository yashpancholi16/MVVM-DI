package com.yash.myproject.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yash.myproject.R
import com.yash.myproject.databinding.ItemSuperheroBinding
import com.yash.myproject.model.Character
import com.yash.myproject.utils.load

class SuperheroAdapter : RecyclerView.Adapter<SuperheroAdapter.SuperHeroViewHolder>() {

    private val characterList = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        return SuperHeroViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_superhero,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size

    fun updateUI(list: ArrayList<Character>) {
        characterList.clear()
        characterList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SuperHeroViewHolder(private val binding: ItemSuperheroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(props: Character) {
            binding.imgThumbnail.load("${props.thumbnail.path}/standard_medium.${props.thumbnail.extension}")
            binding.txtName.text = props.name
        }

    }
}
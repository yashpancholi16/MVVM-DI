package com.yash.myproject.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yash.myproject.R
import com.yash.myproject.databinding.ItemSuperheroBinding
import com.yash.myproject.model.Character
import com.yash.myproject.utils.ClickListener
import com.yash.myproject.utils.load

class SuperheroAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<SuperheroAdapter.SuperHeroViewHolder>() {

    private val characterList = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        return SuperHeroViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_superhero,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int = characterList.size

    fun updateUI(list: ArrayList<Character>, pageNo: Int) {
        if (pageNo == 1 && characterList.isNotEmpty())
        {
            characterList.clear()
            notifyDataSetChanged()
        }
        val startPosition = characterList.size
        characterList.addAll(list)
        notifyItemRangeInserted(startPosition,list.size)
    }

    inner class SuperHeroViewHolder(private val binding: ItemSuperheroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(props: Character) {
            binding.imgThumbnail.load("${props.thumbnail.path}/standard_medium.${props.thumbnail.extension}")
            binding.txtName.text = props.name

            binding.root.setOnClickListener {
                listener.onSuperHeroClick(props)
            }
        }

    }
}
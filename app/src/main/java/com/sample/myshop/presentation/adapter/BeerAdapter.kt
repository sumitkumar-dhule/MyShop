package com.sample.myshop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.myshop.databinding.ItemBeerBinding
import com.sample.domain.model.Beer

class BeerAdapter : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(layoutInflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Beer) -> Unit)? = null

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bindArticle(article)

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }

    }

    fun setOnItemClickListener(listener: (Beer) -> Unit) {
        onItemClickListener = listener
    }

    class BeerViewHolder(private val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(item: Beer) {
            binding.beer = item
            binding.executePendingBindings()
        }
    }

}
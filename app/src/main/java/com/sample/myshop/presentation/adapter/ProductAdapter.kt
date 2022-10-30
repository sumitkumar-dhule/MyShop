package com.sample.myshop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.myshop.databinding.ItemProductBinding
import com.sample.domain.model.Product

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.BeerViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Product) -> Unit)? = null

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bindArticle(article)

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }

    }

    fun setOnItemClickListener(listener: (Product) -> Unit) {
        onItemClickListener = listener
    }

    class BeerViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindArticle(item: Product) {
            binding.beer = item
            binding.executePendingBindings()
        }
    }

}
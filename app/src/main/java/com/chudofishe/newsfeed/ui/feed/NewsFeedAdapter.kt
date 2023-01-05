package com.chudofishe.newsfeed.ui.feed

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chudofishe.newsfeed.databinding.NewsFeedItemBinding
import com.chudofishe.newsfeed.domain.model.Article

class NewsFeedAdapter : PagingDataAdapter<Article, NewsItemViewHolder>(ArticleDiffItemCallback) {
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsFeedItemBinding.inflate(inflater, parent, false)
        return NewsItemViewHolder(binding)
    }
}

class NewsItemViewHolder(private val binding: NewsFeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article?) {
        with(binding) {
            Glide.with(image).load(article?.urlToImage).placeholder(ColorDrawable(Color.TRANSPARENT)).into(image)
            title.text = article?.title
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.url == newItem.url
    }
}
package ru.netology.nmedia.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
internal class PostAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val shareClicked: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.ViewHolder>(Diffcallback) {
    var posts: List<Post> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    private object Diffcallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id != newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem != newItem
    }


    inner class ViewHolder(private val postBinding: PostBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
        fun bind(post: Post) = with(postBinding) {
            println(post)
            postBinding.schoolname.setText(post.author)
            postBinding.date.setText(post.date)
            postBinding.content.setText(post.content)
            if (post.likedbyMe == true) {
                postBinding.likes.setImageResource(ru.netology.nmedia.R.drawable.liked24)
            } else {
                postBinding.likes.setImageResource(ru.netology.nmedia.R.drawable.ic_baseline_favorite_border_24)
            }
            postBinding.likescount.setText((post.liked).toString())
            postBinding.sharescount.setText(post.repostsQ.toString())
            postBinding.likes.setOnClickListener {
                onLikeClicked(post)
            }
            postBinding.shares.setOnClickListener {
                shareClicked(post)
            }
        }
    }


}
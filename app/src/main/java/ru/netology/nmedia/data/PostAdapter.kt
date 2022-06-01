package ru.netology.nmedia.data

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
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
    private val shareClicked: (Post) -> Unit,
    private val deleteClicked: (Post) -> Unit,
    private val editClicked: (Post) -> Unit,
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


        @SuppressLint("SetTextI18n")
        fun bind(post: Post) = with(postBinding) {
            val popupMenu by lazy {
                //println(post)
                PopupMenu(itemView.context, postBinding.menuButton).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                //println(post)
                                deleteClicked(post)
                                true
                            }
                            R.id.edit -> {
                                editClicked(post)
                                true
                            }
                            else -> false
                        }
                    }
                }
            }
            postBinding.schoolname.setText(post.author)
            postBinding.date.setText(post.date)
            postBinding.content.setText(post.content)


            postBinding.likes.setText((post.liked).toString())
            postBinding.likes.isChecked = post.likedbyMe
            postBinding.likes.setIconResource(R.drawable.ic_likes_favorites_red)

            postBinding.shares.setText(post.repostsQ.toString())
            postBinding.shares.isChecked = post.repostsQ > 0
            postBinding.shares.setIconResource(R.drawable.allshares)



            if (post.edited == true) {
                postBinding.edited.setText(postBinding.edited.context.getText(R.string.edited))
                postBinding.edited.setVisibility(android.view.View.VISIBLE)
            } else {
                postBinding.edited.setVisibility(android.view.View.GONE)
            }
            postBinding.likes.setOnClickListener {
                onLikeClicked(post)
            }
            postBinding.shares.setOnClickListener {
                shareClicked(post)
            }

            postBinding.menuButton.setOnClickListener {
                postBinding.menuButton.isChecked = true
                println(postBinding.menuButton.isChecked)
                popupMenu.show()
            }

            popupMenu.setOnDismissListener(){
                postBinding.menuButton.isChecked = false
                println(postBinding.menuButton.isChecked)
            }
        }
    }
}
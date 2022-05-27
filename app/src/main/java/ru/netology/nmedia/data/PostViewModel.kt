package ru.netology.nmedia.data

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post

class PostViewModel : ViewModel() {
    private val repository = PostRepo()
    val likeData = repository.posts

    fun shareClicked(post: Post) = repository.sharePlus(post.id)
    fun likesClicked(post: Post) = repository.likesChange(post.id)
}
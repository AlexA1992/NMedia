package ru.netology.nmedia.data

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post

class PostViewModel : ViewModel() {
    private val repository = PostRepo()
    val likeData = repository.post

    fun shareClicked() = repository.sharePlus()
    fun likesClicked() = repository.likesChange()
}
package ru.netology.nmedia.data

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post

class PostViewModel : ViewModel() {
    private val repository = PostRepo()
    val shareData = repository.shareCount
    val likeData = repository.post
//
//    val likesId = likeData.id
//    val likesLiked = likeData.liked
//    val likesLikedByMe = likeData.likedbyMe

    fun shareClicked() = repository.sharePlus()
    fun likesClicked() = repository.likesChange()
}
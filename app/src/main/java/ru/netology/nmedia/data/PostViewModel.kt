package ru.netology.nmedia.data

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.*
import ru.netology.nmedia.data.SharedPostRepo.Companion.allPosts

class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository = PostRepo()
    private val repository = SharedPostRepo(application)
    val likeData = repository.posts

    //fun playClicked(post: Post) = repository.playPost(post.video)
    fun shareClicked(post: Post) {
        repository.sharePlus(post.id)
        shareActionNeeded.value = post.content
    }

    fun likesClicked(post: Post) = repository.likesChange(post)
    fun onDeleteClicked(post: Post) = repository.delete(post.id)

    fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    val shareActionNeeded = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>()
    val date = getCurrentDateTime()
    val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

    fun onSaveButtonClicked(newPostContent: String) {
        if (newPostContent.isBlank()) return
        val nextId = allPosts?.size?.plus(1)!!
        println("nextId $nextId")
        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?: Post(
            id = nextId,
            author = "Нетология - школа ...",
            content = "$newPostContent $nextId",
            date = dateInString,
            liked = 0,
            likedbyMe = false,
            repostsQ = 0,
            edited = false,
            video = null
        )
        println("post $post")
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelButtonClicked() {
        currentPost.value = null
    }
}
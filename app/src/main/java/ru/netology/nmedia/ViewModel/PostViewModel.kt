package ru.netology.nmedia.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.*
import ru.netology.nmedia.Repos.Repository
import ru.netology.nmedia.Repos.RoomRepo


import ru.netology.nmedia.DB.AppDb
import ru.netology.nmedia.Activitys.SingleLiveEvent
import ru.netology.nmedia.data.Post


class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository = SharedPostRepo(application)
    //private val repository = SharedPostRepoFile(application)
    private val repository: Repository =
        RoomRepo(
            dao = AppDb.getInstance(
                context = application
            ).postDao
        )

    val data = repository.data

    val postToTransfer = SingleLiveEvent<Post>()
    fun postClicked(post: Post) {
        println("in postClicked")
        postToTransfer.value = post
    }

    //val shareActionNeeded = SingleLiveEvent<String>()
    fun shareClicked(post: Post) {
        repository.sharePlus(post.id)
        //shareActionNeeded.value = post.content
    }

    fun likesClicked(post: Post) = repository.likesChange(post)

    fun onDeleteClicked(post: Post) = repository.delete(post)

    val currentPost = MutableLiveData<Post?>()
    fun onEditClicked(post: Post) {
        println("in OnEditClicked")
        currentPost.value = post
    }

    val date = getCurrentDateTime()
    val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

    fun onSaveButtonClicked(newPostContent: String) {
        if (newPostContent.isBlank()) return
        val post = currentPost.value?.copy(
            content = newPostContent,
            date = dateInString,
            edited = true
        )?:
        Post(
            author = "Нетология - школа ...",
            content = newPostContent,
            date = dateInString,
            liked = 0,
            likedByMe = false,
            repostsQ = 0,
            edited = false,
        )

        println("in ViewModel post $post")
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelButtonClicked() {
        currentPost.value = null
    }
}


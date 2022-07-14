package ru.netology.nmedia.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.*
import ru.netology.nmedia.Activitys.SingleLiveEvent
import ru.netology.nmedia.Repos.PostRepo
import ru.netology.nmedia.data.Post


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PostRepo()
    //private val repository = SharedPostRepo(application)
    //private val repository = SharedPostRepoFile(application)
    val data = repository.posts

    var postToTransfer = MutableLiveData<Post?>()
    fun postClicked(post: Post) {
        postToTransfer.value = post
    }

    //fun playClicked(post: Post) = repository.playPost(post.video)
    fun shareClicked(post: Post) {
        repository.sharePlus(post.id)
        shareActionNeeded.value = post.content
    }

    fun likesClicked(post: Post) = repository.likesChange(post)

    fun onDeleteClicked(post: Post) = repository.delete(post.id)

    val currentPost = MutableLiveData<Post?>()
    fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    val shareActionNeeded = SingleLiveEvent<String>()

    val date = getCurrentDateTime()
    val dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

    fun onSaveButtonClicked(newPostContent: String) {
        if (newPostContent.isBlank()) return
        val nextId = PostRepo.allPosts.orEmpty().size + 1
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
//
//    fun onCancelButtonClicked() {
//        currentPost.value = null
//    }
}
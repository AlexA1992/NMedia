package ru.netology.nmedia.data

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.awaitAll
import ru.netology.nmedia.*
import kotlin.math.max


class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository = PostRepo()
    //private val repository = SharedPostRepo(application)
    private val repository = SharedPostRepoFile(application)
    val data = repository.posts

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

        var maxId = repository.allPosts?.maxOfOrNull {
            it.id
        }
        if(maxId == null) maxId = 0
        val nextId = maxId.plus(1)
        println("nextId $nextId")

        val post = currentPost.value?.copy(
            content = newPostContent
        ) ?: nextId.let {
            nextId?.let { it1 ->
                Post(
                    id = it1,
                    author = "Нетология - школа ...",
                    content = "$newPostContent $nextId",
                    date = dateInString,
                    liked = 0,
                    likedbyMe = false,
                    repostsQ = 0,
                    edited = false,
                    video = null
                )
            }
        }
        //println("post $post")
        println()
        if (post != null) {
            repository.save(post)
        }
        currentPost.value = null
    }
//
//    fun onCancelButtonClicked() {
//        currentPost.value = null
//    }
}
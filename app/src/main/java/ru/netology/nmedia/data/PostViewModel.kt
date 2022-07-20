package ru.netology.nmedia.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.*
import ru.netology.nmedia.DB.AppDb


class PostViewModel(application: Application) : AndroidViewModel(application) {
    //private val repository = PostRepo()
    //private val repository = SharedPostRepo(application)
    //private val repository = SharedPostRepoFile(application)
    private val repository: Repository =
        RoomRepo(
            dao = AppDb.getInstance(
                context = application
            ).postDao
        )

    val data = repository.data

    //fun playClicked(post: Post) = repository.playPost(post.video)
    fun shareClicked(post: Post) {
        repository.sharePlus(post.id)
        shareActionNeeded.value = post.content
    }

    fun likesClicked(post: Post) = repository.likesChange(post)
    fun onDeleteClicked(post: Post) = repository.delete(post)

    fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    val shareActionNeeded = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>()
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



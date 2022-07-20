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
        SQLRepo(
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
//
//        var maxId = repository.posts.maxOfOrNull {
//            it.id
//        }
//        if (maxId == null) maxId = 0
//        val nextId = maxId.plus(1)
//        println("nextId $nextId")

        val post =
            Post(
                content = newPostContent,
                date = dateInString,
            )
        //println("post $post")
        println("in ViewModel post $post")
        repository.save(post)
        currentPost.value = null
    }

    fun onCancelButtonClicked() {
        currentPost.value = null
    }
}



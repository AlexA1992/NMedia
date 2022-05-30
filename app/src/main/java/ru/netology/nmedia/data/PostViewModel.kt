package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.getCurrentDateTime
import ru.netology.nmedia.toString

class PostViewModel : ViewModel() {
    private val repository = PostRepo()
    val likeData = repository.posts

    fun shareClicked(post: Post) = repository.sharePlus(post.id)
    fun likesClicked(post: Post) = repository.likesChange(post.id)
    fun onDeleteClicked(post: Post) = repository.delete(post.id)
    fun onEditClicked(post: Post) {
        currentPost.value = post
        repository.save(post)
    }

    val currentPost = MutableLiveData<Post?>(null)
    val date = getCurrentDateTime()
    val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

    fun onSaveButtonClicked(newPostContent: String) {
        if (newPostContent.isBlank()) return
        val nextId = PostRepo.allPosts?.size?.plus(1)!!
        val Post = currentPost.value?.copy(
           content = newPostContent
        )?:
        Post(
            id = nextId,
            author = "Нетология - школа ...",
            content = "$newPostContent $nextId",
            date = dateInString,
            liked = 0,
            likedbyMe = false,
            repostsQ = 0,
            edited = false
        )
        repository.save(Post)
        currentPost.value = null
    }
}
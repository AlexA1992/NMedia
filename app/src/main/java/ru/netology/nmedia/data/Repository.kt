package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

interface Repository {
    val posts: LiveData<List<Post>>

    fun sharePlus(postId: Int)
    fun likesChange(postId: Int)
    fun delete(postId: Int)
    fun save(post: Post)

    companion object{
        const val NEW_POSTID = 0
    }
}
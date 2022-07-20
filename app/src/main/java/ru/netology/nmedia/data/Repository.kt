package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

interface Repository {
    val data: LiveData<List<Post>>

    fun sharePlus(postId: Int)
    fun likesChange(post: Post)
    fun delete(post: Post)
    fun save(post: Post)
    //fun playPost(postVideo: String?)

    companion object{
        const val NEW_POSTID = 0
    }
}
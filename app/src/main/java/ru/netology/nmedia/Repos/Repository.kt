package ru.netology.nmedia.Repos

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.Post

interface Repository {
    val posts: LiveData<List<Post>>

    fun sharePlus(postId: Int)
    fun likesChange(post: Post)
    fun delete(postId: Int)
    fun save(post: Post)
    //fun playPost(postVideo: String?)

    companion object{
        const val NEW_POSTID = 0
    }
}
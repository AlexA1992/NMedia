package ru.netology.nmedia.Repos

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.Post

interface Repository {
    val data: LiveData<List<Post>>

    fun sharePlus(postId: Int)
    fun likesChange(post: Post)
    fun delete(post: Post)
    fun save(post: Post)

}
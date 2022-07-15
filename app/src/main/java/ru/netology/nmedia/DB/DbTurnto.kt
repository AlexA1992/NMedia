package ru.netology.nmedia.DB

import ru.netology.nmedia.Post

interface DbTurnto {
    fun getAll():List<Post>
    fun save(post: Post):Post
    fun likedByMe(id: Int)
    fun removeById(it: Int)

}
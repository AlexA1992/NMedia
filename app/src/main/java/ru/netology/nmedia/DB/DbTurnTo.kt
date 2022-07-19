package ru.netology.nmedia.DB

import ru.netology.nmedia.Post

interface DbTurnTo {
    fun getAll():List<Post>
    fun save(post: Post):Post
    fun likedByMe(id: Int)
    fun removeById(id: Int)
    fun sharePlus(id: Int)
}
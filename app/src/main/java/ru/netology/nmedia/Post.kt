package ru.netology.nmedia

data class Post (
    var id: Int,
    var liked: Int,
    var likedbyMe: Boolean,
    val content: String,
    var date: String,
    val author: String,
    var repostsQ: Int,
    var edited: Boolean,
    val video: String?
)
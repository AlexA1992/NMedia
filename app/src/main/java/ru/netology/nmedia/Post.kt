package ru.netology.nmedia

data class Post (
    val id: Int,
    var liked: Int,
    var likedbyMe: Boolean,
    val content: String,
    val date: String,
    val author: String,
    var repostsQ: Int
)
package ru.netology.nmedia

data class Post (
    val id: Int,
    var liked: Int,
    var likedbyMe: Boolean
)
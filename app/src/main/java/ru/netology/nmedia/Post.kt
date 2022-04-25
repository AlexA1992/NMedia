package ru.netology.nmedia

data class Post (
    val id: Int,
    val liked: Int,
    var likedbyMe: Boolean
)
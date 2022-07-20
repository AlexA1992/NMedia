package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    var id: Int = 0,
    var liked: Int = 0,
    var likedByMe: Boolean = false,
    val content: String,
    var date: String,
    val author: String = "Netologia",
    var repostsQ: Int = 0,
    var edited: Boolean = false,
    val video: String? = ""
)
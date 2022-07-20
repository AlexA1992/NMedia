package ru.netology.nmedia.DB

import android.database.Cursor
import ru.netology.nmedia.data.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    liked = liked,
    likedByMe = likedByMe,
    content = content,
    date = date,
    author = author,
    repostsQ = repostsQ,
    edited = edited,
    video = video,
)

internal fun Post.toEntity() = video?.let {
    PostEntity(
        id = id,
        liked = liked,
        likedByMe = likedByMe,
        content = content,
        date = date,
        author = author,
        repostsQ = repostsQ,
        edited = edited,
        video = it,
    )
}
package ru.netology.nmedia.DB

import android.database.Cursor
import ru.netology.nmedia.Post

fun Cursor.toPosts() = Post(
    id = getInt(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
    liked = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKED.columnName)),
    likedbyMe = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKEDBYME.columnName)) != 0,
    content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
    date = getString(getColumnIndexOrThrow(PostsTable.Column.DATE.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
    repostsQ = getInt(getColumnIndexOrThrow(PostsTable.Column.REPOSTSQ.columnName)),
    edited = getInt(getColumnIndexOrThrow(PostsTable.Column.EDITED.columnName)) != 0,
    video = getString(getColumnIndexOrThrow(PostsTable.Column.VIDEO.columnName)),
)
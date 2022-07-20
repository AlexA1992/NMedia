package ru.netology.nmedia.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false,
    @ColumnInfo(name = "liked")
    val liked: Int = 0,
    @ColumnInfo(name = "repostsQ")
    val repostsQ: Int = 0,
    @ColumnInfo(name = "video")
    val video: String = "",
    @ColumnInfo(name = "edited")
    val edited: Boolean = false,
)
//{
//    fun toDto() = Post(id, author, content, published, likedByMe, likes)
//
//    companion object {
//        fun fromDto(dto: Post) =
//            PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likedByMe, dto.likes)
//
//    }
//}
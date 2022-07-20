package ru.netology.nmedia.DB

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.DB.PostEntity

@Dao
interface DbTurnTo {
    @Query("SELECT * FROM posts ORDER BY id Desc")
    fun getAll():LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content, edited = 1 WHERE id = :id")
    fun update(id: Int, content: String)

//    fun save(post: PostEntity) =
//        if (post.id == 0) insert(post) else update(post.id, post.content)

    @Query("""
        UPDATE posts SET
        liked = liked + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id =  :id        
    """)
    fun likedByMe(id: Int)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Int)

    @Query("UPDATE posts SET repostsQ = repostsQ + 1 WHERE id = :id")
    fun sharePlus(id: Int)
}
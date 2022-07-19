package ru.netology.nmedia.DB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.DB.DbTurnTo
import android.database.Cursor
import ru.netology.nmedia.Post

class DBTurnToImpl(
    private val db: SQLiteDatabase
) : DbTurnTo {

    override fun getAll(): List<Post> = db.query(
        PostsTable.NAME,
        PostsTable.ALL_COLUMNS_NAMES,
        null, null, null, null,
        "${PostsTable.Column.ID.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toPosts()
        }
    }

    override fun save(post: Post): Post {
        println("post in DAO $post")
        val values = ContentValues().apply {
            put(PostsTable.Column.LIKED.columnName, post.liked)
            put(PostsTable.Column.LIKEDBYME.columnName, post.likedbyMe)
            put(PostsTable.Column.REPOSTSQ.columnName, post.repostsQ)
            put(PostsTable.Column.VIDEO.columnName, post.video)
            put(PostsTable.Column.AUTHOR.columnName, post.author)
            put(PostsTable.Column.CONTENT.columnName, post.content)
            put(PostsTable.Column.DATE.columnName, post.date)
            put(PostsTable.Column.EDITED.columnName, post.edited)
        }
        val id = if (post.id != 0) {
            db.update(
                PostsTable.NAME,
                values,
                "${PostsTable.Column.ID.columnName} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostsTable.NAME, null, values)
        }
        return db.query(
            PostsTable.NAME,
            PostsTable.ALL_COLUMNS_NAMES,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPosts()
        }
    }

    override fun likedByMe(id: Int) {
        db.execSQL(
            """
                UPDATE ${PostsTable.NAME} SET
                liked = liked + CASE WHEN likedByMe THEN -1 else 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
               WHERE id = ?;
               """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Int) {
        db.delete(
            PostsTable.NAME,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun sharePlus(id: Int) {
        db.execSQL(
            """
                UPDATE ${PostsTable.NAME} SET
                repostsq = repostsq + 1
               WHERE id = ?;
               """.trimIndent(),
            arrayOf(id)
        )
    }
}
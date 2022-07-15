package ru.netology.nmedia.DB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post
import java.sql.SQLData

class DBTurntoImpl(
    private val db: SQLiteDatabase
) : DbTurnto {

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
        val values = ContentValues().apply {
            put(PostsTable.Column.AUTHOR.columnName, post.author)
            put(PostsTable.Column.CONTENT.columnName, post.content)
            put(PostsTable.Column.DATE.columnName, post.date)
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
                likes = likes + CASE WHEN likedByMe THEN -1 else 1 END,
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


    }
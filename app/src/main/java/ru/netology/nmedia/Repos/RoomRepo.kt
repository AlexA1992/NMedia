package ru.netology.nmedia.Repos

import androidx.lifecycle.map
import ru.netology.nmedia.DB.DbTurnTo
import ru.netology.nmedia.DB.toEntity
import ru.netology.nmedia.DB.toModel
import ru.netology.nmedia.data.Post

class RoomRepo(
    private val dao: DbTurnTo
) : Repository {
    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun sharePlus(postId: Int) {
        dao.sharePlus(postId)
        //println(posts.value)
    }

    override fun likesChange(post: Post) {
        dao.likedByMe(post.id)
    }

    override fun delete(post: Post) {
        dao.removeById(post.id)
    }

    override fun save(post: Post) {
        println("post.id ${post.id}")
        if (post.id == 0) post.toEntity()?.let {
            dao.insert(it) } else post.toEntity()
            ?.let { dao.update(post.toEntity()!!.id, it.content) }
    }
}
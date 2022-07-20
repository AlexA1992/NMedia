package ru.netology.nmedia.data

import androidx.lifecycle.map
import ru.netology.nmedia.DB.DbTurnTo
import ru.netology.nmedia.DB.toEntity
import ru.netology.nmedia.DB.toModel
import ru.netology.nmedia.Post

class SQLRepo(
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
        if (post.id == 0) post.toEntity()?.let { dao.insert(it) } else post.toEntity()
            ?.let { dao.update(post.toEntity()!!.id, it.content) }
    }
}

//    private fun insert(post: Post) {
//        println("post in insert $post")
//        dao.save(post)
//
//        //println(allPosts?.last()?.id)
////        if (posts.isEmpty() == true) post.id = 1 else
////            post.id = posts.last().id.plus(1)
////        //println(post)
////        val newPostList = posts.reversed()
////        data.value = newPostList.plus(post).reversed()
//        data.value = dao.getAll()
//        println("allPosts $posts")
//    }
//
//    private fun update(post: Post) {
//        val date = getCurrentDateTime()
//        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
//        posts.map {
//            if (it.id == post.id) {
//                post.edited = true
//                post.date = dateInString
//                post
//            } else it
//        }
//        data.value = posts
//    }

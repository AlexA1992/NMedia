package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.DB.DbTurnTo
import ru.netology.nmedia.Post
import ru.netology.nmedia.getCurrentDateTime
import ru.netology.nmedia.toString

class SQLRepo(
    private val dao: DbTurnTo
): Repository {
    val posts
        get() = checkNotNull(data.value){
        "Data value is Null"
    }
    val data = MutableLiveData(dao.getAll())

    fun getAll(): LiveData<List<Post>> = data

    override fun sharePlus(postId: Int) {
        //println(allPosts)
//        posts.map {
//            //println(it.repostsQ)
//            if (it.id == postId) {
//                it.repostsQ += 1
//                //println(it.repostsQ)
//            }
//        }
        //println(allPosts)
        dao.sharePlus(postId)
        data.value = dao.getAll()
        //println(posts.value)
    }

    override fun likesChange(post: Post) {
        //println(allPosts)
//        posts.first {
//            it.id == post.id
//        }
//            .apply {
//                this?.likedbyMe = !this?.likedbyMe!!
//                if (this.likedbyMe == true) {
//                    val newLiked = this.copy(liked = this.liked + 1)
//                    this.liked = newLiked.liked
//                } else {
//                    val newLiked = this.copy(liked = this.liked - 1)
//                    this.liked = newLiked.liked
//                }
//            }
        //println(allPosts)
        dao.likedByMe(post.id)
        data.value = dao.getAll()
    }

    override fun delete(post: Post) {
        dao.removeById(post.id)
        data.value = dao.getAll()
//        data.value = posts.filter {
//            it.id != postId
//        }
//        posts.onEach {
//            if (it.id == postId) {
//                posts.toMutableList().remove(it)
//            }
//        }
        data.value = posts
        //println(allPosts)
    }

    override fun save(post: Post) {
        println("post.id ${post.id}")
        dao.save(post)
        data.value = dao.getAll()
//        println(posts)
//        val found = posts.any {
//            it.id == post.id
//        }
//
//        if(found == true) update(post) else insert(post)
    }

    private fun insert(post: Post) {
        println("post in insert $post")
        dao.save(post)

        //println(allPosts?.last()?.id)
//        if (posts.isEmpty() == true) post.id = 1 else
//            post.id = posts.last().id.plus(1)
//        //println(post)
//        val newPostList = posts.reversed()
//        data.value = newPostList.plus(post).reversed()
        data.value = dao.getAll()
        println("allPosts $posts")
    }

    private fun update(post: Post) {
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        posts.map {
            if (it.id == post.id) {
                post.edited = true
                post.date = dateInString
                post
            } else it
        }
        data.value = posts
    }
}
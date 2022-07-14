package ru.netology.nmedia.Repos

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.getCurrentDateTime
import ru.netology.nmedia.toString

class SharedPostRepoFile(
    val application: Application
) : Repository {
    val gson = Gson()
    val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    var allPosts: List<Post>?
        get() = checkNotNull(posts.value) {
            "Data is null"
        }
        set(value) {
            application.openFileOutput(POST_FILE_NAME, Context.MODE_PRIVATE)
                .bufferedWriter().use {
                    it.write(gson.toJson(value))
                }
            posts.value = value!!
        }

//    private val prefer = application.getSharedPreferences(
//        "name", Context.MODE_PRIVATE
//    )

//    val date = getCurrentDateTime()
//    private var dateInString = date.toString("dd/MM/yyyy HH:mm:ss")

    override val posts: MutableLiveData<List<Post>>
    //=
//        MutableLiveData(
//        listOf<Post>(
//            Post(
//                1,
//                0,
//                false,
//                "Типа какой-то контент.... ",
//                dateInString,
//                "Нетология - школа ...",
//                0,
//                false,
//                "https://www.google.com"
//            ),
//            Post(
//                2,
//                0,
//                false,
//                "Типа какой-то контент.... 2",
//                dateInString,
//                "Нетология - школа ...",
//                0,
//                false,
//                null
//            ),
//            Post(
//                3,
//                0,
//                false,
//                "Типа какой-то контент.... 3",
//                dateInString,
//                "Нетология - школа ...",
//                0,
//                false,
//                "https://www.yandex.ru"
//            )
//        )
//    )

    init {
        val postsFile = application.filesDir.resolve(POST_FILE_NAME)
        val allposts: List<Post> = if (postsFile.exists()) {
            val inpurStream = application.openFileInput(POST_FILE_NAME)
            val reader = inpurStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        posts = MutableLiveData(allposts)
    }


    override fun sharePlus(postId: Int) {
        allPosts?.map {
            if (it.id == postId) {
                val newPost = it.copy(repostsQ = it.repostsQ + 1)
                it.repostsQ = newPost.repostsQ
            }
        }
        posts.value = allPosts
    }

    override fun likesChange(post: Post) {
        allPosts?.first {
            it.id == post.id
        }
            .apply {
                this?.likedbyMe = !this?.likedbyMe!!
                if (this?.likedbyMe == true) {
                    val newLiked = this.copy(liked = this.liked + 1)
                    this.liked = newLiked.liked
                } else {
                    val newLiked = this.copy(liked = this.liked - 1)
                    this.liked = newLiked.liked
                }
            }
        posts.value = allPosts
    }

    override fun delete(postId: Int) {
        posts.value = allPosts?.filter {
            it.id != postId
        }
        allPosts?.onEach {
            if (it.id == postId) {
                allPosts!!.toMutableList().remove(it)
            }
        }
        allPosts = posts.value
        println(allPosts)
    }

    override fun save(post: Post) {
        println("post.id ${post.id}")
        if (post.id == allPosts?.size?.plus(1)) {
            insert(post)
        } else update(post)
    }

    private fun insert(post: Post) {
        println("post in insert $post")
        if (allPosts?.isEmpty() == true) post.id = 1 else
            post.id = allPosts?.last()?.id?.plus(1)!!
        val newPostList = allPosts?.reversed()
        if (newPostList != null) {
            posts.value = newPostList.plus(post).reversed()
        }
        allPosts = posts.value
        println(allPosts)
    }

    private fun update(post: Post) {
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        allPosts?.map {
            if (it.id == post.id) {
                post.edited = true
                post.date = dateInString
                post
            } else it
        }
        println(allPosts)
    }

    companion object {
        const val POSTS_PREF_KEY = "posts"
        const val POST_FILE_NAME = "posts.json"
    }
}

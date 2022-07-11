package ru.netology.nmedia.data

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPostRepo(
    application: Application
) : Repository {
    var allPosts: List<Post>?
        get() = checkNotNull(posts.value)
        set(value) {
            prefer.edit {
                val serializedPosts = Json.encodeToString(value)
                putString(POSTS_PREF_KEY, serializedPosts)
            }
            posts.value = allPosts
        }

    private val prefer = application.getSharedPreferences(
        "name", Context.MODE_PRIVATE
    )

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
        val serializedPosts = prefer.getString(POSTS_PREF_KEY, null)
        val postes = if (serializedPosts != null) {
            Json.decodeFromString<List<Post>>(serializedPosts)
        } else emptyList()
        posts = MutableLiveData(postes)
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
            it.id == post.id}
            .apply {
                this?.likedbyMe = !this?.likedbyMe!!
                if (this?.likedbyMe == true) {
                    val newLiked = this.copy(liked = this.liked + 1)
                    this.liked = newLiked.liked
                } else {
                    val newLiked = this?.copy(liked = this.liked - 1)
                    if (newLiked != null) {
                        this?.liked = newLiked.liked
                    }
                }
            }
        posts.value = allPosts
    }

    override fun delete(postId: Int) {
        posts.value = allPosts?.filter {
            it.id != postId
        }
        allPosts = posts.value
    }

    override fun save(post: Post) {
        println("post.id ${post.id}")
        if (post.id == allPosts?.size?.plus(1)) {
            insert(post)
        } else update(post)
    }

    private fun insert(post: Post) {
        println("post in insert $post")
        post.id = allPosts?.size?.plus(1)!!
        val newPostList = allPosts?.reversed()
        if (newPostList != null) {
            posts.value = newPostList.plus(post).reversed()
        }
        allPosts = posts.value
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
    }

    companion object {
        var allPosts: List<Post>?
            get() = checkNotNull(
                SharedPostRepo(
                    application = Application()
                ).posts.value
            )
            set(value) {
                SharedPostRepo(application = Application()).prefer.edit {
                    val serializedPosts = Json.encodeToString(value)
                    putString(POSTS_PREF_KEY, serializedPosts)
                }
                SharedPostRepo(application = Application()).posts.value = allPosts
            }
        const val POSTS_PREF_KEY = "posts"
    }
}

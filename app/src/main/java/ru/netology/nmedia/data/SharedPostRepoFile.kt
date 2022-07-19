//package ru.netology.nmedia.data
//
//import android.app.Application
//import android.content.Context
//import android.content.Intent
//import android.content.Intent.ACTION_VIEW
//import android.net.Uri
//import android.widget.FrameLayout
//import android.widget.Toast
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat.startActivity
//import androidx.core.content.edit
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.map
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import ru.netology.nmedia.*
//import ru.netology.nmedia.databinding.ActivityMainBinding
//import kotlinx.serialization.decodeFromString
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import java.lang.reflect.Type
//
//class SharedPostRepoFile(
//    val application: Application
//) : Repository {
//    val gson = Gson()
//    val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
//    var allPosts: List<Post>?
//        get() = checkNotNull(posts.value) {
//            "Data is null"
//        }
//        set(value) {
//            println("value $value")
//            application.openFileOutput(POST_FILE_NAME, Context.MODE_PRIVATE)
//                .bufferedWriter().use {
//                    it.write(gson.toJson(value))
//                }
//            println("posts.value ${posts.value}")
//            posts.value = value!!
//        }
//
//    override val posts: MutableLiveData<List<Post>>
////    =
////        MutableLiveData(
////        listOf<Post>(
////            Post(
////                1,
////                0,
////                false,
////                "Типа какой-то контент.... ",
////                dateInString,
////                "Нетология - школа ...",
////                0,
////                false,
////                "https://www.google.com"
////            ),
////            Post(
////                2,
////                0,
////                false,
////                "Типа какой-то контент.... 2",
////                dateInString,
////                "Нетология - школа ...",
////                0,
////                false,
////                null
////            ),
////            Post(
////                3,
////                0,
////                false,
////                "Типа какой-то контент.... 3",
////                dateInString,
////                "Нетология - школа ...",
////                0,
////                false,
////                "https://www.yandex.ru"
////            )
////        )
////    )
//
//    init {
//        println("init")
//        val postsFile = application.filesDir.resolve(POST_FILE_NAME)
//        val allposts: List<Post> = if (postsFile.exists()) {
//            val inpurStream = application.openFileInput(POST_FILE_NAME)
//            val reader = inpurStream.bufferedReader()
//            reader.use { gson.fromJson(it, type) }
//        } else emptyList()
//        posts = MutableLiveData(allposts)
//        println("posts init ${posts.value}")
//    }
//
//
//    override fun sharePlus(postId: Int) {
//        //println(allPosts)
//        allPosts?.map {
//            //println(it.repostsQ)
//            if (it.id == postId) {
//                it.repostsQ += 1
//                //println(it.repostsQ)
//            }
//        }
//        //println(allPosts)
//        allPosts = posts.value
//        //println(posts.value)
//    }
//
//    override fun likesChange(post: Post) {
//        //println(allPosts)
//        allPosts?.first {
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
//        //println(allPosts)
//        allPosts = posts.value
//    }
//
//    override fun delete(postId: Int) {
//        posts.value = allPosts?.filter {
//            it.id != postId
//        }
//        allPosts?.onEach {
//            if (it.id == postId) {
//                allPosts!!.toMutableList().remove(it)
//            }
//        }
//        allPosts = posts.value
//        //println(allPosts)
//    }
//
//    override fun save(post: Post) {
//        println("post.id ${post.id}")
//        println(allPosts)
//        val found = allPosts?.any {
//            it.id == post.id
//            }
//
//        if(found == true) update(post) else insert(post)
//        }
//
//    private fun insert(post: Post) {
//        println("post in insert $post")
//        //println(allPosts?.last()?.id)
//        if (allPosts?.isEmpty() == true) post.id = 1 else
//            post.id = allPosts?.last()?.id?.plus(1)!!
//        //println(post)
//        val newPostList = allPosts?.reversed()
//        if (newPostList != null) {
//            posts.value = newPostList.plus(post).reversed()
//        }
//        allPosts = posts.value
//        println("allPosts $allPosts")
//    }
//
//    private fun update(post: Post) {
//
//        val date = getCurrentDateTime()
//        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
//        allPosts?.map {
//            if (it.id == post.id) {
//                post.edited = true
//                post.date = dateInString
//                post
//            } else it
//        }
//        allPosts = posts.value
//    }
//
//    companion object {
//        const val POSTS_PREF_KEY = "posts"
//        const val POST_FILE_NAME = "posts.json"
//    }
//}

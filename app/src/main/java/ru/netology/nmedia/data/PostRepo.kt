package ru.netology.nmedia.data

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.*
import ru.netology.nmedia.databinding.ActivityMainBinding

class PostRepo() : Repository {
    val date = getCurrentDateTime()
    private var dateInString = date.toString("dd/MM/yyyy HH:mm:ss")
    override val posts: MutableLiveData<List<Post>> = MutableLiveData(
        listOf<Post>(
            Post(
                1,
                0,
                false,
                "Типа какой-то контент.... ",
                dateInString,
                "Нетология - школа ...",
                0,
                false,
                "https://www.google.com"
            ),
            Post(
                2,
                0,
                false,
                "Типа какой-то контент.... 2",
                dateInString,
                "Нетология - школа ...",
                0,
                false,
                null
            ),
            Post(
                3,
                0,
                false,
                "Типа какой-то контент.... 3",
                dateInString,
                "Нетология - школа ...",
                0,
                false,
                "https://www.yandex.ru"
            )))


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
        //println(postId)
        allPosts?.map {
            if (it.id == post.id) {
                //println(it)
                it.likedbyMe = !it.likedbyMe
                if (it.likedbyMe == true) {
                    val newLiked = it.copy(liked = it.liked + 1)
                    it.liked = newLiked.liked
                } else {
                    val newLiked = it.copy(liked = it.liked - 1)
                    it.liked = newLiked.liked
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
        if(post.id == allPosts?.size?.plus(1))  {
            insert(post)
        } else update(post)
    }

    private fun insert(post: Post) {
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
        posts.value = allPosts?.map{
            if(it.id == post.id) {
                post.edited = true
                post.date = dateInString
                post
            } else it
        }
    }

    companion object {
        var allPosts: List<Post>? = PostRepo().posts.value
    }



}

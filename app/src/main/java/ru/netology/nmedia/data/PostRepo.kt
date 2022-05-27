package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.Functions
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding

class PostRepo() : Repository {
    private val allPosts: List<Post>?
        get() = checkNotNull(posts.value)

    override val posts = MutableLiveData(
        List(2) { index ->
            Post(
                index + 1,
                0,
                false,
                "Типа какой-то контент.... $index",
                "19.04.2022",
                "Нетология = школа какая-то там...",
                0
            )
        }
    )


    override fun sharePlus(postId: Int) {
        allPosts?.map {
            if (it.id == postId) {
                val newPost = it.copy(repostsQ = it.repostsQ + 1)
                it.repostsQ = newPost.repostsQ
            }
        }
        posts.value = allPosts
    }

    override fun likesChange(postId: Int) {
        //println(postId)
        allPosts?.map {
            if (it.id == postId) {
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


}

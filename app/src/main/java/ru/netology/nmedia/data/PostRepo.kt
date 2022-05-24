package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Functions
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding

class PostRepo() : Repository {

    override val post = MutableLiveData(
        Post(
            1,
            0,
            false,
            "Типа какой-то контент....",
            "19.04.2022",
            "Нетология = школа какая-то там...",
            0
        )
    )

    override fun sharePlus() {
        val currentPost = requireNotNull(post.value)
        val newPost = currentPost.copy(repostsQ = currentPost.repostsQ + 1)
        post.value = newPost
    }

    override fun likesChange() {
        val currentPost = requireNotNull(post.value)
        println(post.value)
        currentPost.likedbyMe = !currentPost.likedbyMe
        if(currentPost.likedbyMe == true) {
            post.value = currentPost.copy(liked = currentPost.liked + 1)
        } else{
            post.value = currentPost.copy(liked = currentPost.liked - 1)
        }
    }
}

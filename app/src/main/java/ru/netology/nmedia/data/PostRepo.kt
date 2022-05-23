package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Functions
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding

class PostRepo() : Repository {

    override val post = MutableLiveData(Post(1,
        0,
        false,
        "Типа какой-то контент....",
        "19.04.2022",
        "Alex",
        0))

    override fun sharePlus() {
        val currentVal = post.value
        val postsReposts = currentVal?.repostsQ?.plus(1)
        if (postsReposts != null) {
            currentVal.repostsQ = postsReposts
        }
        post.value = currentVal
    }

    override fun likesChange() {
        val currentPost = post.value
        if (currentPost != null) {
            currentPost.likedbyMe = !currentPost.likedbyMe
        }
        post.value = currentPost
    }
}

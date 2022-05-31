package ru.netology.nmedia.data

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.getCurrentDateTime
import ru.netology.nmedia.toString
class PostRepo() : Repository {

    val date = getCurrentDateTime()
    var dateInString = date.toString("dd/MM/yyyy HH:mm:ss")
    override val posts = MutableLiveData(

        List(10) { index ->
            Post(
                index + 1,
                0,
                false,
                "Типа какой-то контент.... $index",
                dateInString,
                "Нетология - школа ...",
                0,
                false
            )
        }
    )


    override fun sharePlus(postId: Int) {
        A.allPosts?.map {
            if (it.id == postId) {
                val newPost = it.copy(repostsQ = it.repostsQ + 1)
                it.repostsQ = newPost.repostsQ
            }
        }
        posts.value = A.allPosts
    }

    override fun likesChange(postId: Int) {
        //println(postId)
        A.allPosts?.map {
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
        posts.value = A.allPosts
    }

    override fun delete(postId: Int) {
        posts.value = A.allPosts?.filter {
            it.id != postId
        }
        A.allPosts = posts.value
    }

    override fun save(post: Post) {
        println("post.id ${post.id}")
        println("PostRepo().allPosts?.size ${allPosts?.size?.plus(1)}")
        if(post.id == A.allPosts?.size?.plus(1))  {
//            println("post.id ${post.id}")
//            println("PostRepo().allPosts?.size ${PostRepo().allPosts?.size?.plus(1)}")

            insert(post)
        } else update(post)
    }



    //private val nextId = POSTS_NOW
    private fun insert(post: Post) {
        post.id = A.allPosts?.size?.plus(1)!!
        println("old allPosts $allPosts")
        val newPostList = A.allPosts?.reversed()
        if (newPostList != null) {
            println("old posts.value ${posts.value}")
            posts.value = newPostList.plus(post).reversed()
            println("new posts.value ${posts.value}")
        }
        A.allPosts = posts.value
        println("new allPosts $allPosts")
    }

    private fun update(post: Post) {
        val date = getCurrentDateTime()
        val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
        posts.value = A.allPosts?.map{
            if(it.id == post.id) {
                post.edited = true
                post.date = dateInString
                post
            } else it
        }
    }

    companion object A{
        var allPosts: List<Post>? = PostRepo().posts.value
            //get() = checkNotNull(PostRepo().posts.value)
    }
}

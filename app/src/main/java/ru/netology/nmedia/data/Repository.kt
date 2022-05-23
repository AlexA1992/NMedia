package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

interface Repository {
    val post: LiveData<Post>

    fun sharePlus()
    fun likesChange()
}
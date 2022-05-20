package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Functions
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding

class PostRepo() : Repository {
    override val shareCount = MutableLiveData(0)
    override val post = MutableLiveData(Post(1, 0, false, "Перспективное планирование предполагает независимые способы реализации экспериментов, поражающих по своей масштабности и грандиозности. Повседневная практика показывает, что высокотехнологичная концепция общественного уклада однозначно определяет каждого участника как способного принимать собственные решения касаемо благоприятных перспектив. Кстати, действия представителей оппозиции неоднозначны и будут описаны максимально подробно!", "19.04.2022", "Alex", 0))

    override fun sharePlus() {
        val currentVal: Int? = shareCount.value
        shareCount.value = currentVal?.plus(1)
    }

    override fun likesChange() {
        val currentPost = post.value
        if (currentPost != null) {
            currentPost.likedbyMe = !currentPost.likedbyMe
        }
        post.value = currentPost
    }
}

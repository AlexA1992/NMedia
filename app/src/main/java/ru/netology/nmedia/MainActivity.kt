package ru.netology.nmedia

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.data.PostViewModel
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<PostViewModel>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //обработка лайков
        viewModel.likeData.observe(this) {
            val newLikedByMe = it.likedbyMe
            if (newLikedByMe == false) {
                binding.likes.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                binding.likescount.setText((it.liked).toString())
                binding.content.setText(it.content)
            } else {
                binding.likes.setImageResource(R.drawable.liked24)
                binding.likescount.setText((it.liked + 1).toString())
            }
            binding.sharescount.setText(it.repostsQ.toString())
        }

        binding.likes.setOnClickListener {
            viewModel.likesClicked()
        }

        binding.shares.setOnClickListener {
            viewModel.shareClicked()
        }
    }
}



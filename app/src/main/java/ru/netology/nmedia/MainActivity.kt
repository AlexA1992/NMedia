package ru.netology.nmedia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.data.PostViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import ru.netology.nmedia.data.PostAdapter
import ru.netology.nmedia.data.hideKeyboard
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter1 = PostAdapter(
            viewModel::likesClicked,
            viewModel::shareClicked,
            viewModel::onDeleteClicked,
            viewModel::onEditClicked,
        )
        binding.postsRecyclerView.adapter = adapter1
        viewModel.likeData.observe(this) { posts ->
            adapter1.posts = posts
        }

//        binding.save.setOnClickListener {
//            val theContent = binding.newPost.text.toString()
//            viewModel.onSaveButtonClicked(theContent)
//            Toast.makeText(layoutInflater.context, "Done", Toast.LENGTH_SHORT).show()
//            binding.save.clearFocus()
//            binding.save.hideKeyboard()
//        }

        binding.cancel.setOnClickListener {
            viewModel.onCancelButtonClicked()
            Toast.makeText(layoutInflater.context, "O-o-u-p-s", Toast.LENGTH_SHORT).show()
            binding.save.clearFocus()
            binding.save.hideKeyboard()
        }

//        viewModel.currentPost.observe(this) { currentPost ->
//            if (currentPost != null) {
//                binding.newPost.setText(currentPost.content.toString())
//            } else {
//                binding.newPost.setText("")
//            }
//        }

        viewModel.currentPost.observe(this) { currentPost ->
//            val newContent = currentPost?.content
//            println(newContent) // в этой строке показывается правильное значение - отредактированное
            //binding.newPost.setText(newContent.toString()) // не работает
            if (currentPost != null) {
                editLauncher.launch(currentPost.content)
            }
        }
    }

    val editLauncher = registerForActivityResult(EditPostActivity.ResultContract) {
        val content = it ?: return@registerForActivityResult
        viewModel.onSaveButtonClicked(content)
    }
}





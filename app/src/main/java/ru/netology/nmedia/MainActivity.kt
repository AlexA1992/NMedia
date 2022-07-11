package ru.netology.nmedia

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.data.PostViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
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

        binding.create.setOnClickListener {
            //val theContent = binding.newPost.text.toString()
            createLauncher.launch()
        }

//        binding.cancel.setOnClickListener {
//            viewModel.onCancelButtonClicked()
//            Toast.makeText(layoutInflater.context, "O-o-u-p-s", Toast.LENGTH_SHORT).show()
//            binding.save.clearFocus()
//            binding.save.hideKeyboard()
//        }

        viewModel.shareActionNeeded.observe(this) { content ->
            val intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, content)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(
                intent, "Выберите приложение"
            )
            startActivity(shareIntent)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            if (currentPost != null) {
                editLauncher.launch(currentPost.content)
            }
        }
    }

    val editLauncher = registerForActivityResult(EditPostActivity.ResultContract) {
        val content = it ?: return@registerForActivityResult
        viewModel.onSaveButtonClicked(content)
    }

    val createLauncher = registerForActivityResult(CreatePostActivity.ResultCreateContract) {
        val content = it ?: return@registerForActivityResult
        viewModel.onSaveButtonClicked(content)
        Toast.makeText(layoutInflater.context, "Done", Toast.LENGTH_SHORT).show()
    }
}





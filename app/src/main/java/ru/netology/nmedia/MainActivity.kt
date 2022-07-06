package ru.netology.nmedia

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.data.PostViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import ru.netology.nmedia.data.PostAdapter
import ru.netology.nmedia.data.hideKeyboard
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()
    private val binding = ActivityMainBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.save.setOnClickListener {
            val theContent = binding.newPost.text.toString()
            viewModel.onSaveButtonClicked(theContent)
            Toast.makeText(layoutInflater.context, "Done", Toast.LENGTH_SHORT).show()
            binding.save.clearFocus()
            binding.save.hideKeyboard()
        }

        binding.cancel.setOnClickListener {
            viewModel.onCancelButtonClicked()
            Toast.makeText(layoutInflater.context, "O-o-u-p-s", Toast.LENGTH_SHORT).show()
            binding.save.clearFocus()
            binding.save.hideKeyboard()
        }

        viewModel.currentPost.observe(this) { currentPost ->
            binding.newPost.setText(currentPost?.content.toString())
        }
    }

//    companion object{
//        private lateinit var context: Context
//        fun getNewContent(post: Post){
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                type = "plain/text"
//                putExtra(Intent.EXTRA_TEXT, post.content)
//            }
//            startActivity(context, intent, null)
//
//            val activityLauncher = registerForActivityResult{
//
//            }
//        }
//    }

}



package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityCreatePostBinding
import ru.netology.nmedia.databinding.ActivityEditPostBinding.inflate
import ru.netology.nmedia.databinding.PostBinding.inflate

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editField = binding.newPost

        binding.saveCreated.setOnClickListener {
            val newText = editField.text.toString()
            println(newText)
            if (newText.isBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val newIntent = Intent()
                newIntent.putExtra("newPost", newText)
                setResult(Activity.RESULT_OK, newIntent)
            }
            finish()
        }
    }

    object ResultCreateContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit) =
            Intent(context, CreatePostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            var theReturn: String = ""
            if (resultCode == Activity.RESULT_OK) {
                theReturn = intent?.getStringExtra("newPost").toString()
            }
            return theReturn
        }
    }
}

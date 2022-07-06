package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        if (intent.action != Intent.ACTION_SEND) return

        val editField = binding.newText
        val theText = intent.getStringExtra(Intent.EXTRA_TEXT)

        if (theText.isNullOrBlank()) return else editField.setText(theText)
        val newText = editField.text
        binding.saveButton.setOnClickListener {
            println(newText)
            if(newText.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val newIntent = Intent()
                intent.putExtra("content", newText)
                setResult(Activity.RESULT_OK, newIntent)
            }
        }
    }

    object ResultContract : ActivityResultContract<String, String?>() {
        override fun createIntent(context: Context, input: String) =
            Intent(context, EditPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != RESULT_OK) return null
            intent ?: return null
            return intent.getStringExtra("content")
        }
    }
}
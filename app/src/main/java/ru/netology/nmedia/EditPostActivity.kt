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

        val editField = binding.newText
        val theText = intent.getStringExtra(Intent.EXTRA_TEXT)

        if (theText.isNullOrBlank()) return else editField.setText(theText)

        binding.saveButton.setOnClickListener {
            val newText = editField.text.toString()
            if (newText.isBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val newIntent = Intent()
                newIntent.putExtra("content", newText)
                setResult(Activity.RESULT_OK, newIntent)
            }
            finish()
        }
    }

//    object ResultContract : ActivityResultContract<String, String?>() {
//        override fun createIntent(context: Context, input: String) =
//            Intent(context, this::class.java)
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String? =
//            if (resultCode == Activity.RESULT_OK) {
//                println("RESULT_OK")
//                intent?.getStringExtra(Intent.EXTRA_TEXT)
//            } else {
//                println("No Intent")
//                null
//            }
//    }

    object ResultContract : ActivityResultContract<String, String?>() {
        override fun createIntent(context: Context, input: String) =
            Intent(context, EditPostActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            var theReturn: String = ""
            if (resultCode == Activity.RESULT_OK) {
                theReturn = intent?.getStringExtra("content").toString()
            }
            return theReturn
        }




    }
}

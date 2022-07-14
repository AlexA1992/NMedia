package ru.netology.nmedia.Activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.netology.nmedia.Activitys.CreatePostFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        intent.let {
            val theText = intent.getStringExtra(Intent.EXTRA_TEXT)
//            if (theText.isNullOrBlank()) return else editField.setText(theText)
//
//            binding.saveButton.setOnClickListener {
//                val newText = editField.text.toString()
//                if (newText.isBlank()) {
//                    setResult(Activity.RESULT_CANCELED, intent)
//                } else {
//                    val newIntent = Intent()
//                    newIntent.putExtra("content", newText)
//                    setResult(Activity.RESULT_OK, newIntent)
//                }
//                finish()
//            }
            val fragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
            fragment.navController.navigate(
                R.id.action_feedFragment_to_createPostFragment,
                Bundle().apply {
                    textArg = theText
                })
        }
//    object ResultContract : ActivityResultContract<String, String?>() {
//        override fun createIntent(context: Context, input: String) =
//            Intent(context, AppActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String {
//            var theReturn: String = ""
//            if (resultCode == Activity.RESULT_OK) {
//                theReturn = intent?.getStringExtra("content").toString()
//            }
//            return theReturn
//        }
//    }
    }
}

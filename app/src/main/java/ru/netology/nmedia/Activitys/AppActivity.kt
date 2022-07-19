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
            if (theText.isNullOrBlank()) return

            val fragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment
            fragment.navController.navigate(
                R.id.action_feedFragment_to_createPostFragment,
                Bundle().apply {
                    textArg = theText
                })
        }
    }
}

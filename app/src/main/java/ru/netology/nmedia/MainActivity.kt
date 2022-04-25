package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post1 = Post(1, 0, false)
        //обработка лайков
        var likescount = binding.likescount
        var value = likescount.getText().toString().toInt()
        var count = 0
        println("value $value")
        binding.likes.setOnClickListener {
            post1.likedbyMe = !post1.likedbyMe
            val imageResId =
                if (post1.likedbyMe) R.drawable.liked24 else R.drawable.ic_baseline_favorite_border_24
            binding.likes.setImageResource(imageResId)

            //поставить лайк
            fun add(value: Int): Int {
                var newval = 0
                try {
                    newval = value + 1
                    val newString = Functions().setString(newval)
                    likescount.setText(newString)
                } catch (nfe: NumberFormatException) {
                    println("Could not parse $newval")
                }
                return newval
            }

            //снять лайк
            fun reduce(value: Int) {
                try {
                    val newval = value - 1
                    println("newval $newval")
                    val newString = Functions().setString(newval)
                    likescount.setText(newString)
                } catch (nfe: NumberFormatException) {
                    println("Could not parse $value")
                }
            }

            if (post1.likedbyMe) {
                println("Initial value $value")
                val theval = add(value)
                println("Added value $theval")
                Functions.thisValue = theval
            }

            if (!post1.likedbyMe) {
                val thisValue = Functions.thisValue
                println("Newvalue $thisValue")
                reduce(thisValue)
            }
        }


        //обработка расшариваний
        val sharescount = binding.sharescount
        binding.shares.setOnClickListener {
            val sharecount = binding.sharescount
            var intShareCount = 0
            try {
                intShareCount = sharescount.getText().toString().toInt()
                val newval = intShareCount + 1
                val newString = Functions().setString(newval)
                sharecount.setText(newString)
            } catch (nfe: NumberFormatException) {
                println("Could not parse $intShareCount")
            }
        }
    }
}



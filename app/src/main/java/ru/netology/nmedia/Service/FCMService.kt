package ru.netology.nmedia.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.setString
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {
    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data[action]?.let {
            when (message.data[action]) {
                "LIKE" -> handleLike(gson.fromJson(message.data[content], Like::class.java))
                "NEWPOST" -> handleNewPost(gson.fromJson(message.data[content], NewPost::class.java))
                else -> {
                    //wrongAction(WrongMes::class.java)
                    wrongAction1()
                }
            }

        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }

    //неправильный пуш
    private fun wrongAction(java: Class<WrongMes>) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_send_wrong,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    //неправильный пуш1
    private fun wrongAction1() {
        Log.d("wrong Message", "Приложение попыталось отправить юзеру пуш с несуществующим ACTION")
    }

    //новый пост
    private fun handleNewPost(content: NewPost) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_posted_post_title,
                    content.postOwner
                )
            )
            .setContentText(content.postContent)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.notification_user_posted_post_text,
                content.postContent)))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    //лайк от пользователя
    private fun handleLike(content: Like) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName,
                    content.postAuthor,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }
}

enum class Action {
    LIKE,
    NEWPOST,
}

data class Like(
    val userId: Int,
    val userName: String,
    val postId: Int,
    val postAuthor: String,
)
data class NewPost(
    val postOwner: String,
    val postContent: String,
)
data class WrongMes(
    val postAuthor: String = "Netologia",
    val postContent: String,
)
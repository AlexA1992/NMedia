package ru.netology.nmedia.Activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text
import ru.netology.nmedia.Activitys.CreatePostFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.Repos.PostRepo
import ru.netology.nmedia.StringArg
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostAdapter
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.databinding.SinglePostBinding

class SinglePostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SinglePostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(
            ownerProducer =
            ::requireParentFragment
        )
        val postToShowId = arguments?.textId

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val thePost = posts.find {
                postToShowId?.toInt() == it.id
            }
//            println(thePost)
            val content =
                binding.post.content
            val author =
                binding.post.schoolname
            val likes =
                binding.post.likes
            val repostsQ =
                binding.post.shares
            val date = binding.post.date

            if (thePost?.video != null) {
                val isVideo =
                    binding.post.play
                isVideo.visibility = android.view.View.VISIBLE
            }

            content.setText(thePost?.content)
            author.setText(thePost?.author)
            likes.setText(thePost?.liked.toString())
            if (thePost != null) {
                println("thePost.liked ${thePost.liked}")
                likes.isChecked = thePost.likedbyMe
            }
            repostsQ.setText(thePost?.repostsQ.toString())
            if (thePost != null) {
                println("thePost.repostsQ ${thePost.repostsQ}")
                repostsQ.isChecked = thePost.repostsQ > 0
            }
            date.setText(thePost?.date)

            likes.setOnClickListener {
                if (thePost != null) {
                    viewModel.likesClicked(thePost)
                }
                findNavController().navigateUp()
            }

            repostsQ.setOnClickListener {
                if (thePost != null) {
                    viewModel.shareClicked(thePost)
                    repostsQ.isChecked = thePost.repostsQ > 0
                    repostsQ.setIconResource(R.drawable.allshares)
                }
                findNavController().navigateUp()
            }
            //не получается создать экземпляр Вьюхолдера
//            val singleViewHolder = PostAdapter(
//                PostRepo().::likesChange(thePost)
//            ).ViewHolder(
//                binding
//            )
            val menuButton: MaterialButton = binding.root.findViewById(R.id.menuButton)
                val popupMenu by lazy {
                    //println(post)
                    PopupMenu(this.context, menuButton).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.remove -> {
                                    if (thePost != null) {
                                        viewModel.onDeleteClicked(thePost)
                                    }
                                    findNavController().navigateUp()
                                    true
                                }

                                R.id.edit -> {
                                    if (thePost != null) {
                                        viewModel.onEditClicked(thePost)
                                    }
                                    findNavController().navigate(
                                        R.id.action_singlePostFragment_to_createPostFragment,
                                        Bundle().apply {
                                            if (thePost != null) {
                                                textArg = thePost.content
                                            }
                                        })
                                    true
                                }
                                else -> false
                            }
                        }
                    }
                }
            menuButton.setOnClickListener {
                menuButton.isChecked = true
                popupMenu.show()
            }
        }
        return binding.root
    }

    companion object {
        var Bundle.textId: String? by StringArg
    }
}

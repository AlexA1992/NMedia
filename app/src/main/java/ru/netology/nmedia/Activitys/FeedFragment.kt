package ru.netology.nmedia.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Activitys.CreatePostFragment.Companion.textArg
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostAdapter
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    val viewModel: PostViewModel by viewModels(
        ownerProducer =
        ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter1 = PostAdapter(
            viewModel::likesClicked,
            viewModel::shareClicked,
            viewModel::onDeleteClicked,
            viewModel::onEditClicked,
            viewModel::postClicked,
        )

        binding.postsRecyclerView.adapter = adapter1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter1.posts = posts
        }

        //создание поста
        binding.create.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPostFragment)
        }

//        viewModel.shareActionNeeded.observe(this) { content ->
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, content)
//                type = "text/plain"
//            }
//
//            val shareIntent = Intent.createChooser(
//                intent, "Выберите приложение"
//            )
//            startActivity(shareIntent)
//        }

        //передать фрагмент с целым постом
        viewModel.postToTransfer.observe(viewLifecycleOwner) { postToTransfer ->
            findNavController().navigate(R.id.action_feedFragment2_to_singlePostFragment,
            Bundle().apply {
                 if (postToTransfer != null) {
                     SinglePostFragment().arguments = bundleOf(
                         "post.id" to postToTransfer.id,
                         "post.author" to postToTransfer.author,
                         "post.content" to postToTransfer.content,
                         "post.edited" to postToTransfer.edited,
                         "post.date" to postToTransfer.date,
                         "post.liked" to postToTransfer.liked,
                         "post.likedbyMe" to postToTransfer.likedbyMe,
                         "post.repostsQ" to postToTransfer.repostsQ,
                         "post.video" to postToTransfer.video
                     )
                 }
             })

        }

        //редактим пост
        viewModel.currentPost.observe(viewLifecycleOwner) { currentPost ->
            if (currentPost != null) {
                val textToEdit = currentPost.content
                findNavController().navigate(
                    R.id.action_feedFragment_to_createPostFragment,
                    Bundle().apply {
                        textArg = textToEdit
                    })
            }
        }
        return binding.root
    }
//    val editLauncher = registerForActivityResult(AppActivity.ResultContract) {
//        val content = it ?: return@registerForActivityResult
//        viewModel.onSaveButtonClicked(content)
//    }

//    val createLauncher = registerForActivityResult(CreatePostFragment.ResultCreateContract) {
//        val content = it ?: return@registerForActivityResult
//        viewModel.onSaveButtonClicked(content)
//        Toast.makeText(layoutInflater.context, "Done", Toast.LENGTH_SHORT).show()
//    }
}





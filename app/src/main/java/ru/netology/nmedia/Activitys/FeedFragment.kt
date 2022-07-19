package ru.netology.nmedia.Activitys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Activitys.CreatePostFragment.Companion.textArg
import ru.netology.nmedia.Activitys.SinglePostFragment.Companion.textId
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
            if (postToTransfer != null) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_singlePostFragment,
                    Bundle().apply {
                        println("in postToTransferObserver")
                        textId = postToTransfer?.id.toString()
                    })
            }
        }
        //редактим пост
        viewModel.currentPost.observe(viewLifecycleOwner) { currentPost ->
            if (currentPost != null) {
                val textToEdit = currentPost.content
                findNavController().navigate(
                    R.id.action_feedFragment_to_createPostFragment,
                    Bundle().apply {
                        println("in postcurrentPostObserver")
                        textArg = textToEdit
                    })
            }
        }
        return binding.root
    }
}





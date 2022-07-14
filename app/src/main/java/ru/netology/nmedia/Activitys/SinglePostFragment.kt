package ru.netology.nmedia.Activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Activitys.CreatePostFragment.Companion.textArg
import ru.netology.nmedia.StringArg
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.SinglePostBinding

class SinglePostFragment : Fragment() {
    private val postId by lazy {
        val id = requireArguments().getInt("post.id")
        //println(id)
    }
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
        binding.singlePost.id = id
        println(binding.singlePost.id)
        return binding.root
    }

}
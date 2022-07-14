package ru.netology.nmedia.Activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.StringArg
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer =
        ::requireParentFragment)

//        binding.saveCreated.clearFocus()
        val editField = binding.newPost
        editField.requestFocus()

        val text = arguments?.textArg
        text?.let {
            editField.setText(it)
        }
        binding.saveCreated.setOnClickListener {
            val newText = editField.text.toString()
            //val newIntent = Intent()
            if (!newText.isBlank()) {
                viewModel.onSaveButtonClicked(newText)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
    companion object{
        var Bundle.textArg: String? by StringArg
    }

//    object ResultCreateContract : ActivityResultContract<Unit, String?>() {
//        override fun createIntent(context: Context, input: Unit) =
//            Intent(context, CreatePostFragment::class.java)
//
//        override fun parseResult(resultCode: Int, intent: Intent?): String {
//            var theReturn: String = ""
//            if (resultCode == Activity.RESULT_OK) {
//                theReturn = intent?.getStringExtra("newPost").toString()
//            }
//            return theReturn
//        }
//    }
}

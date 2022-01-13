package com.udacity.shoestore.screens.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionsBinding
import timber.log.Timber

class InstructionsFragment : Fragment() {

    private lateinit var viewModel: InstructionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentInstructionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_instructions, container, false)
        viewModel = ViewModelProvider(this)[InstructionsViewModel::class.java]
        binding.instructionsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.step.observe(viewLifecycleOwner, { step ->
            when (step) {
                1 -> binding.instructionViewText.visibility = View.VISIBLE
                2 -> {
                    binding.instructionRemoveText.visibility = View.VISIBLE
                    binding.instructionButton.text =
                        resources.getString(R.string.instruction_finish)
                }
            }
        })

        viewModel.finished.observe(viewLifecycleOwner, { finished ->
            if (finished) {
                viewModel.onFinishHandled()
                navigateToShoes()
            }
        })

        return binding.root
    }

    private fun navigateToShoes() {
        Timber.d("Navigating to main screen")
        findNavController().navigate(InstructionsFragmentDirections.actionInstructionsFragmentToListFragment())
    }
}
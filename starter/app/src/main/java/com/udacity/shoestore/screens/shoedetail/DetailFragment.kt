package com.udacity.shoestore.screens.shoedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoelist.ListViewModel
import timber.log.Timber

class DetailFragment : Fragment() {

    /**
     * Use shared viewmodel between DetailFragment and ListFragment
     * since we are only saving data to the viewmodel itself.
     */
    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.listViewModel = viewModel
        binding.newShoe = Shoe("", 0.0, "", "")

        binding.saveShoe.setOnClickListener {
            Timber.d("Saving new shoe")
            val newShoe = binding.newShoe
            if (newShoe != null) {
                viewModel.addNewShoe(newShoe)
                navigateToList()
            }
        }

        return binding.root
    }

    private fun navigateToList() {
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToListFragment())
    }
}
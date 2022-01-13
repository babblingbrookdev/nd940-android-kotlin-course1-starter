package com.udacity.shoestore.screens.shoelist

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentListBinding
import com.udacity.shoestore.databinding.ViewShoeBinding
import timber.log.Timber

class ListFragment : Fragment() {

    companion object {
        const val USER_LOGGED_IN = "user_logged_in"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
    }

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
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.listViewModel = viewModel

        // add logout menu
        setHasOptionsMenu(true)

        // navigate to login fragment if user is not logged in
        checkForLoginStatus()

        // navigate to detail screen when fab is clicked
        binding.addShoes.setOnClickListener {
            navigateToDetailScreen()
        }

        // observe shoe list for changes and add views for each shoe
        viewModel.shoes.observe(viewLifecycleOwner, { shoes ->
            shoes.forEach { shoe ->
                val listItemBinding: ViewShoeBinding =
                    DataBindingUtil.inflate(inflater, R.layout.view_shoe, binding.listLayout, false)
                listItemBinding.shoe = shoe
                binding.listLayout.addView(listItemBinding.root)
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            // remove login credentials
            with(sharedPref.edit()) {
                putString(USER_EMAIL, "")
                putString(USER_PASSWORD, "")
                putBoolean(USER_LOGGED_IN, false)
                apply()
            }
            viewModel.clearShoes()
            // navigate user back to login screen
            findNavController().navigate(ListFragmentDirections.actionListFragmentToLoginFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkForLoginStatus() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        if (!sharedPref.getBoolean(USER_LOGGED_IN, false)) {
            Timber.d("User is not logged in, navigating to login screen")
            findNavController().navigate(ListFragmentDirections.actionListFragmentToLoginFragment())
        }
    }

    private fun navigateToDetailScreen() {
        Timber.d("Navigating to detail screen")
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment())
    }
}
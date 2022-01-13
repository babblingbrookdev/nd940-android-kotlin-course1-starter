package com.udacity.shoestore.screens.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

    companion object {
        const val USER_LOGGED_IN = "user_logged_in"
        const val USER_EMAIL = "user_email"
        const val USER_PASSWORD = "user_password"
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel = viewModel

        viewModel.loginClicked.observe(viewLifecycleOwner, { loginClicked ->
            if (loginClicked) {
                viewModel.loginHandled()
                saveCredentials()
                navigateToOnboarding()
            }
        })

        viewModel.isEmailValid.observe(viewLifecycleOwner, { isEmailValid ->
            if (!isEmailValid) {
                Timber.i("email does not pass validation")
                binding.editTextEmailAddress.setError(
                    "Email address is not valid.",
                    ContextCompat.getDrawable(
                        requireContext(),
                        android.R.drawable.stat_notify_error
                    )
                )
            }
        })

        viewModel.isPasswordValid.observe(viewLifecycleOwner, { isPasswordValid ->
            if (!isPasswordValid) {
                Timber.i("Password does not pass validation")
                binding.editTextPassword.setError(
                    "Password must be at least 6 characters in length.",
                    ContextCompat.getDrawable(
                        requireContext(),
                        android.R.drawable.stat_notify_error
                    )
                )
            }
        })
        return binding.root
    }

    private fun saveCredentials() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        Timber.i("Saving user credentials")
        with(sharedPref.edit()) {
            putBoolean(USER_LOGGED_IN, true)
            putString(USER_EMAIL, viewModel.email)
            putString(
                USER_PASSWORD,
                viewModel.password
            )
            apply()
        }
    }

    private fun navigateToOnboarding() {
        Timber.i("Navigating to onboarding screen")
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
    }
}
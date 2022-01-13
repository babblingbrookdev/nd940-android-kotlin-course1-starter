package com.udacity.shoestore.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel : ViewModel() {

    lateinit var email: String
    lateinit var password: String

    private val _loginClicked = MutableLiveData<Boolean>()
    val loginClicked: LiveData<Boolean> get() = _loginClicked

    private val _isEmailValid = MutableLiveData<Boolean>()
    val isEmailValid: LiveData<Boolean> get() = _isEmailValid

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid: LiveData<Boolean> get() = _isPasswordValid

    val loginEnabled = MediatorLiveData<Boolean>()

    init {
        loginEnabled.addSource(isEmailValid) {
            loginEnabled.value = isEmailValid.value == true && isPasswordValid.value == true
        }
        loginEnabled.addSource(isPasswordValid) {
            loginEnabled.value = isEmailValid.value == true && isPasswordValid.value == true
        }
    }

    fun onLoginClicked() {
        Timber.d("onLoginClicked()")
        _loginClicked.value = true
    }

    fun loginHandled() {
        Timber.d("onLoginHandled")
        _loginClicked.value = false
    }

    fun isEmailValid(email: String): Boolean {
        Timber.d("Validating email address")
        this.email = email
        _isEmailValid.value =
            email.isNotEmpty() and android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return isEmailValid.value!!
    }

    fun isPasswordValid(password: String): Boolean {
        Timber.d("Validating password")
        this.password = password
        _isPasswordValid.value = password.length > 6
        return isPasswordValid.value!!
    }
}
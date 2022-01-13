package com.udacity.shoestore.screens.instructions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class InstructionsViewModel : ViewModel() {

    private val _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean> get() = _finished

    private val _step = MutableLiveData<Int>()
    val step: LiveData<Int> get() = _step

    init {
        _step.value = 0
        _finished.value = false
    }

    fun onNextClicked() {
        Timber.d("onNextClicked(), step value is %s", step.value)
        when (step.value) {
            0 -> _step.value = _step.value?.plus(1)
            1 -> _step.value = _step.value?.plus(1)
            2 -> _finished.value = true
        }
    }

    fun onFinishHandled() {
        Timber.d("onFinishHandled()")
        _finished.value = false
        _step.value = 0
    }
}
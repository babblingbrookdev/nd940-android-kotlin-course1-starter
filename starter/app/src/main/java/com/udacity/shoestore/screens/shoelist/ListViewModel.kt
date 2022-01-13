package com.udacity.shoestore.screens.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ListViewModel : ViewModel() {

    private val shoeList = ArrayList<Shoe>()
    private val _shoes = MutableLiveData<List<Shoe>>()
    val shoes: LiveData<List<Shoe>> get() = _shoes

    fun addNewShoe(shoe: Shoe) {
        shoeList.add(shoe)
        _shoes.value = shoeList
        Timber.d("Shoe list size: %s", _shoes.value?.size)
    }

    fun clearShoes() {
        _shoes.value = mutableListOf()
    }
}
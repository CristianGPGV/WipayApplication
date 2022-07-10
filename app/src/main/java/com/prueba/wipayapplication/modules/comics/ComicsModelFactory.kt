package com.prueba.wipayapplication.modules.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prueba.wipayapplication.marvelapi.io.MainRepository

class ComicsModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ComicsViewModel::class.java)) {
            ComicsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
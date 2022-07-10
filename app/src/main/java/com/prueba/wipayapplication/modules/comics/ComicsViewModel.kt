package com.prueba.wipayapplication.modules.comics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.wipayapplication.marvelapi.io.MainRepository
import com.prueba.wipayapplication.marvelapi.model.MarvelRequest
import com.prueba.wipayapplication.marvelapi.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsViewModel constructor(private val repository: MainRepository) : ViewModel() {


    val errorMessage = MutableLiveData<String>()
    val listComics = MutableLiveData<List<Result>>()


    fun getComics(idCharacter: String) {
        val response = repository.getComics(idCharacter)
        response.enqueue(object : Callback<MarvelRequest> {
            override fun onResponse(
                call: Call<MarvelRequest>,
                response: Response<MarvelRequest>
            ) {

                listComics.value = response.body()?.data?.results
            }

            override fun onFailure(call: Call<MarvelRequest>, t: Throwable) {
                errorMessage.postValue("error")
            }
        })
    }

}
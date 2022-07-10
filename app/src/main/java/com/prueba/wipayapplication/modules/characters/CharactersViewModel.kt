package com.prueba.wipayapplication.modules.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prueba.wipayapplication.marvelapi.io.MainRepository
import com.prueba.wipayapplication.marvelapi.model.MarvelRequest
import com.prueba.wipayapplication.marvelapi.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CharactersViewModel constructor(private val repository: MainRepository) : ViewModel() {

    companion object {
        private const val CHARACTER1 = "Iron man"
        private const val CHARACTER2 = "thor"
        private const val CHARACTER3 = "hulk"
        private const val CHARACTER4 = "Captain america"
        private const val ERROR = "Se ha producido un error al consultar los datos."
    }

    val errorMessage = MutableLiveData<String>()
    val listCharacter = MutableLiveData<List<Result>>()
    private lateinit var job: Job

    fun getCharacters() {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val resOne = repository.getCharacter(CHARACTER1)
                val resTwo = repository.getCharacter(CHARACTER2)
                val resThree = repository.getCharacter(CHARACTER3)
                val resFour = repository.getCharacter(CHARACTER4)
                if (resOne.isSuccessful && resTwo.isSuccessful && resThree.isSuccessful && resFour.isSuccessful) {
                    resOne.body()?.let {
                        resTwo.body()?.let { it1 ->
                            resThree.body()?.let { it2 ->
                                resFour.body()?.let { it3 ->
                                    onResponse(it, it1, it2, it3)
                                }
                            }
                        }
                    }
                } else {
                    onError()
                }
            } catch (exception: Exception) {
                onError()
            }
        }
    }


    private fun onResponse(
        resOne: MarvelRequest,
        resTwo: MarvelRequest,
        resThree: MarvelRequest,
        resFour: MarvelRequest
    ) {
        listCharacter.value = (listOf(
            resOne.data.results[0],
            resTwo.data.results[0],
            resThree.data.results[0],
            resFour.data.results[0]
        ))
    }

    private fun onError() {
        errorMessage.value = ERROR
    }

}
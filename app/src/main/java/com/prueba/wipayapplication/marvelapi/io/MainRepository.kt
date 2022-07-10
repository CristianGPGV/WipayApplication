package com.prueba.wipayapplication.marvelapi.io

class MainRepository constructor(private val retrofitService: RetrofitService) {

     suspend fun getCharacter(nameCharacter: String) = retrofitService.getCharacter(nameCharacter)

     fun getComics(idCharacter: String) = retrofitService.getComics(idCharacter)

}
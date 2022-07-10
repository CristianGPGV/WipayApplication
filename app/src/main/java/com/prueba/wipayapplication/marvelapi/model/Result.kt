package com.prueba.wipayapplication.marvelapi.model

import java.io.Serializable


data class Result(
    val id: Long?,
    val name: String?,
    val title: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: Thumbnail?,
    val resourceURI: String?,
) : Serializable
package com.prueba.wipayapplication.marvelapi.model

import java.io.Serializable

data class MarvelRequest(
    val code: Long?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val etag: String?,
    val data: Data
) : Serializable
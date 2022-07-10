package com.prueba.wipayapplication.marvelapi.model

import java.io.Serializable


data class Data (
    val offset: Long?,
    val limit: Long?,
    val total: Long?,
    val count: Long?,
    val results: List<Result>
) : Serializable
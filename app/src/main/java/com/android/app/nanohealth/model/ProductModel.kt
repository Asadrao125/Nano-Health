package com.android.app.nanohealth.model

import java.io.Serializable

class ProductModel(
    val id: String,
    val title: String,
    val price: String,
    val description: String,
    val rate: Double,
    val count: Int,
    val image: String
) : Serializable
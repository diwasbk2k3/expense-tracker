package com.example.expensetracker.repository

import com.example.expensetracker.model.ProductModel

// yesma achai product related funtion rakhney
interface ProductRepository {
//    {
//        "success": true,
//        "message": "Product Added Successfully"
//    }
    // yo productModel call garesi ProductModel ma bhako sabai aaucha
    fun addProduct(productModel: ProductModel, callback:(Boolean, String)-> Unit)

    fun updateProduct(productId: String, data:MutableMap<String, Any>, callback: (Boolean, String) -> Unit)

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit)

    // question marks vaneko nullable ( harek time data huna pani sakcha nahuna pani sakcha)
    fun getProductById(productId: String, callback: (ProductModel?, Boolean, String) -> Unit)

    fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit)
}
package com.example.expensetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.model.ProductModel
import com.example.expensetracker.repository.ProductRepository

class ProductViewModel(val repo: ProductRepository) {
    fun addProduct(productModel: ProductModel, callback:(Boolean, String)-> Unit){
        repo.addProduct(productModel, callback)
    }

    fun updateProduct(productId: String, data:MutableMap<String, Any>, callback: (Boolean, String) -> Unit){
        repo.updateProduct(productId, data, callback)
    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit){
        repo.deleteProduct(productId, callback)
    }

    // getProductById ko lagi
    var _products = MutableLiveData<ProductModel>()
    var products = MutableLiveData<ProductModel>()
        get() = _products

    fun getProductById(productId: String){
        repo.getProductById(productId){
            products, success, message->
            if(success){
                _products.value = products
            }
        }
    }

    //loading ko lagi
    var _loading = MutableLiveData<List<Boolean>>()
    var loading = MutableLiveData<List<Boolean>>()
        get() = _loading

    // getAllProduct ko lagi
    var _allProducts = MutableLiveData<List<ProductModel>>()
    var allProducts = MutableLiveData<List<ProductModel>>()
        get() = _allProducts

    fun getAllProduct(){
        repo.getAllProduct{
            products, success, message->
            if(success){
                _allProducts.value = products
            }
        }
    }
}
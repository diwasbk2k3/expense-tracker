package com.example.expensetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expensetracker.model.ProductModel
import com.example.expensetracker.repository.ProductRepository

class ProductViewModel(private val repo: ProductRepository) {

    private val _products = MutableLiveData<ProductModel>()
    val products: LiveData<ProductModel> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _allProducts = MutableLiveData<List<ProductModel>>(emptyList())
    val allProducts: LiveData<List<ProductModel>> get() = _allProducts

    fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        _loading.value = true
        repo.addProduct(productModel) { success, message ->
            _loading.value = false
            callback(success, message)
        }
    }

    fun updateProduct(productId: String, data: MutableMap<String, Any>, callback: (Boolean, String) -> Unit) {
        _loading.value = true
        repo.updateProduct(productId, data) { success, message ->
            _loading.value = false
            callback(success, message)
        }
    }

    fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        _loading.value = true
        repo.deleteProduct(productId) { success, message ->
            _loading.value = false
            callback(success, message)
        }
    }

    fun getProductById(productId: String) {
        _loading.value = true
        repo.getProductById(productId) { product, success, message ->
            _loading.value = false
            if (success) {
                _products.value = product
            }
        }
    }

    fun getAllProduct() {
        _loading.value = true
        repo.getAllProduct { products, success, message ->
            _loading.value = false
            if (success) {
                _allProducts.value = products
            }
        }
    }
}

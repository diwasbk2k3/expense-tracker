package com.example.expensetracker.repository

import android.util.Log
import com.example.expensetracker.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepositoryImpl: ProductRepository {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference = database.reference.child("products") // yesma products vanni table use gareko yedi chaina vani products vanni table aafai create huncha

    override fun addProduct(productModel: ProductModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString() // primarykey generate gareko (id)
        productModel.productId = id // uta bata khali aako id ma new primary key id push handeko

        ref.child(id).setValue(productModel).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true, "Product Added Successfully")
            }else{
                callback(false, "${it.exception?.message}") // database bata aako error show garcha as a message
            }
        }
    }

    override fun updateProduct(
        productId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).updateChildren(data).addOnCompleteListener{
            if(it.isSuccessful){
                callback(true, "Product Updated Successfully")
            }else{
                callback(false, "${it.exception?.message}") // database bata aako error show garcha as a message
            }
        }
    }

    override fun deleteProduct(productId: String, callback: (Boolean, String) -> Unit) {
        ref.child(productId).removeValue().addOnCompleteListener{
            if(it.isSuccessful){
                callback(true, "Product Removed Successfully")
            }else{
                callback(false, "${it.exception?.message}") // database bata aako error show garcha as a message
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (ProductModel?, Boolean, String) -> Unit
    ) {
        ref.child(productId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val model =  snapshot.getValue(ProductModel:: class.java)
                    callback(model, true, "Product fetched successfully")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message) // jaba connection fail huncha
            }
        })
    }

    override fun getAllProduct(callback: (List<ProductModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<ProductModel>()
                if(snapshot.exists()){
                    for(eachProduct in snapshot.children){
                        var data = eachProduct.getValue(ProductModel::class.java)
                        if(data!=null){
                            products.add(data)
                        }
                    }
                    callback(products,true,"success")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message) // jaba connection fail huncha
            }
        })
    }
}
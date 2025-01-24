package com.example.expensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityUpdateProductBinding
import com.example.expensetracker.model.ProductModel
import com.example.expensetracker.repository.ProductRepositoryImpl
import com.example.expensetracker.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateProductBinding
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        var productId: String = intent.getStringExtra("productId").toString()

        productViewModel.getProductById(productId)

        productViewModel.products.observe(this){
            binding.updateProductName.setText(it?.productName.toString())
            binding.updateProductPrice.setText(it?.price.toString())
            binding.updateProductDescription.setText(it?.productDesc.toString())
        }

        binding.btnUpdate.setOnClickListener {
            var name = binding.updateProductName.text.toString()
            var price = binding.updateProductPrice.text.toString().toInt()
            var desc = binding.updateProductDescription.text.toString()

            var updatedMap = mutableMapOf<String, Any>()
            updatedMap["productName"] = name
            updatedMap["productDesc"] = desc
            updatedMap["price"] = price

            productViewModel.updateProduct(productId, updatedMap){
                success, message->
                if (success) {
                    Toast.makeText(this, "Product updated successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update product: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
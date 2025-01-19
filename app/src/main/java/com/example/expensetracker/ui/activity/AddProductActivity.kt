package com.example.expensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityAddProductBinding
import com.example.expensetracker.databinding.ActivityProductDashboardBinding
import com.example.expensetracker.model.ProductModel
import com.example.expensetracker.repository.ProductRepositoryImpl
import com.example.expensetracker.utils.LoadingUtils
import com.example.expensetracker.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding
    lateinit var loadingUtils: LoadingUtils
    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)

        binding.btnAddProduct.setOnClickListener {
            val name = binding.editProductName.text.toString()
            val desc = binding.editProductDescription.text.toString()
            val price = binding.editProductPrice.text.toString().toInt()

            val model = ProductModel("", name, desc, price)

            productViewModel.addProduct(model){
                success, message->
                if(success)     {
                    Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@AddProductActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

        }
        // not needed
//        setContentView(R.layout.activity_add_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
package com.example.expensetracker.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.adapter.ProductAdapter
import com.example.expensetracker.databinding.ActivityProductDashboardBinding
import com.example.expensetracker.repository.ProductRepositoryImpl
import com.example.expensetracker.viewmodel.ProductViewModel

class ProductDashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDashboardBinding
    lateinit var productViewModel: ProductViewModel
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImpl()
        productViewModel = ProductViewModel(repo)
        adapter = ProductAdapter(this@ProductDashboardActivity,
            ArrayList())

        productViewModel.getAllProduct()

        productViewModel.allProducts.observe(this){it->
            it?.let {
                adapter.updateData(it)
            }
        }
        binding.productDashboardRecyclerView.adapter = adapter
        binding.productDashboardRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(this@ProductDashboardActivity, AddProductActivity::class.java)
            startActivity(intent)
        }
// yo chaidaina
//        setContentView(R.layout.activity_product_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
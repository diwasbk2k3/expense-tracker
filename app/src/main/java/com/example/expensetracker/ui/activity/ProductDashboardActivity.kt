package com.example.expensetracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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

        productViewModel.loading.observe(this){loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var productId = adapter.getProductId(viewHolder.adapterPosition)
                productViewModel.deleteProduct(productId){
                    success, message->
                    if(success){
                        Toast.makeText(this@ProductDashboardActivity, "Product Deleted Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ProductDashboardActivity, "Error Deleting", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.productDashboardRecyclerView)
// yo chaidaina
//        setContentView(R.layout.activity_product_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
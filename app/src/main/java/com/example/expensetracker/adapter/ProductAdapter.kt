package com.example.expensetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.model.ProductModel

class ProductAdapter (
    var context: Context,
    var data: ArrayList<ProductModel>
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //binding gareko
        val pName: TextView = itemView.findViewById(R.id.displayName)
        val pDesc: TextView = itemView.findViewById(R.id.displayDescription)
        val pPrice: TextView = itemView.findViewById(R.id.displayPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO("Not yet implemented")
        // hamro recyclerView ekatira cha ani sample_file ekatira cha (so we link them here together)
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_file, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return  data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        TODO("Not yet implemented")
        // LHS is from the above binding and RHS is from the Model
        holder.pName.text = data[position].productName
        holder.pDesc.text = data[position].productDesc
        holder.pPrice.text = data[position].price.toString()
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged() // App refresh nagari data change or display gardincha (state manage garcha)
    }
}
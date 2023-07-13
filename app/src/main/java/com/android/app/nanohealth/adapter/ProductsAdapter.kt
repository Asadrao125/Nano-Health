package com.android.app.nanohealth.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.app.nanohealth.R
import com.android.app.nanohealth.adapter.ProductsAdapter.MyViewHolder
import com.android.app.nanohealth.model.ProductModel
import com.android.app.nanohealth.ui.DetailActivity
import com.squareup.picasso.Picasso

class ProductsAdapter(var context: Context, var ordersModelArrayList: ArrayList<ProductModel>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val productModel = ordersModelArrayList[position]
        holder.productName.text = productModel.title
        holder.productDescription.text = productModel.description
        holder.productPrice.text = productModel.price + " AED"
        Picasso.get().load(productModel.image).placeholder(R.drawable.placeholder_image)
        holder.productRb.rating = productModel.rate.toFloat()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", productModel.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ordersModelArrayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productName: TextView
        var productDescription: TextView
        var productPrice: TextView
        var productRb: RatingBar
        var productImage: ImageView

        init {
            productName = itemView.findViewById(R.id.tvProductName)
            productDescription = itemView.findViewById(R.id.tvProductDescription)
            productPrice = itemView.findViewById(R.id.tvProductPrice)
            productRb = itemView.findViewById(R.id.productRb)
            productImage = itemView.findViewById(R.id.productImage)
        }
    }
}
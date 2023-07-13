package com.android.app.nanohealth.ui.products

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.app.nanohealth.R
import com.android.app.nanohealth.adapter.ProductsAdapter
import com.android.app.nanohealth.api.ApiCallback
import com.android.app.nanohealth.api.ApiManager
import com.android.app.nanohealth.databinding.FragmentProductsBinding
import com.android.app.nanohealth.model.ProductModel
import com.android.app.nanohealth.utils.Const
import com.loopj.android.http.RequestParams
import org.json.JSONArray
import org.json.JSONObject

class ProductsFragment : Fragment(), ApiCallback {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    var apiCallback: ApiCallback? = null
    val productList: ArrayList<ProductModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        apiCallback = this
        binding.toolbarTitle.setText(getString(R.string.all_products))

        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        binding.rvProducts.setHasFixedSize(true)

        loadAllProducts()

        return root
    }

    fun loadAllProducts() {
        val requestParams = RequestParams()
        val apiManager =
            ApiManager(activity as Activity, "get", Const.PRODUCTS, requestParams, apiCallback!!)
        apiManager.loadURL(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onApiResponce(
        httpStatusCode: Int,
        successOrFail: Int,
        apiName: String?,
        apiResponce: String?
    ) {
        productList.clear()
        val array = JSONArray(apiResponce)
        for (i in 0 until array.length()) {
            val productObj = array.getJSONObject(i)
            val id = productObj.getString("id")
            val name = productObj.getString("title")
            val price = productObj.getString("price")
            val description = productObj.getString("description")
            val rate = productObj.getJSONObject("rating").getDouble("rate")
            val count = productObj.getJSONObject("rating").getInt("count")
            val image = productObj.getString("image")
            val productModel = ProductModel(id, name, price, description, rate, count, image)
            productList.add(productModel)
        }
        binding.rvProducts.adapter = ProductsAdapter(activity as Activity, productList)
    }
}
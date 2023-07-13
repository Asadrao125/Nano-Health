package com.android.app.nanohealth.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.app.nanohealth.R
import com.android.app.nanohealth.api.ApiCallback
import com.android.app.nanohealth.api.ApiManager
import com.android.app.nanohealth.databinding.ActivityDetailBinding
import com.android.app.nanohealth.model.ProductModel
import com.android.app.nanohealth.utils.Const
import com.loopj.android.http.RequestParams
import org.json.JSONArray
import org.json.JSONObject

class DetailActivity : AppCompatActivity(), ApiCallback {
    private var binding: ActivityDetailBinding? = null
    var apiCallback: ApiCallback? = null
    var id: String? = ""
    var isShowRatingLayout: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

        apiCallback = this
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_grey)
        id = intent.getStringExtra("id")

        loadSingleProducts(id)

        binding!!.icBack.setOnClickListener {
            onBackPressed()
        }

        binding!!.upAndDownArrow.setOnClickListener {
            if (isShowRatingLayout != true) {
                binding!!.reviewsLayout.visibility = View.GONE
                binding!!.upAndDownArrow.setImageResource(R.drawable.ic_down_arrow)
                isShowRatingLayout = true
            } else {
                binding!!.reviewsLayout.visibility = View.VISIBLE
                binding!!.upAndDownArrow.setImageResource(R.drawable.ic_up_arrow)
                isShowRatingLayout = false
            }
        }
    }

    fun loadSingleProducts(id: String?) {
        val requestParams = RequestParams()
        val apiManager =
            ApiManager(this, "get", Const.SINGLE_PRODUCT + id, requestParams, apiCallback!!)
        apiManager.loadURL(1)
    }

    override fun onApiResponce(
        httpStatusCode: Int,
        successOrFail: Int,
        apiName: String?,
        apiResponce: String?
    ) {
        val productObj = JSONObject(apiResponce)
        val id = productObj.getString("id")
        val name = productObj.getString("title")
        val price = productObj.getString("price")
        val description = productObj.getString("description")
        val rate = productObj.getJSONObject("rating").getDouble("rate")
        val count = productObj.getJSONObject("rating").getInt("count")
        val image = productObj.getString("image")
        val productModel = ProductModel(id, name, price, description, rate, count, image)

        binding!!.tvPrice.text = productModel.price + " AED"
        binding!!.descriptionValue.text = productModel.description
        binding!!.tvReviewsCount.text = "Reviews(" + productModel.count + ")"
        binding!!.tvRating.text = "" + productModel.rate
        binding!!.productRb.rating = productModel.rate.toFloat()

    }
}
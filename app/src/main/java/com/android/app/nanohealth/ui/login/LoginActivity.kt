package com.android.app.nanohealth.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.app.nanohealth.MainActivity
import com.android.app.nanohealth.R
import com.android.app.nanohealth.api.ApiCallback
import com.android.app.nanohealth.api.ApiManager
import com.android.app.nanohealth.databinding.ActivityLoginBinding
import com.android.app.nanohealth.utils.Const
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), ApiCallback {
    private var binding: ActivityLoginBinding? = null
    var apiCallback: ApiCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)

        apiCallback = this

        binding!!.continueBtn.setOnClickListener {
            val email: String = binding!!.edtEmail.text.toString()
            val password: String = binding!!.edtPassword.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
            } else {
                doLogin(email, password)
            }
        }
    }

    fun doLogin(username: String?, password: String?) {
        val loginObj = JSONObject()
        loginObj.put("username", username)
        loginObj.put("password", password)
        val apiManager = ApiManager(this, "post", Const.LOGIN, loginObj, apiCallback!!)
        apiManager.jsonPost()
    }

    override fun onApiResponce(
        httpStatusCode: Int,
        successOrFail: Int,
        apiName: String?,
        apiResponce: String?
    ) {
        val loginObject = JSONObject(apiResponce)
        val token = loginObject.getString("token")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
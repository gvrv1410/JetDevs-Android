package com.imaginato.homeworkmvvm.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.ui.base.BaseActivity
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import com.imaginato.homeworkmvvm.ui.dashboardActivity.DashboardActivity
import com.imaginato.homeworkmvvm.utility.Constant
import com.imaginato.homeworkmvvm.utility.SharedPrefHelper
import com.imaginato.homeworkmvvm.utility.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity() {
    val loginViewModel  by  viewModel<LoginActivityViewModel>()
    override fun getBaseViewModel(): BaseViewModel = loginViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        

        initView()
        observer()

        if (SharedPrefHelper.get(Constant.Pref_IsLogin,false)){
         toast("Already login")
            finish()
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
        }

    }

    private fun observer() {

        loginViewModel.loginLiveData.observe(this, Observer {
            progressDialogVisibility(false)

            toast(it.errorMessage)
            if (it.errorCode.equals("00")){
                SharedPrefHelper.save(Constant.Pref_IsLogin,true)
                SharedPrefHelper.save(Constant.Pref_UserId,it.data.userId)
                toast("Login Successfull")
                finish()
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))

            }else{
                toast(it.errorMessage)
            }
        })
    }

    private fun initView() {
//        binding.btnLogin.setTextColor(ContextCompat.getColorStateList(this, R.drawable.selector_button))
        binding.btnLogin.isEnabled = false

        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                if (s.toString().trim().length > 2) {
                    binding.etUsername.error = null
                } else {
                    binding.etUsername.error = "Please enter valid username"
                }
                val username = binding.etUsername.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                binding.btnLogin.isEnabled = username.length > 2 && password.length > 4
            }
        })
        binding.etPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().trim().length > 4) {
                    binding.etPassword.error = null
                } else {
                    binding.etPassword.error = "Required atleast 5 character"
                }
                val username = binding.etUsername.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                binding.btnLogin.isEnabled = username.length > 2 && password.length > 4
            }

        })
       binding.btnLogin.setOnClickListener {

           if (isNetworkAvailable()){
               progressDialogVisibility(true)
               val map = mapOf(
                   "username" to binding.etUsername.text.toString().trim(),
                   "password" to binding.etPassword.text.toString()
               )
               loginViewModel.userLogin(map)
           }else{
               toast(getString(R.string.exception_error_network))
           }
       }
    }
}
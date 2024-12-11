package com.imaginato.homeworkmvvm.ui.dashboardActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.databinding.ActivityDashboardBinding
import com.imaginato.homeworkmvvm.ui.base.BaseActivity
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import com.imaginato.homeworkmvvm.ui.loginActivity.LoginActivity
import com.imaginato.homeworkmvvm.utility.Constant
import com.imaginato.homeworkmvvm.utility.SharedPrefHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {
     val dashboardViewmodel by viewModel<DashboardActivityViewmodel>()
    lateinit var binding: ActivityDashboardBinding
    override fun getBaseViewModel(): BaseViewModel  = dashboardViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard)

        initView()
        observe()
        getData()

    }

    private fun getData() {

        dashboardViewmodel.getUser(SharedPrefHelper.get(Constant.Pref_UserId))
    }

    private fun observe() {
        dashboardViewmodel.userLiveData.observe(this, Observer {
            binding.tvUserName.text = it
        })
    }

    private fun initView() {
        binding.btnLogout.setOnClickListener {
            SharedPrefHelper.save(Constant.Pref_IsLogin,false)
            Toast.makeText(this@DashboardActivity,"Logout",Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        }
    }
}
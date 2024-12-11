package com.imaginato.homeworkmvvm.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.JsonSyntaxException
import com.imaginato.homeworkmvvm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity: AppCompatActivity(),CoroutineScope {

    var progressDialog : CustomProgressDialog? = null
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    abstract fun getBaseViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.progressDialog = CustomProgressDialog(this)

        attachBaseObserver()
    }

    private fun attachBaseObserver() {

        getBaseViewModel()?.exceptionLiveData?.observe(this, Observer{
            progressDialogVisibility(false)
            it?.apply {
                when (this) {
                    is HttpException -> {
                        when (this.code()) {
                            HttpsURLConnection.HTTP_UNAUTHORIZED -> toast(getString(R.string.exception_error_unauthorized))
                            HttpsURLConnection.HTTP_FORBIDDEN -> toast(getString(R.string.exception_error_forbidden))
                            HttpsURLConnection.HTTP_INTERNAL_ERROR -> toast(getString(R.string.exception_error_server))
                            HttpsURLConnection.HTTP_BAD_REQUEST -> toast(getString(R.string.exception_error_bad_request))
                            else -> this.localizedMessage
                        }
                    }
                    is JsonSyntaxException -> {
                        toast(getString(R.string.exception_error_unparceble))
                    }
                    else -> {
//                        if (BuildConfig.DEBUG) {
                            toast(this.message!!)
//                        } else {
//                            toast(getString(R.string.something_went_wrong))
//                        }
                    }
                }
            }
        })
    }
    fun progressDialogVisibility(value : Boolean){
        if (value)  progressDialog?.show() else progressDialog?.hide()
    }
    fun toast(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
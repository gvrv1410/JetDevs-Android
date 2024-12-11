package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.demo.ApiServices
import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.LoginMainEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val loginApi: ApiServices) {

    suspend fun loginUser(params: Map<String,String>): LoginMainEntity {
        return withContext(Dispatchers.IO) {
            loginApi.login(params)
        }
    }
}
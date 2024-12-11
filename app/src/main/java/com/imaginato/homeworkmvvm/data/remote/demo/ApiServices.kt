package com.imaginato.homeworkmvvm.data.remote.demo

import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.LoginMainEntity
import com.imaginato.homeworkmvvm.utility.ApiConstant
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST(ApiConstant.ApiLogin)
    suspend fun login(@FieldMap params: Map<String,String>) : LoginMainEntity
}
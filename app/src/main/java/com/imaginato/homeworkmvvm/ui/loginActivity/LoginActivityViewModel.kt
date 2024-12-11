package com.imaginato.homeworkmvvm.ui.loginActivity

import androidx.lifecycle.MutableLiveData
import com.imaginato.homeworkmvvm.data.local.RoomDb.User
import com.imaginato.homeworkmvvm.data.local.RoomDb.UserDao
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.LoginMainEntity
import kotlinx.coroutines.launch

class LoginActivityViewModel (private val loginRepository: LoginRepository
                               ,private val userDao: UserDao) : BaseViewModel() {

     var loginLiveData : MutableLiveData<LoginMainEntity> = MutableLiveData()

    fun userLogin( params : Map<String,String>){

        launch{
            kotlin.runCatching {
                loginRepository.loginUser(params)
            }.fold({
                loginLiveData.postValue(it)
                userDao.insertUser(
                    User(
                        it.data.userId.toLong(), it.data.userName)
                )

            }, {
                exceptionLiveData.postValue(it)

            })

        }
    }


}
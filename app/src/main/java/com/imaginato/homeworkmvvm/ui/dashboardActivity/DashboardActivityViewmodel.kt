package com.imaginato.homeworkmvvm.ui.dashboardActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaginato.homeworkmvvm.data.local.RoomDb.User
import com.imaginato.homeworkmvvm.data.local.RoomDb.UserDao
import com.imaginato.homeworkmvvm.data.remote.dashboard.DashboardRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.LoginMainEntity
import kotlinx.coroutines.launch

class DashboardActivityViewmodel (var repository: DashboardRepository,var userDao: UserDao) : BaseViewModel() {
    var userLiveData : MutableLiveData<String> = MutableLiveData()


    fun getUser(id: String){
        launch {
            kotlin.runCatching {
                userDao.getUserById(id.toLong())
            }.fold({
                userLiveData.postValue(it)
            },{
                exceptionLiveData.postValue(it)

            })
        }
    }

}
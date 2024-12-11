package com.imaginato.homeworkmvvm.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.imaginato.homeworkmvvm.exts.LOG_TYPE_INFO
import com.imaginato.homeworkmvvm.exts.printLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//import org.koin.core.component.KoinComponent


open class BaseViewModel : ViewModel(), CoroutineScope {

    val exceptionLiveData: MutableLiveData<Throwable> = MutableLiveData()

    var job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
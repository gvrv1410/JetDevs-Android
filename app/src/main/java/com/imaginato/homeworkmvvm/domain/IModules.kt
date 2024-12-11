package com.imaginato.homeworkmvvm.domain

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.imaginato.homeworkmvvm.data.local.RoomDb.RoomDatabase
import com.imaginato.homeworkmvvm.data.local.RoomDb.UserDao
import com.imaginato.homeworkmvvm.data.remote.dashboard.DashboardRepository
import com.imaginato.homeworkmvvm.data.remote.demo.ApiServices
import com.imaginato.homeworkmvvm.data.remote.demo.DemoApi
import com.imaginato.homeworkmvvm.data.remote.demo.DemoDataRepository
import com.imaginato.homeworkmvvm.data.remote.demo.DemoRepository
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.dashboardActivity.DashboardActivityViewmodel
import com.imaginato.homeworkmvvm.ui.demo.MainActivityViewModel
import com.imaginato.homeworkmvvm.ui.loginActivity.LoginActivityViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://ifconfig.me/"
private const val BASE_URL2 = "http://private-222d3-homework5.apiary-mock.com/"

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val netModules = module {
    single { provideInterceptors() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideGson() }
}

val apiModules = module {
    single { provideDemoApi(get()) }
    single { provideApiServices(get()) }
}

val repositoryModules = module {
    single { provideDemoRepo(get()) }
    single { provideLoginRepo(get())  }
    single { provideDashboardRepo(get())  }
}

val viewModelModules = module {

    viewModel { MainActivityViewModel(get()) }
    viewModel { LoginActivityViewModel(get(), get()) }
    viewModel { DashboardActivityViewmodel(get(), get()) }
}

private fun provideDemoRepo(api: DemoApi): DemoRepository {
    return DemoDataRepository(api)
}
private fun provideLoginRepo(apiServices: ApiServices): LoginRepository{
    return LoginRepository(apiServices)
}
private fun provideDashboardRepo(apiServices: ApiServices):  DashboardRepository{
    return DashboardRepository(apiServices)
}
private fun provideApiServices(retrofit: Retrofit): ApiServices{
    return retrofit.create(ApiServices::class.java)
}

private fun provideDemoApi(retrofit: Retrofit): DemoApi = retrofit.create(DemoApi::class.java)

private fun provideDatabase(application: Application): RoomDatabase {
    return Room.databaseBuilder(application, RoomDatabase::class.java, "I-Database")
        .fallbackToDestructiveMigration(false)
        .allowMainThreadQueries()
        .build()
}

private fun provideDao(database: RoomDatabase): UserDao {
    return database.userDao
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
    clientBuilder.readTimeout(2, TimeUnit.MINUTES)
    clientBuilder.connectTimeout(2, TimeUnit.MINUTES)
    interceptors.forEach { clientBuilder.addInterceptor(it) }
    return clientBuilder.build()
}

private fun provideInterceptors(): ArrayList<Interceptor> {
    val interceptors = arrayListOf<Interceptor>()
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    interceptors.add(loggingInterceptor)
    return interceptors
}

fun provideGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}

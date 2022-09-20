package com.demo.auth

import android.app.Application
import android.content.Context
import com.demo.auth.di.component.AppComponent
import com.demo.auth.di.component.DaggerAppComponent
import com.demo.auth.di.module.AppModule

/**
 * Created by Jitendra on 19/09/22.
 **/
class AuthApp : Application() {

    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger(this)
    }

    private fun initDagger(context: Context) {
        mAppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context.applicationContext))
            .build()
    }

    fun getAppComponent(context: Context): AppComponent {
        if (!::mAppComponent.isInitialized) {
            initDagger(context)
        }
        return mAppComponent
    }


}
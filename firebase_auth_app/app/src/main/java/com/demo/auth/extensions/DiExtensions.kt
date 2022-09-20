package com.demo.auth.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import com.demo.auth.AuthApp
import com.demo.auth.di.component.AppComponent

/**
 * Created by Jitendra on 19/09/22.
 **/
val Context.appInjector: AppComponent
    get() {
        return (this.applicationContext as AuthApp).getAppComponent(this.applicationContext)
    }

val Fragment.appInjector: AppComponent
    get() {
        return (this.requireContext().applicationContext as AuthApp).getAppComponent(this.requireContext().applicationContext)
    }
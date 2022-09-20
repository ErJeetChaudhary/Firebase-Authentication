package com.demo.auth.di.component

import com.demo.auth.di.module.AppModule
import com.demo.auth.ui.*
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Jitendra on 19/09/22.
 **/
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: RegistrationActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: EditActivity)

    fun inject(activity: SignUpActivity)

}
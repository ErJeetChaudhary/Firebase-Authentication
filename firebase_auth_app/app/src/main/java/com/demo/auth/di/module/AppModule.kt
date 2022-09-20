package com.demo.auth.di.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.demo.auth.di.qualifiers.ApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Jitendra on 19/09/22.
 **/
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideHandler() = Handler(Looper.getMainLooper())

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

}
package com.demo.auth.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.auth.R
import com.demo.auth.databinding.ActivitySplashBinding
import com.demo.auth.extensions.appInjector
import com.demo.auth.extensions.launchActivity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding

    @Inject
    internal lateinit var mAuth: FirebaseAuth

    @Inject
    internal lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInjector.inject(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        mHandler.postDelayed({
            if (currentUser == null) {
                launchActivity<SignUpActivity>(finish = true)
            } else {
                launchActivity<MainActivity>(finish = true)
            }
        }, 2000)
    }
}
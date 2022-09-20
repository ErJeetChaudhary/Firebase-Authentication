package com.demo.auth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.auth.R
import com.demo.auth.databinding.ActivitySignupBinding
import com.demo.auth.extensions.appInjector
import com.demo.auth.extensions.launchActivity
import com.demo.auth.extensions.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySignupBinding

    private lateinit var mSignInResultLauncher: ActivityResultLauncher<Intent>

    @Inject
    internal lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInjector.inject(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        mBinding.activity = this
        registerSignInResult()
        setupGoogleLogin()
    }

    private fun registerSignInResult() {
        mSignInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (signInAccountTask.isSuccessful) {
                toast("Google SignIn Successful")
                try {
                    val account = signInAccountTask.getResult(ApiException::class.java)
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        mAuth.signInWithCredential(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    launchActivity<MainActivity>(finish = true) {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                } else {
                                    Log.e("SignUpActivity", "2. failed exception ${it.exception}")
                                }
                            }
                    }
                } catch (ex: Exception) {
                    Log.e("SignUpActivity", "exception $ex")
                }
            } else {
                Log.e("SignUpActivity", "1. failed exception ${signInAccountTask.exception}")
            }
        }
    }

    private fun setupGoogleLogin() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("591259511257-4jl3sdkidpat2fdgsgot2bm8a41ddkt9.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val signInClient = GoogleSignIn.getClient(this, signInOptions)
        mBinding.btnSignup.setOnClickListener {
            mSignInResultLauncher.launch(signInClient.signInIntent)
        }
    }
}
package com.demo.auth.ui

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.auth.R
import com.demo.auth.databinding.ActivityLoginBinding
import com.demo.auth.extensions.appInjector
import com.demo.auth.extensions.launchActivity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    @Inject
    internal lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInjector.inject(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.activity = this
    }

    fun onLoginClick(view: View) {
        view.isEnabled = false
        val email = mBinding.tilEmail.editText?.text?.toString()
        if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show()
            view.isEnabled = true
            return
        }

        val password = mBinding.tilPassword.editText?.text?.toString()
        if (password.isNullOrEmpty() || password.length < 6) {
            Toast.makeText(this, "Password must be greater or equalsTo six digits", Toast.LENGTH_LONG).show()
            view.isEnabled = true
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                    launchActivity<MainActivity>(finish = true)
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    view.isEnabled = true
                }
            }
    }

    fun onSignUpClick(view: View) {
        view.isEnabled = false
        launchActivity<RegistrationActivity>(finish = true)
    }
}
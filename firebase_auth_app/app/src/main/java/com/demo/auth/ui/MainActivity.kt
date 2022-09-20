package com.demo.auth.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.auth.R
import com.demo.auth.databinding.ActivityMainBinding
import com.demo.auth.extensions.appInjector
import com.demo.auth.extensions.launchActivity
import com.demo.auth.models.UserInfo
import com.demo.auth.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    @Inject
    internal lateinit var mAuth: FirebaseAuth

    @Inject
    internal lateinit var database: FirebaseDatabase

    private lateinit var mActivityResultLauncher: ActivityResultLauncher<Intent>

    private val viewModel by viewModels<HomeViewModel> { HomeViewModel.Factory(mAuth, database) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInjector.inject(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        registerActivityResult()
        updateUi()
        viewModel.userInfoLiveData.observe(this) { updateUi(it) }
        viewModel.fetchUserInfo()
    }

    private fun registerActivityResult() {
        mActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.fetchUserInfo()
            }
        }
    }

    private fun updateUi(userInfo: UserInfo? = null) {
        if (userInfo == null) {
            mBinding.tvUser.text = mAuth.currentUser?.email
        } else {
            mBinding.tvUser.text = userInfo.info
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                mActivityResultLauncher.launch(Intent(this, EditActivity::class.java))
            }
            R.id.action_logout -> {
                mAuth.signOut()
                launchActivity<LoginActivity>(finish = true)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
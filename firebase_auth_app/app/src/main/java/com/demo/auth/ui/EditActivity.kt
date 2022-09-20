package com.demo.auth.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.demo.auth.R
import com.demo.auth.databinding.ActivityEditBinding
import com.demo.auth.extensions.appInjector
import com.demo.auth.models.UserInfo
import com.demo.auth.viewmodels.EditViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class EditActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityEditBinding

    @Inject
    internal lateinit var mAuth: FirebaseAuth

    @Inject
    internal lateinit var database: FirebaseDatabase

    private val viewModel by viewModels<EditViewModel> { EditViewModel.Factory(mAuth, database) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appInjector.inject(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        mBinding.activity = this
        viewModel.userInfoLiveData.observe(this) { updateUi(it) }
        viewModel.loading.observe(this) { mBinding.progressBar.isVisible = it }
        viewModel.errorMessage.observe(this) {
            if (it != null) {
                mBinding.btnUpdate.isEnabled = true
                Toast.makeText(this@EditActivity, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.success.observe(this) {
            if (it) {
                Toast.makeText(this@EditActivity, "Successfully Added", Toast.LENGTH_LONG).show()
                setResult(RESULT_OK)
                finish()
            }
        }
        viewModel.fetchUserInfo()
    }

    private fun updateUi(userInfo: UserInfo? = null) {
        if (userInfo != null) {
            mBinding.tilName.editText?.setText(userInfo.name)
            mBinding.tilMobileNo.editText?.setText(userInfo.mobileNo)
            mBinding.tilAddress.editText?.setText(userInfo.address)
            if (userInfo.gender == "male") {
                mBinding.radioMale.isChecked = true
            } else if (userInfo.gender == "female") {
                mBinding.radioFemale.isChecked = true
            }
        }
    }

    fun onUpdateClick(view: View) {
        view.isEnabled = false
        val name = mBinding.tilName.editText?.text?.toString()?.trim()
        if (name.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_LONG).show()
            view.isEnabled = true
            return
        }

        val mobile = mBinding.tilMobileNo.editText?.text?.toString()?.trim()
        if (mobile.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Mobile No", Toast.LENGTH_LONG).show()
            view.isEnabled = true
            return
        }

        val address = mBinding.tilAddress.editText?.text?.toString()?.trim()
        if (address.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_LONG).show()
            view.isEnabled = true
            return
        }
        val gender = if (mBinding.radioGender.checkedRadioButtonId == R.id.radio_male) "male" else "female"
        val userInfo = UserInfo().apply {
            this.name = name
            this.mobileNo = mobile
            this.gender = gender
            this.address = address
        }
        viewModel.saveUserInfo(userInfo)
    }
}
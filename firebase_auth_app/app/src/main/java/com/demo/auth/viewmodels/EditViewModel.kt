package com.demo.auth.viewmodels

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.auth.models.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Jitendra on 20/09/22.
 **/
class EditViewModel(private val firebaseAuth: FirebaseAuth, private val database: FirebaseDatabase) : ViewModel() {

    val userInfoLiveData = MutableLiveData<UserInfo?>()

    val loading = MutableLiveData<Boolean>()

    val success = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String?>()

    fun fetchUserInfo() {
        val reference = database.getReference("UserInfo")
        val uid = firebaseAuth.currentUser?.uid ?: return
        val userInfoRef = reference.child(uid)
        userInfoRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(UserInfo::class.java)
                Log.e("MainActivity", "onDataChange $userInfo")
                userInfoLiveData.postValue(userInfo)
            }

            override fun onCancelled(error: DatabaseError) {
                userInfoLiveData.postValue(null)
            }
        })
    }

    fun saveUserInfo(userInfo: UserInfo) {
        loading.value = true
        val reference = FirebaseDatabase.getInstance().getReference("UserInfo")
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            loading.value = false
            errorMessage.value = "Something went wrong."
            return
        }
        val userInfoRef = reference.child(uid)
        userInfoRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userInfoRef.setValue(userInfo)
                loading.value = false
                success.value = true
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage.value = "Data insertion Failed"
            }
        })
    }

    class Factory(private val firebaseAuth: FirebaseAuth, private val database: FirebaseDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditViewModel(firebaseAuth, database) as T
        }
    }
}
package com.demo.auth.viewmodels

import android.util.Log
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
class HomeViewModel(private val firebaseAuth: FirebaseAuth, private val database: FirebaseDatabase) : ViewModel() {

    val userInfoLiveData = MutableLiveData<UserInfo?>()

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

    class Factory(private val firebaseAuth: FirebaseAuth, private val database: FirebaseDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(firebaseAuth, database) as T
        }
    }
}
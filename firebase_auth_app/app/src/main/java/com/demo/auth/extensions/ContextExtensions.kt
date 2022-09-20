package com.demo.auth.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Jitendra on 19/09/22.
 **/
inline fun <reified T : Any> newIntent(context: Context) = Intent(context, T::class.java)

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Activity.launchActivity(finish: Boolean = false, requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
    if (finish) {
        this.finish()
    }
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Fragment.launchActivity(finish: Boolean = false, requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(requireContext())
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
    if (finish)
        activity?.finish()
}

@SuppressLint("ObsoleteSdkInt")
inline fun <reified T : Any> Context.launchActivity(finish: Boolean = false, options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }

    if (finish) {
        if (this is Activity) {
            this.finish()
        }
    }
}
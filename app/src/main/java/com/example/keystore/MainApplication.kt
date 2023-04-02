package com.example.keystore

import android.app.Application
import com.example.keystore.utils.SharedPrefUtils

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefUtils.init(this)
    }

}
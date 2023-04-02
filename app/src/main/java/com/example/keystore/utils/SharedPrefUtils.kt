package com.example.keystore.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.keystore.constants.SharedPrefConstants.ENCRYPTED_TEXT
import com.example.keystore.constants.SharedPrefConstants.ENCRYPTION_IV


object SharedPrefUtils {

    private const val SHARED_PREF_NAME = "SharedPref"

    lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context){
        if(this::sharedPreferences.isInitialized.not()){
            sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    private fun saveString(key:String, value:String){
        with(sharedPreferences.edit()){
            putString(key, value)
            apply()
        }
    }

    private fun loadString(key:String):String{
        return sharedPreferences.getString(key,"") ?: ""
    }

    fun loadEncryptedString():String{
        return loadString(ENCRYPTED_TEXT)
    }

    fun saveEncryptedString(value:String){
        saveString(ENCRYPTED_TEXT,value)
    }

    fun loadEncryptionIv():String{
        return loadString(ENCRYPTION_IV)
    }

    fun saveEncryptionIv(encryptionIv:String){
        saveString(ENCRYPTION_IV, encryptionIv)
    }
}
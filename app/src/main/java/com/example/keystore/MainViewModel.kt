package com.example.keystore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.keystore.utils.KeyStoreUtils
import com.example.keystore.utils.SharedPrefUtils

class MainViewModel:ViewModel() {

    private var _savedEncryptedString = MutableLiveData("")
    val savedEncryptedString get() = _savedEncryptedString

    private var _decryptedString = MutableLiveData("")
    val decryptedString get() = _decryptedString

    init {
        _savedEncryptedString.value = SharedPrefUtils.loadEncryptedString()
    }

    fun encryptAndSaveString(value:String){
        val encryptedString = KeyStoreUtils.encrypt(value)
        SharedPrefUtils.saveEncryptedString(encryptedString)
        _savedEncryptedString.value = encryptedString
    }

    fun decryptString(){
        val decryptedString = KeyStoreUtils.decrypt()
        _decryptedString.value = decryptedString
    }

}
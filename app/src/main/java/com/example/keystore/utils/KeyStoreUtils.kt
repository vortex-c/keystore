package com.example.keystore.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.keystore.utils.SharedPrefUtils.loadEncryptedString
import com.example.keystore.utils.SharedPrefUtils.loadEncryptionIv
import com.example.keystore.utils.SharedPrefUtils.saveEncryptionIv
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

object KeyStoreUtils {
    private const val KEY_ALIAS = "myKey"
    private const val KEY_STORE_PROVIDER = "AndroidKeyStore"
    private const val ALGO_MODE_PADDING = "AES/GCM/NoPadding"

    private val keyStore by lazy { KeyStore.getInstance(ALGO_MODE_PADDING, KEY_STORE_PROVIDER) }
    private val keyGenerator =
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE_PROVIDER)
    private val keyGenParameterSpec by lazy {
        KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
    }
    private val key by lazy {
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private val cipherEncrypt by lazy<Cipher> {
        val cipher = Cipher.getInstance(ALGO_MODE_PADDING)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        saveEncryptionIv(cipher.iv.toString())
        return@lazy cipher
    }

    private val cipherDecrypt by lazy<Cipher> {
        val secreteKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
        val secretKey = secreteKeyEntry.secretKey
        val cipher = Cipher.getInstance(ALGO_MODE_PADDING)
        val encryptionIv = loadEncryptionIv().toByteArray()
        val gcmParamSpecs =     GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParamSpecs)
        return@lazy cipher
    }

    fun encrypt(data:String):String{
        val result =  cipherEncrypt.doFinal(data.toByteArray())
        return result.toString()
    }

    fun decrypt(data:String = loadEncryptedString()):String{
        return cipherDecrypt.doFinal(data.toByteArray()).toString()
    }



}
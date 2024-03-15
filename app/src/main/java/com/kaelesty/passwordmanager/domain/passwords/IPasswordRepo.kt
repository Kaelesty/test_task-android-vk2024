package com.kaelesty.passwordmanager.domain.passwords

import android.net.Uri
import androidx.lifecycle.LiveData

interface IPasswordRepo {

	suspend fun savePassword(password: String, url: String)

	fun getLogoUri(id: Long): Uri?

	fun getPasswords(): LiveData<List<Password>>
}
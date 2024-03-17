package com.kaelesty.passwordmanager.domain.masterkey

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface IMasterKeyRepo {

	suspend fun saveMasterKey(masterKey: String)

	fun getMasterKey(): LiveData<MasterKey>
}
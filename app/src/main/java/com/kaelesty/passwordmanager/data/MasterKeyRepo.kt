package com.kaelesty.passwordmanager.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kaelesty.passwordmanager.data.local.KeyDao
import com.kaelesty.passwordmanager.data.local.KeyDbModel
import com.kaelesty.passwordmanager.data.tools.CipherTool
import com.kaelesty.passwordmanager.domain.masterkey.IMasterKeyRepo
import com.kaelesty.passwordmanager.domain.masterkey.MasterKey
import javax.inject.Inject

class MasterKeyRepo @Inject constructor(
	val dao: KeyDao
): IMasterKeyRepo {

	override suspend fun saveMasterKey(masterKey: String) {
		dao.saveKey(
			KeyDbModel(
				type = "masterkey",
				value = CipherTool.encrypt(masterKey)
			)
		)
	}

	override fun getMasterKey(): LiveData<MasterKey> {
		return dao.getMasterkey().map {
			val mk = it?.value ?: ""
			if (mk.isNotEmpty()) {
				MasterKey.Exist(
					CipherTool.decrypt(mk).also {
						Log.d("MASTERKEY", it)
					}
				)
			}
			else {
				MasterKey.Initial()
			}
		}
	}
}
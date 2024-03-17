package com.kaelesty.passwordmanager.presentation.masterkey

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelesty.passwordmanager.domain.masterkey.GetMasterKeyUseCase
import com.kaelesty.passwordmanager.domain.masterkey.MasterKey
import com.kaelesty.passwordmanager.domain.masterkey.SaveMasterKeyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MasterKeyViewModel @Inject constructor(
	private val saveMasterKeyUseCase: SaveMasterKeyUseCase,
	private val getMasterKeyUseCase: GetMasterKeyUseCase,
): ViewModel() {

	fun validateMasterKey(masterKey: String): Boolean {
		return masterKey.length >= 8 && masterKey != masterKey.lowercase()
	}

	fun createMasterKey(masterKey: String) {
		viewModelScope.launch(Dispatchers.IO) {
			saveMasterKeyUseCase(masterKey)
		}
	}

	fun getMasterKey(): LiveData<MasterKey> = getMasterKeyUseCase()
}
package com.kaelesty.passwordmanager.domain.masterkey

import com.kaelesty.passwordmanager.domain.passwords.IPasswordRepo
import javax.inject.Inject

class SaveMasterKeyUseCase @Inject constructor(
	val repo: IMasterKeyRepo
) {
	suspend operator fun invoke(masterKey: String) = repo.saveMasterKey(masterKey)
}
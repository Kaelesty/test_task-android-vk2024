package com.kaelesty.passwordmanager.domain.passwords

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetPasswordsUseCase @Inject constructor(
	val repo: IPasswordRepo
) {
	operator fun invoke(): LiveData<List<Password>> = repo.getPasswords()
}
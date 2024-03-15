package com.kaelesty.passwordmanager.domain.passwords

import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
	private val repo: IPasswordRepo
) {

	suspend operator fun invoke(password: String, url: String) = repo.savePassword(password, url)
}
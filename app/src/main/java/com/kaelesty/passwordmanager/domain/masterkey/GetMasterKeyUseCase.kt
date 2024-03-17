package com.kaelesty.passwordmanager.domain.masterkey

import javax.inject.Inject

class GetMasterKeyUseCase @Inject constructor(
	private val repo: IMasterKeyRepo
) {

	operator fun invoke() = repo.getMasterKey()
}
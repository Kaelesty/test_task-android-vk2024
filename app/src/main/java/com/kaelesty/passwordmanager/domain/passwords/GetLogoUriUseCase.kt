package com.kaelesty.passwordmanager.domain.passwords

import javax.inject.Inject

class GetLogoUriUseCase @Inject constructor(
	val repo: IPasswordRepo
) {
	operator fun invoke(id: Long) = repo.getLogoUri(id)
}
package com.kaelesty.passwordmanager.data

import android.net.Uri
import com.kaelesty.passwordmanager.data.local.PasswordDbModel
import com.kaelesty.passwordmanager.domain.passwords.Password

object PasswordMapper {

	fun dbModel_ToDomain(dbModel: PasswordDbModel) = Password(
		id = dbModel.id,
		password = dbModel.password,
		url = dbModel.url,
		imagePath = dbModel.imagePath
	)
}
package com.kaelesty.passwordmanager.domain.passwords

import android.net.Uri

data class Password(
	val id: Long,
	val password: String,
	val url: String,
	val imagePath: String?
)
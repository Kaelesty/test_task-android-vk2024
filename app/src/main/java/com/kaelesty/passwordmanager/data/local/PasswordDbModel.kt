package com.kaelesty.passwordmanager.data.local

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "passwords'"
)
data class PasswordDbModel(
	@PrimaryKey(autoGenerate = true) val id: Long,
	val password: String,
	val url: String,
	val imagePath: String?
)
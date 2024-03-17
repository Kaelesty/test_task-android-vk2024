package com.kaelesty.passwordmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "keys"
)
data class KeyDbModel(
	@PrimaryKey val type: String,
	val value: String
)
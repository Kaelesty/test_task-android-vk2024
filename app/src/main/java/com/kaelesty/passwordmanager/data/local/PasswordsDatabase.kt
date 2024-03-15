package com.kaelesty.passwordmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
	entities = [PasswordDbModel::class],
	version = 1,
	exportSchema = false,
)
abstract class PasswordsDatabase(): RoomDatabase() {

	abstract fun dao(): PasswordsDao

}
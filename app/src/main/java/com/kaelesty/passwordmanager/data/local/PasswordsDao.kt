package com.kaelesty.passwordmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordsDao {

	@Insert
	suspend fun savePassword(dbModel: PasswordDbModel): Long

	@Query("SELECT * FROM `passwords'`")
	fun getPasswords(): LiveData<List<PasswordDbModel>>

	@Query("UPDATE `passwords'` SET imagePath = :imagePath WHERE id = :id")
	suspend fun setPasswordImagePath(id: Long, imagePath: String)
}
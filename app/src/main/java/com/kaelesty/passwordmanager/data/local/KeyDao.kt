package com.kaelesty.passwordmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KeyDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun saveKey(dbModel: KeyDbModel)

	@Query("SELECT * FROM keys WHERE type = 'masterkey'")
	fun getMasterkey(): LiveData<KeyDbModel?>
}
package com.kaelesty.passwordmanager

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kaelesty.passwordmanager.data.local.PasswordsDatabase
import com.kaelesty.passwordmanager.di.DaggerApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ModifiedApplication : Application() {

	val component by lazy {
		DaggerApplicationComponent.factory()
			.create(
				application = this,
				db = Room
					.databaseBuilder(
						this@ModifiedApplication,
						PasswordsDatabase::class.java,
						"passwords"
					)
					.build(),
			)
	}
}
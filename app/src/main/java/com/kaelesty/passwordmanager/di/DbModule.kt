package com.kaelesty.passwordmanager.di

import com.kaelesty.passwordmanager.data.local.PasswordsDao
import com.kaelesty.passwordmanager.data.local.PasswordsDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule {

	@Provides
	fun provideDao(db: PasswordsDatabase): PasswordsDao = db.dao()
}
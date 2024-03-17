package com.kaelesty.passwordmanager.di

import com.kaelesty.passwordmanager.data.local.KeyDao
import com.kaelesty.passwordmanager.data.local.PasswordsDao
import com.kaelesty.passwordmanager.data.local.PasswordsDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule {

	@Provides
	fun providePasswordsDao(db: PasswordsDatabase): PasswordsDao = db.passwordsDao()

	@Provides
	fun provideKeyDao(db: PasswordsDatabase): KeyDao = db.keyDao()
}
package com.kaelesty.passwordmanager.di

import com.kaelesty.passwordmanager.data.MasterKeyRepo
import com.kaelesty.passwordmanager.data.PasswordRepo
import com.kaelesty.passwordmanager.domain.masterkey.IMasterKeyRepo
import com.kaelesty.passwordmanager.domain.passwords.IPasswordRepo
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

	@Provides
	fun provideIPasswordRepo(impl: PasswordRepo): IPasswordRepo = impl

	@Provides
	fun provideIMasterKeyRepo(impl: MasterKeyRepo): IMasterKeyRepo = impl
}
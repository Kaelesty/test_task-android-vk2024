package com.kaelesty.passwordmanager.di

import com.kaelesty.passwordmanager.data.remote.ApiServiceFactory
import dagger.Module
import dagger.Provides

@Module
class RemoteDatasourceModule {

	@Provides
	fun provideApiService() = ApiServiceFactory.apiService
}
package com.kaelesty.passwordmanager.di

import androidx.lifecycle.ViewModel
import com.kaelesty.passwordmanager.presentation.masterkey.MasterKeyViewModel
import com.kaelesty.passwordmanager.presentation.passwordlist.PasswordListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

	@IntoMap
	@StringKey("PasswordListViewModel")
	@Binds
	fun bindsPasswordViewModel(impl: PasswordListViewModel): ViewModel

	@IntoMap
	@StringKey("MasterKeyViewModel")
	@Binds
	fun bindsMasterKeyViewModel(impl: MasterKeyViewModel): ViewModel
}
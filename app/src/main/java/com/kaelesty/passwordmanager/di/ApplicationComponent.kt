package com.kaelesty.passwordmanager.di

import android.app.Application
import com.kaelesty.passwordmanager.data.local.PasswordsDatabase
import com.kaelesty.passwordmanager.presentation.passwordlist.PasswordListActivity
import dagger.BindsInstance
import dagger.Component

@Component(
	modules = [
		RemoteDatasourceModule::class,
		ViewModelModule::class,
		DbModule::class,
		DomainModule::class,
	]
)
interface ApplicationComponent {

	fun inject(activity: PasswordListActivity)

	@Component.Factory
	interface ApplicationComponentFactory {

		fun create(
			@BindsInstance application: Application,
			@BindsInstance db: PasswordsDatabase
		): ApplicationComponent
	}
}
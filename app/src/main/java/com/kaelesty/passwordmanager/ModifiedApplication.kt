package com.kaelesty.passwordmanager

import android.app.Application
import com.kaelesty.passwordmanager.di.DaggerApplicationComponent

class ModifiedApplication: Application() {

	val component by lazy {
		DaggerApplicationComponent.factory()
			.create(
				application = this
			)
	}
}
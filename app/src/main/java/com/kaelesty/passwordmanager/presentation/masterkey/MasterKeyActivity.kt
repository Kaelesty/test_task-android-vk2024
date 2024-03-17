package com.kaelesty.passwordmanager.presentation.masterkey

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kaelesty.passwordmanager.ModifiedApplication
import com.kaelesty.passwordmanager.domain.masterkey.MasterKey
import com.kaelesty.passwordmanager.presentation.ViewModelFactory
import com.kaelesty.passwordmanager.presentation.masterkey.composables.MasterKeyScreen
import com.kaelesty.passwordmanager.presentation.passwordlist.PasswordListActivity
import com.kaelesty.passwordmanager.presentation.theme.PasswordManagerTheme
import javax.inject.Inject

class MasterKeyActivity : ComponentActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	override fun onCreate(savedInstanceState: Bundle?) {

		(application as ModifiedApplication)
			.component
			.inject(this@MasterKeyActivity)

		super.onCreate(savedInstanceState)

		val viewModel = viewModelFactory.create(MasterKeyViewModel::class.java)

		viewModel.getMasterKey().observe(this) {
			when (it) {
				is MasterKey.Initial -> {
					setContent {
						PasswordManagerTheme {
							MasterKeyScreen(
								viewModelFactory,
							)
						}
					}
				}
				is MasterKey.Exist -> {
					launchPasswordListActivity(it.value)
				}
			}
		}
	}

	private fun launchPasswordListActivity(masterKey: String) {
		startActivity(
			PasswordListActivity.newIntent(
				this@MasterKeyActivity,
				masterKey
			)
		)
		finish()
	}
}
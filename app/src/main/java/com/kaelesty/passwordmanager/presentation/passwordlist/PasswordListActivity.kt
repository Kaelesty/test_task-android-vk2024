package com.kaelesty.passwordmanager.presentation.passwordlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kaelesty.passwordmanager.ModifiedApplication
import com.kaelesty.passwordmanager.presentation.ViewModelFactory
import com.kaelesty.passwordmanager.presentation.passwordlist.composables.PasswordListScreen
import com.kaelesty.passwordmanager.ui.theme.PasswordManagerTheme
import javax.inject.Inject

class PasswordListActivity : ComponentActivity() {

	@Inject lateinit var viewModelFactory: ViewModelFactory

	override fun onCreate(savedInstanceState: Bundle?) {

		(application as ModifiedApplication)
			.component
			.inject(this@PasswordListActivity)

		super.onCreate(savedInstanceState)
		setContent {
			PasswordManagerTheme {
				PasswordListScreen(viewModelFactory = viewModelFactory)
			}
		}
	}
}
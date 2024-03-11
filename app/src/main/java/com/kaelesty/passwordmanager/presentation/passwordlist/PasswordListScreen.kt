package com.kaelesty.passwordmanager.presentation.passwordlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaelesty.passwordmanager.presentation.ViewModelFactory

@Composable
fun PasswordListScreen(
	viewModelFactory: ViewModelFactory
) {

	val viewModel: PasswordListViewModel = viewModel(factory = viewModelFactory)

	val html by viewModel.link.collectAsState()

	viewModel.load()


	Text(
		text = html,
		modifier = Modifier
			.fillMaxSize()
	)
}
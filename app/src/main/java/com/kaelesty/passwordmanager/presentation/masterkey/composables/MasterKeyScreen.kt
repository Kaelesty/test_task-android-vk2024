package com.kaelesty.passwordmanager.presentation.masterkey.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaelesty.passwordmanager.presentation.ViewModelFactory
import com.kaelesty.passwordmanager.presentation.masterkey.MasterKeyViewModel

@Composable
fun MasterKeyScreen(
	viewModelFactory: ViewModelFactory,
) {

	val viewModel: MasterKeyViewModel = viewModel(factory = viewModelFactory)


	MasterKeyInput(viewModel = viewModel)
}
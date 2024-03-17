package com.kaelesty.passwordmanager.presentation.passwordlist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaelesty.passwordmanager.presentation.ViewModelFactory
import com.kaelesty.passwordmanager.presentation.passwordlist.PasswordListViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun PasswordListScreen(
	viewModelFactory: ViewModelFactory,
	masterKey: String,
	onCopy: (String) -> Unit,
	onBiometricAttempt: () -> Unit,
	bioAuthSucceedFlow: Flow<Unit>
) {

	val viewModel: PasswordListViewModel = viewModel(factory = viewModelFactory)
	
	val passwords = viewModel.passwords.observeAsState(listOf())

	var addPasswordDialogState by rememberSaveable {
		mutableStateOf(false)
	}

	if (addPasswordDialogState) {
		AddPasswordDialog(
			onAccept = { password, url ->
				viewModel.savePassword(password, url)
				addPasswordDialogState = false
			},
			onDismiss = {
				addPasswordDialogState = false
			}
		)
	}

	LazyColumn(
		modifier = Modifier
			.fillMaxSize()
	) {
		items(passwords.value) { password ->
			PasswordCard(
				password = password,
				masterKey, onBiometricAttempt, onCopy, bioAuthSucceedFlow
			)
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(32.dp),
		horizontalAlignment = Alignment.End,
	) {
		Spacer(modifier = Modifier.weight(1f))
		FloatingActionButton(onClick = { addPasswordDialogState = true }) {
			Icon(
				Icons.Default.Add,
				contentDescription = "Add button",
			)
		}
	}
}
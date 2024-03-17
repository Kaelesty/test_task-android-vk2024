package com.kaelesty.passwordmanager.presentation.passwordlist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaelesty.passwordmanager.domain.passwords.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun ShowPasswordDialog(
	password: Password,
	onDismiss: () -> Unit,
	onBiometricAttempt: () -> Unit,
	masterKey: String,
	onCopy: (String) -> Unit,
	bioAuthSucceedFlow: Flow<Unit>
) {

	var showPassword by rememberSaveable {
		mutableStateOf(false)
	}

	val scope = CoroutineScope(Dispatchers.IO)
	LaunchedEffect(true) {
		scope.launch {
			bioAuthSucceedFlow.collect {
				showPassword = true
			}
		}
	}


	Dialog(
		onDismissRequest = onDismiss
	) {
		Card(
			modifier = Modifier
				.fillMaxWidth()
		) {
			Column(
				modifier = Modifier
					.padding(16.dp)
			) {

				Text(
					text = "Password of ${password.url}",
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold
				)
				if (showPassword) {
					PasswordMenu(
						password,
						onCopy = onCopy
					)
				} else {
					AccessMenu(
						onBiometricAttempt = onBiometricAttempt,
						onSuccess = {
							showPassword = true
						},
						masterKey = masterKey
					)
				}
			}
		}
	}
}

@Composable
fun PasswordMenu(
	password: Password,
	onCopy: (String) -> Unit,
) {
	Spacer(modifier = Modifier.height(8.dp))
	Text(
		password.password,
		fontSize = 18.sp
	)
	Spacer(modifier = Modifier.height(12.dp))
	OutlinedButton(
		onClick = { onCopy(password.password) },
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 65.dp)
	) {
		Text(text = "Copy")
	}
}

@Composable
fun AccessMenu(
	onBiometricAttempt: () -> Unit,
	onSuccess: () -> Unit,
	masterKey: String
) {

	var inputKey by rememberSaveable {
		mutableStateOf("")
	}

	TextField(
		value = inputKey,
		onValueChange = {
			if (it == masterKey) {
				onSuccess()
			}
			inputKey = it
		},
		label = {
			Text(
				text = "enter master key",
				color = Color.Gray
			)
		}
	)
	Spacer(modifier = Modifier.height(12.dp))
	OutlinedButton(
		onClick = { onBiometricAttempt() },
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 65.dp)
	) {
		Text(text = "Biometric Auth")
	}
}
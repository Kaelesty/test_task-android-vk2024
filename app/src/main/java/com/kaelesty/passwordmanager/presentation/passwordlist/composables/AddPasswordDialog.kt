package com.kaelesty.passwordmanager.presentation.passwordlist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaelesty.passwordmanager.presentation.theme.PasswordManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordDialog(
	onAccept: (String, String) -> Unit,
	onDismiss: () -> Unit
) {

	var passwordText by rememberSaveable {
		mutableStateOf("")
	}
	var urlText by rememberSaveable {
		mutableStateOf("")
	}
	var errorText by rememberSaveable {
		mutableStateOf("")
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

				val protocols = listOf(
					"https://", "http://"
				)

				var dropdownState by rememberSaveable {
					mutableStateOf(false)
				}
				var selectedText by rememberSaveable {
					mutableStateOf(protocols[0])
				}

				Text(
					text = "Add password",
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold
				)
				Spacer(modifier = Modifier.height(12.dp))
				OutlinedTextField(
					value = passwordText,
					onValueChange = {
						passwordText = it
						errorText = ""
					},
					label = { Text("Password") },
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Text,
						autoCorrect = false,
						imeAction = ImeAction.None,
						capitalization = KeyboardCapitalization.None
					)
				)



				Row(
					verticalAlignment = Alignment.CenterVertically
				) {

					ExposedDropdownMenuBox(
						expanded = dropdownState,
						onExpandedChange = { dropdownState = !dropdownState },
						modifier = Modifier
							.width(100.dp)
					) {
						TextField(
							value = selectedText,
							onValueChange = {},
							readOnly = true,
							//trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownState) },
							modifier = Modifier.menuAnchor(),
						)
						ExposedDropdownMenu(
							expanded = dropdownState,
							onDismissRequest = { dropdownState = false }
						) {
							protocols.forEach { s ->
								DropdownMenuItem(
									text = { Text(s) },
									onClick = {
										selectedText = s
										dropdownState = false
									}
								)
							}
						}
					}
					Spacer(Modifier.width(10.dp))
					OutlinedTextField(
						value = urlText,
						onValueChange = {
							urlText = it
							errorText = ""
						},
						label = { Text("URL") },
						modifier = Modifier
							.width(170.dp),
						keyboardOptions = KeyboardOptions(
							keyboardType = KeyboardType.Text,
							autoCorrect = false,
							imeAction = ImeAction.None,
							capitalization = KeyboardCapitalization.None
						)
					)
				}
				Text(
					text = errorText,
					color = Color.Red,
				)
				Spacer(modifier = Modifier.height(8.dp))
				Button(
					onClick = {
						if (passwordText.isEmpty()) {
							errorText = "Password can't be empty!"
						}
						else if (urlText.isEmpty()) {
							errorText = "URL can't be empty"
						}
						else {
							onAccept(passwordText.trim(), (selectedText + urlText).trim())
						}
					},
					modifier = Modifier
						.fillMaxWidth()
				) {
					Text(text = "Save")
				}
			}
		}
	}
}

@Preview
@Composable
fun PreviewAddPasswordDialog() {
	PasswordManagerTheme(darkTheme = true) {
		AddPasswordDialog(onAccept = { password, url -> }, onDismiss = {})
	}
}
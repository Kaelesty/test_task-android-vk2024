package com.kaelesty.passwordmanager.presentation.masterkey.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaelesty.passwordmanager.R
import com.kaelesty.passwordmanager.presentation.masterkey.MasterKeyViewModel

@Composable
fun MasterKeyInput(
	viewModel: MasterKeyViewModel
) {

	var masterKeyInput by rememberSaveable {
		mutableStateOf("")
	}
	var error by rememberSaveable {
		mutableStateOf("")
	}

	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(modifier = Modifier.weight(1f))
		Card(
			modifier = Modifier
				.padding(horizontal = 16.dp)
		) {
			Column(
				modifier = Modifier
					.padding(16.dp),
			) {
				Text(
					text = stringResource(R.string.before_starting_work),
					fontWeight = FontWeight.Bold,
					fontSize = 20.sp
				)
				Spacer(modifier = Modifier.size(4.dp))
				Text(
					text = stringResource(R.string.it_is_necessary_to_create_a_master_key_that_will_be_used_to_access_passwords),
					fontSize = 16.sp,
					color = Color.Gray
				)
				Spacer(modifier = Modifier.size(2.dp))
				Text(
					text = stringResource(R.string.minimum_8_characters),
					fontSize = 16.sp,
					color = Color.Gray
				)
				TextField(
					value = masterKeyInput,
					onValueChange = {
						masterKeyInput = it
						error = ""
					},
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 64.dp),
					singleLine = true
				)
				Spacer(modifier = Modifier.size(4.dp))
				OutlinedButton(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 64.dp),
					onClick = {
						if (viewModel.validateMasterKey(masterKeyInput)) {
							viewModel.createMasterKey(masterKeyInput)
						} else {
							error = "The master key is in an incorrect format"
						}
					}) {
					Text(text = "Create master-key")
				}
				if (error.isNotEmpty()) {
					Text(
						text = error,
						fontSize = 16.sp,
						color = Color.Red
					)
				}
			}
		}
		Spacer(modifier = Modifier.weight(1f))
	}
}
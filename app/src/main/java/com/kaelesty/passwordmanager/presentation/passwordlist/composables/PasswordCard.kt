package com.kaelesty.passwordmanager.presentation.passwordlist.composables

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.kaelesty.passwordmanager.domain.passwords.Password
import kotlinx.coroutines.flow.Flow

@Composable
fun PasswordCard(
	password: Password,
	masterKey: String,
	onBiometricAttempt: () -> Unit,
	onCopy: (String) -> Unit,
	bioAuthSucceedFlow: Flow<Unit>
) {

	var dialogState by rememberSaveable {
		mutableStateOf(false)
	}

	if (dialogState) {
		ShowPasswordDialog(
			password = password,
			onDismiss = { dialogState = false },
			masterKey = masterKey,
			onBiometricAttempt = onBiometricAttempt,
			onCopy = onCopy,
			bioAuthSucceedFlow = bioAuthSucceedFlow
		)
	}

	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(4.dp)
			.clickable {
				dialogState = true
			}
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			val imagePath = password.imagePath
			if (imagePath != null) {
				if (imagePath.endsWith(".svg")) {
					AsyncImage(
						model = ImageRequest.Builder(LocalContext.current)
							.data(Uri.parse(imagePath))
							.decoderFactory(SvgDecoder.Factory())
							.build(),
						contentDescription = "Website Logo",
						modifier = Modifier
							.size(32.dp)
							.padding(8.dp)
					)
				}
				else {
					AsyncImage(
						model = Uri.parse(imagePath),
						contentDescription = "Website Logo",
						modifier = Modifier
							.size(32.dp)
							.padding(8.dp)
					)
				}
			}
			else {
				Box(modifier = Modifier
					.size(32.dp)
					.padding(8.dp))
			}
			Text(
				text = password.url
			)
		}
	}
}
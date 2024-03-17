package com.kaelesty.passwordmanager.presentation.passwordlist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.kaelesty.passwordmanager.ModifiedApplication
import com.kaelesty.passwordmanager.presentation.ViewModelFactory
import com.kaelesty.passwordmanager.presentation.passwordlist.composables.PasswordListScreen
import com.kaelesty.passwordmanager.presentation.theme.PasswordManagerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordListActivity : FragmentActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val scope = CoroutineScope(Dispatchers.IO)

	private val _bioAuthSucceedFlow = MutableSharedFlow<Unit>()
	val bioAuthSucceedFlow: SharedFlow<Unit> get() = _bioAuthSucceedFlow

	override fun onCreate(savedInstanceState: Bundle?) {

		(application as ModifiedApplication)
			.component
			.inject(this@PasswordListActivity)

		val masterKey = intent.getStringExtra(MASTERKEY_EXTRA_KEY)


		super.onCreate(savedInstanceState)
		setContent {
			PasswordManagerTheme {
				PasswordListScreen(
					viewModelFactory = viewModelFactory,
					masterKey = masterKey?: "",
					onBiometricAttempt = {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							biometricAuth()
						} else {
							false
						}
					},
					onCopy = {
						copyTextToClipboard(it)
					},
					bioAuthSucceedFlow = bioAuthSucceedFlow
				)
			}
		}
	}

	private fun copyTextToClipboard(text: String) {
		val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
		val clipData = ClipData.newPlainText("text", text)
		clipboardManager.setPrimaryClip(clipData)
		Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_LONG).show()
	}

	@RequiresApi(Build.VERSION_CODES.Q)
	private fun biometricAuth() {

		if (!canDoBioAuth()) {
			return
		}

		val executor = ContextCompat.getMainExecutor(this)
		val biometricPrompt = BiometricPrompt(
			this, executor,
			object : BiometricPrompt.AuthenticationCallback() {
				override fun onAuthenticationError(
					errorCode: Int,
					errString: CharSequence
				) {
					super.onAuthenticationError(errorCode, errString)
					Toast.makeText(
						applicationContext,
						"Authentication error: $errString", Toast.LENGTH_SHORT
					)
						.show()
				}

				override fun onAuthenticationSucceeded(
					result: BiometricPrompt.AuthenticationResult
				) {
					super.onAuthenticationSucceeded(result)
					Toast.makeText(
						applicationContext,
						"Authentication succeeded!", Toast.LENGTH_SHORT
					)
						.show()
					scope.launch {
						_bioAuthSucceedFlow.emit(Unit)
					}
				}

				override fun onAuthenticationFailed() {
					super.onAuthenticationFailed()
					Toast.makeText(
						applicationContext, "Authentication failed",
						Toast.LENGTH_SHORT
					)
						.show()
				}
			})

		val promptInfo = BiometricPrompt.PromptInfo.Builder()
			.setTitle("Password Manager")
			.setSubtitle("Biometric access to password")
			.setNegativeButtonText("CANCEL")
			.build()

		biometricPrompt.authenticate(promptInfo)
	}

	@RequiresApi(Build.VERSION_CODES.Q)
	private fun canDoBioAuth(): Boolean {
		val biometricManager = BiometricManager.from(this)
		return biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
	}

	companion object {

		const val MASTERKEY_EXTRA_KEY = "masterkey_"

		fun newIntent(context: Context, masterKey: String) = Intent(
			context,
			PasswordListActivity::class.java
		).apply {
			putExtra(MASTERKEY_EXTRA_KEY, masterKey)
		}
	}
}
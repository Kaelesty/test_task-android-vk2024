package com.kaelesty.passwordmanager.presentation.passwordlist

import android.text.Html
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.newStringBuilder
import com.kaelesty.passwordmanager.data.remote.ApiService
import com.kaelesty.passwordmanager.domain.passwords.GetLogoUriUseCase
import com.kaelesty.passwordmanager.domain.passwords.GetPasswordsUseCase
import com.kaelesty.passwordmanager.domain.passwords.Password
import com.kaelesty.passwordmanager.domain.passwords.SavePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordListViewModel @Inject constructor(
	private val savePasswordUseCase: SavePasswordUseCase,
	private val getPasswordsUseCase: GetPasswordsUseCase,
	private val getLogoUriUseCase: GetLogoUriUseCase,
): ViewModel() {

	val passwords: LiveData<List<Password>> = getPasswordsUseCase()

	fun savePassword(password: String, url: String) {
		viewModelScope.launch(Dispatchers.IO) {
			savePasswordUseCase(password, url)
		}
	}

	fun getLogoUri(id: Long) = getLogoUriUseCase(id)

}
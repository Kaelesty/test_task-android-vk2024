package com.kaelesty.passwordmanager.presentation.passwordlist

import android.text.Html
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.newStringBuilder
import com.kaelesty.passwordmanager.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PasswordListViewModel @Inject constructor(
	private val apiService: ApiService
): ViewModel() {

	private val _link = MutableStateFlow("")
	val link: StateFlow<String> get() = _link


	fun load() {
		viewModelScope.launch(Dispatchers.IO) {
			val html = apiService.getHTML("https://www.enterprisedb.com/postgres-tutorials/how-use-postgresql-django").string()
			_link.emit(
				getLink(html)
			)
		}
	}

	private fun getLink(html: String): String {
		html.split("\n").forEach { string ->
			val newString = string.trimStart()
			if (newString.startsWith("<link")) {

				val params = string.split(" ")
				if ("rel=\"icon\"" in params) {

					params.forEach { param ->

						if (param.startsWith("href")) {

							return param.substring(
								startIndex = 6,
								endIndex = param.length - 1
							)
						}
					}
				}
			}
		}
		return "not found"
	}
}
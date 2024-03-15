package com.kaelesty.passwordmanager.data

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kaelesty.passwordmanager.data.local.PasswordDbModel
import com.kaelesty.passwordmanager.data.local.PasswordsDao
import com.kaelesty.passwordmanager.data.remote.ApiService
import com.kaelesty.passwordmanager.data.remote.ApiServiceFactory
import com.kaelesty.passwordmanager.domain.passwords.IPasswordRepo
import com.kaelesty.passwordmanager.domain.passwords.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class PasswordRepo @Inject constructor(
	private val apiService: ApiService,
	private val dao: PasswordsDao,
	private val application: Application,
) : IPasswordRepo {

//	init {
//		CoroutineScope(Dispatchers.IO).launch {
//			loadLogo(-1, "https://github.githubassets.com/favicons/favicon.svg")
//		}
//	}

	override suspend fun savePassword(password: String, websiteUrl: String) {
		val id = dao.savePassword(
			PasswordDbModel(
				id = 0,
				password = password,
				url = websiteUrl,
				null
			)
		)
		try {
			val logoUrl = getLogoUrl(websiteUrl)
			Log.d("PasswordRepo.kt", "website url: $websiteUrl")
			Log.d("PasswordRepo.kt", "logo url: $logoUrl")
			loadLogo(id, logoUrl, websiteUrl)?.let {
				dao.setPasswordImagePath(id, it)
			}
		} catch (e: Exception) {
			Log.d("PasswordRepo.kt", e.message.toString())
		}
	}

	suspend fun getLogoUrl(url: String) = getLink(apiService.getHTML(url).string())

	private fun getLink(html: String): String {
		html.split("<").forEach { string ->
			val newString = string.trimStart()
			if (newString.startsWith("link")) {

				val params = string.split(" ")
				if (
					newString.contains("rel=\"icon\"" ) ||
					newString.contains("rel=\"shortcut icon\"") ||
					newString.contains("rel=\"apple-touch-icon\"")
				) {

					params.forEach { param ->

						if (param.startsWith("href")) {
							var link = param.substring(
								startIndex = 6,
								endIndex = param.indexOfLast { it == '"' }
							)
							return link
						}
					}
				}
			}
		}
		return "not found"
	}

	override fun getPasswords(): LiveData<List<Password>> {

		return dao.getPasswords().map { list ->
			list.map {
				PasswordMapper.dbModel_ToDomain(it)
			}
		}
	}

	override fun getLogoUri(id: Long): Uri? {
		return try {
			val fileName = "/$id.png"
			val path = application.filesDir.absolutePath + fileName
			File(path).toUri()
		} catch (e: Exception) {
			Log.d("PasswordRepo.kt", e.message.toString())
			null
		}
	}

	private suspend fun loadLogo(id: Long, logoUrl: String, websiteUrl: String): String? {
		try {
			val response = if (logoUrl.contains("http")) {
				Log.d("PasswordRepo.kt", "downloading: ${logoUrl}")
				val path = logoUrl.split("/")
				val baseUrl = path.subList(0, path.size - 1)
					.joinToString("/") + "/"
				val requestUrl = path[path.size - 1]
				ApiServiceFactory
					.getApiService(baseUrl)
					.downloadLogo(requestUrl)
			} else {
				Log.d("PasswordRepo.kt", "downloading: ${websiteUrl + logoUrl}")
				ApiServiceFactory.getApiService(websiteUrl).downloadLogo(websiteUrl + logoUrl)
			}
			val body = response.body() ?: throw IOException("Empty body!")
			val fileExtension = if (logoUrl.contains("?")) {
				logoUrl.split("?").first().split(".").last()
			} else {
				logoUrl.split(".").last()
			}
			return saveFile(body, id, fileExtension)
		} catch (e: Exception) {
			Log.d("PasswordRepo.kt", e.message.toString())
			return null
		}
	}

	private fun saveFile(body: ResponseBody, id: Long, extension: String): String? {
		var input: InputStream? = null
		try {
			input = body.byteStream()
			val fileName = "/$id.$extension"
			val pathWhereYouWantToSaveFile = application.filesDir.absolutePath + fileName
			val fos = FileOutputStream(pathWhereYouWantToSaveFile)
			fos.use { output ->
				val buffer = ByteArray(4 * 1024)
				var read: Int
				while (input.read(buffer).also { read = it } != - 1) {
					output.write(buffer, 0, read)
				}

				output.flush()

			}
			return application.filesDir.absolutePath + fileName
		} catch (exception: Exception) {
			Log.e("PasswordRepo.kt", exception.toString())
			return null
		} finally {
			input?.close()
		}
	}
}
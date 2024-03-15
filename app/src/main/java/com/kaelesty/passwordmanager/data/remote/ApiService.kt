package com.kaelesty.passwordmanager.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {

	@GET
	suspend fun getHTML(@Url url: String): ResponseBody

	@Streaming
	@GET
	suspend fun downloadLogo(@Url url: String): Response<ResponseBody>
}
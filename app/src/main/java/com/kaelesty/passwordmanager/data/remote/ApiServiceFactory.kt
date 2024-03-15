package com.kaelesty.passwordmanager.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {

	private const val BASE_URL = "https://github.com/"

	val apiService: ApiService = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(ApiService::class.java)

	fun getApiService(url: String) = Retrofit.Builder()
		.baseUrl(url)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(ApiService::class.java)
}
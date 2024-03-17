package com.kaelesty.passwordmanager.domain.masterkey

sealed class MasterKey {

	class Initial: MasterKey()

	class Exist(val value: String): MasterKey()
}
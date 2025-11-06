package com.passman.prefsHelper

interface IEncryptedPreferenceHelper {
    fun setIntroSeen(isIntroSeen: Boolean)
    fun isIntroSeen(): Boolean?

    fun setUserRegistered(isRegistrationRequied: Boolean)
    fun isUserRegistered(): Boolean?


}
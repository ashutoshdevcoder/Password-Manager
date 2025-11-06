package com.passman.viewModel

import androidx.lifecycle.ViewModel
import com.passman.helper.isTrue
import com.passman.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val iPreferenceHelper: IEncryptedPreferenceHelper) :
    ViewModel() {
    val _redirectToLogin= MutableStateFlow(false)
    val redirectToLogin: StateFlow<Boolean> = _redirectToLogin.asStateFlow()

    init {
        _redirectToLogin.value = iPreferenceHelper.isIntroSeen().isTrue()
    }


}
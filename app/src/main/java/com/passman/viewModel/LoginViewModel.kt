package com.passman.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.passman.database.UserInfoDao
import com.passman.helper.isTrue
import com.passman.models.UserInfo
import com.passman.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val iPreferenceHelper: IEncryptedPreferenceHelper, private val userInfoDao: UserInfoDao):ViewModel(){


    private val _isFirstTimeUserState = MutableStateFlow<Boolean?>(null)
     val isUserRegisteredState : StateFlow<Boolean?> = _isFirstTimeUserState

    private val _isUserExist = MutableStateFlow<Boolean?>(null)
     val isUserExist : StateFlow<Boolean?> = _isUserExist

    init {
        _isFirstTimeUserState.value = iPreferenceHelper.isUserRegistered().isTrue()
    }

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.LoginUserEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                   _isUserExist.value = userInfoDao.isUserExists(event.password)
                }
            }
            is LoginEvent.RegisterUserEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userInfoDao.insertUserInfo(event.userInfo)
                    launch(Dispatchers.Main) {
                        iPreferenceHelper.setUserRegistered(true)
                        _isFirstTimeUserState.value = true
                    }
                }
            }
        }
    }

}

sealed interface LoginEvent{
    data class RegisterUserEvent(val userInfo: UserInfo):LoginEvent
    data class LoginUserEvent(val password:String):LoginEvent
}
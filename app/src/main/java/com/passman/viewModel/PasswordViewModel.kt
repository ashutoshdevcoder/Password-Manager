package com.passman.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.passman.database.PasswordInfoDao
import com.passman.models.PasswordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(private val passwordInfoDao: PasswordInfoDao):ViewModel() {

    private val _getPasswordInfoState = MutableStateFlow<List<PasswordInfo>?>(null)
    val getPasswordInfoState : StateFlow<List<PasswordInfo>?> = _getPasswordInfoState

    fun onEvent(event: PasswordEvent){
        when(event){
           is PasswordEvent.GetPasswordList -> {
               viewModelScope.launch(Dispatchers.IO) {
                   passwordInfoDao.getPasswordLiveData().collectLatest {
                       _getPasswordInfoState.value = it
                   }
               }
           }

            is PasswordEvent.DeletePasswordItem -> {
                viewModelScope.launch (Dispatchers.IO){
                    passwordInfoDao.deletePasswordData(event.id)
                    onEvent(PasswordEvent.GetPasswordList)
                }

            }
        }
    }

}

sealed interface PasswordEvent{
    data object GetPasswordList:PasswordEvent
    data class DeletePasswordItem(val id:Int):PasswordEvent
}
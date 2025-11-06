package com.passman.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.passman.database.CardInfoDao
import com.passman.database.PasswordInfoDao
import com.passman.helper.logit
import com.passman.models.CardInfo
import com.passman.models.PasswordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveInfoViewModel @Inject constructor(
    private val passwordInfoDao: PasswordInfoDao,
    private val cardInfoDao: CardInfoDao
) : ViewModel() {

    private val _getCardInfoState = MutableStateFlow<CardInfo?>(null)
    val getCardInfoState: StateFlow<CardInfo?> = _getCardInfoState
    private val _getPasswordInfoState = MutableStateFlow<PasswordInfo?>(null)
    val getPasswordInfoState: StateFlow<PasswordInfo?> = _getPasswordInfoState

    fun onEvent(event: SaveInfoEvent) {
        when (event) {
            is SaveInfoEvent.GetCardInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cardInfoDao.getCardInfo(event.id).collectLatest {
                        _getCardInfoState.value = it
                    }
                }

            }

            is SaveInfoEvent.GetPasswordInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    passwordInfoDao.getPassword(event.id).collectLatest {
                        _getPasswordInfoState.value = it
                        logit(it)
                    }
                }
            }

            is SaveInfoEvent.SaveCardInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cardInfoDao.insertCardInfo(event.cardInfo)
                }

            }

            is SaveInfoEvent.SavePasswordInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    passwordInfoDao.insertPasswordData(event.passwordInfo)
                }
            }
        }
    }
}

sealed interface SaveInfoEvent {
    data class SaveCardInfoEvent(val cardInfo: CardInfo) : SaveInfoEvent
    data class SavePasswordInfoEvent(val passwordInfo: PasswordInfo) : SaveInfoEvent
    data class GetCardInfoEvent(val id: Int) : SaveInfoEvent
    data class GetPasswordInfoEvent(val id: Int) : SaveInfoEvent
}
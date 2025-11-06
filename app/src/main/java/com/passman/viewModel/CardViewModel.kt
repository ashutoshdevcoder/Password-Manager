package com.passman.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.passman.database.CardInfoDao
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
class CardViewModel@Inject constructor(private val cardInfoDao: CardInfoDao): ViewModel() {
    private val _getCardInfoState = MutableStateFlow<List<CardInfo>?>(null)
    val getCardInfoState : StateFlow<List<CardInfo>?> = _getCardInfoState

    fun onEvent(event: CardEvent){
        when(event){
            is CardEvent.GetCardList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    cardInfoDao.getCardInfoLiveData().collectLatest {
                        _getCardInfoState.value = it
                    }
                }
            }

            is CardEvent.DeleteCardItem -> {
                viewModelScope.launch (Dispatchers.IO){
                    cardInfoDao.deleteCardInfo(event.id)
                    onEvent(CardEvent.GetCardList)
                }

            }
        }
    }
}

sealed interface CardEvent{
    data object GetCardList:CardEvent
    data class DeleteCardItem(val id:Int):CardEvent
}
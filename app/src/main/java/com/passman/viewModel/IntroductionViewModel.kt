package com.passman.viewModel

import androidx.lifecycle.ViewModel
import com.passman.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(private val iPreferenceHelper: IEncryptedPreferenceHelper) :
    ViewModel() {

        fun onEvent(event: IntroEvent){
            when(event){
                is IntroEvent.SetIntroScreenSeen -> {
                    iPreferenceHelper.setIntroSeen(event.isIntro)
                }
            }
        }
}
sealed interface IntroEvent{
    data class SetIntroScreenSeen(val isIntro:Boolean):IntroEvent
}
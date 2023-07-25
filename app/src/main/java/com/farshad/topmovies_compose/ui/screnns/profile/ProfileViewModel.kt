package com.farshad.topmovies_compose.ui.screnns.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.model.network.UserRegisteredModel
import com.farshad.topmovies_compose.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
):ViewModel() {

    init {
        getUserInfo()
    }


    private val _userInfoFlow = MutableStateFlow<UserRegisteredModel?>(null)
    val userInfoFlow= _userInfoFlow.asStateFlow()



    private fun getUserInfo(){
        viewModelScope.launch {
            val response= repository.getUserInfo()
            if (response != null) _userInfoFlow.emit(response)
        }
    }



}
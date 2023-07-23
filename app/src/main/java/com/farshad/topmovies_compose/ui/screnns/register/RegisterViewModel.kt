package com.farshad.topmovies_compose.ui.screnns.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.model.network.RegisterPostBody
import com.farshad.topmovies_compose.data.repository.UserRepository
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _registerUserFlow = Channel<RegisterResponseModel>()
    val registerUserFlow = _registerUserFlow.receiveAsFlow()

    private val _validationMutableFlow= Channel<RegisterFieldValidationModel>()
    val validationFlow = _validationMutableFlow.receiveAsFlow()

    private fun registerUser(user : RegisterPostBody){
        viewModelScope.launch {
            val response=repository.registerUser(user)
            _registerUserFlow.send(response)
        }
    }



    fun validate(userName:String, email:String, password:String ) {
        val userNameB = userName.trim()
        val emailB = email.trim()
        val passwordB = password.trim()

        viewModelScope.launch {
            when{
                userNameB.isEmpty() -> {
                    _validationMutableFlow.send(
                        RegisterFieldValidationModel(userName = "please enter valid user name")
                    )
                    return@launch
                }
                emailB.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    _validationMutableFlow.send(
                        RegisterFieldValidationModel(email = "please enter a valid email")
                    )
                    return@launch
                }
                passwordB.isEmpty() -> {
                    _validationMutableFlow.send(
                        RegisterFieldValidationModel(password = "please enter a valid password")
                    )
                    return@launch
                }

                else ->{
                    _validationMutableFlow.send(
                        RegisterFieldValidationModel(
                            userName = null,
                            email = null,
                            password = null
                        )
                    )

                    registerUser(
                        user = RegisterPostBody(
                            name = userNameB ,
                            email = emailB,
                            password =passwordB,
                        )
                    )

                }
            }
        }


    }



}
package com.farshad.topmovies_compose.ui.screnns.login


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.repository.UserRepository
import com.farshad.topmovies_compose.ui.screnns.login.model.LoginFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.login.model.LoginResponseModel
import com.farshad.topmovies_compose.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val resourcesProvider: ResourcesProvider
): ViewModel() {

    private val _loginUserFlow = Channel<LoginResponseModel>()
    val loginUserFlow = _loginUserFlow.receiveAsFlow()


    private val _validationFlow= Channel<LoginFieldValidationModel>()
    val validationFlow = _validationFlow.receiveAsFlow()



     private fun loginUser(email: String, password: String){
        viewModelScope.launch {
            val response=repository.loginUser(email,password)
            _loginUserFlow.send(response)
        }
    }



    fun validate(email : String, password : String, )=
        viewModelScope.launch {

            val emailT = email.trim()
            val passwordT = password.trim()

            when{
                emailT.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    _validationFlow.send(
                        LoginFieldValidationModel(email = resourcesProvider.getString(R.string.please_enter_a_valid_email))
                    )

                }
                passwordT.isEmpty() -> {
                    _validationFlow.send(
                        LoginFieldValidationModel(password = resourcesProvider.getString(R.string.please_enter_a_valid_password))
                    )
                }

                else -> {
                    _validationFlow.send(
                        LoginFieldValidationModel(
                            email = null,
                            password = null,
                            buttonLoading = true
                        )
                    )

                    loginUser(emailT , passwordT)
                }
            }

        }








}
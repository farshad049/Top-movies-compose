package com.farshad.topmovies_compose.ui.screnns.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.common.LottieHeader
import com.farshad.topmovies_compose.ui.screnns.common.MyButton
import com.farshad.topmovies_compose.ui.screnns.common.MyTextField
import com.farshad.topmovies_compose.ui.screnns.login.model.LoginResponseModel
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterResponseModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.rememberMessageBarState

@Composable
fun RegisterScreenWithViewModel(
    navController: NavHostController,
    registerViewModel: RegisterViewModel= hiltViewModel()
) {

    val context = LocalContext.current

    val validation by registerViewModel.validationFlow.collectAsStateWithLifecycle(initialValue = RegisterFieldValidationModel())

    val registerResponse by registerViewModel.registerUserFlow.collectAsStateWithLifecycle(initialValue = RegisterResponseModel.Loading)


        RegisterScreen(
            error = validation,
            onButtonClick = { userName, email, password ->
                registerViewModel.validate(
                    userName = userName,
                    email = email,
                    password = password,
                )
            },
            onLogonTxtClick = { navController.navigate(Screens.Login.route) }
        )



    when(registerResponse){
        is RegisterResponseModel.Success ->{
            LaunchedEffect(key1 = registerResponse){
                navController.navigate(Screens.Login.route)
            }
        }
        is RegisterResponseModel.Error ->{
            LaunchedEffect(key1 = registerResponse){
                Toast.makeText(context, (registerResponse as RegisterResponseModel.Error).error, Toast.LENGTH_SHORT).show()
            }

        }
    }

}


@Composable
fun RegisterScreen(
    error: RegisterFieldValidationModel,
    onButtonClick: (String, String, String) -> Unit,
    onLogonTxtClick: () -> Unit
) {
    var userName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .verticalScroll(rememberScrollState())
                ,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            LottieHeader(lottieCompositionSpec = LottieCompositionSpec.RawRes(R.raw.register))

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))


            MyTextField(
                error = error.userName,
                label = stringResource(id = R.string.user_name),
                valueOfTxtField = { userName = it }
            )

            MyTextField(
                error = error.email,
                label = stringResource(id = R.string.email),
                valueOfTxtField = { email = it }
            )

            MyTextField(
                error= error.password,
                label = stringResource(id = R.string.password),
                valueOfTxtField = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.6f),
                label = stringResource(id = R.string.register),
                onClick = {onButtonClick(userName,email,password)}
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier
                        .clickable { onLogonTxtClick() },
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(56.dp))




        }
    }
}











@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        RegisterScreen(
            error = RegisterFieldValidationModel(),
            onButtonClick = {a,b,c->},
            onLogonTxtClick = {}

        )
    }

}
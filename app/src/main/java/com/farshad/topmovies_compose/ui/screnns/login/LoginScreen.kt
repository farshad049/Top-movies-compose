package com.farshad.topmovies_compose.ui.screnns.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.farshad.topmovies_compose.ui.screnns.common.PasswordTextField
import com.farshad.topmovies_compose.ui.screnns.login.model.LoginFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.login.model.LoginResponseModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun LoginScreenWithViewModel(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val validation by loginViewModel.validationFlow.collectAsStateWithLifecycle(initialValue = LoginFieldValidationModel())

    val loginResponse by loginViewModel.loginUserFlow.collectAsStateWithLifecycle(initialValue = LoginResponseModel.Loading)


    LoginScreen(
        error = validation,
        onButtonClick = { email, password ->
            loginViewModel.validate(
                email = email,
                password = password,
            )
        }
    )




    when (loginResponse) {
        is LoginResponseModel.Success -> {
            LaunchedEffect(key1 = loginResponse){
                navController.navigate(route = Screens.Dashboard.route){
                    popUpTo(route = Screens.Dashboard.route){
                        inclusive= true
                    }
                }
            }

        }

        is LoginResponseModel.Error -> {
            LaunchedEffect(key1 = loginResponse) {
                Toast.makeText(context, (loginResponse as LoginResponseModel.Error).error, Toast.LENGTH_SHORT).show()
            }
        }

        else -> {}
    }

}


@Composable
fun LoginScreen(
    error: LoginFieldValidationModel,
    onButtonClick: (String, String) -> Unit,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieHeader(lottieCompositionSpec = LottieCompositionSpec.RawRes(R.raw.login))

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))


            MyTextField(
                error = error.email,
                label = stringResource(id = R.string.email),
                valueOfTxtField = { email = it }
            )

            PasswordTextField(
                error = error.password,
                label = stringResource(id = R.string.password),
                valueOfTxtField = { password = it }
            )

            Spacer(modifier = Modifier.height(16.dp))



            MyButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.6f),
                label = stringResource(id = R.string.login),
                isButtonLoading= error.buttonLoading,
                onClick = { onButtonClick(email, password) }
            )

            Spacer(modifier = Modifier.height(56.dp))

        }
    }
}





@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme {
        LoginScreen(
            error = LoginFieldValidationModel(),
            onButtonClick = { a, b -> }
        )
    }
}
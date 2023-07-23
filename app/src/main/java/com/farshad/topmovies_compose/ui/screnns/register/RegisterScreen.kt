package com.farshad.topmovies_compose.ui.screnns.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.common.MyButton
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.register.model.RegisterResponseModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

@Composable
fun RegisterScreenWithViewModel(
    navController: NavHostController,
    registerViewModel: RegisterViewModel= hiltViewModel()
) {

    val validation by registerViewModel.validationFlow.collectAsStateWithLifecycle(initialValue = RegisterFieldValidationModel())

    val registerResponse by registerViewModel.registerUserFlow.collectAsStateWithLifecycle(initialValue = RegisterResponseModel.Loading)

    RegisterScreen(
        error = validation,
        onButtonClick = {userName, email, password ->
            registerViewModel.validate(
                userName = userName,
                email= email,
                password = password,
            )
            Log.e("userName",userName)
            Log.e("email",email)
            Log.e("password",password)
        }
    )


    when(registerResponse){
        is RegisterResponseModel.Success ->{
            navController.navigate(Screens.Login.route)
        }
        is RegisterResponseModel.Error ->{
            Toast.makeText(LocalContext.current, (registerResponse as RegisterResponseModel.Error).error, Toast.LENGTH_SHORT).show()
        }
    }

}


@Composable
fun RegisterScreen(
    error: RegisterFieldValidationModel,
    onButtonClick: (String, String, String) -> Unit,
) {
    var userName by rememberSaveable { mutableStateOf("") }
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
            
            RegisterHeader()

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




        }
    }
}


@Composable
fun RegisterHeader() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.register))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying
    )

    LaunchedEffect(key1 = progress) {
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            .height(300.dp)
            .clickable { isPlaying = true },
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = { progress }
        )


    }
}

@Composable
fun MyTextField(
    label: String,
    valueOfTxtField: (String) -> Unit,
    error: String? = null
) {

    var text by rememberSaveable { mutableStateOf("") }


    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            valueOfTxtField(text)
        },
        placeholder = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        singleLine = true,
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error != null)
                Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
        },
        keyboardActions = KeyboardActions {
            valueOfTxtField(text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground
        )


    )


}






@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        RegisterScreen(
            error = RegisterFieldValidationModel(),
            onButtonClick = {a,b,c->},

        )
    }

}
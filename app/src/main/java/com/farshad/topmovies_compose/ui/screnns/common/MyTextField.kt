package com.farshad.topmovies_compose.ui.screnns.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

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
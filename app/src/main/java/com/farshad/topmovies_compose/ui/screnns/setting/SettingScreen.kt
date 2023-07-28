package com.farshad.topmovies_compose.ui.screnns.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.DARK
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.ENGLISH
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.LIGHT
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.PERSIAN
import com.farshad.topmovies_compose.data.dataStore.DataStoreConstants.SYSTEM
import com.farshad.topmovies_compose.data.dataStore.DataStoreViewModel
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview

data class PairItem(
    val code: String,
    val displayName: String
)

@Composable
fun SettingScreenWithViewModel(
    navController: NavHostController,
    dataStoreViewModel: DataStoreViewModel = hiltViewModel()
) {
    val settingOnClicks = SettingOnClicks(dataStoreViewModel)
    val selectedTheme by dataStoreViewModel.theme.collectAsStateWithLifecycle(initialValue = SYSTEM)
    val selectedLocale by dataStoreViewModel.locale.collectAsStateWithLifecycle(initialValue = ENGLISH)

    SettingScreen(
        selectedTheme = selectedTheme,
        selectedLocale= selectedLocale,
        onThemeChangeClick = { settingOnClicks.onThemeClick(it) },
        onLocaleChangeClick = {settingOnClicks.onLocaleChangeClick(it)}
    )

}


@Composable
fun SettingScreen(
    selectedTheme: String,
    onThemeChangeClick: (String) -> Unit,
    selectedLocale: String,
    onLocaleChangeClick: (String) -> Unit
) {


    val themeItems: List<PairItem> = listOf(
        PairItem(code = DARK, displayName = stringResource(id = R.string.dark)),
        PairItem(code = LIGHT, displayName = stringResource(id = R.string.light)),
        PairItem(code = SYSTEM, displayName = stringResource(id = R.string.system))
    )

    val themeInitValue: PairItem =
        when (selectedTheme) {
            DARK -> themeItems[0]
            LIGHT -> themeItems[1]
            SYSTEM -> themeItems[2]
            else -> {
                PairItem("", "")
            }
        }

    val localeItem: List<PairItem> = listOf(
        PairItem(code = ENGLISH, displayName = stringResource(id = R.string.english)),
        PairItem(code = PERSIAN, displayName = stringResource(id = R.string.farsi))
    )

    val localeInitValue: PairItem=
        if (selectedLocale == ENGLISH){
            localeItem[0]
        }else{
            localeItem[1]
        }



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
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                text = stringResource(id = R.string.theme),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(35.dp)
                    .shadow(
                        elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                themeItems.forEach { item ->

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(
                                shape = MaterialTheme.shapes.small,
                                color = if (item == themeInitValue) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                            .clip(shape = MaterialTheme.shapes.small)
                            .clickable { onThemeChangeClick(item.code) },
                        text = item.displayName,
                        color = if(item == themeInitValue) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )


                }

            }


            Spacer(modifier = Modifier.height(28.dp))

            Text(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                text = stringResource(id = R.string.language),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .height(35.dp)
                    .shadow(
                        elevation = 6.dp, spotColor = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {

                localeItem.forEach { item ->

                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(
                                shape = MaterialTheme.shapes.small,
                                color = if (item == localeInitValue) MaterialTheme.colorScheme.primary else Color.Transparent
                            )
                            .clip(shape = MaterialTheme.shapes.small)
                            .clickable { onLocaleChangeClick(item.code) },
                        text = item.displayName,
                        color = if(item == localeInitValue) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )


                }

            }


        }
    }





}


@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme {
        SettingScreen(
            selectedTheme = "dark",
            onThemeChangeClick = {},
            onLocaleChangeClick = {},
            selectedLocale = "fa"
        )
    }
}


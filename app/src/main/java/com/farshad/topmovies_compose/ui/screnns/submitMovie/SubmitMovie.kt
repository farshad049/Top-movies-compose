package com.farshad.topmovies_compose.ui.screnns.submitMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.ui.theme.AppTheme
import com.farshad.topmovies_compose.util.DarkAndLightPreview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun SubmitScreenWithViewModel(
    navController: NavHostController,
) {
    SubmitScreen(navController = navController)
}




@OptIn(ExperimentalPagerApi::class)
@Composable
fun SubmitScreen(
    navController: NavHostController
) {

    val items = listOf(
        stringResource(id = R.string.upload_base_64),
        stringResource(id = R.string.upload_multipart)
    )

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    val tabBackGroundColorSelected= Brush.verticalGradient(
        colors = listOf(
            Color.Transparent ,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        )
    )

    val tabBackGroundColorUnSelected= Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Transparent,
        )
    )

    val tabShape= RoundedCornerShape(
        topStart = 45.dp, topEnd = 45.dp, bottomStart = 0.dp, bottomEnd = 0.dp
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            TabRow(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small),
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                items.forEachIndexed { index, item ->
                    Tab(
                        modifier = Modifier.clip(shape = tabShape),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    shape = tabShape,
                                    brush = if (pagerState.currentPage == index) tabBackGroundColorSelected else tabBackGroundColorUnSelected,
                                )
                                .padding(10.dp)
                                .clip(shape = tabShape),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = item,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            HorizontalPager(
                count = items.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    when (items[currentPage]){
                        items[0] ->{
                            SubmitMovieBase64ScreenWithViewModel(navController = navController)
                        }
                        items[1] ->{
                            SubmitMovieMultipartWithViewModel(navController= navController)
                        }
                    }
                }
            }

        }
    }
}




@DarkAndLightPreview
@Composable
private fun Preview() {
    AppTheme() {
        SubmitScreen(
          navController = rememberNavController()
        )
    }
}
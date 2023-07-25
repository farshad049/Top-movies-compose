package com.farshad.topmovies_compose.ui.screnns.movieDetail

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.farshad.moviesAppCompose.data.model.ui.Resource
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.topmovies_compose.navigation.Screens
import com.farshad.topmovies_compose.ui.screnns.favorite.FavoriteScreenViewModel
import com.farshad.topmovies_compose.ui.screnns.movieDetail.model.UiMovieDetailModel


class DetailScreenOnClicks(
    private val navController: NavHostController,
    private val  favoriteScreenViewModel: FavoriteScreenViewModel
    ) {

    fun onSimilarMovieClick(movieId:Int){
        navController.navigate(Screens.Detail.passMovieID(movieId))
    }

    fun onFavoriteClick(isFavorite: Boolean, movie: FavoriteMovieEntity){
        if (isFavorite){
            favoriteScreenViewModel.deleteFavoriteMovie(movie)
        }else{
            favoriteScreenViewModel.insertFavoriteMovie(movie)
        }
    }


    fun onShareClick(movieId: Int,context: Context){
        val dataToShare = "https://moviesapi.ir/api/v1/movies/${movieId}"
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hey Check out this Great app:")
        intent.putExtra(Intent.EXTRA_TEXT, dataToShare)
        ContextCompat.startActivity(context, Intent.createChooser(intent, "Share To:"), null)
    }


}
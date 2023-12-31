package com.farshad.topmovies_compose.data.repository

import com.farshad.topmovies_compose.data.roomDatabase.Dao.FavoriteMovieDao
import com.farshad.topmovies_compose.data.roomDatabase.Dao.MovieSearchHistoryDao
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntity
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntityWithoutId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val movieSearchHistoryDao: MovieSearchHistoryDao
) {

    suspend fun insertFavoriteMovie(movie : FavoriteMovieEntity){
        favoriteMovieDao.insert(movie)
    }

    suspend fun deleteFavoriteMovie(movie : FavoriteMovieEntity){
        favoriteMovieDao.delete(movie)
    }

     fun getAllFavoriteMovies() : Flow<List<FavoriteMovieEntity>>{
        return favoriteMovieDao.getAllItemEntities()
    }





    suspend fun insertMovieSearchHistory(movie : SearchHistoryEntity){
        movieSearchHistoryDao.insert(movie)
    }

    suspend fun deleteAllMovieSearchHistory(){
        movieSearchHistoryDao.deleteAll()
    }

    suspend fun deleteMovieSearchById(movieId : Int){
        movieSearchHistoryDao.deleteMovieById(movieId)
    }

     fun getAllMovieSearchHistory() : Flow<List<SearchHistoryEntityWithoutId>>{
        return movieSearchHistoryDao.getAllItemEntities()
    }







}
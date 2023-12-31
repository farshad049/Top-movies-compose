package com.farshad.topmovies_compose.data.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farshad.topmovies_compose.data.roomDatabase.Dao.FavoriteMovieDao
import com.farshad.topmovies_compose.data.roomDatabase.Dao.MovieSearchHistoryDao
import com.farshad.topmovies_compose.data.roomDatabase.Entity.Converters
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import com.farshad.topmovies_compose.data.roomDatabase.Entity.SearchHistoryEntity

@Database(entities = [FavoriteMovieEntity::class , SearchHistoryEntity::class] , version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun favoriteMovieDao() : FavoriteMovieDao
    abstract fun movieSearchHistoryDao() : MovieSearchHistoryDao
}






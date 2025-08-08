package com.farshad.topmovies_compose.data.roomDatabase.Dao

import androidx.room.*
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie")
    fun getAllItemEntities(): Flow<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemEntity: FavoriteMovieEntity): Unit

    @Delete
    suspend fun delete(itemEntity: FavoriteMovieEntity): Int

    @Update
    suspend fun update(itemEntity: FavoriteMovieEntity): Int

    @Query("DELETE FROM favorite_movie")
    suspend fun deleteAll(): Int
}
package com.farshad.topmovies_compose.data.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_search_history")
data class SearchHistoryEntity (
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val movieId : Int,
    val movieTitle: String
        )

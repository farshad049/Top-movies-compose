package com.farshad.topmovies_compose.data.roomDatabase.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Entity(tableName = "favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val actors: String,
    val country: String,
    val director: String ,
    val genres: List<String>,
    val images: List<String>,
    val imdb_rating: String,
    val plot: String,
    val poster: String,
    val rated: String,
    val year: String,
    val runTime: String,
    val imdb_votes: String,
    val released: String,
    val writer: String,
    val awards: String ,
    val metaScore: String
)

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}


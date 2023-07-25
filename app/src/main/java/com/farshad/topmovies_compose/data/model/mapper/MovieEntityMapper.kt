package com.farshad.topmovies_compose.data.model.mapper

import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.roomDatabase.Entity.FavoriteMovieEntity
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() {

    fun buildFrom(domainMovie: DomainMovieModel): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            actors=domainMovie.actors ,
            country=domainMovie.country ,
            director=domainMovie.director,
            genres=domainMovie.genres,
            id=domainMovie.id ,
            images=domainMovie.images ,
            imdb_rating=domainMovie.imdb_rating,
            plot= domainMovie.plot ,
            poster=domainMovie.poster,
            rated=domainMovie.rated ,
            title=domainMovie.title ,
            year=domainMovie.year,
            runTime = domainMovie.runTime,
            imdb_votes = domainMovie.imdb_votes ,
            metaScore = domainMovie.metaScore,
            writer = domainMovie.writer,
            awards = domainMovie.awards ,
            released = domainMovie.released
        )
    }
}
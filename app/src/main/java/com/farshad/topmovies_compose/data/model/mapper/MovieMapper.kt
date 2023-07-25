package com.farshad.topmovies_compose.data.model.mapper

import com.farshad.topmovies_compose.data.model.domain.DomainMovieModel
import com.farshad.topmovies_compose.data.model.network.NetworkMovieModel
import javax.inject.Inject

class MovieMapper @Inject constructor(){

    fun buildFrom(networkMovieModel: NetworkMovieModel?): DomainMovieModel {
        return DomainMovieModel(
            actors=networkMovieModel?.actors ?:"",
            country=networkMovieModel?.country ?:"",
            director=networkMovieModel?.director ?:"",
            genres=networkMovieModel?.genres ?: emptyList(),
            id=networkMovieModel?.id ?:0,
            images=networkMovieModel?.images ?: emptyList(),
            imdb_rating=networkMovieModel?.imdb_rating ?:"",
            plot= networkMovieModel?.plot ?:"",
            poster=networkMovieModel?.poster ?:"",
            rated=networkMovieModel?.rated ?:"",
            title=networkMovieModel?.title ?:"",
            year=networkMovieModel?.year ?:"",
            runTime = networkMovieModel?.runtime ?:"",
            imdb_votes = networkMovieModel?.imdb_votes ?:"",
            metaScore = networkMovieModel?.metascore ?:"",
            writer = networkMovieModel?.writer ?:"",
            awards = networkMovieModel?.awards ?: "",
            released = networkMovieModel?.released ?: ""
        )
    }

}

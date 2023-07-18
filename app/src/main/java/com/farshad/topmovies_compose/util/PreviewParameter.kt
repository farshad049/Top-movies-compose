package com.farshad.topmovies_compose.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.farshad.moviesAppCompose.data.model.domain.DomainMovieModel
import com.farshad.moviesAppCompose.data.model.network.GenresModel
import com.farshad.topmovies_compose.ui.screnns.dashboard.model.DashboardUiModel


val sampleMovieList = listOf(
    DomainMovieModel(
        actors = "farshad",
        country = "Iran",
        director = "Alex",
        genres = listOf("comedy", "action", "dram"),
        id = 1,
        images = listOf("as", "asd", "awe", "art"),
        imdb_rating = "8.3",
        plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
        poster = "rty",
        rated = "R",
        title = "movie title",
        year = "1996",
        runTime = "142 min"
    ),
    DomainMovieModel(
        actors = "farshad1",
        country = "Iran1",
        director = "Alex1",
        genres = listOf("comedy1", "action1", "dram1"),
        id = 2,
        images = listOf("as1", "asd1", "awe1", "art1"),
        imdb_rating = "8.3",
        plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
        poster = "rty1",
        rated = "R",
        title = "movie title",
        year = "1996",
        runTime = "142 min"

    ),
    DomainMovieModel(
        actors = "farshad2",
        country = "Iran2",
        director = "Alex",
        genres = listOf("comedy2", "action2", "dram2"),
        id = 3,
        images = listOf("as2", "asd2", "awe2", "art2"),
        imdb_rating = "8.3",
        plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
        poster = "rty",
        rated = "R",
        title = "movie title",
        year = "1996",
        runTime = "142 min"
    )
)
val sampleMovie1 = DomainMovieModel(
    actors = "farshad",
    country = "Iran",
    director = "Alex",
    genres = listOf("comedy", "action", "dram"),
    id = 1,
    images = listOf("as", "asd", "awe", "art"),
    imdb_rating = "8.3",
    plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
    poster = "rty",
    rated = "R",
    title = "movie title",
    year = "1996",
    runTime = "142 min"
)
val sampleMovie2 = DomainMovieModel(
    actors = "farshad1",
    country = "Iran1",
    director = "Alex1",
    genres = listOf("comedy1", "action1", "dram1"),
    id = 2,
    images = listOf("as1", "asd1", "awe1", "art1"),
    imdb_rating = "8.3",
    plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
    poster = "rty1",
    rated = "R",
    title = "movie title",
    year = "1996",
    runTime = "142 min"

)
val sampleMovie3 = DomainMovieModel(
    actors = "farshad2",
    country = "Iran2",
    director = "Alex",
    genres = listOf("comedy2", "action2", "dram2"),
    id = 3,
    images = listOf("as2", "asd2", "awe2", "art2"),
    imdb_rating = "8.3",
    plot = "Let's talk about combining two flows into one. There are a few ways to do this. The simplest involves merging the elements from two flows into one",
    poster = "rty",
    rated = "R",
    title = "movie title",
    year = "1996",
    runTime = "142 min"
)

val sampleGenreList = listOf(
    GenresModel(1, "comedy"),
    GenresModel(2, "comedy"),
    GenresModel(3, "action"),
    GenresModel(4, "dram"),
    GenresModel(5, "dram"),
    GenresModel(6, "dram"),
    GenresModel(7, "dram"),
)



class SampleGenresModel : PreviewParameterProvider<List<GenresModel>> {
    override val values = sequenceOf(
        sampleGenreList
    )
}

class SampleDomainMovieModel : PreviewParameterProvider<DomainMovieModel> {
    override val values = sequenceOf(sampleMovie1)
}

class SampleDashboardModel : PreviewParameterProvider<DashboardUiModel> {
    override val values = sequenceOf(
        DashboardUiModel(
            movie = listOf(sampleMovie1, sampleMovie2, sampleMovie3),
            genre = listOf(
                GenresModel(1, "comedy"),
                GenresModel(2, "action"),
                GenresModel(3, "dram")
            ),
            randomMovies = listOf(sampleMovie1, sampleMovie2, sampleMovie3)
        )
    )
}
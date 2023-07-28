package com.farshad.topmovies_compose.ui.screnns.submitMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.R
import com.farshad.topmovies_compose.data.model.domain.UploadMovieModel
import com.farshad.topmovies_compose.data.repository.SubmitMovieRepository
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitResponseModel
import com.farshad.topmovies_compose.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitBase64ViewModel @Inject constructor(
    private val repository: SubmitMovieRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    //works like event
    private val _submitMovieBase64Flow = Channel<SubmitResponseModel>()
    val submitMovieBase64Flow = _submitMovieBase64Flow.receiveAsFlow()


    private val _validationMutableFlow = Channel<SubmitFieldValidationModel>()
    val validationFlow = _validationMutableFlow.receiveAsFlow()


    private fun pushMovieBase64(movie: UploadMovieModel) {
        viewModelScope.launch {
            val response = repository.pushMovieBase64(movie)
            _submitMovieBase64Flow.send(response)
        }
    }


    fun validateBase64(
        title: String,
        imdb_id: String,
        country: String,
        year: String,
        poster: String?
    ) = viewModelScope.launch {

        val titleB = title.trim()
        val imdbIdB = imdb_id.trim()
        val countryB = country.trim()
        val yearB = year.trim()

        when {
            titleB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(
                        title = resourcesProvider.getString(R.string.please_enter_a_valid_title)
                    )
                )
            }

            imdbIdB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(imdbId = resourcesProvider.getString(R.string.please_enter_a_valid_imdb_id))
                )
            }

            countryB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(country = resourcesProvider.getString(R.string.please_enter_a_valid_country_name))
                )
            }

            yearB.isEmpty() && year.length < 4 -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(year = resourcesProvider.getString(R.string.please_enter_a_valid_year))
                )


            }

            else -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(
                        title = null,
                        imdbId = null,
                        country = null,
                        year = null,
                        poster = null,
                    )
                )

                pushMovieBase64(
                    UploadMovieModel(
                        title = titleB,
                        imdb_id = imdbIdB,
                        country = countryB,
                        year = yearB.toInt(),
                        poster = "data:image/jpeg;base64,$poster"
                    )
                )

            }
        }

    }


}
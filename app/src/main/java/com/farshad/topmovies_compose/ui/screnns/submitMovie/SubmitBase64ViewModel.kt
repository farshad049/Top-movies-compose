package com.farshad.topmovies_compose.ui.screnns.submitMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.topmovies_compose.data.repository.SubmitMovieRepository
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitFieldValidationModel
import com.farshad.topmovies_compose.ui.screnns.submitMovie.model.SubmitResponseModel
import com.farshad.topmovies_compose.data.model.domain.UploadMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmitBase64ViewModel @Inject constructor(
    private val repository: SubmitMovieRepository
) : ViewModel() {

    //works like event
    private val _submitMovieBase64Flow = Channel<SubmitResponseModel>()
    val submitMovieBase64Flow = _submitMovieBase64Flow.receiveAsFlow()


    private val _validationMutableFlow= Channel<SubmitFieldValidationModel>()
    val validationFlow = _validationMutableFlow.receiveAsFlow()




    private fun pushMovieBase64(movie: UploadMovieModel){
        viewModelScope.launch {
            val response=repository.pushMovieBase64(movie)
            _submitMovieBase64Flow.send(response)
        }
    }




    fun validateBase64(
        title : String,
        imdb_id : String,
        country : String,
        year : String ,
        poster : String?
    )=viewModelScope.launch{

        val titleB = title.trim()
        val imdbIdB = imdb_id.trim()
        val countryB = country.trim()
        val yearB = year.trim()

        when{
            titleB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(title = "* please enter a valid title"
                    )
                    )
            }
            imdbIdB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(imdbId = "* please enter a valid IMDB ID")
                )
            }
            countryB.isEmpty() -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(country = "* please enter a valid country name")
                )
            }
            yearB.isEmpty() && year.length < 4 -> {
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(year = "* please enter a valid year")
                )


            }else ->{
                _validationMutableFlow.send(
                    SubmitFieldValidationModel(
                        title = null,
                        imdbId = null,
                        country = null,
                        year  = null,
                        poster  = null,
                    )
                )

              pushMovieBase64(
                  UploadMovieModel(
                      title = titleB,
                      imdb_id = imdbIdB ,
                      country = countryB,
                      year = yearB.toInt(),
                      poster = "data:image/jpeg;base64,$poster"
                  )
              )

            }
        }

    }




















}
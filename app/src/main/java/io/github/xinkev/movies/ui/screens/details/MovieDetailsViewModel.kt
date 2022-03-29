package io.github.xinkev.movies.ui.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xinkev.movies.repositories.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MovieRepository,
) : ViewModel() {
    var loadState by mutableStateOf(MovieDetailsLoadState())

    fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            repo.getMovieDetails(movieId)
                .collect { loadState = it }
        }
    }

    fun onVoteUpdate(movieId: Long, voted: Boolean) {
        viewModelScope.launch {
            repo.saveVoteState(movieId, voted)
        }
    }
}
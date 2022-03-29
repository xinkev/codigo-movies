package io.github.xinkev.movies.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xinkev.movies.repositories.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: MovieRepository,
) : ViewModel() {
    val popularMovies = repo.popularMoviesFlow.cachedIn(viewModelScope)
    val upcomingMovies = repo.upcomingMoviesFlow.cachedIn(viewModelScope)

    fun onVoteUpdate(movieId: Long, voted: Boolean) {
        viewModelScope.launch {
            repo.saveVoteState(movieId, voted)
        }
    }
}
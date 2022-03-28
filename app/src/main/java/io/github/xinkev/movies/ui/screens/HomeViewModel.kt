package io.github.xinkev.movies.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xinkev.movies.repositories.PopularMovieRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    popularRepo: PopularMovieRepository,
) : ViewModel() {
    val popularMovies = popularRepo.popularMoviesFlow.cachedIn(viewModelScope)
}
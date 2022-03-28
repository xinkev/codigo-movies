package io.github.xinkev.movies.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xinkev.movies.repositories.PopularMovieRepository
import io.github.xinkev.movies.repositories.UpcomingMovieRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    popularRepo: PopularMovieRepository,
    upcomingRepo: UpcomingMovieRepository,
) : ViewModel() {
    val popularMovies = popularRepo.popularMoviesFlow.cachedIn(viewModelScope)
    val upcomingMovies = upcomingRepo.upcomingMoviesFlow.cachedIn(viewModelScope)
}
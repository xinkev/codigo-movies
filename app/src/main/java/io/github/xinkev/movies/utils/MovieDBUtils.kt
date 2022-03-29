package io.github.xinkev.movies.utils

fun imageUrl(path: String?): String = "https://image.tmdb.org/t/p/w500/$path"

/**
 * Format runtime to display. E.g 1hr 20min
 * @param value Runtime in minutes
 */
fun runtimeDisplayFormat(value: Int): String {
    val hr = value / 60
    val min = value % 60
    return "${hr}hr ${min}min"
}
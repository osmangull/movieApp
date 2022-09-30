package com.sanalkasif.movieapp.model.entities.upcoming

import com.sanalkasif.movieapp.model.entities.common.Dates
import com.sanalkasif.movieapp.model.entities.common.Result
/**
 *Created by OsmanGul
 */
data class UpcomingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)
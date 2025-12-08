package com.effectivemobiletask.data.network

import com.effectivemobiletask.data.network.dto.CoursesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-g&export=download")
    suspend fun getCourses(): Response<CoursesResponse>
}
package com.effectivemobiletask.data.network

import com.effectivemobiletask.data.network.dto.CoursesResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStreamReader

class MockApiService : ApiService {
    override suspend fun getCourses(): Response<CoursesResponse> {
        return try {
            val inputStream = javaClass.classLoader
                .getResourceAsStream("courses.json")
                ?: return Response.error(404, ResponseBody.create(null, "File not found"))

            val gson = Gson()
            val coursesResponse = gson.fromJson(
                InputStreamReader(inputStream),
                CoursesResponse::class.java
            )

            inputStream.close()
            Response.success(coursesResponse)
        } catch (e: Exception) {
            Response.error(500, ResponseBody.create(null, e.message ?: "Unknown error"))
        }
    }
}
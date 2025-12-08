package com.effectivemobiletask.data.di

import android.content.Context
import androidx.room.Room
import com.effectivemobiletask.data.database.CoursesDatabase
import com.effectivemobiletask.data.database.FavoritesDao
import com.effectivemobiletask.data.mapper.CoursesMapper
import com.effectivemobiletask.data.network.ApiService
import com.effectivemobiletask.data.network.MockApiService
import com.effectivemobiletask.data.repository.CoursesRepositoryImpl
import com.effectivemobiletask.domain.repository.CoursesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCoursesDatabase(@ApplicationContext context: Context): CoursesDatabase {
        return Room.databaseBuilder(
            context,
            CoursesDatabase::class.java,
            "courses.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: CoursesDatabase): FavoritesDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideCoursesMapper(): CoursesMapper = CoursesMapper()

    @Provides
    @Singleton
    fun provideApiService(): ApiService = MockApiService()

    @Provides
    @Singleton
    fun provideCoursesRepository(
        apiService: ApiService,
        favoritesDao: FavoritesDao,
        mapper: CoursesMapper
    ): CoursesRepository = CoursesRepositoryImpl(
        apiService = apiService,
        favoritesDao = favoritesDao,
        mapper = mapper,
    )
}
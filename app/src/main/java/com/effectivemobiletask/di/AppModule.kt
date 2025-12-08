package com.effectivemobiletask.di

import com.effectivemobiletask.NavigationSourceImpl
import com.effectivemobiletask.navigation.NavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindNavigationProvider(impl: NavigationSourceImpl): NavigationProvider
}
package com.example.verodigitalsolutionandroidtask.data

import com.example.verodigitalsolutionandroidtask.data.repository.AuthRepositoryImpl
import com.example.verodigitalsolutionandroidtask.data.repository.TaskRepositoryImpl
import com.example.verodigitalsolutionandroidtask.domain.repository.AuthRepository
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {



    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository{
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(apiService: ApiService): TaskRepository{
        return TaskRepositoryImpl(apiService)
    }


}
package com.example.verodigitalsolutionandroidtask.data

import com.example.verodigitalsolutionandroidtask.data.repository.AuthRepositoryImpl
import com.example.verodigitalsolutionandroidtask.data.repository.TaskRepositoryImpl
import com.example.verodigitalsolutionandroidtask.domain.repository.AuthRepository
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
}

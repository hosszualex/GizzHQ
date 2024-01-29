package com.ah.gizzhq.data.di

import com.ah.gizzhq.domain.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.domain.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAuthenticationRepositoryRepository(
        userDataRepository: AppwriteAuthenticationRepositoryImpl,
    ): AuthenticationRepository

}
package com.ah.gizzhq.domain.di

import com.ah.gizzhq.data.repositories.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindsAuthenticationRepository(authenticationRepository: AppwriteAuthenticationRepositoryImpl): AuthenticationRepository
}

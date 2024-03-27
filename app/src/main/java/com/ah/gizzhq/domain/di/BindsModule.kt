package com.ah.gizzhq.domain.di

import com.ah.gizzhq.data.repositories.authentication.AppwriteAuthenticationRepositoryImpl
import com.ah.gizzhq.data.repositories.data.AppwriteDataRepositoryImpl
import com.ah.gizzhq.data.repositories.authentication.AuthenticationRepository
import com.ah.gizzhq.data.repositories.data.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    fun bindsAuthenticationRepository(authenticationRepository: AppwriteAuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    fun bindsDataRepository(dataRepository: AppwriteDataRepositoryImpl): DataRepository
}

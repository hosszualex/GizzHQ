package com.ah.gizzhq.domain.di

import com.ah.gizzhq.data.Appwrite
import com.ah.gizzhq.data.AppwriteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesAppwriteServies(): Appwrite = AppwriteService()
}

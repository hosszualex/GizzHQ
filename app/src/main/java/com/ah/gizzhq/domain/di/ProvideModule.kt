package com.ah.gizzhq.domain.di

import android.content.Context
import com.ah.gizzhq.data.AppPreferences
import com.ah.gizzhq.data.services.Appwrite
import com.ah.gizzhq.data.services.AppwriteService
import com.ah.gizzhq.data.AppPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {
    @Provides
    @Singleton
    fun providesAppwriteServies(
        @ApplicationContext context: Context,
    ): Appwrite = AppwriteService(context)

    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(
        @ApplicationContext context: Context
    ): AppPreferences = AppPreferencesDataSource(context)
}

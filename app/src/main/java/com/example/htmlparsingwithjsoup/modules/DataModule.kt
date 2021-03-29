package com.example.htmlparsingwithjsoup.modules

import android.app.Application
import com.example.htmlparsingwithjsoup.data.CutApi
import com.example.htmlparsingwithjsoup.data.CutRepository
import com.example.htmlparsingwithjsoup.data.CutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCutRepository(api: CutApi): CutRepository = CutRepositoryImpl(api)

}
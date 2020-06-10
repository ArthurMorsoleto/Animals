package com.example.animals.di

import android.app.Application
import com.example.animals.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferencesHelper =
        SharedPreferencesHelper(application)
}
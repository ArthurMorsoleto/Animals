package com.example.animals.di

import android.app.Application
import com.example.animals.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton
    open fun provideSharedPreferences(application: Application): SharedPreferencesHelper =
        SharedPreferencesHelper(application)
}
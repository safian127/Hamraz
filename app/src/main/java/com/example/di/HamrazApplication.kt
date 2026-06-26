package com.example.di

import android.app.Application

class HamrazApplication : Application() {
    
    // Core container containing global dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}

package com.example.cheqapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// this is just to give dagger hilt the information about your application,
// so the dagger hilt has also access to application context

@HiltAndroidApp
class CheqApplication: Application() {
}
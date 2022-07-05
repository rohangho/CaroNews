package com.example.caronews

import android.app.Application
import com.example.caronews.dagger.DaggerApplicationComponent

class MyApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

}
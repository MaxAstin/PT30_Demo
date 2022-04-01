package eld.peaksoft.pt30_demo

import android.app.Application
import com.google.firebase.FirebaseApp

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
       // FirebaseApp.initializeApp(this)
    }
}
package com.lifen.mygithubapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CucumberTestRunner: AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, Application::class.java.name, context)
    }
}
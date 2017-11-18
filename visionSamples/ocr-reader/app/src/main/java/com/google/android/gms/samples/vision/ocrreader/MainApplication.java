package com.google.android.gms.samples.vision.ocrreader;

import android.app.Application;

import timber.log.Timber;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

    }
}

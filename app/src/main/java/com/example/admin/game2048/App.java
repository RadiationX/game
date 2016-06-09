package com.example.admin.game2048;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App INSTANCE = null;

    public App() {
        INSTANCE = this;
    }

    public static App getInstance() {
        if (INSTANCE == null)
            INSTANCE = new App();
        return INSTANCE;
    }

    public static Context getContext() {
        return getInstance();
    }
}

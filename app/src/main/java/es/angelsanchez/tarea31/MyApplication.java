package es.angelsanchez.tarea31;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
    ExecutorService diskIOExecutor = Executors.newSingleThreadExecutor();
}

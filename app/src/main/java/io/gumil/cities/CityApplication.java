package io.gumil.cities;

import android.app.Application;

import io.gumil.cities.repository.CitiesRepository;

public class CityApplication extends Application {

    private CitiesRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public CitiesRepository getRepository() {
        if (repository == null) {
            repository = new CitiesRepository(this);
        }
        return repository;
    }
}

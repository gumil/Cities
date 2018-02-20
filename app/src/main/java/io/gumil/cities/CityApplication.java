package io.gumil.cities;

import android.app.Application;

import io.gumil.cities.repository.CitiesPersistence;
import io.gumil.cities.repository.CitiesRepository;

public class CityApplication extends Application {

    private CitiesPersistence persistence;
    private CitiesRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public CitiesPersistence getPersistence() {
        if (persistence == null) {
            persistence = new CitiesPersistence(this);
        }
        return persistence;
    }

    public CitiesRepository getRepository() {
        if (repository == null) {
            repository = new CitiesRepository(getPersistence());
        }
        return repository;
    }
}

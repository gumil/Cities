package io.gumil.cities;

import android.app.Application;

import io.gumil.cities.repository.CitiesRepository;
import io.gumil.cities.repository.CitiesStream;
import io.gumil.cities.repository.Stream;

public class CityApplication extends Application {

    private CitiesRepository repository;
    private Stream stream;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Stream getStream() {
        if (stream == null) {
            stream = new CitiesStream(this);
        }
        return stream;
    }

    public CitiesRepository getRepository() {
        if (repository == null) {
            repository = new CitiesRepository(getStream());
        }
        return repository;
    }
}

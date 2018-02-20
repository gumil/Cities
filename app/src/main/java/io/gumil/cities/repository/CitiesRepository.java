package io.gumil.cities.repository;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import io.gumil.cities.model.City;

public class CitiesRepository {

    private CitiesPersistence citiesPersistence;

    public CitiesRepository(CitiesPersistence citiesPersistence) {
        this.citiesPersistence = citiesPersistence;
    }

    @Nullable
    public List<City> getCities() throws IOException {
        return citiesPersistence.getCities();
    }

}

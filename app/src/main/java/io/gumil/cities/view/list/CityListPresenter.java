package io.gumil.cities.view.list;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import io.gumil.cities.model.City;
import io.gumil.cities.repository.CitiesRepository;

class CityListPresenter {

    private CitiesRepository repository;
    private CityView view;

    CityListPresenter(CitiesRepository repository, CityView view) {
        this.repository = repository;
        this.view = view;
    }

    void loadCities() {
        new AsyncTask<Void, Void, Void>() {

            private List<City> cities;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.setLoading(true);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    cities = repository.getCities();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                view.setLoading(false);
                view.showCities(cities);
            }
        }.execute();
    }
}

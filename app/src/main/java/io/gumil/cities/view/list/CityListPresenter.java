package io.gumil.cities.view.list;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.gumil.cities.R;
import io.gumil.cities.model.City;
import io.gumil.cities.repository.CitiesRepository;

class CityListPresenter {

    private CitiesRepository repository;
    private CityView view;
    private GetCitiesTask task;

    CityListPresenter(CitiesRepository repository, CityView view) {
        this.repository = repository;
        this.view = view;
    }

    void loadCities() {
        task = new GetCitiesTask(repository, view);
        task.execute();
    }

    void filter(String word) {
        view.showCities(repository.filter(word));
    }

    void destroy() {
        if (task != null) {
            task.cancel(true);
        }
    }

    private static class GetCitiesTask extends AsyncTask<Void, Void, List<City>> {

        private CitiesRepository repository;
        private CityView view;

        GetCitiesTask(CitiesRepository repository, CityView view) {
            this.repository = repository;
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.setLoading(true);
        }

        @Override
        protected List<City> doInBackground(Void... voids) {
            try {
                return repository.getCities();
            } catch (IOException e) {
                Log.e(CityListPresenter.class.getSimpleName(), "Error getting cities", e);
                view.showError(R.string.error_get_cities);
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<City> cities) {
            super.onPostExecute(cities);
            view.setLoading(false);
            view.showCities(cities);
        }
    }
}

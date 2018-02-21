package io.gumil.cities.view.list;

import android.support.annotation.StringRes;

import java.util.List;

import io.gumil.cities.model.City;

public interface CityView {
    void setLoading(boolean isLoading);
    void showCities(List<City> cities);
    void showError(@StringRes int stringRes);
}

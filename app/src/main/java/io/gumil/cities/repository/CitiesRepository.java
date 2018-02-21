package io.gumil.cities.repository;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import io.gumil.cities.model.City;

public class CitiesRepository {

    private TreeMap<String, City> citiesMap;
    private Stream stream;

    public CitiesRepository(Stream stream) {
        this.stream = stream;
    }

    /**
     * Lazily gets the list of cities
     */
    @Nullable
    public List<City> getCities() throws IOException {
        if (citiesMap != null) return new ArrayList<>(citiesMap.values());

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream.getStream()));

            String line;
            TreeMap<String, City> cities = new TreeMap<>();
            Gson gson = new Gson();
            while ((line = reader.readLine()) != null) {
                if (!line.equals("[") && !line.equals("]")) {
                    String substring = line;
                    if (line.charAt(line.length() - 1) == ',') {
                        substring = line.substring(0, line.length() - 1);
                    }

                    City city = gson.fromJson(substring, City.class);
                    cities.put(city.getName().toLowerCase(), city);
                }
            }

            citiesMap = cities;

            return new ArrayList<>(cities.values());
        } catch (IOException ex) {
            Log.e(CitiesRepository.class.getSimpleName(), "error", ex);
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public List<City> filter(String prefix) {
        if (citiesMap == null) return new ArrayList<>();

        return new ArrayList<>(citiesMap.subMap(prefix, true,
                prefix + Character.MAX_VALUE, true).values());
    }
}

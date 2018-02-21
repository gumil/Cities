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
     *
     * Steps:
     * 1. Read cities.json by line
     * 2. If line is valid parse to line using Gson
     * 3. Add City into TreeMap using city name as key
     *
     * Since the json file is formatted where each line can be parsed to a city, the list of cities
     * is processed by getting every line since it's safer to process the cities.json
     * file that way. Reading in by buffer might cause OutOfMemoryError. Also reading by chunks
     * can result to malformed json and might need to combine from different chunks.
     *
     * Using TreeMap, it can eliminate duplicates and sort the map when inserting.
     * Searching is also easier since it can get a submap based on prefix.
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
                    cities.put(city.toString().toLowerCase(), city);
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

        prefix = prefix.toLowerCase();
        return new ArrayList<>(citiesMap.subMap(prefix, true,
                prefix + Character.MAX_VALUE, true).values());
    }
}

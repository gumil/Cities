package io.gumil.cities.repository;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.gumil.cities.model.City;

public class CitiesPersistence {

    private List<City> cities;
    private Context context;

    public CitiesPersistence(Context context) {
        this.context = context;
    }

    /**
     * Lazily gets the list of cities
     */
    @Nullable
    public List<City> getCities() throws IOException {
        if (cities != null) return cities;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("cities.json")));

            String line;
            JsonParser parser = new JsonParser();
            List<City> cities = new ArrayList<>();
            Gson gson = new Gson();
            while ((line = reader.readLine()) != null) {
                if (!line.equals("[") && !line.equals("]")) {
                    String substring = line;
                    if (line.charAt(line.length() - 1) == ',') {
                        substring = line.substring(0, line.length() - 1);
                    }

                    cities.add(gson.fromJson(parser.parse(substring), City.class));
                }
            }

            Collections.sort(cities);

            this.cities = cities;

            return cities;
        } catch (IOException ex) {
            Log.e(CitiesPersistence.class.getSimpleName(), "error", ex);
            return null;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}

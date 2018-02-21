package io.gumil.cities.model;

import android.support.annotation.NonNull;

public class City implements Comparable<City> {

    private int _id;
    private String country;
    private String name;
    private Coord coord;

    public City(int _id, String country, String name, Coord coord) {
        this._id = _id;
        this.country = country;
        this.name = name;
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return _id;
    }

    public Coord getCoord() {
        return coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (_id != city._id) return false;
        if (country != null ? !country.equals(city.country) : city.country != null) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        return coord != null ? coord.equals(city.coord) : city.coord == null;
    }

    @Override
    public int hashCode() {
        int result = _id;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coord != null ? coord.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }

    @Override
    public int compareTo(@NonNull City o) {
        int compare = name.toLowerCase().compareTo(o.getName().toLowerCase());

        if (compare == 0) {
            compare = country.compareTo(o.getCountry());
        }

        return compare;
    }
}
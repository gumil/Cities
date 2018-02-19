package io.gumil.cities.model;

public class Coord {

    private double lon;
    private double lat;

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public void setLongitude(double lon) {
        this.lon = lon;
    }

    public double getLatitude() {
        return lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coord coord = (Coord) o;

        if (Double.compare(coord.lon, lon) != 0) return false;
        return Double.compare(coord.lat, lat) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lon);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}

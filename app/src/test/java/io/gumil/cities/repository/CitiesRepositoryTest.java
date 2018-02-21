package io.gumil.cities.repository;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.gumil.cities.model.City;
import io.gumil.cities.model.Coord;

import static org.junit.Assert.*;

public class CitiesRepositoryTest {

    private CitiesRepository repository;

    @Before
    public void setUp() throws IOException {
        TestStream stream = new TestStream();
        assertNotNull(stream.getStream());
        repository = new CitiesRepository(stream);
    }

    @Test
    public void testFilterWithoutGettingCities() {
        assertTrue(repository.filter("").isEmpty());
    }

    @Test
    public void testGetCities() throws IOException {
        List<City> cities = repository.getCities();
        assertNotNull(cities);
        assertEquals(5, cities.size());
    }

    @Test
    public void testFilterCaseSensitivity() throws IOException {
        List<City> cities = repository.getCities();
        assertNotNull(cities);
        assertEquals(5, cities.size());

        List<City> filteredSmallA = repository.filter("a");
        assertEquals(4, filteredSmallA.size());

        List<City> filteredCapitalA = repository.filter("A");
        assertEquals(4, filteredCapitalA.size());
    }

    @Test
    public void testFilterNoResults() throws IOException {
        List<City> cities = repository.getCities();
        assertNotNull(cities);
        assertEquals(5, cities.size());

        List<City> noResults = repository.filter("hello world");
        assertTrue(noResults.isEmpty());
    }

    @Test
    public void testFilterExactlyOneResult() throws IOException {
        List<City> cities = repository.getCities();
        assertNotNull(cities);
        assertEquals(5, cities.size());

        List<City> result = repository.filter("alabama");
        assertEquals(1, result.size());

        City expected = new City(4829764, "US", "Alabama",
                new Coord(-86.750259, 32.750408));
        assertEquals(expected, result.get(0));
    }

    @Test
    public void testFilterSortingOfTwoResults() throws IOException {
        List<City> cities = repository.getCities();
        assertNotNull(cities);
        assertEquals(5, cities.size());

        List<City> result = repository.filter("al");
        assertEquals(2, result.size());

        City alabama = new City(4829764, "US", "Alabama",
                new Coord(-86.750259, 32.750408));
        City albaquerque = new City(5454711, "US", "Albuquerque",
                new Coord(-106.651138, 35.084492));
        assertEquals(alabama, result.get(0));
        assertEquals(albaquerque, result.get(1));
    }

    static class TestStream implements Stream {

        @Override
        public InputStream getStream() throws IOException {
            return getClass().getClassLoader().getResourceAsStream("cities.json");
        }
    }
}
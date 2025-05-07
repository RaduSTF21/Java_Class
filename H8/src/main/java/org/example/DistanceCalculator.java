package org.example;

import java.sql.SQLException;
import java.util.List;

public class DistanceCalculator {
    private final CityDAO cityDAO;


    private static final double EARTH_RADIUS = 6371.0;

    public DistanceCalculator() {
        this.cityDAO = new CityDAO();
    }


    public double calculateDistance(City city1, City city2) {
        double lat1 = Math.toRadians(city1.getLatitude());
        double lon1 = Math.toRadians(city1.getLongitude());
        double lat2 = Math.toRadians(city2.getLatitude());
        double lon2 = Math.toRadians(city2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }


    public void displayCapitalDistances() throws SQLException {
        List<City> capitals = cityDAO.findCapitals();
        System.out.println("Distances between world capitals (in kilometers):");

        for (int i = 0; i < capitals.size(); i++) {
            City city1 = capitals.get(i);
            for (int j = i + 1; j < capitals.size(); j++) {
                City city2 = capitals.get(j);
                double distance = calculateDistance(city1, city2);
                System.out.printf("Distance between %s and %s: %.2f km%n",
                        city1.getName(), city2.getName(), distance);
            }
        }
    }


    public double getDistanceBetweenCities(String cityName1, String cityName2) throws SQLException {
        City city1 = cityDAO.findByName(cityName1);
        City city2 = cityDAO.findByName(cityName2);

        if (city1 == null) {
            throw new IllegalArgumentException("City not found: " + cityName1);
        }
        if (city2 == null) {
            throw new IllegalArgumentException("City not found: " + cityName2);
        }

        return calculateDistance(city1, city2);
    }
}
package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements GenericDAO<City> {

    @Override
    public void create(City city) throws SQLException {
        if (city.getName() == null || city.getCountryId() == null) {
            throw new SQLException("City name and country ID cannot be null");
        }

        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country_id, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, city.getName());
            pstmt.setInt(2, city.getCountryId());
            pstmt.setBoolean(3, city.isCapital());
            pstmt.setDouble(4, city.getLatitude());
            pstmt.setDouble(5, city.getLongitude());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    city.setId(keys.getInt(1));
                }
            }
        }
    }

    public void create(String name, Integer countryId, boolean capital, double latitude, double longitude) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country_id, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setInt(2, countryId);
            pstmt.setBoolean(3, capital);
            pstmt.setDouble(4, latitude);
            pstmt.setDouble(5, longitude);
            pstmt.executeUpdate();
        }
    }

    @Override
    public City findById(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM cities WHERE id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    City city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("name"));
                    city.setCountryId(rs.getInt("country_id"));
                    city.setCapital(rs.getBoolean("capital"));
                    city.setLatitude(rs.getDouble("latitude"));
                    city.setLongitude(rs.getDouble("longitude"));
                    return city;
                }
            }
        }
        return null;
    }

    @Override
    public City findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM cities WHERE name = ?")) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    City city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("name"));
                    city.setCountryId(rs.getInt("country_id"));
                    city.setCapital(rs.getBoolean("capital"));
                    city.setLatitude(rs.getDouble("latitude"));
                    city.setLongitude(rs.getDouble("longitude"));
                    return city;
                }
            }
        }
        return null;
    }

    public List<City> findCapitals() throws SQLException {
        List<City> capitals = new ArrayList<>();
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM cities WHERE capital = true")) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("name"));
                    city.setCountryId(rs.getInt("country_id"));
                    city.setCapital(rs.getBoolean("capital"));
                    city.setLatitude(rs.getDouble("latitude"));
                    city.setLongitude(rs.getDouble("longitude"));
                    capitals.add(city);
                }
            }
        }

        return capitals;
    }

    public List<City> findByCountryId(Integer countryId) throws SQLException {
        List<City> cities = new ArrayList<>();
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM cities WHERE country_id = ?")) {

            pstmt.setInt(1, countryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    City city = new City();
                    city.setId(rs.getInt("id"));
                    city.setName(rs.getString("name"));
                    city.setCountryId(rs.getInt("country_id"));
                    city.setCapital(rs.getBoolean("capital"));
                    city.setLatitude(rs.getDouble("latitude"));
                    city.setLongitude(rs.getDouble("longitude"));
                    cities.add(city);
                }
            }
        }

        return cities;
    }

    @Override
    public List<City> findAll() throws SQLException {
        List<City> cities = new ArrayList<>();
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM cities")) {

            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setCountryId(rs.getInt("country_id"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));
                cities.add(city);
            }
        }

        return cities;
    }

    @Override
    public void update(City city) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "UPDATE cities SET name = ?, country_id = ?, capital = ?, latitude = ?, longitude = ? WHERE id = ?")) {

            pstmt.setString(1, city.getName());
            pstmt.setInt(2, city.getCountryId());
            pstmt.setBoolean(3, city.isCapital());
            pstmt.setDouble(4, city.getLatitude());
            pstmt.setDouble(5, city.getLongitude());
            pstmt.setInt(6, city.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM cities WHERE id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
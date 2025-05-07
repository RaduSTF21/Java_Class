package org.example;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CountryDAO implements GenericDAO<Country> {

    @Override
    public void create(Country country) throws SQLException {
        if (country.getName() == null || country.getCode() == null || country.getContinentId() == null) {
            throw new SQLException("Country name, code, and continent ID cannot be null");
        }

        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, country.getName());
            pstmt.setString(2, country.getCode());
            pstmt.setInt(3, country.getContinentId());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    country.setId(keys.getInt(1));
                }
            }
        }
    }


    public void create(String name, int continentId) throws SQLException {
        Connection con = Database.getConnection();
        String code = name.length() > 3 ? name.substring(0, 3).toUpperCase() : name.toUpperCase();

        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, continentId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Country findById(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM countries WHERE id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Country country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCode(rs.getString("code"));
                    country.setContinentId(rs.getInt("continent_id"));
                    return country;
                }
            }
        }
        return null;
    }

    @Override
    public Country findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM countries WHERE name = ?")) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Country country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCode(rs.getString("code"));
                    country.setContinentId(rs.getInt("continent_id"));
                    return country;
                }
            }
        }
        return null;
    }


    public List<Country> findByContinent(int continentId) throws SQLException {
        List<Country> countries = new ArrayList<>();
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM countries WHERE continent_id = ?")) {

            pstmt.setInt(1, continentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Country country = new Country();
                    country.setId(rs.getInt("id"));
                    country.setName(rs.getString("name"));
                    country.setCode(rs.getString("code"));
                    country.setContinentId(rs.getInt("continent_id"));
                    countries.add(country);
                }
            }
        }

        return countries;
    }

    @Override
    public List<Country> findAll() throws SQLException {
        List<Country> countries = new ArrayList<>();
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM countries")) {

            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
                country.setContinentId(rs.getInt("continent_id"));
                countries.add(country);
            }
        }

        return countries;
    }

    @Override
    public void update(Country country) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "UPDATE countries SET name = ?, code = ?, continent_id = ? WHERE id = ?")) {

            pstmt.setString(1, country.getName());
            pstmt.setString(2, country.getCode());
            pstmt.setInt(3, country.getContinentId());
            pstmt.setInt(4, country.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM countries WHERE id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
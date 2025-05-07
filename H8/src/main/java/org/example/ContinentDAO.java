package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ContinentDAO implements GenericDAO<Continent> {

    @Override
    public void create(Continent continent) throws SQLException {
        if (continent.getName() == null) {
            throw new SQLException("Continent name cannot be null");
        }

        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents (name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, continent.getName());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    continent.setId(keys.getInt(1));
                }
            }
        }
    }


    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Continent findById(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM continents WHERE id = ?")) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Continent continent = new Continent();
                    continent.setId(rs.getInt("id"));
                    continent.setName(rs.getString("name"));
                    return continent;
                }
            }
        }
        return null;
    }


    public String findNameById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM continents WHERE id=" + id)) {

            return rs.next() ? rs.getString(1) : null;
        }
    }

    @Override
    public Continent findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM continents WHERE name='" + name + "'")) {

            if (rs.next()) {
                Continent continent = new Continent();
                continent.setId(rs.getInt("id"));
                continent.setName(rs.getString("name"));
                return continent;
            }
        }
        return null;
    }


    public Integer findIdByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id FROM continents WHERE name='" + name + "'")) {

            return rs.next() ? rs.getInt(1) : null;
        }
    }

    @Override
    public List<Continent> findAll() throws SQLException {
        List<Continent> continents = new ArrayList<>();
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT * FROM continents")) {

            while (rs.next()) {
                Continent continent = new Continent();
                continent.setId(rs.getInt("id"));
                continent.setName(rs.getString("name"));
                continents.add(continent);
            }
        }

        return continents;
    }

    @Override
    public void update(Continent continent) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "UPDATE continents SET name = ? WHERE id = ?")) {

            pstmt.setString(1, continent.getName());
            pstmt.setInt(2, continent.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM continents WHERE id = ?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
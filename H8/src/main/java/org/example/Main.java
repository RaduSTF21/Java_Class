package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            setupDatabase();


            var continentDAO = new ContinentDAO();
            var countryDAO = new CountryDAO();
            var cityDAO = new CityDAO();

            if (continentDAO.findAll().isEmpty()) {
                System.out.println("Creating sample data...");
                createSampleData(continentDAO, countryDAO, cityDAO);
            }


            menu();

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            Database.rollback();
        } finally {
            ConnectionPool.closePool();
        }
    }

    private static void setupDatabase() {
        try {
            var connection = Database.getConnection();

            try (var statement = connection.createStatement()) {

                statement.execute(
                        "CREATE TABLE IF NOT EXISTS continents (" +
                                "id SERIAL PRIMARY KEY, " +
                                "name VARCHAR(255) NOT NULL UNIQUE" +
                                ")"
                );


                statement.execute(
                        "CREATE TABLE IF NOT EXISTS countries (" +
                                "id SERIAL PRIMARY KEY, " +
                                "name VARCHAR(255) NOT NULL, " +
                                "code VARCHAR(3) NOT NULL, " +
                                "continent_id INTEGER, " +
                                "FOREIGN KEY (continent_id) REFERENCES continents(id)" +
                                ")"
                );


                statement.execute(
                        "CREATE TABLE IF NOT EXISTS cities (" +
                                "id SERIAL PRIMARY KEY, " +
                                "name VARCHAR(255) NOT NULL, " +
                                "country_id INTEGER NOT NULL, " +
                                "capital BOOLEAN NOT NULL DEFAULT false, " +
                                "latitude DOUBLE PRECISION NOT NULL, " +
                                "longitude DOUBLE PRECISION NOT NULL, " +
                                "FOREIGN KEY (country_id) REFERENCES countries(id)" +
                                ")"
                );
            }

            connection.commit();
            System.out.println("Database setup completed successfully");
        } catch (SQLException e) {
            System.err.println("Error setting up database: " + e.getMessage());
            Database.rollback();
        }
    }

    private static void createSampleData(ContinentDAO continentDAO, CountryDAO countryDAO, CityDAO cityDAO)
            throws SQLException {

        Continent europe = new Continent(null, "Europe");
        continentDAO.create(europe);

        Continent northAmerica = new Continent(null, "North America");
        continentDAO.create(northAmerica);


        Country romania = new Country(null, "Romania", "ROU", europe.getId());
        countryDAO.create(romania);

        Country ukraine = new Country(null, "Ukraine", "UKR", europe.getId());
        countryDAO.create(ukraine);

        Country usa = new Country(null, "United States", "USA", northAmerica.getId());
        countryDAO.create(usa);


        City bucharest = new City(null, "Bucharest", romania.getId(), true, 44.4268, 26.1025);
        cityDAO.create(bucharest);

        City kyiv = new City(null, "Kyiv", ukraine.getId(), true, 50.4501, 30.5234);
        cityDAO.create(kyiv);

        City newYork = new City(null, "New York", usa.getId(), false, 40.7128, 74.0060);
        cityDAO.create(newYork);

        City washington = new City(null, "Washington D.C.", usa.getId(), true, 38.9072, 77.0369);
        cityDAO.create(washington);

        Database.getConnection().commit();
        System.out.println("Sample data created successfully");
    }

    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n==== World Cities Database ====");
            System.out.println("1. List all continents");
            System.out.println("2. List all countries");
            System.out.println("3. List all cities");
            System.out.println("4. List cities by country");
            System.out.println("5. Import data from CSV");
            System.out.println("6. Calculate distance between two cities");
            System.out.println("7. Show distances between capital cities");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 0:
                        exit = true;
                        System.out.println("Exiting program. Goodbye!");
                        break;
                    case 1:
                        listAllContinents();
                        break;
                    case 2:
                        listAllCountries();
                        break;
                    case 3:
                        listAllCities();
                        break;
                    case 4:
                        System.out.print("Enter country name: ");
                        String countryName = scanner.nextLine();
                        listCitiesByCountry(countryName);
                        break;
                    case 5:
                        System.out.print("Enter CSV file path: ");
                        String filePath = scanner.nextLine();
                        importDataFromCSV(filePath);
                        break;
                    case 6:
                        System.out.print("Enter first city name: ");
                        String city1 = scanner.nextLine();
                        System.out.print("Enter second city name: ");
                        String city2 = scanner.nextLine();
                        calculateDistance(city1, city2);
                        break;
                    case 7:
                        showCapitalDistances();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void listAllContinents() throws SQLException {
        ContinentDAO continentDAO = new ContinentDAO();
        List<Continent> continents = continentDAO.findAll();

        System.out.println("\n=== All Continents ===");
        if (continents.isEmpty()) {
            System.out.println("No continents found in the database.");
        } else {
            for (Continent continent : continents) {
                System.out.println(continent);
            }
            System.out.println("Total: " + continents.size() + " continents");
        }
    }

    private static void listAllCountries() throws SQLException {
        CountryDAO countryDAO = new CountryDAO();
        ContinentDAO continentDAO = new ContinentDAO();
        List<Country> countries = countryDAO.findAll();

        System.out.println("\n=== All Countries ===");
        if (countries.isEmpty()) {
            System.out.println("No countries found in the database.");
        } else {
            for (Country country : countries) {
                Continent continent = continentDAO.findById(country.getContinentId());
                System.out.println(country + " (Continent: " +
                        (continent != null ? continent.getName() : "Unknown") + ")");
            }
            System.out.println("Total: " + countries.size() + " countries");
        }
    }

    private static void listAllCities() throws SQLException {
        CityDAO cityDAO = new CityDAO();
        CountryDAO countryDAO = new CountryDAO();
        List<City> cities = cityDAO.findAll();

        System.out.println("\n=== All Cities ===");
        if (cities.isEmpty()) {
            System.out.println("No cities found in the database.");
        } else {
            for (City city : cities) {
                Country country = countryDAO.findById(city.getCountryId());
                System.out.println(city + " (Country: " +
                        (country != null ? country.getName() : "Unknown") +
                        ", Capital: " + (city.isCapital() ? "Yes" : "No") + ")");
            }
            System.out.println("Total: " + cities.size() + " cities");
        }
    }

    private static void listCitiesByCountry(String countryName) throws SQLException {
        CountryDAO countryDAO = new CountryDAO();
        CityDAO cityDAO = new CityDAO();

        Country country = countryDAO.findByName(countryName);
        if (country == null) {
            System.out.println("Country not found: " + countryName);
            return;
        }

        List<City> cities = cityDAO.findByCountryId(country.getId());

        System.out.println("\n=== Cities in " + country.getName() + " ===");
        if (cities.isEmpty()) {
            System.out.println("No cities found for this country.");
        } else {
            for (City city : cities) {
                System.out.println(city + (city.isCapital() ? " (Capital)" : ""));
            }
            System.out.println("Total: " + cities.size() + " cities");
        }
    }

    private static void importDataFromCSV(String filePath) {
        try {
            DataImporter importer = new DataImporter();
            importer.importFromCSV(filePath);
            System.out.println("Data import completed successfully.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error during import: " + e.getMessage());
            Database.rollback();
        }
    }

    private static void calculateDistance(String cityName1, String cityName2) {
        try {
            DistanceCalculator calculator = new DistanceCalculator();
            double distance = calculator.getDistanceBetweenCities(cityName1, cityName2);
            System.out.printf("Distance between %s and %s: %.2f kilometers%n",
                    cityName1, cityName2, distance);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void showCapitalDistances() {
        try {
            DistanceCalculator calculator = new DistanceCalculator();
            calculator.displayCapitalDistances();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
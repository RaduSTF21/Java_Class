package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataImporter {
    private final ContinentDAO continentDAO;
    private final CountryDAO countryDAO;
    private final CityDAO cityDAO;
    private final Map<String, Integer> continentsMap = new HashMap<>();
    private final Map<String, Integer> countriesMap = new HashMap<>();

    public DataImporter() {
        this.continentDAO = new ContinentDAO();
        this.countryDAO = new CountryDAO();
        this.cityDAO = new CityDAO();
    }


    public void importFromCSV(String filePath) throws IOException, SQLException {
        System.out.println("Starting data import from " + filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 6) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String cityName = data[0].trim();
                String countryName = data[1].trim();
                String continentName = data[2].trim();
                boolean isCapital = Boolean.parseBoolean(data[3].trim());

                try {
                    double latitude = Double.parseDouble(data[4].trim());
                    double longitude = Double.parseDouble(data[5].trim());


                    Integer continentId = continentsMap.get(continentName);
                    if (continentId == null) {
                        Continent continent = continentDAO.findByName(continentName);
                        if (continent == null) {
                            Continent newContinent = new Continent(null, continentName);
                            continentDAO.create(newContinent);
                            continentId = newContinent.getId();
                        } else {
                            continentId = continent.getId();
                        }
                        continentsMap.put(continentName, continentId);
                    }


                    Integer countryId = countriesMap.get(countryName);
                    if (countryId == null) {
                        Country country = countryDAO.findByName(countryName);
                        if (country == null) {
                            String code = generateCountryCode(countryName);
                            Country newCountry = new Country(null, countryName, code, continentId);
                            countryDAO.create(newCountry);
                            countryId = newCountry.getId();
                        } else {
                            countryId = country.getId();
                        }
                        countriesMap.put(countryName, countryId);
                    }


                    City city = cityDAO.findByName(cityName);
                    if (city == null) {
                        cityDAO.create(cityName, countryId, isCapital, latitude, longitude);
                        System.out.println("Added city: " + cityName);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Invalid latitude/longitude for " + cityName + ": " + e.getMessage());
                }
            }

            Database.getConnection().commit();
            System.out.println("Data import completed successfully");
        } catch (Exception e) {
            Database.rollback();
            throw e;
        }
    }

    private String generateCountryCode(String countryName) {
        if (countryName == null || countryName.isEmpty()) {
            return "UNK";
        }

        String[] words = countryName.split("\\s+");
        if (words.length == 1) {
            return countryName.substring(0, Math.min(3, countryName.length())).toUpperCase();
        } else {
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < Math.min(3, words.length); i++) {
                if (!words[i].isEmpty()) {
                    code.append(words[i].charAt(0));
                }
            }


            String lastWord = words[words.length - 1];
            int index = 1;
            while (code.length() < 3 && index < lastWord.length()) {
                code.append(lastWord.charAt(index++));
            }

            return code.toString().toUpperCase();
        }
    }
}
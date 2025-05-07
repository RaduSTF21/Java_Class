-- Create continents table first
CREATE TABLE IF NOT EXISTS continents (
                                          id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                          name VARCHAR(100) NOT NULL
);

-- Create index for continent name lookups
CREATE INDEX idx_continent_name ON continents(name);

-- Create countries table with reference to continents
CREATE TABLE IF NOT EXISTS countries (
                                         id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                         name VARCHAR(100) NOT NULL,
                                         code VARCHAR(2) NOT NULL,
                                         continent_id INTEGER NOT NULL,
                                         CONSTRAINT fk_continent FOREIGN KEY (continent_id) REFERENCES continents(id)
);

-- Create indexes for country lookups
CREATE INDEX idx_country_name ON countries(name);
CREATE INDEX idx_country_code ON countries(code);
CREATE INDEX idx_country_continent ON countries(continent_id);

-- Create cities table with reference to countries
CREATE TABLE IF NOT EXISTS cities (
                                      id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                      country_id INTEGER NOT NULL,
                                      name VARCHAR(100) NOT NULL,
                                      is_capital BOOLEAN DEFAULT FALSE,
                                      latitude DECIMAL(10, 6),
                                      longitude DECIMAL(10, 6),
                                      CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES countries(id)
);

-- Create indexes for city lookups
CREATE INDEX idx_cities_country_id ON cities(country_id);
CREATE INDEX idx_cities_name ON cities(name);
CREATE INDEX idx_cities_is_capital ON cities(is_capital);

-- Insert some default data for continents
INSERT INTO continents (name) VALUES
                                  ('Africa'),
                                  ('Antarctica'),
                                  ('Asia'),
                                  ('Europe'),
                                  ('North America'),
                                  ('Oceania'),
                                  ('South America');
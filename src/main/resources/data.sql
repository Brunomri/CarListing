-- Users
INSERT INTO users (username, password, display_name, contact) VALUES ('JohnDoe', 'John123', 'John', 'john@email.com');
INSERT INTO users (username, password, display_name, contact) VALUES ('Mary', 'Mary123', 'Mary', 'mary@email.com');
INSERT INTO users (username, password, display_name, contact) VALUES ('Jose', 'Jose123', 'Jose', 'jose@email.com');
INSERT INTO users (username, password, display_name, contact) VALUES ('Mike', 'Mike123', 'Mike', 'mike@email.com');
INSERT INTO users (username, password, display_name, contact) VALUES ('Geralt', 'Geralt123', 'Geralt', 'geralt@email.com');

-- Roles
INSERT INTO role (type) VALUES ('user');
INSERT INTO role (type) VALUES ('admin');

-- User / Role mapping
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 2);

-- Cars
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Toyota', 'Corolla', '2020', 'SEG', 'Silver', 'AT', 'Gasoline', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Honda', 'Civic', '2018', 'EX', 'Black', 'AT', 'Gasoline', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Ford', 'F-150', '2017', 'Limited', 'Red', 'AT', 'Diesel', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Honda', 'Civic', '2009', 'SI', 'Red', 'MT', 'Gasoline', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('BMW', '320i', '2014', 'Sport GP', 'White', 'AT', 'Gasoline', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Chevrolet', 'Cruze', '2016', 'LT', 'Blue', 'AT', 'Gasoline', 1);
INSERT INTO car (make, model, year, trim, color, transmission, fuel, user_id) VALUES ('Dodge', 'Challenger', '2019', 'SRT8', 'Yellow', 'AT', 'Gasoline', 1);

-- Listings
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (35000, 0, 'Brand new car', 1, 1);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (30000, 45000, 'Used car', 2, 1);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (50000, 35000, 'Used truck', 3, 2);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (15000, 65000, 'Affordable sport car', 4, 3);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (40000, 20000, 'German saloon', 5, 4);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (18000, 70000, 'Highway use', 6, 4);
INSERT INTO listing (price, mileage, description, car_id, user_id) VALUES (60000, 15000, 'American muscle car', 7, 4);

-- Countries
INSERT INTO country (country_name) VALUES ('USA');
INSERT INTO country (country_name) VALUES ('Canada');
INSERT INTO country (country_name) VALUES ('Mexico');
INSERT INTO country (country_name) VALUES ('Brazil');

-- States
INSERT INTO state (country_id, state_name) VALUES ('1', 'Illinois');
INSERT INTO state (country_id, state_name) VALUES ('1', 'Indiana');
INSERT INTO state (country_id, state_name) VALUES ('1', 'Wisconsin');
INSERT INTO state (country_id, state_name) VALUES ('1', 'Michigan');
INSERT INTO state (country_id, state_name) VALUES ('2', 'British Columbia');
INSERT INTO state (country_id, state_name) VALUES ('4', 'Sao Paulo');


-- Cities
INSERT INTO city (state_id, city_name) VALUES ('1', 'Chicago');
INSERT INTO city (state_id, city_name) VALUES ('1', 'Evanston');
INSERT INTO city (state_id, city_name) VALUES ('1', 'Naperville');
INSERT INTO city (state_id, city_name) VALUES ('1', 'Des Plaines');
INSERT INTO city (state_id, city_name) VALUES ('2', 'Indianapolis');
INSERT INTO city (state_id, city_name) VALUES ('3', 'Milwaukee');
INSERT INTO city (state_id, city_name) VALUES ('4', 'Grand Rapids');
INSERT INTO city (state_id, city_name) VALUES ('4', 'Detroit');
INSERT INTO city (state_id, city_name) VALUES ('5', 'Victoria');
INSERT INTO city (state_id, city_name) VALUES ('6', 'Sao Paulo');

-- Locations
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('1', '1', '1', '1');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('2', '1', '1', '2');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('3', '1', '1', '3');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('4', '2', '5', '9');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('5', '2', '5', '9');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('6', '4', '6', '10');
INSERT INTO location (listing_id, country_id, state_id, city_id) VALUES ('7', '4', '6', '10');
DROP DATABASE IF EXISTS building_company;
CREATE DATABASE building_company;
USE building_company;

CREATE TABLE country (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE city (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    country_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES country(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE address (
    id INT AUTO_INCREMENT,
    line_1 VARCHAR(50),
    line_2 VARCHAR(50),
    line_3 VARCHAR(50),
    country_id INT,
    city_id INT NOT NULL,
    zipcode VARCHAR(20),
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (city_id) REFERENCES city(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE industry (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE company (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    address_id INT NOT NULL,
    industry_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (industry_id) REFERENCES industry(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE employee_role (
    id INT AUTO_INCREMENT,
    title VARCHAR(50),
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE employee (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50) NOT NULL,
    company_id INT NOT NULL,
    employee_role_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_role_id) REFERENCES employee_role(id),
    FOREIGN KEY (company_id) REFERENCES company(id)
);
CREATE TABLE material (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    length_meters DEC(14, 2),
    width_meters DEC(14, 2),
    height_meters DEC(14, 2),
    weight_kg DEC(14, 2),
    PRIMARY KEY (id)
);
CREATE TABLE tool (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    capacity_cubicmeters DEC(14, 2),
    max_load_kg DEC(14, 2),
    weight_kg DEC(14, 2),
    PRIMARY KEY (id)
);

-- CREATE TABLE material_supplier_inventory (
--     id INT AUTO_INCREMENT,
--     company_id INT NOT NULL,
--     material_id INT NOT NULL,
--     amount INT NOT NULL,
--     cost_per_amount DEC(14, 2) NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (company_id) REFERENCES company(id),
--     FOREIGN KEY (material_id) REFERENCES material(id)
-- );
-- CREATE TABLE labor_supplier_laborers (
--     id INT AUTO_INCREMENT,
--     company_id INT NOT NULL,
--     employee_id INT NOT NULL,
--     hourly_pay_rate DEC(14, 2) NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (company_id) REFERENCES company(id),
--     FOREIGN KEY (employee_id) REFERENCES employee(id)
-- );
-- CREATE TABLE tool_supplier_inventory (
--     id INT AUTO_INCREMENT,
--     company_id INT NOT NULL,
--     tool_id INT NOT NULL,
--     daily_rental_rate DEC(14, 2) NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (company_id) REFERENCES company(id),
--     FOREIGN KEY (tool_id) REFERENCES tool(id)
-- );

CREATE TABLE country_material_cost (
    id INT AUTO_INCREMENT,
    country_id INT NOT NULL,
    material_id INT NOT NULL,
    avg_cost_per_amount DEC(14, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country(id),
    FOREIGN KEY (material_id) REFERENCES material(id)
);
CREATE TABLE country_tool_cost (
    id INT AUTO_INCREMENT,
    country_id INT NOT NULL,
    tool_id INT NOT NULL,
    avg_daily_rental_rate DEC(14, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country(id),
    FOREIGN KEY (tool_id) REFERENCES tool(id)
);
CREATE TABLE country_labor_cost (
    id INT AUTO_INCREMENT,
    country_id INT NOT NULL,
    employee_role_id INT NOT NULL,
    avg_hourly_pay_rate DEC(14, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES country(id),
    FOREIGN KEY (employee_role_id) REFERENCES employee_role(id)
);

CREATE TABLE building_type (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    base_cost DEC(14, 2) NOT NULL,
    min_foundation_sqrmeters DEC(14, 2),
    max_foundation_sqrmeters DEC(14, 2),
    PRIMARY KEY (id)
);
CREATE TABLE building_type_material_req (
    id INT AUTO_INCREMENT,
    building_type_id INT NOT NULL,
    material_id INT NOT NULL,
    foundation_amount_per_sqrmeter DEC(14, 2),
    amount_per_sqrmeter DEC(14, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (building_type_id) REFERENCES building_type(id),
    FOREIGN KEY (material_id) REFERENCES material(id)
);
CREATE TABLE building_type_tool_req (
    id INT AUTO_INCREMENT,
    building_type_id INT NOT NULL,
    tool_id INT NOT NULL,
    foundation_hours_of_rental_per_sqrmeter DEC(14, 2),
    hours_of_rental_per_sqrmeter DEC(14, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (building_type_id) REFERENCES building_type(id),
    FOREIGN KEY (tool_id) REFERENCES tool(id)
);
CREATE TABLE building_type_labor_req (
    id INT AUTO_INCREMENT,
    building_type_id INT NOT NULL,
    employee_role_id INT NOT NULL,
    base_hours_of_work DEC(14, 2) NOT NULL,
    foundation_hours_of_work_per_sqrmeter DEC(14, 2),
    hours_of_work_per_sqrmeter DEC(14, 2),
    PRIMARY KEY (id),
    FOREIGN KEY (building_type_id) REFERENCES building_type(id),
    FOREIGN KEY (employee_role_id) REFERENCES employee_role(id)
);


INSERT INTO country(name) VALUES ('USA'), ('Brazil'), ('Ukraine'), ('Japan');

INSERT INTO city(name, country_id) VALUES
    ('Tokyo', 4),
    ('Sao Paulo', 2),
    ('Los Angeles', 1),
    ('Kiev', 3);

CREATE TRIGGER city_country_bi BEFORE INSERT ON address
FOR EACH ROW
SET NEW.country_id = (SELECT country_id FROM city WHERE city.id = NEW.city_id);

CREATE TRIGGER city_country_bu BEFORE UPDATE ON address
FOR EACH ROW
SET NEW.country_id = (SELECT country_id FROM city WHERE city.id = NEW.city_id);

INSERT INTO address(city_id) VALUES (1), (2), (3), (4);

INSERT INTO industry(name) VALUES
    ('Construction'), ('Materials'), ('Labor'), ('Tools');

INSERT INTO company(name, address_id, industry_id) VALUES
    ('Construction Co', 1, 1),
    ('Matt Materials', 2, 2),
    ('Grunts Labor', 3, 3),
    ('Big Tools', 4, 4);

INSERT INTO employee_role(name) VALUES 
    ('Admin'), ('Supervisor'), ('Laborer'), ('Operator'), ('Security'), ('Salesman'), ('Inspector');

INSERT INTO employee(email, company_id, employee_role_id) VALUES
    ('a_construct@mail.com', 1, 2),
    ('matt@mail.com', 2, 1),
    ('grunt_y@mail.com', 3, 3),
    ('tool_big@mail.com', 4, 6);

INSERT INTO material(name) VALUES 
    ('2x4'), 
    ('m^3 Cement'), 
    ('Steel I-Beam');

INSERT INTO tool(name) VALUES 
    ('Digger'), 
    ('Crane'), 
    ('Dirt Truck');

-- INSERT INTO material_supplier_inventory(company_id, material_id, amount, cost_per_amount) VALUES
--     (2, 1, 7890, 2.99),
--     (2, 2, 84420, 10.00);

-- INSERT INTO labor_supplier_laborers(company_id, employee_id, hourly_pay_rate) VALUES
--     (3, 3, 10.00);

-- INSERT INTO tool_supplier_inventory(company_id, tool_id, daily_rental_rate) VALUES
--     (4, 1, 500.00),
--     (4, 2, 1000.00),
--     (4, 3, 300.00);

INSERT INTO country_material_cost(country_id, material_id, avg_cost_per_amount)VALUES
    (1, 1, 5.0),
    (1, 2, 20.0),
    (1, 3, 40.0),
    (4, 1, 6.0),
    (4, 2, 22.0),
    (4, 3, 44.0);

INSERT INTO country_tool_cost(country_id, tool_id, avg_daily_rental_rate) VALUES
    (1, 1, 500.0),
    (1, 2, 1000.0),
    (1, 3, 300.0),
    (4, 1, 550.0),
    (4, 2, 1100.0),
    (4, 3, 330.0);

INSERT INTO country_labor_cost(country_id, employee_role_id, avg_hourly_pay_rate) VALUES
    (1, 1, 20.0),
    (1, 2, 25.0),
    (1, 3, 20.0),
    (1, 4, 30.0),
    (1, 5, 15.0),
    (1, 6, 15.0),
    (1, 7, 30.0),
    (4, 1, 15.0),
    (4, 2, 20.0),
    (4, 3, 15.0),
    (4, 4, 25.0),
    (4, 5, 10.0),
    (4, 6, 10.0),
    (4, 7, 25.0);

INSERT INTO building_type(name, base_cost) VALUES
    ('Family Home', 10000.00),
    ('Warehouse', 20000.00),
    ('Skyscraper', 1000000.00);

INSERT INTO building_type_material_req(building_type_id, material_id, amount_per_sqrmeter) VALUES
    (1, 1, 12.0),
    (1, 2, 0.5),
    (2, 2, 1.0),
    (2, 3, 0.5),
    (3, 2, 1.8),
    (3, (SELECT id FROM material WHERE name = 'Steel I-Beam'), 1.0);

INSERT INTO building_type_tool_req(building_type_id, tool_id, hours_of_rental_per_sqrmeter) VALUES
    (1, 1, 0.05),
    (1, 3, 0.05),
    (
        (SELECT id FROM building_type WHERE name = 'Warehouse'), 
        (SELECT id FROM tool WHERE name = 'Digger'),
        0.2
    ),
    (2, 2, 0.2),
    (2, 3, 0.1),
    (3, 1, 0.1),
    (3, 2, 0.5),
    (3, 3, 0.1);

INSERT INTO building_type_labor_req(building_type_id, employee_role_id, base_hours_of_work) VALUES
    (1, 2, 100),
    (1, 3, 2000),
    (1, 7, 5),
    (2, 2, 200),
    (2, 3, 1000),
    (2, 4, 100),
    (2, 7, 5),
    (3, 2, 500),
    (3, 3, 4000),
    (3, 4, 1000),
    (3, 5, 4000),
    (3, 7, 1000);

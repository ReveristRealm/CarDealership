DROP DATABASE IF EXISTS dealership;

CREATE DATABASE dealership;

USE dealership;

CREATE TABLE dealership(
dealership_id INT AUTO_INCREMENT PRIMARY KEY,
namee VARCHAR(20),
address VARCHAR(20),
phone VARCHAR(12)
);

CREATE TABLE vehicles(
vin VARCHAR(5) PRIMARY KEY,
yearr INT,
make VARCHAR(15),
model VARCHAR(10),
color VARCHAR(10),
odometer INT,
price DOUBLE,
sold BOOLEAN
);

CREATE TABLE inventory(
dealership_id INT,
vin VARCHAR(5),
FOREIGN KEY (dealership_id) REFERENCES dealership(dealership_id),
FOREIGN KEY (VIN) REFERENCES vehicles(VIN)
);

CREATE TABLE sales_contracts(
id INT AUTO_INCREMENT PRIMARY KEY,
vin VARCHAR(5),
sale_date DATE,
price DECIMAL(10,2),
FOREIGN KEY(vin) REFERENCES vehicles(vin)
);

CREATE TABLE lease_contracts(
contract_id INT AUTO_INCREMENT PRIMARY KEY,
    vin VARCHAR(15),
    lease_start DATE,
    lease_end DATE,
    monthly_payment DECIMAL(10, 2),
    FOREIGN KEY (VIN) REFERENCES vehicles(VIN)
);

INSERT INTO dealership(namee, address, phone) 
VALUES
('Los Santos Customs', 'California', '345-532-4542'),
('Danny Hot Wheels', 'New York City', '534-234-2355'),
('Javi Wheel Deals', 'Florida', '962-432-2344');

INSERT INTO vehicles(vin, yearr, make, model, color, odometer, price, sold) VALUES
('4387', 2022, 'Mercedes Benz', 'A-Class', 'Blue',10000, 56000.00, FALSE),
('5709', 2022, 'Audi', 'A3', 'Black', 20000, 80000.00, FALSE),
('2943', 2023, 'Hyundai', 'Accent', 'Grey', 56000, 40000.00, FALSE),
('8104', 2023, 'Lamborghini', 'Aventador', 'Yellow', 5, 206000.00, TRUE),
('4234', 2015, 'Tesla', 'Model S', 'White', 30000, 25000.00, FALSE),
('8032', 2012, 'Jeep', 'Wrangler', 'Black', 19000, 10000.00, FALSE),
('4925', 2018, 'Audi', 'A4', 'Silver', 100, 65000.00, FALSE),
('7384', 2017, 'Lexus', 'RX 350', 'Green', 5000, 56000.00, FALSE),
('6284', 2010, 'Dodge', 'Charger', 'Red', 10000, 23000.00, TRUE),
('3224', 2022, 'Chevrolet', 'Camaro ZL1', 'Blue', 5000, 45000.00, FALSE),
('2034', 2023, 'Ford', 'Mustang', 'Sky Blue', 2000, 34200.00, TRUE);

INSERT INTO inventory 
VALUES
(1, '4387'),
(1, '5709'),
(1, '2943'),
(2, '8104'),
(3, '4234'),
(3, '8032'),
(2, '6284'),
(2, '3224'),
(2, '2034'),
(3, '7384'),
(3, '4925');


INSERT INTO sales_contracts(vin , sale_date, price) 
VALUES
('8104' , '2020-10-03', 20000.00),
('2034' , '2023-06-07', 40000.00);

INSERT INTO lease_contracts (vin , lease_start, lease_end, monthly_payment)
VALUES
('6284', '2020-10-10', '2020-05-05', 450.00);


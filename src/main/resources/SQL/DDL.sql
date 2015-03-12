DROP TABLE IF EXISTS rms.PastTickets;
DROP TABLE IF EXISTS rms.Tickets;
DROP TABLE IF EXISTS rms.TicketTypes;
DROP TABLE IF EXISTS rms.Restaurants;
DROP TABLE IF EXISTS rms.Districts;
DROP TABLE IF EXISTS rms.Areas;
DROP TABLE IF EXISTS rms.Customers;

CREATE TABLE rms.Customers
(
ID INT PRIMARY KEY AUTO_INCREMENT,
RegID TEXT
);

CREATE TABLE rms.Areas
(
ID TINYINT PRIMARY KEY,
Name VARCHAR(20)
);

CREATE TABLE rms.Districts
(
ID TINYINT PRIMARY KEY AUTO_INCREMENT,
Name VARCHAR(20),
AreaID TINYINT,
FOREIGN KEY (AreaID) REFERENCES rms.Areas(ID)
);

CREATE TABLE rms.Restaurants
(
ID INT PRIMARY KEY AUTO_INCREMENT,
Password VARCHAR(50) DEFAULT '40be4e59b9a2a2b5dffb918c0e86b3d7',
Name TINYTEXT,
DistrictID TINYINT,
Address TINYTEXT,
PhoneNo VARCHAR(50),
OpeningHours VARCHAR(50),
Description TINYTEXT,
Availability BIT(1) DEFAULT FALSE,
FOREIGN KEY (DistrictID) REFERENCES rms.Districts(ID)
);

CREATE TABLE rms.TicketTypes
(
RestaurantID INT,
Type TINYINT,
MaxSize TINYINT,
FOREIGN KEY (RestaurantID) REFERENCES rms.Restaurants(ID),
CONSTRAINT TicketTypeID PRIMARY KEY (RestaurantID, Type)
);

CREATE TABLE rms.Tickets
(
CustomerID INT DEFAULT NULL,
RestaurantID INT,
Type TINYINT,
Size TINYINT,
Number INT,
Position INT,
Duration INT,
GetTime DATETIME DEFAULT NOW(),
CallTime DATETIME NULL DEFAULT NULL,
Validity BIT(1) DEFAULT TRUE,
FOREIGN KEY (CustomerID) REFERENCES rms.Customers(ID),
FOREIGN KEY (RestaurantID, Type) REFERENCES rms.TicketTypes(RestaurantID, Type),
CONSTRAINT TicketID PRIMARY KEY (RestaurantID, Type, Number)
);

CREATE TABLE rms.PastTickets
(
CustomerID INT DEFAULT NULL,
RestaurantID INT,
Type TINYINT,
Size TINYINT,
Number INT,
Position INT,
Duration INT,
GetTime DATETIME DEFAULT NOW(),
CallTime DATETIME NULL DEFAULT NULL,
Validity BIT(1) DEFAULT TRUE,
FOREIGN KEY (CustomerID) REFERENCES rms.Customers(ID),
FOREIGN KEY (RestaurantID, Type) REFERENCES rms.TicketTypes(RestaurantID, Type),
CONSTRAINT TicketID PRIMARY KEY (RestaurantID, Type, Number, GetTime)
);
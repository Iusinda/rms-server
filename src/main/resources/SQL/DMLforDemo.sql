INSERT INTO rms.Customers (RegID) VALUES ('Regid123');
INSERT INTO rms.Customers (RegID) VALUES ('Regid234');
INSERT INTO rms.Customers (RegID) VALUES ('Regid345');
INSERT INTO rms.Customers (RegID) VALUES ('Regid456');
INSERT INTO rms.Customers (RegID) VALUES ('Regid567');
INSERT INTO rms.Customers (RegID) VALUES ('Regid678');
INSERT INTO rms.Customers (RegID) VALUES ('Regid789');
INSERT INTO rms.Customers (RegID) VALUES ('Regid890');

INSERT INTO rms.Restaurants (Name, DistrictID, Address, PhoneNo, OpeningHours, Description, Availability)
VALUES ('Restaurant and Bar',11,'123 Happy Street','23332333','Mon - Thu: 09:00 - 01:00, Fri - Sun: 09:00 - 03:00','We serve food and drinks.',TRUE);
INSERT INTO rms.Restaurants (Name, DistrictID, Address, PhoneNo, OpeningHours, Description, Availability)
VALUES ('Chinese Restaurant',47,'2/F King Plaza','24442444','Mon - Sun: 06:00 - 23:00','We have Dim Sum.',TRUE);
INSERT INTO rms.Restaurants (Name) VALUES ('Invisible Restaurant');

INSERT INTO rms.TicketTypes VALUES (1,'A',2);
INSERT INTO rms.TicketTypes VALUES (1,'B',4);
INSERT INTO rms.TicketTypes VALUES (1,'C',6);
INSERT INTO rms.TicketTypes VALUES (1,'D',10);
INSERT INTO rms.TicketTypes VALUES (2,'A',2);
INSERT INTO rms.TicketTypes VALUES (2,'B',5);
INSERT INTO rms.TicketTypes VALUES (2,'C',18);

INSERT INTO rms.Tickets VALUES (1,'D',12,1,'2015-02-02 21:01:45.00001','2015-02-02 21:27:05',FALSE);
INSERT INTO rms.Tickets VALUES (2,'A',1,1,'2015-02-02 21:00:50.000002','2015-02-02 21:28:02',FALSE);
INSERT INTO rms.Tickets VALUES (2,'A',1,2,'2015-02-02 21:01:50.000003','2015-02-02 21:29:02',FALSE);
INSERT INTO rms.Tickets VALUES (2,'A',2,1,'2015-02-02 21:02:12.000004','2015-02-02 21:36:23',TRUE);
INSERT INTO rms.Tickets VALUES (2,'A',2,2,'2015-02-02 21:04:10.000005','2015-02-02 21:37:33',TRUE);
INSERT INTO rms.Tickets VALUES (2,'A',2,3,'2015-02-02 21:14:10.000007','2015-02-02 21:38:33',TRUE);
INSERT INTO rms.Tickets VALUES (2,'A',1,4,'2015-02-02 21:16:45.000008',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (2,'A',2,5,'2015-02-02 21:26:45.000009',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (2,'A',2,6,'2015-02-02 21:27:00.000010',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (2,'B',3,7,'2015-02-02 21:28:14.000011',NULL,FALSE);
INSERT INTO rms.Tickets (RestaurantID, Type, Size, CustomerID) VALUES (2,'C',5, 7);
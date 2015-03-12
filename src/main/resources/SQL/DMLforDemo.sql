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

INSERT INTO rms.TicketTypes VALUES (1,1,2);
INSERT INTO rms.TicketTypes VALUES (1,2,4);
INSERT INTO rms.TicketTypes VALUES (1,3,6);
INSERT INTO rms.TicketTypes VALUES (1,4,10);
INSERT INTO rms.TicketTypes VALUES (2,1,2);
INSERT INTO rms.TicketTypes VALUES (2,2,5);
INSERT INTO rms.TicketTypes VALUES (2,3,18);
INSERT INTO rms.TicketTypes VALUES (3,1,1);

INSERT INTO rms.Tickets VALUES (1,1,4,9,1,1,10,'2015-02-02 17:00:00','2015-02-02 17:27:05',FALSE);
INSERT INTO rms.Tickets VALUES (1,2,1,1,1,1,11,'2015-02-02 18:11:11','2015-02-02 18:38:02',FALSE);
INSERT INTO rms.Tickets VALUES (1,2,1,1,2,2,22,'2015-02-02 18:22:22','2015-02-02 18:49:02',FALSE);
INSERT INTO rms.Tickets VALUES (1,2,1,1,3,3,33,'2015-02-02 18:33:33','2015-02-02 19:00:23',TRUE);
INSERT INTO rms.Tickets VALUES (2,2,1,2,4,4,44,'2015-02-02 18:44:44','2015-02-02 19:37:33',TRUE);
INSERT INTO rms.Tickets VALUES (3,2,1,2,5,5,55,'2015-02-02 18:55:55','2015-02-02 19:38:33',TRUE);
INSERT INTO rms.Tickets VALUES (4,2,1,1,6,6,66,'2015-02-02 19:06:06',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (5,2,1,2,7,7,77,'2015-02-02 19:17:17',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (6,2,1,2,8,8,88,'2015-02-02 19:28:28',NULL,TRUE);
INSERT INTO rms.Tickets VALUES (7,2,2,3,1,9,99,'2015-02-02 19:39:39',NULL,FALSE);
INSERT INTO rms.Tickets (RestaurantID, Type, Size, Number, Position, Duration) VALUES (2,3,9,1,1,0);
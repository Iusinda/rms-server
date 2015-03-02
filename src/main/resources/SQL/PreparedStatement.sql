--Customer

--C first-time use
INSERT INTO rms.Customers (RegID) VALUES ('?');--done
SELECT LAST_INSERT_ID();

--S call customer
SELECT RegID FROM rms.Customers WHERE ID = ?; --done

----------

--Area

--C list areas
SELECT * FROM rms.Areas; --done

----------
--District

--C list districts
SELECT ID, Name FROM rms.Districts WHERE AreaID = ?; --done

----------

--Restaurant

--C list restaurants
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE;--done

--C search and/or filter restaurants
--filter nothing
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND Name LIKE '%?%';
--filter Area only
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND DistrictID IN (SELECT ID FROM rms.Districts WHERE AreaID = ?) AND Name LIKE '%?%';
--filter Area and District
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND DistrictID = ? AND Name LIKE '%?%';

--C choose and view a restaurant
--S show info before modifying
SELECT * FROM rms.Restaurants WHERE ID = ?;

--S login
SELECT Availability FROM rms.Restaurants WHERE ID = ? AND Password = '?';

--S turn on waiting list
UPDATE rms.Restaurants SET Availability = TRUE WHERE ID = ?;

--S turn off waiting list
SELECT LastUpdate FROM rms.Restaurants WHERE ID = ?;
UPDATE rms.Restaurants SET Availability = FALSE, LastUpdate = NOW(6) WHERE ID = ?;

--S modify restaurant info
UPDATE rms.Restaurants SET Name = '?', DistrictID = ?, Address = '?', PhoneNo = '?', OpeningHours = '?', Description = '?' WHERE ID = ?;

--S change password
UPDATE rms.Restaurants SET Password = '?' WHERE ID = ? AND Password = '?';

----------

--Ticket

--C create ticket
INSERT INTO rms.Tickets (RestaurantID, Type, Size, CustomerID) VALUES (?, '?', ?, ?);
--S create ticket
INSERT INTO rms.Tickets (RestaurantID, Type, Size) VALUES (?, '?', ?);

--S show waiting list
SELECT CustomerID, Size, GetTime, CallTime FROM rms.Tickets WHERE RestaurantID = ? AND Type = '?' AND Validity = TRUE ORDER BY GetTime;

--C view ticket
SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? AND Type = '?' AND GetTime < '?' AND Validity = TRUE AND CallTime IS NULL;

--S call ticket
UPDATE rms.Tickets SET CallTime = NOW() WHERE RestaurantID = ? AND Type = '?' AND GetTime = '?';

--S remove ticket
UPDATE rms.Tickets SET Validity = FALSE WHERE RestaurantID = ? AND Type = '?' AND GetTime = '?';

--S turn off waiting list: record tickets of each type
SELECT GetTime, CallTime FROM rms.Tickets WHERE RestaurantID = ? AND Type = '?' AND CallTime > (SELECT LastUpdate FROM rms.Restaurants WHERE ID = ?);
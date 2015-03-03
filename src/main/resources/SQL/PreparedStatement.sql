--Customer.create(String regId)
INSERT INTO rms.Customers (RegID) VALUES (?);
SELECT LAST_INSERT_ID();
--Customer.getCustomer(Integer id)
SELECT * FROM rms.Customers WHERE ID = ?; --done

--Area.listAreas()
SELECT * FROM rms.Areas; --done

--District.listDistricts(Integer areaId)
SELECT ID, Name FROM rms.Districts WHERE AreaID = ?; --done

--Restaurant
--getRestaurant(Integer id)
SELECT * FROM rms.Restaurants WHERE ID = ?;
--getRestaurants()
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE;--done
--getRestaurants(String name)
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND Name LIKE '%?%';
--getRestaurantsInArea(Integer areaId, String name)
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND DistrictID IN (SELECT ID FROM rms.Districts WHERE AreaID = ?) AND Name LIKE '%?%';
--getRestaurantsInDistrict(Integer districtId, String name)
SELECT ID, Name FROM rms.Restaurants WHERE Availability = TRUE AND DistrictID = ? AND Name LIKE '%?%';
--getValidation(Integer id, String password)
SELECT COUNT(*) FROM rms.Restaurants WHERE ID = ? AND Password = ?;
--update(Integer id, Integer districtId, String name, String address, String phoneNo, String openingHours, String description, Boolean availability)
UPDATE rms.Restaurants SET Name = ?, DistrictID = ?, Address = ?, PhoneNo = ?, OpeningHours = ?, Description = ? WHERE ID = ?;
--updateAvailability(Integer id, boolean available)
UPDATE rms.Restaurants SET Availability = TRUE WHERE ID = ?;
UPDATE rms.Restaurants SET Availability = FALSE, LastUpdate = NOW(6) WHERE ID = ?;
--updatePassword(Integer id, String oldPassword, String newPassword)
UPDATE rms.Restaurants SET Password = ? WHERE ID = ? AND Password = ?;

--Ticket
--createTicket(Integer restaurantId, Character type, Integer size, Integer customerId)
INSERT INTO rms.Tickets (RestaurantID, Type, Size, CustomerID) VALUES (?, ?, ?, ?);
--createTicket(Integer restaurantId, Character type, Integer size)
INSERT INTO rms.Tickets (RestaurantID, Type, Size) VALUES (?, ?, ?);
--findTickets(Integer restaurantId, Character type)
SELECT * FROM rms.Tickets WHERE RestaurantID = ? AND Type = ? AND Validity = TRUE ORDER BY GetTime;
--findUnrecordedTickets(Integer restaurantId, Character type)
SELECT GetTime, CallTime FROM rms.Tickets WHERE RestaurantID = ? AND Type = ? AND CallTime > (SELECT LastUpdate FROM rms.Restaurants WHERE ID = ?);
--findNoOfTicketsAhead(Integer restaurantId, Character type, Timestamp getTime)
SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? AND Type = ? AND GetTime < ? AND Validity = TRUE AND CallTime IS NULL;
--updateCallTime(Integer restaurantId, Character type, Timestamp getTime)
UPDATE rms.Tickets SET CallTime = NOW() WHERE RestaurantID = ? AND Type = ? AND GetTime = ?;
--updateValidity(Integer restaurantId, Character type, Timestamp getTime)
UPDATE rms.Tickets SET Validity = FALSE WHERE RestaurantID = ? AND Type = ? AND GetTime = ?;
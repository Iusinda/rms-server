package fyp.rms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.entity.Restaurant;

public class RestaurantMapper implements RowMapper<Restaurant> {
	public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(rs.getString("Address"));
		restaurant.setDescription(rs.getString("Description"));
		restaurant.setDistrictId(rs.getInt("DistrictID"));
		restaurant.setId(rs.getInt("ID"));
		restaurant.setName(rs.getString("Name"));
		restaurant.setOpeningHours(rs.getString("OpeningHours"));
		restaurant.setPhoneNo(rs.getString("PhoneNo"));
		restaurant.setAvailability(rs.getBoolean("Availability"));
		return restaurant;
	}
}

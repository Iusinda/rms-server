package fyp.rms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.entity.Restaurant;

public class SimpleRestaurantMapper implements RowMapper<Restaurant> {
	public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(rs.getInt("ID"));
		restaurant.setName(rs.getString("Name"));
		restaurant.setAddress(rs.getString("Address"));
		return restaurant;
	}
}

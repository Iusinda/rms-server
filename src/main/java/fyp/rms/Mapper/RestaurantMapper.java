package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.Restaurant;

/*
 * 	private Integer id;
 private Integer districtId;
 private String password;
 private String name;
 private String address;
 private String phoneNo;
 private String openingHours;
 private String description;
 private Boolean status;
 * */
public class RestaurantMapper implements RowMapper<Restaurant>{
	public Restaurant mapRow(ResultSet rs, int rowNum) throws SQLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(rs.getString("Address"));
		restaurant.setDescription(rs.getString("Description"));
		restaurant.setDistrictId(rs.getInt("DistrictID"));
		restaurant.setId(rs.getInt("RestaurantID"));
		restaurant.setName(rs.getString("Name"));
		restaurant.setOpeningHours(rs.getString("OpeningHours"));
		restaurant.setPassword(rs.getString("Password"));
		restaurant.setPhoneNo(rs.getString("PhoneNo"));
		restaurant.setStatus(rs.getBoolean("Status"));
		return restaurant;
	}
}

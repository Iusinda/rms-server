package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Restaurant;

/*
 private Integer id;
 private Integer districtId;
 private String password;
 private String name;
 private String address;
 private String phoneNo;
 private String openingHours;
 private String description;
 private Boolean status;
 * */

public interface RestaurantDAO {
	public void setDataSource(DataSource ds);

	public void create(Integer districtId, String name, String address,
			String phoneNo, String openingHours, String description,
			Boolean availability);

	public Restaurant getRestaurant(Integer id);

	public List<Restaurant> listRestaurants();

	public void delete(Integer id);

	public void update(Integer id, Integer districtId, String name,
			String address, String phoneNo, String openingHours,
			String description, Boolean availability);
}

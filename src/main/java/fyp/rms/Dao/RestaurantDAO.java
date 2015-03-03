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

	public int createRestaurant(String name, Integer districtId,
			String address, String phoneNo, String openingHours,
			String description, Boolean availability);

	public Restaurant findRestaurant(Integer id);

	public List<Restaurant> findRestaurants(String name);

	public List<Restaurant> findRestaurantsByDistrictId(Integer districtId,
			String name);

	public List<Restaurant> findRestaurantsByAreaId(Integer areaId, String name);

	public List<Restaurant> findAllRestaurants();

	public boolean getValidation(Integer id, String password);

	public void updateRestaurant(Integer id, Integer districtId, String name,
			String address, String phoneNo, String openingHours,
			String description, Boolean availability);

	public boolean updateAvailability(Integer id, boolean available);

	public boolean updatePassword(Integer id, String oldPassword,
			String newPassword);

	public void deleteRestaurant(Integer id);
}

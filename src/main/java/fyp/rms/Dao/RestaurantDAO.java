package fyp.rms.Dao;

import java.sql.Timestamp;
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

	public int create(Restaurant restaurant);

	public Restaurant find(Integer id);

	public List<Restaurant> find(String name);

	public List<Restaurant> findByDistrictId(Integer districtId,
			String name);

	public List<Restaurant> findByAreaId(Integer areaId, String name);

	public List<Restaurant> findAll();

	public int authenticate(Integer id, String password);
	
	public int update(Restaurant restaurant);

	public int updateAvailability(Integer id, boolean available);
	
	public int updateAvailability(Integer id, boolean availability, Timestamp lastUpdate);

	public int updatePassword(Integer id, String oldPassword,
			String newPassword);

	public int delete(Integer id);
}

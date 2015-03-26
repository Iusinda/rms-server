package fyp.rms.dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.entity.Restaurant;


public interface RestaurantDAO {
	public void setDataSource(DataSource ds);

	public int create(Restaurant restaurant);

	public Restaurant find(Integer id);

	public List<Restaurant> findByName(String name);

	public List<Restaurant> findByDistrict(Integer districtId,
			String name);

	public List<Restaurant> findByArea(Integer areaId, String name);

	public List<Restaurant> findAll();

	public int authenticate(Integer id, String password);
	
	public int update(Restaurant restaurant);

	public int updateAvailability(Integer id, boolean availability);

	public int updatePassword(Integer id, String oldPassword,
			String newPassword);

	public int delete(Integer id);
}

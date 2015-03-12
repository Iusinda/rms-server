package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.Restaurant;
import fyp.rms.Mapper.RestaurantMapper;

public class RestaurantJDBCTemplate implements RestaurantDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(Restaurant restaurant) {
		String SQL = "INSERT INTO rms.Restaurants"
				+ " (Name, DistrictID, Address, PhoneNo, OpeningHours, Description)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL,
				new Object[] { restaurant.getDistrictId(),
						restaurant.getName(), restaurant.getAddress(),
						restaurant.getPhoneNo(), restaurant.getOpeningHours(),
						restaurant.getDescription() });

		SQL = "SELECT LAST_INSERT_ID()";
		return jdbcTemplateObject.queryForInt(SQL);
	}

	@Override
	public Restaurant find(Integer id) {
		String SQL = "SELECT * FROM rms.Restaurants WHERE ID = ?";
		Restaurant restaurant = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new RestaurantMapper());
		return restaurant;
	}

	@Override
	public List<Restaurant> findByName(String name) {
		name = "%" + name + "%";
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE AND Name LIKE ?";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new Object[] { name }, new RestaurantMapper());
		return restaurants;
	}

	@Override
	public List<Restaurant> findByDistrict(Integer districtId, String name) {
		name = "%" + name + "%";
		String SQL = "SELECT * FROM rms.Restaurants"
				+ " WHERE Availability = TRUE AND DistrictID = ? AND Name LIKE ?";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new Object[] { districtId, name }, new RestaurantMapper());
		return restaurants;
	}

	@Override
	public List<Restaurant> findByArea(Integer areaId, String name) {
		name = "%" + name + "%";
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE"
				+ " AND DistrictID IN (SELECT ID FROM rms.Districts WHERE AreaID = ?) AND Name LIKE ?";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new Object[] { areaId, name }, new RestaurantMapper());
		return restaurants;
	}

	@Override
	public List<Restaurant> findAll() {
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new RestaurantMapper());
		return restaurants;
	}

	@Override
	public int authenticate(Integer id, String password) {
		String SQL = "SELECT COUNT(*) FROM rms.Restaurants WHERE ID = ? AND Password = ?";
		return jdbcTemplateObject.queryForInt(SQL,
				new Object[] { id, password });
	}

	@Override
	public int update(Restaurant restaurant) {
		String SQL = "UPDATE rms.Restaurants SET DistrictID = ?, Name = ?, Address = ?, PhoneNo = ?, "
				+ "OpeningHours = ?,Description = ? WHERE ID = ?";
		return jdbcTemplateObject.update(SQL,
				new Object[] { restaurant.getDistrictId(),
						restaurant.getName(), restaurant.getAddress(),
						restaurant.getPhoneNo(), restaurant.getOpeningHours(),
						restaurant.getDescription(), restaurant.getId() });
	}

	@Override
	public int updateAvailability(Integer id, boolean availability) {
		String SQL = "UPDATE rms.Restaurants SET Availability = ? WHERE ID = ?";
		return jdbcTemplateObject
				.update(SQL, new Object[] { availability, id });
	}

	@Override
	public int updatePassword(Integer id, String password, String newPassword) {
		String SQL = "UPDATE rms.Restaurants SET Password = ? WHERE ID = ? AND Password = ?";
		return jdbcTemplateObject.update(SQL, new Object[] { newPassword, id,
				password });
	}

	@Override
	public int delete(Integer id) {
		String SQL = "DELETE FROM rms.Restaurants WHERE ID = ?";
		return jdbcTemplateObject.update(SQL, id);
	}
}

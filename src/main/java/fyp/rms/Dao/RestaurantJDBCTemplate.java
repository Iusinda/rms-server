package fyp.rms.Dao;

import java.sql.Timestamp;
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
	public List<Restaurant> find(String name) {
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE AND Name LIKE '%?%'";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new Object[] { name }, new RestaurantMapper());
		return restaurants;
	}

	@Override
	public List<Restaurant> findByDistrictId(Integer districtId, String name) {
		String SQL = "SELECT ID, Name FROM rms.Restaurants"
				+ " WHERE Availability = TRUE AND DistrictID = ? AND Name LIKE '%?%';";
		List<Restaurant> restaurants = jdbcTemplateObject.query(SQL,
				new Object[] { districtId, name }, new RestaurantMapper());
		return restaurants;
	}

	@Override
	public List<Restaurant> findByAreaId(Integer areaId, String name) {
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE"
				+ " AND DistrictID IN (SELECT ID FROM rms.Districts WHERE AreaID = ?) AND Name LIKE '%?%'";
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
	public boolean getValidation(Integer id, String password) {
		String SQL = "SELECT COUNT(*) FROM rms.Restaurants WHERE ID = ? AND Password = ?";
		return (jdbcTemplateObject.queryForInt(SQL,
				new Object[] { id, password }) == 1);
	}

	@Override
	public boolean update(Restaurant restaurant) {
		String SQL = "UPDATE rms.Restaurants SET DistrictID = ?, Name = ?, Address = ?, PhoneNo = ?, "
				+ "OpeningHour = ?,Description = ?, Availability = ?, LastUpdate = NOW(6) WHERE ID = ?";
		return (jdbcTemplateObject.update(
				SQL,
				new Object[] { restaurant.getDistrictId(),
						restaurant.getName(), restaurant.getAddress(),
						restaurant.getPhoneNo(), restaurant.getOpeningHours(),
						restaurant.getDescription(),
						restaurant.getAvailability(), restaurant.getId() }) == 1);
	}

	@Override
	public boolean updateAvailability(Integer id, boolean availability) {
		String SQL = "UPDATE rms.Restaurants SET Availability = ? WHERE ID = ?";
		return (jdbcTemplateObject.update(SQL,
				new Object[] { availability, id }) == 1);
	}

	@Override
	public boolean updateAvailability(Integer id, boolean availability,
			Timestamp lastUpdate) {
		String SQL = "UPDATE rms.Restaurants SET Availability = ?, LastUpdate = ? WHERE ID = ?";
		return (jdbcTemplateObject.update(SQL, new Object[] { availability,
				lastUpdate.toString(), id }) == 1);
	}

	@Override
	public boolean updatePassword(Integer id, String oldPassword,
			String newPassword) {
		String SQL = "UPDATE rms.Restaurants SET Password = ? WHERE ID = ? AND Password = ?";
		return (jdbcTemplateObject.update(SQL, new Object[] { id, oldPassword,
				newPassword }) == 1);
	}

	@Override
	public void delete(Integer id) {
		String SQL = "DELETE FROM rms.Restaurants WHERE ID = ?";
		jdbcTemplateObject.update(SQL, id);
	}
}

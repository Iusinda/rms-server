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
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Integer districtId, String name, String address,
			String phoneNo, String openingHours, String description,
			Boolean availability) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.Restaurants "
				+ "(Passworkd,Name,DistrictID,Address,PhoneNo,OpeningHours,Description,Availability,lastUpdate)"
				+ " VALUES (?,?,?,?,?,?,?,?,NOW(6))";
		jdbcTemplateObject.update(SQL, new Object[] { districtId, name,
				address, phoneNo, openingHours, description, availability });
	}

	@Override
	public Restaurant getRestaurant(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Restaurants WHERE RestaurantID = ?;";
		Restaurant restaurant = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new RestaurantMapper());
		return restaurant;
	}

	@Override
	public List<Restaurant> listRestaurants() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Restaurants WHERE Availability = TRUE";
		List<Restaurant> restaurant = jdbcTemplateObject.query(SQL,
				new RestaurantMapper());
		return restaurant;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.Restaurants WHERE RestaurantID= ?";
		jdbcTemplateObject.update(SQL, id);
		// do log or something
	}

	@Override
	public void update(Integer id, Integer districtId, String name,
			String address, String phoneNo, String openingHours,
			String description, Boolean availability) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE rms.Restaurants "
				+ "SET DistrictID = ?, Name = ?, Address = ?, PhoneNo = ?, OpeningHour = ?,Description = ?, Availability = ?, LastUpdate = NOW(6)"
				+ "WHERE RestaurantID = ?";
		jdbcTemplateObject
				.update(SQL, new Object[] { districtId, name, address, phoneNo,
						openingHours, description, availability, id });
	}
}

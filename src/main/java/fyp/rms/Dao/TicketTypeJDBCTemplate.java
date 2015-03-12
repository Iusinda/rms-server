package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.TicketType;
import fyp.rms.Mapper.TicketTypeMapper;

public class TicketTypeJDBCTemplate implements TicketTypeDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(TicketType ticketType) {
		String SQL = "INSERT INTO rms.TicketTypes "
				+ "(RestaurantID,Type,MaxSize)" + " VALUES (?,?,?)";
		return jdbcTemplateObject.update(
				SQL,
				new Object[] { ticketType.getRestaurantId(),
						ticketType.getType(), ticketType.getMaxSize() });
	}

	@Override
	public TicketType find(Integer restaurantId, Integer type) {
		String SQL = "SELECT * FROM rms.TicketTypes WHERE RestaurantID = ? AND Type = ?";
		TicketType ticketType = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { restaurantId, type }, new TicketTypeMapper());
		return ticketType;
	}

	@Override
	public List<TicketType> findByRestaurant(Integer restaurantId) {
		String SQL = "SELECT * FROM rms.TicketTypes WHERE RestaurantID = ?";
		List<TicketType> ticketTypes = jdbcTemplateObject.query(SQL,
				new Object[] { restaurantId }, new TicketTypeMapper());
		return ticketTypes;
	}

	@Override
	public int update(TicketType ticketType) {
		String SQL = "UPDATE rms.TicketTypes SET maxSize = ?"
				+ "WHERE RestaurantId = ? AND Type = ?";
		return jdbcTemplateObject.update(
				SQL,
				new Object[] { ticketType.getMaxSize(),
						ticketType.getRestaurantId(), ticketType.getType() });
	}

	@Override
	public int delete(Integer restaurantId, Integer type) {
		String SQL = "DELETE FROM rms.TicketTypes WHERE RestaurantId = ?,Type = ?";
		return jdbcTemplateObject.update(SQL,
				new Object[] { restaurantId, type });
	}
}

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
	public void createTicketType(Integer restaurantId, Character type,
			Integer maxSize) {
		String SQL = "INSERT INTO rms.TicketTypes "
				+ "(RestaurantID,Type,MaxSize)" + " VALUES (?,?,?)";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
				maxSize });
	}

	@Override
	public TicketType findTicketType(Integer restaurantId, Character type) {
		String SQL = "SELECT * FROM rms.TicketTypes WHERE RestaurantID = ? AND Type = ?";
		TicketType ticketType = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { restaurantId, type }, new TicketTypeMapper());
		return ticketType;
	}

	@Override
	public List<TicketType> findTicketTypes(Integer restaurantId) {
		String SQL = "SELECT * FROM rms.TicketTypes WHERE RestaurantID = ?";
		List<TicketType> ticketTypes = jdbcTemplateObject.query(SQL,
				new Object[] { restaurantId }, new TicketTypeMapper());
		return ticketTypes;
	}

	@Override
	public void updateTicketType(Integer restaurantId, Character type,
			Integer maxSize) {
		String SQL = "UPDATE rms.TicketTypes SET maxSize = ?"
				+ "WHERE RestaurantId = ? AND Type = ?";
		jdbcTemplateObject.update(SQL, new Object[] { maxSize, restaurantId,
				type });
	}

	@Override
	public void deleteTicketType(Integer restaurantId, Character type) {
		String SQL = "DELETE FROM rms.TicketTypes WHERE RestaurantId = ?,Type = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type });
	}
}

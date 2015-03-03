package fyp.rms.Dao;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.Ticket;
import fyp.rms.Mapper.TicketMapper;

public class TicketJDBCTemplate implements TicketDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void createTicket(Integer restaurantId, Character type,
			Integer size, Integer customerId) {
		String SQL = "INSERT INTO rms.Tickets "
				+ "(RestaurantID,Type,Size,CustomerID) VALUES (?,?,?,?)";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type, size,
				customerId });
	}

	@Override
	public void createTicket(Integer restaurantId, Character type, Integer size) {
		String SQL = "INSERT INTO rms.Tickets (RestaurantID,Type,Size)"
				+ " VALUES (?,?,?)";
		jdbcTemplateObject.update(SQL,
				new Object[] { restaurantId, type, size });
	}

	@Override
	public Ticket findTicket(Integer restaurantId, Character type,
			Timestamp getTime) {
		String SQL = "SELECT * FROM rms.Tickets"
				+ " WHERE AND RestaurantID = ? AND Type = ? AND GetTime = ?";
		Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] {
				restaurantId, type, getTime }, new TicketMapper());
		return ticket;
	}

	@Override
	public List<Ticket> findTickets(Integer restaurantId, Character type) {
		String SQL = "SELECT * FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?"
				+ " AND Validity = TRUE ORDER BY GetTime";
		List<Ticket> tickets = jdbcTemplateObject.query(SQL, new Object[] {
				restaurantId, type }, new TicketMapper());
		return tickets;
	}

	@Override
	public List<Ticket> findUnrecordedTickets(Integer restaurantId,
			Character type) {
		String SQL = "SELECT GetTime, CallTime FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?"
				+ " AND CallTime > (SELECT LastUpdate FROM rms.Restaurants WHERE ID = ?)";
		List<Ticket> tickets = jdbcTemplateObject.query(SQL, new Object[] {
				restaurantId, type, restaurantId }, new TicketMapper());
		return tickets;
	}

	@Override
	public int findNoOfTicketsAhead(Integer restaurantId, Character type,
			Timestamp getTime) {
		String SQL = "SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?"
				+ " AND GetTime < ? AND Validity = TRUE AND CallTime IS NULL";
		return jdbcTemplateObject.queryForInt(SQL, new Object[] { restaurantId,
				type, getTime });
	}

	@Override
	public void updateCallTime(Integer restaurantId, Character type,
			Timestamp getTime) {
		String SQL = "UPDATE rms.Tickets SET CallTime = NOW() WHERE RestaurantID = ? AND Type = ? AND GetTime = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
				getTime });
	}

	@Override
	public void updateValidity(Integer restaurantId, Character type,
			Timestamp getTime) {
		String SQL = "UPDATE rms.Tickets SET Validity = FALSE WHERE RestaurantID = ? AND Type = ? AND GetTime = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
				getTime });
	}

	@Override
	public void deleteTicket(Integer restaurantId, Character type,
			Timestamp getTime) {
		String SQL = "DELETE FROM rms.Tickets "
				+ "WHERE RestaurantID = ?,Type = ?,GetTime = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
				getTime });
	}
}

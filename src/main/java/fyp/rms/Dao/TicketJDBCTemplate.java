package fyp.rms.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.entity.Ticket;
import fyp.rms.mapper.TicketMapper;

public class TicketJDBCTemplate implements TicketDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(Ticket ticket) {
		String SQL = "INSERT INTO rms.Tickets (CustomerID, RestaurantID, Type, "
				+ "Size, Number, Position, Duration) VALUES (?,?,?,?,?,?,?)";
		return jdbcTemplateObject.update(
				SQL,
				new Object[] { ticket.getCustomerId(),
						ticket.getRestaurantId(), ticket.getType(),
						ticket.getSize(), ticket.getNumber(),
						ticket.getPosition(), ticket.getDuration() });
	}

	@Override
	public Ticket find(Integer customerId) {
		try {
			String SQL = "SELECT * FROM rms.Tickets WHERE CustomerID = ? AND Validity = TRUE";
			Ticket ticket = jdbcTemplateObject.queryForObject(SQL,
					new Object[] { customerId }, new TicketMapper());
			return ticket;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Ticket find(Integer restaurantId, Integer type, Integer number) {
		String SQL = "SELECT * FROM rms.Tickets"
				+ " WHERE RestaurantID = ? AND Type = ? AND Number = ?";
		Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] {
				restaurantId, type, number }, new TicketMapper());
		return ticket;
	}

	@Override
	public List<Ticket> findByType(Integer restaurantId, Integer type) {
		String SQL = "SELECT * FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?"
				+ " AND Validity = TRUE ORDER BY GetTime";
		List<Ticket> tickets = jdbcTemplateObject.query(SQL, new Object[] {
				restaurantId, type }, new TicketMapper());
		return tickets;
	}

	@Override
	public List<Ticket> findByRestaurant(Integer restaurantId) {
		String SQL = "SELECT * FROM rms.Tickets WHERE RestaurantID = ? AND CallTime IS NOT NULL";
		List<Ticket> tickets = jdbcTemplateObject.query(SQL,
				new Object[] { restaurantId }, new TicketMapper());
		return tickets;
	}

	@Override
	public int findNumber(Integer restaurantId, Integer type) {
		String SQL = "SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?";
		return jdbcTemplateObject.queryForInt(SQL, new Object[] { restaurantId,
				type });
	}

	@Override
	public int findPosition(Integer restaurantId, Integer type) {
		String SQL = "SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? "
				+ "AND Type = ? AND Validity = TRUE AND CallTime IS NULL";
		return jdbcTemplateObject.queryForInt(SQL, new Object[] { restaurantId,
				type });
	}

	@Override
	public int findPosition(Integer restaurantId, Integer type, Integer number) {
		String SQL = "SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ? AND Type = ?"
				+ " AND Number < ? AND Validity = TRUE AND CallTime IS NULL";
		return jdbcTemplateObject.queryForInt(SQL, new Object[] { restaurantId,
				type, number });
	}

	@Override
	public Ticket updateCallTime(Integer restaurantId, Integer type) {
		try {
			String SQL = "SELECT * FROM rms.Tickets WHERE RestaurantID = ? AND Type = ? "
					+ "AND Validity = TRUE AND CallTime IS NULL ORDER BY Number LIMIT 1";
			Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] {
					restaurantId, type }, new TicketMapper());
	
			SQL = "UPDATE rms.Tickets SET CallTime = NOW() "
					+ "WHERE RestaurantID = ? AND Type = ? AND Number = ?";
			if (jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
					ticket.getNumber() }) == 1)
				return ticket;
			else
				return null;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int updateValidity(Integer customerId) {
		String SQL = "UPDATE rms.Tickets SET Validity = FALSE "
				+ "WHERE CustomerID = ? AND Validity = TRUE";
		return jdbcTemplateObject.update(SQL, new Object[] { customerId });
	}

	@Override
	public int updateValidity(Integer restaurantId, Integer type, Integer number) {
		String SQL = "UPDATE rms.Tickets SET Validity = FALSE "
				+ "WHERE RestaurantID = ? AND Type = ? AND Number = ?";
		return jdbcTemplateObject.update(SQL, new Object[] { restaurantId,
				type, number });
	}

	@Override
	public int delete(Integer restaurantId) {
		String SQL = "INSERT INTO rms.PastTickets SELECT * FROM rms.Tickets WHERE RestaurantID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId });
		SQL = "DELETE FROM rms.Tickets WHERE RestaurantID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId });

		SQL = "SELECT COUNT(*) FROM rms.Tickets WHERE RestaurantID = ?";
		return jdbcTemplateObject.queryForInt(SQL,
				new Object[] { restaurantId });
	}
}

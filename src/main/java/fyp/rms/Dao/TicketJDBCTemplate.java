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
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime, Timestamp callTime,
			Boolean status) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.Ticket "
				+ "(CustomerID,RestaurantID,Type,GetTime,CallTime,Status)"
				+ " VALUES (?,?,?,?,?,?)";
		jdbcTemplateObject.update(SQL, new Object[] { customerId, restaurantId,
				type, getTime, callTime, status });

	}

	@Override
	public Ticket getTicket(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime) {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Ticket"
				+ " WHERE CustomerID = ? AND RestaurantID = ? AND Type = ? AND GetTime = ?";
		Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] {
				customerId, restaurantId, type, getTime }, new TicketMapper());
		return ticket;
	}

	@Override
	public List<Ticket> listTickets() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Ticket";
		List<Ticket> ticket = jdbcTemplateObject.query(SQL, new TicketMapper());
		return ticket;
	}

	@Override
	public void delete(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.Ticket "
				+ "WHERE CustomerID = ?, RestaurantID = ?,Type = ?,GetTime = ?";
		jdbcTemplateObject.update(SQL, new Object[] { customerId, restaurantId,
				type, getTime });
	}

	@Override
	public void update(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime, Timestamp callTime,
			Boolean status) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE rms.Ticket "
				+ "SET CallTime = ?, Status = ? "
				+ "WHERE  CustomerID = ?, RestaurantID = ?,Type = ?,GetTime = ?";
		jdbcTemplateObject.update(SQL, new Object[] { callTime, status,
				customerId, restaurantId, type, getTime });

	}
}

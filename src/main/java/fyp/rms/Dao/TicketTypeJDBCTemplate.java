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
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Integer restaurantId, Character type, Integer maxSize) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.TicketType "
				+ "(RestaurantID,Type,MaxSize)" + " VALUES (?,?,?)";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type,
				maxSize });
	}

	@Override
	public TicketType getTicketType(Integer restaurantId, Character type) {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.TicketType WHERE RestaurantID = ? AND Type = ?";
		TicketType ticketType = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { restaurantId, type }, new TicketTypeMapper());
		return ticketType;
	}

	@Override
	public List<TicketType> listTicketTypes() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.TicketType";
		List<TicketType> ticketType = jdbcTemplateObject.query(SQL,
				new TicketTypeMapper());
		return ticketType;
	}

	@Override
	public void delete(Integer restaurantId, Character type) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.TicketType WHERE RestaurantId = ?,Type = ?";
		jdbcTemplateObject.update(SQL, new Object[] { restaurantId, type });
	}

	@Override
	public void update(Integer restaurantId, Character type, Integer maxSize) {
		// TODO Auto-generated method stub
		String SQL = "UPDATE rms.TicketType" + "SET maxSize = ?"
				+ "WHERE RestaurantId = ? AND Type = ?";
		jdbcTemplateObject.update(SQL, new Object[] { maxSize, restaurantId,
				type });
	}
}

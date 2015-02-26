package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.Ticket;

public class TicketMapper implements RowMapper<Ticket> {
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ticket ticket = new Ticket();
		ticket.setCallTime(rs.getTimestamp("CallTime"));
		ticket.setCustomerId(rs.getInt("CustomerID"));
		ticket.setGetTime(rs.getTimestamp("GetTime"));
		ticket.setRestaurantId(rs.getInt("RestaurantID"));
		ticket.setStatus(rs.getBoolean("Status"));
		ticket.setType(rs.getString("Type"));
		return ticket;
		
	}

}

package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.Ticket;

public class TicketMapper implements RowMapper<Ticket> {
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
		Ticket ticket = new Ticket();
		ticket.setCustomerId(rs.getInt("CustomerID"));
		ticket.setRestaurantId(rs.getInt("RestaurantID"));
		ticket.setType(rs.getInt("Type"));
		ticket.setSize(rs.getInt("Size"));
		ticket.setNumber(rs.getInt("Number"));
		ticket.setPosition(rs.getInt("Position"));
		ticket.setDuration(rs.getInt("Duration"));
		ticket.setGetTime(rs.getTimestamp("GetTime"));
		ticket.setCallTime(rs.getTimestamp("CallTime"));
		ticket.setValidity(rs.getBoolean("Validity"));
		return ticket;
	}
}

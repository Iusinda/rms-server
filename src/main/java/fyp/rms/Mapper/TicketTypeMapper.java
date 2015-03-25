package fyp.rms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.entity.TicketType;

public class TicketTypeMapper implements RowMapper<TicketType>{
	public TicketType mapRow(ResultSet rs,int numRow) throws SQLException
	{
		TicketType ticketType = new TicketType();
		ticketType.setMaxSize(rs.getInt("MaxSize"));
		ticketType.setRestaurantId(rs.getInt("RestaurantID"));
		ticketType.setType(rs.getInt("Type"));
		return ticketType;
	}
}

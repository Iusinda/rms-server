package fyp.rms.Dao;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Ticket;

/*
 private Integer customerId;
 private Integer restaurantId;
 private Character type;
 private Timestamp getTime;
 private Timestamp callTime;
 private Boolean status; 
 */
public interface TicketDao {

	public void setDataSource(DataSource ds);

	public void createTicket(Integer restaurantId, Character type,
			Integer size, Integer customerId);

	public void createTicket(Integer restaurantId, Character type, Integer size);

	public Ticket findTicket(Integer restaurantId, Character type,
			Timestamp getTime);

	public List<Ticket> findTickets(Integer restaurantId, Character type);

	public List<Ticket> findUnrecordedTickets(Integer restaurantId,
			Character type);

	public int findNoOfTicketsAhead(Integer restaurantId, Character type,
			Timestamp getTime);

	public void deleteTicket(Integer restaurantId, Character type,
			Timestamp getTime);

	public void updateCallTime(Integer restaurantId, Character type,
			Timestamp getTime);

	public void updateValidity(Integer restaurantId, Character type,
			Timestamp getTime);
}

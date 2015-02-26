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

	public void create(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime, Timestamp callTime,
			Boolean status);

	public Ticket getTicket(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime);

	public List<Ticket> listTickets();

	public void delete(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime);

	public void update(Integer customerId, Integer restaurantId,
			Character type, Timestamp getTime, Timestamp callTime,
			Boolean status);
}

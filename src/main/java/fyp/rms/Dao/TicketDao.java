package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Ticket;

public interface TicketDao {

	public void setDataSource(DataSource ds);

	public int create(Ticket ticket);

	public Ticket find(Integer restaurantId, Integer type, Integer number);

	public Ticket find(Integer customerId);

	public List<Ticket> findByType(Integer restaurantId, Integer type);

	public List<Ticket> findByRestaurant(Integer restaurantId);

	public int findNumber(Integer restaurantId, Integer type);

	public int findPosition(Integer restaurantId, Integer type);

	public int findPosition(Integer restaurantId, Integer type, Integer number);

	public Ticket updateCallTime(Integer restaurantId, Integer type);

	public int updateValidity(Integer customerId);

	public int updateValidity(Integer restaurantId, Integer type, Integer number);

	public int delete(Integer restaurantId);
}
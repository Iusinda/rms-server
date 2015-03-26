package fyp.rms.dao;

import java.util.List;



/*
 private Integer restaurantId;
 private Character type;
 private Integer maxSize;
 * */
import javax.sql.DataSource;

import fyp.rms.entity.TicketType;

public interface TicketTypeDao {

	public void setDataSource(DataSource ds);

	public int create(TicketType ticketType);

	public TicketType find(Integer restaurantId, Integer type);

	public List<TicketType> findByRestaurant(Integer restaurantId);

	public int update(TicketType ticketType);

	public int delete(Integer restaurantId, Integer type);
}

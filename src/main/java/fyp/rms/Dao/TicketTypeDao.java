package fyp.rms.Dao;

import java.util.List;


/*
 private Integer restaurantId;
 private Character type;
 private Integer maxSize;
 * */
import javax.sql.DataSource;

import fyp.rms.Entity.TicketType;

public interface TicketTypeDao {

	public void setDataSource(DataSource ds);

	public void createTicketType(Integer restaurantId, Character type, Integer maxSize);

	public TicketType findTicketType(Integer restaurantId, Character type);

	public List<TicketType> findTicketTypes(Integer restaurantId);

	public void updateTicketType(Integer restaurantId, Character type, Integer maxSize);

	public void deleteTicketType(Integer restaurantId, Character type);
}

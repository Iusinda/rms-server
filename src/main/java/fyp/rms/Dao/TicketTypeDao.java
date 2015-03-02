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

	public void create(Integer restaurantId, Character type, Integer maxSize);

	public TicketType getTicketType(Integer restaurantId, Character type);

	public List<TicketType> listTicketTypes();

	public void delete(Integer restaurantId, Character type);

	public void update(Integer restaurantId, Character type, Integer maxSize);
}

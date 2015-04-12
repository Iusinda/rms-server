package fyp.rms.dao;

import java.util.Calendar;

import javax.sql.DataSource;

public interface HolidayDao {

	public void setDataSource(DataSource ds);
	
	public int create(Integer year, Integer month, Integer day);

	public int find(Calendar date);
}

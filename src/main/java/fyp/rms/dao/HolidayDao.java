package fyp.rms.dao;

import java.util.Calendar;

import javax.sql.DataSource;

public interface HolidayDao {

	public void setDataSource(DataSource ds);

	public int find(Calendar date);
}

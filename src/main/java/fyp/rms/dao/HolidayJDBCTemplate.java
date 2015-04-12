package fyp.rms.dao;

import java.util.Calendar;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;


public class HolidayJDBCTemplate implements HolidayDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(Integer year, Integer month, Integer day) {
		String SQL = "INSERT INTO rms.Holidays VALUES (?,?,?)";
		return jdbcTemplateObject
				.update(SQL, new Object[] { year, month, day });
	}

	@Override
	public int find(Calendar date) {
		String SQL = "SELECT COUNT(*) FROM rms.Holidays WHERE Year = ? AND Month = ? AND Day = ?";
		return jdbcTemplateObject.queryForInt(
				SQL,
				new Object[] { date.get(Calendar.YEAR),
						date.get(Calendar.MONTH) + 1,
						date.get(Calendar.DAY_OF_MONTH) });
	}
}

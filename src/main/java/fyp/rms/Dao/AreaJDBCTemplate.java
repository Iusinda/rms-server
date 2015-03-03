package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.Area;
import fyp.rms.Entity.Customer;
import fyp.rms.Mapper.AreaMapper;
import fyp.rms.Mapper.CustomerMapper;

public class AreaJDBCTemplate implements AreaDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int createArea(String name) {
		String SQL = "INSERT INTO rms.Areas (Name) VALUES (?)";
		jdbcTemplateObject.update(SQL, name);
		
		SQL = "SELECT LAST_INSERT_ID()";
		return jdbcTemplateObject.queryForInt(SQL);
	}

	@Override
	public Area findArea(Integer id) {
		String SQL = "SELECT * FROM rms.Areas WHERE ID = ?";
		Area area = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
				new AreaMapper());
		return area;
	}

	@Override
	public List<Area> findAllAreas() {
		String SQL = "SELECT * FROM rms.Areas";
		List<Area> areas = jdbcTemplateObject.query(SQL, new AreaMapper());
		return areas;
	}

	@Override
	public void updateArea(Integer id, String name) {
		String SQL = "UPUDATE rms.Areas SET Name = ? WHERE ID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { id, name });
	}

	@Override
	public void deleteArea(Integer id) {
		String SQL = "DELETE FROM rms.Areas WHERE ID = ?";
		jdbcTemplateObject.update(SQL, id);

	}
}

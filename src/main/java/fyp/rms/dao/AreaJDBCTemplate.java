package fyp.rms.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.entity.Area;
import fyp.rms.mapper.AreaMapper;

public class AreaJDBCTemplate implements AreaDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(Area area) {
		String SQL = "INSERT INTO rms.Areas (Name) VALUES (?)";
		jdbcTemplateObject.update(SQL, area.getName());

		SQL = "SELECT LAST_INSERT_ID()";
		return jdbcTemplateObject.queryForInt(SQL);
	}

	@Override
	public Area find(Integer id) {
		String SQL = "SELECT * FROM rms.Areas WHERE ID = ?";
		Area area = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
				new AreaMapper());
		return area;
	}

	@Override
	public List<Area> findAll() {
		String SQL = "SELECT * FROM rms.Areas";
		List<Area> areas = jdbcTemplateObject.query(SQL, new AreaMapper());
		return areas;
	}

	@Override
	public int update(Area area) {
		String SQL = "UPUDATE rms.Areas SET Name = ? WHERE ID = ?";
		return jdbcTemplateObject.update(SQL,
				new Object[] { area.getId(), area.getName() });
	}

	@Override
	public int delete(Integer id) {
		String SQL = "DELETE FROM rms.Areas WHERE ID = ?";
		return jdbcTemplateObject.update(SQL, id);
	}
}

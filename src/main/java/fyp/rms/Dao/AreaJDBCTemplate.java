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
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String name) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.Areas (Name) VALUES (?)";
		jdbcTemplateObject.update(SQL, name);
	}

	@Override
	public Area getArea(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Areas WHERE ID = ?";
		Area area = jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
				new AreaMapper());
		return area;
	}

	@Override
	public List<Area> listAreas() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Areas";
		List<Area> area = jdbcTemplateObject.query(SQL, new AreaMapper());
		return area;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.Areas WHERE ID = ?";
		jdbcTemplateObject.update(SQL, id);

	}

	@Override
	public void update(Integer id, String name) {
		// TODO Auto-generated method stub
		String SQL = "UPUDATE rms.Areas SET Name = ? WHERE ID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { id, name });
		// log and do somethings
	}

}

package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.District;
import fyp.rms.Mapper.DistrictMapper;

public class DistrictJDBCTemplate implements DistrictDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String name, Integer areaId) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.District (Name,AreaId) VALUES ('?,?')";
		jdbcTemplateObject.update(SQL, name, areaId);
	}

	@Override
	public District getDistrict(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.District WHERE DistrictID = ?)";
		District district = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new DistrictMapper());
		return district;
	}

	@Override
	public List<District> listDistricts() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.District";
		List<District> district = jdbcTemplateObject.query(SQL,
				new DistrictMapper());
		return district;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.District WHERE DistrictID = ?";
		jdbcTemplateObject.update(SQL, id);

	}

	@Override
	public void update(Integer id, String name, Integer areaId) {
		// TODO Auto-generated method stub
		String SQL = "UPUDATE rms.District SET Name = ?,AreaId = ? WHERE DistrictID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { name, areaId, id });
	}

	@Override
	public District getDistrictByAreaId(Integer areaId) {
		String SQL = "SELECT * FROM rms.Districts WHERE AreaID = ?";
		District district = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { areaId }, new DistrictMapper());
		return district;

	}

}

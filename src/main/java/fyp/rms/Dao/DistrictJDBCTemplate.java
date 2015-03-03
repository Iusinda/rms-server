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
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int createDistrict(String name, Integer areaId) {
		String SQL = "INSERT INTO rms.District (Name,AreaId) VALUES (?,?)";
		jdbcTemplateObject.update(SQL, name, areaId);
		
		SQL = "SELECT LAST_INSERT_ID()";
		return jdbcTemplateObject.queryForInt(SQL);
	}

	@Override
	public District findDistrict(Integer id) {
		String SQL = "SELECT * FROM rms.District WHERE DistrictID = ?)";
		District district = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new DistrictMapper());
		return district;
	}

	@Override
	public List<District> findDistricts(Integer areaId) {
		String SQL = "SELECT * FROM rms.District WHERE AreaID = ?";
		List<District> districts = jdbcTemplateObject.query(SQL,
				new Object[] { areaId }, new DistrictMapper());
		return districts;
	}

	@Override
	public void updateDistrict(Integer id, String name, Integer areaId) {
		String SQL = "UPUDATE rms.District SET Name = ?,AreaId = ? WHERE DistrictID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { name, areaId, id });
	}

	@Override
	public void deleteDistrict(Integer id) {
		String SQL = "DELETE FROM rms.District WHERE DistrictID = ?";
		jdbcTemplateObject.update(SQL, id);

	}
}

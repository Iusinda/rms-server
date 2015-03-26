package fyp.rms.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.entity.District;
import fyp.rms.mapper.DistrictMapper;

public class DistrictJDBCTemplate implements DistrictDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public int create(District district) {
		String SQL = "INSERT INTO rms.Districts (Name,AreaId) VALUES (?,?)";
		jdbcTemplateObject
				.update(SQL, district.getName(), district.getAreaId());

		SQL = "SELECT LAST_INSERT_ID()";
		return jdbcTemplateObject.queryForInt(SQL);
	}

	@Override
	public District find(Integer id) {
		String SQL = "SELECT * FROM rms.Districts WHERE ID = ?";
		District district = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new DistrictMapper());
		return district;
	}

	@Override
	public List<District> findByArea(Integer areaId) {
		String SQL = "SELECT * FROM rms.Districts WHERE AreaID = ?";
		List<District> districts = jdbcTemplateObject.query(SQL,
				new Object[] { areaId }, new DistrictMapper());
		return districts;
	}

	@Override
	public int update(District district) {
		String SQL = "UPUDATE rms.Districts SET Name = ?,AreaId = ? WHERE DistrictID = ?";
		return jdbcTemplateObject.update(SQL, new Object[] {
				district.getName(), district.getAreaId(), district.getId() });
	}

	@Override
	public int delete(Integer id) {
		String SQL = "DELETE FROM rms.Districts WHERE DistrictID = ?";
		return jdbcTemplateObject.update(SQL, id);
	}
}

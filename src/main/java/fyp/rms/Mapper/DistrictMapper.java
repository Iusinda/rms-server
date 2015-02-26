package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.District;

public class DistrictMapper implements RowMapper<District> {
	public District mapRow(ResultSet rs, int rowNum) throws SQLException {
		District district = new District();
		district.setId(rs.getInt("DistrictID"));
		district.setName(rs.getString("Name"));
		return district;
	}
}

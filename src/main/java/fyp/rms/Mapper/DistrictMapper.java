package fyp.rms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.entity.District;

public class DistrictMapper implements RowMapper<District> {
	public District mapRow(ResultSet rs, int rowNum) throws SQLException {
		District district = new District();
		district.setId(rs.getInt("ID"));
		district.setName(rs.getString("Name"));
		district.setAreaId(rs.getInt("AreaID"));
		return district;
	}
}

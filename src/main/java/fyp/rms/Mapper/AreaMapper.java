package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.Area;
import fyp.rms.Entity.Customer;

public class AreaMapper implements RowMapper<Area> {
	public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
		Area area = new Area();
		area.setId(rs.getInt("ID"));
		area.setName(rs.getString("Name"));
		return area;
	}

}

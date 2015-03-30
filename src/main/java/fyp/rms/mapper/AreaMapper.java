package fyp.rms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.entity.Area;

public class AreaMapper implements RowMapper<Area> {
	public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
		Area area = new Area();
		area.setId(rs.getInt("ID"));
		area.setName(rs.getString("Name"));
		return area;
	}
}

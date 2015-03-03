package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Area;

public interface AreaDao {
	public void setDataSource(DataSource ds);

	public int createArea(String name);

	public Area findArea(Integer id);

	public List<Area> findAllAreas();

	public void updateArea(Integer id, String name);

	public void deleteArea(Integer id);
}

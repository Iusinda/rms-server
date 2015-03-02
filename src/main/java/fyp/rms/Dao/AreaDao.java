package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Area;

public interface AreaDao {
	public void setDataSource(DataSource ds);

	public void create(String name);

	public Area getArea(Integer id);

	public List<Area> listAreas();

	public void delete(Integer id);

	public void update(Integer id, String name);
}

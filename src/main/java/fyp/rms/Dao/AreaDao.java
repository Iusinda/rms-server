package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Area;

public interface AreaDao {
	public void setDataSource(DataSource ds);

	public int create(Area area);

	public Area find(Integer id);

	public List<Area> findAll();

	public int update(Area area);

	public int delete(Integer id);
}

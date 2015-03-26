package fyp.rms.dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.entity.District;

public interface DistrictDao {
	public void setDataSource(DataSource ds);

	public int create(District district);

	public District find(Integer id);

	public List<District> findByArea(Integer areaId);

	public int update(District district);

	public int delete(Integer id);
}

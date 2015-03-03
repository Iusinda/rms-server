package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.District;

/*
 * 	private Integer id;
 private String name;
 * */
public interface DistrictDao {
	public void setDataSource(DataSource ds);

	public int createDistrict(String name, Integer areaId);

	public District findDistrict(Integer id);

	public List<District> findDistricts(Integer areaId);

	public void updateDistrict(Integer id, String name, Integer areaId);

	public void deleteDistrict(Integer id);
}

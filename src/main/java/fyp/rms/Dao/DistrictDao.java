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

	public void create(String name);

	public District getDistrict(Integer id);

	public List<District> listDistricts();

	public void delete(Integer id);

	public void update(Integer id, String name);
}

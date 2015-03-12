package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Customer;

public interface CustomerDao {
	public void setDataSource(DataSource ds);

	public int create(Customer customer);

	public Customer find(Integer id);

	public List<Customer> findAll();

	public int update(Customer customer);

	public int delete(Integer id);
	
}

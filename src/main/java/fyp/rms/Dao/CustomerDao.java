package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import fyp.rms.Entity.Customer;

/*
 * private Integer id;
 private String regId;
 * */
public interface CustomerDao {
	public void setDataSource(DataSource ds);

	public int createCustomer(String regId);

	public Customer findCustomer(Integer id);

	public List<Customer> findAllCustomers();

	public void updateCustomer(Integer id, String regId);

	public void deleteCustomer(Integer id);
	
}

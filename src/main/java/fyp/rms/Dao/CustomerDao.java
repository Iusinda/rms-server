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

	public void create(String regId);

	public Customer getCustomer(Integer id);

	public List<Customer> listCustomers();

	public void delete(Integer id);

	public void update(Integer id, String regId);
	
}

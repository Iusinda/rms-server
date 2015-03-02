package fyp.rms.Dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import fyp.rms.Entity.Customer;
import fyp.rms.Mapper.CustomerMapper;

public class CustomerJDBCTemplate implements CustomerDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource ds) {
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String regId) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO rms.Customers (RegID) VALUES (?)";
		jdbcTemplateObject.update(SQL, regId);
		// do some log or other things

	}

	@Override
	public Customer getCustomer(Integer id) {
		// TODO Auto-generated method stub\
		String SQL = "SELECT * FROM rms.Customers WHERE ID = ?";
		Customer customer = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new CustomerMapper());
		return customer;
	}

	@Override
	public List<Customer> listCustomers() {
		// TODO Auto-generated method stub
		String SQL = "SELECT * FROM rms.Customers";
		List<Customer> customer = jdbcTemplateObject.query(SQL,
				new CustomerMapper());
		return customer;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM rms.Customers WHERE ID = ?";
		jdbcTemplateObject.update(SQL, id);
		// do some log or other things
	}

	@Override
	public void update(Integer id, String regId) {
		// TODO Auto-generated method stub
		String SQL = "UPUDATE rms.Customers SET RegID = ? WHERE ID = ?";
		jdbcTemplateObject.update(SQL, new Object[] { id, regId });
		// log and do somethings
	}
	
 
}

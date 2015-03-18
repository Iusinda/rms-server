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
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public Customer create(String regId) {
		String SQL = "INSERT INTO rms.Customers (RegID) VALUES (?)";
		jdbcTemplateObject.update(SQL, regId);

		SQL = "SELECT * FROM rms.Customers ORDER BY id DESC LIMIT 1";
		return jdbcTemplateObject.queryForObject(SQL, new CustomerMapper());
	}

	@Override
	public Customer find(Integer id) {
		String SQL = "SELECT * FROM rms.Customers WHERE ID = ?";
		return jdbcTemplateObject.queryForObject(SQL, new Object[] { id },
				new CustomerMapper());
	}

	@Override
	public List<Customer> findAll() {
		String SQL = "SELECT * FROM rms.Customers";
		List<Customer> customers = jdbcTemplateObject.query(SQL,
				new CustomerMapper());
		return customers;
	}

	@Override
	public int update(Customer customer) {
		String SQL = "UPUDATE rms.Customers SET RegID = ? WHERE ID = ?";
		return jdbcTemplateObject.update(SQL, new Object[] { customer.getId(),
				customer.getRegId() });
	}

	@Override
	public int delete(Integer id) {
		String SQL = "DELETE FROM rms.Customers WHERE ID = ?";
		return jdbcTemplateObject.update(SQL, id);
	}
}

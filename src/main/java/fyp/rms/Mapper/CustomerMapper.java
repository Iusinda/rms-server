package fyp.rms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fyp.rms.Entity.Customer;

public class CustomerMapper implements RowMapper<Customer> {
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getInt("ID"));
		customer.setRegId(rs.getString("RegID"));
		return customer;
	}
}

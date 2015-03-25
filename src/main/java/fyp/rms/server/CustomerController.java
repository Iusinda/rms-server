package fyp.rms.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.CustomerJDBCTemplate;
import fyp.rms.entity.Customer;

@Controller
public class CustomerController {
	private CustomerJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (CustomerJDBCTemplate) context.getBean("CustomerJDBCTemplate");
	}

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerController.class);

	@RequestMapping(value = "/customer/create")
	@ResponseBody
	public Customer create(@RequestParam String regId) {
		Customer customer = repository().create(regId);
		logger.info("***** Create Customer {}", customer.getId());
		return customer;
	}

//	@RequestMapping(value = "/customer/create")
//	@ResponseBody
//	public Integer create(@RequestParam String regId) {
//		Integer id = repository().create(regId);
//		logger.info("***** Create Customer {}", id);
//		return id;
//	}
	
	public Customer find(Integer id) {
		Customer customer = repository().find(id);
		logger.info("***** Return Customer {}", customer.getId());
		return customer;
	}
}

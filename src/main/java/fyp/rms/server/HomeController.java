package fyp.rms.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.Dao.CustomerJDBCTemplate;
import fyp.rms.Dao.RestaurantJDBCTemplate;
import fyp.rms.Entity.Customer;
import fyp.rms.Entity.Restaurant;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Customer getCustomer() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		CustomerJDBCTemplate customerOperator = (CustomerJDBCTemplate) context
				.getBean("CustomerJDBCTemplate");
		customerOperator.createCustomer("YO success");
		Customer customer = customerOperator.findCustomer(2);

		return customer;

	}

	@RequestMapping(value = "/allrestaurants", method = RequestMethod.GET)
	@ResponseBody
	public List<Restaurant> getAllRestaurants() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		RestaurantJDBCTemplate restaurantTemplate = (RestaurantJDBCTemplate) context
				.getBean("RestaurantJDBCTemplate");
		List<Restaurant> restaurantList = restaurantTemplate.findAll();
		return restaurantList;

	}
}

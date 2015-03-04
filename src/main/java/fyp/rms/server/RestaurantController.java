package fyp.rms.server;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.Dao.RestaurantJDBCTemplate;
import fyp.rms.Dao.TicketJDBCTemplate;
import fyp.rms.Entity.Restaurant;

@Controller
@RequestMapping(value = "/restaurant")
public class RestaurantController {
	private static final Logger logger = LoggerFactory
			.getLogger(RestaurantController.class);

	private RestaurantJDBCTemplate restaurantRepository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (RestaurantJDBCTemplate) context
				.getBean("RestaurantJDBCTemplate");
	}
	
	/*private TicketJDBCTemplate ticketRepository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (TicketJDBCTemplate) context
				.getBean("TicketJDBCTemplate");
	}*/

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Restaurant> list() {
		List<Restaurant> restaurants = restaurantRepository().findAll();
		logger.info("Listed {} restaurant(s)", restaurants.size());
		return restaurants;
	}

	@RequestMapping(value = "/turnon", method = RequestMethod.GET)
	@ResponseBody
	public boolean turnOn(@RequestParam Integer id) {
		boolean result = restaurantRepository().updateAvailability(id, true);
		if (result)
			logger.info("Turned on the waiting list of Restaurant {}", id);
		else
			logger.info("Failed to turn on the waiting time of Restaurant {}",
					id);
		return result;
	}

	@RequestMapping(value = "/turnoff", method = RequestMethod.GET)
	@ResponseBody
	public boolean turnOff(@RequestParam Integer id) {
		//List<Ticket> tickets = ticketRepository().findUnrecordedTickets(id, type);
		Timestamp time = new Timestamp(new Date().getTime());
		boolean result = restaurantRepository().updateAvailability(id, false, time);
		if (result)
			logger.info("Turned off the waiting list of Restaurant at {}, {}", time, time.toString());
		else
			logger.info("Failed to turn off the waiting time of Restaurant {}",
					id);
		return result;
	}
}

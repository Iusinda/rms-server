package fyp.rms.server;

import java.util.List;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import fyp.rms.Entity.Restaurant;

@Controller
public class RestaurantController {
	private static final Logger logger = LoggerFactory
			.getLogger(RestaurantController.class);

	private RestaurantJDBCTemplate restaurantRepository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (RestaurantJDBCTemplate) context
				.getBean("RestaurantJDBCTemplate");
	}

	private String encode(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * private TicketJDBCTemplate ticketRepository() { ApplicationContext
	 * context = new ClassPathXmlApplicationContext( "jdbcConfig.xml"); return
	 * (TicketJDBCTemplate) context .getBean("TicketJDBCTemplate"); }
	 */

	@RequestMapping(value = "/restaurant", method = RequestMethod.GET)
	@ResponseBody
	public Restaurant info(@RequestParam Integer id) {
		Restaurant restaurant = restaurantRepository().find(id);
		logger.info("Return Restaurant {}", id);
		return restaurant;
	}

	@RequestMapping(value = "/restaurant", method = RequestMethod.POST)
	@ResponseBody
	public boolean modify(@RequestParam Integer id,
			@RequestParam Integer districtId, @RequestParam String name,
			@RequestParam String address, @RequestParam String phoneNo,
			@RequestParam String openingHours, @RequestParam String description) {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setDistrictId(districtId);
		restaurant.setName(name);
		restaurant.setAddress(address);
		restaurant.setPhoneNo(phoneNo);
		restaurant.setOpeningHours(openingHours);
		restaurant.setDescription(description);
		boolean result = (restaurantRepository().update(restaurant) == 1);
		logger.info("Modify info for Restaurant {}: {}", id, result);
		return result;
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.GET)
	@ResponseBody
	public List<Restaurant> listAll() {
		List<Restaurant> restaurants = restaurantRepository().findAll();
		logger.info("Return all {} available restaurant(s)", restaurants.size());
		return restaurants;
	}

	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
	@ResponseBody
	public List<Restaurant> list(@RequestParam Integer areaId,
			@RequestParam Integer districtId, @RequestParam String name) {
		List<Restaurant> restaurants;
		if (districtId > 0)
			restaurants = restaurantRepository().findByDistrictId(districtId,
					name);
		else if (areaId > 0)
			restaurants = restaurantRepository().findByAreaId(areaId, name);
		else
			restaurants = restaurantRepository().find(name);
		logger.info("Return " + restaurants.size()
				+ " restaurant(s) of areaId = " + areaId + ", districtId = "
				+ districtId + ", name = " + name);
		return restaurants;
	}

	@RequestMapping(value = "/restaurant/login", method = RequestMethod.POST)
	@ResponseBody
	public boolean login(@RequestParam Integer id, @RequestParam String password) {
		boolean result = (restaurantRepository().authenticate(id,
				encode(password)) == 1);
		logger.info("Login for Restaurant {}: {}", id, result);
		return result;
	}

	@RequestMapping(value = "/restaurant/turnon")
	@ResponseBody
	public boolean turnOn(@RequestParam Integer id) {
		boolean result = (restaurantRepository().updateAvailability(id, true) == 1);
		logger.info("Turn on for Restaurant {}: {}", id, result);
		return result;
	}

	@RequestMapping(value = "/restaurant/turnoff")
	@ResponseBody
	public boolean turnOff(@RequestParam Integer id) {
		// List<Ticket> tickets = ticketRepository().findUnrecordedTickets(id,
		// type);
		Timestamp time = restaurantRepository().find(id).getLastUpdate();
		boolean result = (new TicketController()).record(time);
		if (result){
			time = new Timestamp(new Date().getTime());
			result = (restaurantRepository().updateAvailability(id, false,
					time) == 1);
			logger.info("Turn off for Restaurant " + id + " at "
					+ time + ": " + result);
		}
		return result;
	}

	@RequestMapping(value = "/restaurant/password", method = RequestMethod.POST)
	@ResponseBody
	public boolean modifyPassword(@RequestParam Integer id,
			@RequestParam String password, @RequestParam String newPassword) {
		boolean result = (restaurantRepository().updatePassword(id,
				encode(password), encode(newPassword)) == 1);
		logger.info("Change password for Restaurant {}: {}", id, result);
		return result;
	}
}

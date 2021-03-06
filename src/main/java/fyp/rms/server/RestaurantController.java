package fyp.rms.server;

import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.RestaurantJDBCTemplate;
import fyp.rms.entity.Restaurant;
import fyp.rms.utility.MLHelper;

@Controller
public class RestaurantController {
	private static final Logger logger = LoggerFactory
			.getLogger(RestaurantController.class);

	private RestaurantJDBCTemplate repository() {
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

	@RequestMapping(value = "/restaurant")
	@ResponseBody
	public Restaurant view(@RequestParam Integer id) {
		Restaurant restaurant = repository().find(id);
		logger.info("Return Restaurant {}", id);
		return restaurant;
	}

	@RequestMapping(value = "/restaurant/edit")
	@ResponseBody
	public boolean modify(@RequestParam Integer id,
			@RequestParam Integer districtId, @RequestParam String name,
			@RequestParam String address, @RequestParam String phoneNo,
			@RequestParam String openingHours, @RequestParam String description) {
		boolean result = (repository().update(
				new Restaurant(id, districtId, name, address, phoneNo,
						openingHours, description)) == 1);
		logger.info("Modify info for Restaurant {}: {}", id, result);
		return result;
	}

	@RequestMapping(value = "/restaurant/waitinglist")
	@ResponseBody
	public boolean modifyAvailability(
			@RequestParam Integer id,
			@RequestParam boolean status,
			@RequestParam(required = false, defaultValue = "false") boolean reset) {
		if (reset == true
				&& (!(new TicketController()).record(id) || !(new MLHelper())
						.update(id)))
			return false;
		boolean result = (repository().updateAvailability(id, status) == 1);
		logger.info("Modify availability to " + status + " for Restaurant "
				+ id + ": " + result);
		return result;
	}

	@RequestMapping(value = "/restaurant/password", method = RequestMethod.POST)
	@ResponseBody
	public boolean modifyPassword(@RequestParam Integer id,
			@RequestParam String password, @RequestParam String newPassword) {
		boolean result = (repository().updatePassword(id, encode(password),
				encode(newPassword)) == 1);
		logger.info("Modify password for Restaurant {}: {}", id, result);
		return result;
	}

	@RequestMapping(value = "/restaurants")
	@ResponseBody
	public List<Restaurant> listAll() {
		List<Restaurant> restaurants = repository().findAll();
		logger.info("Return all {} available restaurant(s)", restaurants.size());
		return restaurants;
	}

	@RequestMapping(value = "/restaurants", params = "areaId")
	@ResponseBody
	public List<Restaurant> list(@RequestParam Integer areaId,
			@RequestParam Integer districtId, @RequestParam String name) {
		List<Restaurant> restaurants;
		if (districtId > 0)
			restaurants = repository().findByDistrict(districtId, name);
		else if (areaId > 0)
			restaurants = repository().findByArea(areaId, name);
		else
			restaurants = repository().findByName(name);
		logger.info("Return " + restaurants.size()
				+ " restaurant(s) of areaId = " + areaId + ", districtId = "
				+ districtId + ", name = " + name);
		return restaurants;
	}

	public boolean login(Integer id, String password) {
		boolean result = (repository().authenticate(id, encode(password)) == 1);
		logger.info("Login for Restaurant {}: {}", id, result);
		return result;
	}
}

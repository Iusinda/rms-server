package fyp.rms.server;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.Dao.TicketJDBCTemplate;
import fyp.rms.Dao.CustomerJDBCTemplate;
import fyp.rms.Entity.Customer;
import fyp.rms.Entity.Ticket;

@Controller
public class TicketController {
	private TicketJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (TicketJDBCTemplate) context.getBean("TicketJDBCTemplate");
	}

	private CustomerJDBCTemplate customerRepository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (CustomerJDBCTemplate) context.getBean("CustomerJDBCTemplate");
	}

	private void performEstimation(Ticket ticket) {
		ticket.setPosition(repository().findPosition(ticket.getRestaurantId(),
				ticket.getType()) + 1);
		// machine learning
		ticket.setDuration(ticket.getPosition());
	}

	private void updateEstimation(Ticket ticket) {
		ticket.setPosition(repository().findPosition(ticket.getRestaurantId(),
				ticket.getType(), ticket.getNumber()) + 1);
		// check current status
		ticket.setDuration(ticket.getPosition());
	}

	private static final Logger logger = LoggerFactory
			.getLogger(TicketController.class);

	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	@ResponseBody
	public Ticket info(@RequestParam Integer customerId) {
		Ticket ticket = repository().find(customerId);
		if (ticket != null) {
			updateEstimation(ticket);
			logger.info("Return Ticket " + (char) (ticket.getType() + 65)
					+ ticket.getNumber() + " of Restaurant "
					+ ticket.getRestaurantId() + " to Customer " + customerId);
		} else
			logger.info("Return no ticket to Customer " + customerId);
		return ticket;
	}

	@RequestMapping(value = "/ticket/preview", method = RequestMethod.GET)
	@ResponseBody
	public Ticket preview(@RequestParam Integer restaurantId,
			@RequestParam Integer type) {
		Ticket ticket = new Ticket(restaurantId, type);
		performEstimation(ticket);
		logger.info("Return preview of " + (char) (type + 65)
				+ " type ticket of Restaurant " + restaurantId);
		return ticket;
	}

	@RequestMapping(value = "/ticket/create", method = RequestMethod.GET)
	@ResponseBody
	public Ticket create(@RequestParam Integer restaurantId,
			@RequestParam Integer type, @RequestParam Integer size,
			@RequestParam(required = false) Integer customerId) {
		Integer number = repository().findNumber(restaurantId, type) + 1;
		Ticket ticket = new Ticket(restaurantId, type, number, size, customerId);
		performEstimation(ticket);
		boolean result = repository().create(ticket) == 1;
		logger.info("Dispense Ticket " + (char) (type + 65) + number
				+ " of Restaurant " + restaurantId
				+ (result ? " successfully" : " unsuccessfully"));
		return ticket;
	}

	@RequestMapping(value = "/ticket/call", method = RequestMethod.GET)
	@ResponseBody
	public Integer call(@RequestParam Integer restaurantId,
			@RequestParam Integer type, @RequestParam Integer customerId) {
		Integer number = repository().updateCallTime(restaurantId, type);
		if (number != 0) {
			if (customerId != 0) {
				Customer customer = customerRepository().find(customerId);
				// send notification to customer's mobile device by regId
			}
			logger.info("Call Ticket " + (char) (type + 65) + number
					+ " of Restaurant " + restaurantId);
		} else
			logger.info("Call " + (char) (type + 65)
					+ " type ticket of Restaurant " + restaurantId
					+ "unsuccessfully");
		return number;
	}

	@RequestMapping(value = "/ticket/remove", method = RequestMethod.GET)
	@ResponseBody
	public boolean remove(@RequestParam Integer customerId,
			@RequestParam(required = false) Integer restaurantId,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) Integer number) {
		boolean result;
		if (customerId != 0)
			result = repository().updateValidity(customerId) >= 1;
		else
			result = repository().updateValidity(restaurantId, type, number) == 1;
		logger.info("Remove Ticket " + (char) (type + 65) + number
				+ " of Restaurant " + restaurantId + ": " + result);
		return result;
	}

	@RequestMapping(value = "/tickets", method = RequestMethod.GET)
	@ResponseBody
	public List<Ticket> list(@RequestParam Integer restaurantId,
			@RequestParam Integer type) {
		List<Ticket> tickets = repository().findByType(restaurantId, type);
		logger.info("Return all " + tickets.size() + " valid "
				+ (char) (type + 65) + " type tickets of Restaurant "
				+ restaurantId);
		return tickets;
	}

	@SuppressWarnings("deprecation")
	public boolean record(@RequestParam Integer restaurantId) {
		List<Ticket> tickets = repository().findByRestaurant(restaurantId);
		int i, time, duration;
		String str;
		// File file = new File(restaurantId + ".arff");
		for (i = 0; i < tickets.size(); i++) {
			time = tickets.get(i).getGetTime().getHours() * 60
					+ tickets.get(i).getGetTime().getMinutes();
			duration = (int) (tickets.get(i).getCallTime().getTime() - tickets
					.get(i).getGetTime().getTime()) / 60000;
			str = tickets.get(i).getType() + ","
					+ tickets.get(i).getGetTime().getDay() + "," + time + ","
					+ tickets.get(i).getPosition() + "," + duration + "\n";
			// try {
			// FileUtils.writeStringToFile(file, str + "\n", true);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			logger.info(str);
		}
		boolean result = repository().delete(restaurantId) == 0;
		logger.info("Record and delete all tickets of Restaurant "
				+ restaurantId + (result ? " successfully" : " unsuccessfully"));
		return result;
	}
}

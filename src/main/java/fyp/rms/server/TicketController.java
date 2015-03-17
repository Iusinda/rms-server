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

	@RequestMapping(value = "/ticket", params = "customerId")
	@ResponseBody
	public Ticket view(@RequestParam Integer customerId) {
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

	@RequestMapping(value = "/ticket", params = "id")
	@ResponseBody
	public Ticket preview(@RequestParam Integer id, @RequestParam Integer type) {
		Ticket ticket = new Ticket(id, type);
		performEstimation(ticket);
		logger.info("Return preview of " + (char) (type + 65)
				+ " type ticket of Restaurant " + id);
		return ticket;
	}

	@RequestMapping(value = "/ticket/create")
	@ResponseBody
	public Ticket create(@RequestParam Integer id, @RequestParam Integer type,
			@RequestParam Integer size,
			@RequestParam(required = false) Integer customerId) {
		Integer number = repository().findNumber(id, type) + 1;
		Ticket ticket = new Ticket(id, type, number, size, customerId);
		performEstimation(ticket);
		boolean result = repository().create(ticket) == 1;
		logger.info("Dispense Ticket " + (char) (type + 65) + number
				+ " of Restaurant " + id
				+ (result ? " successfully" : " unsuccessfully"));
		return ticket;
	}

	@RequestMapping(value = "/ticket/call")
	@ResponseBody
	public Integer call(@RequestParam Integer id, @RequestParam Integer type,
			@RequestParam Integer customerId) {
		Integer number = repository().updateCallTime(id, type);
		if (number != 0) {
			if (customerId != 0) {
				Customer customer = customerRepository().find(customerId);
				// send notification to customer's mobile device by regId
			}
			logger.info("Call Ticket " + (char) (type + 65) + number
					+ " of Restaurant " + id);
		} else
			logger.info("Call " + (char) (type + 65)
					+ " type ticket of Restaurant " + id + "unsuccessfully");
		return number;
	}

	@RequestMapping(value = "/ticket/remove")
	@ResponseBody
	public boolean remove(@RequestParam Integer customerId,
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) Integer number) {
		boolean result;
		if (customerId != 0)
			result = repository().updateValidity(customerId) >= 1;
		else
			result = repository().updateValidity(id, type, number) == 1;
		logger.info("Remove Ticket " + (char) (type + 65) + number
				+ " of Restaurant " + id + ": " + result);
		return result;
	}

	@RequestMapping(value = "/tickets")
	@ResponseBody
	public List<Ticket> list(@RequestParam Integer id,
			@RequestParam Integer type) {
		List<Ticket> tickets = repository().findByType(id, type);
		logger.info("Return all " + tickets.size() + " valid "
				+ (char) (type + 65) + " type tickets of Restaurant " + id);
		return tickets;
	}

	@SuppressWarnings("deprecation")
	public boolean record(Integer id) {
		List<Ticket> tickets = repository().findByRestaurant(id);
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
		boolean result = repository().delete(id) == 0;
		logger.info("Record and delete all tickets of Restaurant " + id
				+ (result ? " successfully" : " unsuccessfully"));
		return result;
	}
}
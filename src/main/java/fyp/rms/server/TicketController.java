package fyp.rms.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.TicketJDBCTemplate;
import fyp.rms.dao.HolidayJDBCTemplate;
import fyp.rms.entity.Customer;
import fyp.rms.entity.Ticket;
import fyp.rms.utility.GCMHelper;
import fyp.rms.utility.MLHelper;

@Controller
public class TicketController {
	private static final Logger logger = LoggerFactory
			.getLogger(TicketController.class);

	private TicketJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (TicketJDBCTemplate) context.getBean("TicketJDBCTemplate");
	}

	private Integer getDayOfWeek(Calendar date) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		HolidayJDBCTemplate holidayRepository = (HolidayJDBCTemplate) context
				.getBean("HolidayJDBCTemplate");

		Integer dayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
		// For holiday: treat as Sunday
		if (dayOfWeek == 0 || holidayRepository.find(date) == 1)
			return 0;
		// For the day before holiday (except Saturday and Sunday): treat as
		// Friday
		date.add(Calendar.DATE, 1);
		if (dayOfWeek < 5 && holidayRepository.find(date) == 1)
			return 5;
		return dayOfWeek;
	}

	private void performEstimation(Ticket ticket) {
		ticket.setPosition(repository().findPosition(ticket.getRestaurantId(),
				ticket.getType()) + 1);
		// machine learning
		Calendar time = Calendar.getInstance();
		Integer duration = (new MLHelper())
				.calculate(ticket.getRestaurantId(), ticket.getType(),
						getDayOfWeek(time), time.get(Calendar.HOUR_OF_DAY) * 60
								+ time.get(Calendar.MINUTE),
						ticket.getPosition());
		ticket.setDuration(duration);
	}

	private void updateEstimation(Ticket ticket) {
		ticket.setPosition(repository().findPosition(ticket.getRestaurantId(),
				ticket.getType(), ticket.getNumber()) + 1);
		Calendar time = Calendar.getInstance();
		Integer timeLeft = (int) (ticket.getGetTime().getTime() / 60000
				+ ticket.getDuration() - time.getTimeInMillis() / 60000);
		ticket.setDuration(timeLeft);
	}

	@RequestMapping(value = "/test")
	@ResponseBody
	public Integer test(@RequestParam Integer type, @RequestParam Integer day,
			@RequestParam Integer time, @RequestParam Integer position) {
		return (new MLHelper()).calculate(2, type, day, time, position);
	}

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

	@RequestMapping(value = "/ticket")
	@ResponseBody
	public void test() {
		Integer duration = (new MLHelper()).calculate(2, 1, 1, 1223, 26);
		System.out.println("duration: " + duration);
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
	public Integer call(@RequestParam Integer id, @RequestParam Integer type) {
		Ticket ticket = repository().updateCallTime(id, type);
		if (ticket != null) {

			if (ticket.getCustomerId() != 0) {
				Customer customer = (new CustomerController()).find(ticket
						.getCustomerId());
				logger.info("Call Customer " + customer.getId());
				GCMHelper gcmHelper = new GCMHelper(
						"Your table is available now.", customer.getRegId());
				gcmHelper.sendMessage();
			}
			logger.info("Call Ticket " + (char) (type + 65)
					+ ticket.getNumber() + " of Restaurant " + id
					+ " successfully");
			return ticket.getNumber();
		} else {
			logger.info("Call " + (char) (type + 65)
					+ " type ticket of Restaurant " + id + " unsuccessfully");
			return 0;
		}

	}

	@RequestMapping(value = "/ticket/remove")
	@ResponseBody
	public boolean remove(@RequestParam(required = false) Integer customerId,
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) Integer number) {
		boolean result;
		if (customerId != null) {
			result = repository().updateValidity(customerId) >= 1;
			logger.info("Remove ticket of Customer " + customerId + ": "
					+ result);
		} else {
			result = repository().updateValidity(id, type, number) == 1;
			logger.info("Remove Ticket " + (char) (type + 65) + number
					+ " of Restaurant " + id + ": " + result);
		}
		return result;
	}

	@RequestMapping(value = "/tickets")
	@ResponseBody
	public List<Ticket> list(@RequestParam Integer id,
			@RequestParam Integer type) {
		return repository().findByType(id, type);
	}

	public boolean record(Integer id) {
		List<Ticket> tickets = repository().findByRestaurant(id);
		Calendar getTime = Calendar.getInstance();
		try {
			PrintWriter file = new PrintWriter(new BufferedWriter(
					new FileWriter("data/" + id + ".arff", true)));
			for (int i = 0; i < tickets.size(); i++) {
				getTime.setTime(tickets.get(i).getGetTime());
				int time = getTime.get(Calendar.HOUR_OF_DAY) * 60
						+ getTime.get(Calendar.MINUTE);
				int duration = (int) (tickets.get(i).getCallTime().getTime() - tickets
						.get(i).getGetTime().getTime()) / 60000;
				String str = tickets.get(i).getType() + ","
						+ getDayOfWeek(getTime) + "," + time + ","
						+ tickets.get(i).getPosition() + "," + duration;
				file.println(str);
				logger.info("" + str);
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean result = repository().delete(id) == 0;
		logger.info("Record and delete all tickets of Restaurant " + id
				+ (result ? " successfully" : " unsuccessfully"));
		return result;
	}
}

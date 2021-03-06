package fyp.rms.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.TicketTypeJDBCTemplate;
import fyp.rms.entity.TicketType;

@Controller
public class TicketTypeController {
	private static final Logger logger = LoggerFactory
			.getLogger(TicketTypeController.class);

	private TicketTypeJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (TicketTypeJDBCTemplate) context
				.getBean("TicketTypeJDBCTemplate");
	}

	@RequestMapping(value = "/tickettypes")
	@ResponseBody
	public List<TicketType> list(@RequestParam Integer id) {
		List<TicketType> ticketTypes = repository().findByRestaurant(id);
		logger.info("Return all {} ticket type(s) of Restaurant {}",
				ticketTypes.size(), id);
		return ticketTypes;
	}
}

package fyp.rms.server;

import java.sql.Timestamp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import fyp.rms.Dao.TicketJDBCTemplate;
import fyp.rms.Entity.Ticket;

@Controller
public class TicketController {
	private TicketJDBCTemplate ticketRepository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (TicketJDBCTemplate) context.getBean("TicketJDBCTemplate");
	}
	
	public boolean record(Timestamp time){
		return true;
	}
}

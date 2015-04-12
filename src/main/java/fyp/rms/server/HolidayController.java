package fyp.rms.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.HolidayJDBCTemplate;

@Controller
public class HolidayController {
	private static final Logger logger = LoggerFactory
			.getLogger(HolidayController.class);

	@RequestMapping(value = "/holiday/create")
	@ResponseBody
	public boolean create(@RequestParam Integer year,
			@RequestParam Integer month, @RequestParam Integer day) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		HolidayJDBCTemplate repository = (HolidayJDBCTemplate) context
				.getBean("HolidayJDBCTemplate");
		try {
			boolean result = repository.create(year, month, day) == 1;
			logger.info("Record holiday " + year + '-' + month + '-' + day
					+ (result ? " successfully" : " unsuccessfully"));
			return result;
		} catch (Exception e) {
			logger.info("Holiday " + year + '-' + month + '-' + day + " exists");
			return false;
		}
	}
}

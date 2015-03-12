package fyp.rms.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.Dao.AreaJDBCTemplate;
import fyp.rms.Entity.Area;

@Controller
public class AreaController {
	private AreaJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (AreaJDBCTemplate) context.getBean("AreaJDBCTemplate");
	}

	private static final Logger logger = LoggerFactory
			.getLogger(AreaController.class);

	@RequestMapping(value = "/areas", method = RequestMethod.GET)
	@ResponseBody
	public List<Area> listAll() {
		List<Area> areas = repository().findAll();
		logger.info("Return all {} area(s)", areas.size());
		return areas;
	}
}

package fyp.rms.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.dao.AreaJDBCTemplate;
import fyp.rms.entity.Area;

@Controller
public class AreaController {
	private static final Logger logger = LoggerFactory
			.getLogger(AreaController.class);

	private AreaJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (AreaJDBCTemplate) context.getBean("AreaJDBCTemplate");
	}

	@RequestMapping(value = "/areas")
	@ResponseBody
	public List<Area> listAll() {
		List<Area> areas = repository().findAll();
		logger.info("Return all {} area(s)", areas.size());
		return areas;
	}
}

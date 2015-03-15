package fyp.rms.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.Dao.DistrictJDBCTemplate;
import fyp.rms.Entity.District;

@Controller
public class DistrictController {
	private DistrictJDBCTemplate repository() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"jdbcConfig.xml");
		return (DistrictJDBCTemplate) context.getBean("DistrictJDBCTemplate");
	}

	private static final Logger logger = LoggerFactory
			.getLogger(DistrictController.class);

	@RequestMapping(value = "/districts")
	@ResponseBody
	public List<District> list(@RequestParam Integer areaId) {
		List<District> districts = repository().findByArea(areaId);
		logger.info("Return all {} district(s) in Area {}", districts.size(), areaId);
		return districts;
	}
}

package fyp.rms.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fyp.rms.server.entity.Customer;
import fyp.rms.server.entity.CustomerRepository;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {

		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/ajaxDemo", method = RequestMethod.GET)
	public String ajax() {
		return "ajax";
	}

	@RequestMapping(value = "/array", method = RequestMethod.GET)
	@ResponseBody
	public TestObject[] jsonArray() {
		TestObject[] a = new TestObject[3];
		a[0] = new TestObject();
		a[1] = new TestObject();
		a[2] = new TestObject();
		return a;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public Customer test() {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JpaConfiguration.class);
		CustomerRepository repository = context
				.getBean(CustomerRepository.class);
		repository.save(new Customer("Jack", "Bauer"));
		repository.save(new Customer("Chloe", "O'Brian"));
		repository.save(new Customer("Kim", "Bauer"));
		repository.save(new Customer("David", "Palmer"));
		repository.save(new Customer("Michelle", "Dessler"));
		Customer customer = repository.findOne(1L);
		return customer;
	}
}

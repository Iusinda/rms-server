package fyp.rms.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ViewController {

	private static final Logger logger = LoggerFactory
			.getLogger(ViewController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if (session.getAttribute("id") != null)
			return "home";
		else
			return "login";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session) {
		logger.info("***** Return login page");
		if (session.getAttribute("id") != null)
			return "home";
		else
			return "login";
	}
}

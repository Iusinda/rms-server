package fyp.rms.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/")
public class ViewController {
	@RequestMapping()
	public String home(HttpSession session) {
		if (session.getAttribute("rid") != null)
			return "home";
		else
			return "login";
	}
	
	@RequestMapping(value = "/signin")
	public String logout(HttpSession session) {
		session.removeAttribute("rid");
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST, params = "id")
	public String login(HttpSession session, ModelMap model,
			@RequestParam String id, @RequestParam String password) {
		try{
			Integer idInt = Integer.parseInt(id);
			if ((new RestaurantController()).login(idInt, password)) {
				session.setAttribute("rid", id);
				return "home";
			} else {
				model.addAttribute("message", "Incorrect User ID or Password.");
			}
		} catch (Exception e){
			model.addAttribute("message", "User ID should be an integer.");
		} 
		model.addAttribute("show", true);
		return "login";
	}
}

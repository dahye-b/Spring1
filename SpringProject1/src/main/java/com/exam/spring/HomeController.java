package com.exam.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.exam.bean.UserBean;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpServletRequest request, Model model) {
		if(getUserSession(request) != null) {
			return redirectView(request, "/");
		}else {
			return new ModelAndView("login");
		}
	}
	
	@RequestMapping(value = "/login-request", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request, Model model,
		@RequestParam (name="login_email") String username,
		@RequestParam (name="login_password") String password){
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("LOGIN_EXCEPTION") != null) {
			session.removeAttribute("LOGIN_EXCEPTION");
		}
		
		final String examUsername = "exam@exam.com";
		final String examPassword  ="1234";
		
		if(username.equals(examUsername) && password.equals(examPassword)) {
			UserBean user = new UserBean();
			user.setUsername(username);
			user.setPassword(password);
			user.setNickname("Spring Project Example");
			
			session.setAttribute("USER_SESSION", user);
			
			return redirectView(request, "/");
		}else {
			session.setAttribute("LOGIN_EXCEPTION", "로그인 정보가 일치하지 않습니다.");
		}
		
	return redirectView(request, "/login");
	}
	
	@RequestMapping(value="/logoff", method = RequestMethod.GET)
	public ModelAndView logoff(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		
		if(session != null){
			session.invalidate();
		}
		return redirectView(request, "/");
	}
	
	private UserBean getUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			Object obj = session.getAttribute("USER_SESSION");
			
			//값이 null이 아니면서 UserBean Class값이
			if(obj != null && obj instanceof UserBean){
				//UserBean Class형태로 캐스팅 한 후 값 리
				return (UserBean) obj;
			}
		}
		return null;
	}
	
	private ModelAndView redirectView(HttpServletRequest request, String url) {
		RedirectView rv = new RedirectView();
		ModelAndView mav = new ModelAndView(rv);
		rv.setUrl(request.getContextPath()+url);
		
		//get방식으로 된 파라메터들 모두 없앰 
		rv.setExposePathVariables(false);
		//model에 등록된 값들을 모두 없엠 
		rv.setExposeModelAttributes(false);
		
		return mav;
	}
}

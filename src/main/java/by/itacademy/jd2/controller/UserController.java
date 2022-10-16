package by.itacademy.jd2.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import by.itacademy.jd2.entity.News;
import by.itacademy.jd2.entity.User;

import by.itacademy.jd2.service.INewsService;
import by.itacademy.jd2.service.IUserService;
import by.itacademy.jd2.service.ServiceException;

import by.itacademy.jd2.util.AttributsName;
import by.itacademy.jd2.util.ConnectorStatus;
import by.itacademy.jd2.util.JspPageName;
import by.itacademy.jd2.util.PageUrl;
import by.itacademy.jd2.util.UserParameterName;
import by.itacademy.jd2.entity.UserRole;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private IUserService service;
	@Autowired
	private INewsService newsService;	
	private static final String AUTHENTICATION_ERROR = "&AuthenticationError";
	private static final int COUNT_NEWS = 5;
	
	@PostMapping("/login")
	public String singIn(HttpServletRequest request, Model model)  {
		String login = request.getParameter(UserParameterName.JSP_LOGIN_PARAM);
		String password = request.getParameter(UserParameterName.JSP_PASSWORD_PARAM);
		HttpSession getSession = request.getSession(true);
		List<News> latestNews;		
		
		if (!dataValidation(login, password)) {            
            return "redirect:/JspPageName.INDEX_PAGE";
        }
		try {			
			User user = service.signIn(login, password);
			latestNews = newsService.latestList(COUNT_NEWS);
			if (user == null) {				
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.NOT_ACTIVE);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				getSession.setAttribute(AttributsName.ROLE, UserRole.GUEST);
				request.setAttribute(AttributsName.NEWS, latestNews);				
				StringBuilder urlForRedirect = new StringBuilder(PageUrl.BASE_PAGE);
				urlForRedirect.append(AUTHENTICATION_ERROR);
				return "redirect:/urlForRedirect.toString()";				
			} else if (!user.getRole().equals(UserRole.GUEST)) {
				getSession.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
				getSession.setAttribute(AttributsName.ROLE, user.getRole().getName());
				getSession.setAttribute(AttributsName.USER, user);
				getSession.removeAttribute(AttributsName.REGISTER_USER);
				request.setAttribute(AttributsName.NEWS, latestNews);
				return "redirect:/PageUrl.NEWS_LIST_PAGE";
			} 
		} catch (ServiceException e) {
			return "redirect:/JspPageName.INDEX_PAGE";
		}
		return "/news_list";		
	}	
	
	private boolean dataValidation(String login, String password) {
        if (login == null || password == null) {
            return false;
        }
        return true;
    }
}
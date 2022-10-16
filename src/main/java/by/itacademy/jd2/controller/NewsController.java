package by.itacademy.jd2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.itacademy.jd2.dao.NewsDaoException;
import by.itacademy.jd2.entity.News;
import by.itacademy.jd2.service.INewsService;
import by.itacademy.jd2.service.ServiceException;
import by.itacademy.jd2.util.AttributsName;
import by.itacademy.jd2.util.ConnectorStatus;
import by.itacademy.jd2.util.JspPageName;
import by.itacademy.jd2.util.PageUrl;

@Controller
public class NewsController {
	@Autowired
    private INewsService newsService;	
	private static final int COUNT_NEWS = 5;
    private static final String PAGE_NUMBER = "pageNo";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String COUNT_PAGE = "countPage";
	private static final String ERROR_ADD_NEWS_MESSAGE = "&AddNewsError";

    @GetMapping("/base_page")
    public String basePage(HttpServletRequest request, Model model) {
        List<News> latestNews;
        
        try {
            latestNews = newsService.latestList(COUNT_NEWS);
            model.addAttribute(AttributsName.NEWS, latestNews);
            return "layouts/baseLayout";
        } catch (ServiceException e) {
            return "error";
        }
    }
    
    @GetMapping("/news_list")
    public String newsList(HttpServletRequest request, Model model) {
    	List<News> newsList;
		Integer pageNumber;
		Integer pageSize = 5;
		Integer countPage = 0;
		
		try {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER));
		} catch (NumberFormatException e) {
			pageNumber = 1;
		}		
		try {
			newsList = newsService.newsList(pageNumber, pageSize);
			countPage = newsService.countPage(pageSize);
			model.addAttribute(CURRENT_PAGE, pageNumber);
			model.addAttribute(COUNT_PAGE, countPage);
			model.addAttribute(AttributsName.NEWS, newsList);
			model.addAttribute(AttributsName.PRESENTATION, AttributsName.NEWS_LIST);
			return "layouts/baseLayout";
		} catch (ServiceException e) {
			return "error";
		}		
	}
    
    @GetMapping("/addNewsPage")
    public String goToAddNewsPage(HttpServletRequest request, @ModelAttribute(AttributsName.NEWS) News news) {
    	request.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
    	request.setAttribute(AttributsName.NEWS_COMMANDS_NAME, AttributsName.ADD_NEWS);
		request.removeAttribute(AttributsName.NEWS_COMMANDS_NAME);
    	return "layouts/baseLayout";
    }

    @PostMapping("/addNews")
    public String addNews(HttpServletRequest request, @ModelAttribute(AttributsName.NEWS) News news) throws NewsDaoException {
        try {
            newsService.save(news);
            request.setAttribute(AttributsName.USER_STATUS, ConnectorStatus.ACTIVE);
            request.setAttribute(AttributsName.ADD_NEWS, AttributsName.COMMAND_EXECUTED);
            return "redirect:/news_list";
        } catch (ServiceException e) {
        	StringBuilder urlForRedirect = new StringBuilder(PageUrl.ADD_NEWS_PAGE);
			urlForRedirect.append(ERROR_ADD_NEWS_MESSAGE);
			return "redirect:/urlForRedirect.toString()";
        }
    }

    @PostMapping("/editNews/{id}")
    public String showEditNewsForm(@PathVariable("id") String id,  Model model) {
        try {
            News news = newsService.findById(Integer.parseInt(id));
            model.addAttribute(newsAttribute, news);
            model.addAttribute(addNewsAttribute, editNewsStatus);
            model.addAttribute(editViewAttribute, editNewsStatus);
            return "baseLayout";
        } catch (ServiceException e) {
            return "error";
        }
    }

    @PostMapping("/delete")
    public String deleteNews(HttpServletRequest request, RedirectAttributes attr) {
        String[] idNews = request.getParameterValues(newsIdParam);
        try {
            if (idNews == null) {
                return "redirect:/newsList";
            }
            newsService.delete(idNews);
            attr.addAttribute(deleteMessageAttribute, deleteMessage);
            return "redirect:/newsList";
        } catch (ServiceException e) {
            return "error";
        }
    }
    
    
}
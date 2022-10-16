package by.itacademy.jd2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itacademy.jd2.entity.News;
import by.itacademy.jd2.dao.INewsDao;
import by.itacademy.jd2.dao.NewsDaoException;
import by.itacademy.jd2.service.INewsService;
import by.itacademy.jd2.service.ServiceException;
import by.itacademy.jd2.util.validation.NewsDataValidation;

@Service
public class NewsServiceImpl implements INewsService{
	private static final int COUNT_NEWS = 5;
	private static final String NEWS_PUBLISHED = "yes";
	private static final String NEWS_UNPUBLISHED = "no";
	
	@Autowired
	private INewsDao newsDao;
	
	//@Autowired
	//public NewsServiceImpl(INewsDao newsDao) {
	//	super();
	//	this.newsDao = newsDao;
	//}

	@Override
	@Transactional
	public List<News> latestList(int count) throws ServiceException {		
		try {
			return newsDao.getLatestList(COUNT_NEWS);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<News> newsList(Integer pageNumber, Integer pageSize) throws ServiceException {
		try {
			return newsDao.getNewsList(pageNumber, pageSize);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public News findById(Integer idNews) throws ServiceException {
		try {
			return newsDao.fetchById(idNews);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean save(News news) throws ServiceException {
		news.setPublished(NEWS_PUBLISHED);
		NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
		NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
				.briefValid(news.getBrief())
				.contentValid(news.getContent())
				.dateValid(news.getDate())
				.authorValid(news.getAuthor())
				.build();
		if(!validNewsData.getDataValid().isEmpty()) {
			throw new ServiceException("The entered news data is not valid!");
		} else
			try {
				if (!(newsDao.addNews(news))) {
					return false;
				}
			} catch (NewsDaoException e) {
				throw new ServiceException(e);
			}
		return true;
	}

	@Override
	@Transactional
	public boolean update(News news) throws ServiceException {
		NewsDataValidation.ValidBuilder valid = new NewsDataValidation.ValidBuilder();			
		NewsDataValidation validNewsData = valid.titleValid(news.getTitle())
				.briefValid(news.getBrief())
				.contentValid(news.getContent())					
				.dateValid(news.getDate())
				.authorValid(news.getAuthor())
				.build();
		if(!validNewsData.getDataValid().isEmpty()) {
			throw new ServiceException("The entered news data is not valid!");
		} else
			try {
				if (!(newsDao.updateNews(news))) {
					return false;
				}
			} catch (NewsDaoException e) {
				throw new ServiceException(e);
			}
		return true;
	}
	
	@Override
	@Transactional
	public boolean unpublishNewsById(String[] idNews) throws ServiceException {
		try {
			if (!(newsDao.unpublishNews(idNews))) {
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean deleteNewsById(String[] idNews) throws ServiceException {		
		try {
			if (!(newsDao.deleteNews(idNews))) {							
				return false;
			}
			return true;
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional
	public int countPage(int countNewsPage) throws ServiceException {
		try {
			int countNews = newsDao.countNews();
			if (countNews == 0) {
				return 0;
			}
			return (int) Math.ceil(countNews / (double) countNewsPage);
		} catch (NewsDaoException e) {
			throw new ServiceException(e);
		}
	}
}
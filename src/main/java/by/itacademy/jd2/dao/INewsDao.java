package by.itacademy.jd2.dao;

import java.util.List;

import by.itacademy.jd2.entity.News;

public interface INewsDao {
	List<News> getLatestList(int pageSize) throws NewsDaoException;
	List<News> getNewsList(int pageNumber, int pageSize) throws NewsDaoException;
	News fetchById(Integer idNews) throws NewsDaoException;
	boolean addNews(News news) throws NewsDaoException;
	boolean updateNews(News news) throws NewsDaoException;
	boolean unpublishNews(String[] idNews) throws NewsDaoException;
	boolean deleteNews(String[] idNews) throws NewsDaoException;
	int countNews() throws NewsDaoException;	
}
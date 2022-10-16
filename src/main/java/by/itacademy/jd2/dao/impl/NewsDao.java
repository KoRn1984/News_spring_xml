package by.itacademy.jd2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import by.itacademy.jd2.entity.News;
import by.itacademy.jd2.dao.INewsDao;
import by.itacademy.jd2.util.HibernateSessionFactoryUtil;

@Repository
public class NewsDao implements INewsDao {
	private static final String NEWS_PUBLISHED = "yes";
	private static final String NEWS_UNPUBLISHED = "no";
		
	private static final String SELECT_NEWS_LATEST_LIST = "from News where published=:paramPublished order by date desc";
	@Override
	public List<News> getLatestList(int pageSize) {
		List<News> newsLatestList = new ArrayList<>();
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<News> query = session.createQuery(SELECT_NEWS_LATEST_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setMaxResults(pageSize);
        newsLatestList = query.getResultList();
        tx.commit();
        session.close();
        return newsLatestList;
	}			

	private static final String SELECT_NEWS_LIST = "from News where published=:paramPublished order by date desc";
	@Override
	public List<News> getNewsList(int pageNumber, int pageSize) {
		List<News> newsList = new ArrayList<>();
		int startSize = (pageNumber - 1) * pageSize;		 
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<News> query = session.createQuery(SELECT_NEWS_LIST, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.setFirstResult(startSize);
        query.setMaxResults(pageSize);
        newsList = query.getResultList();
        tx.commit();
        session.close();
        return newsList;
	}
	
	@Override
	public News fetchById(Integer idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        News news = session.get(News.class, idNews);
        tx.commit();
        session.close();
        return news;
	}
	
	@Override
	public boolean addNews(News news) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(news);
        tx.commit();
        session.close();
        return true;
	}
	
	@Override
	public boolean updateNews(News news) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.refresh(news);
        tx.commit();
        session.close();
        return true;
	}
	
	private static final String UNPUBLISH_NEWS = "update News set published = :paramPublished where id = :ids";
	@Override
	public boolean unpublishNews(String[] idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> ids = new ArrayList<>();
        for (String id : idNews) {
        	ids.add(id);
        }
        session.flush();
        session.clear();
        Query<News> query = session.createQuery(UNPUBLISH_NEWS, News.class);
        query.setParameter("paramPublished", NEWS_UNPUBLISHED);
        query.setParameter("ids", ids);
        query.executeUpdate();
        tx.commit();
        session.close();
        return true;
	}
	
	private static final String DELETE_NEWS = "delete from News where id in (:ids)";
	@Override
	public boolean deleteNews(String[] idNews) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<String> ids = new ArrayList<>();
        for (String id : idNews) {
        	ids.add(id);
        }
        session.flush();
        session.clear();
        Query<News> query = session.createQuery(DELETE_NEWS, News.class);
        query.setParameter("ids", ids);
        query.executeUpdate();
        tx.commit();
        session.close();
        return true;
	}
	
	private static final String SELECT_COUNT_NEWS = "select count(*) from News where published = :paramPublished";
	@Override
	public int countNews() {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query<News> query = session.createQuery(SELECT_COUNT_NEWS, News.class);
        query.setParameter("paramPublished", NEWS_PUBLISHED);
        query.uniqueResult();
        int count = ((Integer)query.getFirstResult());
        tx.commit();
        session.close();
        System.out.println(count);
        return count;
	}	
}	
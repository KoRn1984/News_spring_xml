package by.itacademy.jd2.dao;

import java.sql.SQLException;

import by.itacademy.jd2.entity.User;
import by.itacademy.jd2.service.ServiceException;

public interface IUserDao {	
	User findUserByLogin(String login) throws DaoException;
	boolean saveUser(User user) throws DaoException, ServiceException;
	User findById(Integer id) throws SQLException, DaoException;	
}
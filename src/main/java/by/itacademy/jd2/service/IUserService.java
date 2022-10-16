package by.itacademy.jd2.service;

import by.itacademy.jd2.entity.User;

public interface IUserService {	
	User signIn(String login, String password) throws ServiceException;
	boolean registration(User user) throws ServiceException;
}
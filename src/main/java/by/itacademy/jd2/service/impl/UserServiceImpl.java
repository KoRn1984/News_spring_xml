package by.itacademy.jd2.service.impl;

import by.itacademy.jd2.dao.DaoException;
import by.itacademy.jd2.dao.IUserDao;
import by.itacademy.jd2.service.IUserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itacademy.jd2.entity.User;
import by.itacademy.jd2.service.ServiceException;
import by.itacademy.jd2.util.validation.UserDataValidation;
import by.itacademy.jd2.util.validation.ValidationProvider;

@Service
public class UserServiceImpl implements IUserService{	
	
	@Autowired
	private IUserDao userDao;
	private final UserDataValidation userDataValidation = ValidationProvider. getInstance().getUserDataValidation();
	
	@Override
	@Transactional
	public User signIn(String login, String password) throws ServiceException {
		if (!userDataValidation.checkAuthDataLogination(login, password)) {
			throw new ServiceException("Invalid authorization data!");
   	 }
		User user;
		try {
			user = userDao.findUserByLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}			
		if((user != null) && (BCrypt.checkpw(password, user.getPassword())) && (login.equals(user.getLogin()))) {
			return user;				
			} else {
				return null;
			}
		}
	
	@Override
	@Transactional
	public boolean registration(User user) throws ServiceException  {
		  if (!userDataValidation.checkAuthDataRegistration(user)) {
			  throw new ServiceException("Invalid registration data!");
	  }
		  try {			  
			  if((user != null)) {
				  String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
				  user.setPassword(hashPassword);	                    
				  return userDao.saveUser(user);			
					} else {
						return false;
					}			  			  
		   }catch(DaoException e) {
				throw new ServiceException(e);
				}		  
	}
}
package by.itacademy.jd2.util.validation;

import by.itacademy.jd2.entity.User;
import by.itacademy.jd2.service.ServiceException;

public interface UserDataValidation {
    boolean checkAuthDataLogination(String login, String password)  throws ServiceException;
    boolean checkAuthDataRegistration(User user) throws ServiceException;   
}
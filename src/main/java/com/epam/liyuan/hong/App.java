package com.epam.liyuan.hong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.liyuan.hong.facade.BookingFacade;
import com.epam.liyuan.hong.service.EventService;
import com.epam.liyuan.hong.service.TicketService;
import com.epam.liyuan.hong.service.UserService;

public class App {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
		
		EventService e = (EventService) context.getBean("eventService");
		TicketService t = (TicketService) context.getBean("ticketService");
		UserService u = (UserService) context.getBean("userService");
		BookingFacade b = (BookingFacade) context.getBean("bookingImpl");
		
		Logger logger = LoggerFactory.getLogger(App.class);
		logger.info("Application init successful with xml based configuration");
	}
}

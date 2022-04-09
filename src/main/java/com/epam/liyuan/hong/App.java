package com.epam.liyuan.hong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.epam.liyuan.hong.facade.BookingFacade;
import com.epam.liyuan.hong.service.EventService;
import com.epam.liyuan.hong.service.TicketService;
import com.epam.liyuan.hong.service.UserService;

@PropertySource("classpath:app.properties")
public class App {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
		
//		EventService e = (EventService) context.getBean("eventService");
//		TicketService t = (TicketService) context.getBean("ticketService");
//		UserService u = (UserService) context.getBean("userService");
//		BookingFacade b = (BookingFacade) context.getBean("bookingImpl");
		
		Logger logger = LoggerFactory.getLogger(App.class);
		logger.info("Application init successful with xml based configuration");
		
//		FileReadingMessageSource messageSource = (FileReadingMessageSource) context.getBean("pollableFileSource");
//		messageSource.setDirectory(null);
//		Resource eventResource = context.getResource("${app.prepareddata.file.event}");
		Resource ticketResource = context.getResource("${app.prepareddata.file.ticket}");
		Resource userResource = context.getResource("${app.prepareddata.file.user}");
		FileSystemResource eventResource = (FileSystemResource) context.getBean("eventFileResource");
		System.out.println(eventResource.getPath());
		System.out.println();
		System.out.println();
		boolean successful = eventResource.exists() && ticketResource.exists() && userResource.exists();
		String not = successful ? "" : " not";
		logger.info("Application init" + not + " successful with xml based configuration");
	}
}

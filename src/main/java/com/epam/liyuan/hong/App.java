package com.epam.liyuan.hong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

@PropertySource("classpath:app.properties")
public class App {

	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");) {
		Environment env = context.getEnvironment();
//		EventService e = (EventService) context.getBean("eventService");
//		TicketService t = (TicketService) context.getBean("ticketService");
//		UserService u = (UserService) context.getBean("userService");
//		BookingFacade b = (BookingFacade) context.getBean("bookingImpl");

		Logger logger = LoggerFactory.getLogger(App.class);
		logger.info("Application init successful with xml based configuration");

//		FileReadingMessageSource messageSource = (FileReadingMessageSource) context.getBean("pollableFileSource");
//		messageSource.setDirectory(null);
//		Resource eventResource = context.getResource("${app.prepareddata.file.event}");
//		Resource ticketResource = context.getResource("classpath:tickets.json");
//		Resource userResource = context.getResource("classpath:users.json");
//		FileSystemResource eventResource = (FileSystemResource) context.getBean("eventFileResource");
//		System.out.println(eventResource.getPath() + eventResource.exists());
//		System.out.println(ticketResource.getDescription() + ticketResource.exists());
//		System.out.println(userResource.getDescription() + userResource.exists());
//		boolean successful = eventResource.exists() && ticketResource.exists() && userResource.exists();
//		String not = successful ? "" : " not";
//		logger.info("Resources init" + not + " successful with xml based configuration");
		}
	}
}

package com.epam.liyuan.hong.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.epam.liyuan.hong.repo.ItemRepo;

public class RepoPostProcessor implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		if (beanName == "eventDao") {
			Object obj = BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
			ItemRepo itemRepo = (ItemRepo) obj;
			itemRepo.loadEventsFromResource();
			itemRepo.loadTicketsFromResource();
			itemRepo.loadUsersFromResource();
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
}

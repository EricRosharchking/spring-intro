package com.epam.liyuan.hong.dao;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@PropertySource("classpath:app.properties")
@ContextConfiguration("classpath:configuration.xml")
public class EventDaoTest {

}

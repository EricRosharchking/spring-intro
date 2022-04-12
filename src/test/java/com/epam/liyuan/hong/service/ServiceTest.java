package com.epam.liyuan.hong.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

public abstract class ServiceTest {
	
	@Parameters
	public static List<Object> data() {
		List<Object> data = new ArrayList<>();
		
		return data;
	}

}

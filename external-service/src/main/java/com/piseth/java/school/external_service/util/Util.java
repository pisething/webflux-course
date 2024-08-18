package com.piseth.java.school.external_service.util;

import com.github.javafaker.Faker;

public class Util {
	private static final Faker FAKER = Faker.instance();
	
	public static Faker faker() {
		return FAKER;
	}

}

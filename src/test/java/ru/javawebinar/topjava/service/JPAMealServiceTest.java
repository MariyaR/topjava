package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles ={"jpa, datajpa", "jpa"})
public class JPAMealServiceTest extends AbstractMealServiceTest{
}

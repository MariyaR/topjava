package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles ={"jpa, datajpa", "datajpa"})
public class JPADATAUserServiceTest extends AbstractUserServiceTest {
}

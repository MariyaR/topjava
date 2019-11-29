package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(profiles ={"jdbc", "postgres"})
public class JDBCUserServiceTest extends AbstractUserServiceTest {
}

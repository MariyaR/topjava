package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(profiles ={"jdbc"})
public class JDBCMealServiceTest extends AbstractMealServiceTest {
}

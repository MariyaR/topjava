package ru.javawebinar.topjava;
import org.springframework.test.web.servlet.ResultMatcher;
import java.util.List;
import org.junit.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.user.AdminRestController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.TestUtil.readFromJsonMvcResult;
import static ru.javawebinar.topjava.TestUtil.readListFromJsonMvcResult;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


import java.util.*;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", 2005, Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", 1900, Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        return updated;
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "meals");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "meals").isEqualTo(expected);
    }

    public static class InMemoryAdminRestControllerTest {
        private static ConfigurableApplicationContext appCtx;
        private static AdminRestController controller;

        @BeforeClass
        public static void beforeClass() {
            appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
            controller = appCtx.getBean(AdminRestController.class);
        }

        @AfterClass
        public static void afterClass() {
            appCtx.close();
        }

        @Before
        public void setUp() throws Exception {
            // re-initialize
            InMemoryUserRepository repository = appCtx.getBean(InMemoryUserRepository.class);
            repository.init();
        }

        @Test
        public void delete() throws Exception {
            controller.delete(USER_ID);
            Collection<User> users = controller.getAll();
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(users.iterator().next(), ADMIN);
        }

        @Test(expected = NotFoundException.class)
        public void deleteNotFound() throws Exception {
            controller.delete(10);
        }
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "meals", "password");
}

package hu.unideb.inf.library.model.pojo;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user = new User("tesztElek", "Teszt Elek", "teszt", "Teszt hely", LocalDate.parse("1994-03-03"), true);

    @Test
    void getUserName() {
        assertEquals("tesztElek", user.getUserName());
    }

    @Test
    void setUserName() {
        user.setUserName("tesztElek2");
        assertEquals("tesztElek2", user.getUserName());
    }

    @Test
    void getName() {
        assertEquals("Teszt Elek", user.getName());
    }

    @Test
    void setName() {
        user.setName("Teszt Elek 2");
        assertEquals("Teszt Elek 2", user.getName());
    }

    @Test
    void getPassword() {
        assertEquals("teszt", user.getPassword());
    }

    @Test
    void setPassword() {
        user.setPassword("teszt2");
        assertEquals("teszt2", user.getPassword());
    }

    @Test
    void getAdmin() {
        assertEquals(true, user.getAdmin());
    }

    @Test
    void setAdmin() {
        user.setAdmin(false);
        assertEquals(false, user.getAdmin());
    }

    @Test
    void getBirthPlace() {
        assertEquals("Teszt hely", user.getBirthPlace());
    }

    @Test
    void setBirthPlace() {
        user.setBirthPlace("Teszt hely 2");
        assertEquals("Teszt hely 2", user.getBirthPlace());
    }

    @Test
    void getBirthDate() {
        assertEquals(LocalDate.parse("1994-03-03"), user.getBirthDate());
    }

    @Test
    void setBirthDate() {
        user.setBirthDate(LocalDate.parse("1995-05-05"));
        assertEquals(LocalDate.parse("1995-05-05"), user.getBirthDate());
    }
}
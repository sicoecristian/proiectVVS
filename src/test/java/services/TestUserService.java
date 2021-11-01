package services;

import exceptions.UsernameAlreadyExistsException;
import models.User;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)

public class TestUserService {
    List<User> users=new ArrayList<>();

    private User user=new User("a","a");

    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");

    @Mock
    private UserService userService;

    @Before
    public void init() {
        users.add(new User("user@email.com",UserService.encodePassword("user@email.com","user")));
        users.add(new User("loginTest@email.com",UserService.encodePassword("loginTest@email.com","loginTest")));
        users.add(new User("userAlreadyExists@email.com",UserService.encodePassword("userAlreadyExists@email.com","userAlreadyExists")));
    }

    @Test
    public void checkUserDoesNotAlreadyExistTest() throws UsernameAlreadyExistsException {
        UserService.checkUserDoesNotAlreadyExist("blaa",users);
        //Mockito.verify(userService).checkUserDoesNotAlreadyExist("blaa",users);
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void checkUserDoesAlreadyExistTest() throws UsernameAlreadyExistsException {
       // users.add(new User("user@email.com","user"));
        UserService.addUser("userAlreadyExists@email.com","userExists",users,USERS_PATH);
    }

    @Test
    public void addUserTest() throws UsernameAlreadyExistsException {
        assertEquals("User with e-mail a added succesfully",UserService.addUser("a","a",users,USERS_PATH));
    }

    @Test
    public void checkFalseLoginTest(){
        //users.add(new User("user@email.com","user"));
        assertEquals("false",UserService.logIn("user@email.com","userrr",users));
    }
    @Test
    public void checkTrueLoginTest(){
        users.add(new User("user@email.com","user"));
        assertEquals("true",UserService.logIn("user@email.com","user",users));
    }

    @Test
    public void testLoadUsersFromFile() throws IOException {
        UserService us=new UserService();
        us=mock(UserService.class);
        //Mockito.verify(us, times(1));
        UserService.loadUsersFromFile(USERS_PATH,users);
       // UserService.loadUsersFromFile();
    }

    @Test
    public void persistUsersTest(){
        //UserService us=mock(UserService.class);
        //doNothing().when(us);

       // verify(us, times(1));
        UserService.persistUsers(USERS_PATH,users);
    }
    @Test
    public void testEncodePassword(){
        String expected="\u0012�\u0016�oOR�$\u0006�b�!\u000B\u0003�e^��K��\u0019�y�\u0017Kz\u0002|� �\u000Fr;\u0018�3F'��\u0005�>�\u000F��\u001B'�\u001C�\n��9%";
        assertEquals(expected,UserService.encodePassword("user@email.com","user"));
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}

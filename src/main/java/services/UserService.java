package services;


import com.fasterxml.jackson.core.JsonParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import exceptions.CouldNotWriteUsersException;
import exceptions.UsernameAlreadyExistsException;
import models.User;
import org.apache.commons.io.FileUtils;


import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserService {
    private static List<User> users=new ArrayList<>();
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");

    public UserService() {
    }

    public static void loadUsersFromFile(Path path, List<User> usersList) throws IOException {
        if (!Files.exists(path)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), path.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        usersList = objectMapper.readValue(path.toFile(), new TypeReference<List<User>>() {
        });
        }


    public static void persistUsers(Path path,List<User> usersList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), usersList);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

    public static void checkUserDoesNotAlreadyExist(String username,List<User> usersList) throws UsernameAlreadyExistsException {
        for (User user : usersList) {
            if (Objects.equals(username, user.getEmail()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    public static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static String addUser(String email, String password, List<User> usersList,Path path) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(email,usersList);
        users.add(new User(email, encodePassword(email, password)));
        persistUsers(path, usersList);
        return "User with e-mail "+ email + " added succesfully";

    }

    public static String logIn(String email, String password, List<User> usersList){
        int i = 0;
        int found=0;


            for (User user : usersList) {
                if (Objects.equals(email, user.getEmail())){

                    if (Objects.equals(user.getPassWord(), encodePassword(email, password))){

                        found=1;
                    }
                }
            }

        if(found==1){
            return "true";
        }
        else
            return "false";
    }
}


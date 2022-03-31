package org.example;

import java.util.ArrayList;
import java.util.List;

public class ListUsers {

    private static List<User> users;

    public static void initListUsers() {
        users = new ArrayList<>();
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}

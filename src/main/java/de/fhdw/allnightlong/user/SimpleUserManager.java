package de.fhdw.allnightlong.user;

import java.util.HashMap;

public class SimpleUserManager implements UserManager {
    private HashMap<String, String> users;

    public SimpleUserManager() {
        users = new HashMap<>();
        users.put("user1", "pass1");
        users.put("user2", "pass2");
    }

    @Override
    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}


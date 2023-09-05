package de.fhdw.allnightlong.user;


public interface UserManager {
    boolean authenticate(String username, String password);
}

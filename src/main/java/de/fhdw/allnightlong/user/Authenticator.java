package de.fhdw.allnightlong.user;

public interface Authenticator {
    boolean authenticate(UserManager userManager, String username, String password);
}

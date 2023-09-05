package de.fhdw.allnightlong.user;

public class SimpleAuthenticator implements Authenticator {
    @Override
    public boolean authenticate(UserManager userManager, String username, String password) {
        return userManager.authenticate(username, password);
    }
}

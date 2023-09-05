package de.fhdw.allnightlong.bots.abstractbot;

public abstract class AbstractBot {
    private boolean isEnabled;

    public abstract String handleQuery(String query);

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}

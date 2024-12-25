package io.ruin.model.entity.shared.listeners;

import io.ruin.model.entity.player.Player;

public class LogoutListener {

    public AttemptAction attemptAction;

    public LogoutAction logoutAction;

    public LogoutListener onAttempt(AttemptAction attemptAction) {
        this.attemptAction = attemptAction;
        return this;
    }

    public LogoutListener onAttempt(SimpleAttemptAction attemptAction) {
        this.attemptAction = attemptAction;
        return this;
    }

    public LogoutListener onLogout(LogoutAction logoutAction) {
        this.logoutAction = logoutAction;
        return this;
    }

    public interface AttemptAction {
        boolean allow(Player player);
    }

    public interface SimpleAttemptAction extends AttemptAction {
        default boolean allow(Player player) {
            return allow();
        }
        boolean allow();
    }

    public interface LogoutAction {
        void logout(Player player);
    }

}

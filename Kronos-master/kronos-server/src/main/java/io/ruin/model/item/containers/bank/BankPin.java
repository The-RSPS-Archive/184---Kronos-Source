package io.ruin.model.item.containers.bank;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.TimeUtils;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;

import java.util.function.Consumer;

public class BankPin {

    /**
     * Player
     */

    private Player player;

    public void init(Player player) {
        this.player = player;
    }

    /**
     * Misc settings
     */

    private int confirmationType;

    private int enteredPin = -1;

    private boolean verified;

    /**
     * Pin settings
     */

    @Expose private int pin = -1;

    @Expose private int recoveryDelay = 3;

    @Expose private long recoverAt;

    @Expose private long lockAt = -1;

    @Expose private String lastVerificationIp;

    public void loggedIn() {
        if(pin == -1 || lockAt <= 0)
            return;
        if(!player.getIp().equals(lastVerificationIp) || System.currentTimeMillis() >= lockAt)
            return;
        verified = true;
    }

    public void loggedOut() {
        if(lockAt == -1 || !verified)
            return;
        lockAt = System.currentTimeMillis() + TimeUtils.getMinutesToMillis(5);
        lastVerificationIp = player.getIp();
    }

    /**
     * Open
     */

    public void openSettings() {
        if(requiresVerification(p -> openSettings()))
            return;
        confirmationType = 0;
        enteredPin = -1;
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 14);
        player.getPacketSender().setHidden(14, 0, false);
        player.getPacketSender().setHidden(14, 28, true);
        player.getPacketSender().sendString(14, 6, pin == -1 ? "Not Protected!" : "Bank Protected");
        if(pin == -1) {
            player.getPacketSender().setHidden(14, 18, false);
            player.getPacketSender().setHidden(14, 21, true);
        } else {
            player.getPacketSender().setHidden(14, 18, true);
            player.getPacketSender().setHidden(14, 21, false);
        }
        player.getPacketSender().sendString(14, 8, recoveryDelay + " days");
        player.getPacketSender().sendString(14, 10, lockAt == -1 ? "Always lock" : "Lock after 5 minutes offline");
        player.getPacketSender().sendString(14, 14, "Players are reminded that they should NEVER tell anyone their account PINs or passwords, nor should they ever enter their PINs on any website form.<br><br>PINs are a good form of secondary account protection, and are highly recommended.");
    }

    /**
     * Remove
     */

    public void hardRemove() {
        pin = -1;
        recoverAt = 0;
        player.closeInterfaces();
    }

    /**
     * Recovery
     */

    private void changeRecoveryDelay() {
        if(recoveryDelay == 3)
            recoveryDelay = 7;
        else
            recoveryDelay = 3;
        openSettings();
    }

    private boolean recover() {
        if(recoverAt == 0L)
            return false;
        long currentMs = System.currentTimeMillis();
        if(currentMs >= recoverAt) {
            pin = -1;
            recoverAt = 0L;
            verified = true;
            player.sendMessage("Your PIN has been reset!");
            openSettings();
            return true;
        }
        player.sendMessage("Your bank PIN will be reset in: " + "<col=800000>" + TimeUtils.fromMs(recoverAt - currentMs, true));
        return false;
    }

    /**
     * Logout behaviour
     */

    private void changeLogoutBehaviour() {
        if(lockAt == -1)
            lockAt = 0;
        else
            lockAt = -1;
        openSettings();
    }

    /**
     * Confirmation
     */

    private void sendConfirmation(int type) {
        //1 = setup
        //2 = change
        //3 = delete
        confirmationType = type;
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 14);
        player.getPacketSender().setHidden(14, 0, true);
        player.getPacketSender().setHidden(14, 28, false);
        if(confirmationType == 1) {
            player.getPacketSender().sendString(14, 30, "Do you really wish to set a PIN to protect your bank?");
            player.getPacketSender().sendString(14, 33, "Yes, I really want a PIN. I will never forget it!");
            player.getPacketSender().sendString(14, 36, "No, I might forget it!");
        } else if(confirmationType == 2) {
            player.getPacketSender().sendString(14, 30, "Do you really wish to change your PIN?");
            player.getPacketSender().sendString(14, 33, "Yes, I really want to change my PIN!");
            player.getPacketSender().sendString(14, 36, "No, I want to keep my current PIN!");
        } else {
            player.getPacketSender().sendString(14, 30, "Do you really wish to delete your PIN?");
            player.getPacketSender().sendString(14, 33, "Yes, I really want to delete my PIN!");
            player.getPacketSender().sendString(14, 36, "No, keep my PIN, I want to be safe!");
        }
    }

    private void handleConfirmation(boolean yes) {
        if(!yes) {
            openSettings();
            return;
        }
        if(confirmationType == 1) {
            /* setup */
            setup();
        } else if(confirmationType == 2) {
            /* change */
            setup();
        } else {
            /* delete */
            pin = -1;
            openSettings();
            player.sendMessage("Your PIN has been deleted.");
        }
    }

    /**
     * Setup
     */

    private void setup() {
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 213);
        player.getPacketSender().setHidden(213, 14, true);
        if(enteredPin == -1) {
            player.getPacketSender().sendString(213, 2, "Set new PIN");
            player.getPacketSender().sendString(213, 7, "Please choose a new FOUR DIGIT PIN using the buttons below.");
        } else {
            player.getPacketSender().sendString(213, 2, "Confirm new PIN");
            player.getPacketSender().sendString(213, 7, "Now please enter that number again.");
        }
        player.consumerInt = value -> {
            if(value == 12345) {
                /* exit */
                player.closeInterface(InterfaceType.MAIN);
                return;
            }
            if(enteredPin != -1) {
                if(value == enteredPin) {
                    pin = enteredPin;
                    verified = true;
                    player.sendMessage("Your new PIN has been set, please don't forget it!");
                } else {
                    player.sendMessage("Those numbers did not match, please be sure the PIN you setup is something you can remember!");
                }
                openSettings();
                return;
            }
            enteredPin = value;
            setup();
        };
    }

    /**
     * Verify
     */

    public boolean requiresVerification(Consumer<Player> verifiedAction) {
        if(pin == -1 || verified)
            return false;
        verify(verifiedAction);
        return true;
    }

    private void verify(Consumer<Player> verifiedAction) {
        if(recover()) {
            if(verifiedAction != null)
                verifiedAction.accept(player);
            return;
        }
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, 213);
        player.getPacketSender().setHidden(213, 14, false);
        player.getPacketSender().sendString(213, 2, "Bank of " + World.type.getWorldName() + "");
        player.getPacketSender().sendString(213, 7, "Please enter your PIN using the buttons below.");
        player.consumerInt = value -> {
            if(value == 12345) {
                /* exit */
                player.closeInterface(InterfaceType.MAIN);
                return;
            }
            if(value == 54321) {
                /* forgot */
                if(recoverAt == 0L)
                    recoverAt = System.currentTimeMillis() + TimeUtils.getDaysToMillis(recoveryDelay);

                recover(); //sends message
                player.closeInterface(InterfaceType.MAIN);
                return;

            }
            if(value != pin) {
                player.sendMessage("The PIN you entered is not valid, please try again.");
                verify(verifiedAction);
                return;
            }
            verified = true;
            recoverAt = 0L;
            player.sendMessage("You have successfully verified your PIN!");
            player.closeInterfaces();
            if(verifiedAction != null)
                verifiedAction.accept(player);
        };
    }

    public boolean hasPin() {
        return pin != -1;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    /**
     * Handling
     */

    static {
        InterfaceHandler.register(14, h -> {
            h.actions[19] = (SimpleAction) p -> {
                /* set pin */
                if(p.getBankPin().pin != -1)
                    return;
                p.getBankPin().sendConfirmation(1);
            };
            h.actions[20] = (SimpleAction) p -> {
                /* change recovery delay */
                if(p.getBankPin().pin != -1)
                    return;
                p.getBankPin().changeRecoveryDelay();
            };
            h.actions[33] = (SimpleAction) p -> {
                /* confirmation screen - yes */
                if(p.getBankPin().confirmationType == 0)
                    return;
                p.getBankPin().handleConfirmation(true);
            };
            h.actions[36] = (SimpleAction) p -> {
                /* confirmation screen - no */
                if(p.getBankPin().confirmationType == 0)
                    return;
                p.getBankPin().handleConfirmation(false);
            };
            h.actions[22] = (SimpleAction) p -> {
                /* change pin */
                if(!p.getBankPin().verified)
                    return;
                p.getBankPin().sendConfirmation(2);
            };
            h.actions[23] = (SimpleAction) p -> {
                /* delete pin */
                if(!p.getBankPin().verified)
                    return;
                p.getBankPin().sendConfirmation(3);
            };
            h.actions[24] = (SimpleAction) p -> {
                /* change recovery delay */
                if(!p.getBankPin().verified)
                    return;
                p.getBankPin().changeRecoveryDelay();
            };
            h.actions[25] = (SimpleAction) p -> {
                if(!p.getBankPin().verified)
                    return;
                p.getBankPin().changeLogoutBehaviour();
            };
        });
    }

}

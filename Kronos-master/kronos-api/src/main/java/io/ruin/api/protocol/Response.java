package io.ruin.api.protocol;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

public enum Response {

    SUCCESS(2),
    INVALID_LOGIN(3),
    DISABLED_ACCOUNT(4),
    ALREADY_LOGGED_IN(5),
    GAME_UPDATED(6),
    WORLD_FULL(7),
    LOGIN_SERVER_OFFLINE(8),
    CONNECTION_LIMIT(9),
    BAD_SESSION_ID(10),
    VULNERABLE_PASSWORD(11),
    MEMBERSHIP_REQUIRED(12),
    COULD_NOT_LOGIN(13),
    WORLD_DOWN(13),
    UPDATING(14),
    ERROR(15),
    LOGIN_LIMIT(16),
    UNREGISTERED_ACCOUNT(17),
    ACCOUNT_LOCKED(18),
    CLOSED_BETA(19),
    LOGIN_SERVER_NO_REPLY(23),
    ERROR_LOADING_ACCOUNT(24),
    LOGIN_SERVER_UNEXPECTED_RESPONSE(25),
    COMPUTER_BLOCKED(26),
    CHANGE_DISPLAY_NAME(31),
    EMAIL_VALIDATION(32),
    PRIVATE_ACCESS(55),
    TWO_FACTOR(56),
    TWO_FACTOR_RETRY(57),
    EMAIL_REQUIRED(58),
    EMAIL_IN_USE(59),
    USERNAME_TOO_LONG(60),
    USERNAME_BAD_WORDS(61),
    USERNAME_BAD_LETTERS(62),
    USERNAME_DUPLICATE(63),
    USERNAME_ON_HOLD(64),
    PROXY_LOGIN_ATTEMPT(65),
    IP_BANNED(66),
    USERNAME_IN_USE(67),
    EMAIL_BANNED(68),
    EMAIL_INVALID(69),
    UNEXPECTED(Byte.MAX_VALUE);

    public final int code;

    Response(int code) {
        this.code = code;
    }

    public void send(Channel channel) {
        channel.writeAndFlush(Unpooled.buffer(1).writeByte(code)).addListener(ChannelFutureListener.CLOSE);
    }

    public static Response valueOf(int ordinal) {
        Response[] values = values();

        if(ordinal < 0 || ordinal >= values.length)
            return null;
        return values[ordinal];
    }

    /*
        if(var0 == 4)
            Class46.method675("Your account has been disabled.", "Please check your message-centre for details.", "", 825560900);
        else if(var0 == 5)
            Class46.method675("Your account has not logged out from its last", "session or the server is too busy right now.", "Please try again in a few minutes.", 825560900);
        else if(var0 == 6)
            Class46.method675("Runite has been updated!", "Please reload this page.", "", 825560900);
        else if(var0 == 7)
            Class46.method675("This world is full.", "Please use a different world.", "", 825560900);
        else if(var0 == 8)
            Class46.method675("Unable to connect.", "Login server offline.", "", 825560900);
        else if(var0 == 9)
            Class46.method675("Login limit exceeded.", "Too many connections from your address.", "", 825560900);
        else if(var0 == 10)
            Class46.method675("Unable to connect.", "Bad session id.", "", 825560900);
        else if(var0 == 11)
            Class46.method675("We suspect someone knows your password.", "Press 'change your password' on front page.", "", 825560900);
        else if(var0 == 12)
            Class46.method675("You need a members account to login to this world.", "Please subscribe, or use a different world.", "", 825560900);
        else if(var0 == 13)
            Class46.method675("Could not complete login.", "Please try using a different world.", "", 825560900);
        else if(var0 == 14)
            Class46.method675("The server is being updated.", "Please wait 1 minute and try again.", "", 825560900);
        else if(var0 == 16)
            Class46.method675("Too many login attempts.", "Please wait a few minutes before trying again.", "", 825560900);
        else if(var0 == 17)
            Class46.method675("You are standing in a members-only area.", "To play on this world move to a free area first", "", 825560900);
        else if(var0 == 18)
            Class46.method675("Account locked as we suspect it has been stolen.", "Press 'recover a locked account' on front page.", "", 825560900);
        else if(var0 == 19)
            Class46.method675("This world is running a closed Beta.", "Sorry invited players only.", "Please use a different world.", 825560900);
        else if(var0 == 20)
            Class46.method675("Invalid loginserver requested.", "Please try using a different world.", "", 825560900);
        else if(var0 == 22)
            Class46.method675("Malformed login packet.", "Please try again.", "", 825560900);
        else if(var0 == 23)
            Class46.method675("No reply from loginserver.", "Please wait 1 minute and try again.", "", 825560900);
        else if(var0 == 24)
            Class46.method675("Error loading your profile.", "Please contact customer support.", "", 825560900);
        else if(var0 == 25)
            Class46.method675("Unexpected loginserver response.", "Please try using a different world.", "", 825560900);
        else if(var0 == 26)
            Class46.method675("This computers address has been blocked", "as it was used to break our rules.", "", 825560900);
        else if(var0 == 27)
            Class46.method675("", "Service unavailable.", "", 825560900);
        else if(var0 == 31)
            Class46.method675("Your account must have a displayname set", "in order to play the game.  Please set it", "via the website, or the main game.", 825560900);
        else if(var0 == 32)
            Class46.method675("Your attempt to log into your account was", "unsuccessful.  Don't worry, you can sort", "this out by visiting the billing system.", 825560900);
        else if(var0 == 37)
            Class46.method675("Your account is currently inaccessible.", "Please try again in a few minutes.", "", 825560900);
        else if(var0 == 38)
            Class46.method675("You need to vote to play!", "Visit runescape.com and vote,", "and then come back here!", 825560900);
        else if(var0 == 55)
            Class46.method675("Sorry, but your account is not eligible to", "play this version of the game.  Please try", "playing the main game instead!", 825560900);

     */

}
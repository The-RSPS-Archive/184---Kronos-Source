package io.ruin.central.utility;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author ReverendDread on 8/6/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
public class XenforoUtils {

    private static final String KRONOS_FORUMS = "https://community.kronos.rip/";
    private static final String FIND_USER_REQ = KRONOS_FORUMS + "api/users/find-name?username=";
    private static final String EDIT_USER = KRONOS_FORUMS + "api/users/";
    private static final String AUTH_REQ = KRONOS_FORUMS + "api/auth";
    private static final String AUTH_KEY = "FZOJhAmnipXyr9KavWdKetbzOxXOQeBh";

    public static JSONObject attemptLogin(String user, String pass, String userIp) {
        JSONObject value = login(user, pass, userIp);
        if (value.has("errors")) { //Failed login
            JSONArray errors = value.getJSONArray("errors");
            JSONObject error = errors.getJSONObject(0);
            String code = error.getString("code");
            String message = error.getString("message");
            //JSONArray params = error.getJSONArray("params");
            JSONObject construct = new JSONObject();
            construct.put("success", "false");
            construct.put("login_code", message);
            return construct;
        }
        value.put("login_code", "passed");
        return value;
    }

    public static JSONObject getUser(String username) {
        try {
            OkHttpClient http = new OkHttpClient();

            Request request = new Request.Builder()
            .url(FIND_USER_REQ + username)
            .headers(Headers.of("XF-Api-Key", AUTH_KEY, "XF-Api-User", "1"))
            .build();

            try (Response response = http.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return new JSONObject(response.body().string());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject login(String username, String password, String userIp) {

        try {

            OkHttpClient http = new OkHttpClient();

            HttpUrl httpUrl = HttpUrl.parse(AUTH_REQ).newBuilder()
            .addQueryParameter("login", username)
            .addQueryParameter("password", password)
            .addQueryParameter("limit_ip", userIp)
            .build();

            Request request = new Request.Builder()
            .addHeader("XF-Api-Key", AUTH_KEY)
            .addHeader("XF-Api-User", "1")
            .post(RequestBody.create(MediaType.parse(""), ""))
            .url(httpUrl)
            .build();

            try (Response response = http.newCall(request).execute()) {
                return new JSONObject(response.body().string());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static JSONObject updateUserGroups(int userId, String post) {

        try {

            OkHttpClient http = new OkHttpClient();

            Request request = new Request.Builder()
            .addHeader("XF-Api-Key", AUTH_KEY)
            .addHeader("XF-Api-User", "1")
            .post(RequestBody.create(MediaType.parse("text"), post))
            .url(EDIT_USER + userId + "/")
            .build();

            System.out.println("post : " + request.toString());
            System.out.println("url : " + request.url().toString());

            try (Response response = http.newCall(request).execute()) {
                return new JSONObject(response.body().string());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}

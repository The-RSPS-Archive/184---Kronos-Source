package io.ruin.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;

public class JsonUtils {

    public static final Gson GSON = new GsonBuilder().create();

    public static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();

    public static final Gson GSON_EXPOSE = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static final Gson GSON_EXPOSE_PRETTY = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static final JsonParser JSON_PARSER = new JsonParser();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static String toPrettyJson(Object object) {
        return GSON_PRETTY.toJson(object);
    }

    public static <T> T fromJson(String json, Type rawType, Type... typeArguments) {
        return GSON.fromJson(json, TypeToken.getParameterized(rawType, typeArguments).getType());
    }

    public static void toFile(File file, String json) throws IOException {
        try(BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
            bw.write(json);
        }
    }

    public static String fromFile(File jsonFile) throws IOException {
        try(FileReader reader = new FileReader(jsonFile)) {
            JsonElement element = JSON_PARSER.parse(reader);
            return GSON.toJson(element);
        } catch(Throwable t) {
            ServerWrapper.logError("JSON Failure: " + jsonFile.getAbsolutePath(), t);
            return null;
        }
    }

}
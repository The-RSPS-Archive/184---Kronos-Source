package io.ruin.data.yaml;

import kotlin.io.FileTreeWalk;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class YamlFile<T> {

    public abstract String path();

    public abstract <T extends Object> void forEachFile(T yamlObject);

    public abstract Class<?> dataClass();

    public Predicate<Path> additionalFileFiler(){
        return path -> true;
    }

    public boolean includeSubDirectories(){
        return false;
    }



}

package io.ruin.data.yaml;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Lists;
import io.ruin.Server;
import io.ruin.data.yaml.impl.ShopLoader;
import kilim.agent.KilimAgent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
public class YamlLoader {


    public static final ObjectMapper YAML_OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    public static void initYamlFiles() {
        List<YamlFile<?>> yamlFiles = Lists.newArrayList();

        yamlFiles.add(new ShopLoader());

        load(yamlFiles);
    }

    public static void load(List<YamlFile<?>> yamlFiles){
        yamlFiles.forEach(yamlClass -> {
            try {
                Files
                .walk(Paths.get(Server.dataFolder.getAbsolutePath(), yamlClass.path()), yamlClass.includeSubDirectories() ? Integer.MAX_VALUE : 1)
                .filter(path -> yamlClass.additionalFileFiler().test(path))
                .map(Path::toFile)
                .filter(path -> path.getName().endsWith(".yaml"))
                .map(yamlFile -> {
                    try(FileReader fr = new FileReader(yamlFile)){
                        return YAML_OBJECT_MAPPER.readValue(fr, yamlClass.dataClass());
                    } catch (Exception ex){
                        log.error("", ex);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(yamlClass::forEachFile);
            } catch (Exception ex){
                log.info("Error while loading yaml file {} | {}", yamlClass.getClass().getName(), yamlClass);
                log.error("", ex);
            }
        });
    }

}

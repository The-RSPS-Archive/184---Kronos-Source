package io.ruin.data.yaml.impl;

import io.ruin.data.yaml.YamlFile;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.shop.Shop;
import io.ruin.model.shop.ShopItem;
import io.ruin.model.shop.ShopManager;
import kilim.agent.KilimAgent;
import kilim.tools.Kilim;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.util.List;

@Slf4j
@Accessors(fluent = true)
public class ShopLoader extends YamlFile<Shop> {

    public ShopLoader(){

    }
    @Override
    public String path() {
        return "shops/";
    }

    @Override
    public void forEachFile(Object object) {
        ShopManager.registerShop((Shop) dataClass().cast( object));
    }

    @Override
    public Class dataClass() {
        return Shop.class;
    }

}

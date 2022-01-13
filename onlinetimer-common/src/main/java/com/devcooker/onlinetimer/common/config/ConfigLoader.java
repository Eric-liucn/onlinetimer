package com.devcooker.onlinetimer.common.config;

import io.leangen.geantyref.TypeToken;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {

    private OConfig config;
    private CommentedConfigurationNode node;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;

    public ConfigLoader(Path configDir) throws IOException {
        if (!Files.exists(configDir)){
            Files.createDirectory(configDir);
        }
        Path configFile = configDir.resolve("onlinetimer.conf");
        loader = HoconConfigurationLoader.builder()
                .path(configFile)
                .defaultOptions(ConfigurationOptions.defaults().shouldCopyDefaults(true))
                .build();
        this.load();
    }

    public void load() throws ConfigurateException {
        node = loader.load();
        config = node.get(TypeToken.get(OConfig.class), new OConfig());
    }

    public void save() throws ConfigurateException {
        this.loader.save(node);
    }

    public OConfig getConfig() {
        return config;
    }
}

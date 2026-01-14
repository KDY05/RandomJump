package com.github.kdy05.randomjump.logic;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Manages plugin configuration.
 */
public class ConfigManager {
    private final JavaPlugin plugin;
    private final FileConfiguration config;
    private final Logger logger;

    /**
     * Creates a new ConfigManager.
     *
     * @param plugin The plugin instance
     */
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null");
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
    }

    /**
     * Initializes the configuration file with default values.
     */
    public void initConfig() {
        if (!plugin.getDataFolder().exists()) {
            if (plugin.getDataFolder().mkdirs()) {
                logger.info("데이터 폴더가 정상적으로 생성되었습니다.");
            } else {
                logger.warning("데이터 폴더를 생성하지 못했습니다.");
            }
        }
        plugin.saveDefaultConfig();
    }

    /**
     * Gets whether random jump is enabled.
     *
     * @return true if random jump is enabled
     */
    public boolean isRandomJumpEnabled() {
        return config.getBoolean("enabled", false);
    }

    /**
     * Sets whether random jump is enabled.
     *
     * @param enabled true to enable, false to disable
     */
    public void setRandomJumpEnabled(boolean enabled) {
        config.set("enabled", enabled);
        plugin.saveConfig();
    }

    /**
     * Gets the configuration object.
     *
     * @return The FileConfiguration object
     */
    public FileConfiguration getConfig() {
        return config;
    }
}

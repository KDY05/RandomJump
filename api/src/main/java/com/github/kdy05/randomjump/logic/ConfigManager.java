package com.github.kdy05.randomjump.logic;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Manages plugin configuration.
 */
public class ConfigManager {
    private final JavaPlugin plugin;
    private final Logger logger;
    private FileConfiguration config;
    private List<JumpTier> jumpTiers;

    /**
     * Represents a jump strength tier with chance and value range.
     */
    public record JumpTier(int chance, double min, double max) {}

    /**
     * Creates a new ConfigManager and initializes the configuration.
     *
     * @param plugin The plugin instance
     */
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null");
        this.logger = plugin.getLogger();

        if (!plugin.getDataFolder().exists()) {
            if (plugin.getDataFolder().mkdirs()) {
                logger.info("데이터 폴더가 정상적으로 생성되었습니다.");
            } else {
                logger.warning("데이터 폴더를 생성하지 못했습니다.");
            }
        }
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        loadJumpTiers();
    }

    /**
     * Loads jump tiers from configuration.
     */
    private void loadJumpTiers() {
        jumpTiers = new ArrayList<>();

        ConfigurationSection tiersSection = config.getConfigurationSection("tiers");
        if (tiersSection != null) {
            for (String key : tiersSection.getKeys(false)) {
                ConfigurationSection tier = tiersSection.getConfigurationSection(key);
                if (tier != null) {
                    int chance = tier.getInt("chance", 0);
                    double min = tier.getDouble("min", 0.0);
                    double max = tier.getDouble("max", min);

                    if (chance > 0) {
                        jumpTiers.add(new JumpTier(chance, min, max));
                    }
                }
            }
        }

        // 설정이 없거나 비어있으면 기본값 사용
        if (jumpTiers.isEmpty()) {
            logger.info("기본 점프 티어 설정을 사용합니다.");
            jumpTiers.add(new JumpTier(60, 0.2, 0.6));
            jumpTiers.add(new JumpTier(25, 0.6, 1.4));
            jumpTiers.add(new JumpTier(10, 1.4, 2.6));
            jumpTiers.add(new JumpTier(5, 8.0, 8.0));
        }

        logger.info("점프 티어 " + jumpTiers.size() + "개 로드됨");
    }

    /**
     * Gets the list of jump tiers.
     *
     * @return List of JumpTier objects
     */
    public List<JumpTier> getJumpTiers() {
        return jumpTiers;
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
     * Reloads the configuration from file.
     */
    public void reloadConfig() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        loadJumpTiers();
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

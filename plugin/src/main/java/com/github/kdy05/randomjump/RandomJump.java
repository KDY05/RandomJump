package com.github.kdy05.randomjump;

import com.github.kdy05.randomjump.api.AttributeAdapter;
import com.github.kdy05.randomjump.command.PluginCommand;
import com.github.kdy05.randomjump.listener.PlayerListener;
import com.github.kdy05.randomjump.logic.ConfigManager;
import com.github.kdy05.randomjump.logic.JumpStrManager;
import com.github.kdy05.randomjump.util.ServerVersionDetector;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main plugin class for RandomJump.
 * Manages jump strength randomization across multiple Minecraft versions.
 */
public final class RandomJump extends JavaPlugin {
    private static RandomJump plugin;
    private AttributeAdapter adapter;
    private JumpStrManager jumpStrManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        plugin = this;

        // Initialize adapter based on server version
        if (!initializeAdapter()) {
            getLogger().severe("지원되지 않는 서버 버전입니다. 플러그인을 비활성화합니다.");
            getLogger().severe("지원 버전: 1.20.6+");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize managers
        configManager = new ConfigManager(this);
        jumpStrManager = new JumpStrManager(adapter, configManager);

        // Register listeners and commands
        getServer().getPluginManager().registerEvents(
                new PlayerListener(this, jumpStrManager, configManager), this);
        Objects.requireNonNull(getCommand("randjump"))
                .setExecutor(new PluginCommand(jumpStrManager, configManager));

        getLogger().info("RandomJump 플러그인이 활성화되었습니다. (버전: " + adapter.getSupportedVersion() + ")");
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        getLogger().info("RandomJump 플러그인이 비활성화되었습니다.");
    }

    /**
     * Initializes the version-specific adapter.
     *
     * @return true if adapter was successfully loaded, false otherwise
     */
    private boolean initializeAdapter() {
        String version = ServerVersionDetector.detectVersion();
        getLogger().info("감지된 서버 버전: " + version);

        // Try loading version-specific adapter
        if (ServerVersionDetector.isBetween("1.20.6", "1.21.1")) {
            adapter = loadAdapter("com.github.kdy05.randomjump.v1_20_6.AttributeAdapter_1_20_6");
        } else if (ServerVersionDetector.isAtLeast("1.21.2")) {
            adapter = loadAdapter("com.github.kdy05.randomjump.v1_21_2.AttributeAdapter_1_21_2");
        }

        return adapter != null;
    }

    /**
     * Loads an adapter class by name.
     *
     * @param className The fully qualified class name
     * @return The adapter instance, or null if loading failed
     */
    private AttributeAdapter loadAdapter(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            AttributeAdapter loadedAdapter = (AttributeAdapter) clazz.getDeclaredConstructor().newInstance();
            getLogger().info("어댑터 로딩 성공: " + loadedAdapter.getSupportedVersion());
            return loadedAdapter;
        } catch (Exception e) {
            getLogger().warning("어댑터 로딩 실패: " + className + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the plugin instance.
     *
     * @return The plugin instance
     */
    public static RandomJump getPlugin() {
        return plugin;
    }

    /**
     * Gets the jump strength manager.
     *
     * @return The jump strength manager
     */
    public JumpStrManager getJumpStrManager() {
        return jumpStrManager;
    }

    /**
     * Gets the configuration manager.
     *
     * @return The configuration manager
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
}

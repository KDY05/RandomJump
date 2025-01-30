package com.github.kdy05.randomJump;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RandomJump extends JavaPlugin {
    private static RandomJump plugin;
    FileConfiguration config = this.getConfig();

    private void initConfig() {
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                getLogger().info("데이터 폴더가 정상적으로 생성되었습니다.");
            } else {
                getLogger().warning("데이터 폴더를 생성하지 못했습니다.");
            }
        }
        config.addDefault("Random-jump", false);
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onEnable() {
        plugin = this;

        initConfig();
        getServer().getPluginManager().registerEvents(new AfterPlayerJump(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("randjump"))
                .setExecutor(new TogglePlugin());

        getLogger().info("Enabling plugin completed.");
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        getLogger().info("Disabling plugin completed.");
    }

    public static RandomJump getPlugin() {
        return plugin;
    }
}
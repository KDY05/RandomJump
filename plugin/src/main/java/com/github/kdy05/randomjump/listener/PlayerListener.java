package com.github.kdy05.randomjump.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.github.kdy05.randomjump.logic.ConfigManager;
import com.github.kdy05.randomjump.logic.JumpStrManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Listener for player jump events.
 * Randomizes jump strength when random jump is enabled.
 */
public class PlayerListener implements Listener {
    private final JavaPlugin plugin;
    private final JumpStrManager jumpStrManager;
    private final ConfigManager configManager;

    /**
     * Creates a new AfterPlayerJump listener.
     *
     * @param plugin The plugin instance
     * @param jumpStrManager The jump strength manager
     * @param configManager The configuration manager
     */
    public PlayerListener(JavaPlugin plugin, JumpStrManager jumpStrManager, ConfigManager configManager) {
        this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null");
        this.jumpStrManager = Objects.requireNonNull(jumpStrManager, "JumpStrManager cannot be null");
        this.configManager = Objects.requireNonNull(configManager, "ConfigManager cannot be null");
    }

    @EventHandler
    public void afterPlayerJump(PlayerJumpEvent e) {
        if (!configManager.isRandomJumpEnabled()) {
            return;
        }
        Player player = e.getPlayer();
        // Run after 1 tick to ensure the jump attribute is properly applied
        plugin.getServer().getScheduler().runTaskLater(plugin, () ->
                jumpStrManager.randomize(player), 1);
    }
}

package com.github.kdy05.randomJump;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AfterPlayerJump implements Listener {
    private static final RandomJump PLUGIN = RandomJump.getPlugin();
    private static final FileConfiguration CONFIG = PLUGIN.getConfig();

    @EventHandler
    public void afterPlayerJump(PlayerJumpEvent e) {
        if (!CONFIG.getBoolean("Random-jump")) {
            return;
        }
        Player player = e.getPlayer();
        Bukkit.getScheduler().runTaskLater(PLUGIN, () ->
               JumpStrManager.randomize(player), 1);
    }
}

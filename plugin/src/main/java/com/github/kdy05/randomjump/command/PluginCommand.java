package com.github.kdy05.randomjump.command;

import com.github.kdy05.randomjump.logic.ConfigManager;
import com.github.kdy05.randomjump.logic.JumpStrManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Command executor for /randjump command.
 * Allows toggling random jump on and off.
 */
public class PluginCommand implements CommandExecutor, TabCompleter {
    private final JumpStrManager jumpStrManager;
    private final ConfigManager configManager;

    /**
     * Creates a new TogglePlugin command executor.
     *
     * @param jumpStrManager The jump strength manager
     * @param configManager The configuration manager
     */
    public PluginCommand(JumpStrManager jumpStrManager, ConfigManager configManager) {
        this.jumpStrManager = Objects.requireNonNull(jumpStrManager, "JumpStrManager cannot be null");
        this.configManager = Objects.requireNonNull(configManager, "ConfigManager cannot be null");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length != 1) {
            commandSender.sendMessage(Component.text("/randjump on|off", NamedTextColor.YELLOW));
            return false;
        }

        switch (strings[0]) {
            case "on" -> {
                configManager.setRandomJumpEnabled(true);
                commandSender.sendMessage(Component.text("랜덤 점프를 활성화합니다.", NamedTextColor.DARK_GREEN));
                return true;
            }
            case "off" -> {
                configManager.setRandomJumpEnabled(false);
                jumpStrManager.reset();
                commandSender.sendMessage(Component.text("랜덤 점프를 비활성화합니다.", NamedTextColor.RED));
                return true;
            }
            default -> {
                commandSender.sendMessage(Component.text("/randjump on|off", NamedTextColor.YELLOW));
                return false;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                                 @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> completions = new ArrayList<>();
        if (strings.length == 1) {
            completions.add("on");
            completions.add("off");
        }
        return completions;
    }
}

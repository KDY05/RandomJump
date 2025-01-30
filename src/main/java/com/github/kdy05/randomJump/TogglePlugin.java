package com.github.kdy05.randomJump;

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

public class TogglePlugin implements CommandExecutor, TabCompleter {
    private final RandomJump plugin = RandomJump.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length != 1) {
            commandSender.sendMessage(Component.text("/randjump on|off", NamedTextColor.YELLOW));
            return false;
        }

        switch (strings[0]) {
            case "on" -> {
                plugin.getConfig().set("Random-jump", true);
                plugin.saveConfig();
                commandSender.sendMessage(Component.text("랜덤 점프를 활성화합니다.", NamedTextColor.DARK_GREEN));
                return false;
            }
            case "off" -> {
                plugin.getConfig().set("Random-jump", false);
                plugin.saveConfig();
                JumpStrManager.reset();
                commandSender.sendMessage(Component.text("랜덤 점프를 비활성화합니다.", NamedTextColor.RED));
                return false;
            }
            default -> commandSender.sendMessage(Component.text("/randjump on|off", NamedTextColor.YELLOW));
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        List<String> completions = new ArrayList<>();
        if (strings.length == 1) {
            completions.add("on");
            completions.add("off");
        }
        return completions;
    }
}

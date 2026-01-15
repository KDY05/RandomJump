package com.github.kdy05.randomjump.logic;

import com.github.kdy05.randomjump.api.AttributeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Manages jump strength randomization logic.
 * Version-agnostic - uses AttributeAdapter for version-specific operations.
 */
public class JumpStrManager {
    private static final double BASE_VALUE = 0.41999998688697815;
    private static final Random RANDOM = new Random();

    private final AttributeAdapter adapter;
    private final ConfigManager configManager;

    /**
     * Creates a new JumpStrManager with the specified adapter and config manager.
     *
     * @param adapter The version-specific attribute adapter
     * @param configManager The configuration manager
     * @throws NullPointerException if any parameter is null
     */
    public JumpStrManager(AttributeAdapter adapter, ConfigManager configManager) {
        this.adapter = Objects.requireNonNull(adapter, "AttributeAdapter cannot be null");
        this.configManager = Objects.requireNonNull(configManager, "ConfigManager cannot be null");
    }

    /**
     * Randomizes the jump strength for a player.
     *
     * @param player The player to randomize jump strength for
     */
    public void randomize(Player player) {
        Objects.requireNonNull(player.getAttribute(adapter.getJumpStrengthAttribute()))
                .setBaseValue(getRandomValue());
    }

    /**
     * Resets all online players' jump strength to the default value.
     */
    public void reset() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Objects.requireNonNull(player.getAttribute(adapter.getJumpStrengthAttribute()))
                    .setBaseValue(BASE_VALUE);
        }
    }

    /**
     * Gets a random jump value based on configured tiers.
     *
     * @return Random jump strength value
     */
    private double getRandomValue() {
        List<ConfigManager.JumpTier> tiers = configManager.getJumpTiers();
        int rand = RANDOM.nextInt(100) + 1; // 1~100

        int cumulative = 0;
        for (ConfigManager.JumpTier tier : tiers) {
            cumulative += tier.chance();
            if (rand <= cumulative) {
                return tier.min() + (tier.max() - tier.min()) * RANDOM.nextDouble();
            }
        }

        // 기본값 (티어 설정이 100%를 채우지 않을 경우)
        return BASE_VALUE;
    }
}

package com.github.kdy05.randomjump.logic;

import com.github.kdy05.randomjump.api.AttributeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Random;

/**
 * Manages jump strength randomization logic.
 * Version-agnostic - uses AttributeAdapter for version-specific operations.
 */
public class JumpStrManager {
    private static final double BASE_VALUE = 0.41999998688697815;
    private final AttributeAdapter adapter;

    /**
     * Creates a new JumpStrManager with the specified adapter.
     *
     * @param adapter The version-specific attribute adapter
     * @throws NullPointerException if adapter is null
     */
    public JumpStrManager(AttributeAdapter adapter) {
        this.adapter = Objects.requireNonNull(adapter, "AttributeAdapter cannot be null");
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

    private static double getRandomValue() {
        Random random = new Random();
        int rand = random.nextInt(100) + 1; // 1~100 사이의 난수 생성
        double value;

        // 60%로 확률로 강화 0단계
        if (rand > 40) {
            value = 0.2 + (0.6 - 0.2) * random.nextDouble(); // 0.2 ~ 0.6
        }
        // 25% 확률로 강화 1단계
        else if (rand > 15) {
            value = 0.6 + (1.4 - 0.6) * random.nextDouble(); // 0.6 ~ 1.4
        }
        // 10% 확률로 강화 2단계
        else if (rand > 5) {
            value = 1.4 + (2.6 - 1.4) * random.nextDouble(); // 1.4 ~ 2.6
        }
        // 5% 확률로 강화 3단계
        else {
            value = 8.0;
        }

        return value;
    }
}

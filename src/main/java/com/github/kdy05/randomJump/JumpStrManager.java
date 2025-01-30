package com.github.kdy05.randomJump;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Random;

public class JumpStrManager {
    public static final double BASE_VALUE = 0.41999998688697815;

    public static void randomize(Player player) {
        Objects.requireNonNull(player.getAttribute(AttributeUtil.getJumpStrength()))
                .setBaseValue(getRandValue());
    }

    public static void reset() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Objects.requireNonNull(player.getAttribute(AttributeUtil.getJumpStrength()))
                    .setBaseValue(BASE_VALUE);
        }
    }

    private static double getRandValue() {
        Random random = new Random();
        int rand = random.nextInt(100) + 1; // 1~100 사이의 난수 생성
        double value;

        // 60%로 확률로 강화 0단계
        if (rand > 40) {
            value = 0.25 + (0.7 - 0.25) * random.nextDouble(); // 0.25 ~ 0.7
        }
        // 25% 확률로 강화 1단계
        else if (rand > 15) {
            value = 0.7 + (1.4 - 0.7) * random.nextDouble(); // 0.7 ~ 1.4
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

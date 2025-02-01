package com.github.kdy05.randomJump;

import org.bukkit.attribute.Attribute;
import java.lang.reflect.Field;

public class AttributeUtil {
    private static Attribute JUMP_STRENGTH;

    static {
        try {
            Field field = Attribute.class.getField("JUMP_STRENGTH");
            JUMP_STRENGTH = (Attribute) field.get(null);
            RandomJump.getPlugin().getLogger().info("JUMP_STRENGTH attribute가 감지되었습니다.");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field field = Attribute.class.getField("GENERIC_JUMP_STRENGTH");
                JUMP_STRENGTH = (Attribute) field.get(null);
                RandomJump.getPlugin().getLogger().info("GENERIC_JUMP_STRENGTH attribute가 감지되었습니다.");
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                RandomJump.getPlugin().getLogger().warning("해당 버전에 맞는 attribute를 발견할 수 없습니다. 지원 버전을 확인해주세요.");
            }
        }
    }

    public static Attribute getJumpStrength() {
        return JUMP_STRENGTH;
    }
}


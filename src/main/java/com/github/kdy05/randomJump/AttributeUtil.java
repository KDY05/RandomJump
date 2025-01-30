package com.github.kdy05.randomJump;

import org.bukkit.attribute.Attribute;
import java.lang.reflect.Field;
import java.util.logging.Logger;

public class AttributeUtil {
    private static Attribute JUMP_STRENGTH;

    static {
        try {
            Field field = Attribute.class.getField("JUMP_STRENGTH");
            JUMP_STRENGTH = (Attribute) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field field = Attribute.class.getField("GENERIC_JUMP_STRENGTH");
                JUMP_STRENGTH = (Attribute) field.get(null);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                Logger.getLogger("Minecraft").warning("JUMP_STRENGTH attribute not found!");
            }
        }
    }

    public static Attribute getJumpStrength() {
        return JUMP_STRENGTH;
    }
}


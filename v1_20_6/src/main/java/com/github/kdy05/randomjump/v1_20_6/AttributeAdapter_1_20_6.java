package com.github.kdy05.randomjump.v1_20_6;

import com.github.kdy05.randomjump.api.AttributeAdapter;
import com.github.kdy05.randomjump.util.ServerVersionDetector;
import org.bukkit.attribute.Attribute;

/**
 * AttributeAdapter implementation for Minecraft 1.20.6-1.21.1.
 * Uses GENERIC_JUMP_STRENGTH attribute.
 */
public class AttributeAdapter_1_20_6 implements AttributeAdapter {

    @Override
    public Attribute getJumpStrengthAttribute() {
        return Attribute.GENERIC_JUMP_STRENGTH; // 1.20.6-1.21.1 attribute name
    }

    @Override
    public String getSupportedVersion() {
        return "1.20.6-1.21.1";
    }

    @Override
    public boolean isCompatible(String serverVersion) {
        // Supports 1.20.x versions
        return ServerVersionDetector.isBetween("1.20.6", "1.21.1");
    }
}

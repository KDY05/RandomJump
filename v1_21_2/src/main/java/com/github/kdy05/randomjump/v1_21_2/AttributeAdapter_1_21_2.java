package com.github.kdy05.randomjump.v1_21_2;

import com.github.kdy05.randomjump.api.AttributeAdapter;
import com.github.kdy05.randomjump.util.ServerVersionDetector;
import org.bukkit.attribute.Attribute;

/**
 * AttributeAdapter implementation for Minecraft 1.21.2+.
 * Uses JUMP_STRENGTH attribute.
 */
public class AttributeAdapter_1_21_2 implements AttributeAdapter {

    @Override
    public Attribute getJumpStrengthAttribute() {
        return Attribute.JUMP_STRENGTH; // 1.21.2+ attribute name
    }

    @Override
    public String getSupportedVersion() {
        return "1.21.2+";
    }

    @Override
    public boolean isCompatible(String serverVersion) {
        // Supports 1.21.2+ versions
        return ServerVersionDetector.isAtLeast("1.21.2");
    }
}

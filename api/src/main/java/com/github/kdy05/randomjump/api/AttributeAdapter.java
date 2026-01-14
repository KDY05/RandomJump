package com.github.kdy05.randomjump.api;

import org.bukkit.attribute.Attribute;

/**
 * Version-specific attribute adapter interface.
 * Implementations provide access to the correct jump strength attribute
 * for their respective Minecraft version.
 */
public interface AttributeAdapter {

    /**
     * Gets the jump strength attribute for this version.
     *
     * @return The Attribute enum constant for jump strength
     *         - 1.20.6+: GENERIC_JUMP_STRENGTH
     *         - 1.21.2+:  JUMP_STRENGTH
     */
    Attribute getJumpStrengthAttribute();

    /**
     * Gets the supported version string.
     *
     * @return Version identifier (e.g., "1.20.6", "1.21")
     */
    String getSupportedVersion();

    /**
     * Checks if this adapter supports the current server version.
     *
     * @param serverVersion The server's version string
     * @return true if compatible, false otherwise
     */
    boolean isCompatible(String serverVersion);
}

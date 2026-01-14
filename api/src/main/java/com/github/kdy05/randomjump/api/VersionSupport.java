package com.github.kdy05.randomjump.api;

/**
 * Metadata about version support and compatibility.
 */
public interface VersionSupport {

    /**
     * Gets the minimum supported Minecraft version.
     */
    String getMinVersion();

    /**
     * Gets the maximum supported Minecraft version.
     */
    String getMaxVersion();

    /**
     * Gets the adapter class name for runtime loading.
     */
    String getAdapterClassName();
}

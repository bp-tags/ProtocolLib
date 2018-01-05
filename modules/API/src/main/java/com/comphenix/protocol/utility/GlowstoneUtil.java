package com.comphenix.protocol.utility;

public class GlowstoneUtil {

    private static Boolean glowstone = null;

    public static boolean isGlowstoneServer() {
        if (glowstone != null) {
            return glowstone;
        }
        try {
            Class.forName("net.glowstone.GlowServer");
            glowstone = true;
        } catch (ClassNotFoundException ex) {
            glowstone = false;
        }
        return glowstone;
    }
}

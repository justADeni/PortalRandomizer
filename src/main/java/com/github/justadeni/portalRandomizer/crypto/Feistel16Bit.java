package com.github.justadeni.portalRandomizer.crypto;

import com.github.justadeni.portalRandomizer.PortalRandomizer;

public class Feistel16Bit {
    // Example 8-bit round keys
    private static final int[] KEYS = PortalRandomizer.getInstance().getConfig().getIntegerList("keys")
            .stream().mapToInt(i->i).toArray();

    public static int feistelEncrypt(int input) {
        int x = input ^ 0x8000; // flip sign bit: signed -> unsigned
        int left = (x >>> 8) & 0xFF;
        int right = x & 0xFF;

        for (int i = 0; i < KEYS.length; i++) {
            int f = roundFunction(right, KEYS[i]);
            int newLeft = right;
            int newRight = left ^ f;
            left = newLeft;
            right = newRight;
        }

        int combined = ((left << 8) | right) ^ 0x8000;
        return toSigned16(combined);
    }

    public static int feistelDecrypt(int input) {
        int x = input ^ 0x8000;
        int left = (x >>> 8) & 0xFF;
        int right = x & 0xFF;

        for (int i = KEYS.length - 1; i >= 0; i--) {
            int f = roundFunction(left, KEYS[i]);
            int newRight = left;
            int newLeft = right ^ f;
            right = newRight;
            left = newLeft;
        }

        int combined = ((left << 8) | right) ^ 0x8000;
        return toSigned16(combined);
    }

    private static int roundFunction(int value, int key) {
        return ((value * 0x9E + key) & 0xFF); // 8-bit round output
    }

    private static int toSigned16(int value) {
        value &= 0xFFFF;
        if ((value & 0x8000) != 0) {
            value |= 0xFFFF0000; // sign-extend
        }
        return value;
    }

}

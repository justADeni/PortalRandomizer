package com.github.justadeni.portalRandomizer.crypto;

public class Feistel8Bit {
    // Example 4-bit round keys
    private static final int[] KEYS = {0xD, 0x7, 0xE}; // values 0–15

    public static int feistelEncrypt(int input) {
        int left = (input >>> 4) & 0xF;  // upper 4 bits
        int right = input & 0xF;         // lower 4 bits

        for (int i = 0; i < KEYS.length; i++) {
            int f = roundFunction(right, KEYS[i]);
            int newLeft = right;
            int newRight = left ^ f;
            left = newLeft;
            right = newRight;
        }

        return ((left << 4) | right) & 0xFF;
    }

    public static int feistelDecrypt(int input) {
        int left = (input >>> 4) & 0xF;
        int right = input & 0xF;

        for (int i = KEYS.length - 1; i >= 0; i--) {
            int f = roundFunction(left, KEYS[i]);
            int newRight = left;
            int newLeft = right ^ f;
            right = newRight;
            left = newLeft;
        }

        return ((left << 4) | right) & 0xFF;
    }

    private static int roundFunction(int value, int key) {
        return ((value * 3 + key) & 0xF); // simple 4-bit function
    }

}
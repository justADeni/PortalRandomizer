package com.github.justadeni.portalRandomizer.crypto;

public class Feistel16Bit {

    private final int[] KEYS;

    public Feistel16Bit(int[] keys) {
        KEYS = keys;
    }

    public int encrypt(int input) {
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

    public int decrypt(int input) {
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

    private int roundFunction(int value, int key) {
        return ((value * 0x9E + key) & 0xFF); // 8-bit round output
    }

    private int toSigned16(int value) {
        value &= 0xFFFF;
        if ((value & 0x8000) != 0) {
            value |= 0xFFFF0000; // sign-extend
        }
        return value;
    }

}

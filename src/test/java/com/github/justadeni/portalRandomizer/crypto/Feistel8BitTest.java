package com.github.justadeni.portalRandomizer.crypto;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Feistel8BitTest {

    private static final Feistel8Bit feistel8Bit = new Feistel8Bit();

    private static final int[] output = new int[256];

    @Test
    @Order(0)
    void encrypt() {
        // encrypt all 8-bit numbers
        for (int i = 0; i < 256; i++) {
            output[i] = feistel8Bit.encrypt(i);
            //System.out.println("Encrypted " + i + " -> " + output[i]);
        }

        // check if each is represented in the array exactly once
        long[] bits = new long[4]; // 4 * 64 = 256 bits, think of it as array of booleans

        for (int num : output) {
            assertFalse(num < 0 || num > 255);

            int index = num / 64;
            int bit = num % 64;

            assertFalse((bits[index] & (1L << bit)) != 0);

            bits[index] |= (1L << bit);
        }

        assertTrue(true);
    }

    @Test
    @Order(1)
    void decrypt() {
        // Ensure that each number is successfully decrypted
        for (int i = 0; i < 256; i++) {
            int decrypted = feistel8Bit.decrypt(output[i]);
            assertEquals(decrypted, i);
            //System.out.println("Decrypted " + output[i] + " -> " + decrypted);
        }
    }
}
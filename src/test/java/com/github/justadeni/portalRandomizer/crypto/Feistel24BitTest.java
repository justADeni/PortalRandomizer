package com.github.justadeni.portalRandomizer.crypto;

import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Feistel24BitTest {

    private static final Feistel24Bit feistel24Bit = new Feistel24Bit(new int[]{10,107,189});

    private static int[] input;
    private static int[] output;

    @BeforeAll
    static void generateInput() {
        // Ensure no collisions
        Set<Integer> uniqueInput = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            uniqueInput.add(random.nextInt(16777216) - 8388608);
        }
        input = uniqueInput.stream().mapToInt(i -> i).toArray();
        output = new int[input.length];
    }

    @Test
    @Order(0)
    void encrypt() {
        for (int i = 0; i < input.length; i++) {
            output[i] = feistel24Bit.encrypt(input[i]);
        }

        // Ensure no collisions
        Set<Integer> seen = new HashSet<>();
        for (int num : output) {
            assertTrue(seen.add(num), "Duplicate number found: " + num);
        }
    }

    @Test
    @Order(1)
    void decrypt() {
// Ensure that each number is successfully decrypted
        for (int i = 0; i < output.length; i++) {
            assertEquals(input[i], feistel24Bit.decrypt(output[i]));
        }
    }
}
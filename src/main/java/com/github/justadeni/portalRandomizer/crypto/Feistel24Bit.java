package com.github.justadeni.portalRandomizer.crypto;

// This class combines 16-bit Feistel for MSB and 8-Bit Feistel for LSB
public class Feistel24Bit {

    private final Feistel8Bit feistel8Bit = new Feistel8Bit();
    private final Feistel16Bit feistel16Bit;

    public Feistel24Bit(int[] keys) {
        feistel16Bit = new Feistel16Bit(keys);
    }

    public int encrypt(int input) {
        int input16 = (input >> 8) & 0xFFFF;   // middle 16 bits
        int input8 = input & 0xFF;             // lowest 8 bits

        // encryption
        int encrypt16 = feistel16Bit.encrypt(input16);
        int encrypt8 = feistel8Bit.encrypt(input8);

        // putting it back together
        int encrypted = (encrypt16 << 8) | (encrypt8 & 0xFF);
        if ((encrypted & 0x800000) != 0) {
            encrypted |= 0xFF000000;
        } else {
            encrypted &= 0x00FFFFFF;
        }
        return encrypted;
    }

    public int decrypt(int encrypted) {
        int output16 = (encrypted >> 8) & 0xFFFF;
        int output8 = encrypted & 0xFF;

        // decryption
        int decrypt16 = feistel16Bit.decrypt(output16);
        int decrypt8 = feistel8Bit.decrypt(output8);

        // putting it back together
        int decrypt = (decrypt16 << 8) | (decrypt8 & 0xFF);
        if ((decrypt & 0x800000) != 0) {
            decrypt |= 0xFF000000;
        } else {
            decrypt &= 0x00FFFFFF;
        }
        return decrypt;
    }

}

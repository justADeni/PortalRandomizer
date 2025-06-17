package com.github.justadeni.portalRandomizer.crypto;

public class CombinedFeistel {

    public static int forward(int input) {
        int input16 = (input >> 8) & 0xFFFF;   // middle 16 bits
        int input8 = input & 0xFF;             // lowest 8 bits

        // encryption
        int encrypt16 = Feistel16Bit.feistelEncrypt(input16);
        int encrypt8 = Feistel8Bit.feistelEncrypt(input8);

        // putting it back together
        int encrypted = (encrypt16 << 8) | (encrypt8 & 0xFF);
        if ((encrypted & 0x800000) != 0) {
            encrypted |= 0xFF000000;
        } else {
            encrypted &= 0x00FFFFFF;
        }
        return encrypted;
    }

    public static int backward(int encrypted) {
        int output16 = (encrypted >> 8) & 0xFFFF;
        int output8 = encrypted & 0xFF;

        // decryption
        int decrypt16 = Feistel16Bit.feistelDecrypt(output16);
        int decrypt8 = Feistel8Bit.feistelDecrypt(output8);

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

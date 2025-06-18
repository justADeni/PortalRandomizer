package com.github.justadeni.portalRandomizer.util;

public class Virtual {

    public static void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}

package com.aurionpro.pig;

import java.util.Random;

public class Die {
    private Random random;

    public Die() {
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }
}

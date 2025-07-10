package com.aurionpro.pig;

public class Turn {
    private int turnCount;

    public Turn() {
        this.turnCount = 1;
    }

    public void next() {
        turnCount++;
    }

    public int getCurrentTurn() {
        return turnCount;
    }

    public boolean isGameOver() {
        return turnCount > 5;
    }
}

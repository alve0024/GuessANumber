package com.algonquincollege.alve0024.guessanumber;

/**
 * Created by leonardoalps (alve0024) on 2016-09-29.
 */

import java.util.Random;

public class RandomNumber {
    protected Random random;
    protected int currentRandomNumber;
    protected int minNumber;
    protected int maxNumber;

    /**
     * For security reason, it's not possible to call new RandomNumber();
     */
    private RandomNumber() {
        //No body can call: new RandomNumber()
    }

    /**
     * Constructor
     * @param min - Define the minimum value
     * @param max - Define the maximum value
     */
    public RandomNumber(int min, int max) {
        currentRandomNumber = 0;
        minNumber = min;
        maxNumber = max;
        random = new Random();
    }

    /**
     * Generate a random number between the numbers passed in the constructor
     * @return the random number
     */
    public int generateRandomNumber() {
        currentRandomNumber = random.nextInt((maxNumber - minNumber) + 1);
        return currentRandomNumber;
    }

}

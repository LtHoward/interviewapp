package com.model;

/**
 * Class to represent the rewards in the application. Manages the type of reward, amount, and if the reward was redeemed or not.
 */
public class Reward {
    private RewardType type;
    private int amount;
    private boolean redeemed;

    /**
     * Constructor for the the reward class with the given parameters
     * @param type the reward type 
     * @param amount the amount of the reward
     * @param redeemed wheater the reward has been redeemed
     */
    public Reward (RewardType type, int amount, boolean redeemed) {
        this.type = type;
        this.amount = amount;
        this.redeemed = redeemed;
    }

    /**
     * Method to get the type of the reward
     * @return the reward type
     */
    public RewardType getType() {
        return type;
    }

    /**
     * Method to get the amount of the reward
     * @return the reward value
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Method to check if the reward has been redeemed
     * @return true if the reward was redeemed, false if otherwise
     */
    public boolean isRedeemed() {
        return redeemed;
    }
}

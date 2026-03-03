package com.model;

public class Reward {
    private RewardType type;
    private int amount;
    private boolean redeemed;

<<<<<<< HEAD
    public Reward (RewardType type, int amount) {

    }

    public RewardType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isRedeemed() {
        return true;
=======
    public Reward(RewardType type, int amount) {
        this.type = type;
        this.amount = amount;
        this.redeemed = false;
    }

    public RewardType getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean isRedeemed() {
        return this.redeemed;
>>>>>>> 32b7bbd57b11d024f9b9eee8711f348e08b8b2ce
    }
}

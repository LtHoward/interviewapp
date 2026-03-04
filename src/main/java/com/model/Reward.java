package com.model;

public class Reward {
    private RewardType type;
    private int amount;
    private boolean redeemed;

    public Reward (RewardType type, int amount) {
        this.type = type;
        this.amount = amount;
        this.redeemed = true;
    }

    public RewardType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isRedeemed() {
        return true;
    }
}

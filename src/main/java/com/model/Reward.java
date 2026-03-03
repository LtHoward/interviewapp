package com.model;

public class Reward {
    private RewardType type;
    private int amount;
    private boolean redeemed;

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
    }
}

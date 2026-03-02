package com.model;

public class Reward {
<<<<<<< HEAD
=======

>>>>>>> fdb99d3959b5bb708c3f38cbe349b043eb400c9d
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
    }
}
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
    }
}
>>>>>>> fdb99d3959b5bb708c3f38cbe349b043eb400c9d

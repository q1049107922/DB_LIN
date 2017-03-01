package com.DB_LIN.beans;

/**
 * Created by b_lin on 2017/3/1.
 */
public class PoolConfig {
    private int initialSize;
    private int maxSize;
    private int maxFreeSize;
    private int timeOut;
    private int inCreaseSize;

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getInCreaseSize() {
        return inCreaseSize;
    }

    public void setInCreaseSize(int inCreaseSize) {
        this.inCreaseSize = inCreaseSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxFreeSize() {
        return maxFreeSize;
    }

    public void setMaxFreeSize(int maxFreeSize) {
        this.maxFreeSize = maxFreeSize;
    }
}

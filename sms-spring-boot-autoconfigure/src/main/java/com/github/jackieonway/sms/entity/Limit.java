package com.github.jackieonway.sms.entity;

/**
 * limit param
 */
public class Limit{
    /**
     * Limit interval time: ms, default: 60s
     */
    private int limitTime = 60000;

    /**
     * Is or not allow restrictions, default: false
     */
    private boolean enable = false;

    public int getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Limit{" +
                "limitTime=" + limitTime +
                ", enable=" + enable +
                '}';
    }
}
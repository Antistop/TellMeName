package com.hanhan.currentLimiting;


/**
 * 使用令牌算法进行限流
 */
public class TokenBucket {
    /**
     * 时间
     */
    private long time;
    /**
     * 总量
     */
    private double total;
    /**
     * token 放入速度
     */
    private double rate;
    /**
     * 当前总量
     */
    private double nowSize;

    public TokenBucket(double total, double rate) {
        this.total = total;
        this.rate = rate;
    }

    public synchronized boolean limit() {
        long now = System.currentTimeMillis();
        nowSize = Math.min(total, nowSize + (now - time) * rate);
        time = now;
        if (nowSize < 1) {
            // 桶里没有token
            return false;
        } else {
            //存在token
            nowSize -= 1;
            return true;
        }
    }
}

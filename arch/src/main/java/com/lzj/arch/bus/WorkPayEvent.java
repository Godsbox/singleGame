/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.bus;



/**
 * 作品内支付按钮点击事件。
 *
 * @author wsy
 */
public class WorkPayEvent {

    /**
     * 解锁事件。
     */
    public static final int WORK_UNLOCK_CHARAPTER = 1;

    /**
     * 购买商品。
     */
    public static final int WORK_GOODS_BUY = 2;

    /**
     * 闪币支付
     */
    public static final int SCOIN = 0;

    /**
     * 星星支付
     */
    public static final int STAR_COIN = 1;

    private int type;

    private boolean result;

    private boolean isRecharge;

    private int payType = SCOIN;

    public WorkPayEvent(int type, boolean result, boolean isRecharge, boolean isStarPay){
        this.type = type;
        this.result = result;
        if(isStarPay){
            this.payType = STAR_COIN;
        }
        this.isRecharge = isRecharge;
    }

    public int getType() {
        return type;
    }

    public int getPayType(){
        return this.payType;
    }

    public boolean isResult() {
        return result;
    }

    public boolean isRecharge() {
        return isRecharge;
    }
}

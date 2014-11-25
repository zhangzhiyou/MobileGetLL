package com.xiayule.getll.db.model;

import java.util.Date;

/**
 * 流量币的使用记录
 * 只包含兑换流量的记录
 * Created by tan on 14-11-25.
 */
public class FlowMoneyCost {
    /**
     * id
     */
    private Integer id;

    /**
     * 花费人
     */
    private String mobile;

    /**
     * 话费金额
     */
    private Double cost;

    /**
     * 话费时间
     */
    private Date time;



}

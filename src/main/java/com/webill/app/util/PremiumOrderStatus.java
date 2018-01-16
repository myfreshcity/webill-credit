package com.webill.app.util;

import java.util.ArrayList;
import java.util.List;

public enum PremiumOrderStatus {
//  -1:已废弃、2000：已创建(草稿)、2100:无报价、2200:有报价、3000:预约、4000:已推送、4100:已查看、4200:完成投保信息、4300:已确认、4400：待支付、5000：询价中、
//	7000:支付完成、 7100：支付失败、8000：已过期、9000：已完成、9100：已删除、
	USELESS(-1,"已废弃"),
	CREATED(2000,"已创建"),
	NOPRICE(2100,"无报价"),
	HAVEPRICE(2200,"有报价"),
	APPOINTMENT(3000,"预约"),
	ALREADYPUSH(4000,"已推送"),
	ALREADYSEE(4100,"已查看"),
	FINISHINFO(4200,"完成投保信息"),
	ASKPRICEING(4300,"已确认"),
	ALREADYCONFIRM(4400,"待支付"),
	PAYWAITTING(5000,"询价中"),
	PAYSUCCESS(7000,"支付完成"),
	PAYFAIL(7100,"支付失败"),
	OVERDUE(8000,"已过期"),
	COMPLETED(9000,"已完成"),
	DELETED(9100,"已删除");


    private Integer value;
    private String description;

    PremiumOrderStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
    
}

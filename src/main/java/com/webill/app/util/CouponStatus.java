package com.webill.app.util;

public enum CouponStatus {//状态：新建-100；提交审核-200；审核失败-300；审核通过未发放-400；发放中-500；停止发放-600；发放结束-700；作废-800
//  -1:已废弃、2000：已创建(草稿)、2100:无报价、2200:有报价、3000:预约、4000:已推送、4100:已查看、4200:完成投保信息、4300:已确认、4400：待支付、5000：询价中、
//	7000:支付完成、 7100：支付失败、8000：已过期、9000：已完成、9100：已删除、
	NEW_COUPON(100,"新建"),
	COMIT_CHECK(200,"提交审核"),
	CHECK_FAIL(300,"审核失败"),
	CHECK_PASS_NO_SEND(400,"审核通过未发放"),
	SENDING(500,"发放中"),
	STOP_SEND(600,"停止发放"),
	SEND_END(700,"发放结束"),
	DELETE(800,"作废");


    private Integer value;
    private String description;

    CouponStatus(Integer value, String description) {
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

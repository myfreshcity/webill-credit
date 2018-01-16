package com.webill.app.util;

import java.io.Serializable;
import java.util.Date;

import com.webill.core.model.User;

public class UserWeixinModel implements Serializable {

    private static final long serialVersionUID = 5383320664312912736L;

    /**
     * 是否获取了userinfo
     */
    public static final int IS_USERINFO_NO = 0;    // 未获取
    public static final int IS_USERINFO_YES = 1;    // 已获取

    public static final int SUBSCRIBE_NO = 0; // 未订阅（或取消订阅）
    public static final int SUBSCRIBE_YES = 1; // 已订阅

    private String openId;
    private User staff;
    private String nickName;
    private String headUrl;
    private String sex;
    private String city;
    private String province;
    private String country;
    private String privilege;
    private String unionid;
    private String jwtToken;
    private int isUserinfo;
    private Date createTime;
    private Date updateTime;

    private int subscribe;  // 是否订阅（0:未订阅   1:已订阅）
    private Date subscribe_stat_time; // 订阅状态变更时间

    
    public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public User getStaff() {
        return staff;
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

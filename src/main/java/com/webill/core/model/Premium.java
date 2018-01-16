package com.webill.core.model;

import java.io.Serializable;

import com.webill.framework.annotations.IdType;
import com.webill.framework.annotations.TableField;
import com.webill.framework.annotations.TableId;

public class Premium implements Serializable{
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(type = IdType.AUTO)
    private Integer id;

    private Integer sortCode;

    private String prmName;

    private String amount;

    private String prmCode;

    private Integer prmType;

    @TableField(exist = false)
    private String prmTypeStr;
    
    @TableField(exist = false)
    private String orderId;
    
    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPrmTypeStr() {
		return prmTypeStr;
	}

	public void setPrmTypeStr(String prmTypeStr) {
		this.prmTypeStr = prmTypeStr;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getPrmName() {
        return prmName;
    }

    public void setPrmName(String prmName) {
        this.prmName = prmName == null ? null : prmName.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }

    public String getPrmCode() {
		return prmCode;
	}

	public void setPrmCode(String prmCode) {
		this.prmCode = prmCode;
	}

	public Integer getPrmType() {
        return prmType;
    }

    public void setPrmType(Integer prmType) {
        this.prmType = prmType;
    }
}
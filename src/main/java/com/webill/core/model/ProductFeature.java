package com.webill.core.model;
/** 
* @ClassName: ProductFeature 
* @Description: 产品特色
* @author ZhangYadong
* @date 2017年12月4日 下午3:49:12 
*/
public class ProductFeature {
	private int	dataType;	//数据对应平台类型1：PC，2：H5，3：APP
	private String	content;	//特色内容
	private String	pic;	//特色图片
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}

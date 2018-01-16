package com.webill.app.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by david on 16/10/28.
 */
public class WeixinRequest {

    public final static int REQUEST_TYPE_MESSAGE = 1;
    public final static int REQUEST_TYPE_EVENT = 2;

    public final static String EVENT = "event";
    public final static String EVENT_SUBSCRIBE = "subscribe";
    public final static String EVENT_UNSUBSCRIBE = "unsubscribe";
    public final static String EVENT_CLICK = "CLICK";
    public final static String EVENT_VIEW = "VIEW";

    private int requestType = WeixinRequest.REQUEST_TYPE_MESSAGE;
    private String xml = "";

    private String toUserName;
    private String fromUserName;
    private long createTime;
    private String msgType;
    private String content;

    private String event; // 自定义按钮事件请求
    private String eventKey; // 事件请求key值
    private String ticket;

    public WeixinRequest() {
    }

    public void parseXML(InputStream is) throws IOException, JDOMException {
        SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(is);
        XMLOutputter XMLOut = new XMLOutputter();
        xml = XMLOut.outputString(doc);

        // 获得文件的根元素
        Element root = doc.getRootElement();
        // 获得根元素的第一级子节点
        List list = root.getChildren();
        for (int j = 0; j < list.size(); j++) {
            // 获得结点
            Element first = (Element) list.get(j);

            if (first.getName().equals("ToUserName")) {
                toUserName = first.getValue().trim();
            } else if (first.getName().equals("FromUserName")) {
                fromUserName = first.getValue().trim();
            } else if (first.getName().equals("CreateTime")) {
//                createTime = WeixinUtil.getDateFromUnixTime(Long.parseLong(first.getValue().trim()));
            	createTime = Long.parseLong(first.getValue().trim());
            } else if (first.getName().equals("MsgType")) {
                msgType = first.getValue().trim();
                if(msgType.equals(WeixinRequest.EVENT)) {
                    requestType = WeixinRequest.REQUEST_TYPE_EVENT;
                }
            } else if (first.getName().equals("Content")) {
                content = first.getValue().trim();
            } else if (first.getName().equals("Event")) {
                event = first.getValue().trim();
            } else if (first.getName().equals("EventKey")) {
                eventKey = first.getValue().trim();
            }
        }
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}

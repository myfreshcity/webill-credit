package com.webill.app.util;

import java.util.ArrayList;
import java.util.Date;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 * Created by david on 16/10/28.
 */
public class WeixinResponse {
    public final static String MESSAGE_TYPE_TEXT  = "text";
    public final static String MESSAGE_TYPE_IMAGE = "image";
    public final static String MESSAGE_TYPE_VOICE = "voice";
    public final static String MESSAGE_TYPE_VIDEO = "video";
    public final static String MESSAGE_TYPE_MUSIC = "music";
    public final static String MESSAGE_TYPE_NEWS  = "news";

    private String xml = "";
    private String toUserName;
    private String fromUserName;
    private Date createTime;
    private String content;
    private String title;
    private String description;
    private String musicUrl;
    private String hqMusicUrl;
    private ArrayList<WeixinNewsItem> newsItemList = new ArrayList<WeixinNewsItem>();

    public WeixinResponse() {
        this.createTime = new Date();
    }

    public void generateTextMessage(){
        String time = ""+WeixinUtil.getUnixTimeFromDate(this.getCreateTime());

        Element rootXML = new Element("xml");
        rootXML.addContent(new Element("ToUserName").setText(toUserName));
        rootXML.addContent(new Element("FromUserName").setText(fromUserName));
        rootXML.addContent(new Element("CreateTime").setText(time));
        rootXML.addContent(new Element("MsgType").setText(WeixinResponse.MESSAGE_TYPE_TEXT));
        rootXML.addContent(new Element("Content").setText(content));
        Document doc = new Document(rootXML);

        XMLOutputter XMLOut = new XMLOutputter();
        xml = XMLOut.outputString(doc);
    }

    public void generateNewsMessage(){
        String time = ""+WeixinUtil.getUnixTimeFromDate(this.getCreateTime());

        Element rootXML = new Element("xml");
        rootXML.addContent(new Element("ToUserName").setText(toUserName));
        rootXML.addContent(new Element("FromUserName").setText(fromUserName));
        rootXML.addContent(new Element("CreateTime").setText(time));
        rootXML.addContent(new Element("MsgType").setText(WeixinResponse.MESSAGE_TYPE_NEWS));
        rootXML.addContent(new Element("ArticleCount").setText(""+this.getNewsItemList().size()));

        Element fXML = new Element("Articles");
        Element mXML = null;
        for(WeixinNewsItem item: this.newsItemList) {
            mXML = new Element("item");
            mXML.addContent(new Element("Title").setText(item.getTitle()));
            mXML.addContent(new Element("Description").setText(item.getDescription()));
            mXML.addContent(new Element("PicUrl").setText(item.getPicUrl()));
            mXML.addContent(new Element("Url").setText(item.getUrl()));
            fXML.addContent(mXML);
        }
        rootXML.addContent(fXML);
        Document doc = new Document(rootXML);

        XMLOutputter XMLOut = new XMLOutputter();
        xml = XMLOut.outputString(doc);
    }

    public void generateMusicMessage(){
        String time = ""+WeixinUtil.getUnixTimeFromDate(this.getCreateTime());

        Element rootXML = new Element("xml");
        rootXML.addContent(new Element("ToUserName").setText(toUserName));
        rootXML.addContent(new Element("FromUserName").setText(fromUserName));
        rootXML.addContent(new Element("CreateTime").setText(time));
        rootXML.addContent(new Element("MsgType").setText(WeixinResponse.MESSAGE_TYPE_MUSIC));

        Element mXML = new Element("Music");
        mXML.addContent(new Element("Title").setText(title));
        mXML.addContent(new Element("Description").setText(description));
        mXML.addContent(new Element("MusicUrl").setText(musicUrl));
        mXML.addContent(new Element("HQMusicUrl").setText(hqMusicUrl));
        rootXML.addContent(mXML);
        Document doc = new Document(rootXML);

        XMLOutputter XMLOut = new XMLOutputter();
        xml = XMLOut.outputString(doc);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public ArrayList<WeixinNewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(ArrayList<WeixinNewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

}

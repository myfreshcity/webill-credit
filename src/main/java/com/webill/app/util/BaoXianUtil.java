package com.webill.app.util;

public enum BaoXianUtil {
	taipingyangbaoxian("taipingyangbaoxian","太平洋保险"),
	renbaocaixian("renbaocaixian","人保财险"),
	dadibaoxian("dadibaoxian","大地保险"),
	yangguangbaoxian("yangguangbaoxian","阳光保险");


    private String pinyin;
    private String chinese;

    BaoXianUtil(String pinyin, String chinese) {
        this.pinyin = pinyin;
        this.chinese = chinese;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getChinese() {
        return chinese;
    }
    
}

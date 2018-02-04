package com.webill.app.controller;  
  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.Mongo;  
  
@SuppressWarnings("deprecation")
public class testController {  
	@Test   
    public void testMongodb() {  
      try{     
            // 连接到 mongodb 服务  
             Mongo mongo = new Mongo("192.168.98.133", 27017);    
            //根据mongodb数据库的名称获取mongodb对象 ,  
             DB db = mongo.getDB( "webill" );  
             Set<String> collectionNames = db.getCollectionNames();    
               // 打印出test中的集合    
              for (String name : collectionNames) {    
                    System.out.println("collectionName==="+name);    
              }    
               
          }catch(Exception e){  
             e.printStackTrace();  
          }  
    }
	public static void main(String[] args) throws Exception {
		/*Integer ms = 1482552000;
		if(ms==null){  
            ms=0;  
        }  
        long msl=(long)ms*1000;  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date temp=null;  
        if(ms!=null){  
            try {  
                String str=sdf.format(msl);  
                temp=sdf.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println(temp);
		
		String res="1482552000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(res);
        Date date = new Date(lt);
        String parse = simpleDateFormat.format(date);
        return res;
		
		String a = "1482552000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = sdf.parse(a);
		System.out.println(parse);*/
	}
}
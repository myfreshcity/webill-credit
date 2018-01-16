package com.webill.app.util;

import java.lang.reflect.Field;

import com.alibaba.fastjson.JSON;

public class CombineBeansUtil {
    
    /** 
     * @Title: combineSydwCore 
     * @Description: 该方法是用于相同对象不同属性值的合并，如果两个相同对象中同一属性都有值，那么sourceBean中的值会覆盖tagetBean重点的值
     * @author: WangLongFei
     * @date: 2017年12月26日 下午1:53:19 
     * @param sourceBean    被提取的对象bean
     * @param targetBean    用于合并的对象bean
     * @return targetBean 	合并后的对象
     * @return: Object
     */
    public static Object combineSydwCore(Object sourceBean,Object targetBean){
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();
        
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for(int i=0; i<sourceFields.length; i++){
            Field sourceField = sourceFields[i]; 
            Field targetField = targetFields[i];  
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if( !(sourceField.get(sourceBean) == null) &&  !"serialVersionUID".equals(sourceField.getName().toString())){
                    targetField.set(targetBean,sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targetBean;
    }
    
    
//    //测试 combineBeans方法
//    public static void main(String[] args) {
//        ProductOrder sourceModel = new ProductOrder();    //    第一个对象
//        ProductOrder targetModel = new ProductOrder();    //    第二个model对象
//        
//        sourceModel.setSex("1");
//        sourceModel.setcName("张三");
//        
//        targetModel.setSex("2");
//        targetModel.setcName("李四");
//        targetModel.setCardName("身份证");
//        targetModel.setCardNumber("222222222222222222222");
//        
//        CombineBeansUtil test = new CombineBeansUtil();
//        test.combineSydwCore(sourceModel, targetModel);
//        
//        System.out.println(JSON.toJSONString(targetModel));
//    }
}

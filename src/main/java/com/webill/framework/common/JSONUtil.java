package com.webill.framework.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * Created by david on 16/10/19.
 */
public class JSONUtil {
	
	private static final ObjectMapper mapper = new ObjectMapper();
    
    public static <T> T toObject(String jsonString, Class<T> valueType) 
    		throws IOException {
    	return mapper.readValue(jsonString, valueType);
    }
    
    public static String toJSONString(Object object,
                                      SerializerFeature... features) {
        SerializeWriter out = new SerializeWriter();
        String s;
        JSONSerializer serializer = new JSONSerializer(out);
        SerializerFeature[] arr$ = features;
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; i$++) {
            SerializerFeature feature = arr$[i$];
            serializer.config(feature, true);
        }

        serializer.getValueFilters().add(new ValueFilter() {
            public Object process(Object obj, String s, Object value) {
                if (null != value) {
                    if (value instanceof java.util.Date) {
                        return String.format("%1$tF %1tT", value);
                    }

                    return value;
                } else {
                    return "";
                }
            }
        });
        serializer.write(object);
        s = out.toString();
        out.close();

        return s;
    }

    public static <T>List<T> toObjectList(String jsonString, Class<T> valueType) {
    	JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
    	try {
    		List<T> list = mapper.readValue(jsonString, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static void main(String[] args) {
        List<Map<String, Date>> list = new ArrayList<Map<String, Date>>();

        for (int i = 0; i <= 20; i++) {
            Map<String, Date> m = new HashMap<String, Date>();
            m.put(String.valueOf(i), null);
            m.put("date", new Date(System.currentTimeMillis()));
            list.add(m);
        }

        String json = JSONUtil.toJSONString(list);
        System.err.println(json);

        List<Map> parse = (List<Map>) JSON.parse(json);
        System.err.println("end ....");
    }


}

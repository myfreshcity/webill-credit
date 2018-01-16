package com.webill.app;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 字符串类型转换日期类型---页面表单中字符串格式的日期传到后台时，自动转成Date类型
 */
public class DateConverter implements Converter<String, Date> {

    public static Log logger = LogFactory.getLog(DateConverter.class);

    @Override
    public Date convert(String dateStr) {
        try {
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }
            return DateUtils.parseDate(dateStr, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm","yyyy年MM月dd日");
        } catch (ParseException e) {
            logger.error(String.format("%s的日期格式转换错误！", dateStr), e);
        }
        return null;
    }

}
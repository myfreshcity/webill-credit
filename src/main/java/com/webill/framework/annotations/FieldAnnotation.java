/**
 * @Copyright Atos Origin
 */
package com.webill.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Do check the default value of each annotation fields with compare to the entity field
 * @column annotation which will be overrided by @FieldAnnotation.
 * 
 * @author <a href="mailto:wangtao.pkuss@gmail.com">Wang Tao</a>
 * @since Dec 3, 2009
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited()
public @interface FieldAnnotation {
	//basic CRUD capability
	boolean updatable() default false;
	boolean listable() default false;
	boolean viewable() default false;
	boolean insertable() default false;
	boolean filterable() default false;
	String tabId() default "";
	String tabTitle() default "";
	//for input validate
	String dataType() default "String";
	int minLength() default 0;// 
	int maxLength() default 10;//@Attention!!! In case of non alphanumeric characters, 
								//The field maxLength must be greater than 10 and the actually input field will be devided by 3.
	int orderValue() default 0;// field with same orderValue will be order by field name.
	
	// for enum type;
	String enumKey() default "" ;
	
	//for relation information CRUD
//	String relationIdField() default "";
	String relationNameField() default "";// for relational field list and view.
	String relationHql() default "";// the hql can create a map with key and value for select.such as 'select id,name from...'
	
	
	
	
	
	
}

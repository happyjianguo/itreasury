/*
 * Created on 2006-3-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import com.iss.itreasury.util.Log4j;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataTranserUtil {

    protected Log4j log = new Log4j(1, this);

    /**
     * 从ResultSet中获取查询结果 规则是rs里面的名字和data entity对应字段必须同名
     * 
     * @param className
     *            需要查找的数据库表对应的Data Entity的类名
     * @return 不会返回空，有可能返回0长度collection
     * @throws RuntimeException
     *             传入非法参数或者发生不可恢复的错误
     */
    public Collection getDataEntitiesFromResultSet(Class dataEntityClass,
            ResultSet rs) {
        if (dataEntityClass == null || rs == null) {
            throw new RuntimeException("传入了空参数，违反方法前置条件");
        }
        ArrayList result = new ArrayList();
        ResultSetDynaClass rsdc = null;
        try {
            rsdc = new ResultSetDynaClass(rs);
            BeanInfo beanInfo = Introspector.getBeanInfo(dataEntityClass);
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            for (Iterator it = rsdc.iterator(); it.hasNext();) {
                //通过反射生成的对象用于存储从ResultSet中取得的值
                Object resourceObject = dataEntityClass.newInstance();
                DynaBean row = (DynaBean) it.next();
                //对单条记录循环处理其所有属性
                for (int i = 0; i < properties.length; i++) {
                    String fieldName = properties[i].getName();
                    Method writeMethod = properties[i].getWriteMethod();
                    if (writeMethod == null) {
                        continue;
                    }
                    Object valueObject = null;
                    //可能在DataEntity中定义有对应的数据库表中没有的变量
                    try {
                        valueObject = row.get(fieldName.toLowerCase());
                    } catch (Exception e) {
                    }
                    if (valueObject == null) {
                        continue;
                    }
                    Class type = properties[i].getPropertyType();
                    if (valueObject instanceof BigDecimal) {
                        if (type.getName().compareToIgnoreCase("long") == 0) {
                            valueObject = new Long(valueObject.toString());
                        } else if (type.getName().compareToIgnoreCase("double") == 0) {
                            valueObject = new Double(valueObject.toString());
                        }
                    } else if (valueObject instanceof Timestamp
                            || valueObject instanceof String) {
                    } else {
                        continue;
                    }
                    writeMethod.invoke(resourceObject,
                            new Object[] { valueObject });
                }
                result.add(resourceObject);
            }
        } catch (Exception e) {
            throw new RuntimeException("转换结果集过程中发生了不可恢复的错误", e);
        }
        return result;
    }

}


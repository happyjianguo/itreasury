/*
 * Created on 2006-3-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yinghu TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
 */
public class TinyBeanUtil {
    //支持转换的数据类型
    private static final Set DATATYPES = new HashSet();
    
    static {
        DATATYPES.add("long");
        DATATYPES.add("double");
        DATATYPES.add("java.lang.String");
        DATATYPES.add("java.sql.Timestamp");
    }
    
    /**
     * 拷贝两个对象的值 规则是拷贝有相同名字的字段 忽略大小写
     * 在可以自动转型的情况下会自动转型，比如从int到long 
     * 此方法对于对象只采取简单的引用拷贝而不是值复制
     * 不能传入空参数
     * @param src
     * @param dest
     * @throws TRF_Exception 失败时抛出
     */
    public static void copyFieldsValues(Object src, Object dest) {
        if(src==null || dest==null){
            throw new NullPointerException("null parameter is not permitted");
        }
        try {
            BeanInfo beanInfoOfSrc = Introspector.getBeanInfo(src.getClass());
            PropertyDescriptor[] propsOfSrc = beanInfoOfSrc
                    .getPropertyDescriptors();
            BeanInfo beanInfoOfDest = Introspector.getBeanInfo(dest.getClass());
            PropertyDescriptor[] propsOfDest = beanInfoOfDest
                    .getPropertyDescriptors();
            //遍历比较源对象和目标对象的字段名，相同则复制值
            for (int i = 0; i < propsOfSrc.length; i++) {
                String fieldNameOfSrc = propsOfSrc[i].getName();
                for (int j = 0; j < propsOfDest.length; j++) {
                    if (fieldNameOfSrc.equalsIgnoreCase(propsOfDest[j]
                            .getName())) {
                        Method readMethod = propsOfSrc[i].getReadMethod();
                        //源对象没有读方法时忽略
                        if (readMethod == null) {
                            System.out.println(src.getClass().getName() + "."
                                    + fieldNameOfSrc
                                    + " has not a read method!");
                            continue;
                        }
                        Object valueOfFieldOfSrc = readMethod.invoke(src,
                                new Object[] {});
                        Method writeMethod = propsOfDest[j].getWriteMethod();
                        //目标对象没有写方法时忽略
                        if (writeMethod == null) {
                            System.out.println(dest.getClass().getName() + "."
                                    + propsOfDest[j].getName()
                                    + " has not a write method!");
                            continue;
                        }
                        try {
                            writeMethod.invoke(dest, new Object[] { valueOfFieldOfSrc });
                        } catch (Exception e) {
                            System.out.println("fail to copy value from "
                                    + src.getClass().getName() + "."
                                    + fieldNameOfSrc + " to "
                                    + dest.getClass().getName() + "."
                                    + propsOfDest[j].getName());
                            System.out.println(e.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new TRF_Exception("fatal error occurs when copying data", e);
        }
    }
    
    /**
     * 查找数据实体中发生变化的字段 能处理的字段类型是long double String Timestamp类型可以添加
     * 用实体对象和基准对象进行比较，如果基准对象为空，就和类型默认值进行比较
     * @param dataEntity
     * @param baseLineEntity
     * @return 没有发生变化的字段时返回长度为0的map
     * @throws RuntimeException
     *             传入null或者方法发生了不可恢复的错误
     */
    public static Map findChangedFieldsOfDataEntity(Object dataEntity,Object baseLineEntity) {
        if (dataEntity == null) {
            throw new RuntimeException("传入了空参数");
        }
        if(baseLineEntity!=null&&!dataEntity.getClass().equals(baseLineEntity.getClass())){
            throw new RuntimeException("基准对象和被比较对象类型不同");
        }
        Map result = new HashMap();
        //取原型对象，准备通过这个对象取对应类型各字段的默认值
        try {
            Object protoTypeObject = baseLineEntity;
            if(baseLineEntity==null){
                protoTypeObject= dataEntity.getClass().newInstance();
            }    
            BeanInfo beanInfo = Introspector.getBeanInfo(dataEntity.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < properties.length; i++) {
                String fieldName = properties[i].getName();
                Method readMethod = properties[i].getReadMethod();
                if (readMethod == null) {
                    continue;
                }
                Class type = properties[i].getPropertyType();
                Object valueOfFieldOfDataEntity = readMethod.invoke(dataEntity,
                        new Object[] {});
                Object valueOfFieldOfProtoTypeObject = readMethod.invoke(
                        protoTypeObject, new Object[] {});
                //对于字段不同的类型情况进行值判断，记录发生改变的字段
                if (DATATYPES.contains(type.getName())
                        && isFieldValueChanged(valueOfFieldOfProtoTypeObject,
                                valueOfFieldOfDataEntity)) {
                    result.put(fieldName, valueOfFieldOfDataEntity);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("发生不可恢复的错误");
        }
        return result;
    }

    private static boolean isFieldValueChanged(Object valueOfFieldOfProtoTypeObject,
            Object valueOfFieldOfDataEntity) {
        return (valueOfFieldOfProtoTypeObject == null && valueOfFieldOfDataEntity != null)
                || (valueOfFieldOfProtoTypeObject != null && (!valueOfFieldOfProtoTypeObject
                        .equals(valueOfFieldOfDataEntity)));
    }
    
    /**
     * 查找数据实体中所有的字段 能处理的字段类型是long double String Timestamp类型可以添加
     * @param dataEntity
     * @return 没有字段时返回长度为0的map
     * @throws RuntimeException
     *             传入null或者方法发生了不可恢复的错误
     */
    public static Map getAllFields(Object dataEntity){
        if (dataEntity == null) {
            throw new RuntimeException("传入了空参数");
        }
        Map result = new HashMap();
        try{
	        BeanInfo beanInfo = Introspector.getBeanInfo(dataEntity.getClass());
	        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
	        for (int i = 0; i < properties.length; i++) {
                String fieldName = properties[i].getName();
                Method readMethod = properties[i].getReadMethod();
                if (readMethod == null) {
                    continue;
                }
                Class type = properties[i].getPropertyType();
                Object value = readMethod.invoke(dataEntity,new Object[] {});
                //符合定义的类型字段才被记录
                if (DATATYPES.contains(type.getName())) {
                    result.put(fieldName, value);
                }
	        }
        }
        catch(Exception e){
            throw new RuntimeException("发生不可恢复的错误");            
        }
        return result;
    }
    

}
package com.iss.system.dataentity;

/**
 * <p>
 * 所有DataEntityBean的抽象基类，提供了对DataEntityBean对象的数据状态维护的默认实现
 * 。
 * </p>
 * <p>
 * 在具体子类中，在需要维护当前对象的数据状态的set()方法中，调用该抽象类提供的setPr
 * operty()方法完成赋值。
 * </p>
 * <p>
 * 例子：
 * 
 *     package com.iss.iam.dataentity;
 * 
 *     import java.util.*;
 * 
 *     public class ExampleBean extends BaseDataEntityBean 
 *     {  
 *          private Date m_dtDate ;
 *          private long m_lLong;
 * 	
 *          public ExampleBean()
 *          {
 *               super();  	
 *          }
 * 	
 * 	        public Date getDate(){ return m_dtDate;}
 * 
 * 	        public long getLong(){ return m_lLong;}
 * 
 * 	        // 该set()方法需维护 当前bean的数据状态 
 *          public void setDate(Date date) 
 *              throws Exception 
 *          {
 *              setProperty("m_dtDate", date); 
 *          }
 * 
 * 	        // 该set()方法需维护当前bean的数据状态  
 *          public void setLong(long l) 
 *              throws Exception 
 *          {
 *              setProperty("m_lLong", l); 
 *          }
 *     }
 * </p>
 */
public abstract class BaseDataEntityBean
{

	/**
	 * 数据实体对象的数据状态。
	 * 100        Original 原始数据（从数据库中取得）
	 */
	public static final long DATAENTITYBEAN_STATUS_ORIGINAL = 100;

	/**
	 * 新建数据
	 * 100        New
	 */
	public static final long DATAENTITYBEAN_STATUS_NEW = 200;

	/**
	 * 已被修改的数据
	 * 300        Modify
	 */
	public static final long DATAENTITYBEAN_STATUS_MODIFY = 300;

	/**
	 * 已被删除的数据 
	 * 300        Delete
	 */
	public static final long DATAENTITYBEAN_STATUS_DELETE = 400;

	/**
	 * 标识DataEntityBean对象的数据状态，系统共定义了四种状态：
	 *     Original     原始状态，数据库中数据；
	 *     New          新建数据；
	 *     Modify       已修改；
	 *     Delete       已删除；
	 */
	protected long m_lDataStatus;

	/**
	 * Constructor for DataEntityBean.
	 * 初始化m_lDataStatus为 New （新建数据）
	 * @roseuid 3EC883E301C3
	 */
	public BaseDataEntityBean()
	{
		//初始化数据状态为New
		m_lDataStatus = DATAENTITYBEAN_STATUS_NEW;
	}

	/**
	 * 将指定的值对象赋给指定名称的属性，同时完成对当前DataEntityBean的数据状态的维护
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception@throws java.lang.Exception
	 * @roseuid 3EC883E301D3
	 */
	protected final boolean setProperty(String strProperty, Object value)
		throws Exception
	{
		boolean result = false;

		//校验参数是否为空,值对象value可以为空
		if (strProperty == null || strProperty.equalsIgnoreCase(""))
		{
			throw new Exception("参数无效！");
		}
        
        if(value == null)
        {
            return false;
        }

		//获得当前对象指定名称的Field对象
		java.lang.reflect.Field field = null;
		try
		{
			field = this.getClass().getDeclaredField(strProperty);

			field.setAccessible(true);
		}
		catch (NoSuchFieldException exp)
		{
			throw new Exception(
				this.getClass().getName() + " 无 " + strProperty + " 属性！");
		}
		catch (SecurityException exp)
		{
			throw new Exception(this.getClass().getName() + "受安全约束，无法访问！");
		}

		try
		{
			Object obj = field.get(this);

			//属性当前值和要赋予的值不等时再赋
			if (!value.equals(obj))
			{
				field.set(this, value);

				result = true;

				//  修改当前对象的数据状态
				if (m_lDataStatus == DATAENTITYBEAN_STATUS_ORIGINAL)
				{
					m_lDataStatus = DATAENTITYBEAN_STATUS_MODIFY;
				}
			}
		}
		catch (IllegalArgumentException exp)
		{
			throw new Exception("赋值对象类型无效！应为" + field.getType().getName());
		}
		catch (IllegalAccessException exp)
		{
			throw new Exception(this.getClass().getName() + "受安全约束，无法访问！");
		}
		catch (SecurityException exp)
		{
			throw new Exception(this.getClass().getName() + "受安全约束，无法访问！");
		}

		return result;
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受boolean类型的值作为将赋之值，String参数指定的bean属性应是boolea
	 * n简单数据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E303B7
	 */
	protected final boolean setProperty(String strProperty, boolean value)
		throws Exception
	{
		Boolean bTemp = new Boolean(value);

		return setProperty(strProperty, bTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受byte类型的值作为将赋之值，String参数指定的bean属性应是byte简单?
	 * 据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E40197
	 */
	protected final boolean setProperty(String strProperty, byte value)
		throws Exception
	{
		Byte bTemp = new Byte(value);

		return setProperty(strProperty, bTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受char类型的值作为将赋之值，String参数指定的bean属性应是char简单?
	 * 据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E40379
	 */
	protected final boolean setProperty(String strProperty, char value)
		throws Exception
	{
		Character cTemp = new Character(value);

		return setProperty(strProperty, cTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受double类型的值作为将赋之值，String参数指定的bean属性应是double?
	 * 单数据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E50158
	 */
	protected final boolean setProperty(String strProperty, double value)
		throws Exception
	{
		Double dTemp = new Double(value);

		return setProperty(strProperty, dTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受float类型的值作为将赋之值，String参数指定的bean属性应是float简?
	 * 数据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E5032C
	 */
	protected final boolean setProperty(String strProperty, float value)
		throws Exception
	{
		Float fTemp = new Float(value);

		return setProperty(strProperty, fTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受int类型的值作为将赋之值，String参数指定的bean属性应是int简单数?
	 * 类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E6010A
	 */
	protected final boolean setProperty(String strProperty, int value)
		throws Exception
	{
		Integer iTemp = new Integer(value);

		return setProperty(strProperty, iTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受long类型的值作为将赋之值，String参数指定的bean属性应是long简单?
	 * 据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E602DF
	 */
	protected final boolean setProperty(String strProperty, long value)
		throws Exception
	{
		Long lTemp = new Long(value);

		return setProperty(strProperty, lTemp);
	}

	/**
	 * 重载setProperty(String, 
	 * Object)方法，接受short类型的值作为将赋之值，String参数指定的bean属性应是short简?
	 * 数据类型。
	 * @param strProperty 要赋值的属性的名称
	 * @param value 要赋予的值对象
	 * @return boolean true：赋值成功；false：失败
	 * @throws Exception
	 * @roseuid 3EC883E700CA
	 */
	protected final boolean setProperty(String strProperty, short value)
		throws Exception
	{
		Short sTemp = new Short(value);

		return setProperty(strProperty, sTemp);
	}

	/**
	 * Returns the dataStatus.
	 * @return long
	 * @roseuid 3EC883E7029E
	 */
	public long getDataStatus()
	{
		return m_lDataStatus;
	}

	/**
	 * Sets the dataStatus.
	 * @param dataStatus The dataStatus to set
	 * @roseuid 3EC883E702AE
	 */
	public void setDataStatus(long dataStatus)
	{
		m_lDataStatus = dataStatus;
	}
}

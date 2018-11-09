/**
 * 
 */
package com.iss.itreasury.settlement.bankinstruction;

import java.lang.reflect.Constructor;
import com.iss.itreasury.util.Config;

/**
 * @author qijiang
 * 结算入账操作类工厂
 * 在不同的业务需求下结算入账的处理方法也可能存在很大差别
 * 此类工场返回一个IImportAccount类型的接口,意在于调用接口的多态性而实际执行具体子类的方法
 */
public final class ImportAccountFactory {

	private static final String importAccountConfigTag = Config.INTEGRATION_SERVICE_IMPORTACCOUNTCLASS;
	
	/**
     * 使用默认配置项,默认构造函数构造入账操作类
     * @return
     */
    public static IImportAccount getInstance()
	{
        return getInstance(importAccountConfigTag);
	}
    
    /**
     * 使用指定配置项,默认构造函数构造入账操作类
     * @return
     */
    public static IImportAccount getInstance(String strTag)
	{
		return getInstance(strTag,null);
	}
    
    /**
     * 使用默认配置项,利用带参数的构造函数构造入账操作类
     * @return
     */
    public static IImportAccount getInstance(Object arg)
	{
		return getInstance(importAccountConfigTag,arg);
	}
    
    /**
     * 使用指定配置项,利用带参数的构造函数构造入账操作类
     * @return
     */
    public static IImportAccount getInstance(String strTag,Object arg)
	{
    	IImportAccount importAccount = null;
        String className = Config.getProperty(strTag,"com.iss.itreasury.settlement.bankinstruction.InstructionBean.InstructionBean_SouthAir");
		Class cls = null;
		try
		{
		    cls = Class.forName(className);
			if (arg == null)
			{
				importAccount = (IImportAccount) cls.newInstance();
			}
			else
			{
				Constructor constructor = cls.getConstructor(new Class[]{arg.getClass()});
				importAccount = (IImportAccount) constructor.newInstance(new Object[] { arg });
			}
		}
		catch (Exception e)
		{
			System.out.println(className + ":" + e.getMessage());
		}
		
		return importAccount;
	}

}

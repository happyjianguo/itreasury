/*
 * Created on 2005-8-10
 *
 */
package com.iss.itreasury.settlement.bankinstruction;

import java.lang.reflect.Constructor;
import com.iss.itreasury.util.Config;

/**
 * @author weilu
 * 银行指令操作类 类工场
 * 在不同的业务需求下银行指令的处理方法也可能存在很大差别
 * 此类工场返回一个IBankInstruction类型的接口,意在于调用接口的多态性而实际执行具体子类的方法
 */
public final class BankInstructionFactory {
    
    private static final String instructionConfigTag = Config.INTEGRATION_SERVICE_INSTRUCTIONCLASS;    
    
    /**
     * 使用默认配置项,默认构造函数构造银行指令操作类
     * @return
     */
    public static IBankInstruction getInstance()
	{
        return getInstance(instructionConfigTag);
	}
    
    /**
     * 使用指定配置项,默认构造函数构造银行指令操作类
     * @return
     */
    public static IBankInstruction getInstance(String strTag)
	{
		return getInstance(strTag,null);
	}
    
    /**
     * 使用默认配置项,利用带参数的构造函数构造指令操作类
     * @return
     */
    public static IBankInstruction getInstance(Object arg)
	{
		return getInstance(instructionConfigTag,arg);
	}
    
    
    /**
     * 使用指定配置项,利用带参数的构造函数构造指令操作类
     * @return
     */
    public static IBankInstruction getInstance(String strTag,Object arg)
	{
        IBankInstruction aInstruction = null;
        String className = Config.getProperty(strTag,null);
		Class cls = null;
		try
		{
		    cls = Class.forName(className);
			if (arg == null)
			{
			    aInstruction = (IBankInstruction) cls.newInstance();
			}
			else
			{
				Constructor constructor = cls.getConstructor(new Class[]{arg.getClass()});
				aInstruction = (IBankInstruction) constructor.newInstance(new Object[] { arg });
			}
		}
		catch (Exception e)
		{
			System.out.println(className + ":" + e.getMessage());
		}
		return aInstruction;
	}
    
    

}

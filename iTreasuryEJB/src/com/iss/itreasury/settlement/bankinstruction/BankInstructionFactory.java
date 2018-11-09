/*
 * Created on 2005-8-10
 *
 */
package com.iss.itreasury.settlement.bankinstruction;

import java.lang.reflect.Constructor;
import com.iss.itreasury.util.Config;

/**
 * @author weilu
 * ����ָ������� �๤��
 * �ڲ�ͬ��ҵ������������ָ��Ĵ�����Ҳ���ܴ��ںܴ���
 * ���๤������һ��IBankInstruction���͵Ľӿ�,�����ڵ��ýӿڵĶ�̬�Զ�ʵ��ִ�о�������ķ���
 */
public final class BankInstructionFactory {
    
    private static final String instructionConfigTag = Config.INTEGRATION_SERVICE_INSTRUCTIONCLASS;    
    
    /**
     * ʹ��Ĭ��������,Ĭ�Ϲ��캯����������ָ�������
     * @return
     */
    public static IBankInstruction getInstance()
	{
        return getInstance(instructionConfigTag);
	}
    
    /**
     * ʹ��ָ��������,Ĭ�Ϲ��캯����������ָ�������
     * @return
     */
    public static IBankInstruction getInstance(String strTag)
	{
		return getInstance(strTag,null);
	}
    
    /**
     * ʹ��Ĭ��������,���ô������Ĺ��캯������ָ�������
     * @return
     */
    public static IBankInstruction getInstance(Object arg)
	{
		return getInstance(instructionConfigTag,arg);
	}
    
    
    /**
     * ʹ��ָ��������,���ô������Ĺ��캯������ָ�������
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

/**
 * 
 */
package com.iss.itreasury.settlement.bankinstruction;

import java.lang.reflect.Constructor;
import com.iss.itreasury.util.Config;

/**
 * @author qijiang
 * �������˲����๤��
 * �ڲ�ͬ��ҵ�������½������˵Ĵ�����Ҳ���ܴ��ںܴ���
 * ���๤������һ��IImportAccount���͵Ľӿ�,�����ڵ��ýӿڵĶ�̬�Զ�ʵ��ִ�о�������ķ���
 */
public final class ImportAccountFactory {

	private static final String importAccountConfigTag = Config.INTEGRATION_SERVICE_IMPORTACCOUNTCLASS;
	
	/**
     * ʹ��Ĭ��������,Ĭ�Ϲ��캯���������˲�����
     * @return
     */
    public static IImportAccount getInstance()
	{
        return getInstance(importAccountConfigTag);
	}
    
    /**
     * ʹ��ָ��������,Ĭ�Ϲ��캯���������˲�����
     * @return
     */
    public static IImportAccount getInstance(String strTag)
	{
		return getInstance(strTag,null);
	}
    
    /**
     * ʹ��Ĭ��������,���ô������Ĺ��캯���������˲�����
     * @return
     */
    public static IImportAccount getInstance(Object arg)
	{
		return getInstance(importAccountConfigTag,arg);
	}
    
    /**
     * ʹ��ָ��������,���ô������Ĺ��캯���������˲�����
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

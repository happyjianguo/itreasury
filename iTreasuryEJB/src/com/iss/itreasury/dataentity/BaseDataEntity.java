/*
 * Created on 2003-12-6
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.dataentity;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BaseDataEntity
{

	/**
	 * 
	 */
	public BaseDataEntity()
	{
		super();
		// TODO Auto-generated constructor stub
	}
    public String toString()
    {
        StringBuffer sbResult = new StringBuffer(128);

        sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");

        //获得当前对象指定名称的Field对象
        java.lang.reflect.Field[] field = null;
        try
        {
            field = this.getClass().getDeclaredFields();

            if (field != null)
            {
                for (int i = 0; i < field.length; i++)
                {
                    field[i].setAccessible(true);

                    sbResult.append(field[i].getName() + " = ");
                    sbResult.append(field[i].get(this) + ";\r\n");
                }
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }

        return sbResult.toString();
    }

}

/*
 * Created on 2005-11-23
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print.dataentity;

/**
 * @author rxie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TemplateSettingXmlInfo
{
	public static void main(String[] args)
	{}
	
	private long   TemplateType = -1;//ģ������
	private String TemplateDetailName = "";//ģ��Ԫ������
	private String TemplateDetailCode = "";//ģ��Ԫ�ر���
	private String TemplateDetailVariable = "";//ģ��Ԫ�ر���
	/**
	 * @return Returns the templateDetailCode.
	 */
	public String getTemplateDetailCode()
	{
		return TemplateDetailCode;
	}
	/**
	 * @param templateDetailCode The templateDetailCode to set.
	 */
	public void setTemplateDetailCode(String templateDetailCode)
	{
		TemplateDetailCode = templateDetailCode;
	}
	/**
	 * @return Returns the templateDetailName.
	 */
	public String getTemplateDetailName()
	{
		return TemplateDetailName;
	}
	/**
	 * @param templateDetailName The templateDetailName to set.
	 */
	public void setTemplateDetailName(String templateDetailName)
	{
		TemplateDetailName = templateDetailName;
	}
	/**
	 * @return Returns the templateDetailVariable.
	 */
	public String getTemplateDetailVariable()
	{
		return TemplateDetailVariable;
	}
	/**
	 * @param templateDetailVariable The templateDetailVariable to set.
	 */
	public void setTemplateDetailVariable(String templateDetailVariable)
	{
		TemplateDetailVariable = templateDetailVariable;
	}
	/**
	 * @return Returns the templateType.
	 */
	public long getTemplateType()
	{
		return TemplateType;
	}
	/**
	 * @param templateType The templateType to set.
	 */
	public void setTemplateType(long templateType)
	{
		TemplateType = templateType;
	}
}

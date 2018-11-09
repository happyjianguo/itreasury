/*
 * Created on 2004-10-20
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.print.templateinfo;
/**
 * @author ruixie To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintOptionInfo implements java.io.Serializable
{
	public PrintOptionInfo()
	{
		super();
	}
	//	模版ID
	public long		m_lTemplateID			= -1;
	//	模版Details ID
	public long		m_lTemplateDetailsID	= -1;
	//	套打字段名称
	public String	m_strDetailsName		= "";
	//	套打字段显示实例
	public String	m_strDetailsData		= "";
	//	办事处
	public long		m_lOfficeID				= -1;
	//	套打名称
	public String	m_strName				= "";
	//	套打描述
	public String	m_strDesc				= "";
	//	打印机名称
	public String	m_strPrintName			= "";
	//	模版顶空
	public double	m_dTemplateTop			= 0.0;
	//	模版左空
	public double	m_dTemplateLeft			= 0.0;
	//	模版状态
	public long		m_lStatusID				= -1;
	//	模版Details 编码
	public String	m_strCode				= "";
	//	模版Details 顶空
	public double	m_dDetailsTop			= 0.0;
	//	模版Details 左空
	public double	m_dDetailsLeft			= 0.0;
	//	模版Details 字体
	public String	m_strFont				= "";
	//	nTypeID
	public long		m_lTypeID				= -1;
	//	是否粗体
	public long		m_lIsBold				= 0;
	//	是否斜体
	public long		m_lIsItalic				= 0;
	//	字体大小
	public double	m_lSize					= 0.0;
	//	字体宽度
	public long		m_lFiledWidth			= 0;
	
	public long		m_lAllPage				= 0;
	
	
	//币种
	public long     m_lCurrencyID			=-1;
	//模版的类型（1：存款类型		2:贷款类型)
	public long 	m_nPrintTemplateType	=-1;
	
}

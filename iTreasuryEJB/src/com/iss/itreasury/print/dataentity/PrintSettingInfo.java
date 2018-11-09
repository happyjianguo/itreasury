/*
 * Created on 2004-12-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.print.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintSettingInfo extends ITreasuryBaseDataEntity
{
	private long Id = -1;
	private long Office = -1;
	private long Currency = -1;
	private String Header = "";
	private String Footer = "";
	private long LeftMargin = -1;
	private long RightMargin = -1;
	private long TopMargin = -1;
	private long BottomMargin = -1;
	private long ModuleType = -1;
	
	/**
	 * @return Returns the bottomMargin.
	 */
	public long getBottomMargin()
	{
		return BottomMargin;
	}
	/**
	 * @param bottomMargin The bottomMargin to set.
	 */
	public void setBottomMargin(long bottomMargin)
	{
		BottomMargin = bottomMargin;
		putUsedField("BottomMargin", bottomMargin);
		BottomMargin = bottomMargin;
	}
	/**
	 * @return Returns the currency.
	 */
	public long getCurrency()
	{
		return Currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(long currency)
	{
		Currency = currency;
		putUsedField("Currency", currency);
		Currency = currency;
	}
	/**
	 * @return Returns the footer.
	 */
	public String getFooter()
	{
		return Footer;
	}
	/**
	 * @param footer The footer to set.
	 */
	public void setFooter(String footer)
	{
		Footer = footer;
		putUsedField("Footer", footer);
		Footer = footer;
	}
	/**
	 * @return Returns the header.
	 */
	public String getHeader()
	{
		return Header;
	}
	/**
	 * @param header The header to set.
	 */
	public void setHeader(String header)
	{
		Header = header;
		putUsedField("Header", header);
		Header = header;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId()
	{
		return Id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id)
	{
		Id = id;
		putUsedField("Id", id);
		Id = id;
	}
	/**
	 * @return Returns the leftMargin.
	 */
	public long getLeftMargin()
	{
		return LeftMargin;
	}
	/**
	 * @param leftMargin The leftMargin to set.
	 */
	public void setLeftMargin(long leftMargin)
	{
		LeftMargin = leftMargin;
		putUsedField("LeftMargin", leftMargin);
		LeftMargin = leftMargin;
	}
	/**
	 * @return Returns the moduleType.
	 */
	public long getModuleType()
	{
		return ModuleType;
	}
	/**
	 * @param moduleType The moduleType to set.
	 */
	public void setModuleType(long moduleType)
	{
		ModuleType = moduleType;
		putUsedField("ModuleType", moduleType);
		ModuleType = moduleType;
	}
	/**
	 * @return Returns the office.
	 */
	public long getOffice()
	{
		return Office;
	}
	/**
	 * @param office The office to set.
	 */
	public void setOffice(long office)
	{
		Office = office;
		putUsedField("Office", office);
		Office = office;
	}
	/**
	 * @return Returns the rightMargin.
	 */
	public long getRightMargin()
	{
		return RightMargin;
	}
	/**
	 * @param rightMargin The rightMargin to set.
	 */
	public void setRightMargin(long rightMargin)
	{
		RightMargin = rightMargin;
		putUsedField("RightMargin", rightMargin);
		RightMargin = rightMargin;
	}
	/**
	 * @return Returns the topMargin.
	 */
	public long getTopMargin()
	{
		return TopMargin;
	}
	/**
	 * @param topMargin The topMargin to set.
	 */
	public void setTopMargin(long topMargin)
	{
		TopMargin = topMargin;
		putUsedField("TopMargin", topMargin);
		TopMargin = topMargin;
	}
}

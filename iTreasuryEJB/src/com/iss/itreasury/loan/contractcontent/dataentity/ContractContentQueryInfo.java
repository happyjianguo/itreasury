/*
 * Created on 2003-11-05
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.contractcontent.dataentity;

/**
 * @author pepsi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ContractContentQueryInfo implements java.io.Serializable
{
	private String year = "";                                                                        
	private String season = "";
	private long docType = -1;
	private long lParentId = -1;
	/**
	 * @return 返回 parentId。
	 */
	public long getParentId() {
		return lParentId;
	}
	/**
	 * @param parentId 要设置的 parentId。
	 */
	public void setParentId(long parentId) {
		lParentId = parentId;
	}
	/**
	 * @return Returns the docType.
	 */
	public long getDocType()
	{
		return docType;
	}
	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(long docType)
	{
		this.docType=docType;
	}
	/**
	 * @return Returns the season.
	 */
	public String getSeason()
	{
		return season;
	}
	/**
	 * @param season The season to set.
	 */
	public void setSeason(String season)
	{
		this.season=season;
	}
	/**
	 * @return Returns the year.
	 */
	public String getYear()
	{
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year)
	{
		this.year=year;
	}
}

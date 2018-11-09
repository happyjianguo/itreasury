/*
 * Created on 2003-11-11
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;

/**
 * @author xrli
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QueryLoanNoticeInfo implements Serializable
{

	/**
	 * 
	 */
	public QueryLoanNoticeInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	private long NoticeTypeID =-1;                   //通知书类型
	private long OfficeID = -1;                      //办事处  
	private long CurrencyID = -1;                    //币种
	private String ClientNoFrom = "";                //客户编号
    private String ClientNoTo = "";                  //客户编号
    private String ClientNameFrom = "";              //客户名称
    private String ClientNameTo = "";                //客户名称
    private String AccountNoFrom = "";               //贷款账户号
    private String AccountNoTo = "";                 //贷款账户号
	private String ContractNoFrom = "";              //合同号
	private String ContractNoTo = "";                //合同号		
	private String PayFormNoFrom = "";               //放款通知单号
	private String PayFormNoTo = "";                 //放款通知单号	
	private String FormYearFrom = "";                //通知书年度
	private String FormYearTo = "";                  //通知书年度
	private String NoticeNoFrom = "";                //通知书编号
	private String NoticeNoTo = "";                  //通知书编号
    private long Desc = 1;                           //排序方式
    private long OrderField = 1;                     //排序字段
	private long FormNumFrom = -1;                   //催收次数
	private long FormNumTo = -1;                     //催收次数

	

	/**
	 * @return
	 */
	public String getAccountNoFrom()
	{
		return AccountNoFrom;
	}

	/**
	 * @return
	 */
	public String getAccountNoTo()
	{
		return AccountNoTo;
	}

	/**
	 * @return
	 */
	public String getClientNameFrom()
	{
		return ClientNameFrom;
	}

	/**
	 * @return
	 */
	public String getClientNameTo()
	{
		return ClientNameTo;
	}

	/**
	 * @return
	 */
	public String getClientNoFrom()
	{
		return ClientNoFrom;
	}

	/**
	 * @return
	 */
	public String getClientNoTo()
	{
		return ClientNoTo;
	}

	/**
	 * @return
	 */
	public String getContractNoFrom()
	{
		return ContractNoFrom;
	}

	/**
	 * @return
	 */
	public String getContractNoTo()
	{
		return ContractNoTo;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return
	 */
	public long getDesc()
	{
		return Desc;
	}

	

	/**
	 * @return
	 */
	public String getNoticeNoFrom()
	{
		return NoticeNoFrom;
	}

	/**
	 * @return
	 */
	public String getNoticeNoTo()
	{
		return NoticeNoTo;
	}

	/**
	 * @return
	 */
	public long getNoticeTypeID()
	{
		return NoticeTypeID;
	}

	/**
	 * @return
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @return
	 */
	public long getOrderField()
	{
		return OrderField;
	}

	/**
	 * @return
	 */
	public String getPayFormNoFrom()
	{
		return PayFormNoFrom;
	}

	/**
	 * @return
	 */
	public String getPayFormNoTo()
	{
		return PayFormNoTo;
	}

	/**
	 * @param string
	 */
	public void setAccountNoFrom(String string)
	{
		AccountNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setAccountNoTo(String string)
	{
		AccountNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setClientNameFrom(String string)
	{
		ClientNameFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNameTo(String string)
	{
		ClientNameTo = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoFrom(String string)
	{
		ClientNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setClientNoTo(String string)
	{
		ClientNoTo = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoFrom(String string)
	{
		ContractNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setContractNoTo(String string)
	{
		ContractNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setDesc(long l)
	{
		Desc = l;
	}

	

	/**
	 * @param string
	 */
	public void setNoticeNoFrom(String string)
	{
		NoticeNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setNoticeNoTo(String string)
	{
		NoticeNoTo = string;
	}

	/**
	 * @param l
	 */
	public void setNoticeTypeID(long l)
	{
		NoticeTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @param l
	 */
	public void setOrderField(long l)
	{
		OrderField = l;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoFrom(String string)
	{
		PayFormNoFrom = string;
	}

	/**
	 * @param string
	 */
	public void setPayFormNoTo(String string)
	{
		PayFormNoTo = string;
	}

	/**
	 * @return
	 */
	public long getFormNumFrom()
	{
		return FormNumFrom;
	}

	/**
	 * @return
	 */
	public long getFormNumTo()
	{
		return FormNumTo;
	}

	/**
	 * @param l
	 */
	public void setFormNumFrom(long l)
	{
		FormNumFrom = l;
	}

	/**
	 * @param l
	 */
	public void setFormNumTo(long l)
	{
		FormNumTo = l;
	}

	/**
	 * @return
	 */
	public String getFormYearFrom()
	{
		return FormYearFrom;
	}

	/**
	 * @return
	 */
	public String getFormYearTo()
	{
		return FormYearTo;
	}

	/**
	 * @param string
	 */
	public void setFormYearFrom(String string)
	{
		FormYearFrom = string;
	}

	/**
	 * @param string
	 */
	public void setFormYearTo(String string)
	{
		FormYearTo = string;
	}

}

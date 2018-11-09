/*
 * Created on 2004-8-3
 *
 * Title:				iTreasury
 * @author             	yanliu 
 * Company:             iSoftStone
 * Copyright:           Copyright (c) 2003
 * @version
 * Description:         
 */
package com.iss.itreasury.loan.transdiscountcredence.dataentity;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.DataFormat;

/**
 * @author yfan
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SelectedTransDiscountBillInfo extends LoanBaseDataEntity
{
    private long id = -1;
    private long[] allBillID = null;			//所有被选票据的ID数组
    private String allBillString = "";			//所有被选票据ID拼成的字串
    private long count = -1;					//所选票据个数
	private double totalAmount = 0.0;     		//总汇票金额
	private double totalAccrual = 0.0;    		//总票据利息
	private double totalRealAmount = 0.0; 		//总票据实付金额
	private double repurchasedAmount = 0.0;		//已回购金额（回购凭证）
	private long credenceID = -1;				//转贴现凭证编号（回购凭证）
	private String credenceCode = "";			//转贴现凭证编号（回购凭证用）
	private long contractID = -1;				//合同ID
	private long credenceType = -1;				//凭证类型
	private long billSourceType = -1;			//票据来源
	private long discountTypeID = -1;			//转贴现种类
	private long repurchaseTypeID = -1;			//回购类型
	private long inOrOut = -1;					//买入还是卖出
	private long acceptPOTypeID = -1;			//汇票种类
	private String draftCode = "";				//汇票号码
	private long repurchaseTerm = -1;			//回购期限
	private Timestamp repurchaseDate = null;	//回购日期
	private Timestamp creatDate = null;			//发票日
	private Timestamp endDate = null;			//到期日
	private String sBank = "";                  //承兑方
	
    public String getSBank() {
		return sBank;
	}

	public void setSBank(String bank) {
		sBank = bank;
	}

	public long getId()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
     */
    public void setId(long id)
    {
        // TODO Auto-generated method stub
        
    }
    


	/**
	 * @return
	 */
	public String getAllBillString()
	{
		return allBillString;
	}

	/**
	 * @return
	 */
	public long getCount()
	{
		return count;
	}

	/**
	 * @return
	 */
	public double getTotalAccrual()
	{
		return totalAccrual;
	}

	/**
	 * @return
	 */
	public double getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @return
	 */
	public double getTotalRealAmount()
	{
		return totalRealAmount;
	}

	/**
	 * @param string
	 */
	public void setAllBillString(String string)
	{
		allBillString = string;
		
		Vector v = new Vector();
		long lBillID = -1;
		v = DataFormat.changeStringGroup(string);
		if( (v != null) && (v.size() > 0) )
		{
			long[] ls = new long[v.size()];
			Iterator it = v.iterator();
			int i =0;
			while (it.hasNext() && i<v.size())
			{
				lBillID = ((Long)it.next()).longValue();
				ls[i] = lBillID;
				i++;
			}
			this.allBillID = ls;
		}
	}

	/**
	 * @param l
	 */
	public void setCount(long l)
	{
		count = l;
	}

	/**
	 * @param d
	 */
	public void setTotalAccrual(double d)
	{
		totalAccrual = d;
	}

	/**
	 * @param d
	 */
	public void setTotalAmount(double d)
	{
		totalAmount = d;
	}

	/**
	 * @param d
	 */
	public void setTotalRealAmount(double d)
	{
		totalRealAmount = d;
	}

	/**
	 * @return
	 */
	public long[] getAllBillID()
	{
		return allBillID;
	}

	/**
	 * @param ls
	 */
	public void setAllBillID(long[] ls)
	{
		allBillID = ls;
		
		String strBillID ="";
		long lBillID = ls[0];
		strBillID += lBillID;
		for(int i=1;i<ls.length;i++)
		{				
			lBillID = ls[i];
			strBillID += ","+lBillID;
		}
		this.allBillString = strBillID;
	}

	/**
	 * @return
	 */
	public long getContractID()
	{
		return contractID;
	}

	/**
	 * @param l
	 */
	public void setContractID(long l)
	{
		contractID = l;
	}

	/**
	 * @return
	 */
	public long getBillSourceType()
	{
		return billSourceType;
	}

	/**
	 * @param l
	 */
	public void setBillSourceType(long l)
	{
		billSourceType = l;
	}

	/**
	 * @return
	 */
	public long getDiscountTypeID()
	{
		return discountTypeID;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTypeID()
	{
		return repurchaseTypeID;
	}

	/**
	 * @param l
	 */
	public void setDiscountTypeID(long l)
	{
		discountTypeID = l;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTypeID(long l)
	{
		repurchaseTypeID = l;
	}

	/**
	 * @return
	 */
	public long getInOrOut()
	{
		return inOrOut;
	}

	/**
	 * @param l
	 */
	public void setInOrOut(long l)
	{
		inOrOut = l;
	}

	/**
	 * @return
	 */
	public long getAcceptPOTypeID()
	{
		return acceptPOTypeID;
	}

	/**
	 * @param l
	 */
	public void setAcceptPOTypeID(long l)
	{
		acceptPOTypeID = l;
	}

	/**
	 * @return
	 */
	public long getCredenceType()
	{
		return credenceType;
	}

	/**
	 * @param l
	 */
	public void setCredenceType(long l)
	{
		credenceType = l;
	}

	/**
	 * @return
	 */
	public double getRepurchasedAmount()
	{
		return repurchasedAmount;
	}

	/**
	 * @param d
	 */
	public void setRepurchasedAmount(double d)
	{
		repurchasedAmount = d;
	}

	/**
	 * @return
	 */
	public Timestamp getCreatDate()
	{
		return creatDate;
	}

	/**
	 * @return
	 */
	public String getDraftCode()
	{
		return draftCode;
	}

	/**
	 * @return
	 */
	public Timestamp getEndDate()
	{
		return endDate;
	}

	/**
	 * @return
	 */
	public Timestamp getRepurchaseDate()
	{
		return repurchaseDate;
	}

	/**
	 * @return
	 */
	public long getRepurchaseTerm()
	{
		return repurchaseTerm;
	}

	/**
	 * @param timestamp
	 */
	public void setCreatDate(Timestamp timestamp)
	{
		creatDate = timestamp;
	}

	/**
	 * @param string
	 */
	public void setDraftCode(String string)
	{
		draftCode = string;
	}

	/**
	 * @param timestamp
	 */
	public void setEndDate(Timestamp timestamp)
	{
		endDate = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setRepurchaseDate(Timestamp timestamp)
	{
		repurchaseDate = timestamp;
	}

	/**
	 * @param l
	 */
	public void setRepurchaseTerm(long l)
	{
		repurchaseTerm = l;
	}

	/**
	 * @return
	 */
	public String getCredenceCode()
	{
		return credenceCode;
	}

	/**
	 * @param string
	 */
	public void setCredenceCode(String string)
	{
		credenceCode = string;
	}

	/**
	 * @return
	 */
	public long getCredenceID()
	{
		return credenceID;
	}

	/**
	 * @param l
	 */
	public void setCredenceID(long l)
	{
		credenceID = l;
	}

}

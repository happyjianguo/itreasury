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
package com.iss.itreasury.loan.transdiscountapply.dataentity;

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
public class SelectBillInfo extends LoanBaseDataEntity
{
    private long id = -1;
    private long loanID = -1;
    private long transDiscountApplyID = -1;
    private long[] allBillID = null;			//���б�ѡƱ�ݵ�ID����
    private String allBillString = "";			//���б�ѡƱ��IDƴ�ɵ��ִ�
    private long count = -1;					//��ѡƱ�ݸ���
	private double totalAmount = 0.0;     		//�ܻ�Ʊ���
	private double totalAccrual = 0.0;    		//��Ʊ����Ϣ
	private double totalRealAmount = 0.0; 		//��Ʊ��ʵ�����
	private long contractID = -1;				//��ͬID
	private long billSourceType = -1;			//Ʊ����Դ
	private long discountTypeID = -1;
	private long repurchaseTypeID = -1;
	private long inOrOut = -1;

    private long[] isLocal = null;//ISLOCAL �Ƿ񱾵�
    private long[] addDays = null;//ADDDAYS �ڼ�����������
    private Timestamp[] repurchaseDate = null;//REPURCHASEDATE �ع�����
    private long[] repurchaseTerm = null;//REPURCHASETERM �ع�����
    
    private long isSubmit = -1;//���ύ���Ǳ���
	
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
     * @param 
     * return long[]
     */
    public long[] getAddDays()
    {
        return addDays;
    }

    /**
     * @param 
     * return long[]
     */
    public long[] getIsLocal()
    {
        return isLocal;
    }

    /**
     * @param 
     * return Timestamp[]
     */
    public Timestamp[] getRepurchaseDate()
    {
        return repurchaseDate;
    }

    /**
     * @param 
     * return long[]
     */
    public long[] getRepurchaseTerm()
    {
        return repurchaseTerm;
    }

    /**
     * @param 
     * return long
     */
    public long getTransDiscountApplyID()
    {
        return transDiscountApplyID;
    }

    /**
     * @param 
     * return void
     */
    public void setAddDays(long[] ls)
    {
        addDays = ls;
    }

    /**
     * @param 
     * return void
     */
    public void setIsLocal(long[] ls)
    {
        isLocal = ls;
    }

    /**
     * @param 
     * return void
     */
    public void setRepurchaseDate(Timestamp[] timestamps)
    {
        repurchaseDate = timestamps;
    }

    /**
     * @param 
     * return void
     */
    public void setRepurchaseTerm(long[] ls)
    {
        repurchaseTerm = ls;
    }

    /**
     * @param 
     * return void
     */
    public void setTransDiscountApplyID(long l)
    {
        transDiscountApplyID = l;
    }

    /**
     * @param 
     * return long
     */
    public long getLoanID()
    {
        return loanID;
    }

    /**
     * @param 
     * return void
     */
    public void setLoanID(long l)
    {
        loanID = l;
    }

    /**
     * @param 
     * return long
     */
    public long getIsSubmit()
    {
        return isSubmit;
    }

    /**
     * @param 
     * return void
     */
    public void setIsSubmit(long l)
    {
        isSubmit = l;
    }

}

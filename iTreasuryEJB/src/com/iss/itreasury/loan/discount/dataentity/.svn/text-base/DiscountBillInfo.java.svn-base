/*
 * DiscountBillInfo.java
 *
 * Created on 2002年4月8日, 下午2:40
 */

package com.iss.itreasury.loan.discount.dataentity;

import java.sql.Timestamp;

/**
 *
 * @author  yzhang
 * @version 
 */
public class DiscountBillInfo implements java.io.Serializable {

    /** Creates new DiscountBillInfo */
    public DiscountBillInfo() {
        super (  );   
    }     

    private long ID;                //ID标识
    private long DiscountApplyID;   //贴现申请标识
    private long DiscountContractID;//贴现合同标识
    private long DiscountCredenceID;//贴现凭证标识
	private long OBDiscountCredenceID;//网银贴现凭证标识
	private String DiscountContractCode;  //贴现合同号码
    
    private long SerialNo;           //序列号
    private String UserName;        //原始出票人
    private String Bank;            //承兑银行
    private long IsLocal;          //承兑银行所在地（是否北京）
    private Timestamp Create;      //出票日
    private Timestamp End;         //到期日
    private long Days;               //实际贴现天数
    private String Code;            //汇票号码
    private double Amount;          //汇票金额
    private double DiscountRate;    //贴现利率
    private double Accrual;         //贴现利息
    private double RealAmount;      //实付贴现金额
    private long StatusID;            //是否删除
    private long AddDays;			//节假日增加计息天数
    private Timestamp DiscountDate;//计息日
    private long AcceptPOTypeID;	//汇票类型 
    private String FormerOwner;     //汇票直接前手  
    
    private String Payee;
    
    private long  NbillstatusId;    //票据状态
    private Timestamp   DcancelDate;  //销账日期
    
    
    private long  NcheckStatus;    //复查状态
    private String  ScheckID;        //复查编号
    private Timestamp  DtcheckDate;  //复查日期
    
    private long Count;             //记录数
    private long BankCount;         //记录数
    private long BizCount;          //记录数
    private double TotalAmount;     //总汇票金额
    private double TotalAccrual;    //总票据利息
    private double TotalRealAmount; //总票据实付金额
    
    private double PurchaserAccrual;    //买方付息
    private double DiscountAccrual;    //贴现人付息
    private double DiscountAcount;    //贴现金额

    
    public double getDiscountAccrual() {
		return DiscountAccrual;
	}
	public void setDiscountAccrual(double discountAccrual) {
		DiscountAccrual = discountAccrual;
	}
	public double getDiscountAcount() {
		return DiscountAcount;
	}
	public void setDiscountAcount(double discountAcount) {
		DiscountAcount = discountAcount;
	}
	public double getPurchaserAccrual() {
		return PurchaserAccrual;
	}
	public void setPurchaserAccrual(double purchaserAccrual) {
		PurchaserAccrual = purchaserAccrual;
	}
	public long getNbillStatusId()
    {
        return NbillstatusId;
    }
         /**
	 * @param l
	 */
     public void setNbillStatusId(long billstatusId)
    {
     	NbillstatusId = billstatusId;
    }
    
     
     /**
  	 * @param timestamp
  	 */
     public Timestamp getDcancelDate()
     {
         return DcancelDate;
     }
     
     public String getPayee() {
  		return Payee;
  	}
  	public void setPayee(String payee) {
  		Payee = payee;
  	}
          /**
 	 * @param timestamp
 	 */
      public void setDcancelDate(Timestamp timestamp)
     {
      	DcancelDate = timestamp;
     }
    /**
     * @return
     */
    public long getAcceptPOTypeID()
    {
        return AcceptPOTypeID;
    }

    /**
     * @return
     */
    public double getAccrual()
    {
        return Accrual;
    }

    /**
     * @return
     */
    public long getAddDays()
    {
        return AddDays;
    }

    /**
     * @return
     */
    public double getAmount()
    {
        return Amount;
    }

    /**
     * @return
     */
    public String getBank()
    {
        return Bank;
    }

    /**
     * @return
     */
    public String getCode()
    {
        return Code;
    }
    public String getDiscountContractCode()
    {
        return  DiscountContractCode;
    }
    
    public void setDiscountContractCode(String ContractCode)
    {
    	DiscountContractCode = ContractCode;
    }
    /**
     * @return
     */
    public long getCount()
    {
        return Count;
    }
    
    /**
     * @return
     */
    public long getBankCount()
    {
        return BankCount;
    }
    
    /**
     * @return
     */
    public long getBizCount()
    {
        return BizCount;
    }

    /**
     * @return
     */
    public Timestamp getCreate()
    {
        return Create;
    }

    /**
     * @return
     */
    public long getDays()
    {
        return Days;
    }

	/**
     * @return
     */
    public long getDiscountContractID()
    {
        return DiscountContractID;
    }

    /**
     * @return
     */
    public long getDiscountApplyID()
    {
        return DiscountApplyID;
    }

    /**
     * @return
     */
    public long getDiscountCredenceID()
    {
        return DiscountCredenceID;
    }

    /**
     * @return
     */
    public Timestamp getDiscountDate()
    {
        return DiscountDate;
    }

    /**
     * @return
     */
    public double getDiscountRate()
    {
        return DiscountRate;
    }

    /**
     * @return
     */
    public Timestamp getEnd()
    {
        return End;
    }

    /**
     * @return
     */
    public long getID()
    {
        return ID;
    }

    /**
     * @return
     */
    public long getIsLocal()
    {
        return IsLocal;
    }

    /**
     * @return
     */
    public double getRealAmount()
    {
        return RealAmount;
    }

    /**
     * @return
     */
    public long getSerialNo()
    {
        return SerialNo;
    }

    /**
     * @return
     */
    public long getStatusID()
    {
        return StatusID;
    }

    /**
     * @return
     */
    public double getTotalAccrual()
    {
        return TotalAccrual;
    }

    /**
     * @return
     */
    public double getTotalAmount()
    {
        return TotalAmount;
    }

    /**
     * @return
     */
    public double getTotalRealAmount()
    {
        return TotalRealAmount;
    }

    /**
     * @return
     */
    public String getUserName()
    {
        return UserName;
    }

    /**
     * @param l
     */
    public void setAcceptPOTypeID(long l)
    {
        AcceptPOTypeID = l;
    }

    /**
     * @param d
     */
    public void setAccrual(double d)
    {
        Accrual = d;
    }

    /**
     * @param l
     */
    public void setAddDays(long l)
    {
        AddDays = l;
    }

    /**
     * @param d
     */
    public void setAmount(double d)
    {
        Amount = d;
    }

    /**
     * @param string
     */
    public void setBank(String string)
    {
        Bank = string;
    }

    /**
     * @param string
     */
    public void setCode(String string)
    {
        Code = string;
    }

    /**
     * @param l
     */
    public void setCount(long l)
    {
        Count = l;
    }
    
    /**
     * @param l
     */
    public void setBankCount(long l)
    {
        BankCount = l;
    }
    
    /**
     * @param l
     */
    public void setBizCount(long l)
    {
        BizCount = l;
    }

    /**
     * @param timestamp
     */
    public void setCreate(Timestamp timestamp)
    {
        Create = timestamp;
    }

    /**
     * @param l
     */
    public void setDays(long l)
    {
        Days = l;
    }

    /**
     * @param l
     */
    public void setDiscountApplyID(long l)
    {
        DiscountApplyID = l;
    }
    
    /**
     * @param l
     */
    public void setDiscountContractID(long l)
    {
        DiscountContractID = l;
    }

    /**
     * @param l
     */
    public void setDiscountCredenceID(long l)
    {
        DiscountCredenceID = l;
    }

    /**
     * @param timestamp
     */
    public void setDiscountDate(Timestamp timestamp)
    {
        DiscountDate = timestamp;
    }

    /**
     * @param d
     */
    public void setDiscountRate(double d)
    {
        DiscountRate = d;
    }

    /**
     * @param timestamp
     */
    public void setEnd(Timestamp timestamp)
    {
        End = timestamp;
    }

    /**
     * @param l
     */
    public void setID(long l)
    {
        ID = l;
    }

    /**
     * @param l
     */
    public void setIsLocal(long l)
    {
        IsLocal = l;
    }

    /**
     * @param d
     */
    public void setRealAmount(double d)
    {
        RealAmount = d;
    }

    /**
     * @param l
     */
    public void setSerialNo(long l)
    {
        SerialNo = l;
    }

    /**
     * @param l
     */
    public void setStatusID(long l)
    {
        StatusID = l;
    }

    /**
     * @param d
     */
    public void setTotalAccrual(double d)
    {
        TotalAccrual = d;
    }

    /**
     * @param d
     */
    public void setTotalAmount(double d)
    {
        TotalAmount = d;
    }

    /**
     * @param d
     */
    public void setTotalRealAmount(double d)
    {
        TotalRealAmount = d;
    }

    /**
     * @param string
     */
    public void setUserName(String string)
    {
        UserName = string;
    }

    public void setFormerOwner(String formerOwner)
    {
        FormerOwner = formerOwner;
    }

    public String getFormerOwner()
    {
        return FormerOwner;
    }

	/**
	 * @return
	 */
	public long getOBDiscountCredenceID() {
		return OBDiscountCredenceID;
	}

	/**               
	 * @param l
	 */
	public void setOBDiscountCredenceID(long l) {
		OBDiscountCredenceID = l;
	}

	/**
	 * @return
	 */
      public long getNcheckStatus()
    {
        return NcheckStatus;
    }
         /**
	 * @param l
	 */
     public void setNcheckStatus(long l)
    {
        NcheckStatus = l;
    }
    
        /**
	 * @return
	 */
      public String getScheckID()
    {
        return ScheckID;
    }
    
         /**
	 * @param str
	 */
      public void setScheckID(String str)
    {
        ScheckID = str;
    }
        /**
	 * @return
	 */
     public Timestamp getDtcheckDate()
    {
        return DtcheckDate;
    }
    
         /**
	 * @param timestamp
	 */
     public void setDtcheckDate(Timestamp timestamp)
    {
        DtcheckDate = timestamp;
    }
}

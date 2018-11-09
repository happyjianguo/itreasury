package com.iss.itreasury.ebank.obdiscountapply.dataentity;
import java.sql.Timestamp;
/**
 * @author gqzhang
 *贴现票据信息
 * To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class DiscountBillInfo implements java.io.Serializable
{
	public DiscountBillInfo()
	{
		super();
	}
	private long ID = -1;
	private long lDiscountBillID = -1; //贴现票据标识,
	private long lDiscountApplyID = -1; //贴现标识,
	private long lDiscountCredenceID = -1; //贴现凭证标识,
	private long DiscountContractID = -1;
	private Timestamp DiscountDate = null;
	private String strUser = ""; //原始出票人,
	private String strBank = ""; //承兑银行,
	private long lSerialNo = -1; //序列号,
	private long lIsLocal = -1; //是否在本地,
	private Timestamp tsCreate = null; //出票日,
	private Timestamp tsEnd = null; // 到期日,
	private String strCode = ""; //汇票号码,
	private double dAmount = 0.0; //汇票金额,
	private double dCheckAmount = 0.0; //核定金额,
	private double Interest = 0.0; //汇票利息
	private long lAddDay = 0; //节假日增加天数,
	private long lAcceptPOTypeID = -1; //票据类型,
	private String strFormerOwner = ""; //贴现单位直接前手

	private long Count = 0;             //记录数
	private double TotalAmount = 0;     //总汇票金额
	private double TotalInterest = 0;   //总汇票利息

	private long Days = -1;             //实际贴现天数
	private double DiscountRate = 0;    //贴现利息

	private long OBDiscountCredenceID = -1; //网银使用的贴现凭证标识,
	private long nLoanID = -1;//贴现申请指令ID
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return dAmount;
	}
	/**
	 * Returns the acceptPOTypeID.
	 * @return long
	 */
	public long getAcceptPOTypeID()
	{
		return lAcceptPOTypeID;
	}
	/**
	 * Returns the addDay.
	 * @return long
	 */
	public long getAddDay()
	{
		return lAddDay;
	}
	/**
	 * Returns the discountApplyID.
	 * @return long
	 */
	public long getDiscountApplyID()
	{
		return lDiscountApplyID;
	}
	/**
	 * Returns the discountBillID.
	 * @return long
	 */
	public long getDiscountBillID()
	{
		return lDiscountBillID;
	}
	/**
	 * Returns the discountCredenceID.
	 * @return long
	 */
	public long getDiscountCredenceID()
	{
		return lDiscountCredenceID;
	}
	/**
	 * Returns the isLocal.
	 * @return long
	 */
	public long getIsLocal()
	{
		return lIsLocal;
	}
	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo()
	{
		return lSerialNo;
	}
	/**
	 * Returns the bank.
	 * @return String
	 */
	public String getBank()
	{
		return strBank;
	}
	/**
	 * Returns the code.
	 * @return String
	 */
	public String getCode()
	{
		return strCode;
	}
	/**
	 * Returns the formerOwner.
	 * @return String
	 */
	public String getFormerOwner()
	{
		return strFormerOwner;
	}
	/**
	 * Returns the user.
	 * @return String
	 */
	public String getUser()
	{
		return strUser;
	}
	/**
	 * Returns the create.
	 * @return Timestamp
	 */
	public Timestamp getCreate()
	{
		return tsCreate;
	}
	/**
	 * Returns the end.
	 * @return Timestamp
	 */
	public Timestamp getEnd()
	{
		return tsEnd;
	}
	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		dAmount = amount;
	}
	/**
	 * Sets the acceptPOTypeID.
	 * @param acceptPOTypeID The acceptPOTypeID to set
	 */
	public void setAcceptPOTypeID(long acceptPOTypeID)
	{
		lAcceptPOTypeID = acceptPOTypeID;
	}
	/**
	 * Sets the addDay.
	 * @param addDay The addDay to set
	 */
	public void setAddDay(long addDay)
	{
		lAddDay = addDay;
	}
	/**
	 * Sets the discountApplyID.
	 * @param discountApplyID The discountApplyID to set
	 */
	public void setDiscountApplyID(long discountApplyID)
	{
		lDiscountApplyID = discountApplyID;
	}
	/**
	 * Sets the discountBillID.
	 * @param discountBillID The discountBillID to set
	 */
	public void setDiscountBillID(long discountBillID)
	{
		lDiscountBillID = discountBillID;
	}
	/**
	 * Sets the discountCredenceID.
	 * @param discountCredenceID The discountCredenceID to set
	 */
	public void setDiscountCredenceID(long discountCredenceID)
	{
		lDiscountCredenceID = discountCredenceID;
	}
	/**
	 * Sets the isLocal.
	 * @param isLocal The isLocal to set
	 */
	public void setIsLocal(long isLocal)
	{
		lIsLocal = isLocal;
	}
	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo)
	{
		lSerialNo = serialNo;
	}
	/**
	 * Sets the bank.
	 * @param bank The bank to set
	 */
	public void setBank(String bank)
	{
		strBank = bank;
	}
	/**
	 * Sets the code.
	 * @param code The code to set
	 */
	public void setCode(String code)
	{
		strCode = code;
	}
	/**
	 * Sets the formerOwner.
	 * @param formerOwner The formerOwner to set
	 */
	public void setFormerOwner(String formerOwner)
	{
		strFormerOwner = formerOwner;
	}
	/**
	 * Sets the user.
	 * @param user The user to set
	 */
	public void setUser(String user)
	{
		strUser = user;
	}
	/**
	 * Sets the create.
	 * @param create The create to set
	 */
	public void setCreate(Timestamp create)
	{
		tsCreate = create;
	}
	/**
	 * Sets the end.
	 * @param end The end to set
	 */
	public void setEnd(Timestamp end)
	{
		tsEnd = end;
	}

	public long getCount()
	{
		return Count;
	}

	public void setCount(long Count)
	{
		this.Count = Count;
	}

	public double getTotalAmount()
	{
		return TotalAmount;
	}

	public void setTotalAmount(double TotalAmount)
	{
		this.TotalAmount = TotalAmount;
    }

	public double getInterest()
	{
		return Interest;
	}

	public void setInterest(double Interest)
	{
		this.Interest = Interest;
	}

	public double getTotalInterest()
	{
		return TotalInterest;
	}

	public void setTotalInterest(double TotalInterest)
	{
		this.TotalInterest = TotalInterest;
	}

	public long getID()
	{
		return ID;
	}

	public void setID(long ID)
	{
		this.ID = ID;
	}

	public long getDays()
	{
		return Days;
	}

	public void setDays(long Days)
	{
		this.Days = Days;
    }

	public double getDiscountRate()
	{
		return DiscountRate;
	}

	public void setDiscountRate(double DiscountRate)
	{
		this.DiscountRate = DiscountRate;
	}

	public long getDiscountContractID()
	{
		return DiscountContractID;
	}

	public void setDiscountContractID(long DiscountContractID)
	{
		this.DiscountContractID = DiscountContractID;
	}

	public Timestamp getDiscountDate()
	{
		return DiscountDate;
	}

	public void setDiscountDate(Timestamp DiscountDate)
	{
		this.DiscountDate = DiscountDate;
	}

	public long getOBDiscountCredenceID()
	{
		return OBDiscountCredenceID;
	}

	public void setOBDiscountCredenceID(long OBDiscountCredenceID)
	{
		this.OBDiscountCredenceID = OBDiscountCredenceID;
    }
	/**
	 * @return
	 */
	public double getCheckAmount()
	{
		return dCheckAmount;
	}

	/**
	 * @param d
	 */
	public void setCheckAmount(double d)
	{
		dCheckAmount = d;
	}
	public long getNLoanID() {
		return nLoanID;
	}
	public void setNLoanID(long loanID) {
		nLoanID = loanID;
	}

}
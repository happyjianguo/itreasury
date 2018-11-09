package com.iss.itreasury.bill.venture.dataentity;
import java.sql.Timestamp;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.bill.util.BillDataEntity;
/**
 * @author gqzhang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BlackQueryCondition extends BillDataEntity
{
	private long QueryBillTypeID = -1; //票据类型
	private String QueryCodeStart = ""; //票据编号
	private String QueryCodeEnd = ""; //票据编号
	private Timestamp QueryCreateDateStart = null; //出票日
	private Timestamp QueryCreateDateEnd = null; //出票日
	private Timestamp QueryEndDateStart = null; //到期日
	private Timestamp QueryEndDateEnd = null; //到期日	
	private double QueryAmounStart = 0.0; //票面金额
	private double QueryAmounEnd = 0.0; //票面金额	
	private String QueryAcceptorName = ""; //承兑人名称
	private long QueryStatusID = -1; //协防状态
	private long[] QueryStatusIDs = null; //协防状态
	private long QueryInputUserID = -1;
	private Timestamp QueryInputDateStart = null;
	private Timestamp QueryInputDateEnd = null;
	private long QueryOfficeID = -1; //办事处
	private long QueryCurrencyID = -1; //币种
	private long QueryOrderByParam = -1;
	private long QueryDesc = Constant.PageControl.CODE_ASCORDESC_ASC; //是否降序 	
	private long QueryPageCount = -1;//总页数
	private long QueryPageLineCount = -1;//每页记录数
	private long QueryPageNo = -1;//当前页

	/**
	 * Returns the queryAcceptorName.
	 * @return String
	 */
	public String getQueryAcceptorName()
	{
		return QueryAcceptorName;
	}

	/**
	 * Returns the queryAmounEnd.
	 * @return double
	 */
	public double getQueryAmounEnd()
	{
		return QueryAmounEnd;
	}

	/**
	 * Returns the queryAmounStart.
	 * @return double
	 */
	public double getQueryAmounStart()
	{
		return QueryAmounStart;
	}

	/**
	 * Returns the queryBillTypeID.
	 * @return long
	 */
	public long getQueryBillTypeID()
	{
		return QueryBillTypeID;
	}

	/**
	 * Returns the queryCodeEnd.
	 * @return String
	 */
	public String getQueryCodeEnd()
	{
		return QueryCodeEnd;
	}

	/**
	 * Returns the queryCodeStart.
	 * @return String
	 */
	public String getQueryCodeStart()
	{
		return QueryCodeStart;
	}

	/**
	 * Returns the queryCreateDateEnd.
	 * @return Timestamp
	 */
	public Timestamp getQueryCreateDateEnd()
	{
		return QueryCreateDateEnd;
	}

	/**
	 * Returns the queryCreateDateStart.
	 * @return Timestamp
	 */
	public Timestamp getQueryCreateDateStart()
	{
		return QueryCreateDateStart;
	}

	/**
	 * Returns the queryCurrencyID.
	 * @return long
	 */
	public long getQueryCurrencyID()
	{
		return QueryCurrencyID;
	}

	/**
	 * Returns the queryDesc.
	 * @return long
	 */
	public long getQueryDesc()
	{
		return QueryDesc;
	}

	/**
	 * Returns the queryEndDateEnd.
	 * @return Timestamp
	 */
	public Timestamp getQueryEndDateEnd()
	{
		return QueryEndDateEnd;
	}

	/**
	 * Returns the queryEndDateStart.
	 * @return Timestamp
	 */
	public Timestamp getQueryEndDateStart()
	{
		return QueryEndDateStart;
	}

	/**
	 * Returns the queryInputDateEnd.
	 * @return Timestamp
	 */
	public Timestamp getQueryInputDateEnd()
	{
		return QueryInputDateEnd;
	}

	/**
	 * Returns the queryInputDateStart.
	 * @return Timestamp
	 */
	public Timestamp getQueryInputDateStart()
	{
		return QueryInputDateStart;
	}

	/**
	 * Returns the queryInputUserID.
	 * @return long
	 */
	public long getQueryInputUserID()
	{
		return QueryInputUserID;
	}

	/**
	 * Returns the queryOfficeID.
	 * @return long
	 */
	public long getQueryOfficeID()
	{
		return QueryOfficeID;
	}

	/**
	 * Returns the queryOrderByParam.
	 * @return long
	 */
	public long getQueryOrderByParam()
	{
		return QueryOrderByParam;
	}

	/**
	 * Returns the queryStatusID.
	 * @return long
	 */
	public long getQueryStatusID()
	{
		return QueryStatusID;
	}

	/**
	 * Returns the queryStatusIDs.
	 * @return long[]
	 */
	public long[] getQueryStatusIDs()
	{
		return QueryStatusIDs;
	}

	/**
	 * Sets the queryAcceptorName.
	 * @param queryAcceptorName The queryAcceptorName to set
	 */
	public void setQueryAcceptorName(String queryAcceptorName)
	{
		QueryAcceptorName = queryAcceptorName;
	}

	/**
	 * Sets the queryAmounEnd.
	 * @param queryAmounEnd The queryAmounEnd to set
	 */
	public void setQueryAmounEnd(double queryAmounEnd)
	{
		QueryAmounEnd = queryAmounEnd;
	}

	/**
	 * Sets the queryAmounStart.
	 * @param queryAmounStart The queryAmounStart to set
	 */
	public void setQueryAmounStart(double queryAmounStart)
	{
		QueryAmounStart = queryAmounStart;
	}

	/**
	 * Sets the queryBillTypeID.
	 * @param queryBillTypeID The queryBillTypeID to set
	 */
	public void setQueryBillTypeID(long queryBillTypeID)
	{
		QueryBillTypeID = queryBillTypeID;
	}

	/**
	 * Sets the queryCodeEnd.
	 * @param queryCodeEnd The queryCodeEnd to set
	 */
	public void setQueryCodeEnd(String queryCodeEnd)
	{
		QueryCodeEnd = queryCodeEnd;
	}

	/**
	 * Sets the queryCodeStart.
	 * @param queryCodeStart The queryCodeStart to set
	 */
	public void setQueryCodeStart(String queryCodeStart)
	{
		QueryCodeStart = queryCodeStart;
	}

	/**
	 * Sets the queryCreateDateEnd.
	 * @param queryCreateDateEnd The queryCreateDateEnd to set
	 */
	public void setQueryCreateDateEnd(Timestamp queryCreateDateEnd)
	{
		QueryCreateDateEnd = queryCreateDateEnd;
	}

	/**
	 * Sets the queryCreateDateStart.
	 * @param queryCreateDateStart The queryCreateDateStart to set
	 */
	public void setQueryCreateDateStart(Timestamp queryCreateDateStart)
	{
		QueryCreateDateStart = queryCreateDateStart;
	}

	/**
	 * Sets the queryCurrencyID.
	 * @param queryCurrencyID The queryCurrencyID to set
	 */
	public void setQueryCurrencyID(long queryCurrencyID)
	{
		QueryCurrencyID = queryCurrencyID;
	}

	/**
	 * Sets the queryDesc.
	 * @param queryDesc The queryDesc to set
	 */
	public void setQueryDesc(long queryDesc)
	{
		QueryDesc = queryDesc;
	}

	/**
	 * Sets the queryEndDateEnd.
	 * @param queryEndDateEnd The queryEndDateEnd to set
	 */
	public void setQueryEndDateEnd(Timestamp queryEndDateEnd)
	{
		QueryEndDateEnd = queryEndDateEnd;
	}

	/**
	 * Sets the queryEndDateStart.
	 * @param queryEndDateStart The queryEndDateStart to set
	 */
	public void setQueryEndDateStart(Timestamp queryEndDateStart)
	{
		QueryEndDateStart = queryEndDateStart;
	}

	/**
	 * Sets the queryInputDateEnd.
	 * @param queryInputDateEnd The queryInputDateEnd to set
	 */
	public void setQueryInputDateEnd(Timestamp queryInputDateEnd)
	{
		QueryInputDateEnd = queryInputDateEnd;
	}

	/**
	 * Sets the queryInputDateStart.
	 * @param queryInputDateStart The queryInputDateStart to set
	 */
	public void setQueryInputDateStart(Timestamp queryInputDateStart)
	{
		QueryInputDateStart = queryInputDateStart;
	}

	/**
	 * Sets the queryInputUserID.
	 * @param queryInputUserID The queryInputUserID to set
	 */
	public void setQueryInputUserID(long queryInputUserID)
	{
		QueryInputUserID = queryInputUserID;
	}

	/**
	 * Sets the queryOfficeID.
	 * @param queryOfficeID The queryOfficeID to set
	 */
	public void setQueryOfficeID(long queryOfficeID)
	{
		QueryOfficeID = queryOfficeID;
	}

	/**
	 * Sets the queryOrderByParam.
	 * @param queryOrderByParam The queryOrderByParam to set
	 */
	public void setQueryOrderByParam(long queryOrderByParam)
	{
		QueryOrderByParam = queryOrderByParam;
	}

	/**
	 * Sets the queryStatusID.
	 * @param queryStatusID The queryStatusID to set
	 */
	public void setQueryStatusID(long queryStatusID)
	{
		QueryStatusID = queryStatusID;
	}

	/**
	 * Sets the queryStatusIDs.
	 * @param queryStatusIDs The queryStatusIDs to set
	 */
	public void setQueryStatusIDs(long[] queryStatusIDs)
	{
		QueryStatusIDs = queryStatusIDs;
	}

	/**
	 * Returns the queryPageCount.
	 * @return long
	 */
	public long getQueryPageCount()
	{
		return QueryPageCount;
	}

	/**
	 * Returns the queryPageLineCount.
	 * @return long
	 */
	public long getQueryPageLineCount()
	{
		return QueryPageLineCount;
	}

	/**
	 * Returns the queryPageNo.
	 * @return long
	 */
	public long getQueryPageNo()
	{
		return QueryPageNo;
	}

	/**
	 * Sets the queryPageCount.
	 * @param queryPageCount The queryPageCount to set
	 */
	public void setQueryPageCount(long queryPageCount)
	{
		QueryPageCount = queryPageCount;
	}

	/**
	 * Sets the queryPageLineCount.
	 * @param queryPageLineCount The queryPageLineCount to set
	 */
	public void setQueryPageLineCount(long queryPageLineCount)
	{
		QueryPageLineCount = queryPageLineCount;
	}

	/**
	 * Sets the queryPageNo.
	 * @param queryPageNo The queryPageNo to set
	 */
	public void setQueryPageNo(long queryPageNo)
	{
		QueryPageNo = queryPageNo;
	}

}
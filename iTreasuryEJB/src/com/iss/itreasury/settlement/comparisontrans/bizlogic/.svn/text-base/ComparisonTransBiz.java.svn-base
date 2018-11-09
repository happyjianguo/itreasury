package com.iss.itreasury.settlement.comparisontrans.bizlogic;

/**
 * @author yyhe Date 2007-01-26
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Stack;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IDate;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.ObjectUtil;
import com.iss.itreasury.settlement.comparisontrans.dao.ComparisonTransDAO;
import com.iss.itreasury.settlement.comparisontrans.dataentity.AccountInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.AccountTransInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.BankInfo;
import com.iss.itreasury.settlement.comparisontrans.dataentity.ComparisonTransInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo;
import com.iss.itreasury.settlement.util.UtilOperation;

public class ComparisonTransBiz
{

	private HashMap	settMap	= null;

	private HashMap	bankMap	= null;

	// private QueryTransactionInfo qtaInfo=null;

	public void querTransInfo(ComparisonTransInfo info) throws IException
	{

		System.out.println("go into com.iss.itreasury.settlement.comparisontrans.bizlogic.querTransInfo");
		// if(info.getBankAccountNo()==null || info.getBankAccountNo().trim().equals(""))
		// {
		// throw new IException("查询银行账户信息为空，请输入.");
		// }
		if (info.getSubjectCode() == null || info.getSubjectCode().trim().equals(""))
		{
			throw new IException("查询的科目信息为空,请输入.");
		}
		if (info.getStartDate() == null)
		{
			throw new IException("查询的开始日期为空，请输入.");
		}
		if (info.getEndDate() == null)
		{
			throw new IException("查询的结束日期为空,请输入.");
		}
		// 验证输入的信息是否正确
		   isValidDate(info);
		// 验证科目是否有对应的开户行信息
		  hasSettBankInfo(info);
		// 验证银行账户信息是否注册有效
		   hasBankportlInfo(info);
		settMap = findAllSettTrans(info);
		// ++++++++++++修改(根据选择此过渡开户行科目的银行总账户来查询其所对应的银企银行)
		try
		{
			if (info.getBankAccountNo() != null && !info.getBankAccountNo().trim().equals(""))
			{
				ComparisonTransDAO dao = new ComparisonTransDAO();
				AccountInfo acctInfo = dao.findBankidByAccountNo(info.getBankAccountNo());
				if (acctInfo != null)
				{
					info.setBankID(acctInfo.getBankId()); // 银行ID
				}
			}
			else
			{
				throw new IException("查询账户信息为空");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询账户信息出错");
		}
		// ++++++++++++修改结束
		bankMap = findAllBankTrans(info);
		// qtaInfo=findParameterByTransNo(qtaInfo.getTransNo());

	}

	/**
	 * 开户行电子辅助对账
	 * @param info
	 * @throws IException
	 */
	public void compareTransInfo(ComparisonTransInfo info) throws IException
	{

		System.out.println("go into com.iss.itreasury.settlement.comparisontrans.bizlogic.ComparisonTransBiz.compareTransInfo");
		if (info.getSubjectCode() == null || info.getSubjectCode().trim().equals(""))
		{
			throw new IException("查询的科目信息为空,请输入.");
		}
		if (info.getStartDate() == null)
		{
			throw new IException("查询的开始日期为空，请输入.");
		}
		if (info.getEndDate() == null)
		{
			throw new IException("查询的结束日期为空,请输入.");
		}
		// 验证输入的信息是否正确
		   isValidDate(info);
		// 验证科目是否有对应的开户行信息
		  hasSettBankInfo(info);
		// 验证银行账户信息是否注册有效
		   hasBankportlInfo(info);
		settMap = selectAllSettTrans(info);
		// ++++++++++++修改(根据选择此过渡开户行科目的银行总账户来查询其所对应的银企银行)
		try
		{
			if (info.getBankAccountNo() != null && !info.getBankAccountNo().trim().equals(""))
			{
				ComparisonTransDAO dao = new ComparisonTransDAO();
				AccountInfo acctInfo = dao.findBankidByAccountNo(info.getBankAccountNo());
				if (acctInfo != null)
				{
					info.setBankID(acctInfo.getBankId()); // 银行ID
				}
			}
			else
			{
				throw new IException("查询账户信息为空");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询账户信息出错");
		}
		bankMap = selectAllBankTrans(info);

	}

	// 验证输入的信息是否正确
	private void isValidDate(ComparisonTransInfo info) throws IException
	{

		Date curDate = null;

		if (IDate.compareDate(info.getStartDate(), info.getEndDate()) > 0)
		{
			throw new IException("指定查询日期条件起始日期必须早于等于截至日期");
		}
		curDate = Env.getSystemDate(info.getOfficeID(), info.getCurrencyID());
		if (IDate.compareDate(info.getEndDate(), curDate) > 0)
		{
			throw new IException("指定查询日期条件结束日期不能晚于系统当前日期");
		}
	}

	// 验证科目是否有对应的开户行信息
	private void hasSettBankInfo(ComparisonTransInfo info) throws IException
	{

		ComparisonTransDAO dao = new ComparisonTransDAO();
		try
		{
			ComparisonTransInfo tempInfo = dao.querySettBankAcctInfo(info);
			if (tempInfo == null)
			{
				throw new IException("输入的科目信息无效，请重新输入");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("查询科目信息时，出现SQL异常");
		}

	}

	// 验证银行账户信息是否注册有效
	private void hasBankportlInfo(ComparisonTransInfo info) throws IException
	{

		ComparisonTransDAO dao = new ComparisonTransDAO();
		try
		{
			ComparisonTransInfo tempInfo = dao.queryBankportalAcctInfo(info);
			if (tempInfo == null)
			{
				throw new IException("输入的银行账户信息无效，请重新输入");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new IException("查询银行账户信息时，出现SQL异常");
		}
	}

	// 查找科目交易信息 -- 结算信息
	private HashMap findAllSettTrans(ComparisonTransInfo info) throws IException
	{

		System.out.println("enter into findAllSettTrans method");
		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			ComparisonTransDAO dao = new ComparisonTransDAO();
			aList = dao.queryAllSettTrans(info);
			if (aList != null && aList.size() > 0)
			{
				ComparisonTransInfo[] trans = null;
				trans = (ComparisonTransInfo[]) aList.toArray(new ComparisonTransInfo[0]);
				trans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(trans, "getDtexecute", true);
				transMap = convertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询科目信息表出错.");
		}
		return transMap;
	}
	/**
	 * 查询结算交易对账信息
	 * @param info
	 * @return
	 * @throws IException
	 */
	private HashMap selectAllSettTrans(ComparisonTransInfo info) throws IException
	{

		System.out.println("enter into selectAllSettTrans method");
		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			ComparisonTransDAO dao = new ComparisonTransDAO();
			aList = dao.selectAllSettTrans(info);
			if (aList != null && aList.size() > 0)
			{
				ComparisonTransInfo[] trans = null;
				trans = (ComparisonTransInfo[]) aList.toArray(new ComparisonTransInfo[0]);
				//trans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(trans, "getDtexecute", true);
				transMap = compareConvertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询科目信息表出错.");
		}
		return transMap;
	}
	// 查找银行帐户交易信息 -- 资金监控信息
	private HashMap findAllBankTrans(ComparisonTransInfo info) throws IException
	{

		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			ComparisonTransDAO dao = new ComparisonTransDAO();
			aList = dao.queryAllBankTrans(info);
			if (aList != null && aList.size() > 0)
			{
				ComparisonTransInfo[] trans = null;
				trans = (ComparisonTransInfo[]) aList.toArray(new ComparisonTransInfo[0]);
				trans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(trans, "getDtexecute", true);
				transMap = convertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询银行账户信息表出错.");
		}
		return transMap;
	}
	/**
	 *  查找银行帐户交易信息 -- 资金监控信息
	 * @param info
	 * @return
	 * @throws IException
	 */
	private HashMap selectAllBankTrans(ComparisonTransInfo info) throws IException
	{

		HashMap transMap = null;
		try
		{
			ArrayList aList = null;
			ComparisonTransDAO dao = new ComparisonTransDAO();
			aList = dao.selectAllBankTrans(info);
			if (aList != null && aList.size() > 0)
			{
				ComparisonTransInfo[] trans = null;
				trans = (ComparisonTransInfo[]) aList.toArray(new ComparisonTransInfo[0]);
				//trans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(trans, "getDtexecute", true);
				transMap = compareConvertInfo(trans);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询银行账户信息表出错.");
		}
		return transMap;
	}
	// 将结果集转换为按时间为key的hashMap
	private HashMap convertInfo(ComparisonTransInfo[] trans) throws Exception
	{

		HashMap transMap = null;
		if (trans != null && trans.length > 0)
		{
			transMap = new HashMap();
			Date tempDate = null;
			ArrayList transList = null;
			for (int i = 0; i < trans.length; i++)
			{
				ComparisonTransInfo info = trans[i];
				if (tempDate == null)
				{
					tempDate = info.getDtexecute();
					transList = new ArrayList();
				}
				if (IDate.compareDate(tempDate, info.getDtexecute()) == 0)
				{
					transList.add(info);
				}
				else
				{
					transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), convertStack(transList));
					transList = new ArrayList();
					tempDate = info.getDtexecute();
					transList.add(info);
				}
			}
			if (trans != null && trans.length > 0)
			{
				transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), convertStack(transList));
			}
		}
		return transMap;
	}
	/**
	 * 将结果集转换为按时间为key的hashMap
	 * @param trans
	 * @return
	 * @throws Exception
	 */
	private HashMap compareConvertInfo(ComparisonTransInfo[] trans) throws Exception
	{

		HashMap transMap = null;
		if (trans != null && trans.length > 0)
		{
			transMap = new HashMap();
			Date tempDate = null;
			ArrayList transList = null;
			for (int i = 0; i < trans.length; i++)
			{
				ComparisonTransInfo info = trans[i];
				if (tempDate == null)
				{
					tempDate = info.getDtexecute();
					transList = new ArrayList();
				}
				if (IDate.compareDate(tempDate, info.getDtexecute()) == 0)
				{
					transList.add(info);
				}
				else
				{
					transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), compareConvertStack(transList));
					transList = new ArrayList();
					tempDate = info.getDtexecute();
					transList.add(info);
				}
			}
			if (trans != null && trans.length > 0)
			{
				transMap.put(DataFormat.formatDate(tempDate, DataFormat.FMT_DATE_SPECIAL), compareConvertStack(transList));
			}
		}
		return transMap;
	}
	// 将ComparisonTransInfo[]对象转换为堆栈对象
	private Stack convertStack(ArrayList list) throws Exception
	{

		Stack tempStack = null;
		if (list != null && list.size() > 0)
		{
			tempStack = new Stack();
			ComparisonTransInfo[] tempTrans = (ComparisonTransInfo[]) list.toArray(new ComparisonTransInfo[0]);
			tempTrans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(tempTrans, "getAmount", true);
			for (int i = 0; i < tempTrans.length; i++)
			{
				tempStack.push(tempTrans[i]);
			}
		}
		return tempStack;
	}
	/**
	 * 将ComparisonTransInfo[]对象转换为堆栈对象
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Stack compareConvertStack(ArrayList list) throws Exception
	{

		Stack tempStack = null;
		if (list != null && list.size() > 0)
		{
			tempStack = new Stack();
			ComparisonTransInfo[] tempTrans = (ComparisonTransInfo[]) list.toArray(new ComparisonTransInfo[0]);
			//tempTrans = (ComparisonTransInfo[]) ObjectUtil.sortObjectByField(tempTrans, "getAmount", true);
			for (int i = tempTrans.length-1;i>=0; i--)
			{
				tempStack.push(tempTrans[i]);
			}
		}
		return tempStack;
	}
	/**
	 * 
	 * 提供两个数的比较 by Huang Ye
	 * 
	 * @param d1 需要比较的数字1
	 * @param d2 需要比较的数字2
	 * @param scale 与之比较的数字的小数位数
	 * 
	 * @return 是否相等
	 * 
	 */
	public static boolean equal(double d1, double d2, int scale)
	{

		if (Double.isNaN(d1) || Double.isNaN(d2))
		{
			return false;
		}

		double difference = Math.abs(d1 - d2);
		double minNum = Math.pow(10, (-1) * scale);
		if (difference < minNum)
			return true;
		else
			return false;
	}

	// d1>d2 return true
	public static boolean getLessNum(double d1, double d2)
	{

		if (Double.isNaN(d1) && Double.isNaN(d2))
		{
			return false;
		}
		if (Double.isNaN(d1) && !Double.isNaN(d2))
		{
			return false;
		}
		if (!Double.isNaN(d1) && Double.isNaN(d2))
		{
			return true;
		}
		if (!Double.isNaN(d1) && !Double.isNaN(d2))
		{
			if (d1 < d2)
			{
				return true;
			}
		}
		return false;
	}

	public static long intervalDays(Date dtBeginDate, Date dtEndDate)
	{

		GregorianCalendar gc1, gc2;

		gc1 = new GregorianCalendar();
		gc1.setTime(dtBeginDate);
		gc2 = new GregorianCalendar();
		gc2.setTime(dtEndDate);

		gc1.clear(Calendar.MILLISECOND);
		gc1.clear(Calendar.SECOND);
		gc1.clear(Calendar.MINUTE);
		gc1.clear(Calendar.HOUR_OF_DAY);
		gc1.clear(Calendar.HOUR);

		gc2.clear(Calendar.MILLISECOND);
		gc2.clear(Calendar.SECOND);
		gc2.clear(Calendar.MINUTE);
		gc2.clear(Calendar.HOUR_OF_DAY);
		gc2.clear(Calendar.HOUR);

		long lInterval = 0;
		lInterval = gc2.getTime().getTime() - gc1.getTime().getTime();
		lInterval = lInterval / (24 * 60 * 60 * 1000);
		return lInterval;
	}

	/**
	 * 获得该日期指定天数之前的日期
	 * 
	 * @param dtDate
	 * @param lDays
	 * @return 返回日期
	 */
	public static Date before(Date dtDate, long lDays)
	{

		long lCurrentDate = 0;
		lCurrentDate = dtDate.getTime() - lDays * 24 * 60 * 60 * 1000;
		Date dtBefor = new Date(lCurrentDate);
		return dtBefor;
	}

	public static Date parseDate(String strDate, int nFmt) throws Exception
	{

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		switch (nFmt)
		{
			default :
			case DataFormat.FMT_DATE_YYYYMMDD :
				dateFormat.applyPattern("yyyy-MM-dd");
				break;
		}
		return dateFormat.parse(strDate);
	}

	public HashMap getBankMap()
	{

		return bankMap;
	}

	public void setBankMap(HashMap bankMap)
	{

		this.bankMap = bankMap;
	}

	public HashMap getSettMap()
	{

		return settMap;
	}

	public void setSettMap(HashMap settMap)
	{

		this.settMap = settMap;
	}

	/**
	 * 功能：返回查看电子对帐详细信息所需参数
	 * 
	 * @param TransNo
	 * @return
	 * @throws IException
	 */
	public static QueryTransactionInfo findParameterByTransNo(String TransNo) throws IException
	{

		System.out.println("enter into findParameterByTransNo method");
		QueryTransactionInfo qtInfo = null;
		try
		{
			 qtInfo = UtilOperation.findParameterByTransNo(TransNo);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询电子对帐详细信息所需参数出错.");
		}
		return qtInfo;
	}

	/**
	 * 功能：返回电子对帐详细信息中账户信息及AccountID
	 * 
	 * @param Transnoofbank
	 * @return
	 * @throws IException
	 */
	public static AccountTransInfo findBankAccountNoByTransnoofbank(String Transnoofbank) throws IException
	{

		System.out.println("enter into findBankAccountNoByTransnoofbank method");
		AccountTransInfo transInfo = null;
		try
		{
			// System.out.println("*******************************Transnoofbank%%%%%%%%%%%%%%%%%%%%%%%%%%%%" +
			// Transnoofbank);
			transInfo = ComparisonTransDAO.findBankAccountNoByTransnoofbank(Transnoofbank);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("电子对帐详细信息中账户信息及AccountID出错.");
		}
		return transInfo;
	}

	/**
	 * 功能：返回电子对帐详细信息所需BankID
	 * 
	 * @param AccountID
	 * @return
	 * @throws IException
	 */
	public static AccountInfo findBankidByAccountid(long AccountID) throws IException
	{

		System.out.println("enter into findBankidByAccountid method");
		AccountInfo acctInfo = null;
		try
		{
			acctInfo = ComparisonTransDAO.findBankidByAccountid(AccountID);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询电子对帐详细信息所需BankID出错.");
		}
		return acctInfo;
	}

	/**
	 * 功能：返回电子对帐详细信息所需银行信息
	 * 
	 * @param BankID
	 * @return
	 * @throws IException
	 */
	public static BankInfo findBankByBankID(long BankID) throws IException
	{

		System.out.println("enter into findBankByBankID method");
		BankInfo bankInfo = null;
		try
		{
			bankInfo = ComparisonTransDAO.findBankByBankID(BankID);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("查询电子对帐详细信息所需银行信息出错.");
		}
		return bankInfo;
	}

	public static void main(String[] args) throws Exception
	{/*
		 * String startDate="2006-12-04"; String endDate="2006-12-04"; ComparisonTransInfo info=new
		 * ComparisonTransInfo(); info.setStartDateString(startDate); info.setEndDateString(endDate);
		 * info.setBankAccountNo("0200080129027399930"); info.setSubjectCode("100202"); ComparisonTransBiz biz=new
		 * ComparisonTransBiz(); biz.querTransInfo(info); HashMap settMap=biz.getSettMap(); HashMap
		 * bankMap=biz.getBankMap(); long
		 * intervalDays=ComparisonTransBiz.intervalDays(info.getStartDate(),info.getEndDate()); Date tempDate=null;
		 * for(int i=0;i<=intervalDays;i++) { //首先显示日期行,从最近一天开始往前推 if(tempDate==null) { tempDate=info.getEndDate(); }
		 * else { tempDate=ComparisonTransBiz.before(tempDate,1); } Stack settStack=null; if(settMap!=null) {
		 * settStack=(Stack)settMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL));
		 * System.out.println("settStack="+settStack.size()); } Stack bankStack=null; if(bankMap!=null) {
		 * bankStack=(Stack)bankMap.get(DataFormat.formatDate(tempDate,DataFormat.FMT_DATE_SPECIAL));
		 * System.out.println("bankStack="+bankStack.size()); } if(settStack==null || settStack.empty()) {
		 * if(bankStack!=null && !bankStack.empty()) { ComparisonTransInfo bankInfo=null; while(!bankStack.empty()) {
		 * bankInfo=(ComparisonTransInfo)settStack.pop(); System.out.println("****************");
		 * System.out.println("bankStack="+bankStack.size()); } } } else if(bankStack==null || bankStack.empty()) {
		 * if(settStack!=null && !settStack.empty()) { ComparisonTransInfo settInfo=null; while(!settStack.empty()) {
		 * settInfo=(ComparisonTransInfo)settStack.pop(); System.out.println("****************");
		 * System.out.println("settStack="+settStack.size()); } } } else { ComparisonTransInfo bankInfo=null;
		 * ComparisonTransInfo settInfo=null; while(!settStack.empty() || !bankStack.empty()) { if(!settStack.empty()) {
		 * System.out.print("settStack.size="+settStack.size()); } if(!bankStack.empty()) {
		 * System.out.println(",bankStack.size="+bankStack.size()); } if(settInfo==null) { if(!settStack.empty()) {
		 * settInfo=(ComparisonTransInfo)settStack.pop(); } } if(bankInfo==null) { if(!bankStack.empty()) {
		 * bankInfo=(ComparisonTransInfo)bankStack.pop(); } } //比较金额的大小
		 * if(ComparisonTransBiz.equal(settInfo.getAmount(),bankInfo.getAmount(),2)) { //相等 if(!settStack.empty()) {
		 * settInfo=(ComparisonTransInfo)settStack.pop(); } if(!bankStack.empty()) {
		 * bankInfo=(ComparisonTransInfo)bankStack.pop(); } } else
		 * if(getLessNum(settInfo.getAmount(),bankInfo.getAmount())) { //sett>bank
		 * System.out.println("********sett>bank*********"); if(!settStack.empty()) {
		 * settInfo=(ComparisonTransInfo)settStack.pop(); } } else
		 * if(getLessNum(bankInfo.getAmount(),settInfo.getAmount())) { //sett<bank System.out.println("********sett<bank*********");
		 * if(!bankStack.empty()) { bankInfo=(ComparisonTransInfo)bankStack.pop(); } } if(settStack.empty()) {
		 * //settStack=null; settInfo=new ComparisonTransInfo(); } if(bankStack.empty()) { //bankStack=null;
		 * bankInfo=new ComparisonTransInfo(); } }//End while } }
		 */

	}
}

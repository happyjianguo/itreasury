/*
 * Created on 2003-9-8
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package com.iss.itreasury.settlement.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletRequest;

import com.iss.itreasury.codingrule.bizlogic.CreateCodeBiz;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.extendapply.dataentity.ExtendContractInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;
import com.iss.itreasury.loan.loanpaynotice.dataentity.PayNoticeRateInfo;
import com.iss.itreasury.loan.overdueapply.dataentity.OverDueInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.settlement.account.dao.Sett_AccountTypeDAO;
import com.iss.itreasury.settlement.account.dataentity.AccountTypeInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestsInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryTransactionInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code
 * and Comments
 */
public class UtilOperation extends SettlementDAO
{
	Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	/**
	 *  
	 */
	public UtilOperation()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	/**
		 *  
		 */
	public UtilOperation(Connection transConn)
	{
		super(transConn);
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
		UtilOperation utilOperation = new UtilOperation();
		try {
			utilOperation.getOpenDepositNo(322);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 方法说明：生成并返回交易号
	 * 
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return @throws
	 *         SQLException
	 */
	public String getNewTransactionNo(long lOfficeID, long lCurrencyID) throws SQLException
	{
		String strTransNo = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select to_number(scode) scode from office where id="+lOfficeID+" ");
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			long lScode = -1;
			if (rs.next())
			{
				lScode = rs.getLong("scode");
			}
			cleanup(rs);
			cleanup(ps);
			//
			String strSequenceName = "SEQ_TRANS" + DataFormat.formatInt(lScode, 2) + DataFormat.formatInt(lCurrencyID, 2);
			// 从sequence中取得当天交易的序列号。
			sb.setLength(0);
			sb.append("select " + strSequenceName + ".nextval nextid from dual");
			log.info(sb.toString());
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			long lSerial = -1;
			if (rs.next())
			{
				lSerial = rs.getLong("nextid");
			}
			cleanup(rs);
			cleanup(ps);
			//
			// 取得开机时间。
			sb.setLength(0);
			sb.append("select to_char(dtopendate,'yyyymmdd') opendate from sett_officetime where nofficeid=" + lOfficeID + " and ncurrencyid=" + lCurrencyID);
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			String strOpenDate = "";
			if (rs.next())
			{
				strOpenDate = rs.getString("opendate");
			}
			// 交易号分为4段： ： 日期：20030919； 办事处scode：01 ；币种：01 ；当天序列号：00001
			strTransNo = strOpenDate + DataFormat.formatInt(lScode, 2) + DataFormat.formatInt(lCurrencyID, 2) + DataFormat.formatInt(lSerial, 5);
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return strTransNo;
	}
	
	/**
	 * 方法说明：生成并返回交易号(新)
	 * add by zcwang 2007-8-2
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return @throws
	 *         SQLException
	 */
	public String getNewTransactionNo(long lOfficeID, long lCurrencyID,long transTypeID) throws IException, Exception
	{
		String strTransNo = "";
		HashMap map = new HashMap();
		try
		{
			map.put("officeID",String.valueOf(lOfficeID));
			map.put("currencyID",String.valueOf(lCurrencyID));
			map.put("moduleID",String.valueOf(Constant.ModuleType.SETTLEMENT));
			map.put("transTypeID",String.valueOf(transTypeID));
			strTransNo=CreateCodeManager.createCode(map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return strTransNo;
	}	
	/**
	 * 方法说明：生成并返回交易号(新)
	 * add by xwhe 2009-3-5
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return @throws
	 *         SQLException
	 */
	public String getNewTransactionNo(Connection con,long lOfficeID, long lCurrencyID,long transTypeID) throws IException, Exception
	{
		String strTransNo = "";
		HashMap map = new HashMap();
		try
		{
			map.put("officeID",String.valueOf(lOfficeID));
			map.put("currencyID",String.valueOf(lCurrencyID));
			map.put("moduleID",String.valueOf(Constant.ModuleType.SETTLEMENT));
			map.put("transTypeID",String.valueOf(transTypeID));
			map.put("connection",con);
			strTransNo=CreateCodeManager.createCode(map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		return strTransNo;
	}	
	/**get next n day's timesamp
	 * @param n>0 means get furture day, else means get past day
	*/
	public static Timestamp getNextNDay(Timestamp currentDay, int n)
	{
		/*GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDay);
		calendar.add(Calendar.DATE, n);
		java.util.Date resDate = calendar.getTime();
		return new Timestamp(resDate.getTime());*/
		return DataFormat.getNextDate(currentDay, n);
	}
	/**
	 * 判断状态是否正确
	 * 
	 * @param 页面操作类型，页面传过来的状态，后台取到的状态
	 * @return boolean false 状态不匹配
	 * @throws java.rmi.RemoteException
	 * @throws IRollbackException
	 */
	public static String checkStatus(long lStatusID, long lNewStatusID, long lActionID)
	{
		String rtnStr = "";
		//修改保存
		if (lActionID == SETTConstant.Actions.MODIFYSAVE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE && lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				//已被复核
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
			}
			else
			{
				//已被复核
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
			}
		}
		//修改暂存
		if (lActionID == SETTConstant.Actions.MODIFYTEMPSAVE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				//已被复核
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
				//已被保存
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E130";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E130";
				}
			}
		}
		//删除
		if (lActionID == SETTConstant.Actions.DELETE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE && lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E018";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E019";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E018";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E019";
				}
			}
		}
		//复核
		if (lActionID == SETTConstant.Actions.CHECK)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE)
			{
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E022";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E021";
				}

			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E022";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E021";
				}
			}
		}
		//取消复核
		if (lActionID == SETTConstant.Actions.CANCELCHECK)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CHECK)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E023";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E025";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E023";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E025";
				}
			}
		}
		//勾账
		if (lActionID == SETTConstant.Actions.SQUAREUP)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CHECK)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E045";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E046";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CIRCLE)
				{
					return "Sett_E052";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E045";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E046";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.CIRCLE)
				{
					return "Sett_E052";
				}
			}
		}
		//取消勾账
		if (lActionID == SETTConstant.Actions.CANCELSQUAREUP)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CIRCLE)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E048";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E049";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E050";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E048";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E049";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E050";
				}
			}
		}

		return rtnStr;
	}
	/**
	 * 方法说明：对付款方的客户加锁，使其不能用于其它业务的付款。
	 * 
	 * @param lClientID
	 * @return @throws
	 *         SQLException
	 */
	public long waitUtilSuccessLock(long lClientID) throws SQLException
	{
		long lReturn = -1;
		return lReturn;
	}
	/**
	 * 方法说明：将锁定的客户解锁
	 * 
	 * @param lClientID
	 * @param lSessionID
	 * @return @throws
	 *         SQLException
	 */
	public long releaseLock(long lClientID, long lSessionID) throws SQLException
	{
		long lReturn = -1;
		return lReturn;
	}
	/**
	 * 方法说明：判断客户是否被指定的SessionID锁定
	 * 
	 * @param lClientID
	 * @param lSessionID
	 * @return @throws
	 *         SQLException
	 */
	public boolean isLock(long lClientID, long lSessionID) throws SQLException
	{
		boolean bReturn = false;
		return bReturn;
	}
	/**
	 * 方法说明：判断客户是否被锁定
	 * 
	 * @param lClientID
	 * @return @throws
	 *         SQLException
	 */
	public boolean isLock(long lClientID) throws SQLException
	{
		boolean bReturn = false;
		return bReturn;
	}

	/**
	 * 根据账户ID，获得存单号（定期/通知/保证金存款开立时使用）
	 * 
	 * @param lAccountID
	 *            账户ID
	 * @return @throws
	 *         Exception
	 */
	public String getOpenDepositNo(long lAccountID) throws Exception
	{
		String strReturn = "";
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lAccountTypeID = -1;
		String clientCode = "";
		StringBuffer buffer = new StringBuffer();
		try
		{
			buffer.append("select nAccountTypeID from sett_Account where ID=?");
			ps = con.prepareStatement(buffer.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lAccountTypeID = rs.getLong("nAccountTypeID");
			}
			cleanup(rs);
			cleanup(ps);

			if (lAccountID > 0) {
				clientCode = NameRef.getClientCodeByAccountID(lAccountID);
			}
			int clientLength = clientCode.length();

			buffer = null;
			buffer = new StringBuffer();
			buffer.append("select nvl(max(sequence),1) as sequence from \n");
			buffer.append("(select max(substr(sa.AF_sDepositNo,"+(clientLength+2)+",6)) as sequence  \n");
			buffer.append("from  SETT_SUBACCOUNT sa,sett_Account a where sa.nAccountID=a.ID and sa.nAccountID=? and a.nAccountTypeID=" + lAccountTypeID + "  \n");
			if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
			{
				buffer.append("union select max(substr(sDepositNo,"+(clientLength+2)+",6)) as sequence \n");
				buffer.append("from SETT_TRANSOPENFIXEDDEPOSIT where nTransactionTypeID=" + SETTConstant.TransactionType.OPENFIXEDDEPOSIT + " \n");
				buffer.append("union select max(substr(sNewDepositNo,"+(clientLength+2)+",6)) as sequence \n");
				buffer.append("from SETT_TRANSFIXEDCONTINUE \n");
			}
			else if (SETTConstant.AccountType.isMarginAccountType(lAccountTypeID))
			{
				buffer.append("union select max(substr(sDepositNo,"+(clientLength+2)+",6)) as sequence \n");
				buffer.append("from sett_transopenmargindeposit where nTransactionTypeID=" + SETTConstant.TransactionType.OPENMARGIN + " \n");
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				buffer.append("union select max(substr(sDepositNo,"+(clientLength+2)+",6)) as sequence \n");
				buffer.append("from SETT_TRANSOPENFIXEDDEPOSIT where nTransactionTypeID=" + SETTConstant.TransactionType.OPENNOTIFYACCOUNT + " \n");
			}
			buffer.append(") \n");

			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lAccountID);

			rs = ps.executeQuery();
			if (rs.next())
			{
				strReturn = rs.getString("sequence");
				int sequence = Integer.valueOf(strReturn).intValue();
				sequence++;
				strReturn = DataFormat.formatInt(sequence, 6);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try {
				buffer = null;
				buffer = new StringBuffer();
				buffer.append("select nvl(max(sequence),1) as sequence from \n");
				buffer.append("(select count(sa.id) as sequence  \n");
				buffer.append("from  SETT_SUBACCOUNT sa,sett_Account a where sa.nAccountID=a.ID and sa.nAccountID=? and a.nAccountTypeID=" + lAccountTypeID + "  \n");
				if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
				{
					buffer.append("union select count(id) as sequence \n");
					buffer.append("from SETT_TRANSOPENFIXEDDEPOSIT where nTransactionTypeID=" + SETTConstant.TransactionType.OPENFIXEDDEPOSIT + " \n");
					buffer.append("union select count(id) as sequence \n");
					buffer.append("from SETT_TRANSFIXEDCONTINUE \n");
				}
				else if (SETTConstant.AccountType.isMarginAccountType(lAccountTypeID))
				{
					buffer.append("union select count(id) as sequence \n");
					buffer.append("from sett_transopenmargindeposit where nTransactionTypeID=" + SETTConstant.TransactionType.OPENMARGIN + " \n");
				}
				else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
				{
					buffer.append("union select count(id) as sequence \n");
					buffer.append("from SETT_TRANSOPENFIXEDDEPOSIT where nTransactionTypeID=" + SETTConstant.TransactionType.OPENNOTIFYACCOUNT + " \n");
				}
				buffer.append(") \n");
	
				ps = con.prepareStatement(buffer.toString());
				int index = 1;
				ps.setLong(index++, lAccountID);
	
				rs = ps.executeQuery();
				if (rs.next())
				{
					strReturn = rs.getString("sequence");
					int sequence = Integer.valueOf(strReturn).intValue();
					sequence++;
					strReturn = DataFormat.formatInt(sequence, 6);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			try
			{
				if (strReturn != null && !"".equals(strReturn))
				{
					if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
					{	
						strReturn = "D" + clientCode + strReturn;
					}
					else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
					{
						strReturn = "T" + clientCode + strReturn;
					}	
					else if (SETTConstant.AccountType.isMarginAccountType(lAccountTypeID))
					{
						strReturn = "B" + clientCode + strReturn;
					}
				}
				else
				{
					if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
					{
						strReturn = "D" + clientCode + "000001";
					}
					else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
					{
						strReturn = "T" + clientCode + "000001";
					}
					else if (SETTConstant.AccountType.isMarginAccountType(lAccountTypeID))
					{
						strReturn = "B" + clientCode + "000001";
					}
				}
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return strReturn;
	}
	
	/**
	 * 后台根据编码规则获取存单号（定期/通知/保证金存款开立时使用）
	 * 
	 * @param lAccountID
	 *            账户ID
	 * @return @throws
	 *         Exception
	 */
	public String getOpenDepositNoBackGround(long lAccountID) throws Exception
	{
		String strReturn = "";
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lAccountTypeID = -1;
		long lOfficeID = -1;
		long lCurrencyID = -1;
		long lClientID = -1;
		long lModuleID = Constant.ModuleType.SETTLEMENT;
		String clientCode = "";
		StringBuffer buffer = new StringBuffer();
		try
		{
			buffer.append("select nclientid,nofficeid,ncurrencyid,nclientid,nAccountTypeID from sett_Account where ID=?");
			ps = con.prepareStatement(buffer.toString());
			ps.setLong(1, lAccountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lAccountTypeID = rs.getLong("nAccountTypeID");
				lOfficeID = rs.getLong("nofficeid");
				lCurrencyID = rs.getLong("ncurrencyid");
				lClientID = rs.getLong("nclientid");
			}
			cleanup(rs);
			cleanup(ps);
			
			//构造编码规则参数
			HashMap paraMap = new HashMap();
			paraMap.put("officeID", String.valueOf(lOfficeID));
			paraMap.put("currencyID", String.valueOf(lCurrencyID));
			paraMap.put("moduleID", String.valueOf(lModuleID));
			paraMap.put("clientID", String.valueOf(lClientID));
			if (SETTConstant.AccountType.isFixAccountType(lAccountTypeID))
			{	
				paraMap.put("transTypeID", String.valueOf(SETTConstant.CodingRuleTransactionType.FIXEDDEPOSITRECEIPT));
			}
			else if (SETTConstant.AccountType.isNotifyAccountType(lAccountTypeID))
			{
				paraMap.put("transTypeID", String.valueOf(SETTConstant.CodingRuleTransactionType.NOTIFYACCOUNTRECEIPT));
			}	
			else if (SETTConstant.AccountType.isMarginAccountType(lAccountTypeID))
			{
				paraMap.put("transTypeID", String.valueOf(SETTConstant.CodingRuleTransactionType.MARGINRECEIPT));
			}
			//流水号根据帐户分，借用放款通知单根据合同分的字段
			paraMap.put("contractID", String.valueOf(lAccountID));
			
			//获取编码
			CreateCodeBiz createCodeBiz = new CreateCodeBiz(con);
			strReturn = createCodeBiz.creatCode(paraMap);
			
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
			throw new IException("获取编码出错：获取流水号出错");
		}
		finally
		{
			cleanup(con);
		}
		return strReturn;
	}
	
	public static String dataentityToString(Object dataentity)
	{

		StringBuffer res = new StringBuffer(dataentity.getClass().getName() + " \n");
		Method[] methods = dataentity.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method tmp = methods[i];
			String mName = tmp.getName();
			if (mName.startsWith("get"))
			{
				String fName = mName.substring(3);
				res.append(fName + " = ");
				try
				{
					Object o = tmp.invoke(dataentity, new Object[]{});
					if (o == null)
					{
						res.append(" null \n");
						continue;
					}
					if (o instanceof Double)
					{
						res.append("" + ((Double) o).doubleValue() + " \n");
					}
					if (o instanceof Float)
					{
						res.append("" + ((Float) o).floatValue() + " \n");
					}
					else if (o instanceof Long)
					{
						res.append("" + ((Long) o).longValue() + " \n");
					}
					else if (o instanceof String)
					{
						res.append("" + (String) o + " \n");
					}
					else if (o instanceof Timestamp)
					{
						res.append("" + ((Timestamp) o).toString() + " \n");
					}
					else
						continue;
				}
				catch (IllegalAccessException e)
				{
					continue;
				}
				catch (InvocationTargetException e)
				{
					continue;
				}
			}

		}
		return res.toString();
	}

	/**
	 * 
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
	 * 
	 * 确的浮点数运算，包括加减乘除和四舍五入。 From CSDN
	 *  
	 */
	static public class Arith
	{

		//默认除法运算精度
		private static final int DEF_DIV_SCALE = 10;

		/**
		 * 
		 * 提供精确的加法运算。
		 * 
		 * @param v1
		 *            被加数
		 * 
		 * @param v2
		 *            加数
		 * 
		 * @return 两个参数的和
		 *  
		 */
		public static double add(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.add(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的减法运算。
		 * 
		 * @param v1
		 *            被减数
		 * 
		 * @param v2
		 *            减数
		 * 
		 * @return 两个参数的差
		 *  
		 */
		public static double sub(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.subtract(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的乘法运算。
		 * 
		 * @param v1
		 *            被乘数
		 * 
		 * @param v2
		 *            乘数
		 * 
		 * @return 两个参数的积
		 *  
		 */
		public static double mul(double v1, double v2)
		{

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.multiply(b2).doubleValue();

		}

		/**
		 * 
		 * 提供精确的连乘运算。
		 * 
		 * @param inputs
		 *            连乘参数
		 * 
		 * @return 积
		 *  
		 */
		public static double mul(double[] inputs)
		{

			double res = 0.0;
			for (int i = 0; i < inputs.length - 1; i++)
			{
				double v1 = 0.0;
				double v2 = 0.0;
				if (i == 0)
					v1 = inputs[i];
				else
					v1 = res;
				v2 = inputs[i + 1];
				BigDecimal b1 = new BigDecimal(Double.toString(v1));

				BigDecimal b2 = new BigDecimal(Double.toString(v2));

				res = b1.multiply(b2).doubleValue();
			}
			return res;

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
		 * 
		 * 小数点以后10位，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * 
		 * @param v2
		 *            除数
		 * 
		 * @return 两个参数的商
		 *  
		 */
		public static double div(double v1, double v2)
		{

			return div(v1, v2, DEF_DIV_SCALE);

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。
		 * 
		 * @param v1
		 *            被除数
		 * 
		 * @param v2
		 *            除数
		 * 
		 * @param scale
		 *            表示表示需要精确到小数点以后几位。
		 * 
		 * @return 两个参数的商
		 *  
		 */
		public static double div(double v1, double v2, int scale)
		{

			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}

			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}

		/**
		 * 
		 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
		 * 
		 * 定精度，以后的数字四舍五入。
		 * 
		 * @param inputs
		 *            连除参数，必须保证顺序
		 * 
		 * @param scale
		 *            表示表示需要精确到小数点以后几位。
		 * 
		 * @return 参数的商
		 * 
		 * @author Add by Huang Ye
		 *  
		 */
		public static double div(double[] inputs, int scale)
		{
			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}
			double res = 0.0;
			for (int i = 0; i < inputs.length - 1; i++)
			{
				double v1 = 0.0;
				double v2 = 0.0;
				if (i == 0)
					v1 = inputs[i];
				else
					v1 = res;
				v2 = inputs[i + 1];
				BigDecimal b1 = new BigDecimal(Double.toString(v1));

				BigDecimal b2 = new BigDecimal(Double.toString(v2));

				res = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			return res;

		}
		/**
		 * 
		 * 提供精确的小数位四舍五入处理。
		 * 
		 * @param v
		 *            需要四舍五入的数字
		 * 
		 * @param scale
		 *            小数点后保留几位
		 * 
		 * @return 四舍五入后的结果
		 *  
		 */
		public static double round(double v, int scale)
		{

			if (scale < 0)
			{

				throw new IllegalArgumentException("The scale must be a positive integer or zero");

			}

			BigDecimal b = new BigDecimal(Double.toString(v));

			BigDecimal one = new BigDecimal("1");

			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

		}

		/**
		 * 
		 * 提供两个数的比较　by Huang Ye
		 * 
		 * @param d1            需要比较的数字1
		 * @param d2            需要比较的数字2
		 * @param scale
		 *            与之比较的数字的小数位数
		 * 
		 * @return 是否相等
		 *  
		 */
		public static boolean equal(double d1, double d2, int scale)
		{
			double difference = Math.abs(d1 - d2);
			double minNum = Math.pow(10, (-1) * scale);
			if (difference < minNum)
				return true;
			else
				return false;
		}

	}
	/**
	* 计算实际的活期利率
	* @param lOfficeID:
	*            办事处 
	* @param lInterestPlanID:
	*            利率计划标识
	* @param dBalance:
	*            金额
	* @param tsDate:
	*            计算日期
	* @param lDays:
	*            持续天数
	*/
	public static double getCurrentInterestRate(Connection conn, long lOfficeID, long lInterestPlanID, double dBalance, Timestamp tsDate, long lDays) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bIsPassCheck = true;
		String strSQL = "";
		double dInterestRate = 0;
		boolean bCloseConnection = false;
		try
		{
			if (conn == null)
			{
				conn = Database.getConnection();
				bCloseConnection = true;
			}
			long lInterestPlanTypeID = -1; //利率计划类型
			strSQL = "select * from sett_InterestRatePlan where id=" + lInterestPlanID + " and NOFFICEID = " + lOfficeID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lInterestPlanTypeID = rs.getLong("nInterestPlanTypeID");
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			switch ((int) lInterestPlanTypeID)
			{
				case (int) SETTConstant.InterestRatePlanType.BALANCE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.nInterestRatePlanID=? order by a.mBanlance desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AVERAGE :
				{
					strSQL =
						"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.nInterestRatePlanID=? order by a.mBanlance desc ";
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, tsDate);
					ps.setDouble(2, dBalance);
					ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
					ps.setDouble(4, dBalance);
					ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
					ps.setLong(6, lInterestPlanID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dInterestRate = rs.getDouble("InterestRate");
					}
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					break;
				}				
				case (int) SETTConstant.InterestRatePlanType.DATE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and a.dtDateStart<=?  and a.dtDateEnd>=? and a.nInterestRatePlanID=?  ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setTimestamp(2, tsDate);
						ps.setTimestamp(3, tsDate);
						ps.setLong(4, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.DAYS :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.NDayCount>? and a.NDayType=?)  or (a.NDayCount<=? and a.NDayType=?)) and a.nInterestRatePlanID=? order by a.NDayCount desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setLong(2, lDays);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setLong(4, lDays);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DATE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate,b.dtEffective from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.dtDateStart<=?  and a.dtDateEnd>=? and a.nInterestRatePlanID=?  and b.dtEffective<=a.dtDateStart order by a.mBanlance desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setTimestamp(6, tsDate);
						ps.setTimestamp(7, tsDate);
						ps.setLong(8, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DAYS :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and ((a.NDayCount>? and a.NDayType=?)  or (a.NDayCount<=? and a.NDayType=?)) and a.nInterestRatePlanID=? order by a.fInterestRate desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lDays);
						ps.setLong(7, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setLong(8, lDays);
						ps.setLong(9, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(10, lInterestPlanID);
						//  Common.log("查找Balance and Days利率的语句："+strSQL);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				default :
					break;
			}
			if (bCloseConnection)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return dInterestRate;
	}
	/**
	 * 计算实际的活期利率
	 * @param lOfficeID:
	 *            办事处 
	 * @param lInterestPlanID:
	 *            利率计划标识
	 * @param dBalance:
	 *            金额
	 * @param tsDate:
	 *            计算日期
	 * @param lDays:
	 *            持续天数
	 */
	public static double getCurrentInterestRate(Connection conn, long lInterestPlanID, double dBalance, Timestamp tsDate, long lDays) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bIsPassCheck = true;
		String strSQL = "";
		double dInterestRate = 0;
		boolean bCloseConnection = false;
		try
		{
			if (conn == null)
			{
				conn = Database.getConnection();
				bCloseConnection = true;
			}
			long lInterestPlanTypeID = -1; //利率计划类型
			strSQL = "select * from sett_InterestRatePlan where id=" + lInterestPlanID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lInterestPlanTypeID = rs.getLong("nInterestPlanTypeID");
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			switch ((int) lInterestPlanTypeID)
			{
				case (int) SETTConstant.InterestRatePlanType.BALANCE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.nInterestRatePlanID=? order by a.mBanlance desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.DATE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and a.dtDateStart<=?  and a.dtDateEnd>=? and a.nInterestRatePlanID=?  ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setTimestamp(2, tsDate);
						ps.setTimestamp(3, tsDate);
						ps.setLong(4, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.DAYS :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.NDayCount>? and a.NDayType=?)  or (a.NDayCount<=? and a.NDayType=?)) and a.nInterestRatePlanID=? order by a.NDayCount desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setLong(2, lDays);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setLong(4, lDays);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DATE :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.dtDateStart<=?  and a.dtDateEnd>=? and a.nInterestRatePlanID=? order by a.mBanlance desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setTimestamp(6, tsDate);
						ps.setTimestamp(7, tsDate);
						ps.setLong(8, lInterestPlanID);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AND_DAYS :
					{
						strSQL =
							"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and ((a.NDayCount>? and a.NDayType=?)  or (a.NDayCount<=? and a.NDayType=?)) and a.nInterestRatePlanID=? order by a.fInterestRate desc ";
						ps = conn.prepareStatement(strSQL);
						ps.setTimestamp(1, tsDate);
						ps.setDouble(2, dBalance);
						ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setDouble(4, dBalance);
						ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(6, lDays);
						ps.setLong(7, SETTConstant.InterestRatePlanIntemType.UNDER);
						ps.setLong(8, lDays);
						ps.setLong(9, SETTConstant.InterestRatePlanIntemType.OVER);
						ps.setLong(10, lInterestPlanID);
						//  Common.log("查找Balance and Days利率的语句："+strSQL);
						rs = ps.executeQuery();
						if (rs.next())
						{
							dInterestRate = rs.getDouble("InterestRate");
						}
						if (rs != null)
						{
							rs.close();
							rs = null;
						}
						if (ps != null)
						{
							ps.close();
							ps = null;
						}
						break;
					}
				case (int) SETTConstant.InterestRatePlanType.BALANCE_AVERAGE :
				{
					strSQL =
						"select b.fRate+a.fInterestRate InterestRate from sett_InterestRatePlanItem a,(select a.ID,a.fRate from sett_InterestRate a,(select ID, max(dtEffective) dtEffective  from sett_interestrate where dtEffective <= ? group by  ID) b where a.ID = b.ID and a.dtEffective = b.dtEffective ) b where a.nInterestRateID = b.ID(+) and ((a.mBanlance>? and a.NBalanceType=?)  or (a.mBanlance<=? and a.NBalanceType=?))  and a.nInterestRatePlanID=? order by a.mBanlance desc ";
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, tsDate);
					ps.setDouble(2, dBalance);
					ps.setLong(3, SETTConstant.InterestRatePlanIntemType.UNDER);
					ps.setDouble(4, dBalance);
					ps.setLong(5, SETTConstant.InterestRatePlanIntemType.OVER);
					ps.setLong(6, lInterestPlanID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						dInterestRate = rs.getDouble("InterestRate");
					}
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					break;
				}
				default :
					break;
			}
			if (bCloseConnection)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return dInterestRate;
	}
	/**
	 * 得到贷款执行利率，参数lLoanID和lContractID必传入一个，不传入的话请设置为-1。
	 * 
	 * @param lLoanID：
	 *            贷款ID
	 * @param lContractID：
	 *            合同ID
	 * @param tsDate：
	 *            时间，如传入为NULL值或空串则默认为当前日期。
	 * @return double： 贷款执行利率
	 * @exception Exception
	 */
	public static double getLoanInterestRate(Connection con, long lLoanID, long lContractID, Timestamp tsDate) throws Exception
	{
		double dResult = 100;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bCloseConnection = false;
		try
		{
			if (con == null)
			{
				con = Database.getConnection();
				bCloseConnection = true;
			}

			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(con);
			}

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT b.mrate FROM loan_rateAdjustContractDetail a ,loan_interestRate b  ");
			sbSQL.append(" WHERE a.nContractID = ?  ");
			sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd')<= TO_CHAR(?,'yyyymmdd')");
			sbSQL.append(" and a.nBankInterestID = b.id(+) ");
			sbSQL.append(" ORDER BY  a.dtStartDate DESC ");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lContractID);
			ps.setTimestamp(2, tsDate);
			rs = ps.executeQuery();
			System.out.println("********************SQL"+sbSQL.toString());	
			if (rs.next())
			{
				dResult = rs.getDouble("mRate");
			}
			else if (lLoanID > 0)
			{
				sbSQL.setLength(0);
				sbSQL.append("SELECT mInterestRate FROM loan_loanform WHERE id = ? ");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lLoanID);
				rs = ps.executeQuery();
					
				if (rs.next())
				{
					dResult = rs.getDouble("mInterestRate");
				}
			}
			else if (lContractID > 0 && lLoanID <= 0)
			{
				sbSQL.setLength(0);
				sbSQL.append(" SELECT mInterestRate ");
				sbSQL.append(" FROM loan_contractForm ");
				sbSQL.append(" WHERE 1 = 1 ");
				sbSQL.append(" AND id = ?");						
				ps = con.prepareStatement(sbSQL.toString());				
				ps.setLong(1, lContractID);
				rs = ps.executeQuery();
				
				if (rs.next())
				{
					dResult = rs.getDouble("mInterestRate");
				}
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (bCloseConnection)
			{
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (bCloseConnection)
				{
					if (con != null)
					{
						con.close();
						con = null;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}
				
		
		return dResult;
	}

	/**
	 * 根据放款通知单取利率
	 * 刘惠军 2003-11-20
	 * 该方法拷贝于com.iss.itreasury.loan.loanpaynotice.dao下的public PayNoticeRateInfo getRateValue()方法
	 * 返回类型做了变化
	 * @param con
	 * @param lRateTypeID
	 * 	public static final long INTEREST = 1; //贷款利率
		public static final long CHARGE = 2; //委托手续费率
		public static final long FINE = 3; //罚息利率
		public static final long ASSURE = 4; //担保费率
		public static final long AGENT = 5; //代理费率
	 * @param lLoanPayNoticeID
	 * @param tsDate
	 * @return
	 * @throws IException
	 * 修改时间：2004-07-08 增加了固定浮动点，另外，浮动利率不在取自合同。
	 * 修改：2005-03-25 重拷com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao.getRateValue方法
	 */
	public PayNoticeRateInfo getRateValue(long lRateTypeID, long lLoanPayNoticeID, Timestamp tsDate) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		StringBuffer sbSQL = new StringBuffer();
		boolean bIsHaveLate = true;
		PayNoticeRateInfo info = new PayNoticeRateInfo();		
		long lInterestTypeID = -1;
		String strRate = "";
		
		try
		{
			con = Database.getConnection();
			
			if (tsDate == null || tsDate.equals(""))
			{
				tsDate = DataFormat.getDateTime(con);
			}
			
			switch ((int) lRateTypeID)
			{
				//利率
				case (int) Constant.RateType.INTEREST :
				    if (lLoanPayNoticeID > 0)
					{
						sbSQL.setLength(0);
						sbSQL.append(" SELECT b.nInterestTypeID,b.nLiborRateID,nvl(b.mInterestRate,0) mInterestRate,b.nBankInterestID,nvl(b.mAdjustRate,0) mAdjustRate, ");
						sbSQL.append(" nvl(b.mStaidAdjustRate,0) mStaidAdjustRate,nvl(c.mRate,nvl(b.mInterestRate,0)) mRate,d.LiborName,d.IntervalNum, nvl(b.overDueAdjustRate,0) overDueAdjustRate, nvl(b.overDueStaidAdjustRate,0) overDueStaidAdjustRate, b.isRemitOverDueInterest ");
						sbSQL.append(" FROM loan_payForm b,loan_interestRate c,loan_liborInterestRate d ");
						sbSQL.append(" WHERE 1 = 1 ");
						sbSQL.append(" AND nvl(b.nBankInterestID,-1) = c.ID(+) ");
						sbSQL.append(" AND nvl(b.nLiborRateID,-1) = d.ID(+) ");
						sbSQL.append(" AND b.ID = ? ");
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						rs = ps.executeQuery();
						if (rs.next())
						{
						    //利率类型
							lInterestTypeID = rs.getLong("nInterestTypeID");
							//未调整的利率	
							info.setBasicRate(rs.getDouble("mRate"));
							//未调整的基准利率
							info.setBankInterestID(rs.getLong("nBankInterestID"));
							//未调整的利率ID
							info.setRate(info.getBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
							info.setAdjustRate(rs.getDouble("mAdjustRate"));
							info.setStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
							//Libor利率ID
							info.setLiborRateID(rs.getLong("nLiborRateID"));
							//Libor利率名称
							info.setLiborName(rs.getString("LiborName"));
							//Libor利率期限
							info.setLiborIntervalNum(rs.getLong("IntervalNum"));
							
							//modify by leiyang3 罚息利率
							info.setOverDueAdjustRate(rs.getDouble("overDueAdjustRate"));
							info.setOverDueStaidAdjustRate(rs.getDouble("overDueStaidAdjustRate"));
							info.setIsRemitOverDueInterest(rs.getLong("isRemitOverDueInterest"));
							
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
					}
				    else
					{
					    return null;
					}
					
					if (lInterestTypeID == LOANConstant.InterestRateType.BANK)
					{
						sbSQL.setLength(0);
						sbSQL.append(" SELECT a.dtStartDate, nvl(b.mRate,0) mRate,nvl(a.mrate,0) as newrate, b.ID, nvl(a.mStaidAdjustRate,0) mStaidAdjustRate, nvl(a.mAdjustRate,0) mAdjustRate ");
						sbSQL.append(" FROM loan_RateAdjustPayDetail a,loan_InterestRate b ");
						sbSQL.append(" WHERE a.nLoanPayNoticeID = ? ");
						
						////modified by mzh_fu 2007/07/19 增加条件 status != Constant.RecordStatus.INVALID
						sbSQL.append(" AND a.status != " + Constant.RecordStatus.INVALID);
						
						sbSQL.append(" AND TO_CHAR(a.dtStartDate,'yyyymmdd') <= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND a.nBankInterestID = b.id(+) ");
						sbSQL.append(" ORDER BY a.dtStartDate DESC ");
						logger.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						ps.setTimestamp(2, tsDate);
						rs = ps.executeQuery();
						if (rs.next())
						{
							info.setLateBasicRate(rs.getDouble("mRate")); //调整后的基准利率
							//如果是委托贷款利率调整，因为无利率ID，需要取loan_rateadjustpaydetail中的mrate
							if(info.getLateBasicRate()==0.0){
								info.setLateBasicRate(rs.getDouble("newrate"));
							}
							info.setLateRate(info.getLateBasicRate() * (1 + rs.getDouble("mAdjustRate") / 100) + rs.getDouble("mStaidAdjustRate"));
							info.setLateAdjustRate(rs.getDouble("mAdjustRate"));
							//调整后的利率
							info.setLateStaidAdjustRate(rs.getDouble("mStaidAdjustRate"));
							//调整后的基准利率ID
							info.setLateBankInterestID(rs.getLong("ID"));
							//调整生效时间
							info.setAdjustDate(rs.getTimestamp("dtStartDate"));
							
							//modify by leiyang3 罚息利率
							if(info.getIsRemitOverDueInterest() == 0)
							{
								info.setOverDueInterestRate(info.getLateRate());
							}
							else
							{
								info.setOverDueInterestRate(info.getLateRate() * (1 + info.getOverDueAdjustRate() / 100) + info.getOverDueStaidAdjustRate());
							}
						}
						else
						{
							bIsHaveLate = false;
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;

						//如果利率未调整过，取未调整的原始利率值
						if (!bIsHaveLate)
						{
							info.setLateBankInterestID(info.getBankInterestID());
							info.setLateBasicRate(info.getBasicRate());
							info.setLateAdjustRate(info.getAdjustRate());
							info.setLateStaidAdjustRate(info.getStaidAdjustRate());
							info.setLateRate(info.getRate());
							info.setLateRateString(DataFormat.formatRate(info.getLateRate()));
							
							//modify by leiyang3 罚息利率
							if(info.getIsRemitOverDueInterest() == 0)
							{
								info.setOverDueInterestRate(info.getLateRate());
							}
							else
							{
								info.setOverDueInterestRate(info.getLateRate() * (1 + info.getOverDueAdjustRate() / 100) + info.getOverDueStaidAdjustRate());
							}
						}
					}
					else if (lInterestTypeID == LOANConstant.InterestRateType.LIBOR)
					{
					    strRate = info.getLiborName();
					    if (info.getAdjustRate() < 0)
					    {
					        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
					    }
					    else if (info.getAdjustRate() > 0)
					    {
					        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getAdjustRate()));
					    }
					    if (info.getStaidAdjustRate() < 0)
					    {
					        strRate = strRate + " - " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
					    }
					    else if (info.getStaidAdjustRate() > 0)
					    {
					        strRate = strRate + " + " + DataFormat.formatRate(java.lang.Math.abs(info.getStaidAdjustRate()));
					    }
					    info.setLateRateString(strRate);
					    //Libor利率通知
					    sbSQL.setLength(0);
						sbSQL.append(" SELECT nvl(a.LiborRate,0) LiborRate ");
						sbSQL.append(" FROM Loan_LiborInform a ");
						sbSQL.append(" WHERE a.PayNoticeID = ? ");
						sbSQL.append(" AND TO_CHAR(a.InterestStart,'yyyymmdd') <= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND TO_CHAR(a.InterestEnd,'yyyymmdd') >= TO_CHAR(?,'yyyymmdd') ");
						sbSQL.append(" AND a.StatusID = " + LOANConstant.RecordStatus.VALID);
						sbSQL.append(" ORDER BY a.ID DESC ");
						logger.info(sbSQL.toString());
						ps = con.prepareStatement(sbSQL.toString());
						ps.setLong(1, lLoanPayNoticeID);
						ps.setTimestamp(2, tsDate);
						ps.setTimestamp(3, tsDate);
						rs = ps.executeQuery();
						if (rs.next())
						{
						    info.setLateBasicRate(rs.getDouble("LiborRate"));	//Libor利率值
						    info.setLateRate(info.getLateBasicRate() * (1 + info.getAdjustRate() / 100) + info.getStaidAdjustRate());
						}
						rs.close();
						rs = null;
						ps.close();
						ps = null;
					}
					//针对委托贷款
					else
					{
						info.setLateBankInterestID(info.getBankInterestID());
						info.setLateBasicRate(info.getBasicRate());
						info.setLateAdjustRate(info.getAdjustRate());
						info.setLateStaidAdjustRate(info.getStaidAdjustRate());
						info.setLateRate(info.getRate());
						info.setLateRateString(DataFormat.formatRate(info.getLateRate()));
					}
					
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					info.setInterestRate(info.getLateRate());
					info.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.INTEREST));
					info.setRateType(Constant.RateType.INTEREST);			
					break;
				case (int) Constant.RateType.AGENT :
					break;
				case (int) Constant.RateType.ASSURE :
					break;
				case (int) Constant.RateType.CHARGE :
					sbSQL.setLength(0);
					sbSQL.append(" SELECT a.mChargeRate FROM loan_contractform a,loan_payform b ");
					sbSQL.append(" where a.ID = b.nContractID and b.ID = ? ");
					logger.info(sbSQL.toString());
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lLoanPayNoticeID);
					rs = ps.executeQuery();
					if (rs.next())
					{
					    info.setInterestRate(rs.getDouble("mChargeRate"));
					}
					if (rs != null)
					{
						rs.close();
						rs = null;
					}
					if (ps != null)
					{
						ps.close();
						ps = null;
					}
					info.setRateStyle(Constant.RateType.getRateStyle(Constant.RateType.CHARGE));
					info.setRateType(Constant.RateType.CHARGE);
					break;
				case (int) Constant.RateType.FINE :
					break;
				default :
					break;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			logger.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				logger.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}

	/**
	 * 根据放款通知单ID返回罚息对象
	 * @author hjliu
	 * 2003-11-30
	 * @param conn 外部调用者传入的数据库连接，不可为空！！
	 * @param loanNoteID 放款通知单ID
	 * @return OverDueInfo
	 * @throws IException 
	 */
	public OverDueInfo getOverDueInfoByLoanNoteID(long loanNoteID) throws IException
	{
		//log.info("lhj debug info == 放款通知单ID是：" + loanNoteID);
		OverDueInfo overDueInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			StringBuffer sqlString = new StringBuffer();
			//select * from LOAN_OVERDUEFORM
			sqlString.append(" select * from LOAN_OVERDUEFORM \n");
			sqlString.append(" where nStatusID = 3 and NPAYFORMID = \n");//modify by ninh 2005-06-21 逾期没有审核完成则不能罚息
			sqlString.append(" " + loanNoteID);

			ps = conn.prepareStatement(sqlString.toString());
			//log.info("lhj debug info == 查询语句是:" + sqlString.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				overDueInfo = new OverDueInfo();
				//放款通知单ID
				overDueInfo.setLoanPayID(loanNoteID);
				//标示
				overDueInfo.setID(rs.getLong("ID"));
				//逾期编号
				//overDueInfo.setOverDueApplyCode(rs.getString(""));
				//合同标示
				overDueInfo.setContractID(rs.getLong("NCONTRACTID"));
				//合同编号
				//overDueInfo.setContractCode(rs.getString(""));
				//合同执行计划ID
				overDueInfo.setPlanID(rs.getLong("NPLANID"));
				//合同执行计划版本号ID
				//overDueInfo.setPlanVersionID(rs.getLong(""));
				//金额
				//overDueInfo.setAmount(rs.getDouble(""));
				//计划余额
				overDueInfo.setPlanBalance(rs.getDouble("MPLANBALANCE"));
				//计划日期
				//overDueInfo.setPlanDate(rs.getTimestamp(""));
				//罚息金额
				overDueInfo.setFineAmount(rs.getDouble("MFINEAMOUNT"));
				//罚息利率
				overDueInfo.setFineInterestRate(rs.getDouble("MFINEINTERESTRATE"));
				//是否复利
				overDueInfo.setIsCompoundInterest(rs.getLong("NISCOMPOUNDINTEREST"));
				//罚息日期
				overDueInfo.setFineDate(rs.getTimestamp("DTFINEDATE"));
				//逾期原因
				overDueInfo.setOverDueReason(rs.getString("SOVERDUEREASON"));
				//录入人的ID号码
				overDueInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				//逾期状态ID
				overDueInfo.setStatusID(rs.getLong("NSTATUSID"));

			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的贷款罚息记录，交易失败", null);

		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);

			}
			catch (Exception e)
			{
				throw new IException("");
			}

		}

		return overDueInfo;
	}
	
	/**
	 * 根据放款通知单ID查找贷款展期的记录。
	 * @param loanNoteID
	 * @return
	 * @throws IException
	 */
	public ExtendContractInfo getExtContractInfoByLoanNoteID(long loanNoteID,Timestamp tsDate) throws IException
	{
		//log.info(" debug info == 放款通知单ID是：" + loanNoteID);
		ExtendContractInfo extContractInfo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			StringBuffer sqlString = new StringBuffer();
			//select * from LOAN_OVERDUEFORM
			sqlString.append(" select * from LOAN_EXTENDCONTRACT \n");
			sqlString.append(" where nStatusID = 3 and NPAYFORMID = \n");//modify by ninh 2005-06-21 逾期没有审核完成则不能罚息
			sqlString.append(" " + loanNoteID);

			ps = conn.prepareStatement(sqlString.toString());
			//log.info("lhj debug info == 查询语句是:" + sqlString.toString());
			rs = ps.executeQuery();

			while (rs.next())
			{
				extContractInfo = new ExtendContractInfo();
				

			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		catch (SQLException sqlE)
		{
			sqlE.printStackTrace();
			throw new IException(true, "无法找到对应的贷款逾期记录，交易失败", null);

		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);

			}
			catch (Exception e)
			{
				throw new IException("");
			}

		}

		return extContractInfo;
	}


	/**
	*
	* 得到合同详细信息 copy package of "com.iss.itreasury.loan.contract.dao"
	* Create Date: 2003-10-15
	* @param lID 合同ID
	* @return ContractInfo 合同详细信息
	* @exception Exception
	*/
	public ContractInfo findContractByID(long lID) throws IException
	{
		ContractInfo info = null;
		PreparedStatement ps = null;
		//		PreparedStatement ps2 = null;
		//		ResultSet rs2 = null;
		String strSQL = "";
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = getConnection();
			StringBuffer sbSQL = new StringBuffer();
			//		

			sbSQL.append(" select * from loan_contractForm where id = " + lID);
			// sbSQL.append(" where a.nloanId = b.id and a.id = " + lID); 
			//log.info(sbSQL.toString());

			ps = con.prepareStatement(sbSQL.toString());
			//ps.setLong(1, lID);

			rs = ps.executeQuery();

			if (rs.next())
			{

				info = new ContractInfo();
				info.setLoanID(rs.getLong("nLoanID")); //贷款ID
				info.setContractID(rs.getLong("id")); //合同ID
				info.setLoanTypeID(rs.getLong("NTYPEID")); //贷款类型
				info.setContractCode(rs.getString("SCONTRACTCODE")); //合同编号
				//				info.setApplyCode(rs.getString("sApplyCode")); //申请书编号
				//				info.setBorrowClientID(rs.getLong("nBorrowClientID")); //借款单位ID
				//				info.setBorrowClientName(rs.getString("sClientName")); //借款单位
				//				info.setBorrowClientCode(rs.getString("sClientCode")); //客户编号
				//				info.setLoanAmount(rs.getDouble("mLoanAmount")); //借款金额
				//				info.setIntervalNum(rs.getLong("nIntervalNum")); //期限
				//				RateInfo rInfo = new RateInfo();
				//				rInfo = getLatelyRate(-1, info.getContractID(), null);
				//				info.setInterestRate(rInfo.getLateRate()); //执行利率
				//				info.setRate(rInfo.getRate());	//调整前利率
				//				info.setBasicInterestRate(rInfo.getLateBasicRate()); //基准利率
				//				info.setBankInterestID(rInfo.getLateBankInterestID());//银行利率ID
				//				info.setAdjustRate(rs.getDouble("mAdjustRate")); //利率调整
				//				info.setLoanStart(rs.getTimestamp("dtStartDate")); //合同起始日期
				//				info.setLoanEnd(rs.getTimestamp("dtEndDate")); //合同结束日期
				//				info.setLoanPurpose(rs.getString("sLoanPurpose")); //借款用途
				//				info.setExamineAmount(rs.getDouble("mExamineAmount")); //审批金额
				//				info.setAssureAmount(rs.getDouble("AssureAmount")); //有担保贷款总额
				info.setChargeRate(rs.getDouble("MCHARGERATE")); //手续费率
				info.setChargeRateType(rs.getLong("NCHARGERATETYPEID")); //手续费率类型
				//				info.setClientID(rs.getLong("nConsignClientID")); //委托单位

			}

			cleanup(rs);
			cleanup(ps);
			cleanup(con);

		}
		catch (Exception e)
		{
			log.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				log.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		////log.info("**********************ok retrun");
		return info;

	}

	/**
		* 得到执行利率，参数lLoanPayNoticeID
		* Create Date: 2003-10-15
		* @param lLoanPayNoticeID 放款通知单ID
		* @param tsDate 时间，如传入为NULL值或空串则默认为当前日期。
		* @return double 执行利率
		* @exception Exception
		*/
	public double getLatelyRate(long lLoanPayNoticeID, Timestamp tsDate) throws IException
	{
		PayNoticeRateInfo payNoticeRateInfo = new PayNoticeRateInfo();
		payNoticeRateInfo = this.getRateValue(Constant.RateType.INTEREST, lLoanPayNoticeID, tsDate);
		if(payNoticeRateInfo == null){
			return 0.0;
		}else{
			return payNoticeRateInfo.getInterestRate();
		}
	}

	/**
	 * lhj 由com.iss.itreasury.loan.loanpaynotice.bizlogic.LoanPayNoticeEJB拷贝
	 * 2003-11-21
	 * 
	 * 查找放款通知单
	 * @param lLoanPayNoticeID 放款通知单标识
	 */
	public LoanPayNoticeInfo findLoanPayNoticeByID(long lLoanPayNoticeID) throws IException
	{
		LoanPayNoticeInfo info = new LoanPayNoticeInfo();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//LoanPayNoticeDao dao = new LoanPayNoticeDao();
		//boolean CloseConnection = false;
		try
		{

			conn = getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.sContractCode,");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");

			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.id = ? and a.nAccountBankID = j.id(+)");

			ps = conn.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, lLoanPayNoticeID);
			//log.info(sb.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				info.setID(rs.getLong("ID"));
				info.setContractCode(rs.getString("sContractCode"));
				info.setContractID(rs.getLong("NCONTRACTID"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//已发放金额info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(getLatelyRate(lLoanPayNoticeID, null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				info.setIsRemitCompoundInterest(rs.getLong("isRemitCompoundInterest"));
				info.setIsRemitOverDueInterest(rs.getLong("isRemitOverDueInterest"));
				////log.info("%%%%%%%% info.getOutDate()="+info.getOutDate());
			}
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			sb.setLength(0);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new IException("");
			}
		}
		return info;
	}
	
	/**
	 * 判断传入日期是否为结息日
	 * 
	 * @param time
	 * @return
	 * @throws IException
	 */
	public boolean isClearInterestDay(long subAccountId, Timestamp date) throws IException
	{
		boolean isClearInterestDay = false;
		PreparedStatement ps = null;
		Connection conn =null;
		ResultSet rset = null;
		StringBuffer sql = new StringBuffer();
		
		try
		{
			conn =getConnection();
			sql.append(" select a.*                                                 \n");
			sql.append("   from SETT_CLEARINTERESTPLAN a,loan_payform b, loan_contractform c , sett_subaccount        d  \n");
			sql.append("    where a.statusid = 1                                    \n");
			sql.append("  and  b.ncontractid = c.id \n");
			sql.append("  and d.al_nloannoteid = b.id \n");
			sql.append("  and  c.nsubtypeid = a.subloantype \n");
			sql.append(" and ((case                                                    "); 
			sql.append("       when b.dtend <= to_date('"+DataFormat.formatDate(date)+"', 'yyyy-mm-dd') then ");
			sql.append("        a.overduetype                                          ");
			sql.append("     end) = "+SETTConstant.ClearInterestPlayOverDueType.OVERDUE+" or (case  ");
			sql.append("       when b.dtend > to_date('"+DataFormat.formatDate(date)+"', 'yyyy-mm-dd') then");
			sql.append("        a.overduetype                                          ");
			sql.append("     end) = "+SETTConstant.ClearInterestPlayOverDueType.COMMON+" )       ");
			sql.append(" and ((case                                                   ");
			sql.append("       when c.nintervalnum <= 6 then                          ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.SIXMOUTH_BELOW+" or (case ");
			sql.append("       when c.nintervalnum > 6 and c.nintervalnum <= 12 then  ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.SIXMOUTH_ONEYEAR+" or (case ");
			sql.append("       when c.nintervalnum > 12 and c.nintervalnum <= 36 then ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.ONEYEAR_THREEYEAR+" or (case  ");
			sql.append("       when c.nintervalnum > 36 and c.nintervalnum <= 60 then ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.THREEYEAR_FIVEYEAR+" or (case   ");
			sql.append("       when c.nintervalnum > 60 then                          ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.FIVEYEAR_ABOVE+" or a.loantermtypeid = -1)                       ");
			sql.append(" and d.id = "+subAccountId);
			sql.append(" order by a.loantermtypeid desc ");
			
			ps = conn.prepareStatement(sql.toString());
			rset = ps.executeQuery();
			
			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(date);
			int i=0;
			while(rset.next())
			{
				i++;
				if(rset.getLong("cleartype")==SETTConstant.ClearInterestPlayClearType.MONTH)//月
				{
					if(rset.getLong("cleartime")==calendar.get(Calendar.DAY_OF_MONTH))
					{
						isClearInterestDay = true;
					}
				}
				else if(rset.getLong("cleartype")==SETTConstant.ClearInterestPlayClearType.SEASON)//季
				{
					if(rset.getLong("cleartime")==calendar.get(Calendar.DAY_OF_MONTH)&&(calendar.get(Calendar.MONTH)+1)%3==0)
					{
						isClearInterestDay = true;
					}
				}
				break;
			}
			if(i==0)
			{
				StringBuffer sqltemp = new StringBuffer();
				String temp ="";
				sqltemp.append(" select u.*                                                      \n");   
				sqltemp.append("   from loan_contractform u, loan_payform h, sett_subaccount y   \n");
				sqltemp.append("  where 1 = 1                                                    \n");
				sqltemp.append("    and y.al_nloannoteid = h.id                                  \n");
				sqltemp.append("    and h.ncontractid = u.id                                     \n");
				sqltemp.append("    and y.id = "+subAccountId+"                                             \n");
				ps = conn.prepareStatement(sqltemp.toString());
				rset = ps.executeQuery();
				while(rset.next())
				{
					temp = rset.getString("scontractcode");
				}
				throw new IException("贷款合同"+temp+"没有设置结息计划");
			}
		}
		catch(Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try 
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isClearInterestDay;
	}
	
	/**
	 * 返回时间段内是否有结息日
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws IException
	 */
	public ArrayList findClearInterestPlan(Timestamp start, Timestamp end) throws IException
	{
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{

			conn = getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" select a.cleartime                                       \n");
			sb.append("   from sett_ClearInterestplan a                          \n");
			sb.append("    and a.statusid = 1                                    \n");
			sb.append("    and a.cleartime >= to_date('"+ DataFormat.formatDate(start) +"', 'yyyy-mm-dd') \n");
			sb.append("    and a.cleartime <= to_date('"+DataFormat.formatDate(end)+"', 'yyyy-mm-dd') \n");
			
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();

			if (rs.next())
			{
				list.add(rs.getTimestamp("cleartime"));
			}
			
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
			sb.setLength(0);

		}
		catch (Exception e)
		{
			//				log4j.error(e.toString());
			throw new IException("");
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);
			}
			catch (Exception e)
			{
				throw new IException("");
			}
		}
		if(list.size() == 0)
		{
			return null;
		}
		return list;
		
	}
	
	/**author Barry
	 * 2003-12-18
	 * 把dataentity转换成Attribute置入request
	 */
	public static void setDataentityToRequest(ServletRequest request, Object o) throws IException
	{

		if (request == null)
		{
			throw new IException("传入的request对象为空");
		}
		if (o == null)
		{
			throw new IException("传入的dataEntity为空");
		}

		Method[] methods = o.getClass().getDeclaredMethods();

		String strKey = null;
		String strValue = null;
		try
		{
			for (int n = 0; n < methods.length; n++)
			{
				strKey = methods[n].getName();

				if (strKey.startsWith("get"))
				{
					strKey = strKey.substring("get".length());
					Object result = methods[n].invoke(o, new Object[]{});
					if (result instanceof Long)
					{
						strKey = "l" + strKey;
						strValue = "" + ((Long) result).longValue();
					}
					else if (result instanceof Float)
					{
						strKey = "f" + strKey;
						strValue = "" + ((Float) result).floatValue();
					}
					else if (result instanceof Double)
					{
						strKey = "d" + strKey;
						strValue = "" + ((Double) result).doubleValue();
					}
					else if (result instanceof String)
					{
						strKey = "str" + strKey;
						strValue = (String) result;
					}
					else if (result instanceof Timestamp)
					{
						strKey = "ts" + strKey;
						strValue = ((Timestamp) result).toString();
					}
					System.out.println("Key:" + strKey + " / " + "Value:" + strValue);
					request.setAttribute(strKey, strValue);
				}
			}
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	/**@author Barry
	 * 2004-1-9
	 * 把request中的值置入dataentity
	 */
	public static void setAttributesToEntity(ServletRequest request, Object obj) throws Exception
	{

		//定义所支持的数据类型
		final String[] DATA_TYPE = { "int", "long", "double", "java.lang.String", "java.sql.Timestamp" };

		//判断reqeust是否为空
		if (request == null)
		{
			throw new IException("传入的request对象为空");
		}

		//判断对象是否为空
		if (obj == null)
		{
			throw new IException("传入的DataEntity为空");
		}

		//得到对象所有声明的方法
		Method[] m = obj.getClass().getDeclaredMethods();

		for (int n = 0; n < m.length; n++)
		{

			//得到方法名
			String strMethodName = m[n].getName();

			//判断是否是set方法
			if (strMethodName.startsWith("set"))
			{

				//得到当前方法的参数类型
				Class cCurrentParamType = m[n].getParameterTypes()[0];

				//取得参数名称
				String strParaName = strMethodName.substring("set".length());

				//按照参数类型给参数名称添加前缀
				if (cCurrentParamType.getName().equals(DATA_TYPE[0]))
				{ //参数是int型
					strParaName = "int" + strParaName;
				}
				if (cCurrentParamType.getName().equals(DATA_TYPE[1]))
				{ //参数是long型
					strParaName = "l" + strParaName;
				}
				if (cCurrentParamType.getName().equals(DATA_TYPE[2]))
				{ //参数是double型
					strParaName = "d" + strParaName;
				}
				if (cCurrentParamType.getName().equals(DATA_TYPE[3]))
				{ //参数是String型
					strParaName = "str" + strParaName;
				}
				if (cCurrentParamType.getName().equals(DATA_TYPE[4]))
				{ //参数是Timestamp型
					strParaName = "ts" + strParaName;
				}

				//从request中得到的参数
				String strValueInRequest = (String) request.getAttribute(strParaName);

				//判断如果从request中取得的参数不为空,置入dataentity
				if (strValueInRequest != null && strValueInRequest.length() > 0)
				{
					//set方法赋值对象
					Object[] objParam = null;

					//判断参数类型
					if (cCurrentParamType.getName().equals(DATA_TYPE[0]))
					{ //参数是int型
						objParam = new Integer[] { new Integer(strValueInRequest)};
					}
					if (cCurrentParamType.getName().equals(DATA_TYPE[1]))
					{ //参数是long型
						objParam = new Long[] { new Long(strValueInRequest)};
					}
					if (cCurrentParamType.getName().equals(DATA_TYPE[2]))
					{ //参数是double型
						objParam = new Double[] { new Double(DataFormat.parseNumber(strValueInRequest))};
					}
					if (cCurrentParamType.getName().equals(DATA_TYPE[3]))
					{ //参数是String型
						objParam = new String[] { strValueInRequest };
					}
					if (cCurrentParamType.getName().equals(DATA_TYPE[4]))
					{ //参数是Timestamp型
						objParam = new Timestamp[] { DataFormat.getDateTime(strValueInRequest)};
					}

					//将从request中取得的参数置入dataentity
					m[n].invoke(obj, objParam);
				}
			}
		}
	}
	
	
	/**
	 * @author hjliu
	 * 2003-11-26
	 * 根据子账户ID取不同账户类型(定期及贷款)的计提利息
	 * @param subAccountID
	 * @param accountType
	 * @return
	 * @throws SQLException
	 * 增加保证金账户类型的计提利息，2006-04-09,jason fang
	 */
	public SubAccountPredrawInterestInfo getPredrawInterestBySubAccountIDAndAccountType(long subAccountID, long accountType) throws SQLException
	{
		SubAccountPredrawInterestInfo subAccountPredrawInterestInfo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sbSQL = null;
		double predrawInterest = 0.0; //计提利息（贷款、定期）
		double interestRate = 0.0; //当前利率
		double balance = 0.0; //当前余额
		Timestamp predrawDate = null;
		Timestamp clearInterestDate = null;
		String fixedDepositNo = ""; //定期单据号
		subAccountPredrawInterestInfo = new SubAccountPredrawInterestInfo();
		try
		{
			//establish the query sql string
			sbSQL = new StringBuffer();
	        log.debug("---------判断账户类型------------");
			//long accountTypeID = resultInfo.getAccountTypeID();
	        Sett_AccountTypeDAO sett_AccountTypeDAO = new Sett_AccountTypeDAO();
	        AccountTypeInfo accountTypeInfo = null;
	        try {
				accountTypeInfo = sett_AccountTypeDAO.findByID(accountType);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (accountTypeInfo != null) {
				if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.FIXED || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.NOTIFY )
					//保证金存款和活期一样
					//accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN)
				{
					sbSQL.append(" select AF_MPREDRAWINTEREST, MBALANCE, AF_DTPREDRAW, AF_MRATE, DTCLEARINTEREST from sett_SubAccount ");
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.TRUST || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CONSIGN || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.DISCOUNT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.YT || 
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERLOAN)
				{
					sbSQL.append(" select AL_MPREDRAWINTEREST, MBALANCE, AL_DTPREDRAW, AF_MRATE, DTCLEARINTEREST from sett_SubAccount ");
				}
				else if (accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.CURRENT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.OTHERDEPOSIT ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.BAK ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.RESERVE ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.LENDING ||
					accountTypeInfo.getAccountGroupID() == SETTConstant.AccountGroupType.MARGIN )
				{
					//工行联通特殊处理，将计提利息放在字段al_mPredrawInterest.
					sbSQL.append(" select AL_MPREDRAWINTEREST, MBALANCE, AL_DTPREDRAW, AF_MRATE, DTCLEARINTEREST from sett_SubAccount ");
				}
				else
				{
					return subAccountPredrawInterestInfo;
				}
				sbSQL.append(" where ID = ? ");
				//log.info(sbSQL.toString());
				conn = getConnection();
				ps = conn.prepareStatement(sbSQL.toString());
				ps.setLong(1, subAccountID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					predrawInterest = rs.getDouble(1);
					balance = rs.getDouble("MBALANCE");
					predrawDate = rs.getTimestamp(3);
	                interestRate = rs.getDouble("AF_MRATE");
	                clearInterestDate = rs.getTimestamp("DTCLEARINTEREST");
				}
				cleanup(rs);
				cleanup(ps);
				cleanup(conn);

				//log.info("子账户中的余额是: " + balance);
				//log.info("子账户中的计提利息是: " + predrawInterest);
				subAccountPredrawInterestInfo.setBalance(balance);
				subAccountPredrawInterestInfo.setPredrawInterest(predrawInterest);
				subAccountPredrawInterestInfo.setPredrawDate(predrawDate);
		        subAccountPredrawInterestInfo.setRate(interestRate);
		        subAccountPredrawInterestInfo.setEDate(clearInterestDate);
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}

		return subAccountPredrawInterestInfo;
	}

	/**
		 * 功能：返回所有的今天审核的逾期通知单集合
		 * @param officeID
		 * @param currencyID
		 * @param closeDate
		 * @return
		 * @throws Exception
		 */
		public Collection findAllOverDueLoanAccount(long officeID, long currencyID, Timestamp closeDate) throws Exception
		{
			Connection conn = null;
			Vector overDueLoanVector = new Vector();
			PreparedStatement ps = null;
			ResultSet rset = null;
			StringBuffer bufferSQL = null;
			OverDueInfo overDueLoanInfo = null;
			bufferSQL = new StringBuffer();
			
			long[] subloantype = LOANConstant.SubLoanType.getAllCode(officeID,currencyID);
			String subtype = "";
			
			for(int i=0;i<subloantype.length;i++)
			{
				if(i == 0)
				{
					subtype = ""+subloantype[i];
				}
				else
				{
					subtype = subtype + ","+subloantype[i];
				}
			}

			bufferSQL.append(" select overDue.* FROM  sett_subaccount sub,loan_overdueform overdue \n");
			bufferSQL.append(" where sub.al_nloannoteid in \n");		
			bufferSQL.append(" ( select distinct npayformid from loan_overdueform o, \n");
			//bufferSQL.append(" (select  *  from loan_approvaltracing where napprovalid=28 and nstatusid=1) a   \n");		
			
			//Boxu Update 2008年4月21日 换审批流之后换了新的审批记录表
			//bufferSQL.append(" (select  *  from loan_approvaltracing where nloanTypeid in (" + subtype + " ) and nactionid = " + Constant.ApprovalAction.OVERDUE_APPLY + " and nstatusid=1) a   \n");
			bufferSQL.append(" (select * from sys_approvalrecord where transtypeid in (" + subtype + " ) and actionid = " + Constant.ApprovalAction.OVERDUE_APPLY + " and statusid=1) a \n");
			
			bufferSQL.append(" where o.nstatusid= 3 ) \n");	
			bufferSQL.append(" and ( sub.ac_nisnegotiate <> 9 or sub.ac_nisnegotiate is null ) \n");
			bufferSQL.append(" and sub.al_nloannoteid = overdue.npayformid and sub.nstatusid != 2 ");
			
			//Boxu Update 2008年4月21日 区分办事处
			bufferSQL.append(" and overdue.nofficeid = "+officeID+" and overdue.ncurrencyid = "+currencyID+" ");
		
			String sqlString = bufferSQL.toString();
			//log.info(sqlString);
		
			//add by rxie for nhcw 
			if(subtype != null && subtype.length() > 0)
			{
				try
				{
					conn = getConnection();
					ps = conn.prepareStatement(sqlString);
					//ps.setTimestamp(1, closeDate);
					rset = ps.executeQuery();
					while (rset.next())
					{
						//////////log.info(" **************888888888888888888********************8 ");
						//新建一个罚息通知书对象
						overDueLoanInfo = new OverDueInfo();
						//放款通知单ID
						overDueLoanInfo.setLoanPayID(rset.getLong("NPAYFORMID"));
						//标示
						overDueLoanInfo.setID(rset.getLong("ID"));
						//逾期编号
						//overDueInfo.setOverDueApplyCode(rs.getString(""));
						//合同标示
						overDueLoanInfo.setContractID(rset.getLong("NCONTRACTID"));
						//合同编号
						//overDueInfo.setContractCode(rs.getString(""));
						//合同执行计划ID
						overDueLoanInfo.setPlanID(rset.getLong("NPLANID"));
						//合同执行计划版本号ID
						//overDueInfo.setPlanVersionID(rs.getLong(""));
						//金额
						//overDueInfo.setAmount(rs.getDouble(""));
						//计划余额
						overDueLoanInfo.setPlanBalance(rset.getDouble("MPLANBALANCE"));
						//计划日期
						//overDueInfo.setPlanDate(rs.getTimestamp(""));
						//罚息金额
						overDueLoanInfo.setFineAmount(rset.getDouble("MFINEAMOUNT"));
						//罚息利率
						overDueLoanInfo.setFineInterestRate(rset.getDouble("MFINEINTERESTRATE"));
						//是否复利
						overDueLoanInfo.setIsCompoundInterest(rset.getLong("NISCOMPOUNDINTEREST"));
						//罚息日期
						overDueLoanInfo.setFineDate(rset.getTimestamp("DTFINEDATE"));
						//逾期原因
						overDueLoanInfo.setOverDueReason(rset.getString("SOVERDUEREASON"));
						//录入人的ID号码
						overDueLoanInfo.setInputUserID(rset.getLong("NINPUTUSERID"));
						//逾期状态ID
						overDueLoanInfo.setStatusID(rset.getLong("NSTATUSID"));
						//将罚息通知书overDueLoanInfo加入到Vector之中
						overDueLoanVector.add(overDueLoanInfo);
					}
					cleanup(rset);
					cleanup(ps);
					cleanup(conn);
				}
				catch (SQLException sqlE)
				{
					sqlE.printStackTrace();
					throw new Exception();
				}
				finally
				{
					cleanup(rset);
					cleanup(ps);
					cleanup(conn);
				}
			}
			else
			{
				overDueLoanVector = null;
			}
			return overDueLoanVector;
		}

	/**
	 * 对提供的对象集合按照指定的属性进行排序。
	 * <br>
	 * 逻辑：根据提供的getMethodName参数指定的方法名，获取待排序对象的相应属性，然后根据
	 * <br>此属性进行排序，当getMethodName参数指定的方法返回值是java原始类型时，直接用布
	 * <br>尔运算比较，如果是对象，则检查对象是否提供compareTo(Object o)方法，如果有，就
	 * <br>使用此方法进行比较。如果没有则抛异常。
	 * @param objs 原对象集合，不能为null，size=0，直接返回该数组
	 * @param getMethodName objs中对象的获取属性的方法名，例如："getName"。不能为null，如果对象中无此方法，将抛异常
	 * @param isDesc 是否降序，true 降序排列，false 升序排列
	 * @return Object[]
	 */
	public Object[] sortObjectByField(Object[] objs,String getMethodName, boolean isDesc)
		throws Exception
	{
		if (objs == null || objs.length <= 0)
		{
			throw new Exception("Object[] is null.");
		}
	
		Class objClass = null;
		Method objMethod = null;

		Object valueObj = null;
		int type = -1;
		Object objectTemp = null;
	
		try
		{
			objClass = objs[0].getClass();
			objMethod = objClass.getMethod(getMethodName, new Class[]{});
			if (objMethod == null)
			{
				throw new Exception();
			}
		}catch (Throwable t)
		{
			throw new Exception("\nClass:"+objClass.toString()
					+" has no such method:"+objMethod+"().");
		}
	
		Class classTemp = objMethod.getReturnType();
	
		if (classTemp.equals(long.class))
		{
			type = 1;
		}
		else if (classTemp.equals(double.class))
		{
			type = 2;
		}
		else if (!classTemp.isArray())
		{
			try
			{
				Method valueObjMethod = null;
				valueObjMethod =
					classTemp.getMethod("compareTo", new Class[]{Object.class});
				if (valueObjMethod == null)
				{
					throw new Exception();
				}
				type = 4;
			}catch (Throwable t)
			{
				throw new Exception("\nClass:"+classTemp.toString()
						+" has no such method:compareTo(Object o).");
			}
		}
		else
		{
			throw new Exception("unexpected type.");
		}
	
		if (type == 1)
		{
			for (int i=0; i <objs.length - 1; i++)
			{
				Object doubleObjecti = objMethod.invoke(objs[i], new Object[]{});
				Object doubleObjectj = null;
				Object doubleObjectTemp = doubleObjecti;
				int pos = -1;
				for (int j = i + 1; j < objs.length; j++)
				{
					doubleObjectj = objMethod.invoke(objs[j], new Object[]{});
					if (isDesc == true && compareTo(doubleObjectTemp, doubleObjectj, type, null) < 0)
					{
						doubleObjectTemp = doubleObjectj;
						pos = j;
					}
					else if (isDesc == false && compareTo(doubleObjectTemp, doubleObjectj, type, null) > 0)
					{
						doubleObjectTemp = doubleObjectj;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}
		else if (type == 2)
		{
			for (int i=0; i <objs.length - 1; i++)
			{
				Object longObjecti = objMethod.invoke(objs[i], new Object[]{});
				Object longObjectj = null;
				Object longObjectTemp = longObjecti;
				int pos = -1;
				for (int j = i + 1; j < objs.length; j++)
				{
					longObjectj = objMethod.invoke(objs[j], new Object[]{});
					if (isDesc == true && compareTo(longObjectTemp, longObjectj, type, null) < 0)
					{
						longObjectTemp = longObjectj;
						pos = j;
					}
					else if (isDesc == false && compareTo(longObjectTemp, longObjectj, type, null) > 0)
					{
						longObjectTemp = longObjectj;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}
		else if (type == 4)
		{
			int isDescEndPos = 0;
			for (int i=0; i < objs.length - 1 - isDescEndPos; i++)
			{
				Object objectValuei = objMethod.invoke(objs[i], new Object[]{});
				if (objectValuei == null && isDesc == false)
				{
					objectTemp = objs[i];
					for(int k = i; k > 0; k--)
					{
						objs[k] = objs[k-1];
					}
					objs[0] = objectTemp;
					continue;
				}
				else if (objectValuei == null && isDesc == true)
				{
					objectTemp = objs[i];
					for(int k = i; k < objs.length - 1 - isDescEndPos; k++)
					{
						objs[k] = objs[k+1];
					}
					objs[objs.length - 1 - isDescEndPos] = objectTemp;
					isDescEndPos++;
					i--;
					continue;
				}
			
				Object objectValuej = null;
				Object objectValueTemp = objectValuei;
			
				Class objClassTemp = objectValuei.getClass();
				Method objMethodTemp = objClassTemp.getMethod("compareTo",new Class[]{Object.class});
			
				int pos = -1;
				for (int j = i + 1; j < objs.length - isDescEndPos; j++)
				{
					objectValuej = objMethod.invoke(objs[j], new Object[]{});
				
					if (objectValuej == null && isDesc == false)
					{
						objectTemp = objs[j];
						for(int k = j; k > 0; k--)
						{
							objs[k] = objs[k-1];
						}
						objs[0] = objectTemp;
						i++;
						continue;
					}
					else if (objectValuej == null && isDesc == true)
					{
						objectTemp = objs[j];
						for(int k = j; k < objs.length - 1 - isDescEndPos; k++)
						{
							objs[k] = objs[k+1];
						}
						objs[objs.length - 1 - isDescEndPos] = objectTemp;
						isDescEndPos++;
						j--;
						continue;
					}
				
					int result = compareTo(objectValueTemp, objectValuej, type, objMethodTemp);
					if (isDesc == true && result < 0)
					{
						objectValueTemp = objectValuej;
						pos = j;
					}
					else if (isDesc == false && result > 0)
					{
						objectValueTemp = objectValuej;
						pos = j;
					}
				}
				if (pos != -1)
				{
					objectTemp = objs[i];
					objs[i] = objs[pos];
					objs[pos] = objectTemp;
				}
			}
		}
	
		return objs;
	}

	private static int compareTo(Object obj1,Object obj2,int type,Method compareToMethod) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		int result = 0;
	
		if (type == 1)
		{
			long longValue1 = ((Long)obj1).longValue();
			long longValue2 = ((Long)obj2).longValue();
		
			if (longValue1 > longValue2)
			{
				result = 1;
			}
			else if (longValue1 == longValue2)
			{
				result = 0;
			}
			else
			{
				result = -1;
			}
		}
		else if (type == 2)
		{
			double longValue1 = ((Double)obj1).doubleValue();
			double longValue2 = ((Double)obj2).doubleValue();
		
			if (longValue1 > longValue2)
			{
				result = 1;
			}
			else if (longValue1 == longValue2)
			{
				result = 0;
			}
			else
			{
				result = -1;
			}
		}
		else if (type == 4)
		{
			result = ((Integer)compareToMethod.invoke(obj1,new Object[]{obj2})).intValue();
		}
	
		return result;
	}
	
	
	/**
	 * 功能：返回查看电子对帐详细信息所需参数
	 * @param ID
	 * @param TransactionTypeID
	 * @param TransNo
	 * @param SerialNo
	 * @return
	 * @throws Exception
	 */
	public static QueryTransactionInfo findParameterByTransNo(String TransNo) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		boolean bCloseConnection = false;
		QueryTransactionInfo qtInfo = null;
		Connection conn=null;
		try
		{			
			conn = Database.getConnection();
			bCloseConnection = true;	
			
			strSQL = "select ID,TransactionTypeID,SerialNo,transNo from sett_vtransaction where transNo = ?";
			ps = conn.prepareStatement(strSQL);
			ps.setString(1, TransNo);
			rs = ps.executeQuery();
			System.out.println("取电子对帐详细信息所需参数SQL: " + strSQL);
			if (rs.next())
			{
				qtInfo = new QueryTransactionInfo();
				qtInfo.setTransNo(rs.getString("TransNo"));
				qtInfo.setTransactionTypeID(rs.getLong("TransactionTypeID"));
				qtInfo.setSerialNo(rs.getLong("SerialNo"));
				qtInfo.setID(rs.getLong("ID"));
				
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if(conn != null)
			{
				conn.close();
				conn =null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (bCloseConnection && conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}
		return qtInfo;
	}
	
	/**
	 * 功能：返回今天审核的逾期通知单
	 * @param officeID
	 * @param currencyID
	 * @param closeDate
	 * @return OverDueInfo
	 * @throws Exception
	 */
	public OverDueInfo findOverDueLoanAccount(long officeID, long currencyID, Timestamp closeDate) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;
		StringBuffer bufferSQL = null;
		OverDueInfo overDueLoanInfo = null;
		bufferSQL = new StringBuffer();
		
		long[] subloantype = LOANConstant.SubLoanType.getAllCode(officeID,currencyID);
		String subtype = "";
		
		for(int i=0;i<subloantype.length;i++)
		{
			if(i == 0)
			{
				subtype = ""+subloantype[i];
			}
			else
			{
				subtype = subtype + ","+subloantype[i];
			}
		}
		
		bufferSQL.append(" select overDue.* FROM  sett_subaccount sub,loan_overdueform overdue \n");
		bufferSQL.append(" where sub.al_nloannoteid in \n");		
		bufferSQL.append(" ( select distinct npayformid from loan_overdueform o, \n");
		//bufferSQL.append(" (select  *  from loan_approvaltracing where napprovalid=28 and nstatusid=1) a \n");		
		//Boxu Update 2008年4月21日 换审批流之后换了新的审批记录表
		//bufferSQL.append(" (select  *  from loan_approvaltracing where nloanTypeid in (" + subtype + " ) and nactionid = " + Constant.ApprovalAction.OVERDUE_APPLY + " and nstatusid=1) a \n");
		bufferSQL.append(" (select * from sys_approvalrecord where transtypeid in (" + subtype + " ) and actionid = " + Constant.ApprovalAction.OVERDUE_APPLY + " and statusid=1) a \n");
		bufferSQL.append(" where o.nstatusid= 3 ) \n");
		
		//暂时屏蔽，以后需要用到 sub.ac_nisnegotiate <> 9 已避免重复算息
		//bufferSQL.append(" and ( sub.ac_nisnegotiate <> 9 or sub.ac_nisnegotiate is null ) \n");
		
		bufferSQL.append(" and sub.al_nloannoteid = overdue.npayformid and sub.nstatusid != 2 ");
		//Boxu Update 2008年4月21日 区分办事处
		bufferSQL.append(" and overdue.nofficeid = "+officeID+" and overdue.ncurrencyid = "+currencyID+" ");
		
		String sqlString = bufferSQL.toString();
		//log.info(sqlString);
	
		//add by rxie for nhcw 
		if(subtype != null && subtype.length() > 0)
		{
			try
			{
				conn = getConnection();
				ps = conn.prepareStatement(sqlString);
				rset = ps.executeQuery();
				while (rset.next())
				{
					//////////log.info(" **************888888888888888888********************8 ");
					//新建一个罚息通知书对象
					overDueLoanInfo = new OverDueInfo();
					//放款通知单ID
					overDueLoanInfo.setLoanPayID(rset.getLong("NPAYFORMID"));
					//标示
					overDueLoanInfo.setID(rset.getLong("ID"));
					//逾期编号
					//overDueInfo.setOverDueApplyCode(rs.getString(""));
					//合同标示
					overDueLoanInfo.setContractID(rset.getLong("NCONTRACTID"));
					//合同编号
					//overDueInfo.setContractCode(rs.getString(""));
					//合同执行计划ID
					overDueLoanInfo.setPlanID(rset.getLong("NPLANID"));
					//合同执行计划版本号ID
					//overDueInfo.setPlanVersionID(rs.getLong(""));
					//金额
					//overDueInfo.setAmount(rs.getDouble(""));
					//计划余额
					overDueLoanInfo.setPlanBalance(rset.getDouble("MPLANBALANCE"));
					//计划日期
					//overDueInfo.setPlanDate(rs.getTimestamp(""));
					//罚息金额
					overDueLoanInfo.setFineAmount(rset.getDouble("MFINEAMOUNT"));
					//罚息利率
					overDueLoanInfo.setFineInterestRate(rset.getDouble("MFINEINTERESTRATE"));
					//是否复利
					overDueLoanInfo.setIsCompoundInterest(rset.getLong("NISCOMPOUNDINTEREST"));
					//罚息日期
					overDueLoanInfo.setFineDate(rset.getTimestamp("DTFINEDATE"));
					//逾期原因
					overDueLoanInfo.setOverDueReason(rset.getString("SOVERDUEREASON"));
					//录入人的ID号码
					overDueLoanInfo.setInputUserID(rset.getLong("NINPUTUSERID"));
					//逾期状态ID
					overDueLoanInfo.setStatusID(rset.getLong("NSTATUSID"));
				}
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			}
			catch (SQLException sqlE)
			{
				sqlE.printStackTrace();
				throw new Exception();
			}
			finally
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			}
		}
		
		return overDueLoanInfo;
	}
	
	public static String checkStatusForAutoDel(long lStatusID, long lNewStatusID, long lActionID)
	{
		String rtnStr = "";
		//修改保存
		if (lActionID == SETTConstant.Actions.MODIFYSAVE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE && lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				//已被复核
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
			}
			else
			{
				//已被复核
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
			}
		}
		//修改暂存
		if (lActionID == SETTConstant.Actions.MODIFYTEMPSAVE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
				//已被复核
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				//已被删除
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
				//已被保存
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E130";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E016";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E017";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E130";
				}
			}
		}
		//删除
		if (lActionID == SETTConstant.Actions.DELETE)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE && lStatusID != SETTConstant.TransactionStatus.TEMPSAVE)
			{
			//	if (lStatusID == SETTConstant.TransactionStatus.CHECK)
			//	{
			//		return "Sett_E018";
			//	}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E019";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E018";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E019";
				}
			}
		}
		//复核
		if (lActionID == SETTConstant.Actions.CHECK)
		{
			if (lStatusID != SETTConstant.TransactionStatus.SAVE)
			{
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E022";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E021";
				}

			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E022";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E021";
				}
			}
		}
		//取消复核
		if (lActionID == SETTConstant.Actions.CANCELCHECK)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CHECK)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E023";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E025";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E023";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E025";
				}
			}
		}
		//勾账
		if (lActionID == SETTConstant.Actions.SQUAREUP)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CHECK)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E045";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E046";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CIRCLE)
				{
					return "Sett_E052";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E045";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E046";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.CIRCLE)
				{
					return "Sett_E052";
				}
			}
		}
		//取消勾账
		if (lActionID == SETTConstant.Actions.CANCELSQUAREUP)
		{
			if (lStatusID != SETTConstant.TransactionStatus.CIRCLE)
			{
				if (lStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E048";
				}
				if (lStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E049";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E050";
				}
			}
			else
			{
				if (lNewStatusID == SETTConstant.TransactionStatus.SAVE)
				{
					return "Sett_E048";
				}
				if (lNewStatusID == SETTConstant.TransactionStatus.DELETED)
				{
					return "Sett_E049";
				}
				if (lStatusID == SETTConstant.TransactionStatus.CHECK)
				{
					return "Sett_E050";
				}
			}
		}

		return rtnStr;
	}
	/**
	 * 功能：
	 * @param ID
	 * @param TransactionTypeID
	 * @param TransNo
	 * @param SerialNo
	 * @return
	 * @throws Exception
	 */
	public  InterestsInfo findPayInterestAmount(long subAccountId,Timestamp InterestDate) throws Exception
	{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		InterestsInfo qtInfo = new InterestsInfo();
		Connection conn=null;
		String lastClearInterestDate = null;
		try
		{			
			conn = getConnection();
			lastClearInterestDate = getLastClearInterestDay(subAccountId,InterestDate);
			
			strSQL.append(" select nvl(d.mcompoundinterest -d.mcompounddailyinterset , 0) mcompoundinterest,                        \n");
			strSQL.append("        nvl(d.mforfeitinterest-d.mforfeitdailyinterset, 0) mforfeitinterest,                          \n");
			strSQL.append("        nvl(d.minterest-d.mdailyinterest, 0) minterest,                                        \n");
			strSQL.append("        nvl(table1.mcompounddailyinterset + d.mcompoundinterest -                     \n");
			strSQL.append("        table2.mcompoundinterest,0) paymcompoundinterest,                             \n");
			strSQL.append("        nvl(table1.mforfeitdailyinterset + d.mforfeitinterest -                        \n");
			strSQL.append("        table2.mforfeitinterest,0) paymforfeitinterest,                               \n");
			strSQL.append("        nvl(table1.mdailyinterest + d.minterest - table2.minterest,0) payminterest       \n");
			strSQL.append("   from sett_dailyaccountbalance d,                                                \n");
			strSQL.append("        (select sum(b.mcompounddailyinterset) mcompounddailyinterset,              \n");
			strSQL.append("                sum(b.mforfeitdailyinterset) mforfeitdailyinterset,                \n");
			strSQL.append("                sum(mdailyinterest) mdailyinterest,                                \n");
			strSQL.append("                b.nsubaccountid                                                    \n");
			strSQL.append("           from sett_dailyaccountbalance b                                         \n");
			strSQL.append("          where b.nsubaccountid = "+subAccountId+"                                             \n");
			strSQL.append("    and b.dtdate >   to_date('"+lastClearInterestDate+"', 'yyyy-mm-dd') \n");

			strSQL.append("            and b.dtdate < to_date('"+DataFormat.formatDate(InterestDate)+"', 'yyyy-mm-dd')                     \n");
			strSQL.append("          group by b.nsubaccountid) table1,                                        \n");
			strSQL.append("        (select b.mcompoundinterest,                                               \n");
			strSQL.append("                b.mforfeitinterest,                                                \n");
			strSQL.append("                b.minterest,                                                       \n");
			strSQL.append("                b.nsubaccountid                                                    \n");
			strSQL.append("           from sett_dailyaccountbalance b                                         \n");
			strSQL.append("          where b.nsubaccountid = "+subAccountId+"                                             \n");
			strSQL.append("            and b.dtdate = to_date('"+DataFormat.formatDate(InterestDate)+"', 'yyyy-mm-dd') - 1) table2       \n");
			strSQL.append("  where d.nsubaccountid = "+subAccountId+"                                                     \n");
			strSQL.append("    and d.nsubaccountid = table1.nsubaccountid(+)                                     \n");
			strSQL.append("    and d.nsubaccountid = table2.nsubaccountid(+)                                  \n");
			strSQL.append("    and d.dtdate =   to_date('"+lastClearInterestDate+"', 'yyyy-mm-dd') \n");
		
			
			
			
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			System.out.println("查询已还利息金额SQL: " + strSQL);
			if (rs.next())
			{
				qtInfo.setMinterest(rs.getDouble("minterest"));
				qtInfo.setMcompoundinterest(rs.getDouble("mcompoundinterest"));
				qtInfo.setMforfeitinterest(rs.getDouble("mforfeitinterest"));
				qtInfo.setPayminterest(rs.getDouble("payminterest"));
				qtInfo.setPaymcompoundinterest(rs.getDouble("paymcompoundinterest"));
				qtInfo.setPaymforfeitinterest(rs.getDouble("paymforfeitinterest"));
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return qtInfo;
	}
	
	public String getLastClearInterestDay(long subAccountId, Timestamp date)throws IException
	{
		String lastClearInterestDay = null;
		PreparedStatement ps = null;
		Connection conn =null;
		ResultSet rset = null;
		StringBuffer sql = new StringBuffer();
		
		try
		{
			conn =getConnection();
			sql.append(" select a.*                                                 \n");
			sql.append("   from SETT_CLEARINTERESTPLAN a,loan_payform b, loan_contractform c  , sett_subaccount        d \n");
			sql.append("    where a.statusid = 1                                    \n");
			sql.append("  and  b.ncontractid = c.id \n");
			sql.append("  and d.al_nloannoteid = b.id \n");
			sql.append("  and  c.nsubtypeid = a.subloantype \n");
			sql.append(" and ((case                                                    "); 
			sql.append("       when b.dtend <= to_date('"+DataFormat.formatDate(date)+"', 'yyyy-mm-dd') then ");
			sql.append("        a.overduetype                                          ");
			sql.append("     end) = "+SETTConstant.ClearInterestPlayOverDueType.OVERDUE+" or (case  ");
			sql.append("       when b.dtend > to_date('"+DataFormat.formatDate(date)+"', 'yyyy-mm-dd') then");
			sql.append("        a.overduetype                                          ");
			sql.append("     end) = "+SETTConstant.ClearInterestPlayOverDueType.COMMON+") ");
			sql.append(" and ((case                                                   ");
			sql.append("       when c.nintervalnum <= 6 then                          ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.SIXMOUTH_BELOW+" or (case ");
			sql.append("       when c.nintervalnum > 6 and c.nintervalnum <= 12 then  ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.SIXMOUTH_ONEYEAR+" or (case ");
			sql.append("       when c.nintervalnum > 12 and c.nintervalnum <= 36 then ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.ONEYEAR_THREEYEAR+" or (case   ");
			sql.append("       when c.nintervalnum > 36 and c.nintervalnum <= 60 then ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.THREEYEAR_FIVEYEAR+" or (case   ");
			sql.append("       when c.nintervalnum > 60 then                          ");
			sql.append("        a.loantermtypeid                                      ");
			sql.append("     end) = "+LOANConstant.LoanTerm.FIVEYEAR_ABOVE+" or a.loantermtypeid = -1)   ");
			sql.append(" and d.id = "+subAccountId);
			sql.append( " order by  a.loantermtypeid desc ");
			
			ps = conn.prepareStatement(sql.toString());
			rset = ps.executeQuery();
			
			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(date);
			int i= 0;
			while(rset.next())
			{
				i++;
				if(rset.getLong("cleartype")==SETTConstant.ClearInterestPlayClearType.MONTH)//月
				{
					if(rset.getLong("cleartime")<calendar.get(Calendar.DAY_OF_MONTH))
					{
						calendar.set(Calendar.DAY_OF_MONTH, (int)rset.getLong("cleartime"));
					}
					else
					{
						calendar.set(Calendar.DAY_OF_MONTH, (int)rset.getLong("cleartime"));
						if(calendar.get(Calendar.MONTH)==Calendar.JANUARY)
						{
							calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
							calendar.set(Calendar.MONTH,Calendar.DECEMBER);
						}
						else
						{
							calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
						}
					}
				}
				else if(rset.getLong("cleartype")==SETTConstant.ClearInterestPlayClearType.SEASON)//季
				{
						if(calendar.get(Calendar.MONTH)<Calendar.MARCH)
						{
							calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
							calendar.set(Calendar.MONTH,Calendar.DECEMBER);
						}
						else if(calendar.get(Calendar.MONTH)==Calendar.MARCH)
						{
							if(rset.getLong("cleartime")>=calendar.get(Calendar.DAY_OF_MONTH))
							{
								calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
								calendar.set(Calendar.MONTH,Calendar.DECEMBER);
							}
						}
						else if(calendar.get(Calendar.MONTH)<Calendar.JUNE)
						{
							calendar.set(Calendar.MONTH,Calendar.MARCH);
						}
						else if(calendar.get(Calendar.MONTH)==Calendar.JUNE)
						{
							if(rset.getLong("cleartime")>=calendar.get(Calendar.DAY_OF_MONTH))
							{
								calendar.set(Calendar.MONTH,Calendar.MARCH);
							}
						}
						else if(calendar.get(Calendar.MONTH)<Calendar.SEPTEMBER)
						{
							calendar.set(Calendar.MONTH,Calendar.JUNE);
						}
						else if(calendar.get(Calendar.MONTH)==Calendar.SEPTEMBER)
						{
							if(rset.getLong("cleartime")>=calendar.get(Calendar.DAY_OF_MONTH))
							{
								calendar.set(Calendar.MONTH,Calendar.JUNE);
							}
						}
						else if(calendar.get(Calendar.MONTH)<Calendar.DECEMBER)
						{
							calendar.set(Calendar.MONTH,Calendar.SEPTEMBER);
						}
						else if(calendar.get(Calendar.MONTH)==Calendar.DECEMBER)
						{
							if(rset.getLong("cleartime")>=calendar.get(Calendar.DAY_OF_MONTH))
							{
								calendar.set(Calendar.MONTH,Calendar.SEPTEMBER);
							}
						}
						calendar.set(Calendar.DAY_OF_MONTH, (int)rset.getLong("cleartime"));
				}
				lastClearInterestDay=(DataFormat.formatDate(calendar.getTime(),DataFormat.FMT_DATE_YYYYMMDD ));
				break;
			}
			if(i==0)
			{
				StringBuffer sqltemp = new StringBuffer();
				String temp ="";
				sqltemp.append(" select u.*                                                      \n");   
				sqltemp.append("   from loan_contractform u, loan_payform h, sett_subaccount y   \n");
				sqltemp.append("  where 1 = 1                                                    \n");
				sqltemp.append("    and y.al_nloannoteid = h.id                                  \n");
				sqltemp.append("    and h.ncontractid = u.id                                     \n");
				sqltemp.append("    and y.id = "+subAccountId+"                                             \n");
				ps = conn.prepareStatement(sqltemp.toString());
				rset = ps.executeQuery();
				while(rset.next())
				{
					temp = rset.getString("scontractcode");
				}
				throw new IException("贷款合同"+temp+"没有设置结息计划");
			}
		}
		catch(Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try 
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return lastClearInterestDay;
	}
	public InterestsInfo findAllClearCompoundInterset(long subAccountId,Timestamp time)throws Exception
	{
		PreparedStatement ps = null;
		Connection conn =null;
		ResultSet rset = null;
		InterestsInfo interestsInfo = new InterestsInfo();
		try
		{
			conn = getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select sum(mrealinterest) mrealinterest,                                              \n");
			sql.append("        sum(mrealcompoundinterest) mrealcompoundinterest,                              \n");
			sql.append("        sum(mrealoverdueinterest) mrealoverdueinterest                                 \n");
			
			//sql.append("   from (select sum(a.mrealinterest) mrealinterest,                                    \n");
			//sql.append("                sum(a.mrealcompoundinterest) mrealcompoundinterest,                    \n");
			//sql.append("                sum(a.mrealoverdueinterest) mrealoverdueinterest                       \n");
			//如果是免还剩余利息的，需要取全额利息，而不是实付利息
			sql.append("  from  (select sum(decode(nisremitinterest,1,minterest,mrealinterest)) mrealinterest,   	                        \n");
			sql.append("        sum(decode(nisremitcompoundinterest,1,mcompoundinterest,mrealcompoundinterest)) mrealcompoundinterest,    \n");
			sql.append("        sum(decode(nisremitoverdueinterest,1,moverdueinterest,mrealoverdueinterest)) mrealoverdueinterest         \n");
			
			sql.append("           from sett_TransRepaymentLoan a                                              \n");
			sql.append("          where a.dtinterestclear = to_date('"+DataFormat.formatDate(time)+"', 'yyyy-mm-dd')                \n");
			sql.append("            and a.nsubaccountid = "+subAccountId+"                                     \n");
			//sql.append("            and a.ntransactiontypeid in (18, 45)                                       \n");
			sql.append("            and a.ntransactiontypeid in (18, 45, 20)                                   \n");
			sql.append("            and a.nstatusid = 3                                                        \n");
			sql.append("         union                                                                         \n");
			sql.append("         select decode(b.ninteresttype, 1, sum(b.minterest), 0) mrealinterest,         \n");
			sql.append("                decode(b.ninteresttype, 4, sum(b.minterest), 0) mrealcompoundinterest, \n");
			sql.append("                decode(b.ninteresttype, 5, sum(b.minterest), 0) mrealoverdueinterest   \n");
			sql.append("           from sett_TransInterestSettlement b                                         \n");
			//sql.append("          where b.ntransactiontypeid = 104                                             \n");
			sql.append("          where b.ntransactiontypeid in(104,106)                                       \n");
			sql.append("            and b.nsubaccountid = "+subAccountId+"                                     \n");
			sql.append("            and b.dtinterestsettlement = to_date('"+DataFormat.formatDate(time)+"', 'yyyy-mm-dd')           \n");
			sql.append("            and b.nstatusid <> 0                                                       \n");
			sql.append("          group by b.ninteresttype)                                                    \n");
			
			
			ps = conn.prepareStatement(sql.toString());
			log.info(sql.toString());
			rset = ps.executeQuery();
			
			while(rset.next())
			{
				interestsInfo.setPaymcompoundinterest(rset.getDouble("mrealcompoundinterest"));
				interestsInfo.setPaymforfeitinterest(rset.getDouble("mrealoverdueinterest"));
				interestsInfo.setPayminterest(rset.getDouble("mrealinterest"));
			}
			
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			cleanup(rset);
			cleanup(ps);
			cleanup(conn);
		}
		
		return interestsInfo;
	}
	public Timestamp getStartCompoundinterestDay(long subAccountId)throws IException
	{
		PreparedStatement ps = null;
		Connection conn =null;
		ResultSet rset = null;
		StringBuffer sql = new StringBuffer();
		Timestamp time = null;
		
		try
		{
			conn =getConnection();
			sql.append(" select *                           \n");
			sql.append("   from sett_dailyaccountbalance d  \n");
			sql.append("  where d.nsubaccountid = "+subAccountId+"  \n");
			sql.append("    and d.mcompounddailyinterset>0  \n");
			sql.append("  order by d.dtdate asc             \n");
			
			ps = conn.prepareStatement(sql.toString());
			rset = ps.executeQuery();
			
			while(rset.next())
			{
				time = rset.getTimestamp("dtdate");
				break;
			}
		}
		catch(Exception e)
		{
			throw new IException(e.getMessage());
		}
		finally
		{
			try 
			{
				cleanup(rset);
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return time;
	}
}
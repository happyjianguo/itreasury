package com.iss.itreasury.bill.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.bill.blankvoucher.dataentity.BlankRegisterInfo;
import com.iss.itreasury.bill.blankvoucher.dataentity.BlankTransactionInfo;
import com.iss.itreasury.bill.blankvoucher.dataentity.BlankUseInfo;
import com.iss.itreasury.settlement.generalledger.bizlogic.GeneralLedgerBean;
import com.iss.itreasury.settlement.generalledger.dataentity.GenerateGLEntryParam;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
/**
 * @author gqzhang
公用工具类 To  change this generated comment edit the template variable "typecomment":
Window>Preferences>Java>Templates. To enable and disable the creation of type
comments go to Window>Preferences>Java>Code Generation.
 */
public class UtilOperation
{
	protected Log4j log = new Log4j(Constant.ModuleType.BILL, this);
	/**
	 * Method getFormatBillCode.
	 * @param strCodePrefix 票据号前缀
	 * @param lCodeNum      票据号数字部分
	 * @param lNumLength    票据号数字部分长度
	 * @return String
	 */
	public static String getFormatBillCode(String strCodePrefix, long lCodeNum, long lNumLength)
	{
		String strBillCode = "";
		String strTmp = "";
		if (lNumLength > 0)
		{
			strTmp = "" + lCodeNum;
			if (strTmp != null && strTmp.trim().length() > 0)
			{
				if (strTmp.trim().length() <= lNumLength)
				{
					long lLength = lNumLength - strTmp.trim().length();
					strBillCode = strCodePrefix;
					for (int i = 0; i < lLength; i++)
					{
						strBillCode += "0";
					}
					strBillCode += lCodeNum;
				}
			}
		}
		return strBillCode;
	}
	/**
	 * Method getNewTransactionNo.
	 * 调用结算的方法生成统一的交易编号。在票据管理系统中主要是空白凭证交易调用此方法生成交易编号
	 * @param lOfficeID 
	 * @param lCurrencyID     
	 * @return String
	 */
	public String getNewTransactionNo(long lOfficeID, long lCurrencyID) throws BillException
	{
		String transNo = "";
		com.iss.itreasury.settlement.util.UtilOperation bean = new com.iss.itreasury.settlement.util.UtilOperation();
		try
		{
			transNo = bean.getNewTransactionNo(lOfficeID, lCurrencyID);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new BillException("Gen_E009", e);
		}
		return transNo;
	}
	/**
	 * Method getNewDraftTransactionNo.
	 * 获取汇票管理的流水编号
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @param lOperationTypeID 取自常量BILLConstant.DraftOperationType
	 * @return String
	 * @throws BillException
	 */
	public String getNewDraftTransactionNo(long lOfficeID, long lCurrencyID, long lOperationTypeID) throws BillException
	{
		String transNo = "";
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		long Records=0;
		String sql="";
		String strOpenDate = "";
		System.out.println("---------------------------------------3");
		try{
			con=Database.getConnection();
			//根据办事处币种调用结算方法获取当前结算系统开机日
			sql="select to_char(dtopendate,'yyyymmdd') opendate from sett_officetime where nofficeid=" + lOfficeID + " and ncurrencyid=" + lCurrencyID;
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				strOpenDate = rs.getString("opendate");
			}
			//System.out.println("---------------------------------------0");
			//Timestamp ts=Common.getSystemDate(lOfficeID,lCurrencyID);
			//System.out.println("---------------------------------------1");
			
			//根据不同的操作类型到对应的数据库表中（如汇票入库对应的表名为BILL_TRANSDRAFTIN）获取当日已有的记录数，将该记录数+1格式化为五位数，作为本次交易的流水编号后缀
			switch((int)lOperationTypeID)
			{
				case (int)BILLConstant.DraftOperationType.DraftIn:
					sql="select count(*) records from BILL_TRANSDRAFTIN where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;
				case (int)BILLConstant.DraftOperationType.DraftOut:
					sql="select count(*) records from BILL_TRANSDRAFTOUT where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;
				case (int)BILLConstant.DraftOperationType.DraftQuery:
					sql="select count(*) records from BILL_TRANSDRAFTQUERY where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;	
				case (int)BILLConstant.DraftOperationType.DraftQueryReply:
					sql="select count(*) records from BILL_TRANSDRAFTQUERYREPLY where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;	
				case (int)BILLConstant.DraftOperationType.DraftConsign:
					sql="select count(*) records from BILL_TRANSDRAFTCONSIGN where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;	
				case (int)BILLConstant.DraftOperationType.DraftConsignReply:
					sql="select count(*) records from BILL_TRANSDRAFTCONSIGNREPLY where INPUTDATE = to_date('"+strOpenDate+"','yyyymmdd')";
					break;	
			}
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			if (rs.next())
			{
				Records = rs.getLong("records");
			}
			System.out.println("---------------------------------------4:"+sql);
		}catch(Exception ie){
			throw new BillException("Gen_E009",ie);
		}
		finally{
			try {
				if(rs!=null)
				{
					rs.close();
					rs=null;
				}
				if(ps!=null)
				{
					ps.close();
					ps=null;
				}
				if(con!=null)
				{
					con.close();
					con=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		System.out.println("---------------------------------------5");
		//例如汇票入库，结算开机日2005-02-22，办事处01，币种01，取得流水号为00004，则交易号为20050222010100004，依次类推汇票出库等
		transNo=""+strOpenDate+BILLConstant.DraftOperationType.getOperateString(lOperationTypeID)+DataFormat.formatInt(lOfficeID, 2)+DataFormat.formatInt(lCurrencyID, 2)+DataFormat.formatInt(Records+1, 5);
		System.out.println("获取汇票管理的流水编号:"+transNo);
		return transNo;
	}
	/**
	 * 生成内部会计分录。在交易复核完成时调用
	 * @param transInfo
	 * @return
	 * @throws BillException
	 */
	public boolean generateGLEntry(BlankTransactionInfo transInfo) throws BillException
	{
		boolean ret = false;
		//分录类型 无关
		long lEntryType = SETTConstant.EntryType.IRRESPECTIVE;
		//收款方账户
		long receiveAccountID = -1;
		//付款方账户
		long payAccountID = -1;
		//本金开户行ID
		long principalBankID = -1;
		//总帐类
		long generalledgerID = -1;
		//发生额
		double dAmount = 0;
		//如果收/付款银行帐户ID>0，则本金 流向lPrincipalType =2银行，否则本金流向lPrincipalType =1 内部转帐
		long lPrincipalType = -1;
		if ((transInfo.getTransTypeID() == BILLConstant.BlankTransctionType.REGISTER) || (transInfo.getTransTypeID() == BILLConstant.BlankTransctionType.USE))
		{
			//如果是注册交易
			if ((transInfo.getTransTypeID() == BILLConstant.BlankTransctionType.REGISTER))
			{
				BlankRegisterInfo rInfo = (BlankRegisterInfo) transInfo.getDetailInfo();
				if (rInfo != null)
				{
					dAmount = rInfo.getAmount();
					principalBankID = rInfo.getPayBankID();
					generalledgerID = rInfo.getPayLedgerID();
				}
			}
			//如果是领用交易
			if ((transInfo.getTransTypeID() == BILLConstant.BlankTransctionType.USE))
			{
				BlankUseInfo uInfo = (BlankUseInfo) transInfo.getDetailInfo();
				if (uInfo != null)
				{
					//dAmount=uInfo.get
					//principalBankID=uInfo.getPayBankID();
					//generalledgerID=uInfo.getPayLedgerID();
				}
			}
			GeneralLedgerBean glopOperation = new GeneralLedgerBean();
			GenerateGLEntryParam param = new GenerateGLEntryParam();
			if (principalBankID > 0)
			{
				//本金流向是 银行
				lPrincipalType = SETTConstant.CapitalType.BANK;
			}
			else
				if (generalledgerID > 0)
				{
					//本金流向是 总账
					lPrincipalType = SETTConstant.CapitalType.GENERALLEDGER;
				}
			param.setOfficeID(transInfo.getOfficeID());
			param.setCurrencyID(transInfo.getCurrencyID());
			param.setTransactionTypeID(transInfo.getTransTypeID());
			param.setExecuteDate(transInfo.getExecuteDate());
			param.setInterestStartDate(transInfo.getInterestStartDate());
			param.setTransNo(transInfo.getTransCode());
			param.setAbstractStr(transInfo.getSummary());
			param.setInputUserID(transInfo.getInputUserID());
			param.setCheckUserID(transInfo.getCheckUserID());
			param.setPrincipalType(lPrincipalType);
			param.setEntryType(lEntryType);
			param.setReceiveAccountID(receiveAccountID);
			param.setPayAccountID(payAccountID);
			param.setPrincipalOrTransAmount(dAmount);
			param.setPrincipalBankID(principalBankID);
			param.setGenaralLedgerTypeID(generalledgerID);
			param.setAutoCreateBankInstruction(false);
			boolean res;
			try
			{
				res = glopOperation.generateGLEntry(param);
			}
			catch (IException e)
			{
				// TODO Auto-generated catch block
				throw new BillException(e.getErrorCode(), e);
			}
			if (!res)
			{
				throw new BillException("Gen_001", null);
			}
		}
		else
		{
			ret = true;
		}
		return ret;
	}
	/**
	 * 删除已经生成的会计分录。在取消复核交易时调用
	 * @param strTransNo
	 * @return
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long deleteTransAccountDetail(String strTransNo) throws BillException
	{
		GeneralLedgerBean glopOperation = new GeneralLedgerBean();
		try
		{
			glopOperation.deleteGLEntry(strTransNo);
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new BillException(e.getErrorCode(), e);
		}
		return 1;
	}
	public static void main(String [] args)
	{
		String scode="";
		Timestamp ts=new Timestamp(2005, 1, 1, 1, 1, 1, 1);
			System.out.println("start");
			scode=""+ts.getYear()+ts.getMonth()+ts.getDate();
			System.out.println(scode);
		
	}
}
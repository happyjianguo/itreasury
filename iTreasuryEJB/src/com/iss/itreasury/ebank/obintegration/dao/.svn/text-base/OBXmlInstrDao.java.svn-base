/*
 * Created on 2006-1-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.ebank.obintegration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.settlement.account.dataentity.AccountInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.settlement.query.resultinfo.QueryAccountBalanceResultInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 * @author xintan
 *
 */
public class OBXmlInstrDao {
	/**
	 * 
	 */
	public OBXmlInstrDao() 
	{
		super();
	}
	/**
	 * 取提交人用户ID，若数据包提供的提交人名称在ob_user里有则去其ID，否则取系统管理员的ID
	 * @param ComfirmUserName
	 * @param lClientID
	 * @param strClientCode
	 * @return lUserID
	 * @throws Exception
	 */
	public long getComfirmUser(String ComfirmUserName,long lClientID,String strClientCode) throws Exception
	{
		long lUserID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		try
		{
			conn = Database.getConnection();
			strSQL = " select * from ob_user \n";
			strSQL += " where NSTATUS >0 and NCLIENTID ="+lClientID+" \n";
			strSQL += " and SNAME = ? \n";			
			ps = conn.prepareStatement(strSQL);	
			ps.setString(1,ComfirmUserName);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lUserID = rs.getLong("ID");
			}
			else
			{
				strSQL = " select * from ob_user \n";
				strSQL += " where NSTATUS >0 and NCLIENTID ="+lClientID+" \n";
				strSQL += " and SLOGINNO = ? \n";				
				ps = null;
				rs = null;
				ps = conn.prepareStatement(strSQL);	
				ps.setString(1,strClientCode);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lUserID = rs.getLong("ID");
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}		
		return lUserID;
	}
	/**
	 * 根据客户编号取客户id
	 * @param strClientCode
	 * @return lClientID
	 * @throws Exception
	 */
	public long getClientIDByClientCode(String strClientCode) throws Exception
	{
		long lClientID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		try
		{
			conn = Database.getConnection();
			strSQL = " select client.id ClientID from client \n";
			strSQL += " where client.NSTATUSID >0 \n";
			strSQL += " and client.SCODE = '"+strClientCode.trim()+"' \n";
			//Log.print("strSQL = "+strSQL);	
			ps = conn.prepareStatement(strSQL);	
			rs = ps.executeQuery();
			if (rs.next())
			{
				lClientID = rs.getLong("ClientID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}		
		return lClientID;
	}
	/**
	 * 取账户的当前余额包含可用余额
	 * @param qInfo
	 * @param aInfo
	 * @return
	 * @throws Exception
	 */
	public Collection getCurrenctBalance(QueryAccountWhereInfo qInfo,AccountInfo aInfo) throws  Exception
	{
		StringBuffer  strSQL = new StringBuffer();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v =  new Vector();
		Connection conn = null;
		QueryAccountBalanceResultInfo resInfo = null;
		double dEbankAmount = 0.0;
		OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
		try
		{
			conn =Database.getConnection();
			strSQL.append(" select ");
			strSQL.append(" acct.saccountno AccountNo,subAcct.mbalance Balance,");
			strSQL.append(" subAcct.mUnCheckPaymentAmount UncheckPaymentAmount");
			strSQL.append(" from ");
			strSQL.append(" sett_OfficeTime OfficeTime,client,sett_account acct, sett_subAccount subAcct");
			strSQL.append(" where 1=1");
			strSQL.append(" and acct.nclientid = " +aInfo.getClientID());
			strSQL.append(" and client.id=acct.nclientid");
			strSQL.append(" and acct.id=subAcct.nAccountID ");
			strSQL.append(" and acct.nofficeid=" +aInfo.getOfficeID());
			strSQL.append(" and acct.ncurrencyid="+aInfo.getCurrencyID() );
			strSQL.append(" and OfficeTime.nOfficeID=acct.nOfficeID and OfficeTime.nCurrencyID=acct.nCurrencyID");
			strSQL.append(" and subAcct.NSTATUSID>0 ");
			strSQL.append(" and acct.saccountno= '"+ aInfo.getAccountNo()+"'");
						
			Log.print("SAP-Ebank getCurrenctBalanceSQL--"+strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while(rs!=null&&rs.next())
			{
				//网银已确认，已复核，已签认交易指令金额总计，调用getUsableBalanceByAccountID()
				dEbankAmount = obFinanceInstrDao.getUsableBalanceByAccountID(aInfo.getAccountID(), aInfo.getCurrencyID(), -1);
				Log.print("网银提交金额dEbankAmount=" + dEbankAmount);
				resInfo = new QueryAccountBalanceResultInfo();
				resInfo.setAccountNo(rs.getString("AccountNo"));
				resInfo.setBalance(rs.getDouble("Balance"));
				//未复核金额,包括结算未复核金额,也包括网银已确认，已复核，已签认交易指令金额总计
				resInfo.setUncheckPaymentAmount(rs.getDouble("UncheckPaymentAmount")+dEbankAmount);
				v.add(resInfo);
			}
		} 
		catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());		
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
					if (conn != null)
					{
						conn.close();
						conn = null;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new IException("Gen_E001");
				}
			}		
	
		
		return v.size()>0?v:null;
	}
	/**
	 * 根据帐户号查找帐户信息,已复核，正常状态的账户
	 * @param strAccountNo
	 * @return
	 * @throws Exception
	 */
	public AccountInfo getAccountInfobyAcctNo(String strAccountNo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";
		AccountInfo acctInfo = null;

		try
		{
			conn = Database.getConnection();			
			strSQL = " select a.* from sett_Account a \n";
			strSQL += " where a.NCHECKSTATUSID ="+SETTConstant.AccountCheckStatus.CHECK+" \n";
			strSQL += " and a.NSTATUSID = "+SETTConstant.AccountStatus.NORMAL+" \n";
			if(strAccountNo!=null && strAccountNo.length()>0)
			{
				strSQL += " and a.sAccountNo = '"+strAccountNo.trim()+"' \n";
			}
			//Log.print("strSQL = "+strSQL);	
			ps = conn.prepareStatement(strSQL);	
			rs = ps.executeQuery();
			if (rs.next())
			{
				acctInfo = new AccountInfo();
				acctInfo.setAccountID(rs.getLong("ID")); // 帐户ID
				acctInfo.setAccountNo(rs.getString("sAccountNo")); // 帐户编号
				acctInfo.setOfficeID(rs.getLong("nOfficeID")); // 办事处
				acctInfo.setCurrencyID(rs.getLong("nCurrencyID")); //币种
				acctInfo.setAccountTypeID(rs.getLong("nAccountTypeID")); // 帐户类型
				acctInfo.setClientID(rs.getLong("nClientID")); // 客户
				acctInfo.setAccountName(rs.getString("sName")); // 帐户名称
				acctInfo.setOpenDate(rs.getTimestamp("dtOpen")); // 开户日期
				acctInfo.setInputUserID(rs.getLong("nInputUserID")); // 录入人
				acctInfo.setInputDate(rs.getTimestamp("dtInput")); // 录入日期
				acctInfo.setCheckUserID(rs.getLong("nCheckUserID")); // 复核人
				acctInfo.setCheckDate(rs.getTimestamp("dtCheck")); // 复核日期
				acctInfo.setCheckStatusID(rs.getLong("nCheckStatusID")); // 复核状态
				acctInfo.setStatusID(rs.getLong("nStatusID")); // 帐户状态
				acctInfo.setAbstact(rs.getString("sAbstract")); // 摘要
				acctInfo.setSubject(rs.getString("sSubject")); // 对应科目号
				acctInfo.setMaxSinglePayAmount(rs.getDouble("mMaxSinglePayAmount"));
				// 单笔最高金额限制
				acctInfo.setMinSinglePayAmount(rs.getDouble("mMinSinglePayAmount"));
				// 单笔最低金额限制
				acctInfo.setBatchUpdateID(rs.getLong("nBatchUpdateID"));
				acctInfo.setBatchUpdateTypeID(rs.getLong("nBatchUpdateTypeID"));
				acctInfo.setFinishDate(rs.getTimestamp("dtFinish"));//清户日期
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}		
		return 	acctInfo;
	}
	/**
	 * 根据传入的交易编号获得相对应的业务申请编号返回给SAP
	 * @param sTransNo
	 * @return
	 * @throws Exception
	 */
	public String getApplyCodeByTransNo(String sTransNo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";			
		String strApplyCode = " ";	//业务申请编号,返回给SAP
		try
		{
			conn = Database.getConnection();
			strSQL = "select SAPPLYCODE	 from ob_financeinstr o where 1=1 " +
					" and o.cpf_stransno ='"+sTransNo+"'"+
					" and o.nstatus ="+OBConstant.SettInstrStatus.FINISH;
				
			ps = conn.prepareStatement(strSQL);	
			rs = ps.executeQuery();
			if (rs.next())
			{		
				strApplyCode= rs.getString("SAPPLYCODE");
			}
			if(strApplyCode==null||strApplyCode.equals("null")||strApplyCode.equals(""))
				strApplyCode =" ";
			//收款没有业务申请编号将业务申请编号置为空
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}		
			
	
		return strApplyCode;
		
	}
	/**
	 * 查询一条记录
	 * @param qInfo QueryCapForm
	 * @return FinanceInfo
	 * @throws Exception
	 */
	public FinanceInfo QueryXmlInstr(QueryCapForm qInfo) throws Exception
	{
		FinanceInfo info = new FinanceInfo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";			
		String strApplyCode = "";	//业务申请编号（校验重复用）
		try
		{
			conn = Database.getConnection();
			
			strSQL = " select t.id InstrID,t.sapplycode ApplyCode,t.nstatus InstrStatus,t.cpf_stransno TransNo, \n";			
			strSQL += "  t.cpf_sreject RejectReason,nvl(st.Dtmodify,t.dtexecute) ExecuteDate,t.sNote Note \n";
			strSQL += "  from ob_financeinstr t ,Sett_Transcurrentdeposit st \n";	
			strSQL += "  where t.cpf_stransno=st.Stransno(+) and t.NSTATUS >0 \n";			
			if(qInfo.getClientID()>0)
			{
				//限制导入客户,0003客户只能查到0003客户导入的记录,0021只能导入0021导入的记录
				strSQL += " and t.nclientid = "+qInfo.getClientID()+" \n";
			}
			//执行日
			// 执行日期-从
			if (qInfo.getStartExe() != null && qInfo.getStartExe().trim().length() > 0)
			{
				strSQL += " AND nvl(t.dtexecute,st.Dtmodify) >= ? \n";
			}
			// 执行日期-到
			if (qInfo.getEndExe() != null && qInfo.getEndExe().trim().length() > 0)
			{
				strSQL += " AND nvl(t.dtexecute,st.Dtmodify) <= ? \n";
			}			
			//业务申请编号
			if(qInfo.getApplyCodeFrom() != null && qInfo.getApplyCodeFrom().trim().length() > 0)
			{
				strSQL += " and t.sapplycode >= '"+ qInfo.getApplyCodeFrom().trim()+"' \n";
			}
			if(qInfo.getApplyCodeTo() != null && qInfo.getApplyCodeTo().trim().length()>0)
			{
				strSQL += " and t.sapplycode <= '"+ qInfo.getApplyCodeTo().trim()+"' \n";
			}			
			 //排序
			strSQL += " order by t.sapplycode,ExecuteDate desc,t.nstatus \n";
			ps = conn.prepareStatement(strSQL);
			Log.print(strSQL);
			int nIndex = 1;
			// 执行日期-从
			if (qInfo.getStartExe() != null && qInfo.getStartExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qInfo.getStartExe().trim() + " 00:00:00"));
			}
			// 执行日期-到
			if (qInfo.getEndExe() != null && qInfo.getEndExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qInfo.getEndExe().trim() + " 23:59:59"));
			}				
			rs = ps.executeQuery();			
			if(rs!=null && rs.next())
			{								
				info.setID(rs.getLong("InstrID"));
				info.setApplyCode(rs.getString("ApplyCode"));
				info.setExecuteDate(rs.getTimestamp("ExecuteDate"));
				info.setDealDate(rs.getTimestamp("ExecuteDate"));
				info.setReject(rs.getString("RejectReason"));
				info.setStatus(rs.getLong("InstrStatus"));
				info.setNote(rs.getString("Note"));					
				info.setTransNo(rs.getString("TransNo"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}		
		return info;
	}
	/**
	 * 查询历史帐户余额，
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryAccountBalance(QueryAccountWhereInfo qInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";			
		Vector vResult = new Vector();
		QueryAccountBalanceResultInfo rInfo = null;
		try
		{
			conn = Database.getConnection();
			strSQL = "select sd.mbalance Balance ,sa.saccountno AccountNo ,sd.dtdate BalanceDate from  SETT_DAILYACCOUNTBALANCE sd , SETT_ACCOUNT sa  where 1=1 \n";
			strSQL +=	" and sa.saccountno = '"+ qInfo.getStartAccountNo() +"' \n";
			if(qInfo.getStartQueryDate()!=null)
			{
				strSQL += " and sd.dtdate >= to_date('" + DataFormat.getDateString(qInfo.getStartQueryDate()) + "','yyyy-mm-dd') \n";
			}
			if(qInfo.getEndQueryDate()!=null)
			{
				strSQL += " and sd.dtdate <= to_date('" + DataFormat.getDateString(qInfo.getEndQueryDate()) + "','yyyy-mm-dd') \n";
			}
			strSQL +=	" and  sd.naccountid = sa.id \n";
			strSQL +=	" and sa.nstatusid >0 \n";
			strSQL +=   " order by sd.dtdate asc " ;
			
			Log.print("SAP-Ebank - 查询帐户余额" +strSQL);
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery() ;
			while(rs!=null&&rs.next())
			{
				rInfo = new QueryAccountBalanceResultInfo();
				rInfo.setAccountNo(rs.getString("AccountNo"));
				rInfo.setBalance(rs.getDouble("Balance"));
				rInfo.setBalanceDate(rs.getTimestamp("BalanceDate"));
				vResult.add(rInfo);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());		
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}			
		return vResult.size()>0?vResult:null;
	}
	public boolean isToday(long lOfficeID, long lCurrencyID, Timestamp tsQueryDate)
	{
		boolean b = true;
		Timestamp tsOpenDate = Env.getSystemDate(lOfficeID, lCurrencyID);
		if (tsOpenDate != null && tsQueryDate != null)
		{
			if (tsOpenDate.toString().substring(0, 10).equals(tsQueryDate.toString().substring(0, 10)))
				b = true;
			else
				b = false;
		}
		return b;
	}
	public static void main(String[] args) 
	{
		OBXmlInstrDao dao = new OBXmlInstrDao();
		String strXml = "";
		strXml="<?xml version=\"1.0\" encoding = \"GBK\"?>";
		strXml+="  <Iss_Itreasury>";
		strXml+="    <InstrReq>   ";
		strXml+="	<OperationType>操作类型</OperationType>   ";
		strXml+="		<SystemID>系统标识</SystemID> ";
		strXml+="		<InstrContent>            ";
		strXml+="			<ApplyCode>业务申请编号</ApplyCode>          ";
		strXml+="			<TransType>交易类型</TransType>              ";
		strXml+="			<Amount>金额</Amount>                        ";
		strXml+="			<ExcuteDate>执行日</ExcuteDate>              ";
		strXml+="			<ClientCode>客户编号</ClientCode>            ";
		strXml+="			<PayerAcctNo>付款方账号</PayerAcctNo>      ";
		strXml+="			<PayeeAcctNo>收款方账号</PayeeAcctNo>      ";
		strXml+="			<PayeeAcctName>收款方账户名称</PayeeAcctName>";
		strXml+="			<RemitBankName>汇入行名称</RemitBankName>    ";
		strXml+="			<RemitProvince>汇入省</RemitProvince>        ";
		strXml+="			<RemitCity>汇入市</RemitCity>                ";
		strXml+="			<ComfirmUser>提交人</ComfirmUser>            ";
		strXml+="			<ComfirmTime>提交时间</ComfirmTime>          ";
		strXml+="			<Note>摘要（备注）</Note>	             ";
		strXml+="		</InstrContent>			                     ";
		strXml+="	</InstrReq>    ";
		strXml+="</Iss_Itreasury>   ";
		Log.print(Timestamp.valueOf("2006-1-14 10:50:00"));
		//dao.TransObjToDoc(strXml);
	}
}

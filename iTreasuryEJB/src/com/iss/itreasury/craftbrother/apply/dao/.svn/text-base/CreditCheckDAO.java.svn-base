package com.iss.itreasury.craftbrother.apply.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.iss.itreasury.craftbrother.credit.dao.CreditSettingDAO;
import com.iss.itreasury.craftbrother.credit.dataentity.CreditSettingInfo;
import com.iss.itreasury.craftbrother.util.CRANameRef;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.transdiscountapply.dataentity.TransDiscountApplyInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.securities.apply.dataentity.ApplyInfo;
import com.iss.itreasury.securities.exception.SecuritiesDAOException;
import com.iss.itreasury.securities.util.NameRef;
import com.iss.itreasury.securities.util.SECConstant;
import com.iss.itreasury.securities.util.SecuritiesDAO;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

public class CreditCheckDAO extends SecuritiesDAO
{
	public CreditCheckDAO()
	{
		super("cra_creditlimit");
	}
	
	/**
	 * 查询授信额度（转贴现）
	 * @param lTransTypeID交易类型
	 * @param lCreditClientID授信方客户id
	 * @param lCreditedClientID//被授信方客户id
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CreditSettingInfo getCreditAmount ( long lClientID, long lTransDiscountInOrOutID,long transDiscountTypeId, Timestamp transactionDate) throws SecuritiesDAOException
	{
		double creditAmount = 0.0;
		String strSQL = "";
		CreditSettingInfo info = new CreditSettingInfo();
		
		try
		{
			initDAO();
			long financeID = -1;
			financeID = CRANameRef.getPartenerInfo(1).getClientID();
			strSQL = "select id,creditamount,startdate,enddate from cra_creditlimit";
			strSQL += " where statusid=" + CRAconstant.TransactionStatus.APPROVALED;
			//由于转贴现交易的结束时间不是很明确，所以只通过交易开始时间来确定授信区间
			strSQL += " and STARTDATE<=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.IN)
			{
				strSQL += " and creditedclientid=" + lClientID;
			}
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.OUT)
			{
				strSQL += " and creditclientid=" + lClientID;
				strSQL +=" and creditedClientid=" + financeID;
			}
			if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
			{
			    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE;
			}
			else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
			{
				strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_BREAK;
			}
			System.out.println("------转贴现授信验证的SQL--------:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	info.setID(rs.getLong("id"));
            	info.setAmount(rs.getDouble("creditamount"));
            	info.setStartDate(rs.getTimestamp("startdate"));
            	info.setEndDate(rs.getTimestamp("enddate"));
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return info;
	}
	/**
	 * 查询授信额度（转贴现）
	 * @param lTransTypeID交易类型
	 * @param lCreditClientID授信方客户id
	 * @param lCreditedClientID//被授信方客户id
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CreditSettingInfo getCreditAmountInfo ( long lClientID, long lTransDiscountInOrOutID,long transDiscountTypeId) throws SecuritiesDAOException
	{
		double creditAmount = 0.0;
		String strSQL = "";
		CreditSettingInfo info = new CreditSettingInfo();
		
		try
		{
			initDAO();
			long financeID = -1;
			financeID = CRANameRef.getPartenerInfo(1).getClientID();
			strSQL = "select id,creditamount,startdate,enddate from cra_creditlimit";
			strSQL += " where statusid=" + CRAconstant.TransactionStatus.APPROVALED;
			//由于转贴现交易的结束时间不是很明确，所以只通过交易开始时间来确定授信区间
			//strSQL += " and STARTDATE<=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			//strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.IN)
			{
				strSQL += " and creditedclientid=" + lClientID;
			}
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.OUT)
			{
				strSQL += " and creditclientid=" + lClientID;
				strSQL +=" and creditedClientid=" + financeID;
			}
			if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
			{
			    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE;
			}
			else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
			{
				strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_BREAK;
			}
			System.out.println("------转贴现授信验证的SQL--------:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	info.setID(rs.getLong("id"));
            	info.setAmount(rs.getDouble("creditamount"));
            	info.setStartDate(rs.getTimestamp("startdate"));
            	info.setEndDate(rs.getTimestamp("enddate"));
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return info;
	}
	
	/**
	 * 查询授信额度（转贴现）
	 * @param lTransTypeID交易类型
	 * @param lCreditClientID授信方客户id
	 * @param lCreditedClientID//被授信方客户id
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CreditSettingInfo getCreditAmount ( long lClientID, long lTransDiscountInOrOutID,long transDiscountTypeId,long officeid) throws SecuritiesDAOException
	{
		double creditAmount = 0.0;
		String strSQL = "";
		CreditSettingInfo info = new CreditSettingInfo();
		
		try
		{
			initDAO();
			long financeID = -1;
			financeID = CRANameRef.getPartenerInfo(officeid).getClientID();
			strSQL = "select id,creditamount,startdate,enddate from cra_creditlimit";
			strSQL += " where statusid in("+ CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED;
			//由于转贴现交易的结束时间不是很明确，所以只通过交易开始时间来确定授信区间
			//strSQL += " and STARTDATE<=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			//strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			strSQL +=")";
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.IN)
			{
				strSQL += " and creditedclientid=" + lClientID;
				if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
				{
				    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE;
				}
				else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
				{
					strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_BREAK;
				}
				
				
			}
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.OUT)
			{
				strSQL += " and creditclientid=" + lClientID;
				strSQL +=" and creditedClientid=" + financeID;
				
				if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
				{
				    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_AVERAGE_REPURCHASE;
				}
				else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
				{
					strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_AVERAGE_BREAK;
				}
			}
			//if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
			//{
			//    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE;
			//}
			//else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
			//{
			//	strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_BREAK;
			//}
			System.out.println("------转贴现授信验证的SQL1--------:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	info.setID(rs.getLong("id"));
            	info.setAmount(rs.getDouble("creditamount"));
            	info.setStartDate(rs.getTimestamp("startdate"));
            	info.setEndDate(rs.getTimestamp("enddate"));
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return info;
	}
	/**
	 * 查询授信额度（转贴现）
	 * @param lTransTypeID交易类型
	 * @param lCreditClientID授信方客户id
	 * @param lCreditedClientID//被授信方客户id
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public CreditSettingInfo getCreditAmountHL ( long lClientID, long lTransDiscountInOrOutID,long transDiscountTypeId,long officeid) throws SecuritiesDAOException
	{
		double creditAmount = 0.0;
		String strSQL = "";
		CreditSettingInfo info = new CreditSettingInfo();
		
		try
		{
			initDAO();
			long financeID = -1;
			financeID = CRANameRef.getPartenerInfo(officeid).getClientID();
			strSQL = "select id,creditamount,startdate,enddate from cra_creditlimit";
			strSQL += " where statusid in("+ CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED;
			//由于转贴现交易的结束时间不是很明确，所以只通过交易开始时间来确定授信区间
			//strSQL += " and STARTDATE<=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			//strSQL += " and ENDDATE>=To_Date('" + DataFormat.getDateString(transactionDate) + "','yyyy-mm-dd')";
			strSQL +=")";
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.IN)
			{
				strSQL += " and creditedclientid=" + lClientID;
				if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
				{
				    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_REPURCHASE;
				}
				else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
				{
					strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_INVEST_BREAK;
				}
				
				
			}
			if(lTransDiscountInOrOutID == LOANConstant.TransDiscountInOrOut.OUT)
			{
				strSQL += " and creditclientid=" + lClientID;
				strSQL +=" and creditedClientid=" + financeID;
				
				if(transDiscountTypeId==LOANConstant.TransDiscountType.REPURCHASE)
				{
				    strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_AVERAGE_REPURCHASE;
				}
				else if(transDiscountTypeId==LOANConstant.TransDiscountType.BUYBREAK)
				{
					strSQL += " and transactiontype=" + CRAconstant.TransactionType.ZTX_AVERAGE_BREAK;
				}
			}
			strSQL += " order by ENDDATE desc";
			System.out.println("------转贴现授信验证的SQL1--------:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            if(rs.next())
            {
            	info.setID(rs.getLong("id"));
            	info.setAmount(rs.getDouble("creditamount"));
            	info.setStartDate(rs.getTimestamp("startdate"));
            	info.setEndDate(rs.getTimestamp("enddate"));
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return info;
	}
	
	/**
	 * 查询已用授信额度的申请金额总和
	 * @param lTransTypeID	交易类型
	 * @param lBorrowClientID
	 * @param lTransDiscountInOrOutID	转贴现类型（买入/卖出）
	 * @param creditStartDate	关联授信开始时间
	 * @param creditEndDate	关联授信结束时间
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public double getApplyedAmount ( long lBorrowClientID, long lTransDiscountInOrOutID,long transDiscountTypeId , Timestamp creditStartDate, Timestamp creditEndDate) throws SecuritiesDAOException
	{
		double applyedAmount = 0.0;
		
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = " select sum(mloanamount) sumLoanAmount from cra_loanform";
			strSQL += " where nstatusid in(" + LOANConstant.LoanStatus.SAVE + "," + LOANConstant.LoanStatus.SUBMIT 
			 + "," + LOANConstant.LoanStatus.APPROVALING + ","  + LOANConstant.LoanStatus.CHECK + ")";
			strSQL += " and dtstartdate>=To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
			//由于有时 dtenddate 没有值，这样就没法限定落在那个交易区间，所以直接根据交易开始时间来确定授信区间
			strSQL += " and dtstartdate<=To_Date('"+DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
			strSQL += " and ntypeid=" + LOANConstant.LoanType.ZTX;
			strSQL += " and NDISCOUNTTYPEID = "+transDiscountTypeId;
			// 转贴现申请中汇票为银行承兑汇票时申请金额不占用授信额度
			strSQL += " and nbizacceptpo > 0";
			strSQL += " and NINOROUT = "+lTransDiscountInOrOutID;
			strSQL += " and NBORROWCLIENTID = "+lBorrowClientID;
		    System.out.println("-----------查询转贴现已累积占用授信额度的SQL:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while(rs.next())
            {
            	applyedAmount = rs.getDouble("sumLoanAmount");
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return applyedAmount;
	}
	/**
	 * 查询已用授信额度的申请金额总和
	 * @param lTransTypeID	交易类型
	 * @param lBorrowClientID
	 * @param lTransDiscountInOrOutID	转贴现类型（买入/卖出）
	 * @param creditStartDate	关联授信开始时间
	 * @param creditEndDate	关联授信结束时间
	 * @return
	 * @throws SecuritiesDAOException
	 */
	public double getApplyedAmountHL( TransDiscountApplyInfo info, Timestamp creditStartDate, Timestamp creditEndDate) throws SecuritiesDAOException
	{
		double applyedAmount = 0.0;
		
		String strSQL = "";
		try
		{
			initDAO();
			strSQL = " select sum(mloanamount) sumLoanAmount from cra_loanform";
			strSQL += " where nstatusid in(" + LOANConstant.LoanStatus.SAVE + "," + LOANConstant.LoanStatus.SUBMIT 
			 + "," + LOANConstant.LoanStatus.APPROVALING + ","  + LOANConstant.LoanStatus.CHECK + ")";
			strSQL += " and dtstartdate>=To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')";
			//由于有时 dtenddate 没有值，这样就没法限定落在那个交易区间，所以直接根据交易开始时间来确定授信区间
			strSQL += " and dtstartdate<=To_Date('"+DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
			strSQL += " and ntypeid=" + LOANConstant.LoanType.ZTX;
			strSQL += " and NDISCOUNTTYPEID = "+info.getDiscountTypeId();
			// 转贴现申请中汇票为银行承兑汇票时申请金额不占用授信额度
			strSQL += " and nbizacceptpo > 0";
			strSQL += " and NINOROUT = "+ info.getInOrOut();
			strSQL += " and NBORROWCLIENTID = "+info.getBorrowClientId();
			//如果是修改保存
			if(info.getId() > 0){
				strSQL += " and id != "+info.getId();
			}
		    System.out.println("-----------查询转贴现已累积占用授信额度的SQL:"+strSQL);
			prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while(rs.next())
            {
            	applyedAmount = rs.getDouble("sumLoanAmount");
            }
		}
		catch (SQLException e1)
        {
            e1.printStackTrace();
        }
		catch (ITreasuryDAOException e)	{
			e.printStackTrace();
			throw new SecuritiesDAOException(e);
		}
		finally
        {
            try
            {
                finalizeDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                e.printStackTrace();
                throw new SecuritiesDAOException(e);
            }
        }
		
		return applyedAmount;
	}
	public String checkAttormCredit(ApplyInfo info) throws Exception
	{
		String strSQL = "";
		Connection conn =null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		String sReturn ="";
		double creditAmount = 0.00;
		double usedCreditAmount=0.00;
		double useableCreditAmount=0.00;
		try
		{
            conn = Database.getConnection();
            //开始查询授信设置信息
            CreditSettingDAO creditDao =new CreditSettingDAO();
            CreditSettingInfo creditInfo =null;
            long counterPartID = info.getCounterpartId();
            String counterpartName = NameRef.getCounterpartNameByID(counterPartID);
            //creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),counterPartID,-1,1,CRAconstant.TransactionType.AVERAGE_NOTIFY,counterpartName,"财务公司");
            creditInfo = creditDao.findCreditAmountByDate(info.getTransactionStartDate(),info.getTransactionEndDate(),counterPartID,-1,1,info.getTransactionTypeId(),counterpartName,"财务公司");
            creditAmount = creditInfo.getAmount()*10000;
            Timestamp creditStartDate = creditInfo.getStartDate();
            Timestamp creditEndDate= creditInfo.getEndDate();
            
            //查询所在授信区间内已用授信额度
         	strSQL = "select nvl(sum(AMOUNT),0) usedCreditAmount from sec_applyform"
        		//+" where TRANSACTIONTYPEID ="+CRAconstant.TransactionType.AVERAGE_NOTIFY
         		+" where TRANSACTIONTYPEID = "+info.getTransactionTypeId()
        		+" and( STATUSID ="+SECConstant.ApplyFormStatus.SUBMITED
        		+" or STATUSID ="+SECConstant.ApplyFormStatus.CHECKED
        		+" or STATUSID ="+SECConstant.ApplyFormStatus.APPROVALING
        		+" or STATUSID ="+SECConstant.ApplyFormStatus.USED+")"
        		+" and COUNTERPARTID ="+counterPartID
        		+" and TRANSACTIONSTARTDATE>= To_Date('" + DataFormat.getDateString(creditStartDate) + "','yyyy-mm-dd')"
        		//modified by qihuazhou 2009-4-07 华联修改，只统计交易开始日期在授信区间内
        	    //+" and TRANSACTIONENDDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
        		+" and TRANSACTIONSTARTDATE<=To_Date('" + DataFormat.getDateString(creditEndDate) + "','yyyy-mm-dd')";
         	if(info.getId()>0)
         	{
         		strSQL+=" and id!="+info.getId();
         	}
			ps = conn.prepareStatement(strSQL);
            rs = ps.executeQuery();
         	if (rs.next())
        	{
        		usedCreditAmount = rs.getDouble("usedCreditAmount");
        	}
         	rs.close();
         	rs = null;
         	ps.close();
         	ps = null;
         	conn.close();
         	conn = null;
         	useableCreditAmount = creditAmount-usedCreditAmount;
         	if(info.getAmount()>useableCreditAmount)
         	{
         		sReturn = " 该资产转让申请的回购金额"+DataFormat.formatDisabledAmount(info.getAmount())+"元，超过了财务公司对交易对手\""
 			   + counterpartName+"\"对于合同买入（回购）交易类型授信的可用授信额度，目前该交易对手的可用授信额度为"
 			   +DataFormat.formatDisabledAmount(useableCreditAmount,2)+"元"
		       +" 请修改后再提交";
         	}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
        {   

			if(rs!=null)
			{
				rs.close();
				rs = null;
			}
			if(ps!=null)
			{
				ps.close();
				ps = null;
			}
			if(conn!=null)
			{
				conn.close();
				conn = null;
			}
        }
		return sReturn;
	}
}

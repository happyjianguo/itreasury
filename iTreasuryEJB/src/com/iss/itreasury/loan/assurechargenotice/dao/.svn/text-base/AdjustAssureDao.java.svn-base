package com.iss.itreasury.loan.assurechargenotice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureAdjustInfo;
import com.iss.itreasury.loan.assurechargenotice.dataentity.AssureChargeNoticeInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.loancommonsetting.dao.LoanCommonSettingDao;
import com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

public class AdjustAssureDao extends LoanDAO 
{
	protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	public AdjustAssureDao() 
	{
		super("LOAN_ASSUREADJUSTDETAIL");
		// TODO Auto-generated constructor stub
	}

	/*
	 * 
	 */
	public Collection findByMultiOption(AssureAdjustInfo qInfo) throws LoanDAOException
	{
		String strSelect = "";
		String strSQL = "";
		String strSQL1 = "";
		Vector v = new Vector();

		Timestamp startDate = qInfo.getEffectDateFrom();
		Timestamp endDate = qInfo.getEffectDateTo();
		long queryPurpose = qInfo.getQueryPurpose();
		long statusID = qInfo.getStatusID();
		long userID = qInfo.getInputUserID();
		String checkUser = qInfo.getCheckUser();
		long currencyID = qInfo.getCurrencyID();
		long officeID = qInfo.getOfficeID();
		
		long pageLineCount = qInfo.getPageLineCount();
		long pageNo = qInfo.getPageNo();
		long orderParam = qInfo.getOrderParam();
		long desc = qInfo.getDesc();
		String orderParamString = qInfo.getOrderParamString();
		long recordCount = -1;
		long pageCount = -1;
		long rowNumStart = -1;
		long rowNumEnd = -1;
		
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            //计算记录总数				
            if (queryPurpose == 1) //for modify
            {
            	strSQL = "";
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSUREADJUSTDETAIL a,Loan_ContractForm b,Client c "
            			+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) "
            			+ " and a.StatusID >= "
            			+ LOANConstant.AssureChargeNoticeStatus.SUBMIT
            			+ " and a.StatusID <= "
            			+ LOANConstant.AssureChargeNoticeStatus.CHECK
            			+ " and a.InputUserID = "
            			+ userID;

            	strSQL += " and a.nextCheckLevel = 1 ";
            	strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;

            }
            else if (queryPurpose == 2) //for examine
            {
            	strSelect = " select count(*) ";
            	strSQL = " from LOAN_ASSUREADJUSTDETAIL a,Loan_ContractForm b,Client c " 
            	    	+ " where a.ContractID = b.ID and b.nBorrowClientID = c.ID(+) ";
            	    	
            	if (statusID == LOANConstant.AssureChargeNoticeStatus.SUBMIT)
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.SUBMIT;
            		strSQL += " and " + checkUser;
            	}
            	else if(statusID == LOANConstant.AssureChargeNoticeStatus.CHECK)
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.CHECK;
            	}
            	else if(statusID == LOANConstant.AssureChargeNoticeStatus.REFUSE)
            	{
            		strSQL += " and a.StatusID = " + LOANConstant.AssureChargeNoticeStatus.REFUSE;
            	}
            }

            //////////////////////查询条件////////////////////////////////////////////////////		
            if (officeID > 0)
            {
            	strSQL += " and a.OfficeID = " + officeID;
            }
            if (currencyID > 0)
            {
            	strSQL += " and a.currencyID = " + currencyID;
            }
            if (startDate != null)
            {
            	strSQL += " and to_char(a.EffectDate,'yyyy-mm-dd') >= '" + DataFormat.getDateString(startDate) + "'";
            }
            if (endDate != null)
            {
            	strSQL += " and to_char(a.EffectDate,'yyyy-mm-dd') <= '" + DataFormat.getDateString(endDate) + "'";
            }
            
            ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
            int nIndex = 0;
            nIndex = orderParamString.indexOf(".");
            if (nIndex > 0)
            {
            	if (orderParamString.substring(0, nIndex).toLowerCase().equals("LOAN_ASSUREADJUSTDETAIL"))
            	{
            		strSQL += " order by a." + orderParamString.substring(nIndex + 1);
            	} 
            	else if (orderParamString.substring(0, nIndex).toLowerCase().equals("loan_contractform"))
            	{
            		strSQL += " order by b." + orderParamString.substring(nIndex + 1);
            	}
            	else if (orderParamString.substring(0, nIndex).toLowerCase().equals("client"))
            	{
            		strSQL += " order by c." + orderParamString.substring(nIndex + 1);
            	}
            }
            else
            {
            	strSQL += " order by a.ID";
            }
            if (desc == Constant.PageControl.CODE_ASCORDESC_DESC)
            {
            	strSQL += " desc";
            }
            log4j.debug(strSelect + strSQL);
            try
            {
            	prepareStatement(strSelect + strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		recordCount = rs.getLong(1);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询通知单笔数产生错误", e);
            }
            recordCount = recordCount / pageLineCount;
            if ((recordCount % pageLineCount) != 0)
            {
                recordCount++;
            }
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //返回需求的结果集
            rowNumStart = (pageNo - 1) * pageLineCount + 1;
            rowNumEnd = rowNumStart + pageLineCount - 1;
            strSelect = " select a.*,b.sContractCode,c.sName,c.id ClientID ";
            strSQL = " select * from ( select aa.*,rownum r from ( " + strSelect + strSQL;
            strSQL += " ) aa ) where r between " + rowNumStart + " and " + rowNumEnd;
            log4j.debug(strSQL);
            System.out.println(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs1 = executeQuery();
            	while (rs1 != null && rs1.next())
            	{
            		System.out.print("here");
            		AssureAdjustInfo info = new AssureAdjustInfo();
            		info.setId(rs1.getLong("ID")); //id
            		info.setContractID(rs1.getLong("CONTRACTID")); //CONTRACTID
            		info.setOfficeID(rs1.getLong("OfficeID")); //办事处
            		info.setCurrencyID(rs1.getLong("CurrencyID")); //币种
            		info.setCode(rs1.getString("Code")); //编号    
            		info.setAssureChargeFormID(rs1.getLong("AssureChargeFormID"));
            		info.setEffectDate(rs1.getTimestamp("EffectDate"));
            		info.setAdjustEndDate(rs1.getTimestamp("AdjustEndDate"));
            		info.setAdjustRate(rs1.getDouble("AdjustRate"));
            		info.setIsInterest(rs1.getLong("IsInterest"));
            		info.setReason(rs1.getString("Reason"));
            		info.setNextCheckUserID(rs1.getLong("NextCheckUserID"));
            		info.setNextCheckLevel(rs1.getLong("NextCheckLevel"));
            		info.setInputUserID(rs1.getLong("InputUserID")); //录入人
            		info.setInputDate(rs1.getTimestamp("InputDate")); //录入时间
            		info.setStatusID(rs1.getLong("StatusID")); //状态

					//表中没有的字段
            		info.setClientID(rs1.getLong("ClientID"));
            		info.setClientName(rs1.getString("sName")); //
            		info.setContractCode(rs1.getString("sContractCode")); //            		
            		info.setRecordCount(recordCount); //记录数
            		info.setPageCount(pageCount); //页数
            		
            		AssureChargeNoticeDao dao = new AssureChargeNoticeDao();
            		info.setNoticeInfo((AssureChargeNoticeInfo)dao.findByID(rs1.getLong("AssureChargeFormID"),AssureChargeNoticeInfo.class));
            		
            		v.add(info);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("批量查询保证金产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("批量查询保证金产生错误", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		return (v.size() > 0 ? v : null);
	}
	
	public long check(ApprovalTracingInfo ATInfo) throws LoanDAOException
	{
		long lMaxID = -1;
		long lSerialID = -1;
		long lStatusID = -1;
		long lResultID = -1;
		String strSQL = "";
		//定义相应操作常量
		//模块类型
		long lModuleID = ATInfo.getModuleID();
		//业务类型
		long lLoanTypeID = ATInfo.getLoanTypeID();
		//操作类型
		long lActionID = ATInfo.getActionID();
		long lApprovalContentID = ATInfo.getApprovalContentID();
		long lNextUserID = ATInfo.getNextUserID();
		//long lNextLevel = ATInfo.getNextLevel();
		long lApprovalID = ATInfo.getApprovalID();
		long lUserID = ATInfo.getInputUserID();
		
		AssureAdjustInfo aInfo = new AssureAdjustInfo();
		AssureAdjustInfo tempInfo = new AssureAdjustInfo();
		try
        {
            tempInfo = (AssureAdjustInfo) findByID(lApprovalContentID,tempInfo.getClass());
        } 
		catch (ITreasuryDAOException e)
		{
			throw new LoanDAOException(e);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.REJECT) //拒绝
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.REFUSE);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECK) //审批
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(lNextUserID);
			//aInfo.setNextCheckLevel(lNextLevel+1);
			//aInfo.setNextCheckLevel(tempInfo.getNextCheckLevel() + 1);
			
			ApprovalDelegation appbiz = new ApprovalDelegation();
			try 
			{
				
				aInfo.setNextCheckLevel(appbiz.findApprovalUserLevel(lApprovalID,lNextUserID));
				
			} 
			catch (Exception e) {
			}
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.CHECKOVER) //审批&&最后
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.CHECK);
			aInfo.setNextCheckUserID(lNextUserID);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
			//审批完成后需要做的操作
			//doAfterCheckOver(lApprovalContentID);
		}
		if (ATInfo.getCheckActionID() == LOANConstant.Actions.RETURN) //修改
		{
			aInfo.setId(lApprovalContentID);
			aInfo.setStatusID(LOANConstant.AssureChargeNoticeStatus.SUBMIT);
			aInfo.setNextCheckUserID(ATInfo.getInputUserID());
			//置下一级审核为1
			aInfo.setNextCheckLevel(1);
			try
			{
				update(aInfo);
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		log4j.debug("check end");

		return lApprovalContentID;
	}
	/*
	 *  (non-Javadoc)
	 * 
	 */
	public String getApplyCode(long lContractID) throws LoanDAOException
	{
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		Timestamp tsToday = Env.getSystemDateTime();
		String strYear = DataFormat.getDateString(tsToday).substring(2, 4);
		try
        {
            try
            {
            	initDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
            strSQL = " select max(nvl(Code,0)) Code from LOAN_ASSUREADJUSTDETAIL where ContractID = " + lContractID;
            log4j.debug(strSQL);
            try
            {
            	prepareStatement(strSQL);
            	ResultSet rs = executeQuery();
            	if (rs != null && rs.next())
            	{
            		strCode = rs.getString(1);
            		log4j.debug(strCode);
            		if (strCode != null && strCode.length() > 0)
            		{
            			lCode = Long.parseLong(strCode) + 1;
            		}
            		else
            		{
            			lCode = 1;
            		}
            		strCode = DataFormat.formatInt(lCode, 3, true);
            	}
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException("生成通知单编号产生错误", e);
            }
            catch (SQLException e)
            {
            	throw new LoanDAOException("生成通知单编号产生错误", e);
            }
            try
            {
            	finalizeDAO();
            }
            catch (ITreasuryDAOException e)
            {
            	throw new LoanDAOException(e);
            }
        } 
		catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		finally
		{
			try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }			
		}
		log4j.debug(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}

	/*
	 * 判断在LOAN_ASSUREADJUSTDETAIL表中是否有生效日期大于等于本次审核记录的生效日的日期
	 * 如果有，则将原有记录置为无效
	 */
	public void doAfterCheckOver(long lApplyID) throws LoanDAOException
	{
		String strSQL = "";
		long lStatusID = -1;
		long lContractID = -1;
		try
		{
			try
			{
				initDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
			
			AssureAdjustInfo info = new AssureAdjustInfo();
			info = (AssureAdjustInfo)this.findByID(lApplyID,info.getClass());
			
			strSQL = " select a.id from LOAN_ASSUREADJUSTDETAIL a "
				+ " where a.contractid = "+info.getContractID()
				+ " and a.statusid = " + LOANConstant.AssureChargeNoticeStatus.CHECK
				+ " and a.effectdate > to_date('"+DataFormat.formatDate(info.getEffectDate())+"','yyyy-mm-dd')"; 
			log4j.debug(strSQL);
			try
			{
				prepareStatement(strSQL);
				ResultSet rs = executeQuery();
				while(rs!=null && rs.next())
				{
					AssureAdjustInfo tempInfo = new AssureAdjustInfo();
					tempInfo.setId(rs.getLong(1));
				    info.setStatusID(LOANConstant.AssureChargeNoticeStatus.CANCEL);
				    this.update(info);
				}
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			catch (SQLException e)
			{
				throw new LoanDAOException("取消通知单（更改合同状态）产生错误", e);
			}
			try
			{
				finalizeDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new LoanDAOException(e);
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				finalizeDAO();
			} catch (ITreasuryDAOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
	}	
	public static void main(String[] args)
	{
		AdjustAssureDao dao = new AdjustAssureDao();

		try
		{
			AssureAdjustInfo info = new AssureAdjustInfo();
			info.setQueryPurpose(1);
			info.setInputUserID(1);
			info.setOfficeID(1);
			info.setCurrencyID(1);
			//info.setPageCount(1);
			info.setPageNo(1);
			info.setPageLineCount(10);
			
			//Collection c = dao.findByMultiOption(info);
			info = (AssureAdjustInfo)dao.findByID(1,info.getClass());
			
			System.out.println("ok");
			System.out.println("ok = "+ dao.getApplyCode(47));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

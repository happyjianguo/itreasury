package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetialResultInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetailConditionInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.NoticeQueryInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeDetailInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.loan.loaninterestsetting.dataentity.AdjustInterestConditionInfo;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountLoanInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestCalculation;
import com.iss.itreasury.settlement.transloan.dataentity.TransRepaymentLoanInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.craftbrother.util.CRAconstant;

/**
 * 收款通知单代收成员单位dao
 * @author zcwang
 * @version 1.1
 */
public class ReceiveTransferNoticeDao extends ITreasuryDAO
{
	public ReceiveTransferNoticeDao()
	{
		super("CRA_TRANSFERNOTICEFORM");
	}
	public ReceiveTransferNoticeDao(Connection con)
	{
		super("CRA_TRANSFERNOTICEFORM",con);
	}
	public StringBuffer m_sbSelect   = null;
	public StringBuffer m_sbFrom     = null;
	public StringBuffer m_sbWhere    = null;
	public StringBuffer m_sbOrderBy  = null;
	
	/**
	 * 信贷转让通知单查询
	 * @param NoticeQueryInfo
	 * @return
	 * @throws IException
	 */
	public PageLoader findByConditions(NoticeQueryInfo queryInfo) throws IException
	{
		getNoticeSQL(queryInfo);
        PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader
		(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo",
			null
		);
		pageLoader.setOrderBy(m_sbOrderBy.toString());
		
		return pageLoader;
	}
	
	/**
	 * 信贷转让通知单查询SQL
	 * @param queryInfo
	 */
	public void getNoticeSQL(NoticeQueryInfo queryInfo)
	{
        //select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" notice.ID, notice.NOTICECODE, notice.NOTICETYPEID, notice.ISURROGATEPAY, \n");
		m_sbSelect.append(" notice.CRACONTRACTID, cracontract.CONTRACTCODE CRACONTRACTCODE, notice.AMOUNT, notice.INTEREST, \n");
		m_sbSelect.append(" notice.MCOMMISSION, notice.INPUTUSERID, USERINFO.SNAME INPUTUSERNAME, notice.INPUTDATE, notice.STATUSID \n");
		
        //from
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" CRA_TRANSFERNOTICEFORM notice, CRA_TRANSFERCONTRACTFORM cracontract, USERINFO \n");
		
        //where
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" 1 = 1 ");
		m_sbWhere.append(" and notice.CRACONTRACTID = cracontract.ID \n");
		m_sbWhere.append(" and notice.INPUTUSERID = USERINFO.ID \n");
		
		if(queryInfo.getNoticeTypeId()>0)
		{
			m_sbWhere.append(" and notice.NOTICETYPEID = "+queryInfo.getNoticeTypeId()+"\n");
		}
		if(queryInfo.getCracontractIdFrom()>0)
		{
			m_sbWhere.append(" and notice.CRACONTRACTID >= "+queryInfo.getCracontractIdFrom()+"\n");
		}
		if(queryInfo.getCracontractIdTo()>0)
		{
			m_sbWhere.append(" and notice.CRACONTRACTID <= "+queryInfo.getCracontractIdTo()+"\n");
		}
		if(queryInfo.getStartAmount()>0.00)
		{
			m_sbWhere.append(" and notice.AMOUNT >= "+queryInfo.getStartAmount()+"\n");
		}
		if(queryInfo.getEndAmount()>0.00)
		{
			m_sbWhere.append(" and notice.AMOUNT <= "+queryInfo.getEndAmount()+"\n");
		}
		if(queryInfo.getStartInputDate()!=null)
		{
			m_sbWhere.append(" and notice.INPUTDATE >= to_date('"+queryInfo.getStartInputDate()+"','yyyy-mm-dd') \n");
		}
		if(queryInfo.getEndInputDate()!=null)
		{
			m_sbWhere.append(" and notice.INPUTDATE <= to_date('"+queryInfo.getEndInputDate()+"','yyyy-mm-dd') \n");
		}
		if(queryInfo.getIsurrogatePay()>0)
		{
			m_sbWhere.append(" and notice.ISURROGATEPAY = "+queryInfo.getIsurrogatePay()+"\n");
		}
		
		m_sbWhere.append(" and notice.STATUSID <> "+CRAconstant.TransactionStatus.DELETED+"\n");
		m_sbWhere.append(" and cracontract.STATUSID <> "+CRAconstant.TransactionStatus.DELETED+"\n");
		
        //Order By 
		m_sbOrderBy = new StringBuffer();
		m_sbOrderBy.append(" order by notice.NOTICECODE desc ");
	}
	/**
	 * 方法说明：生成并返回通知单编号(新)
	 * add by xwhe 2009-7-2
	 * @param lOfficeID
	 * @param lCurrencyID
	 * @return 
	 * @throws SQLException
	 */
	public String getNewTransactionNo(long lOfficeID, long lCurrencyID) throws IException, Exception
	{
		String strTransNo = "";
		HashMap map = new HashMap();
		try
		{
			map.put("officeID",String.valueOf(lOfficeID));
			map.put("currencyID",String.valueOf(lCurrencyID));
			map.put("moduleID",String.valueOf(Constant.ModuleType.CRAFTBROTHER));
			map.put("transTypeID",String.valueOf(Constant.ApprovalAction.CRA_TRANSLOANNOTICE));
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
	 * @deprecated 代收不再使用该方法
	 * 
	 *  查询收款通知单,转让合同明细组装数据
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public Collection findContractOrNoticeDetial(ContractAndNoticeDetailConditionInfo conditionInfo) throws IException
	{
		Collection coll = null;
		String sql = "";
		sql +="select a.id detailID,a.TRANSFERCONTRACTFORMID ,a.TRANSFERAMOUNT, a.loanContractID ,b.sContractCode loanContractCode,b.nborrowclientid,c.id loanPayNoticeID,c.scode loanPayNoticeCode  \n";
		sql +="from cra_contractdetail a, loan_contractform b, loan_payform c  \n";
		sql +=" where a.loancontractid = b.id AND a.loannoteid = c.id  and a.statusid >0 and a.TRANSFERCONTRACTFORMID=? and a.officeid = ? and a.currencyid = ?  \n";
		initDAO();
		prepareStatement(sql);
		int index = 1;
		try {
			transPS.setLong(index++, conditionInfo.getTransferContractFormID());
			transPS.setLong(index++,conditionInfo.getOfficeID());
			transPS.setLong(index++,conditionInfo.getCurrencyID());
			executeQuery();
			coll = new ArrayList();
			while(transRS.next())
			{
				ContractAndNoticeDetialResultInfo resultInfo = new ContractAndNoticeDetialResultInfo();
				resultInfo.setOfficeID(conditionInfo.getOfficeID());
				resultInfo.setCurrencyID(conditionInfo.getCurrencyID());
				resultInfo.setContractDetailID(transRS.getLong("detailID"));
				resultInfo.setLoancontractCode(transRS.getString("loanContractCode"));
				resultInfo.setNborrowclientid(transRS.getLong("nborrowclientid"));
				resultInfo.setLoanContractID(transRS.getLong("loanContractID"));
				resultInfo.setLoanPayNoticeID(transRS.getLong("loanPayNoticeID"));
				resultInfo.setTransferContractFormId(transRS.getLong("TRANSFERCONTRACTFORMID"));
				resultInfo.setTransferAmount(transRS.getDouble("TRANSFERAMOUNT"));
				resultInfo.setClearInterestDate(conditionInfo.getClearInterestDate());
				resultInfo.setLastClearInterestDate(getTransferClientAccountClearInterest(resultInfo,transConn));
				
				resultInfo.setNoticeFormId(conditionInfo.getTransferNoticeFormId());
				TransferNoticeDetailInfo noticeResultInfo = getNoticeDetailInfo(resultInfo,transConn);
				if(noticeResultInfo!=null && conditionInfo.getTransferNoticeFormId()>0)
				{
					resultInfo.setChecked(true);
					//minzhao重新定义转让余额,该方法待修改
					resultInfo.setSellamount(resultInfo.getTransferAmount() - getRepayedNoticeContractdetailAmount(resultInfo,transConn));
					resultInfo.setBalance(noticeResultInfo.getAmount());
					resultInfo.setRate(noticeResultInfo.getRate());
					resultInfo.setInterest(noticeResultInfo.getInterest());
					resultInfo.setPayAccountID(noticeResultInfo.getPayAccountID());
					
				}
				else
				{
					resultInfo.setBalance(resultInfo.getTransferAmount() - getRepayedNoticeContractdetailAmount(resultInfo,transConn));
					//minzhao重新定义转让余额,该方法待修改
					resultInfo.setSellamount(resultInfo.getBalance());
					LoanPayNoticeDao dao = new LoanPayNoticeDao();
					resultInfo.setRate(dao.getLatelyRate(resultInfo.getLoanPayNoticeID(),resultInfo.getClearInterestDate()));
				}
				coll.add(resultInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细出错");
		}
		finally
		{
			finalizeDAO();
		}
		
		return coll;
	}
	/**
	 * @deprecated 卖出卖断不再使用该方法
	 * 
	 * 得到卖出卖断对成员单位的子账户的上次结息日
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public Timestamp getTransferClientAccountClearInterest(ContractAndNoticeDetialResultInfo conditionInfo,Connection con)throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tReturn = null;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select case when t2.dtinterestsettlement is null then  t1.startdate  else  t2.dtinterestsettlement end clearinterest \n");
		sql.append("  from cra_transfercontractform t1, (select t.cracontractid, max(t.dtinterestsettlement)  dtinterestsettlement from sett_transferintersetrecord t where   t.NINTERESTTYPE ="+SETTConstant.InterestFeeType.INTEREST+"  and t.statusid > 0 group by t.cracontractid) t2   where t1.id = t2.cracontractid(+)  and  t1.id  = ?  and t1.officeid = ? and t1.currencyid = ?  and t1.STATUSID >0 \n");
		int index = 1;
		try {
			
			if(con==null)
			{
				con = Database.getConnection();
			}
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, conditionInfo.getTransferContractFormId());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			rs = ps.executeQuery();
			if(rs.next())
			{
				tReturn = rs.getTimestamp("clearinterest");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询转让合同明细对应上次结息日出错");
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
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return tReturn;
	}
	
	/**
	 * 查询转让合同明细已经被收款通知单明细占用的金额
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public double getRepayedNoticeContractdetailAmount(ContractAndNoticeDetialResultInfo conditionInfo,Connection con)throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = 0.0;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select sum(t.AMOUNT) sumAmount \n");
		sql.append(" from CRA_NOTICECONTRACTDETAIL t,cra_transfernoticeform t1 where  t1.id = t.NOTICEFORMID and t.CONTRACTDETAILID = ?   and t.officeid = ?  and t.currencyid = ? and t.STATUSID>0 and t1.STATUSID="+CRAconstant.TransactionStatus.APPROVALED+" \n");
		int index = 1;
		try {
			if(con==null)
			{
				con = Database.getConnection();
			}
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, conditionInfo.getContractDetailID());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			rs = ps.executeQuery();
			if(rs.next())
			{
				dReturn = rs.getDouble("sumAmount");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询装让合同已经被收款通知单明细占用的金额出错");
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
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return dReturn;
	}
	
	/**
	 * 得到收款通知单明细
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public TransferNoticeDetailInfo getNoticeDetailInfo(ContractAndNoticeDetialResultInfo conditionInfo,Connection con)throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		TransferNoticeDetailInfo resultInfo = null;
		StringBuffer sql = new StringBuffer(); 
		sql.append("select ID,OFFICEID,CURRENCYID,NOTICEFORMID,CONTRACTID,PAYFORMID,AMOUNT,INTEREST,RATE,PAYACCOUNTID,STATUSID,INPUTUSERID,INPUTDATE,CONTRACTDETAILID \n");
		sql.append(" from CRA_NOTICECONTRACTDETAIL t where t.NOTICEFORMID=? and t.CONTRACTDETAILID = ?   and t.officeid = ?  and t.currencyid = ? and t.STATUSID>0 \n");
		int index = 1;
		try {
			if(con==null)
			{
				con = Database.getConnection();
			}
			ps = con.prepareStatement(sql.toString());
			ps.setLong(index++, conditionInfo.getNoticeFormId());
			ps.setLong(index++, conditionInfo.getContractDetailID());
			ps.setLong(index++,conditionInfo.getOfficeID());
			ps.setLong(index++,conditionInfo.getCurrencyID());
			rs = ps.executeQuery();
			if(rs.next())
			{
				resultInfo = new TransferNoticeDetailInfo();
				resultInfo.setId(rs.getLong("ID"));
				resultInfo.setOfficeID(rs.getLong("OFFICEID"));
				resultInfo.setCurrencyID(rs.getLong("CURRENCYID"));
				resultInfo.setNoticeFormID(rs.getLong("NOTICEFORMID"));
				resultInfo.setContractID(rs.getLong("PAYFORMID"));
				resultInfo.setAmount(rs.getDouble("AMOUNT"));
				resultInfo.setInterest(rs.getDouble("INTEREST"));
				resultInfo.setRate(rs.getDouble("RATE"));
				resultInfo.setPayAccountID(rs.getLong("PAYACCOUNTID"));
				resultInfo.setStatusID(rs.getLong("STATUSID"));
				resultInfo.setInputuserID(rs.getLong("INPUTUSERID"));
				resultInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				resultInfo.setContractDetailID(rs.getLong("CONTRACTDETAILID"));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException("查询装让合同已经被收款通知单明细占用的金额出错");
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
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return resultInfo;
	}
}

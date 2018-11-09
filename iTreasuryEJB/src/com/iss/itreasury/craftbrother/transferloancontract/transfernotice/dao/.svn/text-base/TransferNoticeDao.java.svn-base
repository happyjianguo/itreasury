package com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.ContractAndNoticeDetailConditionInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.NoticeQueryInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransPayNoticeDetailInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transfernotice.dataentity.TransferNoticeInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.transferloancontract.dataentity.TransferLoanContractInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.CreateCodeManager;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.craftbrother.util.CRAconstant;

public class TransferNoticeDao extends ITreasuryDAO
{
	public TransferNoticeDao()
	{
		super("CRA_TRANSFERNOTICEFORM");
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
	
	public void getNoticeSQL(NoticeQueryInfo queryInfo)
	{
        //select
		m_sbSelect = new StringBuffer();
		m_sbSelect.append(" notice.ID, notice.NOTICECODE, notice.NOTICETYPEID, notice.ISURROGATEPAY, notice.TRANSTYPEID, \n");
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
		/**
		if(queryInfo.getCracontractIdFrom()>0)
		{
			m_sbWhere.append(" and notice.CRACONTRACTID >= "+queryInfo.getCracontractIdFrom()+"\n");
		}
		if(queryInfo.getCracontractIdTo()>0)
		{
			m_sbWhere.append(" and notice.CRACONTRACTID <= "+queryInfo.getCracontractIdTo()+"\n");
		}	
		**/	
		if (!queryInfo.getStrCraContractNoFrom().trim().equals(""))
		{ //转让合同号开始
			m_sbWhere.append(" and( ");
			m_sbWhere.append(" cracontract.CONTRACTCODE >= '" + queryInfo.getStrCraContractNoFrom() + "'");
			m_sbWhere.append(" ) ");
		}
		if (!queryInfo.getStrCraContractNoTo().trim().equals(""))
		{ //转让合同号结束
			m_sbWhere.append(" and( ");
			m_sbWhere.append(" cracontract.CONTRACTCODE <= '" + queryInfo.getStrCraContractNoTo() + "'");
			m_sbWhere.append(" ) ");
		}
		if(queryInfo.getStartAmount()>0.00)
		{
			m_sbWhere.append(" and notice.AMOUNT >= "+queryInfo.getStartAmount()+"\n");
		}
		if(queryInfo.getEndAmount()>0.00)
		{
			m_sbWhere.append(" and notice.AMOUNT <= "+queryInfo.getEndAmount()+"\n");
		}
		if(!queryInfo.getStartInputDate().equals(""))
		{
			m_sbWhere.append(" and notice.INPUTDATE >= to_date('"+queryInfo.getStartInputDate()+"','yyyy-mm-dd') \n");
		}
		if(!queryInfo.getEndInputDate().equals(""))
		{
			m_sbWhere.append(" and notice.INPUTDATE <= to_date('"+queryInfo.getEndInputDate()+"','yyyy-mm-dd')+1 \n");
		}
		if(queryInfo.getIsurrogatePay()>0)
		{
			m_sbWhere.append(" and notice.ISURROGATEPAY = "+queryInfo.getIsurrogatePay()+"\n");
		}
		if(queryInfo.getInputUserId()>0)
		{
			m_sbWhere.append(" and notice.INPUTUSERID = "+queryInfo.getInputUserId()+"\n");
		}
		if(queryInfo.getStatusId()>0)
		{
			m_sbWhere.append(" and notice.STATUSID = "+queryInfo.getStatusId()+"\n");
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
	 * 方法说明：根据通知单ID，得到通知单详细信息
	 * @param INFO
	 * @return Sett_TransCurrentDepositInfo
	 * @throws IException
	 */
	public TransferNoticeInfo findByID(TransferNoticeInfo noticeInfo) throws Exception
	{
		TransferNoticeInfo info = null;
		ResultSet rs = null;
		try
		{
			initDAO();
			String sql = "select * from " + this.strTableName + " where id="+noticeInfo.getId();
			prepareStatement(sql);;
			rs = executeQuery();
			info = this.transferResultsetIntoInfo(rs);
		}
		finally {
			
			finalizeDAO();
		}

		return info;
	}
	private TransferNoticeInfo transferResultsetIntoInfo(ResultSet rs) throws Exception
	{
		TransferNoticeInfo info = null;
		while (rs.next())
		{
			info = new TransferNoticeInfo();
			info.setId(rs.getLong("ID"));
			info.setOfficeId(rs.getLong("OfficeID"));
			info.setCurrencyId(rs.getLong("CurrencyID"));
			info.setNoticeCode(rs.getString("Noticecode"));
			info.setNoticeTypeId(rs.getLong("noticetypeid"));
			info.setAmount(rs.getDouble("amount"));
			info.setBankId(rs.getLong("bankid"));
			info.setContractId(rs.getLong("contractid"));
		//	info.setCracontractCode(rs.getString("contractcode"));
	        info.setDtclearInterest(rs.getTimestamp("dtclearinterest"));
	        info.setInputDate(rs.getTimestamp("inputdate"));
	        info.setInputuserId(rs.getLong("inputuserid"));
	        info.setInterest(rs.getDouble("interest"));
	        info.setIsdeductCharge(rs.getLong("COMMISSIONPAYMENTTYPE"));
	        info.setIsdirectSett(rs.getLong("isdirectsett"));
	        info.setIsurrogatePay(rs.getLong("isurrogatepay"));
	        info.setLastClearInterest(rs.getTimestamp("lastclearinterest"));
	        info.setMcommission(rs.getDouble("mcommission"));
	        info.setRate(rs.getDouble("rate"));
	        info.setSremark(rs.getString("sremark"));
	        info.setStatusId(rs.getLong("statusid"));
	        info.setCracontractId(rs.getLong("CRACONTRACTID"));
	        info.setTranstypeid(rs.getLong("TRANSTYPEID"));
	        info.setCommissionPaymentType(rs.getLong("commissionPaymentType"));
		}

		return info;
	}
	
	/**
	 * 方法说明：根据转让合同ID、转让类型，得到第一笔收款业务的起息日
	 * @param contractId,transferTypeId
	 * @return Timestamp
	 * @throws Exception
	 */
	public Timestamp getLastClearInterest(long contractId,long transferTypeId) throws Exception
	{
		Timestamp lastClearInterest = null;
		StringBuffer sql = null;
		try{
			initDAO();
			sql = new StringBuffer();
			sql.append(" select INTERESTSTART from sett_transferloanamount t \n ");
			sql.append(" where t.STATUSID = " + SETTConstant.TransactionStatus.CHECK);
			sql.append(" and and t.transfercontractid = " + contractId );
			sql.append(" and t.transactiontypeid =  " + SETTConstant.TransactionType.TRANSFERREPAY);
			sql.append(" order by t.intereststart ");			
			transPS = transConn.prepareStatement(sql.toString());
	        transRS = transPS.executeQuery();
	        if(transRS.next())
	        {
	        	lastClearInterest = transRS.getTimestamp("INTERESTSTART");
	        }
	        System.out.println("查询上次结息日的SQL："+sql.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		finally
		{
            finalizeDAO();
        }
		return lastClearInterest;
	}
	
	/**
	 * 方法说明：根据转让通知单ID，查询该通知单是否在结算中被使用（删除、取消审批时校验使用）
	 * @param noticeId
	 * @return long
	 * @throws Exception
	 */
	public long validate(long noticeId) throws Exception
	{
		long lReturn = -1;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append("select id from SETT_TRANSFERLOANAMOUNT ");
			sql.append("where NOTICEID = "+noticeId+" ");
			sql.append("and STATUSID <> "+CRAconstant.TransactionStatus.DELETED);
			transPS = transConn.prepareStatement(sql.toString());
	        transRS = transPS.executeQuery();
	        if(transRS.next())
	        {
	        	lReturn = transRS.getLong("id");
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
            finalizeDAO();
        }
		return lReturn;
	}
	
	/**
	 * 更改状态
	 * @param id,statusId
	 * @return
	 * @throws IException
	 */
	public long updateStatus(long id, long statusId) throws Exception
	{
		long lReturn = -1;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append("update "+strTableName+" ");
		    sql.append("set STATUSID = "+statusId+" ");
		    sql.append("where ID = "+id);
		    transPS = transConn.prepareStatement(sql.toString());
		    transPS.executeUpdate(sql.toString());
		    lReturn = id;
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		finally
		{
            finalizeDAO();
        }		
		return lReturn;
	}
	
	/**
	 * 查找账户编号
	 * @param bankId
	 * @return bankNo
	 * @throws IException
	 */
	public String findBankNoByID(long bankId) throws Exception
	{
		String lReturn = "";
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append("select counterpartbankno from cra_counterpartbank where id = "+bankId+" and STATUSID <> "+CRAconstant.TransactionStatus.DELETED);
		    transPS = transConn.prepareStatement(sql.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
	        {
	        	lReturn = transRS.getString("counterpartbankno");
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		finally
		{
            finalizeDAO();
        }		
		return lReturn;
	}
	
	/**
	 *  查询收款通知单,转让合同组装数据（卖出回购）
	 * @param conditionInfo
	 * @return
	 * @throws IException
	 */
	public Collection findReceiveNoticeDetial(ContractAndNoticeDetailConditionInfo conditionInfo) throws IException
	{
		Collection coll = null;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append("select a.id repayNoticeID, a.craContractID, a.amount, b.drate, c.interestStart, \n ");
			sql.append("b.id, b.contractCode craContractCode, b.transTypeID, b.counterPartID \n ");
			sql.append("from cra_transfernoticeform a, cra_transfercontractform b, sett_transferloanamount c \n ");
			sql.append("where a.craContractID = b.id and c.noticeID = a.id \n ");
			sql.append("and a.craContractID = "+conditionInfo.getTransferContractFormID()+" \n ");
			sql.append("and b.transTypeID = "+conditionInfo.getTransTypeID()+" \n ");
			sql.append("and a.statusid = "+CRAconstant.TransactionStatus.USED+" \n ");
			sql.append("and a.noticeTypeID = "+CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT+" \n ");
			sql.append("and c.statusid = "+SETTConstant.TransactionStatus.CHECK+" \n ");
			sql.append("order by c.interestStart ");
			transPS = transConn.prepareStatement(sql.toString());
		    transRS = transPS.executeQuery();
		    coll = new ArrayList();
			while(transRS.next())
			{
				TransPayNoticeDetailInfo detailInfo = new TransPayNoticeDetailInfo();
				detailInfo.setAmount(transRS.getDouble("amount"));
				detailInfo.setRate(transRS.getDouble("drate"));
				detailInfo.setLastClearInterest(transRS.getTimestamp("interestStart"));
				detailInfo.setTransferRepayNoticeID(transRS.getLong("repayNoticeID"));
                detailInfo.setTransferContractID(transRS.getLong("craContractID"));
				detailInfo.setCounterPartID(transRS.getLong("counterPartID"));

				coll.add(detailInfo);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询收款通知单明细出错");
		}
		finally
		{
            finalizeDAO();
        }
		return coll;
	}
	
	/**
	 * 查询付款通知单明细已付金额合计
	 * @param repayNoticeId
	 * @return 
	 * @throws IException
	 */
	public double findPayBalanceByConId(long repayNoticeId) throws IException
	{
		double lReturn = 0.00;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append(" select nvl(sum(d.amount),0) payamount \n");
			sql.append(" from cra_transpaynoticedetail d, cra_transfernoticeform e \n");
			sql.append(" where d.TRANSFERPAYNOTICEID = e.id \n");
			sql.append(" and d.TRANSFERREPAYNOTICEID = "+repayNoticeId);
			sql.append(" and d.statusid = "+Constant.RecordStatus.VALID);
			sql.append(" and e.statusid in( "+CRAconstant.TransactionStatus.APPROVALED+","+CRAconstant.TransactionStatus.USED+")");	
			transPS = transConn.prepareStatement(sql.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	lReturn = transRS.getDouble("payamount");
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询已付款通知单明细出错");
		}
		finally
		{
            finalizeDAO();
        }
		return lReturn;
	}
	
	/**
	 * 根据转让合同ID查找该笔合同的已收款金额与已付款金额的差额
	 * @param contractId
	 * @return sumamount
	 * @throws IException
	 */
	public double findBalanceByConId(long contractId) throws IException
	{
		double lReturn = 0.00;
		double sumReceiveAmount = 0.00;
		double sumPayAmount = 0.00;
		StringBuffer sql1 = null;
		StringBuffer sql2 = null;
		try
		{
			initDAO();
			sql1 = new StringBuffer();			
			sql1.append("select nvl(sum(A.amount),0) sumReceiveAmount from sett_transferloanamount A \n ");
			sql1.append("where A.Transfercontractid = "+ contractId +" \n ");
			sql1.append("and A.TRANSACTIONTYPEID in ("+SETTConstant.TransactionType.AGENTTRANSFERREPAY+") \n ");
			sql1.append("and A.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
			transPS = transConn.prepareStatement(sql1.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	sumReceiveAmount = transRS.getDouble("sumReceiveAmount");
		    }
		    sql2 = new StringBuffer();
			sql2.append("select nvl(sum(B.amount),0) sumPayAmount from sett_transferloanamount B \n ");
			sql2.append("where B.Transfercontractid = "+ contractId +" \n ");
			sql2.append("and B.TRANSACTIONTYPEID = "+ SETTConstant.TransactionType.TRANSFERPAY +" \n ");
			sql2.append("and B.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
			transPS = transConn.prepareStatement(sql2.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	sumPayAmount = transRS.getDouble("sumPayAmount");
		    }
		    lReturn = sumReceiveAmount - sumPayAmount;
		    System.out.println("查询转让合同已收金额的SQL："+sql1.toString());
		    System.out.println("查询转让合同已付金额的SQL："+sql2.toString());
		    System.out.println("查询转让合同的应付余额值："+lReturn);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new IException("查询可付金额出错");
		}
		finally
		{
            finalizeDAO();
        }
		return lReturn;
	}
	
	/**
	 * 根据转让合同ID查找该笔合同的已收利息与已付利息的差额
	 * @param contractId
	 * @return sumInterest
	 * @throws IException
	 */
	public double findInterestByConId(long contractId) throws IException
	{
		double lReturn = 0.00;
		double transReceiveInterest = 0.00;
		double balanceInterest = 0.00;
		double sumPayInterest = 0.00;
		StringBuffer sql1 = null;
		StringBuffer sql2 = null;
		StringBuffer sql3 = null;
		try
		{
			initDAO();
			sql1 = new StringBuffer();		
			sql1.append("select nvl(sum(C.interest),0) sumTransInterest from sett_transferloanamount C \n ");
			sql1.append("where C.Transfercontractid = "+ contractId +" \n ");
			sql1.append("and C.TransActionTypeId in ("+ SETTConstant.TransactionType.TRANSFERREPAY +","+SETTConstant.TransactionType.AGENTTRANSFERREPAY+") \n ");
			sql1.append("and C.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
			transPS = transConn.prepareStatement(sql1.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	transReceiveInterest = transRS.getDouble("sumTransInterest");
		    }
		    sql3 = new StringBuffer();		
		    sql3.append("select nvl(sum(m.minterest),0) sumBalanceInterest from sett_transferintersetrecord m \n ");
		    sql3.append("where m.cracontractid = "+ contractId +" \n ");
		    sql3.append("and m.ninteresttype =  "+ SETTConstant.InterestOperateType.INTERESTSETTLEMENT);
		    sql3.append("and m.statusid = 1 ");
			transPS = transConn.prepareStatement(sql3.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	balanceInterest = transRS.getDouble("sumBalanceInterest");
		    }
		    sql2 = new StringBuffer();
		    sql2.append("select nvl(sum(D.interest),0) sumPayInterest from sett_transferloanamount D \n ");
		    sql2.append("where D.Transfercontractid = "+ contractId +" \n ");
		    sql2.append("and D.TransActionTypeId = "+ SETTConstant.TransactionType.TRANSFERPAY +" \n ");
		    sql2.append("and D.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
		    transPS = transConn.prepareStatement(sql2.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	sumPayInterest = transRS.getDouble("sumPayInterest");
		    }
		    lReturn = transReceiveInterest + balanceInterest - sumPayInterest;
		    System.out.println("查询转让合同已收利息值的SQL："+sql1.toString());
		    System.out.println("查询转让合同已付利息值的SQL："+sql2.toString());
		    System.out.println("查询转让合同的应付利息值："+lReturn);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new IException("查询可付利息出错");
		}
		finally
		{
            finalizeDAO();
        }
		return lReturn;
	}
	
	/**
	 * 根据转让合同ID查找收付款交易
	 * @param contractId
	 * @return collection
	 * @throws Exception
	 */
	public Collection findTransInfo(long contractId,Timestamp dtStartDate,Timestamp dtEndDate) throws Exception
	{
		Vector coll = new Vector();
		StringBuffer sql = new StringBuffer();
		try
		{
			initDAO();
			sql.append(" select a.* from sett_transferloanamount a \n");
			sql.append(" where a.statusid = "+SETTConstant.TransactionStatus.CHECK);
			sql.append(" and a.transfercontractid = "+contractId);
			sql.append(" and a.transactiontypeid in ("+SETTConstant.TransactionType.TRANSFERPAY+","+SETTConstant.TransactionType.TRANSFERREPAY+")");
			sql.append(" and a.intereststart >= to_date('"+ DataFormat.formatDate(dtStartDate) + "','yyyy-mm-dd')");
			sql.append(" and a.intereststart <= to_date('"+ DataFormat.formatDate(dtEndDate) + "','yyyy-mm-dd')");
			sql.append(" and a.amount > 0 ");
			transPS = transConn.prepareStatement(sql.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	TransferLoanContractInfo info = new TransferLoanContractInfo();
	        	info.setID(transRS.getLong("id"));
	        	info.setOFFICEID(transRS.getLong("officeid"));
	        	info.setCURRENCYID(transRS.getLong("currencyid"));
	        	info.setSTRANSNO(transRS.getString("stransno") == null ? "" : transRS.getString("stransno"));
	        	info.setTRANSACTIONTYPEID(transRS.getLong("transactiontypeid"));
	        	info.setNOTICEID(transRS.getLong("noticeid"));
	        	info.setPAYBANKID(transRS.getLong("paybankid"));
	        	info.setPAYGENRALLEDGERTYPEID(transRS.getLong("PAYGENRALLEDGERTYPEID"));
	        	info.setAMOUNT(transRS.getDouble("amount"));
	        	info.setINTEREST(transRS.getDouble("interest"));
	        	info.setCOMMISSION(transRS.getDouble("commission"));
	        	info.setRECEIVEBANKID(transRS.getLong("RECEIVEBANKID"));
	        	info.setRECGENERALLEDGERTYPEID(transRS.getLong("RECGENERALLEDGERTYPEID"));
	        	info.setINTERESTSTART(transRS.getTimestamp("intereststart"));
	        	info.setEXECUTE(transRS.getTimestamp("execute"));
	        	info.setINPUTUSERID(transRS.getLong("inputuserid"));
	        	info.setSABSTRACT(transRS.getString("sabstract"));
	        	info.setSCHECKABSTRACT(transRS.getString("scheckabstract"));
	        	info.setABSTRACTID(transRS.getLong("abstractid"));
	        	info.setSTATUSID(transRS.getLong("statusid"));
	        	info.setCHECKUSERID(transRS.getLong("checkuserid"));
	        	info.setNMODIFY(transRS.getTimestamp("nmodify"));
	        	info.setINPUTDATE(transRS.getTimestamp("inputdate"));
	        	coll.add(info);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		finally
		{
            finalizeDAO();
        }
		return coll;
	}
	
	/**
	 * 说明：根据合同编号查询已审批和已使用的通知单金额
	 * 作者:minzhao
	 * 日期:Aug 5, 2009
	 * 参数:@param contractid
	 * 参数:@return
	 * 参数:@throws Exception
	 * 返回类型:double
	 */
	public double searchSellAmount(long contractid) throws Exception
	{
		double returnamount=0.0;
		StringBuffer strSQL=null;
		ResultSet rs=null;
		try
		{
			initDAO();
			strSQL = new StringBuffer();
        	
        	strSQL.append("select c.cracontractid, sum(c.amount) sellamount " );
        	strSQL.append(" from CRA_TRANSFERNOTICEFORM c " );
        	strSQL.append(" where c.statusid in( " + CRAconstant.TransactionStatus.APPROVALED +","+CRAconstant.TransactionStatus.USED+")");
        	strSQL.append(" and c.noticetypeid = " + CRAconstant.CRANoticeActionType.TRANSFERREPAYAMOUNT);
        	strSQL.append(" and c.isurrogatepay= " + CRAconstant.ISURROGATEPAY.ISNOT);
			if(contractid!=-1)
			{
				strSQL.append(" and c.cracontractid = "+contractid);
			}
			else
			{
				throw new IException ("传入contractid为空，查询数据失败！");
			}
			strSQL.append("  group by c.cracontractid ");
			log.info(strSQL.toString());
			prepareStatement(strSQL.toString());
			rs=executeQuery();
			while(rs.next())
			{
				returnamount=rs.getDouble("sellamount");
			}
		}
		catch(ITreasuryDAOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			finalizeDAO();
		}
		return returnamount;
	}
	
	/**
	 * 查询付款通知单明细上次结息日期
	 * @param repayNoticeId
	 * @return 
	 * @throws IException
	 */
	public Timestamp queryLastclearDate(ContractAndNoticeDetailConditionInfo conditionInfo) throws IException
	{
		Timestamp lReturn = null;
		StringBuffer sql = null;
		try
		{
			initDAO();
			sql = new StringBuffer();
			sql.append(" select max(z.intereststart) lastclearinterest \n");
			sql.append(" from sett_transferloanamount z \n");
			sql.append(" where z.transfercontractid = "+conditionInfo.getTransferContractFormID()+" \n ");
			sql.append(" and z.transfertype = "+conditionInfo.getTransTypeID()+" \n ");
			sql.append(" and z.statusid = "+SETTConstant.TransactionStatus.CHECK+" \n ");
			sql.append(" and z.transactiontypeid = "+SETTConstant.TransactionType.TRANSFERPAY+" \n ");
			sql.append(" and z.interest > 0  " );
			sql.append(" and z.officeid = " +conditionInfo.getOfficeID() +" \n ");
			sql.append(" and z.currencyid = " +conditionInfo.getCurrencyID() +" \n ");
			sql.append(" and z.intereststart >= to_date('"+ DataFormat.formatDate(conditionInfo.getDtstartDate()) + "','yyyy-mm-dd')");
			transPS = transConn.prepareStatement(sql.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	lReturn = transRS.getTimestamp("lastclearinterest");
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询已付款通知单明细出错");
		}
		finally
		{
            finalizeDAO();
        }
		return lReturn;
	}
	
	
	public double findCommsionByConId(long contractId) throws IException
	{
		double sumCommsion=0.00;
		double sumReceiveCommsion = 0.00;
		double sumPayCommsiom =0.00;
		StringBuffer sql1 = null;
		StringBuffer sql2 = null;
		try
		{
			initDAO();
			sql1 = new StringBuffer();			
			sql1.append("select nvl(sum(A.commission),0) sumReceiveAmount from sett_transferloanamount A \n ");
			sql1.append("where A.Transfercontractid = "+ contractId +" \n ");
			sql1.append("and A.TRANSACTIONTYPEID in ("+SETTConstant.TransactionType.AGENTTRANSFERREPAY+") \n ");
			sql1.append("and A.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
			transPS = transConn.prepareStatement(sql1.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	sumReceiveCommsion = transRS.getDouble("sumReceiveAmount");
		    }
		    sql2 = new StringBuffer();
			sql2.append("select nvl(sum(B.commission),0) sumPayAmount from sett_transferloanamount B \n ");
			sql2.append("where B.Transfercontractid = "+ contractId +" \n ");
			sql2.append("and B.TRANSACTIONTYPEID = "+ SETTConstant.TransactionType.TRANSFERPAY +" \n ");
			sql2.append("and B.Statusid = "+ CRAconstant.TransactionStatus.APPROVALED);
			transPS = transConn.prepareStatement(sql2.toString());
		    transRS = transPS.executeQuery();
		    if(transRS.next())
		    {
		    	sumPayCommsiom = transRS.getDouble("sumPayAmount");
		    }
		    sumCommsion = sumReceiveCommsion - sumPayCommsiom;
		    System.out.println("查询转让合同已收手续费的SQL："+sql1.toString());
		    System.out.println("查询转让合同已付手续的SQL："+sql2.toString());
		    System.out.println("查询转让合同的应付手续费值："+sumPayCommsiom);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
			throw new IException("查询可付金额出错");
		}
		finally
		{
            finalizeDAO();
        }
		
		return sumCommsion;
	}
}

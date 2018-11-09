/*
* Created on 2003-9-8
*
* To change the template for this generated file go to
* Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.ebank.obfinanceinstr.dao;
/**
 * @author hyzeng 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.OfficeInfo;
import com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRecordBiz;
import com.iss.itreasury.ebank.approval.bizlogic.InutApprovalRelationBiz;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRecordInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutApprovalRelationInfo;
import com.iss.itreasury.ebank.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.BranchbankInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.ClientAccountInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OBCapSummarizeInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.OpenDateInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.PayerOrPayeeInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.SubLoanAccountDetailInfo;
import com.iss.itreasury.ebank.obfinanceinstr.exp.RepeatedApplyCodeException;
import com.iss.itreasury.ebank.oboutersourceregister.judgement.IsOuterSourceJudgement;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBOperation;
import com.iss.itreasury.loan.loanpaynotice.dao.LoanPayNoticeDao;
import com.iss.itreasury.settlement.account.bizlogic.AccountBean;
import com.iss.itreasury.settlement.account.dao.Sett_SubAccountDAO;
import com.iss.itreasury.settlement.account.dataentity.SubAccountAssemblerInfo;
import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.dataentity.InterestTaxInfo;
import com.iss.itreasury.settlement.interest.dataentity.LoanAccountInterestInfo;
import com.iss.itreasury.settlement.interest.dataentity.SubAccountPredrawInterestInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_FilialeAccountSetDAO;
import com.iss.itreasury.settlement.setting.dataentity.FilialeAccountInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;
import com.iss.itreasury.util.ITreasuryException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.OBFSWorkflowManager;
import com.iss.system.dao.PageLoader;

public class OBFinanceInstrDao extends ITreasuryDAO
{
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;
	private String ENTITY_NAME = "OB_INSTRNO";
	private static Log4j log4j = null;
  	private static  Object lockObj = new Object();  //静态
	public OBFinanceInstrDao()
	{
		log4j = new Log4j(Constant.ModuleType.EBANK, this);
	}
	
	/**
	 * 定期支取时检查是否已提交过该存单
	 */
	 public long checkCapitalTrans(FinanceInfo info)throws Exception{
		 long lReturn = -1;
		 StringBuffer strSQL = new StringBuffer();
		 strSQL.append(" select count(*) from ob_financeinstr where nstatus <> "+OBConstant.SettInstrStatus.DELETE+" and nstatus <>"+OBConstant.SettInstrStatus.REFUSE);
		 if(info != null && info.getSubAccountID() > -1){
			 strSQL.append(" and nsubaccountid = "+info.getSubAccountID());
		 }
		 if(info != null && info.getID() > -1){
			 strSQL.append(" and id <> "+info.getID());
		 }
		 log4j.print("checkCapitalTrans sql :"+strSQL.toString());
		 try{
			 initDAO();
			 transPS = prepareStatement(strSQL.toString());
			 transRS = transPS.executeQuery();
			 while(transRS != null && transRS.next()){
				 lReturn = transRS.getLong(1);
			 }
		 }catch (Exception e) {
				throw e;
			}finally{
				finalizeDAO();
			}
		 return lReturn;
	 }
	
	/**
	 * added by mzh_fu 更新签名值与原始数据
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateSignatureInfo(FinanceInfo info)
			throws ITreasuryDAOException {
		long lReturn = -1;

		//modified by mzh_fu 2008/04/11 解决ORA异常(有一定风险存在), 期待Oracle解决JDBC驱动包BUG 
		 String strSQL="update ob_financeinstr t set t.signatureValue = ?, t.signatureOriginalValue = ? where t.id = ?";
		
		//String strSQL = "update ob_financeinstr set signatureValue = '"
		//		+ info.getSignatureValue()
		//		+ "',signatureOriginalValue = ? where id = ?";

		try {
			initDAO();
			prepareStatement(strSQL);
			transPS.setString(1, info.getSignatureValue());
			transPS.setString(2, info.getSignatureOriginalValue());
			transPS.setLong(3, info.getID());

			lReturn = executeUpdate();
		} catch (Exception e) {
			throw new ITreasuryDAOException("更新签名信息失败:" + e.getMessage(), e);
		} finally {
			finalizeDAO();
		}

		return lReturn;
	}
	
	/**
	 * 一汽财务网银批量复核查询所有批次的方法
	 * 作者：菅中尉
	 * 时间：2007-04-20
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List findBatchInfoByDate(FinanceInfo finanInfo) throws Exception
	{
		
		List lstReturn  = new ArrayList();
		try
		{
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select f.sbatchno sbatchno,\n");
			strSQL.append("       count(1) ncount,\n");
			strSQL.append("       f.dtconfirm dtconfirm,\n");
			strSQL.append("       u.sname sname,\n");
			strSQL.append("       f.ntranstype ntranstype,\n");
			strSQL.append("       sum(f.mamount) mamount\n");
			strSQL.append("  from ob_financeinstr f\n");
			strSQL.append("  join ob_user u on f.nconfirmuserid = u.id\n");
			strSQL.append(" where f.dtconfirm > to_date(?, 'yyyy-mm-dd')\n");
			strSQL.append("   and f.dtconfirm < to_date(?, 'yyyy-mm-dd')\n");
			strSQL.append("   and f.sbatchno is not null\n");
			strSQL.append("   and f.nstatus = ?\n");
			strSQL.append(" group by f.sbatchno, f.dtconfirm, f.nconfirmuserid, f.ntranstype, u.sname");
			this.prepareStatement(strSQL.toString());
			this.transPS.setString(1, DataFormat.getDateString(finanInfo.getDtDepositBillCheckdate()));
			this.transPS.setString(2, DataFormat.getDateString(finanInfo.getDtDepositBillInputdate()));
			this.transPS.setLong(3, Long.parseLong(finanInfo.getSStatus()));
			this.executeQuery();
			while(transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setSBatchNo(transRS.getString("sbatchno"));
				info.setNCount(transRS.getInt("ncount"));
				info.setConfirmDate(transRS.getTimestamp("dtconfirm"));
				info.setConfirmUserName(transRS.getString("sname"));
				info.setTransType(transRS.getLong("ntranstype"));
				info.setAmount(transRS.getDouble("mamount"));
				lstReturn.add(info);
			}
		}catch(Exception e){
			log.error(e.toString());
			throw new ITreasuryException();
		}finally{
			finalizeDAO();
		}
		return lstReturn;
	}
	/**
	 * 一汽财务网银批量复核查询所有批次的方法for 华联新加！
	 * 作者：菅中尉
	 * 时间：2007-04-20
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List findBatchInfoByDatehl(FinanceInfo finanInfo) throws Exception
	{
		
		List lstReturn  = new ArrayList();
		try
		{
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select f.sbatchno sbatchno,\n");
			strSQL.append("       count(1) ncount,\n");
			strSQL.append("        to_char(f.dtconfirm ,'yyyy-mm-dd' ) dtconfirm,\n");//相同的批次号输出一次，华联修改By wjyang
			strSQL.append("       u.sname sname,\n");
			//strSQL.append("      --f.ntranstype ntranstype,\n");
			strSQL.append("       sum(f.mamount) mamount\n");
			strSQL.append("  from ob_financeinstr f\n");
			strSQL.append("  join ob_user u on f.nconfirmuserid = u.id\n");
			strSQL.append(" where f.dtconfirm > to_date(?, 'yyyy-mm-dd')\n");
			strSQL.append("   and f.dtconfirm < to_date(?, 'yyyy-mm-dd')+1\n");
			strSQL.append(" AND f.nConfirmUserID != " + finanInfo.getNUserID() + " \n");
			strSQL.append("   and f.sbatchno is not null\n");
			strSQL.append("   and f.nstatus = ?\n");
			if( finanInfo.getClientID() > 0 )
			{
				strSQL.append("   and f.nclientid = " + finanInfo.getClientID());
			}
			//strSQL.append(" group by f.sbatchno,  to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid, f.ntranstype, u.sname");
			strSQL.append(" group by f.sbatchno,  to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid,  u.sname ");
			strSQL.append("  order by f.sbatchno desc ");
			this.prepareStatement(strSQL.toString());
			this.transPS.setString(1, DataFormat.getDateString(finanInfo.getDtDepositBillCheckdate()));
			this.transPS.setString(2, DataFormat.getDateString(finanInfo.getDtDepositBillInputdate()));
			this.transPS.setLong(3, Long.parseLong(finanInfo.getSStatus()));
			this.executeQuery();
			log4j.print("-----------------------------=========="+strSQL.toString());
			while(transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setSBatchNo(transRS.getString("sbatchno"));
				info.setNCount(transRS.getInt("ncount"));
				info.setConfirmDate(DataFormat.getDateTime(transRS.getString("dtconfirm")));//输出格式将String 转换为Timestamp
				info.setConfirmUserName(transRS.getString("sname"));
				//info.setTransType(transRS.getLong("ntranstype"));
				info.setAmount(transRS.getDouble("mamount"));
				lstReturn.add(info);
			}
		}catch(Exception e){
			log.error(e.toString());
			throw new ITreasuryException();
		}finally{
			finalizeDAO();
		}
		return lstReturn;
	}
	
	
	public List findBatchInfoByDateforsign(FinanceInfo finanInfo) throws Exception
	{
		log4j.print("============================+findBatchInfoByDateforsign");
		List lstReturn  = new ArrayList();
		try
		{
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select f.sbatchno sbatchno,\n");
			strSQL.append("       count(1) ncount,\n");
			strSQL.append("        to_char(f.dtconfirm ,'yyyy-mm-dd' ) dtconfirm,\n");//相同的批次号输出一次，华联修改By wjyang
			strSQL.append("       u.sname sname,\n");
			//strSQL.append("      --f.ntranstype ntranstype,\n");
			strSQL.append("       sum(f.mamount) mamount\n");
			strSQL.append("  from ob_financeinstr f\n");
			strSQL.append("  join ob_user u on f.nconfirmuserid = u.id\n");
			strSQL.append(" where f.dtconfirm > to_date(?, 'yyyy-mm-dd')\n");
			strSQL.append("   and f.dtconfirm < to_date(?, 'yyyy-mm-dd')+1\n");			
			strSQL.append(" AND f.nclientid="+finanInfo.getClientID()+ "\n");
			//			
			if(finanInfo.getStatus()==OBConstant.SettInstrStatus.CHECK){
			strSQL.append(" AND (NVL(f.mAmount,0)+NVL(f.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealCompoundInterest,0)+NVL(f.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealSuretyFee,0)+NVL(f.mRealCommission,0)) >= " + this.getMinSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current")+ " \n");
			strSQL.append(" AND (NVL(f.mAmount,0)+NVL(f.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealCompoundInterest,0)+NVL(f.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(f.mRealSuretyFee,0)+NVL(f.mRealCommission,0)) < " + this.getMaxSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current") + " \n");
			strSQL.append(" AND f.nIsCanAccept != 1 \n");
			}
			else if(finanInfo.getStatus()==OBConstant.SettInstrStatus.SIGN){
				strSQL.append(" AND f.nIsCanAccept = 1 \n");
				strSQL.append(" AND f.nSignUserID = " + finanInfo.getUserID() + " \n");
				
			}			
			//
			strSQL.append("   and f.nClientID = " + finanInfo.getClientID() + " \n");
			strSQL.append("   and f.sbatchno is not null\n");
			strSQL.append("   and f.nstatus = ?\n");
			//strSQL.append(" group by f.sbatchno,  to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid, f.ntranstype, u.sname");
			strSQL.append(" group by f.sbatchno,  to_char(f.dtconfirm ,'yyyy-mm-dd' ), f.nconfirmuserid,  u.sname ");
			strSQL.append("  order by f.sbatchno desc ");
			this.prepareStatement(strSQL.toString());
			this.transPS.setString(1, DataFormat.getDateString(finanInfo.getDtDepositBillCheckdate()));
			this.transPS.setString(2, DataFormat.getDateString(finanInfo.getDtDepositBillInputdate()));
			this.transPS.setLong(3, Long.parseLong(finanInfo.getSStatus()));
			this.executeQuery();
			log4j.print("-----------------------------=========="+strSQL.toString());
			while(transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setSBatchNo(transRS.getString("sbatchno"));
				info.setNCount(transRS.getInt("ncount"));
				info.setConfirmDate(DataFormat.getDateTime(transRS.getString("dtconfirm")));//输出格式将String 转换为Timestamp
				info.setConfirmUserName(transRS.getString("sname"));
				//info.setTransType(transRS.getLong("ntranstype"));
				info.setAmount(transRS.getDouble("mamount"));
				lstReturn.add(info);
			}
		}catch(Exception e){
			log.error(e.toString());
			throw new ITreasuryException();
		}finally{
			finalizeDAO();
		}
		return lstReturn;
	}	
	public List findFinanceInfoByBatforsign(FinanceInfo finanInfo) throws Exception
	{
		List lstReturn  = new ArrayList();
		try
		{
			initDAO();			
			log4j.print("aaaaaaaaaaaaaa:"+finanInfo.getSBatchNo());						
			StringBuffer strSQL = new StringBuffer();
			strSQL.append(" select ob.*,\n");
			strSQL.append(" sa.accountno payeeacctno,\n");
			strSQL.append(" sa.name payeename,\n");			
			strSQL.append(" sa.prov spayeeprov,\n");// 华联银行汇款显示收款方：汇入省
			strSQL.append(" sa.city spayeecity,\n");//华联银行汇款显示收款方：汇入市
			strSQL.append(" sa.bankname spayeebankname ,\n");//  华联银行汇款显示收款方：汇入行   
			//财企接口新增
			strSQL.append(" sa.bankexchangeno spayeebankexchangeno,\n");   
			strSQL.append(" sa.bankcnapsno spayeebankcnapsno,\n");  
			strSQL.append(" sa.bankorgno spayeebankorgno,\n"); 
			
			
			strSQL.append(" sett.saccountno payeracctno,\n");
			strSQL.append(" sett.sname payername\n");			
			strSQL.append(" from ob_financeinstr ob,\n");
			strSQL.append(" sett_account sett, ");
			strSQL.append(" (  select t1.id, ");
			strSQL.append("  'bank' payeeType, ");
			strSQL.append(" t1.SPAYEEACCTNO ACCOUNTNO, ");
			strSQL.append(" t1.SPAYEENAME NAME, ");
			strSQL.append(" t1.SPAYEEPROV PROV, ");
			strSQL.append(" t1.SPAYEECITY CITY, ");
			strSQL.append(" t1.SPAYEEBANKNAME BANKNAME, ");
			
			strSQL.append(" t1.spayeebankexchangeno bankexchangeno,  ");
			strSQL.append(" t1.spayeebankcnapsno bankcnapsno, ");
			strSQL.append("  t1.spayeebankorgno bankorgno  ");
			
			
			
			strSQL.append(" from OB_PAYEEINFO t1 ");
			strSQL.append(" union ");
			strSQL.append(" select t2.id, ");
			strSQL.append(" 'system' payeeType, ");
			strSQL.append(" t2.SACCOUNTNO ACCOUNTNO, ");
			strSQL.append(" t2.SNAME NAME, ");
			strSQL.append(" '' PROV, ");
			strSQL.append(" '' CITY, ");
			strSQL.append(" '' BANKNAME, ");

			strSQL.append(" '' bankexchangeno,  ");
			strSQL.append(" '' bankcnapsno, ");
			strSQL.append("  '' bankorgno ");
			
			
			strSQL.append(" from SETT_ACCOUNT t2  )  sa ");
			strSQL.append(" where ob.npayeeacctid = sa.id(+) ");
			strSQL.append(" and ob.npayeracctid = sett.id(+) ");
			strSQL.append("  and decode(ob.NREMITTYPE, 102, 'bank', 'system') = sa.payeetype ");
			
		if(finanInfo.getStatus()==OBConstant.SettInstrStatus.CHECK){
			strSQL.append(" AND (NVL(ob.mAmount,0)+NVL(ob.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealCompoundInterest,0)+NVL(ob.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealSuretyFee,0)+NVL(ob.mRealCommission,0)) >= " + this.getMinSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current")+ " \n");
			strSQL.append(" AND (NVL(ob.mAmount,0)+NVL(ob.mRealInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealCompoundInterest,0)+NVL(ob.mRealOverdueInterest,0)+ \n");
			strSQL.append(" 	NVL(ob.mRealSuretyFee,0)+NVL(ob.mRealCommission,0)) < " + this.getMaxSignAmountDist(finanInfo.getUserID(), finanInfo.getClientID(), finanInfo.getCurrencyID(), "current") + " \n");
			strSQL.append(" AND ob.nIsCanAccept != 1 \n");
				}
		else if(finanInfo.getStatus()==OBConstant.SettInstrStatus.SIGN){
			strSQL.append(" AND ob.nIsCanAccept = 1 \n");
			strSQL.append(" AND ob.nSignUserID = " + finanInfo.getUserID() + " \n");
					
				}			
			strSQL.append("  and ob.sbatchno =  " +  "'"+finanInfo.getSBatchNo().toString()+"'");
		if(finanInfo.getStatus()>0){
			strSQL.append("  and ob.nstatus = "+finanInfo.getStatus());
			}
			

			this.prepareStatement(strSQL.toString());
			//this.transPS.setString(1, finanInfo.getSBatchNo().trim());
			log4j.print(strSQL.toString());
			log4j.print(finanInfo.getSBatchNo().trim());
			transRS = this.executeQuery();
			
			log4j.print("attttttttttttttttttttttt           finanInfo.getSBatchNo() = "+finanInfo.getSBatchNo());
			log4j.print("attttttttttttttttttttttt           transRS = "+transRS);
			
			while(transRS!=null && transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setID(transRS.getLong("id"));
				info.setCurrencyID(transRS.getLong("ncurrencyid"));
				info.setPayerAcctNo(transRS.getString("payeracctno"));
				info.setPayerName(transRS.getString("payername"));
				info.setAmount(transRS.getDouble("mamount"));
				info.setPayeeAcctNo(transRS.getString("payeeacctno"));
				info.setPayeeName(transRS.getString("payeename"));
				info.setNote(transRS.getString("snote"));
				info.setSStatus(String.valueOf(transRS.getLong("nstatus")));
				info.setTransType(transRS.getLong("nremittype"));
				info.setPayeeProv(transRS.getString("spayeeprov"));
				info.setPayeeCity(transRS.getString("spayeecity")); 
				info.setPayeeBankName(transRS.getString("spayeebankname"));
				info.setDtModify(transRS.getTimestamp("dtmodify"));
				//财企接口新增
				info.setSPayeeBankCNAPSNO(transRS.getString("spayeebankcnapsno"));
				info.setSPayeeBankExchangeNO(transRS.getString("spayeebankexchangeno"));
				info.setSPayeeBankOrgNO(transRS.getString("spayeebankorgno"));
				info.setApplyCode(transRS.getString("sapplycode"));
				info.setTransType(transRS.getLong("ntranstype"));
				info.setSignatureValue(transRS.getString("signaturevalue"));
				info.setPayerAcctID(transRS.getLong("npayeracctid"));
				info.setConfirmUserID(transRS.getLong("nconfirmuserid"));	
				info.setSBatchNo(transRS.getString("sbatchno"));
				lstReturn.add(info);
			}
			
		}catch(Exception e){
			log.error(e.toString());
			throw new ITreasuryException();
		}finally{
			finalizeDAO();
		}
		return lstReturn;

	}
	
	public List findFinanceInfoByBatchno(FinanceInfo finanInfo) throws Exception
	{
		List lstReturn  = new ArrayList();
		try
		{
			initDAO();
			
			log4j.print("aaaaaaaaaaaaaa:"+finanInfo.getSBatchNo());
			
			
			StringBuffer strSQL = new StringBuffer();
			strSQL.append(" select ob.*, \n");
			strSQL.append(" sa.accountno payeeacctno, \n");
			strSQL.append(" sa.name payeename, \n");			
			strSQL.append(" sa.prov spayeeprov, \n");// 华联银行汇款显示收款方：汇入省
			strSQL.append(" sa.city spayeecity, \n");//华联银行汇款显示收款方：汇入市
			strSQL.append(" sa.bankname spayeebankname , \n");//  华联银行汇款显示收款方：汇入行   
			
			strSQL.append(" sa.bankexchangeno spayeebankexchangeno,\n");    
			strSQL.append(" sa.bankcnapsno spayeebankcnapsno, \n");     
			strSQL.append(" sa.bankorgno spayeebankorgno, \n");  
			
			strSQL.append(" sett.saccountno payeracctno, \n");
			strSQL.append(" sett.sname payername \n");			
			strSQL.append("  from ob_financeinstr   ob,\n");
			strSQL.append("  (  select t1.id, \n");
			strSQL.append("   'bank' payeeType, \n");
			strSQL.append("   t1.SPAYEEACCTNO ACCOUNTNO, \n");
			strSQL.append("   t1.SPAYEENAME NAME, \n");
			strSQL.append("   t1.SPAYEEPROV PROV, \n");
			strSQL.append("   t1.SPAYEECITY CITY, \n");
			strSQL.append("   t1.SPAYEEBANKNAME BANKNAME, \n");

			strSQL.append("   t1.spayeebankexchangeno bankexchangeno, \n");
			strSQL.append("   t1.spayeebankcnapsno bankcnapsno, \n");
			strSQL.append("   t1.spayeebankorgno bankorgno \n");
			
			strSQL.append("   from OB_PAYEEINFO t1 \n");
			strSQL.append("   union \n");
			strSQL.append("   select t2.id, \n");
			strSQL.append("   'system' payeeType, \n");
			strSQL.append("   t2.SACCOUNTNO ACCOUNTNO, \n");
			strSQL.append("   t2.SNAME NAME, \n");
			strSQL.append("   '' PROV, \n");
			strSQL.append("   '' CITY, \n");
			strSQL.append("   '' BANKNAME, \n");

			strSQL.append("   '' bankexchangeno, \n");
			strSQL.append("   '' bankcnapsno, \n");
			strSQL.append("   '' bankorgno \n");
			
			
			strSQL.append("   from SETT_ACCOUNT t2  )    sa, \n");
			strSQL.append("   sett_account sett \n");
		
			strSQL.append(" where ob.npayeeacctid = sa.id(+) \n");
			strSQL.append(" and ob.npayeracctid = sett.id(+) \n");
			strSQL.append(" and decode(ob.NREMITTYPE, 102, 'bank', 'system') = sa.payeetype \n");
			strSQL.append("  and ob.sbatchno =  " +  "'"+finanInfo.getSBatchNo().toString()+"'");
			strSQL.append("  and ob.nstatus = 1");

			this.prepareStatement(strSQL.toString());
			//this.transPS.setString(1, finanInfo.getSBatchNo().trim());
			log4j.print(strSQL.toString());
			log4j.print(finanInfo.getSBatchNo().trim());
			transRS = this.executeQuery();
			
			log4j.print("attttttttttttttttttttttt           finanInfo.getSBatchNo() = "+finanInfo.getSBatchNo());
			log4j.print("attttttttttttttttttttttt           transRS = "+transRS);
			
			while(transRS!=null && transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setID(transRS.getLong("id"));
				info.setCurrencyID(transRS.getLong("ncurrencyid"));
				info.setPayerAcctNo(transRS.getString("payeracctno"));
				info.setPayerName(transRS.getString("payername"));
				info.setAmount(transRS.getDouble("mamount"));
				info.setPayeeAcctNo(transRS.getString("payeeacctno"));
				info.setPayeeName(transRS.getString("payeename"));
				info.setNote(transRS.getString("snote"));
				info.setSStatus(String.valueOf(transRS.getLong("nstatus")));
				info.setTransType(transRS.getLong("nremittype"));
				info.setPayeeProv(transRS.getString("spayeeprov"));   //华联银行汇款储存收款方：汇入省
				info.setPayeeCity(transRS.getString("spayeecity"));   //华联银行汇款储存收款方：汇入市
				info.setPayeeBankName(transRS.getString("spayeebankname"));//华联银行汇款储存收款方：汇入行	
				info.setDtModify(transRS.getTimestamp("dtmodify"));//上次修改时间 
				info.setApplyCode(transRS.getString("sapplycode"));
				info.setSPayeeBankCNAPSNO(transRS.getString("spayeebankcnapsno"));
				info.setSPayeeBankExchangeNO(transRS.getString("spayeebankexchangeno"));
				info.setSPayeeBankOrgNO(transRS.getString("spayeebankorgno"));
				//info.setTransType(transRS.getLong("ntranstype"));
				info.setSignatureValue(transRS.getString("signaturevalue"));
				info.setPayerAcctID(transRS.getLong("npayeracctid"));
				info.setConfirmUserID(transRS.getLong("nconfirmuserid"));
				info.setSBatchNo(transRS.getString("sbatchno"));
				lstReturn.add(info);
			}
			
		}catch(Exception e){
			log.error(e.toString());
			throw new ITreasuryException();
		}finally{
			finalizeDAO();
		}
		return lstReturn;

	}
	/**
	 * 根据财务交易指令ID，查询指令信息
	 * Create Date: 2003-8-13
	 * @param lInstructionID 财务交易指令ID
	 * @param lUserID 登录人ID
	 * @param lCurrencyID 币种ID
	 * @return FinanceInfo
	 * @exception Exception
	 */
	public FinanceInfo findByID(long lInstructionID, long lUserID, long lCurrencyID) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.* ,");
			sbSQL.append(" cfmUser.sname confirmUserName,");
			sbSQL.append(" checkUser.sname checkUserName,");
			sbSQL.append(" signUser.sname signUserName,");
			sbSQL.append(" delUser.sname delUserName,");
			sbSQL.append(" office.sname officename,");
			sbSQL.append(" cpfUser.sname DealUserName, ");
			sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid \n");
			sbSQL.append(" FROM ob_FinanceInStr fin,");
			sbSQL.append(" OB_USER cfmUser,");
			sbSQL.append(" OB_USER checkUser,");
			sbSQL.append(" OB_USER signUser,");
			sbSQL.append(" OB_USER delUser,");
			sbSQL.append(" office ,");
			sbSQL.append(" userinfo cpfUser, ");
			sbSQL.append(" BS_BANKINSTRUCTIONINFO bs ");
			sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+)");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) ");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+)");
			sbSQL.append(" and bs.s_transactionno(+)='1'||to_char(fin.CPF_STRANSNO) \n");	
			sbSQL.append(" AND fin.id=?");
			log4j.info("lInstructionID=" + lInstructionID);
			log4j.info("SQL=\n" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInstructionID);
			log4j.info("FinanceInstrEJB.findByID()\n");
			log4j.info("SQL=\n" + sbSQL.toString());
			rs = ps.executeQuery();
			log4j.info("findByID():success\n");
			//
			info = new FinanceInfo();
			if (rs.next())
			{
				info.setID(rs.getLong("ID")); // 指令序号
				info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
				info.setContractID(rs.getLong("NContractID")); // 贷款合同ID
				info.setLoanNoteID(rs.getLong("NLoanNoteID")); //放款通知单ID
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setSubAccountID(rs.getLong("nSubAccountID")); //子账户ID
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setClearInterest(rs.getTimestamp("dtClearInterest")); //结息日
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
				info.setNoticeDay(rs.getLong("nnoticeday")); //通知存款品种
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名	
				info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(rs.getString("CPF_STRANSNO")); //CPF-交易号
				info.setFinishDate(rs.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
				info.setReject(rs.getString("CPF_SREJECT")); //CPF-拒绝原因
				info.setIsCanAccept(rs.getLong("NISCANACCEPT")); //能否接受
				info.setDefaultTransType(rs.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户
				info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //结息日期
				info.setInterestStart(rs.getTimestamp("DTINTERESTSTART"));
				info.setCompoundStart(rs.getTimestamp("DTCOMPOUNDINTERESTSTART"));
				info.setCompoundRate(rs.getDouble("MCOMPOUNDRATE"));
				info.setOverDueStart(rs.getTimestamp("DTOVERDUESTART"));
				info.setOverDueRate(rs.getDouble("MOVERDUERATE"));
				info.setSuretyStart(rs.getTimestamp("DTSURETYFEESTART"));
				info.setSuretyRate(rs.getDouble("MSURETYFEERATE"));
				info.setCommissionStart(rs.getTimestamp("DTCOMMISSIONSTART"));
				info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
				info.setInterestRate(rs.getDouble("MLOANREPAYMENTRATE"));
				info.setCompoundAmount(rs.getDouble("MCOMPOUNDAMOUNT"));
				info.setOverDueAmount(rs.getDouble("MOVERDUEAMOUNT"));
				info.setInterestReceiveable(rs.getDouble("MINTERESTRECEIVEABLE"));
				info.setInterestIncome(rs.getDouble("MINTERESTINCOME"));
				info.setRealInterestReceiveable(rs.getDouble("MREALINTERESTRECEIVEABLE"));
				info.setRealInterestIncome(rs.getDouble("MREALINTERESTINCOME"));
				info.setInterestTax(rs.getDouble("MINTERESTTAX"));
				info.setRealInterestTax(rs.getDouble("MREALINTERESTTAX"));
				info.setReturnMsg(rs.getString("sReturnMsg"));
				//modify by lxr for budget 增加 budgetitemID 字段
				info.setBudgetItemID(rs.getLong("budgetItemID"));
				//modify by juncai  增加 ISFIXCONTINUE 字段
				info.setIsFixContinue(rs.getLong("ISFIXCONTINUE"));
				//modify by juncai  增加 FIXEDREMARK 字段
				info.setFixEdremark(rs.getString("FIXEDREMARK"));
				//modify by juncai  增加 MAMOUNTFORTRANS 字段
				info.setMamOuntForTrans(rs.getDouble("MAMOUNTFORTRANS"));
				info.setSDepositBillNo(rs.getString("sDepositBillNo"));
				//rs.getLong()在rs字段为空的情况下，默认为0
				if(rs.getObject("nDepositBillStatusId")!=null)
				{
					info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				}
				info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
				info.setNDepositBillSignUserID(rs.getLong("NDEPOSITBILLSIGNUSERID"));
				info.setDtDepositBillSignDate(rs.getTimestamp("DTDEPOSITBILLSIGNDATE"));
				info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));
				info.setSDepositBillEndDate(rs.getTimestamp("FIXEDNEXTENDDATE"));
				info.setSDepositBillPeriod(rs.getLong("FIXEDNEXTPERIOD"));
				info.setSDepositInterestDeal(rs.getLong("FIXEDINTERESTDEAL"));
				info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID"));
				if(rs.getLong("isautocontinue") > 0)
				{
					info.setIsAutoContinue(rs.getLong("isautocontinue"));
				}
				if(rs.getLong("autocontinuetype") > 0)
				{
					info.setAutocontinuetype(rs.getLong("autocontinuetype"));
				}
				if(rs.getLong("autocontinueaccountid") > 0)
				{
					info.setAutocontinueaccountid(rs.getLong("autocontinueaccountid"));
				}
				
				//added by mzh_fu 2007/05/21 增加签名值
				info.setSignatureValue(rs.getString("SIGNATUREVALUE"));
				info.setActionStatus(rs.getLong("ACTIONSTATUS"));
                info.setSource(rs.getLong("LSOURCE"));
                info.setApplyCode(rs.getString("SAPPLYCODE"));
                //add by xwhe 2008-10-24 增加批次号
                info.setSBatchNo(rs.getString("sbatchno"));
                log4j.print("~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@"+rs.getTimestamp("dtmodify"));
                info.setDtModify(rs.getTimestamp("dtmodify"));
                info.setEbankStatus(rs.getLong("n_statusid"));
                
                //Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
                info.setRemitArea(rs.getLong("remitArea"));	//汇款区域
                info.setRemitSpeed(rs.getLong("remitSpeed"));	//汇款速度
                
                //ADD xiangzhou 增加数据来源
                info.setSource(rs.getLong("lsource"));	//数据来源
                info.setApplyCode(rs.getString("sapplycode"));	//申请指令编号
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//下一级审批级别和是否曾经被拒绝过 added by yanliu 2007/05/21
			if(info.getID()>0)
			{
				//下一级审批级别
				InutApprovalRecordBiz biz = new InutApprovalRecordBiz();
				InutApprovalRecordInfo qInfo = new InutApprovalRecordInfo();
				qInfo.setTransID(String.valueOf(info.getID()));
				qInfo.setStatusID(Constant.RecordStatus.VALID);
				qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				//pInfo.setTransTypeID(info.getTransType());
				Collection c = biz.queryByCondition(qInfo);
				if(c!=null && c.size()>0)
				{
					InutApprovalRecordInfo tempInfo = (InutApprovalRecordInfo)(c.toArray()[0]);
					info.setNextLevel(tempInfo.getNextLevel());
				}
				else
				{
					info.setNextLevel(0);
				}
				
				//是否被拒绝过,有无效状态的审批记录时则表明曾经被拒绝过
				qInfo = new InutApprovalRecordInfo();
				qInfo.setTransID(String.valueOf(info.getID()));
				qInfo.setStatusID(Constant.RecordStatus.INVALID);//无效状态
				qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
				c = biz.queryByCondition(qInfo);
				if(c!=null && c.size()>0)
				{
					info.setRefused(true);
				}
				else
				{
					info.setRefused(false);
				}				
			}	
			
			//下属单位
			if (info.getChildClientID() > 0)
			{
				info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
				info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
			}
			//合同编号以及放款通知单编号
			info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); //贷款合同编号
			info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单编号
			//获取收款方和付款方的详细信息
			PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
			PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
			PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
			payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
			payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
			interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
			info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
			info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
			info.setPayerName(payerInfo.getAccountName()); // 付款方名称
			info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
			info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
			info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
			info.setPayeeProv(payeeInfo.getProv()); // 汇入省
			info.setPayeeCity(payeeInfo.getCity()); // 汇入市
			info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
			
			info.setSPayeeBankCNAPSNO(payeeInfo.getSPayeeBankCNAPSNO());
			info.setSPayeeBankExchangeNO(payeeInfo.getSPayeeBankExchangeNO());
			info.setSPayeeBankOrgNO(payeeInfo.getSPayeeBankOrgNO());
			
			info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
			info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
			info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
			info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
			info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
			info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
			
			info.setSInterestPayeeBankExchangeNO(interestpPayeeInfo.getSPayeeBankExchangeNO());
			info.setSInterestPayeeBankCNAPSNO(interestpPayeeInfo.getSPayeeBankCNAPSNO());
			info.setSInterestPayeeBankOrgNO(interestpPayeeInfo.getSPayeeBankOrgNO());
			
			// 获得账户的当前余额 
			AccountBalanceInfo abInfo = new AccountBalanceInfo();
			abInfo = getCurrBalanceByAccountID(info.getPayerAcctID(), lCurrencyID, lInstructionID);
			info.setCurrentBalance(abInfo.getCurrentBalance());
			info.setIsCycleAccount(abInfo.getIsCycleAccount());
			info.setMaxUsableAmount(abInfo.getMaxUsableAmount());
			info.setOverdraftAmount(abInfo.getOverdraftAmount());
			info.setUsableBalance(abInfo.getUsableBalance());
			// 获取是否需要当前用户签认
			if (((info.getAmount() + info.getRealInterest() + info.getRealOverdueInterest() + info.getRealCompoundInterest() + info.getRealSuretyFee() + info.getRealCommission())
				>= this.getMinSignAmountDist(lUserID, info.getClientID(), lCurrencyID, String.valueOf(info.getTransType())))
				&& ((info.getAmount() + info.getRealInterest() + info.getRealOverdueInterest() + info.getRealCompoundInterest() + info.getRealSuretyFee() + info.getRealCommission())
					< this.getMaxSignAmountDist(lUserID, info.getClientID(), lCurrencyID, String.valueOf(info.getTransType())))
				&& (info.getIsCanAccept() != 1))
			{
				info.setIsNeedSign(true);
			}
			if (info.getLoanNoteID() > 0)
			{
				//获得当前放款通知单的贷款余额
				con = Database.getConnection();
				sbSQL = new StringBuffer();
				//根据放款通知单id得到子账户ID
				log4j.info("=============查找贷款余额开始=======");
				long lSubAccountID = -1;
				sbSQL.append("select ID from sett_SubAccount ");
				sbSQL.append("where AL_nLoanNoteID=");
				sbSQL.append(info.getLoanNoteID() + " \n");
				sbSQL.append("and nStatusId=1");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lSubAccountID = rs.getLong(1);
					log4j.info("SubAccountId:" + lSubAccountID);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL = new StringBuffer();
				if (lSubAccountID > 0)
				{
					if (info.getTransType()==OBConstant.SettInstrType.YTLOANRECEIVE)
					{
						sbSQL.append("select round((a.mAmount-nvl(dd.mAmount,0)),2) MBalance");
						sbSQL.append(" from LOAN_PAYFORM a,");
						sbSQL.append(" (select nvl(sum(aa.mAmount),0) mAmount,aa.nFormid nFormid ");
						sbSQL.append(" from SETT_SYNDICATIONLOANINTEREST aa, SETT_TRANSREPAYMENTLOAN bb");
						sbSQL.append(" where bb.id = aa.nsyndicationLoanReceiveID");
						sbSQL.append(" and  bb.nStatusID in (2,3)");
						sbSQL.append(" group by aa.nFormid) dd");
						sbSQL.append(" where a.id = dd.nFormid(+)");
						sbSQL.append(" and a.id="+info.getLoanNoteID());
					}
					else
					{
						sbSQL.append("select MBalance ");
						sbSQL.append(" from SETT_SUBACCOUNT where ID=? ");
					}
					
					ps = con.prepareStatement(sbSQL.toString());
					if (info.getTransType()!=OBConstant.SettInstrType.YTLOANRECEIVE)
					{
						ps.setLong(1, lSubAccountID);
					}
					rs = ps.executeQuery();
					if (rs.next())
					{
						info.setBalance(rs.getDouble("MBalance"));
						log4j.info("MBalance:" + info.getBalance());
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				log4j.info("=============查找贷款余额结束=======");

				LoanPayNoticeDao lpndao = new LoanPayNoticeDao();
				info.setRate(lpndao.getLatelyRate(info.getLoanNoteID(), null));

				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			//log4j.error(e.toString());
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
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				//log4j.error(e.toString());
				e.printStackTrace();
				throw new IException("Gen_E001");
			}
		}
		log4j.info("findbyid返回查询结果");
		log4j.print("查询出来2"+info.getSBatchNo());
		return info;
	}
	/**
	 * 根据财务交易指令ID，查询指令信息
	 * Create Date: 2004-2-3
	 * @param lInstructionID 财务交易指令ID	 
	 * @return FinanceInfo
	 * @exception Exception
	 */
	public FinanceInfo findByID(long lInstructionID) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.* ,");
			sbSQL.append(" cfmUser.sname confirmUserName,");
			sbSQL.append(" checkUser.sname checkUserName,");
			sbSQL.append(" signUser.sname signUserName,");
			sbSQL.append(" delUser.sname delUserName,");
			sbSQL.append(" office.sname officename,");
			sbSQL.append(" cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM ob_FinanceInStr fin,");
			sbSQL.append(" OB_USER cfmUser,");
			sbSQL.append(" OB_USER checkUser,");
			sbSQL.append(" OB_USER signUser,");
			sbSQL.append(" OB_USER delUser,");
			sbSQL.append(" office ,");
			sbSQL.append(" userinfo cpfUser");
			sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+)");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) ");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+)");
			sbSQL.append(" AND fin.id=?");
			log4j.info("lInstructionID=" + lInstructionID);
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInstructionID);
			log4j.info("FinanceInstrEJB.findByID()\n");
			log4j.info("SQL=\n" + sbSQL.toString());
			rs = ps.executeQuery();
			log4j.info("findByID():success\n");
			//
			info = new FinanceInfo();			
			if (rs.next())
			{
				info.setID(rs.getLong("ID")); // 指令序号
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
				info.setContractID(rs.getLong("NContractID")); // 贷款合同ID
				info.setLoanNoteID(rs.getLong("NLoanNoteID")); //放款通知单id
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setOfficeID(rs.getLong("CPF_NOFFICEID"));
				info.setIsCanAccept(rs.getLong("NISCANACCEPT")); //能否接受
				//CPF-默认办事处ID
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID
				info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户
				info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //结昔日期
				info.setReturnMsg(rs.getString("sReturnMsg"));//失败原因（出错信息）
				
				//结算交易类型
				info.setDefaultTransType(rs.getLong("cpf_ndefaulttranstype"));
				
				//modify by lxr for budget 增加 budgetitemID 字段
				info.setBudgetItemID(rs.getLong("budgetItemID"));
				//modify by juncai  增加 ISFIXCONTINUE 字段
				info.setIsFixContinue(rs.getLong("ISFIXCONTINUE"));
				//modify by juncai  增加 FIXEDREMARK 字段
				info.setFixEdremark(rs.getString("FIXEDREMARK"));
				//modify by juncai  增加 MAMOUNTFORTRANS 字段
				info.setMamOuntForTrans(rs.getDouble("MAMOUNTFORTRANS"));
				
				
				//added by mzh_fu 2007/05/21 增加签名值
				info.setSignatureValue(rs.getString("SIGNATUREVALUE"));
				info.setActionStatus(rs.getLong("ACTIONSTATUS"));
                info.setSource(rs.getLong("LSOURCE"));
                info.setApplyCode(rs.getString("SAPPLYCODE"));
                //add by xwhe 2008-11-27 增加批次号
                info.setSBatchNo(rs.getString("sbatchno"));
                info.setDtModify(rs.getTimestamp("dtmodify"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//合同编号以及放款通知单编号
			info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); //贷款合同编号
			info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单编号
			//获取收款方和付款方的详细信息
			PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
			PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
			PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
			payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getTransType(), OBConstant.PayerOrPayee.PAYER);
			payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getTransType(), OBConstant.PayerOrPayee.PAYEE);
			interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
			info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
			info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
			info.setPayerName(payerInfo.getAccountName()); // 付款方名称
			info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
			info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
			info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
			info.setPayeeProv(payeeInfo.getProv()); // 汇入省
			info.setPayeeCity(payeeInfo.getCity()); // 汇入市
			info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
			info.setBankName(payeeInfo.getBankAllName());  //汇入行全称
			info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
			info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
			info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
			info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
			info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
			info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
			info.setSInterestBankName(interestpPayeeInfo.getBankAllName());  //利息汇入行全称
			// 获得账户的当前余额 
			AccountBalanceInfo abInfo = new AccountBalanceInfo();
			abInfo = getCurrBalanceByAccountID(info.getPayerAcctID(), info.getCurrencyID(), lInstructionID);
			info.setCurrentBalance(abInfo.getCurrentBalance());
			info.setIsCycleAccount(abInfo.getIsCycleAccount());
			info.setMaxUsableAmount(abInfo.getMaxUsableAmount());
			info.setOverdraftAmount(abInfo.getOverdraftAmount());
			info.setUsableBalance(abInfo.getUsableBalance());
			if (info.getLoanNoteID() > 0)
			{
				//获得当前放款通知单的贷款余额
				con = Database.getConnection();
				sbSQL = new StringBuffer();
				//根据放款通知单id得到子账户ID
				log4j.info("=============查找贷款余额开始=======");
				long lSubAccountID = -1;
				sbSQL.append("select ID from sett_SubAccount ");
				sbSQL.append("where AL_nLoanNoteID=");
				sbSQL.append(info.getLoanNoteID() + " \n");
				sbSQL.append("and nStatusId=1");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lSubAccountID = rs.getLong(1);
					log4j.info("SubAccountId:" + lSubAccountID);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				sbSQL = new StringBuffer();
				if (lSubAccountID > 0)
				{
					sbSQL.append("select MBalance ");
					sbSQL.append(" from SETT_SUBACCOUNT where ID=? ");
					ps = con.prepareStatement(sbSQL.toString());
					ps.setLong(1, lSubAccountID);
					rs = ps.executeQuery();
					if (rs.next())
					{
						info.setBalance(rs.getDouble("MBalance"));
						log4j.info("MBalance:" + info.getBalance());
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
				}
				log4j.info("=============查找贷款余额结束=======");
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		log4j.info("findbyid返回查询结果");
		return info;
	}
	/**
	 * 修改指令交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatus(long lID, long lStatusID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	
	/**
	 * 修改指令交易状态和取消 复核/审批 标识的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatusAndActionStatus(long lID, long lStatusID,long lActionStatus) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=?,nIsCanAccept=?,actionstatus=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, -1);//modify by xwhe 2008-11-27 取消复核的时候将接受指令状态手动改为-1.
			ps.setLong(index++, lActionStatus);
			ps.setLong(index++, lID);
			
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	
	/**
	 * 修改指令交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long updateStatusAndTransNo(Connection con, FinanceInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = Database.getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(" update OB_FinanceInstr set nstatus=?, cpf_sTransno=?, cpf_nDealUserId=?, cpf_dtDeal=?, cpf_dtFinish=? ");
			if(info.getInterestRate() > 0.0)
			{
				buffer.append(" , mLoanRepayMentRate=? ");
			}
			if(info.getDepositRate() > 0.0)
			{
				buffer.append(" , MDEPOSITRATE=? ");
			}
			//modify by xwhe 2008-11-18
			if(info.getReject()!= null && info.getReject().trim().length()>0)
			{
				buffer.append(" , CPF_SREJECT=? ");
			}
			buffer.append(" where ID=? ");
			
			ps = conInternal.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, info.getStatus());
			ps.setString(index++, info.getTransNo());
			ps.setLong(index++, info.getDealUserID());
			ps.setTimestamp(index++, info.getConfirmDate());
			ps.setTimestamp(index++, info.getFinishDate());
			if(info.getInterestRate() > 0.0)
			{
				ps.setDouble(index++, info.getInterestRate());
			}
			if(info.getDepositRate() > 0.0)
			{
				ps.setDouble(index++, info.getDepositRate());
			}
			if(info.getReject()!= null && info.getReject().trim().length()>0)
			{
				ps.setString(index++, info.getReject());
			}
			ps.setLong(index++, info.getID());
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
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
			    if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con == null)
				{
					if (conInternal != null)
					{
						conInternal.close();
						conInternal = null;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 查找指令交易状态的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long findstatusAndTransNO(Connection con, FinanceInfo info) throws Exception
	{
		long lReturn = -1;
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = Database.getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(" select * from  OB_FinanceInstr  ");

			
			buffer.append(" where ID=? and nstatus = ? ");
			
			ps = conInternal.prepareStatement(buffer.toString());
			int index = 1;

			ps.setLong(index++, info.getID());
			ps.setLong(index++, OBConstant.SettInstrStatus.CHECK);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
			else
			{
				throw new IException("银行指令号"+info.getID()+"已经被处理，请检查！");
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
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con == null)
				{
					if (conInternal != null)
					{
						conInternal.close();
						conInternal = null;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改指令拒绝原因的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long refuseInstr(FinanceInfo info) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		Timestamp tsDealDate = Env.getSystemDate(info.getOfficeID(), info.getCurrencyID());
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=?,cpf_sReject=?,cpf_nDealUserId=?,cpf_dtDeal=? where ID=? and dtModify =? ");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, OBConstant.SettInstrStatus.REFUSE);
			ps.setString(index++, info.getReject());
			ps.setLong(index++, info.getUserID());
			ps.setTimestamp(index++, tsDealDate);
			ps.setLong(index++, info.getID());
			ps.setTimestamp(index++, info.getDtModify());
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = info.getID();
			}
			else
			{
				throw new IException("银行指令号"+info.getID()+"已经被修改，请检查！");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("银行指令号"+info.getID()+"已经被修改，请检查！");
		}
		finally
		{
			try
			{
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 修改指令拒绝原因的方法：
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long refuseInstr(long lID, String strReject, long lUserID, Timestamp tsDealDate) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=?,cpf_sReject=?,cpf_nDealUserId=?,cpf_dtDeal=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, OBConstant.SettInstrStatus.REFUSE);
			ps.setString(index++, strReject);
			ps.setLong(index++, lUserID);
			ps.setTimestamp(index++, tsDealDate);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 放弃指令
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long abandonInstr(long lID, long lStatusID, long lUserID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=?,cpf_nDealUserId=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lUserID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();
			if (i > 0)
			{
				lReturn = lID;
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 接收指令
	 * 逻辑说明：
	 * 
	 * @param lID, long, 标识
	 * @param lStatusID, long, 状态标识
	 * @return long, 被修改记录的标识
	 * @throws Exception
	 */
	public long acceptInstr(long lID, long lStatusID, long lUserID) throws Exception
	{
		long lReturn = -1;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = Database.getConnection();
			StringBuffer buffer = new StringBuffer();
			buffer.append("update OB_FinanceInstr set nstatus=?,cpf_nDealUserId=? where ID=?");
			ps = con.prepareStatement(buffer.toString());
			int index = 1;
			ps.setLong(index++, lStatusID);
			ps.setLong(index++, lUserID);
			ps.setLong(index++, lID);
			int i = ps.executeUpdate();

			if (i > 0)
			{
				lReturn = lID;
			}
			else
			{
				lReturn = -9;
			}
		}
		catch (IException e)
		{
			log4j.error(e.toString());
			throw e;
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
				e.printStackTrace();
				throw e;
			}
		}
		return lReturn;
	}
	/**
	 * 获得账户的网上交易金额（网上银行模块种此账户的所有已经保存确认复核签认的付款指令金额值和）
	 * Create Date: 2003-8-13
	 * @param lAccountID 账户ID
	 * @param lCurrencyID 币种
	 * @param strTransactionNo 当前交易号
	 * @return double
	 * @exception Exception
	 */
	public double getUsableBalanceByAccountID(long lAccountID, long lCurrencyID, long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = 0.0;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT (SUM(mAmount)+SUM(MREALINTEREST)+SUM(MREALCOMPOUNDINTEREST)+SUM(MREALOVERDUEINTEREST)+SUM(MREALSURETYFEE)+SUM(MREALCOMMISSION))  aa ";
			strSQL += " FROM ob_FinanceInStr";
			strSQL += " WHERE 1=1"; 
			if (lCurrencyID>0)	
				strSQL+=" AND ncurrencyid = " + lCurrencyID;
			strSQL += " AND npayeracctID = '" + lAccountID + "'";
			strSQL += " AND (nStatus = " + OBConstant.SettInstrStatus.SAVE;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.APPROVALING;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.APPROVALED;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.CHECK;
			strSQL += " OR (nStatus = " + OBConstant.SettInstrStatus.SIGN+ ")";
			strSQL += " OR (nStatus = " + OBConstant.SettInstrStatus.DEAL + " and  cpf_stransno is null))";
			if (lInstructionID != -1)
			{
				strSQL += " AND ID != " + lInstructionID;
			}
			log4j.info("SQL= \n" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble(1);
			}
			rs.close();
			log4j.print("----strSQL----="+strSQL);
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dReturn;
	}
	/**
	 * 获得账户的当前余额、可用余额、最大可发放金额、透支限额、可用上存
	 * Create Date: 2003-8-13
	 * @param strAccountNo 账号
	 * @param LcurrencyID 币种
	 * @param strTransactionNo 当前交易号
	 * @return AccountBalanceInfo
	 * @exception Exception
	 */
	public AccountBalanceInfo getCurrBalanceByAccountID(long lAccountID, long lCurrencyID, long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AccountBalanceInfo info = new AccountBalanceInfo();
		double dCPF2Amount = 0;
		double dTemp = 0.00;
		try
		{
			con = Database.getConnection();
			//网银已确认，已复核，已签认交易指令金额总计，调用getUsableBalanceByAccountID()
			dCPF2Amount = getUsableBalanceByAccountID(lAccountID, lCurrencyID, lInstructionID);
			log4j.info("dCPF2Amount=" + dCPF2Amount);
			String strSQL = "";
			strSQL += " SELECT ab.mAmount,";
			strSQL += " sa.AC_nFirstLimitTypeID,sa.AC_mFirstLimitAmount, ";
			strSQL += " sa.AC_nSecondLimitTypeID,sa.AC_mSecondLimitAmount, ";
			strSQL += " sa.AC_nThirdLimitTypeID,sa.AC_mThirdLimitAmount ";
			strSQL += " FROM ";
			strSQL += " (SELECT SUM(nvl(a.mBalance,0)-nvl(a.muncheckpaymentamount,0)) AS mAmount ";
			strSQL += " FROM  SETT_SubAccount a,SETT_Account b";
			strSQL += " WHERE a.nAccountID = b.id ";
			strSQL += " AND b.nStatusID = " + Constant.RecordStatus.VALID;
			strSQL += " AND b.ID = '" + lAccountID + "'";
			if (lCurrencyID>0)
				strSQL += " AND b.ncurrencyid = " + lCurrencyID;
			strSQL += " ) ab,";
			strSQL += " SETT_SubAccount sa,SETT_Account aa";
			strSQL += " WHERE sa.nAccountID = aa.id ";
			strSQL += " AND aa.nStatusID = " + Constant.RecordStatus.VALID;
			strSQL += " AND aa.id = '" + lAccountID + "'";
			if (lCurrencyID>0)
				strSQL += " AND aa.ncurrencyid = " + lCurrencyID;
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info.setCurrentBalance(rs.getDouble("mAmount"));
				log4j.print("----CurrentBalance---="+rs.getDouble("mAmount"));
				info.setUsableBalance(info.getCurrentBalance() - dCPF2Amount);
				if (rs.getLong("AC_nFirstLimitTypeID") == OBConstant.AccountOverDraftType.ALL)
				{
					info.setOverdraftAmount(rs.getDouble("AC_mFirstLimitAmount"));
				}
				else
				{
					if (rs.getLong("AC_nSecondLimitTypeID") == OBConstant.AccountOverDraftType.ALL)
					{
						info.setOverdraftAmount(rs.getDouble("AC_mSecondLimitAmount"));
					}
					else
					{
						if (rs.getLong("AC_nThirdLimitTypeID") == OBConstant.AccountOverDraftType.ALL)
						{
							info.setOverdraftAmount(rs.getDouble("AC_mThirdLimitAmount"));
						}
					}
				}
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	/**
	 * 检测用户是否拥有该账户的操作权限
	 * Create Date: 2003-8-13
	 * @param strAccountNo 账号
	 * @param lUserID 登录人ID
	 * @return boolean 是否有权限
	 * @exception Exception
	 */
	public boolean checkAccountIsOwnedByUser(long lAccountID, long lUserID,long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bIsPrvg = false;
		try
		{
			
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " select * from OB_AccountOwnedByUser a, Sett_Account ai ";
			strSQL +=" where ai.nStatusID=1 ";
			strSQL +=" and a.saccountno=ai.saccountno ";
			strSQL +=" and ai.ncurrencyid="+lCurrencyID;
			strSQL += " and a.nUserID =" + lUserID;
			strSQL += " and a.NACCOUNTID = '" + lAccountID + "'";
			log4j.info("*******判断账户权限*************");
			log4j.info("SQL=");
			log4j.info(strSQL);
			log4j.info("*******************************");
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				bIsPrvg = true;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return bIsPrvg;
	}
	/**
	 * 获得账户进行某项交易的权限
	 * Create Date: 2003-8-13
	 * @param strAccountNo 账号
	 * @param lInstrTypeID 指令类型
	 * @param lCurrencyID 币种ID
	 * @param lClientID 客户ID
	 * @return boolean 是否有权限
	 * @exception Exception
	 */
	public boolean getAccountPrvg(long lAccountID, long lInstrTypeID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bIsPrvg = false;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT * FROM OB_AccountPrvg  ";
			strSQL += " WHERE nAccountID =" + lAccountID;
			strSQL += " AND ntranstype =" + lInstrTypeID;
			log4j.info("*******判断账户权限*************");
			log4j.info("getAccountPrvg:SQL=");
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				bIsPrvg = true;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return bIsPrvg;
	}
	/**
	 * 获得某个财务交易指令类型的默认办事处
	 * Create Date: 2003-8-13
	 * @param strPayerAccountNo 付款方账号
	 * @param strPayeeAccountNo 收款方账号
	 * @param lTransType 交易类型
	 * @return long 交易指令类型的默认办事处
	 * @exception Exception
	 */
	public long getDefaultOfficeID(long clientid) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lReturn = -1;
		
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT nofficeid FROM client where id="+clientid;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				lReturn = rs.getLong("nofficeid");
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
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;		
	}
	/**
	 * 获得签认人ID，对交易指令复核时需要指定签认人，此方法返回签认人ID
	 * Create Date: 2003-8-13
	 * @param lInstructionID  交易指令ID
	 * @return long 签认人ID
	 * @exception Exception
	 */
	public long getSignUserID(long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		long lReturn = -1;
		long lSignUserID[] = new long[3];
		double dSignAmount[] = new double[4];
		double dFinanceAmount = 0;
		String signTable = "OB_SignAmount";
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			FinanceInfo financeInfo = this.findByID(lInstructionID);
			if(financeInfo.getTransType() == OBConstant.TransTypeSet.EXTERNALTRANSFER || financeInfo.getTransType() == OBConstant.TransTypeSet.INTERNALTRANSFER){
				signTable = "OB_SignAmount_Curr";
			}else{
				signTable = "OB_SignAmount_Fix";
			}
		}
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT a.nSignuserid,a.mAmount as SignAmount,(NVL(b.mAmount,0)+NVL(b.mRealInterest,0)+ \n ";
			strSQL += " NVL(b.mRealCompoundInterest,0)+NVL(b.mRealOverdueInterest,0)+ \n ";
			strSQL += " NVL(b.mRealSuretyFee,0)+NVL(b.mRealCommission,0)) as FinanceAmount ";
			strSQL += " FROM "+signTable+" a,OB_FinanceInStr b";
			strSQL += " WHERE a.nclientid = b.nclientid ";
			strSQL += " AND a.ncurrencyid = b.ncurrencyid ";
			strSQL += " AND b.id = " + lInstructionID;
			strSQL += " ORDER BY a.mAmount ";
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				lSignUserID[i] = rs.getLong("nsignuserid");
				dSignAmount[i] = rs.getDouble("SignAmount");
				dFinanceAmount = rs.getDouble("FinanceAmount");
				i++;
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
			if (con != null)
			{
				con.close();
				con = null;
			}
			dSignAmount[i] = 9999999999999999.999999;
			for (int n = 0; n < i; n++)
			{
				if (dSignAmount[n] <= dFinanceAmount && dFinanceAmount < dSignAmount[n + 1])
				{
					lReturn = lSignUserID[n];
					log4j.info("得到签认人ID== " + lSignUserID[n]);
					break;
				}
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lReturn;
	}
	/**
	 * 增加一条财务交易指令
	 * Create Date: 2003-8-13
	 * @param FinanceInfo 账号
	 * @return long 新增加的财务交易指令流水号
	 * @exception Exception
	 */
	public long add(FinanceInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lMaxID = -1;
		boolean bLocked = true;
		//此交易中的付款方账号，（上存资金调回中收款方账号）
		//进行交易的登录单位的账号
		long lAccountID = -1;
		long lPayeeAcctID = -1;
		try
		{
			con = Database.getConnection();
			//判断用户是否拥有账户的操作权限 : 付款方没有权限,收款方没有权限
			if (info.getTransType() == OBConstant.SettInstrType.OPENFIXDEPOSIT
				|| info.getTransType() == OBConstant.SettInstrType.OPENNOTIFYACCOUNT
				|| info.getTransType() == OBConstant.SettInstrType.TRUSTLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.CONSIGNLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.INTERESTFEEPAYMENT)
			{
				//定期开立，通知开立，贷款回收，控制付款方账户和收款方账户
				//查询出的付款方账号必须是该用户拥有权限的
				if (!checkAccountIsOwnedByUser(info.getPayerAcctID(), info.getConfirmUserID(),info.getCurrencyID()))
				{
					throw new IException("OB_EC10");
				}
				//查询出的收款方账号必须是该用户拥有权限的
				if (!checkAccountIsOwnedByUser(info.getPayeeAcctID(), info.getConfirmUserID(),info.getCurrencyID()))
				{
					throw new IException("OB_EC11");
				}
			}
			else
			{ //其它业务，只控制付款方账户
				//查询出的付款方账号必须是该用户拥有权限的
				if (!checkAccountIsOwnedByUser(info.getPayerAcctID(), info.getConfirmUserID(),info.getCurrencyID()))
				{
					throw new IException("OB_EC10");
				}
			}
			//判断当前账号是否有权限进行此项交易，没有页面提示账户没有设置进行此项交易
			lAccountID = info.getPayerAcctID();
			lPayeeAcctID = info.getPayeeAcctID();
			System.out.print(lAccountID);
			System.out.print(info.getTransType());
			System.out.print(lPayeeAcctID);
			if ((info.getTransType() == OBConstant.SettInstrType.OPENFIXDEPOSIT
					|| info.getTransType() == OBConstant.SettInstrType.OPENNOTIFYACCOUNT))
			{				
				if(!getPayeeAcctPrvg(lPayeeAcctID, info.getTransType()))
				{
				throw new IException("账户没有设置进行此项交易的权限!");
				}
			}
			if (!getAccountPrvg(lAccountID, info.getTransType()))
			{
				throw new IException("账户没有设置进行此项交易的权限!");
			}
			else
			{
				StringBuffer sb = new StringBuffer();
				// get max(id)+1 as PK
				log4j.info("get max(id)+1 as PK");
				//调用BizCapital方法，得到最大流水号
				lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
				
				if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY||info.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
				{	
					if(info.getSource()==SETTConstant.ExtSystemSource.EBANK)
					{
						info.setApplyCode(String.valueOf(lMaxID));
					}
				}
	
				sb.setLength(0);
				// insert into  拼写插入语句 
				log4j.info("财务指令插入语句= \n");
				sb.append(" INSERT INTO OB_FinanceInStr(  \n");
				sb.append("ID, nClientID, nCurrencyID,nTransType,");
				sb.append("nPayerAcctID,nRemitType,nPayeeAcctID, ");
				sb.append(" mAmount,nChildClientid, \n");	
				sb.append("dtExecute, sNote, nFixedDepositTime, nNoticeDay, ");
				sb.append("NCONTRACTID,  NLOANNOTEID, dtPay,");
				sb.append("sDepositNo,NSUBACCOUNTID, dtDepositStart, mDepositRate, mDepositAmount, mDepositBalance, \n");
				sb.append("nStatus, nConfirmUserID, dtConfirm, ");
				sb.append("CPF_nOfficeID, CPF_nDefaultTransType, \n");
				sb.append("DTCLEARINTEREST, nInterestPayeeAcctID, nInterestRemitType, ");
				sb.append("mInterest, mCompoundInterest, mOverdueInterest, mSuretyFee, mCommission, ");
				sb.append("mRealInterest, mRealCompoundInterest, mRealOverdueInterest, mRealSuretyFee, mRealCommission, \n");
				//sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX) \n");
				//modify by lxr for budget 增加 budgetitemID 字段
				//sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX,budgetItemID) \n");
				//modify by juncai 增加 ISFIXCONTINUE,FIXEDREMARK,MAMOUNTFORTRANS字段
				sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX,budgetItemID,ISFIXCONTINUE,FIXEDREMARK,MAMOUNTFORTRANS,sBatchNo,sApplyCode,lSource,isSameBank,isDiffLocal,dtmodify,isautocontinue,autocontinuetype,autocontinueaccountid, \n");
				
				//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
				sb.append("remitArea, remitSpeed,signatureoriginalvalue,signaturevalue,timestamp) \n");
				
				sb.append("VALUES \n");
				sb.append("(?,?,?,?,?,?,?,?,?," );
				if (info.getTransType()== OBConstant.SettInstrType.APPLYCAPITAL)
				{
					sb.append("sysdate,");
				}else
				{
					sb.append("?," );
				}					
				//sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//modify by lxr for budget 增加 budgetitemID 字段
				//sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//modify by juncai 增加 ISFIXCONTINUE,FIXEDREMARK,MAMOUNTFORTRANS字段
				sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?)");
				log4j.info("add插入语句=" + sb.toString());
				ps = con.prepareStatement(sb.toString());
				int nIndex = 1;
				//////// 第1行 8个字段
				log4j.info("lMaxID=" + lMaxID);
				ps.setLong(nIndex++, lMaxID);
				ps.setLong(nIndex++, info.getClientID()); // 网上银行客户ID
				ps.setLong(nIndex++, info.getCurrencyID()); // 币种
				ps.setLong(nIndex++, info.getTransType()); // 网上银行交易类型
				ps.setLong(nIndex++, info.getPayerAcctID()); // 付款方账户ID
				ps.setLong(nIndex++, info.getRemitType()); // 汇款方式
				ps.setLong(nIndex++, info.getPayeeAcctID()); // 收款方账户ID
				ps.setDouble(nIndex++, info.getAmount()); // 交易金额
				ps.setLong(nIndex++, info.getChildClientID()); // 下属单位
				///////// 第2行  12字段
				if (info.getTransType()!= OBConstant.SettInstrType.APPLYCAPITAL)
				{
					ps.setTimestamp(nIndex++, info.getExecuteDate()); // 执行日
				}
				
				ps.setString(nIndex++, info.getNote()); // 汇款用途/摘要
				ps.setLong(nIndex++, info.getFixedDepositTime()); // 定期存款期限（月）
				ps.setLong(nIndex++, info.getNoticeDay()); // 通知存款品种（天）
				ps.setLong(nIndex++, info.getContractID()); // 贷款合同ID
				ps.setLong(nIndex++, info.getLoanNoteID()); //方款通知单号
				ps.setTimestamp(nIndex++, info.getPayDate()); // 贷款放款日期
				ps.setString(nIndex++, info.getDepositNo()); //定期（通知）存款单据号
				ps.setLong(nIndex++, info.getSubAccountID()); //子账户ID
				ps.setTimestamp(nIndex++, info.getDepositStart()); //定期（通知）存款起始日
				ps.setDouble(nIndex++, info.getDepositRate()); //定期存单利率
				ps.setDouble(nIndex++, info.getDepositAmount()); //存单金额（开立金额）
				ps.setDouble(nIndex++, info.getDepositBalance()); //存单余额
				/////// 第3行  5字段(日期以有，4)
				ps.setLong(nIndex++, OBConstant.SettInstrStatus.SAVE); // 状态为未复核
				ps.setLong(nIndex++, info.getConfirmUserID()); // 录入人
				//ps.setLong(nIndex++, getDefaultOfficeID(info.getClientID())); // CPF-默认办事处		
				ps.setLong(nIndex++, info.getOfficeID()); // CPF-默认办事处	
				ps.setLong(nIndex++, OBConstant.getDefaultTransactionType(info.getTransType())); // CPF-处理业务类型
				/////// 第4行  12字段
				ps.setTimestamp(nIndex++, info.getClearInterest()); //结息日期
				ps.setLong(nIndex++, info.getInterestPayeeAcctID()); //利息收款方账户ID
				ps.setLong(nIndex++, info.getInterestRemitType()); //利息汇款方式
				ps.setDouble(nIndex++, info.getInterest()); //应付贷款利息
				ps.setDouble(nIndex++, info.getCompoundInterest()); //应付复利
				ps.setDouble(nIndex++, info.getOverdueInterest()); // 应付逾期罚息
				ps.setDouble(nIndex++, info.getSuretyFee()); // 应付担保费
				ps.setDouble(nIndex++, info.getCommission()); // 应付手续费
				ps.setDouble(nIndex++, info.getRealInterest()); // 实付贷款利息
				ps.setDouble(nIndex++, info.getRealCompoundInterest()); // 实付复利
				ps.setDouble(nIndex++, info.getRealOverdueInterest()); // 实付逾期罚息
				ps.setDouble(nIndex++, info.getRealSuretyFee()); // 实付担保费
				ps.setDouble(nIndex++, info.getRealCommission()); // 实付手续费
				/////// 第5行  12字段
				ps.setTimestamp(nIndex++, info.getInterestStart()); //利息起息日
				ps.setTimestamp(nIndex++, info.getCompoundStart()); //复利起息日
				ps.setDouble(nIndex++, info.getCompoundRate()); //复利利率
				ps.setTimestamp(nIndex++, info.getOverDueStart()); // 罚息起息日
				ps.setDouble(nIndex++, info.getOverDueRate()); //罚息利率
				ps.setTimestamp(nIndex++, info.getSuretyStart()); // 担保费起息日
				ps.setDouble(nIndex++, info.getSuretyRate()); //担保费率
				ps.setTimestamp(nIndex++, info.getCommissionStart()); // 手续费起息日
				ps.setDouble(nIndex++, info.getCommissionRate()); //手续费率
				ps.setDouble(nIndex++, info.getInterestRate()); //贷款利率
				ps.setDouble(nIndex++, info.getCompoundAmount()); //复利本金
				ps.setDouble(nIndex++, info.getOverDueAmount()); //逾期罚息本金
				/////// 第5行  6字段
				ps.setDouble(nIndex++, info.getInterestReceiveable()); //计提利息
				ps.setDouble(nIndex++, info.getInterestIncome()); //本次利息
				ps.setDouble(nIndex++, info.getRealInterestReceiveable()); //实付计提利息
				ps.setDouble(nIndex++, info.getRealInterestIncome()); //实付本次利息
				ps.setDouble(nIndex++, info.getInterestTax()); //利息税费
				ps.setDouble(nIndex++, info.getRealInterestTax()); //实付利息税费
				
				//modify by lxr for budget 增加 budgetitemID 字段
				ps.setLong(nIndex++, info.getBudgetItemID());  //预算项目id
				//modify by juncai增加 ISFIXCONTINUE 字段
				ps.setLong(nIndex++, info.getIsFixContinue()); //到期是否续存
				//modify by juncai增加 FIXEDREMARK 字段
				ps.setString(nIndex++, info.getFixEdremark()); //备注（定期是否续存）
				//modify by juncai增加 MAMOUNTFORTRANS 字段
				ps.setDouble(nIndex++, info.getMamOuntForTrans()); //定期子帐户留存金额
				ps.setString(nIndex++, info.getSBatchNo());	//批次号
                ps.setString(nIndex++, info.getApplyCode());//业务申请编号
                //added by bingliu 2012-06-26,如果传进来的数据来源为空，则默认为网银
                if(info.getSource()<=0)
                {
                	info.setSource(SETTConstant.ExtSystemSource.EBANK);
                }
                ps.setLong(nIndex++, info.getSource()); //数据来源
                ps.setLong(nIndex++, info.getIsSameBank()); //是否同行
                ps.setLong(nIndex++, info.getIsDiffLocal()); //是否同城
                ps.setLong(nIndex++, info.getIsAutoContinue());//是否自动续存
                ps.setLong(nIndex++, info.getAutocontinuetype());//自动转续存类型（本金or本息）
                ps.setLong(nIndex++, info.getAutocontinueaccountid());//利息转至活期账户号
                
                ps.setLong(nIndex++, info.getRemitArea());	//汇款区域
                ps.setLong(nIndex++, info.getRemitSpeed());	//汇款速度
                
                //签名信息
                ps.setString(nIndex++, info.getSignatureOriginalValue());
                ps.setString(nIndex++, info.getSignatureValue());
                ps.setLong(nIndex++, info.getTimestamp());
                
				log4j.info("nIndex=" + nIndex);
				ps.executeUpdate();
				ps.close();
				ps = null;
				//对数据进行解锁，此操作必不可少
			}
			con.close();
			con = null;
		}
		catch (IException e)
		{
			log4j.error(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lMaxID;
	}
	/**
	 * 检查交易指令状态是否与lStatus相等,
	 * Create Date: 2003-8-13
	 * @param lInstructionID 交易指令ID
	 * @param lStatus 交易状态
	 * @return boolean true-相等 false-不相等
	 * @exception Exception
	 */
	public boolean isStatus(long lInstructionID, long lStatus) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		long lQueryStatus = -1;
		try
		{
			con = Database.getConnection();
			sql = " SELECT nStatus FROM OB_FinanceInstr WHERE ID = " + lInstructionID;
			log4j.info("isStatus SQL = " + sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lQueryStatus = rs.getLong("nStatus");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
		if (lQueryStatus == lStatus)
		{
			log4j.info("return status = true");
			return true;
		}
		log4j.info("return status = false");
		return false;
	}
	/**
	 * 判断指令是否被删除
	 * Create Date: 2003-8-13
	 * @param long lInstructionID  交易指令ID
	 * @return boolean 指令是否被删除
	 * @exception Exception
	 */
	public boolean isDelete(long lInstructionID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		long lCount = -1;
		boolean bDelete = false;
		try
		{
			//判断是否物理删除
			con = Database.getConnection();
			sql = " SELECT COUNT(*) AS count";
			sql += " FROM OB_FinanceInstr";
			sql += " WHERE ID = " + lInstructionID;
			log4j.info("isDelete SQL = " + sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lCount = rs.getLong("count");
				if (lCount <= 0)
				{
					bDelete = true;
				}
			}
			//判断是否逻辑删除
			if (!bDelete)
			{
				bDelete = isStatus(lInstructionID, OBConstant.SettInstrStatus.DELETE);
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
		log4j.info("Delete Status = " + bDelete);
		return bDelete;
	}

	public long updateStatus(long lInstructionID, long lStatus, long lUserID, String strAction) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		long lSignUserID = -1;
		long lUpdateStatus = -1;
		lUpdateStatus = lStatus;
		OpenDateInfo info = null;
		FinanceInfo financeInfo = null;
		financeInfo = this.findByID(lInstructionID);
		log4j.print("===================STransType====="+financeInfo.getSTransType());
		log4j.print("===================RemitType====="+financeInfo.getRemitType());
		log4j.print("strAction#######################################---------"+strAction);
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE OB_FinanceInstr SET nstatus = ? ");
			if (strAction.equals("check"))
			{
				//复核需要同时设置签认人信息
				//得到签认ID  
				lSignUserID = getSignUserID(lInstructionID);
				log4j.print("lSignUserID######################"+lSignUserID);
				if (lSignUserID > 0)
				{
					//签认人ID>0，签认人存在，设置复核人、复核时间
					sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 2 ");
					//		检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                    sb.append(" , dtModify=sysdate");
                    
                    //Update Wjyang 2008-4-2 为华联修改，付款交易的执行日，在录入时不能确定，需要在最终提交财务公司时（签认或复核时）取当前自然
                  /*
                    if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY){
                    	sb.append(" , dtExecute=sysdate");	
                    }
                   */ 
            	}
				else
				{
					//签认人ID <0,签认不存在，复核后状态为签认，设置复核人、复核时间
					sb.append(" , NCHECKUSERID=?, dtCheck=sysdate, nIsCanAccept = 1 ");
					//		检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                    sb.append(" , dtModify=sysdate");
                    
//                  Update Wjyang 2008-4-2 为华联修改，付款交易的执行日，在录入时不能确定，需要在最终提交财务公司时（签认或复核时）取当前自然
                   /*
                    if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY){
                    	sb.append(" , dtExecute=sysdate");
                    }
                    */
				}
			}
			if (strAction.equals("cancelcheck"))
			{
				//modified by mzh_fu 2007/05/31 在下面一行增加下列代码,针对签名用: [ ,actionStatus = "+OBConstant.SettActionStatus.CANCELCHECKED ]
				sb.append(" , NCHECKUSERID=?, dtCheck=sysdate,nIsCanAccept = 2 ,actionStatus = "+OBConstant.SettActionStatus.CANCELCHECKED);

				//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
			}
			if(strAction.indexOf("billcheck") > -1)
			{
				sb.append(" ,ndepositbillstatusid=" + OBConstant.SettInstrStatus.CHECK);
				sb.append(" ,ndepositbillcheckuserid=?");
				sb.append(" ,dtdepositbillcheckdate=sysdate");
				sb.append(" ,sdepositbillcheckabstract='" + strAction.substring(9,strAction.length()) +"'");
                //检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
				lSignUserID = getSignUserID(lInstructionID);
				if (lSignUserID > 0)
				{
					sb.append(" ,nIsCanAccept = 2 ");
				}
				else
				{
					sb.append(" ,nIsCanAccept = 1 ");
				}
			}
			if(strAction.indexOf("billcancelCheck") > -1)
			{
				sb.append(" ,ndepositbillstatusid=" + OBConstant.SettInstrStatus.SAVE);
				sb.append(" ,ndepositbillcheckuserid=?");
				sb.append(" ,dtdepositbillcheckdate=sysdate");
				//sb.append(" ,sdepositbillcheckabstract='" + strAction.substring(15,strAction.length()) +"'");
//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
				sb.append(" ,nIsCanAccept = 1 ");
			}
			if (strAction.equals("sign") && isBill(lInstructionID) <= 0)
			{
				sb.append(" , NSIGNUSERID=?, dtSign=sysdate,nIsCanAccept = 1 ");
				 //检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
                
                //Update Wjyang 2008-4-2 为华联修改，付款交易的执行日，在录入时不能确定，需要在最终提交财务公司时（签认或复核时）取当前自然
              /*
                if(financeInfo.getRemitType()==OBConstant.SettRemitType.BANKPAY){
                	sb.append(" , dtExecute=sysdate");      
                }
                */
			}
			if(strAction.equals("sign") && isBill(lInstructionID) > 0)
			{
				sb.append(" ,ndepositbillstatusid=" + OBConstant.SettInstrStatus.SIGN);
				sb.append(" ,ndepositbillsignuserid=?");
				sb.append(" ,dtdepositbillsigndate=sysdate");
				sb.append(" ,nIsCanAccept = 1 ");
//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
			}
			if (strAction.equals("cancelSign") && isBill(lInstructionID) <= 0)
			{
				sb.append(" , NSIGNUSERID=?, dtSign=sysdate,nIsCanAccept = 2 ");
//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
			}
			if(strAction.equals("cancelSign") && isBill(lInstructionID) > 0)
			{
				sb.append(" ,ndepositbillstatusid=" + OBConstant.SettInstrStatus.CHECK);
				sb.append(" ,ndepositbillsignuserid=?");
				sb.append(" ,dtdepositbillsigndate=sysdate");
				sb.append(" ,nIsCanAccept = 2 ");
//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
			}
			if (strAction.equals("delete"))
			{
				sb.append(" , NDELETEUSERID=?, dtDelete=sysdate ,nIsCanAccept=2");
//				检查时间戳--增加并发控制--增加dtmodify字段--modify by hfwang(2007-1-10)
                sb.append(" , dtModify=sysdate");
			}
			sb.append(" where id = ?");
			//为了协调网银和一期在“执行日”上的一致性增加下面一段代码
			//用途：获取开关机状态和开机时间
			
			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setLong(nIndex++, lUpdateStatus);
			ps.setLong(nIndex++, lUserID);
			ps.setLong(nIndex++, lInstructionID);
			
			
			lResult = ps.executeUpdate();
			if (lResult == 0 && strAction.equals("check"))
			{
				ps.close();
				ps = null;
				con.close();
				con = null;
				lResult=-6;
				throw new IException("OB_EC17");
			}
			else if (lResult == 0 && strAction.equals("sign"))
			{
				ps.close();
				ps = null;
				con.close();
				con = null;
				lResult=-7;
				//throw new IException("OB_EC21");
			}
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * 修改财务交易值指令状态为已确认
	 * Create Date: 2003-8-13
	 * @param long lInstructionID  交易指令ID
	 * @param long lConfirmUserID  确认人
	 * @return long , 大于0表示成功，等于0表示失败
	 * @exception Exception
	 */
	public long confirm(long lInstructionID, long lConfirmUserID) throws Exception
	{
		//检查此交易指令在确认前是否已经被删除
		boolean bStatus = false;
		try
		{
			bStatus = isDelete(lInstructionID);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		//如果被删除
		if (bStatus)
		{
			throw new IException("OB_EC07");
		}
		try
		{
			//add by sxyao@isoftstone.com 2003-01-24
			//判断用户是否有此项操作的权力
			Connection con = null;
			PreparedStatement ps = null;
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			long lStatusTest = -1;
			long lConfirmUserIDTest = -1;
			sb.append("SELECT nStatus, nConfirmUserID FROM ob_financeinstr WHERE id=?");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lInstructionID);
			ResultSet rs = ps.executeQuery();
			if (rs == null)
			{
				throw new IException("Gen_E003");
			}
			if (rs.next())
			{
				lStatusTest = rs.getLong("nStatus");
				lConfirmUserIDTest = rs.getLong("nConfirmUserID");
			}
			else
			{
				throw new IException("Gen_E003");
			}
			//只有状态为未确认或已确认时才能确认
			if ((lStatusTest != OBConstant.SettInstrStatus.SAVE) && (lStatusTest != OBConstant.SettInstrStatus.CHECK)) //未确认或已确认
			{
				log4j.info("操作异常, 原因可能是有其他用户改变了交易指令状态");
				return -1;
			}
			//登录人是否为确认人
			if (lConfirmUserID != lConfirmUserIDTest)
			{
				log4j.info("没有此项操作的权力");
				throw new IException("Gen_E003");
			}
			return updateStatus(lInstructionID, OBConstant.SettInstrStatus.SAVE, lConfirmUserID, "confirm");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	}
	/**
	 * 修改一条财务交易指令
	 * Create Date: 2003-8-13
	 * @param FinanceInfo  财务交易信息
	 * @return long 财务交易指令流水号
	 * @exception Exception
	 */
	public long update(FinanceInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lResult = -1;
		int nIndex = 1;
		//检查此交易指令在修改前是否已经被复核,只有没有被复核的指令才能被修改
		boolean bStatus = false;
		try
		{
			bStatus = isDelete(info.getID());
			//如果被删除
			if (bStatus)
			{
				throw new IException("OB_EC08");
			}
			bStatus = isStatus(info.getID(), OBConstant.SettInstrStatus.SAVE);
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		//指令已经复合,返回错误代码
		if (!bStatus)
		{
			throw new IException("OB_EC02");
		}
		try
		{
			if(info.getRemitType()==OBConstant.SettRemitType.BANKPAY||info.getRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
			{	
				if(info.getSource()==SETTConstant.ExtSystemSource.EBANK)
				{
					info.setApplyCode(String.valueOf(info.getID()));
				}
			}
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_FinanceInstr SET  ");
			sbSQL.append(" dtmodify=sysdate , ");
			if (info.getChildClientID() > 0)
			{
				sbSQL.append(" nChildClientid=?, ");
			}
			if (info.getPayerAcctID() > 0)
			{
				sbSQL.append(" nPayerAcctID=?,CPF_nOfficeID=?, ");
			}
			if (info.getRemitType() > 0)
			{
				sbSQL.append(" nRemitType=?, ");
			}
			if (info.getPayeeAcctID() > 0)
			{
				sbSQL.append(" nPayeeAcctID=?,");
			}
			if (info.getAmount() >= 0)
			{
				sbSQL.append(" mAmount=?, ");
			}
			if (info.getExecuteDate() != null)
			{
				sbSQL.append(" dtExecute=?, ");
			}
			if (info.getNote() != null && info.getNote().length() >= 0)
			{
				sbSQL.append(" sNote=?, ");
			}
			if (info.getFixedDepositTime() > 0)
			{
				sbSQL.append(" nFixedDepositTime=?, ");
			}
			if (info.getNoticeDay() > 0)
			{
				sbSQL.append(" nNoticeDay=?, ");
			}
			if (info.getContractID() > 0)
			{
				sbSQL.append(" nContractID=?, ");
			}
			if (info.getLoanNoteID() > 0)
			{
				sbSQL.append(" nLoanNoteID=?, ");
			}
			if (info.getPayDate() != null)
			{
				sbSQL.append(" dtPay=? , ");
			}
			if (info.getDepositNo() != null && info.getDepositNo().length() > 0)
			{
				sbSQL.append(" SDEPOSITNO=? , ");
			}
			if (info.getDepositStart() != null)
			{
				sbSQL.append(" DTDEPOSITSTART=? , ");
			}
			if (info.getDepositRate() > 0)
			{
				sbSQL.append(" MDEPOSITRATE=? , ");
			}
			if (info.getDepositAmount() > 0)
			{
				sbSQL.append(" MDEPOSITAMOUNT=? , ");
			}
			if (info.getDepositBalance() > 0)
			{
				sbSQL.append(" MDEPOSITBALANCE=? , ");
			}
			if (info.getSubAccountID() > 0)
			{
				sbSQL.append(" NSUBACCOUNTID=? , ");
			}
			if (info.getClearInterest() != null)
			{
				sbSQL.append(" DTCLEARINTEREST=? , ");
			}
			if (info.getInterestPayeeAcctID() > 0)
			{
				sbSQL.append(" NINTERESTPAYEEACCTID=? , ");
			}
			if (info.getInterestRemitType() > 0)
			{
				sbSQL.append(" NINTERESTREMITTYPE=? , ");
			}
			//modify by lxr for budget add budgetitemid
			if (info.getBudgetItemID() > 0)
			{
				sbSQL.append(" BudgetItemID=? , ");
			}
			//modify by juncai add ISFIXCONTINUE
			if (info.getIsFixContinue() > 0)
			{
				sbSQL.append(" ISFIXCONTINUE=? , ");
			}
			//modify by juncai add FIXEDREMARK
			if (info.getFixEdremark() != null)
			{
				sbSQL.append(" FIXEDREMARK=? , ");
			}
			//modify by juncai add MAMOUNTFORTRANS
			if (info.getIsFixContinue() >= 0)
			{
				sbSQL.append(" MAMOUNTFORTRANS=? , ");
			}
			if (info.getSDepositInterestDeal() >= 0)
			{
				sbSQL.append(" FIXEDINTERESTDEAL=? , ");
			}
			//modify by xwhe 2008-04-08
			if (info.getTransType() ==  OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
			{
				if( info.getSDepositBillStartDate()!= null)
				{
					sbSQL.append(" FIXEDNEXTSTARTDATE=? , ");
				}
				if( info.getSDepositBillEndDate()!= null)
				{
					sbSQL.append(" FIXEDNEXTENDDATE=? , ");
				}
				if( info.getSDepositBillPeriod() > 0 )
				{
					sbSQL.append(" FIXEDNEXTPERIOD=? , ");
				}
				if( info.getSDepositInterestToAccountID() > 0)
				{
					sbSQL.append(" FixedinteresttoaccountID=? , ");
				}
			}
			if (info.getTransType() == OBConstant.SettInstrType.TRUSTLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.CONSIGNLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.INTERESTFEEPAYMENT)
			{
				if (info.getInterest() >= 0)
				{
					sbSQL.append(" MINTEREST=? , ");
				}
				if (info.getCompoundInterest() >= 0)
				{
					sbSQL.append(" MCOMPOUNDINTEREST=? , ");
				}
				if (info.getOverdueInterest() >= 0)
				{
					sbSQL.append(" MOVERDUEINTEREST=? , ");
				}
				if (info.getSuretyFee() >= 0)
				{
					sbSQL.append(" MSURETYFEE=? , ");
				}
				if (info.getCommission() >= 0)
				{
					sbSQL.append(" MCOMMISSION=? , ");
				}
				if (info.getRealInterest() >= 0)
				{
					sbSQL.append(" MREALINTEREST=? , ");
				}
				if (info.getRealCompoundInterest() >= 0)
				{
					sbSQL.append(" MREALCOMPOUNDINTEREST=? , ");
				}
				if (info.getRealOverdueInterest() >= 0)
				{
					sbSQL.append(" MREALOVERDUEINTEREST=? , ");
				}
				if (info.getRealSuretyFee() >= 0)
				{
					sbSQL.append(" MREALSURETYFEE=? , ");
				}
				if (info.getRealCommission() >= 0)
				{
					sbSQL.append(" MREALCOMMISSION=? , ");
				}
				if (info.getInterestStart() != null)
				{
					sbSQL.append(" DTINTERESTSTART=? , ");
				}
				if (info.getCompoundStart() != null)
				{
					sbSQL.append(" DTCOMPOUNDINTERESTSTART=? , ");
				}
				if (info.getCompoundRate() >= 0)
				{
					sbSQL.append(" MCOMPOUNDRATE=? , ");
				}
				if (info.getOverDueStart() != null)
				{
					sbSQL.append(" DTOVERDUESTART=? , ");
				}
				if (info.getOverDueRate() >= 0)
				{
					sbSQL.append(" MOVERDUERATE=? , ");
				}
				if (info.getSuretyStart() != null)
				{
					sbSQL.append(" DTSURETYFEESTART=? , ");
				}
				if (info.getSuretyRate() >= 0)
				{
					sbSQL.append(" MSURETYFEERATE=? , ");
				}
				if (info.getCommissionStart() != null)
				{
					sbSQL.append(" DTCOMMISSIONSTART=? , ");
				}
				if (info.getCommissionRate() >= 0)
				{
					sbSQL.append(" MCOMMISSIONRATE=? , ");
				}
				if (info.getInterestRate() >= 0)
				{
					sbSQL.append(" MLOANREPAYMENTRATE=? , ");
				}
				if (info.getCompoundAmount() >= 0)
				{
					sbSQL.append(" MCOMPOUNDAMOUNT=? , ");
				}
				if (info.getOverDueAmount() >= 0)
				{
					sbSQL.append(" MOVERDUEAMOUNT=? , ");
				}
				//计提利息
				if (info.getInterestReceiveable() >= 0)
				{
					sbSQL.append(" MINTERESTRECEIVEABLE=? , ");
				}
				//本次利息
				if (info.getInterestIncome() >= 0)
				{
					sbSQL.append(" MINTERESTINCOME=? , ");
				}
				//实付计提利息
				if (info.getRealInterestReceiveable() >= 0)
				{
					sbSQL.append(" MREALINTERESTRECEIVEABLE=? , ");
				}
				//实付本次利息
				if (info.getRealInterestIncome() >= 0)
				{
					sbSQL.append(" MREALINTERESTINCOME=? , ");
				}
				//利息税费
				if (info.getInterestTax() >= 0)
				{
					sbSQL.append(" MINTERESTTAX=? , ");
				}
				//实付利息税费
				if (info.getRealInterestTax() >= 0)
				{
					sbSQL.append(" MREALINTERESTTAX=? , ");
				}
			}
			if(info.getIsAutoContinue() >= 0)
			{
				sbSQL.append(" isautocontinue=? , ");
				sbSQL.append(" autocontinuetype=? ,");
				sbSQL.append(" autocontinueaccountid=? ,");
			}
			
			//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
			if(info.getRemitArea() > 0)
			{
				sbSQL.append(" remitArea = ? , ");
			}
			if(info.getRemitSpeed() > 0)
			{
				sbSQL.append(" remitSpeed = ? , ");
			}
			
			//财企接口新增
			sbSQL.append(" sApplyCode = ? , ");
			sbSQL.append(" lSource = ? , ");
			
			sbSQL.append("  nconfirmuserid=nconfirmuserid,  ");
			sbSQL.append(" signatureoriginalvalue = ?, ");
			sbSQL.append(" signaturevalue = ?, ");
			sbSQL.append(" timestamp = ? ");
			sbSQL.append(" WHERE id = ?  AND  nCurrencyID = ? AND nconfirmuserid = ? ");
			log4j.info("执行update SQL = " + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			////
			nIndex = 1;
			if (info.getChildClientID() > 0)
			{
				ps.setLong(nIndex++, info.getChildClientID());
			}
			if (info.getPayerAcctID() > 0)
			{
				ps.setLong(nIndex++, info.getPayerAcctID());
				// 付款方银行账户ID，会影响默认办事处
				//ps.setLong(nIndex++, getDefaultOfficeID(info.getClientID()));
				ps.setLong(nIndex++, info.getOfficeID());
			}
			if (info.getRemitType() > 0)
			{
				ps.setLong(nIndex++, info.getRemitType());
			}
			if (info.getPayeeAcctID() > 0)
			{
				ps.setLong(nIndex++, info.getPayeeAcctID());
			}
			if (info.getAmount() >= 0)
			{
				ps.setDouble(nIndex++, info.getAmount());
			}
			if (info.getExecuteDate() != null)
			{
				ps.setTimestamp(nIndex++, info.getExecuteDate());
			}
			if (info.getNote() != null && info.getNote().length() >= 0)
			{
				ps.setString(nIndex++, info.getNote());
			}
			if (info.getFixedDepositTime() > 0)
			{
				ps.setLong(nIndex++, info.getFixedDepositTime());
			}
			if (info.getNoticeDay() > 0)
			{
				ps.setLong(nIndex++, info.getNoticeDay());
			}
			if (info.getContractID() > 0)
			{
				ps.setLong(nIndex++, info.getContractID());
			}
			if (info.getLoanNoteID() > 0)
			{
				ps.setLong(nIndex++, info.getLoanNoteID());
			}
			if (info.getPayDate() != null)
			{
				ps.setTimestamp(nIndex++, info.getPayDate());
			}
			if (info.getDepositNo() != null && info.getDepositNo().length() > 0)
			{
				ps.setString(nIndex++, info.getDepositNo());
			}
			if (info.getDepositStart() != null)
			{
				ps.setTimestamp(nIndex++, info.getDepositStart());
			}
			if (info.getDepositRate() > 0)
			{
				ps.setDouble(nIndex++, info.getDepositRate());
			}
			if (info.getDepositAmount() > 0)
			{
				ps.setDouble(nIndex++, info.getDepositAmount());
			}
			if (info.getDepositBalance() > 0)
			{
				ps.setDouble(nIndex++, info.getDepositBalance());
			}
			if (info.getSubAccountID() > 0)
			{
				ps.setLong(nIndex++, info.getSubAccountID());
			}
			if (info.getClearInterest() != null)
			{
				ps.setTimestamp(nIndex++, info.getClearInterest());
			}
			if (info.getInterestPayeeAcctID() > 0)
			{
				ps.setLong(nIndex++, info.getInterestPayeeAcctID());
			}
			if (info.getInterestRemitType() > 0)
			{
				ps.setLong(nIndex++, info.getInterestRemitType());
			}
			
			//modify by lxr for budget add budgetitemid
			if (info.getBudgetItemID() > 0)
			{
				ps.setLong(nIndex++, info.getBudgetItemID());
			}
			//modify by juncai add ISFIXCONTINUE
			 if (info.getIsFixContinue() > 0){
				 ps.setLong(nIndex++, info.getIsFixContinue());
			 }
			//modify by juncai add FIXEDREMARK
			if (info.getFixEdremark() != null)
			{
				ps.setString(nIndex++, info.getFixEdremark());
			}
			//modify by juncai add MAMOUNTFORTRANS
			if (info.getIsFixContinue() >= 0)
			{
				ps.setDouble(nIndex++, info.getMamOuntForTrans());
			}
			if (info.getSDepositInterestDeal() >= 0)
			{
				ps.setDouble(nIndex++, info.getSDepositInterestDeal());
			}
			if (info.getTransType() ==  OBConstant.SettInstrType.DRIVEFIXDEPOSIT)
			{
				if( info.getSDepositBillStartDate()!= null)
				{
					ps.setTimestamp(nIndex++, info.getSDepositBillStartDate());
				}
				if( info.getSDepositBillEndDate()!= null)
				{
					ps.setTimestamp(nIndex++, info.getSDepositBillEndDate());
				}
				if( info.getSDepositBillPeriod() > 0 )
				{
					ps.setLong(nIndex++, info.getSDepositBillPeriod());
				}
				if(info.getSDepositInterestToAccountID() > 0)
				{
					ps.setLong(nIndex++, info.getSDepositInterestToAccountID());
				}
			}			
			if (info.getTransType() == OBConstant.SettInstrType.TRUSTLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.CONSIGNLOANRECEIVE
				|| info.getTransType() == OBConstant.SettInstrType.INTERESTFEEPAYMENT)
			{
				if (info.getInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getInterest());
				}
				if (info.getCompoundInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getCompoundInterest());
				}
				if (info.getOverdueInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getOverdueInterest());
				}
				if (info.getSuretyFee() >= 0)
				{
					ps.setDouble(nIndex++, info.getSuretyFee());
				}
				if (info.getCommission() >= 0)
				{
					ps.setDouble(nIndex++, info.getCommission());
				}
				if (info.getRealInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealInterest());
				}
				if (info.getRealCompoundInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealCompoundInterest());
				}
				if (info.getRealOverdueInterest() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealOverdueInterest());
				}
				if (info.getRealSuretyFee() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealSuretyFee());
				}
				if (info.getRealCommission() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealCommission());
				}
				if (info.getInterestStart() != null)
				{
					ps.setTimestamp(nIndex++, info.getInterestStart());
				}
				if (info.getCompoundStart() != null)
				{
					ps.setTimestamp(nIndex++, info.getCompoundStart());
				}
				if (info.getCompoundRate() >= 0)
				{
					ps.setDouble(nIndex++, info.getCompoundRate());
				}
				if (info.getOverDueStart() != null)
				{
					ps.setTimestamp(nIndex++, info.getOverDueStart());
				}
				if (info.getOverDueRate() >= 0)
				{
					ps.setDouble(nIndex++, info.getOverDueRate());
				}
				if (info.getSuretyStart() != null)
				{
					ps.setTimestamp(nIndex++, info.getSuretyStart());
				}
				if (info.getSuretyRate() >= 0)
				{
					ps.setDouble(nIndex++, info.getSuretyRate());
				}
				if (info.getCommissionStart() != null)
				{
					ps.setTimestamp(nIndex++, info.getCommissionStart());
				}
				if (info.getCommissionRate() >= 0)
				{
					ps.setDouble(nIndex++, info.getCommissionRate());
				}
				if (info.getInterestRate() >= 0)
				{
					ps.setDouble(nIndex++, info.getInterestRate());
				}
				if (info.getCompoundAmount() >= 0)
				{
					ps.setDouble(nIndex++, info.getCompoundAmount());
				}
				if (info.getOverDueAmount() >= 0)
				{
					ps.setDouble(nIndex++, info.getOverDueAmount());
				}
				//计提利息
				if (info.getInterestReceiveable() >= 0)
				{
					ps.setDouble(nIndex++, info.getInterestReceiveable());
				}
				//本次利息
				if (info.getInterestIncome() >= 0)
				{
					ps.setDouble(nIndex++, info.getInterestIncome());
				}
				//实付计提利息
				if (info.getRealInterestReceiveable() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealInterestReceiveable());
				}
				//实付本次利息
				if (info.getRealInterestIncome() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealInterestIncome());
				}
				//利息税费
				if (info.getInterestTax() >= 0)
				{
					ps.setDouble(nIndex++, info.getInterestTax());
				}
				//实付利息税费
				if (info.getRealInterestTax() >= 0)
				{
					ps.setDouble(nIndex++, info.getRealInterestTax());
				}
			}
			if(info.getIsAutoContinue() >= 0)
			{
				ps.setLong(nIndex++, info.getIsAutoContinue());
				ps.setLong(nIndex++, info.getAutocontinuetype());
				ps.setLong(nIndex++, info.getAutocontinueaccountid());
			}
			
			//Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
			if(info.getRemitArea() > 0)
			{
				ps.setLong(nIndex++, info.getRemitArea());
			}
			if(info.getRemitSpeed() > 0)
			{
				ps.setLong(nIndex++, info.getRemitSpeed());
			}
			
			//财企接口新增
			ps.setString(nIndex++, info.getApplyCode());
			//added by bingliu 2012-06-26,如果传进来的数据来源为空，则默认为网银
            if(info.getSource()<=0)
            {
            	info.setSource(SETTConstant.ExtSystemSource.EBANK);
            }
			ps.setLong(nIndex++, info.getSource());
			
			//签名信息
			ps.setString(nIndex++,info.getSignatureOriginalValue());
			ps.setString(nIndex++,info.getSignatureValue());
			ps.setLong(nIndex++,info.getTimestamp());
			
			
			ps.setLong(nIndex++, info.getID());
			ps.setLong(nIndex++, info.getCurrencyID());
			ps.setLong(nIndex++, info.getConfirmUserID());
			log4j.print("\n====参数个数nIndex:" + nIndex);
			lResult = ps.executeUpdate();
			if (lResult < 1)
			{
				throw new IException("OB_EC19");
			}
			else
			{
				lResult = info.getID();
			}
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		log4j.info("update结果=" + lResult);
		return lResult;
	}
	/**
	 * 修改财务交易值指令状态为已删除
	 * Create Date: 2003-8-13
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lDeleteUserID      -- 删除人
	 * @return long , 大于0表示成功，等于0表示失败
	 * @exception Exception
	 */
	public long delete(long lInstructionID, long lDeleteUserID) throws Exception
	{
		boolean bStatus = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			bStatus = isDelete(lInstructionID);
			//如果被删除
			if (bStatus)
			{
				throw new IException("OB_EC08");
			}
			bStatus = isStatus(lInstructionID, OBConstant.SettInstrStatus.SAVE);
			//指令已经复合,返回错误代码
			if (!bStatus)
			{
				throw new IException("OB_EC03");
			}
			//判断用户是否有此项操作的权力
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			long lConfirmUserIDTest = -1;
			sb.append("SELECT  nConfirmUserID FROM ob_financeinstr WHERE id=?");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lInstructionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lConfirmUserIDTest = rs.getLong("nConfirmUserID");
			}
			//关闭数据库资源
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//登录人是否为确认人
			if (lDeleteUserID != lConfirmUserIDTest)
			{
				log4j.info("没有此项操作的权力");
				throw new IException("Gen_E003");
			}
			return updateStatus(lInstructionID, OBConstant.SettInstrStatus.DELETE, lDeleteUserID, "delete");
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		} 
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
	}
	
	/**
	 * 增加一条财务交易指令
	 * Create Date: 2003-8-13
	 * @param FinanceInfo 账号
	 * @return long 新增加的财务交易指令流水号
	 * @exception Exception
	 */
	public long addTrans(FinanceInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		long lMaxID = -1;
		boolean bLocked = true;
		//此交易中的付款方账号，（上存资金调回中收款方账号）
		//进行交易的登录单位的账号
		long lAccountID = -1;
		try
		{
			con = Database.getConnection();
			
				StringBuffer sb = new StringBuffer();
				// get max(id)+1 as PK
				//log4j.info("get max(id)+1 as PK");
				//调用BizCapital方法，得到最大流水号
				lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
				sb.setLength(0);
				// insert into  拼写插入语句 
				//log4j.info("财务指令插入语句= \n");
				sb.append(" INSERT INTO OB_FinanceInStr(  \n");
				sb.append("ID, nClientID, nCurrencyID,nTransType,");
				sb.append("nPayerAcctID,nRemitType,nPayeeAcctID, ");
				sb.append(" mAmount,nChildClientid, \n");	
				sb.append("dtExecute, sNote, nFixedDepositTime, nNoticeDay, ");
				sb.append("NCONTRACTID,  NLOANNOTEID, dtPay,");
				sb.append("sDepositNo,NSUBACCOUNTID, dtDepositStart, mDepositRate, mDepositAmount, mDepositBalance, \n");
				sb.append("nStatus, nConfirmUserID, dtConfirm, ");
				sb.append("CPF_nOfficeID, CPF_nDefaultTransType, \n");
				sb.append("DTCLEARINTEREST, nInterestPayeeAcctID, nInterestRemitType, ");
				sb.append("mInterest, mCompoundInterest, mOverdueInterest, mSuretyFee, mCommission, ");
				sb.append("mRealInterest, mRealCompoundInterest, mRealOverdueInterest, mRealSuretyFee, mRealCommission, \n");
				//sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX) \n");
				//modify by lxr for budget 增加 budgetitemID 字段
				sb.append("DTINTERESTSTART, DTCOMPOUNDINTERESTSTART, MCOMPOUNDRATE, DTOVERDUESTART, MOVERDUERATE, DTSURETYFEESTART, MSURETYFEERATE, DTCOMMISSIONSTART, MCOMMISSIONRATE, MLOANREPAYMENTRATE, MCOMPOUNDAMOUNT, MOVERDUEAMOUNT,MINTERESTRECEIVEABLE, MINTERESTINCOME, MREALINTERESTRECEIVEABLE, MREALINTERESTINCOME,MINTERESTTAX,MREALINTERESTTAX,budgetItemID,");
				sb.append("FIXEDNEXTSTARTDATE,FIXEDNEXTENDDATE,FIXEDNEXTPERIOD,FIXEDINTERESTDEAL,FIXEDINTERESTTOACCOUNTID,SDEPOSITBILLNO,");
				
				//add by mingfang 2007-05-29  数字签名字段
				sb.append("SIGNATUREVALUE,SIGNATUREORIGINALVALUE,SAPPLYCODE,LSOURCE");
				
				sb.append(",dtmodify,isautocontinue,autocontinuetype,autocontinueaccountid,timestamp ) \n");
				sb.append("VALUES \n");
				sb.append("(?,?,?,?,?,?,?,?,?," );
				if (info.getTransType()== OBConstant.SettInstrType.APPLYCAPITAL)
				{
					sb.append("sysdate,");
				}else
				{
					sb.append("?," );
				}					
				//sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//modify by lxr for budget 增加 budgetitemID 字段
                sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
				
                //add by mingfang 2007-05-29  数字签名字段
                sb.append(",?,?,?,?,sysdate,?,?,?,?)");
                
                //sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//log4j.info("add插入语句=" + sb.toString());
				ps = con.prepareStatement(sb.toString());
				int nIndex = 1;
				//////// 第1行 8个字段
				//log4j.info("lMaxID=" + lMaxID);
				ps.setLong(nIndex++, lMaxID);
				ps.setLong(nIndex++, info.getClientID()); // 网上银行客户ID
				ps.setLong(nIndex++, info.getCurrencyID()); // 币种
				ps.setLong(nIndex++, info.getTransType()); // 网上银行交易类型
				ps.setLong(nIndex++, info.getPayerAcctID()); // 付款方账户ID
				ps.setLong(nIndex++, info.getRemitType()); // 汇款方式
				ps.setLong(nIndex++, info.getPayeeAcctID()); // 收款方账户ID
				ps.setDouble(nIndex++, info.getAmount()); // 交易金额
				ps.setLong(nIndex++, info.getChildClientID()); // 下属单位
				///////// 第2行  12字段
				if (info.getTransType()!= OBConstant.SettInstrType.APPLYCAPITAL)
				{
					ps.setTimestamp(nIndex++, info.getExecuteDate()); // 执行日
				}
				
				ps.setString(nIndex++, info.getNote()); // 汇款用途/摘要
				ps.setLong(nIndex++, info.getFixedDepositTime()); // 定期存款期限（月）
				ps.setLong(nIndex++, info.getNoticeDay()); // 通知存款品种（天）
				ps.setLong(nIndex++, info.getContractID()); // 贷款合同ID
				ps.setLong(nIndex++, info.getLoanNoteID()); //方款通知单号
				ps.setTimestamp(nIndex++, info.getPayDate()); // 贷款放款日期
				ps.setString(nIndex++, info.getDepositNo()); //定期（通知）存款单据号
				ps.setLong(nIndex++, info.getSubAccountID()); //子账户ID
				ps.setTimestamp(nIndex++, info.getDepositStart()); //定期（通知）存款起始日
				ps.setDouble(nIndex++, info.getDepositRate()); //定期存单利率
				ps.setDouble(nIndex++, info.getDepositAmount()); //存单金额（开立金额）
				ps.setDouble(nIndex++, info.getDepositBalance()); //存单余额
				/////// 第3行  5字段(日期以有，4)
				ps.setLong(nIndex++, OBConstant.SettInstrStatus.SAVE); // 状态为未复核
				ps.setLong(nIndex++, info.getConfirmUserID()); // 录入人
				ps.setLong(nIndex++, info.getOfficeID()); // CPF-默认办事处				
				ps.setLong(nIndex++, OBConstant.getDefaultTransactionType(info.getTransType())); // CPF-处理业务类型
				/////// 第4行  12字段
				ps.setTimestamp(nIndex++, info.getClearInterest()); //结息日期
				ps.setLong(nIndex++, info.getInterestPayeeAcctID()); //利息收款方账户ID
				ps.setLong(nIndex++, info.getInterestRemitType()); //利息汇款方式
				ps.setDouble(nIndex++, info.getInterest()); //应付贷款利息
				ps.setDouble(nIndex++, info.getCompoundInterest()); //应付复利
				ps.setDouble(nIndex++, info.getOverdueInterest()); // 应付逾期罚息
				ps.setDouble(nIndex++, info.getSuretyFee()); // 应付担保费
				ps.setDouble(nIndex++, info.getCommission()); // 应付手续费
				ps.setDouble(nIndex++, info.getRealInterest()); // 实付贷款利息
				ps.setDouble(nIndex++, info.getRealCompoundInterest()); // 实付复利
				ps.setDouble(nIndex++, info.getRealOverdueInterest()); // 实付逾期罚息
				ps.setDouble(nIndex++, info.getRealSuretyFee()); // 实付担保费
				ps.setDouble(nIndex++, info.getRealCommission()); // 实付手续费
				/////// 第5行  12字段
				ps.setTimestamp(nIndex++, info.getInterestStart()); //利息起息日
				ps.setTimestamp(nIndex++, info.getCompoundStart()); //复利起息日
				ps.setDouble(nIndex++, info.getCompoundRate()); //复利利率
				ps.setTimestamp(nIndex++, info.getOverDueStart()); // 罚息起息日
				ps.setDouble(nIndex++, info.getOverDueRate()); //罚息利率
				ps.setTimestamp(nIndex++, info.getSuretyStart()); // 担保费起息日
				ps.setDouble(nIndex++, info.getSuretyRate()); //担保费率
				ps.setTimestamp(nIndex++, info.getCommissionStart()); // 手续费起息日
				ps.setDouble(nIndex++, info.getCommissionRate()); //手续费率
				ps.setDouble(nIndex++, info.getInterestRate()); //贷款利率
				ps.setDouble(nIndex++, info.getCompoundAmount()); //复利本金
				ps.setDouble(nIndex++, info.getOverDueAmount()); //逾期罚息本金
				/////// 第5行  6字段
				ps.setDouble(nIndex++, info.getInterestReceiveable()); //计提利息
				ps.setDouble(nIndex++, info.getInterestIncome()); //本次利息
				ps.setDouble(nIndex++, info.getRealInterestReceiveable()); //实付计提利息
				ps.setDouble(nIndex++, info.getRealInterestIncome()); //实付本次利息
				ps.setDouble(nIndex++, info.getInterestTax()); //利息税费
				ps.setDouble(nIndex++, info.getRealInterestTax()); //实付利息税费
				
				//modify by lxr for budget 增加 budgetitemID 字段
				ps.setLong(nIndex++, info.getBudgetItemID());  //预算项目id
				//modify by liangge for ebank 	增加 fixednextstartdate,fixednextenddate,fixednextperoid
				ps.setTimestamp(nIndex++, info.getSDepositBillStartDate());
				ps.setTimestamp(nIndex++,info.getSDepositBillEndDate());
				ps.setLong(nIndex++, info.getSDepositBillPeriod());
				ps.setLong(nIndex++,info.getSDepositInterestDeal());
				ps.setLong(nIndex++,info.getSDepositInterestToAccountID());
				ps.setString(nIndex++,info.getSDepositBillNo());
				
				 //add by mingfang 2007-05-29  数字签名字段
				ps.setString(nIndex++,info.getSignatureValue());
				ps.setString(nIndex++,info.getSignatureOriginalValue());
                ps.setString(nIndex++, info.getApplyCode());
                //added by bingliu 2012-06-27,如果传进来的数据来源为空，则默认为网银
                if(info.getSource()<=0)
                {
                	info.setSource(SETTConstant.ExtSystemSource.EBANK);
                }
                ps.setLong(nIndex++, info.getSource());
                ps.setLong(nIndex++, info.getIsAutoContinue());
                ps.setLong(nIndex++, info.getAutocontinuetype());
                ps.setLong(nIndex++, info.getAutocontinueaccountid());
                ps.setLong(nIndex++, info.getTimestamp());
				//log4j.info("nIndex=" + nIndex);
				ps.executeUpdate();
				ps.close();
				ps = null;
				//对数据进行解锁，此操作必不可少
			con.close();
			con = null;
		}
		catch (IException e)
		{
			log4j.error(e.toString());
			throw e;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lMaxID;
	}
	
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息
	 * Create Date: 2003-8-13
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection query(QueryCapForm qcf) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName, \n");
			sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser, \n");
			sbSQL.append(" (select * from BS_BANKINSTRUCTIONINFO bs where bs.n_id in ( \n");
			sbSQL.append("  select max(bs.n_id)\n");
			sbSQL.append("  from BS_BANKINSTRUCTIONINFO bs \n");
			sbSQL.append("  group by bs.s_transactionno) \n");
			sbSQL.append("  ) bs \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");			
			sbSQL.append(" and bs.s_transactionno(+)='1'||to_char(fin.CPF_STRANSNO) \n");		
			if ( qcf.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qcf.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qcf.getClientID() + " \n");
			//账户权限控制(签认时不用限制)
			if (qcf.getOperationTypeID() != OBConstant.QueryOperationType.SIGN)
			{
				if (qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT
					|| qcf.getTransType() == OBConstant.QueryInstrType.OPENNOTIFYACCOUNT
					|| qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT
					|| qcf.getTransType() == OBConstant.QueryInstrType.TRUSTLOANRECEIVE
					|| qcf.getTransType() == OBConstant.QueryInstrType.CONSIGNLOANRECEIVE
					|| qcf.getTransType() == OBConstant.QueryInstrType.INTERESTFEEPAYMENT)
				{
					if (qcf.getUserID() != -1)
					{
						//定期开立，贷款回收，控制付款方账户和收款方账户
						//查询出的付款方账号必须是该用户拥有权限的
						sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
						sbSQL.append(" select a.naccountid \n");
						sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
						sbSQL.append(" where ai.nStatusID=1  \n");
						sbSQL.append(" and a.saccountno=ai.saccountno  \n");
						sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
						sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
						
						//查询出的收款方账号必须是该用户拥有权限的
						sbSQL.append(" AND fin.nPayeeAcctID IN ( \n");
						sbSQL.append(" select a.naccountid \n");
						sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
						sbSQL.append(" where ai.nStatusID=1  \n");
						sbSQL.append(" and a.saccountno=ai.saccountno  \n");
						sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
						sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
					}
				}
				else
				{
					//其它业务，只控制付款方账户
					//查询出的付款方账号必须是该用户拥有权限的
					if (qcf.getUserID() != -1)
					{
						sbSQL.append(" AND fin.nPayerAcctID IN ( \n");
						sbSQL.append(" select a.naccountid \n");
						sbSQL.append(" from OB_AccountOwnedByUserQuery a, Sett_Account ai \n");
						sbSQL.append(" where ai.nStatusID=1  \n");
						sbSQL.append(" and a.saccountno=ai.saccountno  \n");
						sbSQL.append(" and a.nUserID="+qcf.getUserID()+" \n");
						sbSQL.append(" and ai.ncurrencyid="+qcf.getCurrencyID()+") \n");
					}
				}
			}
			//交易指令类型
			
			//新奥活期业务，当选全部时只查询出活期业务
			if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("current"))
			{
				sbSQL.append(
						" AND fin.ntranstype in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
			}	
			//新奥定期业务，当选全部时只查询出定期业务
			if (qcf.getTransType() == OBConstant.QueryInstrType.ALL&&qcf.getSign().equals("fixd"))
			{
				sbSQL.append(
						" AND fin.ntranstype not in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
			}	
			if (qcf.getTransType() > 0)
			{
				if (qcf.getTransType() == 1)
				{
					sbSQL.append(
						" AND fin.ntranstype in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
				}
				else if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n ");
					sbSQL.append(" AND fin.ndepositbillstatusid is not null \n ");		
				}
				else if(qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
					sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid=");		
					sbSQL.append(OBConstant.SettInstrStatus.SAVE);	
					sbSQL.append(")\n ");
					sbSQL.append(" and fin.settfinid is null");
				}
				else
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
					sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
				}
			}
			else
			{
				if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
				{
					//取消复核查询，不能查询出挂接审批流并且是自动复核的记录，原因：挂接审批流并且自动复核的记录只有在取消审批菜单处做动作
					String tempAutoTransTypes = checkTransType(qcf);
					if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
					{
						sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
					}
				}
			}
			//业务复核查询，查询未复核的记录：非登录人录入的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + " \n");
				}
				else
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
				sbSQL.append(" AND fin.nConfirmUserID != " + qcf.getUserID() + " \n");
			}
			//业务复核查询，查询已复核的记录：登录人复核的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
					sbSQL.append(" AND fin.ndepositbillcheckuserid = " + qcf.getUserID() + " \n");
				}
				else
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
					sbSQL.append(" AND fin.nCheckUserID = " + qcf.getUserID() + " \n");
				}
			}
			//业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{    sbSQL.append(" AND fin.sbatchno is  null\n");
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
				}
				else
					sbSQL.append(" AND ((fin.nStatus = " 
							+ OBConstant.SettInstrStatus.CHECK 
							+ ") or (fin.ntranstype="
							+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
							+ " and fin.nstatus="+OBConstant.SettInstrStatus.FINISH 
							+ " and fin.ndepositbillstatusid="
							+ OBConstant.SettInstrStatus.CHECK 
							+ ")) \n");
				sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) >= " + this.getMinSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign())+ " \n");
				sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) < " + this.getMaxSignAmountDist(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID(), qcf.getSign()) + " \n");
				sbSQL.append(" AND fin.nIsCanAccept != 1 \n");
			}
			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{   
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SIGN + " \n");
					sbSQL.append(" AND fin.ndepositbillsignuserid = " + qcf.getUserID() + " \n");
				}
				else
				{
					sbSQL.append(" AND ((fin.nstatus = " 
							+ OBConstant.SettInstrStatus.SIGN 
							+ ") or (fin.ntranstype="
							+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
							+ " and fin.nstatus="
							+ OBConstant.SettInstrStatus.FINISH 
							+ " and fin.ndepositbillstatusid="
							+ OBConstant.SettInstrStatus.SIGN 
							+ ")) \n");
					sbSQL.append(" AND fin.nSignUserID = " + qcf.getUserID() + " \n");
				}
				sbSQL.append(" AND fin.nIsCanAccept = 1 \n");
				sbSQL.append(" AND fin.sbatchno is  null\n");
			}
			//交易申请查询，查询所有状态
			if (qcf.getStatus() > 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" and fin.ndepositbillstatusid= " + qcf.getStatus() + " \n");
				}
				else
				{
					//交易申请查询，查询所有状态
					sbSQL.append(" AND fin.nstatus = " + qcf.getStatus() + " \n");
				}
			}
			
			//银行指令状态
			//撤销
			if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.CANCEL);
			}
			//以保存未发送
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SAVED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SAVED);
			}
			//支付处理中
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTING)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTING);
			}
			//支付成功
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTED);
			}
			//支付失败
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.FAILED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.FAILED);
			}
			//支付未知
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.UNKNOWENED)
			{
				sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.UNKNOWENED);
			}
			//无状态
			else if(qcf.getNEbankStatus()==OBConstant.BankInstructionStatus.NONE)
			{
				sbSQL.append(" and nvl(n_statusid,-1) ="+OBConstant.BankInstructionStatus.UNSEND);
			}
			
			// 提交日期-从1
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
			}
			// 提交日期-到
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
			}
			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
			}
			// 交易金额-从
			if (qcf.getMinAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
			}
			// 交易金额-到
			if (qcf.getMaxAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
			}
			// 合同ID
			if (qcf.getContractID() != -1)
			{
				sbSQL.append(" AND nContractID = " + qcf.getContractID() + " \n");
			}
			// 放款通知单ID
			if (qcf.getDepositID() != -1)
			{
				sbSQL.append(" AND fin.NSUBACCOUNTID = " + qcf.getDepositID() + " \n");
			}
			//下属单位
			if (qcf.getChildClientID() > 0)
			{
				sbSQL.append(" AND nChildClientid=" + qcf.getChildClientID() + " \n");
			}
			if (qcf.getChildClientID() == -2)
			{
				sbSQL.append(" AND nChildClientid > 0 \n");
			}
			
			if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
			{
				sbSQL.append(" and (fin.signaturevalue is not null or fin.lsource >"+SETTConstant.ExtSystemSource.EBANK+") \n");
			}				

			if (qcf.isOrderBy())
			{
				sbSQL.append(" order by  fin.id desc ,TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID \n");
			}
			else
			{
				sbSQL.append(" ORDER BY fin.id desc ,fin.dtconfirm ASC ,fin.nPayerAcctID \n");
			}
			
			log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			// 提交日期-从
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartSubmit().trim() + " 00:00:00"));
			}
			// 提交日期-到 
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndSubmit().trim() + " 23:59:59"));
			}
			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartExe().trim() + " 00:00:00"));
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndExe().trim() + " 23:59:59"));
			}
			
			
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new FinanceInfo();
				info.setLSource(rs.getLong("lSource"));
				info.setID(rs.getLong("ID")); // 指令序号
				info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
				//				下属单位
				if (info.getChildClientID() > 0)
				{
					info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
					info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
				}
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME"));
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户ID
				info.setContractID(rs.getLong("NCONTRACTID")); // 贷款合同id
				info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); // 贷款合同编号
				info.setLoanNoteID(rs.getLong("NLOANNOTEID")); //放款通知单id
				info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单号
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(rs.getString("CPF_STRANSNO")); //交易号
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名
				info.setReject(rs.getString("CPF_SREJECT")); // CPF-拒绝原因
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setSDepositBillNo(rs.getString("sDepositBillNo"));
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
				info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间  ---  add   by  zhanglei  2010.06.03
				info.setSignatureValue(rs.getString("signaturevalue"));
				info.setSignatureOriginalValue(rs.getString("signatureoriginalvalue"));
				
				//获取收款方和付款方的详细信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
				info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				info.setPayeeProv(payeeInfo.getProv()); // 汇入省
				info.setPayeeCity(payeeInfo.getCity()); // 汇入市
				info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
				info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
				info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
				info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
				info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
				info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
				info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
				info.setSBatchNo(rs.getString("SBATCHNO"));//批量付款批次号 modify by xwhe 2008-11-10
				info.setEbankStatus(rs.getLong("n_statusid")); //银行申请指令状态
				info.setNoticeDay(rs.getLong("NNOTICEDAY"));  //通知存款品种
				info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));  //存单起始日
				info.setSDepositBillEndDate(rs.getTimestamp("FIXEDNEXTENDDATE"));	//存单结束日
				info.setSDepositBillPeriod(rs.getLong("fixednextperiod"));  //定期存款期限
				info.setSDepositInterestDeal(rs.getLong("fixedinterestdeal"));//利息处理方式
				info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID")); //利息转至活期账户id
				vReturn.add(info);
			}
			
			rs.close();
			
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			//log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	/**
	 * 用于在确认页面复核页面的校验
	 * Create Date: 2003-8-13
	 * @param financeInfo 
	 * @return boolean  
	 * @exception Exception
	 */
	public boolean checkAmount(FinanceInfo financeInfo) throws Exception
	{
		AccountBalanceInfo accountBalanceInfo = null;
		double dMaxAmount = 0.0;
		double dAmount1 = 0.0;
		double dAmount2 = 0.0;
		boolean bResult = true;
		try
		{
			//获得账户的当前余额、可用余额、最大可发放金额、透支限额、可用上存
			accountBalanceInfo = getCurrBalanceByAccountID(financeInfo.getPayerAcctID(), financeInfo.getCurrencyID(), -1);
			//可用余额－交易金额＋透支限额
			dAmount1 = accountBalanceInfo.getUsableBalance() - financeInfo.getAmount() + accountBalanceInfo.getOverdraftAmount();
			if (dAmount1 < 0)
			{
				bResult = false;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return bResult;
	}
	/**
	 * 返回财务交易的信息
	 * @param id
	 * @return
	 */
	public FinanceInfo getFinanceInfo(long id)throws Exception{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append(" SELECT ");
			sbSQL.append(" nremittype, ");//汇款方式
			sbSQL.append(" mamount, ");//交易金额
			sbSQL.append(" dtexecute, ");//执行日
			sbSQL.append(" nconfirmuserid, ");//确认人ID
			sbSQL.append(" dtconfirm, ");//确认时间
			sbSQL.append(" npayeracctid, ");//本金付款方账户ID
			sbSQL.append(" npayeeacctid ");//本金收款方账户ID
			sbSQL.append(" FROM OB_FinanceInStr ");
			sbSQL.append(" where id=" + id);
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				info = new FinanceInfo();
				info.setRemitType(rs.getLong("nremittype"));//汇款方式
				info.setAmount(rs.getDouble("mamount"));//交易金额
				info.setExecuteDate(rs.getTimestamp("dtexecute"));//执行日
				info.setConfirmUserID(rs.getLong("nconfirmuserid"));//确认人ID
				info.setConfirmDate(rs.getTimestamp("dtconfirm"));//确认时间
				info.setPayerAcctID(rs.getLong("npayeracctid"));//本金付款方账户ID
				info.setPayeeAcctID(rs.getLong("npayeeacctid"));//本金收款方账户ID
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return info;		
	}
	/**
	 * 修改财务交易值指令状态为已复核
	 * Create Date: 2003-8-13
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lCheckUserID -- 复核人
	 * @return long , 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public long check(long lInstructionID, long lCheckUserID) throws Exception
	{
		//检查此交易指令在复核前是否被修改或删除,只有确认的指令才能被复核
		boolean bStatus = false;
		Connection con = null;
		PreparedStatement ps = null;
		long lStatusTest=1;
		long lConfirmUserIDTest=-1;
        long lSource = -1;  //数据来源，标识交易指令是由外部系统提交还是由网银自己产生
		ResultSet rs = null;
		try
		{
			//判断是否被删除
			bStatus = isDelete(lInstructionID);
			if (bStatus)
			{
				throw new IException("OB_EC13");
			}
			//add by sxyao@isoftstone.com 2003-01-24
			//判断用户是否有此项操作的权力
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("SELECT nStatus, nConfirmUserID,lSource FROM ob_financeinstr WHERE id=?");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInstructionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lStatusTest = rs.getLong("nStatus");
				lConfirmUserIDTest = rs.getLong("nConfirmUserID");
                lSource = rs.getLong("lSource");
			}
			else
			{	
				throw new IException("Gen_E003");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//只有状态为已确认时才能复核
			if (lStatusTest != OBConstant.SettInstrStatus.SAVE 
                    && lStatusTest != OBConstant.SettInstrStatus.APPROVALED) //未复核
			{
				log4j.info("操作异常, 原因可能是有其他用户改变了交易指令状态");
				
				throw new IException("OB_EC04");
			}
			//登录人是否为确认人
			//如果是非自动复核
			if(!OBFSWorkflowManager.isAutoCheck()
                    && !IsOuterSourceJudgement.judge(lSource))
			{
				if (lCheckUserID == lConfirmUserIDTest)
				{
					log4j.info("复核人不能为确认人");
					throw new IException("OB_EC14");
				}
			}
			return updateStatus(lInstructionID, OBConstant.SettInstrStatus.CHECK, lCheckUserID, "check");
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
	}
	/**
	 * 修改财务交易值指令状态由已复核变为未复核
	 * Create Date: 2003-8-13
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lCheckUserID -- 复核人
	 * @return long , 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public long cancelCheck(long lInstructionID, long lCheckUserID) throws Exception
	{
		synchronized(lockObj)
		{
			//检查此交易指令在取消复核前是否被签任,只有未签任的指令才能取消复核
			long retlong=-1;
			boolean bStatus = false;
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			long lStatusTest=-1; 
			long lCheckUserIDTest=-1;
			try
			{
				//判断是否被删除
				bStatus = isDelete(lInstructionID);
				if (bStatus){
					retlong=-2;
					//throw new IException("OB_EC13");
				}
				bStatus = isStatus(lInstructionID, OBConstant.SettInstrStatus.SIGN);
				//指令已经复合,返回错误代码
				if (bStatus)
				{
					retlong=-8;//状态已经被修改//
					//throw new IException("OB_EC05");
				}
				//判断用户是否有此项操作的权力
				con = Database.getConnection();
				StringBuffer sbSQL = new StringBuffer();
				
				sbSQL.append("SELECT nStatus, nCheckUserID FROM ob_financeinstr WHERE id=?");
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lInstructionID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lStatusTest = rs.getLong("nStatus");
					lCheckUserIDTest = rs.getLong("nCheckUserID");
				}
				else
				{
					retlong=-3;
					//throw new IException("Gen_E003");
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				//只有状态为已复核时才能取消复核
				if (lStatusTest != OBConstant.SettInstrStatus.CHECK) //已复核
				{
					retlong=-4;
					log4j.info("操作异常, 原因可能是有其他用户改变了交易指令状态");
					//throw new IException("OB_EC15");
				}
				//登录人是否为复核人
				if (lCheckUserID != lCheckUserIDTest)
				{
					retlong=-5;
					log4j.info("取消复核只能是复核人");
					//throw new IException("OB_EC16");
				}
				//log4j.print("retlong===++++++++++======"+retlong);
				if(retlong==-1){
					// 查询复核前的状态
					FinanceInfo fInfo=new FinanceInfo();
				    fInfo=this.findByID(lInstructionID);
				    //modify by xwhe 2008-11-27 批量付款没有走审批流，直接将取消复核的状态置为已保存
				    long lCancelCheckStatus = -1;
				    if(fInfo.getTransType()== OBConstant.SettInstrType.CAPTRANSFER_BANKPAY && fInfo.getSBatchNo()!=null && fInfo.getSBatchNo().length()>0)
				    {
				    	lCancelCheckStatus  = OBConstant.SettInstrStatus.SAVE;
				    }
				    else
				    {	
					    lCancelCheckStatus = OBFSWorkflowManager.getOBCancelCheckStatus(new InutParameterInfo(fInfo.getOfficeID(),
							fInfo.getCurrencyID(),
							fInfo.getClientID(),
							Constant.ModuleType.SETTLEMENT,
							fInfo.getTransType(),
							fInfo.getRemitType()
							));
				    }				
				retlong=updateStatus(lInstructionID, lCancelCheckStatus, lCheckUserID, "cancelcheck");				
				}
			//log4j.print("retlong========="+retlong);
			}
			catch (IException ie)
			{
				log4j.error(ie.toString());
				throw ie;
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
			return retlong;
		}
	}
	/**
	 * 得到网银开机日和开机状态
	 * Create Date: 2003-8-13
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lCheckUserID -- 复核人
	 * @return long , 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public OpenDateInfo getOpenDate(long nOfficeID, long nCurrencyID) throws Exception
	{
		//检查此交易指令在取消复核前是否被签任,只有未签任的指令才能取消复核
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OpenDateInfo info = null;
		try
		{
			//获取网银开机日和开机状态
			String strSQL = " SELECT nSystemStatusID,dtOpenDate ";
			strSQL += " FROM sett_officeTime so";
			strSQL += " WHERE so.nCurrencyID = " + nCurrencyID;
			strSQL += " AND so.nOfficeID = " + nOfficeID;
			log4j.info("strSQL=" + strSQL);
			con = Database.getConnection();
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = new OpenDateInfo();
				info.setSystemStatusID(rs.getLong("nSystemStatusID"));
				info.setOpenDate(rs.getTimestamp("dtOpenDate"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			return info;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
	}
	/**
	 * 得到当前的交易指令
	 * @param lInstructionID
	 * @return
	 * @throws IException
	 */
	public long getStatus(long lInstructionID) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retlong = -1;
		try
		{
			//判断用户是否有此项操作的权力
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("select nstatus from ob_financeinstr where id=?");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lInstructionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				retlong = rs.getLong("nstatus");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return retlong;	
	}
	/**
	 * 修改财务交易值指令状态为已签认
	 * Create Date: 2003-8-13
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lSignUserID      -- 签认人
	 * @return long , 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public long sign(long lInstructionID, long lSignUserID) throws Exception
	{   synchronized(lockObj){
		boolean bStatus = false;
		long retlong=-1;
		try
		{  	if (this.getStatus(lInstructionID)==OBConstant.SettInstrStatus.CHECK){
			//判断是否被删除
			bStatus = isDelete(lInstructionID);
			if (bStatus)
			{
				retlong=-2;
				//throw new IException("OB_EC13");
			}
			//检查此交易指令在签任前是否被复核,只有复核的指令才能签任	
			if(this.getStatus(lInstructionID) != OBConstant.SettInstrStatus.FINISH)
				bStatus = isStatus(lInstructionID, OBConstant.SettInstrStatus.CHECK);
			else
				bStatus = true;
			if (!bStatus)
			{
				retlong=-3;
				//throw new IException("OB_EC20");
			}
				//log4j.print("sign========="+retlong);
			if(retlong==-1){
				if(this.getStatus(lInstructionID) != OBConstant.SettInstrStatus.FINISH)
					retlong= updateStatus(lInstructionID, OBConstant.SettInstrStatus.SIGN, lSignUserID, "sign");
				else
					retlong= updateStatus(lInstructionID, OBConstant.SettInstrStatus.FINISH, lSignUserID, "sign");
			}
			//log4j.print("sign====+++++++++====="+retlong);
		}
			else{
				throw new IException("只有状态为复核的才可以签认");

			}
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return retlong;
			}
	}
	/**
	 * 修改财务交易指令状态由已签认变为已复核
	 * Create Date: 2003-10-6
	 * @param long lInstructionID -- 交易指令ID
	 * @param long lSignUserID      -- 签认人
	 * @return long , 大于0表示成功，小于,等于0表示失败
	 * @exception Exception
	 */
	public long cancelSign(long lInstructionID, long lSignUserID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long retlong=-1;
		try
		{
			//判断是否被删除
			boolean bStatus = false;
			bStatus = isDelete(lInstructionID);
			if (bStatus)
			{
				retlong=-2;
				//throw new IException("OB_EC13");
			}
			//检查此交易指令是否被签任,只有签任的指令才能取消签任	
			if(this.getStatus(lInstructionID) != OBConstant.SettInstrStatus.FINISH)
				bStatus = isStatus(lInstructionID, OBConstant.SettInstrStatus.SIGN);
			else
				bStatus = true;
			if (!bStatus)
			{
				retlong=-3;
				//throw new IException("OB_EC22");
			}
			//判断用户是否有此项操作的权力
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			long lSignUserIDTest = -1;
			if(this.getStatus(lInstructionID) == OBConstant.SettInstrStatus.FINISH)
				sb.append("select ndepositbillsignuserid from ob_financeinstr where id=?");
			else
				sb.append("select nSignUserID from ob_financeinstr where id=?");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, lInstructionID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				if(this.getStatus(lInstructionID) == OBConstant.SettInstrStatus.FINISH)
					lSignUserIDTest = rs.getLong("ndepositbillsignuserid");
				else
					lSignUserIDTest = rs.getLong("nSignUserID");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			//登录人是否为签认人
			if (lSignUserID != lSignUserIDTest)
			{
				retlong=-4;
				throw new IException("OB_EC23");
			}
			//log4j.print("cancelSign===============retlong  "+retlong);
			if(retlong==-1){
				if(this.getStatus(lInstructionID) != OBConstant.SettInstrStatus.FINISH)
					retlong= updateStatus(lInstructionID, OBConstant.SettInstrStatus.CHECK, lSignUserID, "cancelSign");
				else
					retlong= updateStatus(lInstructionID, OBConstant.SettInstrStatus.FINISH, lSignUserID, "cancelSign");
			}
			//log4j.print("cancelSign=====+++==========retlong  "+retlong);
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return retlong;
	}
	/**
	 * 查询交易总结
	 * Create Date: 2003-8-13
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件的交易总结信息
	 * @exception Exception
	 */
	public Collection querySummarize(QueryCapForm qcf) throws Exception
	{
		//定义变量
		FinanceInfo info = null;
		OBCapSummarizeInfo csInfo = null;
		Collection c = null;
		Vector vReturn = new Vector();
		Iterator it = null;
		Timestamp tsConfirmDate = null; // 确认时间
		long lTotalCount = 0; //共有笔数	
		long lUnCheckCount = 0; //未复核笔数
		long lCheckCount = 0; //已复核笔数
		long lSignCount = 0; //已签认笔数
		long lOnGoingCount = 0; //处理中笔数
		long lFinishedCount = 0; //已完成笔数
		long lRefusedCount = 0; //已拒绝笔数
		double dTotalAmount = 0; //总交易金额
		double dLoanAmount = 0; //其中贷金额
		double dDebitAmount = 0; //其中借金额	
		try
		{
			if (qcf != null)
			{
				c = query(qcf);
				if (c != null)
				{
					it = c.iterator();
					for (int i = 0; it.hasNext(); i++)
					{
						info = (FinanceInfo) it.next();
						if (tsConfirmDate != info.getConfirmDate())
						{
							csInfo = new OBCapSummarizeInfo();
							csInfo.setConfirmDate(tsConfirmDate);
							tsConfirmDate = info.getConfirmDate();
							csInfo.setTotalCount(lTotalCount);
							lTotalCount = 0;
							csInfo.setUnCheckCount(lUnCheckCount);
							lUnCheckCount = 0;
							csInfo.setCheckCount(lCheckCount);
							lCheckCount = 0;
							csInfo.setSignCount(lSignCount);
							lSignCount = 0;
							csInfo.setOnGoingCount(lOnGoingCount);
							lOnGoingCount = 0;
							csInfo.setFinishedCount(lFinishedCount);
							lFinishedCount = 0;
							csInfo.setRefusedCount(lRefusedCount);
							lRefusedCount = 0;
							csInfo.setTotalAmount(dTotalAmount);
							dTotalAmount = 0;
							csInfo.setLoanAmount(dLoanAmount);
							dLoanAmount = 0;
							csInfo.setDebitAmount(dDebitAmount);
							dDebitAmount = 0;
							if (i > 0)
							{
								vReturn.add(csInfo);
							}
						}
						lTotalCount = lTotalCount + 1; //共有笔数
						if (info.getStatus() == OBConstant.SettInstrStatus.SAVE)
						{
							lUnCheckCount = lUnCheckCount + 1; //未复核笔数
						}
						if (info.getStatus() == OBConstant.SettInstrStatus.CHECK)
						{
							lCheckCount = lCheckCount + 1; //已复核笔数
						}
						if (info.getStatus() == OBConstant.SettInstrStatus.SIGN)
						{
							lSignCount = lSignCount + 1; //已签认笔数
						}
						if (info.getStatus() == OBConstant.SettInstrStatus.DEAL)
						{
							lOnGoingCount = lOnGoingCount + 1; //处理中笔数
						}
						if (info.getStatus() == OBConstant.SettInstrStatus.FINISH)
						{
							lFinishedCount = lFinishedCount + 1; //已完成笔数
						}
						if (info.getStatus() == OBConstant.SettInstrStatus.REFUSE)
						{
							lRefusedCount = lRefusedCount + 1; //已拒绝笔数
						}
						//总交易金额
						dTotalAmount = dTotalAmount + info.getAmount();
						//其中贷金额
						dDebitAmount = dDebitAmount + info.getAmount();
					}
					csInfo = new OBCapSummarizeInfo();
					csInfo.setConfirmDate(tsConfirmDate);
					csInfo.setTotalCount(lTotalCount);
					csInfo.setUnCheckCount(lUnCheckCount);
					csInfo.setCheckCount(lCheckCount);
					csInfo.setSignCount(lSignCount);
					csInfo.setOnGoingCount(lOnGoingCount);
					csInfo.setFinishedCount(lFinishedCount);
					csInfo.setRefusedCount(lRefusedCount);
					csInfo.setTotalAmount(dTotalAmount);
					csInfo.setLoanAmount(dLoanAmount);
					csInfo.setDebitAmount(dDebitAmount);
					vReturn.add(csInfo);
				}
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	/**
	 * 根据放款通知单id,查找子账户中关于利息和费用的信息
	 * added by gqzhang
	 * @param info
	 *            SubLoanAccountDetailInfo
	 * @return SubLoanAccountDetailInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition1(SubLoanAccountDetailInfo info) throws Exception
	{
		SubLoanAccountDetailInfo returnInfo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			log4j.info("\n================findSubLoanAccountDetailByCondition start==========\n");
			long lSubAccountID = -1;
			con = Database.getConnection();
			StringBuffer sbSQL = null;
			sbSQL = new StringBuffer();
			//根据放款通知单得到子账户ID; AL_nLoanNoteID 放款通知单 sett_SubAccount子账户表
			sbSQL.append("select ID from sett_SubAccount ");
			sbSQL.append("where AL_nLoanNoteID=");
			sbSQL.append(info.getLoanNoteID() + " \n");
			sbSQL.append("and nStatusId=1");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lSubAccountID = rs.getLong(1);
				log4j.info("SubAccountId:" + lSubAccountID);
			}
			else
			{
				throw new IException("未找到放款通知单对应的子账户");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			sbSQL = new StringBuffer();
			if (lSubAccountID > 0)
			{
				sbSQL.append(
					"select MINTEREST dInterest,"
						+ " AL_MCOMPOUNDINTEREST dCompoundInterest,"
						+ " AL_MOVERDUEINTEREST dOverDueInterest,"
						+ " AL_MSURETYFEE dSuretyFee, "
						+ " al_mcommission dCommission ");
				sbSQL.append(" from SETT_SUBACCOUNT where ID=? ");
				log4j.info("============strSQL start=============");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lSubAccountID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					returnInfo = new SubLoanAccountDetailInfo();
					returnInfo.setSubAccountID(lSubAccountID);
					returnInfo.setLoanNoteID(info.getLoanNoteID());
					returnInfo.setInterest(rs.getDouble("dInterest"));
					returnInfo.setCompoundInterest(rs.getDouble("dCompoundInterest"));
					returnInfo.setOverDueInterest(rs.getDouble("dOverDueInterest"));
					returnInfo.setSuretyFee(rs.getDouble("dSuretyFee"));
					returnInfo.setCommission(rs.getDouble("dCommission"));
					returnInfo.setTotal(returnInfo.getInterest() + returnInfo.getCompoundInterest() + returnInfo.getOverDueInterest() + returnInfo.getSuretyFee() + returnInfo.getCommission());
				}
				log4j.info("============strSQL start=============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				StringBuffer sbSqlContractType = new StringBuffer();
				sbSqlContractType.append("select nTypeID from loan_contractform ");
				sbSqlContractType.append("where id=(select nContractID ");
				sbSqlContractType.append("from loan_payform where id=?)");

				ps = con.prepareStatement(sbSqlContractType.toString());
				ps.setLong(1, info.getLoanNoteID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					returnInfo.setContractType(rs.getLong(1));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
			}
			con.close();
			con = null;
			log4j.info("\n================findSubLoanAccountDetailByCondition End==========\n");
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return returnInfo;
	}
	/**
	 * Method findSubLoanAccountDetailByCondition1.
	 * 调用算息接口（暂时保留）
	 * @param info
	 * @return SubLoanAccountDetailInfo
	 * @throws Exception
	 */
	public SubLoanAccountDetailInfo findSubLoanAccountDetailByCondition(SubLoanAccountDetailInfo info) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SubLoanAccountDetailInfo returnInfo = new SubLoanAccountDetailInfo();
		try
		{
			log4j.info("\n================findSubLoanAccountDetailByCondition start==========\n");
			long lSubAccountID = -1;
			long lLoanAccountID = -1;
			con = Database.getConnection();
			StringBuffer sbSQL = null;
			//根据放款通知单查找子账户id,贷款账号,利息税费
			sbSQL = new StringBuffer();
			sbSQL.append(" select ID,NACCOUNTID,AL_MINTERESTTAXRATE from sett_SubAccount ");
			sbSQL.append(" where AL_nLoanNoteID=");
			sbSQL.append(info.getLoanNoteID());
			sbSQL.append(" and nStatusId=" + Constant.RecordStatus.VALID + " ");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				lSubAccountID = rs.getLong("ID");
				lLoanAccountID = rs.getLong("NACCOUNTID");
				info.setSubAccountID(lSubAccountID);
				info.setLoanAccountID(lLoanAccountID);
				returnInfo.setSubAccountID(lSubAccountID);
				returnInfo.setLoanAccountID(lLoanAccountID);
				returnInfo.setInterestTaxRate(rs.getDouble("AL_MINTERESTTAXRATE"));
				log4j.info("子账户ID:" + lSubAccountID);
				log4j.info("贷款账户ID:" + lLoanAccountID);
				log4j.print("利息税率:" + returnInfo.getInterestTaxRate());
			}
			else
			{
				throw new IException("未找到放款通知单对应的子账户信息");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			
			boolean bIsYT = false;
			
			sbSQL.setLength(0);
			sbSQL.append(" select c.MRATE");
			sbSQL.append(" from sett_SubAccount s,loan_payform p,loan_yt_loancontractbankassign c");
			sbSQL.append(" where s.AL_nLoanNoteID = p.id");
			sbSQL.append(" and p.NCONTRACTID = c.NCONTRACTID");
			sbSQL.append(" and c.NISHEAD = 1");
			sbSQL.append(" and s.nStatusId=" + Constant.RecordStatus.VALID);
			sbSQL.append(" and  s.AL_nLoanNoteID="+info.getLoanNoteID());
			
			log4j.info(sbSQL.toString());
			
			ps = con.prepareStatement(sbSQL.toString());
			rs = ps.executeQuery();
			
			BigDecimal rate = new BigDecimal("1");
			
			if (rs.next())
			{
				bIsYT = true;
				rate = rs.getBigDecimal(1);
				log4j.info("======================rate:" + rate);
			}

			con.close();
			con = null;
			//调用算息接口返回到resultInfo中
			log4j.info("====开始调用算息接口======");
			/**
			 * 得到当前系统时间
			 */
			Timestamp tsSys = Env.getSystemDate(info.getOfficeID(), info.getCurrencyID());
			InterestOperation interestOperation = new InterestOperation();
			//贷款利息信息
			log4j.info("====开始计算贷款利息======");
			log4j.info("====贷款账户id:" + info.getLoanAccountID());
			log4j.info("===子账户id:" + info.getSubAccountID());
			log4j.info("===结息日:" + info.getInterestStart());
			LoanAccountInterestInfo interest =
				interestOperation.GetLoanAccountInterest(info.getOfficeID(), info.getCurrencyID(), info.getLoanAccountID(), info.getSubAccountID(), info.getInterestStart(), tsSys);
			log4j.info("=====after=========");
			if (interest != null)
			{	
				if (bIsYT)
				{
					BigDecimal temp = new BigDecimal(Double.toString(interest.getInterest()*100));
					returnInfo.setInterest(DataFormat.BigToDouble(temp.divide(rate,20,BigDecimal.ROUND_HALF_UP),2));
				}
				else
				{
					returnInfo.setInterest(interest.getInterest());
				}
				returnInfo.setInterestStart(interest.getSDate());
				double dInterestRate = UtilOperation.Arith.round(UtilOperation.Arith.div(interest.getRate(), 100), 6);
				log4j.info("===贷款利息:" + returnInfo.getInterest());
				returnInfo.setInterestRate(dInterestRate);
			}
			//计提利息
			log4j.info("====开始计算计提利息======");
			Sett_SubAccountDAO sett_SubAccountDAO = new Sett_SubAccountDAO();
			long lAccountType = sett_SubAccountDAO.getAccountTypeBySubAccountID(info.getSubAccountID());
			SubAccountPredrawInterestInfo interestPredraw = interestOperation.getLoanAccountPredrawInterest(info.getLoanAccountID(), info.getSubAccountID(), lAccountType, info.getInterestStart());
			returnInfo.setInterestReceiveable(interestPredraw.getPredrawInterest());
			log4j.info("===计提利息:" + returnInfo.getInterestReceiveable());
			//本次利息
			log4j.print("====开始计算本次利息====");
			double dInterestIncome = UtilOperation.Arith.sub(returnInfo.getInterest(), returnInfo.getInterestReceiveable());
			returnInfo.setInterestIncome(dInterestIncome);
			log4j.info("===本次利息:" + returnInfo.getInterestIncome());
			//复利信息
			log4j.info("====开始计算复利======");
			LoanAccountInterestInfo compoundInterest =
				interestOperation.getLoanAccountFee(
					info.getOfficeID(),
					info.getCurrencyID(),
					info.getLoanAccountID(),
					info.getSubAccountID(),
					info.getInterestStart(),
					tsSys,
					SETTConstant.InterestFeeType.COMPOUNDINTEREST);
			if (compoundInterest != null)
			{
				returnInfo.setCompoundInterest(compoundInterest.getInterest()); //得到复利
				returnInfo.setCompInterestStart(compoundInterest.getSDate()); //得到复利起息日
				returnInfo.setCompoundAmount(compoundInterest.getBalance()); //得到复利本金
				double dCompoundRate = UtilOperation.Arith.round(UtilOperation.Arith.div(compoundInterest.getRate(), 100), 6);
				returnInfo.setCompRate(dCompoundRate); //得到复利的利率
			}
			//逾期罚息信息
			log4j.info("====开始计算逾期罚息======");
			LoanAccountInterestInfo overDueInterest =
				interestOperation.getLoanAccountFee(
					info.getOfficeID(),
					info.getCurrencyID(),
					info.getLoanAccountID(),
					info.getSubAccountID(),
					info.getInterestStart(),
					tsSys,
					SETTConstant.InterestFeeType.FORFEITINTEREST);
			if (overDueInterest != null)
			{
				returnInfo.setOverDueInterest(overDueInterest.getInterest()); //得到逾期罚息
				returnInfo.setOverDueStart(overDueInterest.getSDate()); //得到逾期罚息起息日
				returnInfo.setOverDueAmount(overDueInterest.getBalance()); //得到逾期罚息本金
				double dOverDueRate = UtilOperation.Arith.round(UtilOperation.Arith.div(overDueInterest.getRate(), 100), 6);
				returnInfo.setOverDueRate(dOverDueRate); //得到逾期罚息利率
			}
			//担保费信息
			log4j.info("====开始计算担保费======");
			LoanAccountInterestInfo suretyInterest =
				interestOperation.getLoanAccountFee(
					info.getOfficeID(),
					info.getCurrencyID(),
					info.getLoanAccountID(),
					info.getSubAccountID(),
					info.getInterestStart(),
					tsSys,
					SETTConstant.InterestFeeType.ASSURE);
			if (suretyInterest != null)
			{
				//最后一个是类型码
				returnInfo.setSuretyFee(suretyInterest.getInterest());
				returnInfo.setSuretyStart(suretyInterest.getSDate());
				double dSuretyFeeRate = UtilOperation.Arith.round(UtilOperation.Arith.div(suretyInterest.getRate(), 100), 6);
				returnInfo.setSuretyRate(dSuretyFeeRate);
			}
			//手续费信息
			log4j.info("====开始计算手续费======");
			LoanAccountInterestInfo commission =
				interestOperation.getLoanAccountFee(
					info.getOfficeID(),
					info.getCurrencyID(),
					info.getLoanAccountID(),
					info.getSubAccountID(),
					info.getInterestStart(),
					tsSys,
					SETTConstant.InterestFeeType.COMMISION);
			if (commission != null)
			{
				//最后一个是类型码
				returnInfo.setCommission(commission.getInterest());
				returnInfo.setCommissionStart(commission.getSDate());
				double dCommissionRate = UtilOperation.Arith.round(UtilOperation.Arith.div(commission.getRate(), 100), 6);
				returnInfo.setCommissionRate(dCommissionRate);
			}
			//利息税费
			log4j.print("----开始计算利息税费----");
			double dInterestShouldTax = UtilOperation.Arith.add(returnInfo.getOverDueInterest(), UtilOperation.Arith.add(returnInfo.getInterest(), returnInfo.getCompoundInterest()));
			//应缴税利息
			InterestTaxInfo tax = interestOperation.getInterestTax(info.getLoanAccountID(), info.getSubAccountID(), dInterestShouldTax);
			returnInfo.setInterestTax(tax.getInterestTax());
			returnInfo.setInterestTaxRate(tax.getInterestTaxRate());
			log4j.print("利息税费:" + returnInfo.getInterestTax());

			//计算利息费用总和			
			if (bIsYT)
			{
				returnInfo.setTotal(returnInfo.getInterest() + returnInfo.getCompoundInterest() + returnInfo.getOverDueInterest() + returnInfo.getSuretyFee());
			}
			else
			{
				returnInfo.setTotal(returnInfo.getInterest() + returnInfo.getCompoundInterest() + returnInfo.getOverDueInterest() + returnInfo.getSuretyFee() + returnInfo.getCommission());
			}
			log4j.info("\n================findSubLoanAccountDetailByCondition End==========\n");
		}
		catch (IException e)
		{
			log4j.error(e.toString());
			/*if(e.getErrorCode()=="起息日晚于止息日，交易失败")
			{
				throw new IException("OB_EC26");
			}*/
			throw e;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return returnInfo;
	}
	public PayerOrPayeeInfo getLoanAccountSelectInfo(long lClientID, long lCurrencyID, long lAccountGroupID,long lUserID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PayerOrPayeeInfo info = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
				strSQL =
					" select * from SETT_ACCOUNT where nclientid = "
						+ lClientID
						+ " and ncurrencyid = "
						+ lCurrencyID
						+ "  and naccounttypeid in (select id from sett_accountType where nAccountGroupID = "
						+ lAccountGroupID
						+ ")  and  NSTATUSID = "
						+ SETTConstant.AccountStatus.NORMAL //账户状态为正常
						+"  and  NCHECKSTATUSID = " 
						+ SETTConstant.AccountCheckStatus.CHECK //已复核
						+" and saccountno in "
						+" (select a.saccountno "
						+" from OB_AccountOwnedByUser a, Sett_Account ai "
						+" where ai.nStatusID=1 "
						+" and a.saccountno=ai.saccountno "
						+" and a.nUserID="
						+lUserID
						+" and ai.ncurrencyid="
						+lCurrencyID
						+")";
						
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info = new PayerOrPayeeInfo();
				info.setAccountID(rs.getLong("ID"));
				info.setAccountName(rs.getString("SNAME"));
				info.setAccountNo(rs.getString("SACCOUNTNO"));
				info.setAccountType(rs.getLong("NACCOUNTTYPEID"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.info(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	/**
	 * 根据账户种类得到账户信息
	 * 
	 * @param  ClientID,CurrencyID,AccountTypeID
	 *            
	 * @return PayerOrPayeeInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public PayerOrPayeeInfo getLoanAccountInfo(long lClientID, long lCurrencyID, long lAccountGroupID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PayerOrPayeeInfo info = null;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
				strSQL =
					" select * from SETT_ACCOUNT        where nclientid = "
						+ lClientID
						+ " and ncurrencyid = "
						+ lCurrencyID
						+ "  and naccounttypeid in (select id from sett_accountType where nAccountGroupID = "
						+ lAccountGroupID
						+ ")  and  NSTATUSID = "
						+ SETTConstant.AccountStatus.NORMAL //账户状态为正常
	+"  and  NCHECKSTATUSID = " + SETTConstant.AccountCheckStatus.CHECK; //已复核
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				info = new PayerOrPayeeInfo();
				info.setAccountID(rs.getLong("ID"));
				info.setAccountName(rs.getString("SNAME"));
				info.setAccountNo(rs.getString("SACCOUNTNO"));
				info.setAccountType(rs.getLong("NACCOUNTTYPEID"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.info(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	/**
	 * 根据账户种类得到账户信息
	 * 
	 * @param  ClientID,CurrencyID,AccountTypeID
	 *            
	 * @return long
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public long getLoanAccountID(long lClientID, long lCurrencyID, long lAccountTypeID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lResult = -1;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL =
				" select ID from SETT_ACCOUNT where nclientid = "
					+ lClientID
					+ " and ncurrencyid = "
					+ lCurrencyID
					+ "  and naccounttypeid = "
					+ lAccountTypeID
					+ "  and  NSTATUSID = "
					+ SETTConstant.AccountStatus.NORMAL;
			//账户状态为正常
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lResult = rs.getLong("ID");
			}
		}
		catch (Exception e)
		{
			log4j.info(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	/**
	 * 根据账户ID得到账户信息
	 * 
	 * @param  AccountID,TransTypeID
	 *           
	 * @return PayerOrPayeeInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	public PayerOrPayeeInfo getPayerOrPayeeInfoByID(long lAccountID, long lRemitType, long lPayerOrPayee) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PayerOrPayeeInfo info = new PayerOrPayeeInfo();
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			if ((lRemitType == OBConstant.SettRemitType.BANKPAY||lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER||lRemitType == OBConstant.SettRemitType.FINCOMPANYPAY||lRemitType == OBConstant.SettRemitType.PAYSUBACCOUNT) && lPayerOrPayee == OBConstant.PayerOrPayee.PAYEE)
			{
				strSQL = "select * from OB_PAYEEINFO where ID = " + lAccountID;
				log4j.info("strSQL=" + strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SPAYEENAME"));
					info.setAccountNo(rs.getString("SPAYEEACCTNO"));
					info.setBankName(rs.getString("SPAYEEBANKNAME"));
					info.setProv(rs.getString("SPAYEEPROV"));
					info.setCity(rs.getString("SPAYEECITY"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
					info.setSPayeeBankCNAPSNO(rs.getString("SPAYEEBANKCNAPSNO"));
					info.setSPayeeBankExchangeNO(rs.getString("SPAYEEBANKEXCHANGENO"));
					info.setSPayeeBankOrgNO(rs.getString("SPAYEEBANKORGNO"));
					info.setBankAllName(rs.getString("bankName"));
					
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			else if (lRemitType == OBConstant.SettRemitType.SELF && lPayerOrPayee == OBConstant.PayerOrPayee.PAYEE)
			{
				strSQL = "select obacct.* from OB_PAYEEINFO obacct,SETT_ACCOUNT seacct where obacct.spayeeacctno=seacct.saccountno(+) and seacct.ID = " + lAccountID;
				log4j.info("strSQL=" + strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SPAYEENAME"));
					info.setAccountNo(rs.getString("SPAYEEACCTNO"));
					//info.setBankName(rs.getString("SPAYEEBANKNAME"));
					info.setBankName(Env.getClientName());
					info.setProv(rs.getString("SPAYEEPROV"));
					info.setCity(rs.getString("SPAYEECITY"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
					info.setSPayeeBankCNAPSNO(rs.getString("SPAYEEBANKCNAPSNO"));
					info.setSPayeeBankExchangeNO(rs.getString("SPAYEEBANKEXCHANGENO"));
					info.setSPayeeBankOrgNO(rs.getString("SPAYEEBANKORGNO"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			else
			{
				strSQL = "select * from SETT_ACCOUNT,SETT_ACCOUNTBANK where ID = " + lAccountID + " and SETT_ACCOUNT.ID = SETT_ACCOUNTBANK.NACCOUNTID(+) ";
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SNAME"));
					info.setBankNo(rs.getString("SBANKACCOUNTNO"));
					info.setAccountNo(rs.getString("SACCOUNTNO"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.info(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	
	/**
	 * 根据账户ID得到账户信息
	 * 
	 * @param  AccountID,TransTypeID
	 *           
	 * @return PayerOrPayeeInfo
	 * @throws RemoteException
	 * @throws IRollbackException
	 */
	/* 作废
	public PayerOrPayeeInfo getPayerOrPayeeInfoByIDForCurrent(long lAccountID, long lRemitType, long lPayerOrPayee) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PayerOrPayeeInfo info = new PayerOrPayeeInfo();
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			if ((lRemitType == OBConstant.SettRemitType.BANKPAY||lRemitType == OBConstant.SettRemitType.BANKPAY_DOWNTRANSFER||lRemitType == OBConstant.SettRemitType.FINCOMPANYPAY||lRemitType == OBConstant.SettRemitType.PAYSUBACCOUNT) && lPayerOrPayee == OBConstant.PayerOrPayee.PAYEE)
			{
				strSQL = "select * from OB_PAYEEINFO where ID = " + lAccountID;
				log4j.info("strSQL=" + strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SPAYEENAME"));
					info.setAccountNo(rs.getString("SPAYEEACCTNO"));
					info.setBankName(rs.getString("SPAYEEBANKNAME"));
					info.setProv(rs.getString("SPAYEEPROV"));
					info.setCity(rs.getString("SPAYEECITY"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			else if (lRemitType == OBConstant.SettRemitType.SELF && lPayerOrPayee == OBConstant.PayerOrPayee.PAYEE)
			{
				strSQL = "select obacct.* from OB_PAYEEINFO obacct,SETT_ACCOUNT seacct where obacct.spayeeacctno=seacct.saccountno(+) and seacct.ID = " + lAccountID;
				log4j.info("strSQL=" + strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SPAYEENAME"));
					info.setAccountNo(rs.getString("SPAYEEACCTNO"));
					//info.setBankName(rs.getString("SPAYEEBANKNAME"));
					info.setBankName(Env.getClientName());
					info.setProv(rs.getString("SPAYEEPROV"));
					info.setCity(rs.getString("SPAYEECITY"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
			else
			{
				strSQL = "select * from SETT_ACCOUNT,SETT_ACCOUNTBANK where ID = " + lAccountID + " and SETT_ACCOUNT.ID = SETT_ACCOUNTBANK.NACCOUNTID(+) ";
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					info = new PayerOrPayeeInfo();
					info.setAccountName(rs.getString("SNAME"));
					info.setBankNo(rs.getString("SBANKACCOUNTNO"));
					info.setAccountNo(rs.getString("SACCOUNTNO"));
					info.setClientID(rs.getLong("NCLIENTID"));
					info.setCurrencyID(rs.getLong("NCURRENCYID"));
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.info(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;
	}
	*/
	/**
	* 根据签认人ID，获取该签认人的签认金额最小值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMinSignAmount(long lUserID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT nSignuserid,mAmount ";
			strSQL += " FROM OB_SignAmount  ";
			strSQL += " WHERE nclientid = " + lClientID;
			strSQL += " AND ncurrencyid = " + lCurrencyID;
			strSQL += " AND nSignuserid = " + lUserID;
			strSQL += " ORDER BY mAmount ";
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble("mAmount");
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dReturn;
	}
	/**
	* 根据签认人ID，获取该签认人的签认金额最大值
	* Create Date: 2003-8-13
	* @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	* @return double 签认金额
	* @exception Exception
	*/
	public double getMaxSignAmount(long lUserID, long lClientID, long lCurrencyID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		double dReturn = -1.0;
		double dMinAmount = this.getMinSignAmount(lUserID, lClientID, lCurrencyID);
		if (dMinAmount >= 0.0)
		{
			try
			{
				con = Database.getConnection();
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM OB_SignAmount  ";
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " 	AND ncurrencyid = " + lCurrencyID;
				//strSQL += " AND nSignuserid = " + lUserID;
				strSQL += " AND mAmount > " + dMinAmount;
				log4j.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
				else
				{
					dReturn = 9999999999999999.999999;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}
		return dReturn;
	}
	/**
	 * 根据签认人ID，获取该签认人的签认金额最小值(区分签认人)
	 * Create Date: 2003-8-13
	 * @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	 * @return double 签认金额
	 * @exception Exception
	 */
	public double getMinSignAmountDist(long lUserID, long lClientID, long lCurrencyID ,String type) throws Exception
	{
		double dReturn = -1.0;
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int i = 0;
			try
			{
				con = Database.getConnection();
				String signTable = "OB_SignAmount";
				if(type.equals("current")||type.equals(String.valueOf(OBConstant.TransTypeSet.EXTERNALTRANSFER))||type.equals(String.valueOf(OBConstant.TransTypeSet.INTERNALTRANSFER))){
					signTable = "OB_SignAmount_Curr";
				}else{
					signTable = "OB_SignAmount_Fix";
				}
				String strSQL = "";
				strSQL = " SELECT nSignuserid,mAmount ";
				strSQL += " FROM "+signTable;
				strSQL += " WHERE nclientid = " + lClientID;
				strSQL += " AND ncurrencyid = " + lCurrencyID;
				strSQL += " AND nSignuserid = " + lUserID;
				strSQL += " ORDER BY mAmount ";
				log4j.info(strSQL);
				ps = con.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					dReturn = rs.getDouble("mAmount");
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
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
					log4j.error(e.toString());
					throw new IException("Gen_E001");
				}
			}
		}else{
			dReturn = getMinSignAmount(lUserID, lClientID, lCurrencyID);
		}
		return dReturn;
	}
	/**
	 * 根据签认人ID，获取该签认人的签认金额最大值(区分签认人)
	 * Create Date: 2003-8-13
	 * @param lUserID  签认人ID，lClientID 客户ID,lCurrencyID 币种ID
	 * @return double 签认金额
	 * @exception Exception
	 */
	public double getMaxSignAmountDist(long lUserID, long lClientID, long lCurrencyID ,String type) throws Exception
	{
		double dReturn = -1.0;
		if(Config.getBoolean(ConfigConstant.EBANK_SIGN_DISTINCT,false)){
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			int i = 0;
			double dMinAmount = this.getMinSignAmountDist(lUserID, lClientID, lCurrencyID, type);
			if (dMinAmount >= 0.0)
			{
				try
				{
					con = Database.getConnection();
					String signTable = "OB_SignAmount";
					if(type.equals("current")||type.equals(String.valueOf(OBConstant.TransTypeSet.EXTERNALTRANSFER))||type.equals(String.valueOf(OBConstant.TransTypeSet.INTERNALTRANSFER))){
						signTable = "OB_SignAmount_Curr";
					}else{
						signTable = "OB_SignAmount_Fix";
					}
					String strSQL = "";
					strSQL = " SELECT nSignuserid,mAmount ";
					strSQL += " FROM "+signTable;
					strSQL += " WHERE nclientid = " + lClientID;
					strSQL += " 	AND ncurrencyid = " + lCurrencyID;
					//strSQL += " AND nSignuserid = " + lUserID;
					strSQL += " AND mAmount > " + dMinAmount;
					log4j.info(strSQL);
					ps = con.prepareStatement(strSQL);
					rs = ps.executeQuery();
					if (rs != null && rs.next())
					{
						dReturn = rs.getDouble("mAmount");
					}
					else
					{
						dReturn = 9999999999999999.999999;
					}
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
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
						log4j.error(e.toString());
						throw new IException("Gen_E001");
					}
				}
			}
		}else{
			dReturn = getMaxSignAmount(lUserID, lClientID, lCurrencyID);
		}
		return dReturn;
	}

	/**
		 * 检查是否能操作下属单位,
		 * Create Date: 2003-8-13
		 * @param lClientID 
		 * @return boolean 1-能操作 0-不能操作
		 * @exception Exception
		 */
	public long getIsControlChild(long lClientID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		long lResult = 0;
		try
		{
			con = Database.getConnection();
			sql = " SELECT sCode FROM client";
			sql += " WHERE ID = " + lClientID;
			sql += " AND sCode IN ('01-0003','01-0021','01-0020','01-0001','01-0002')";
			log4j.info(" SQL = " + sql);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lResult = 1;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
		return lResult;
	}

	/**
			 * 检查是否能操作下属单位,
			 * Create Date: 2003-8-13
			 * @param lClientID 
			 * @return boolean 1-能操作 0-不能操作
			 * @exception Exception
			 */
	public long getIsControlChild(String sClientNo) throws Exception
	{
		long lResult = 0;
		String[] strNo = { "01-0003", "01-0021", "01-0020", "01-0001", "01-0002" };
		try
		{
			for (int i = 0; i < strNo.length; i++)
			{
				if (strNo[i].equals(sClientNo))
				{
					lResult = 1;
				}
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return lResult;
	}

	public static void main(String[] args)
	{
		try
		{
			OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			FinanceInfo financeInfo = new FinanceInfo();
			financeInfo.setClientID(71);
			financeInfo.setCurrencyID(1);
			financeInfo.setID(20060420010100004L);
			log4j.print(obFinanceInstrDao.getSettInfo(20060416010100009l));
			/*SubLoanAccountDetailInfo subLoanAccountDetailInfo = new SubLoanAccountDetailInfo();
			subLoanAccountDetailInfo.setOfficeID(1);
			subLoanAccountDetailInfo.setCurrencyID(1);
			subLoanAccountDetailInfo.setLoanNoteID(28);
			subLoanAccountDetailInfo.setLoanAccountID(292);
			subLoanAccountDetailInfo.setInterestStart(DataFormat.getDateTime(" 2004-02-29"));
			subLoanAccountDetailInfo = obFinanceInstrDao.findSubLoanAccountDetailByCondition(subLoanAccountDetailInfo);
			if (subLoanAccountDetailInfo != null)
			{
				log4j.info("========interest====");
				log4j.info(subLoanAccountDetailInfo.getInterest() + "");
				//subLoanAccountDetailInfo.getCompoundInterest();
				log4j.info("========interest!====");
			}
			*/
			/*OBFinanceInstrDao obFinanceInstrDao = new OBFinanceInstrDao();
			FinanceInfo financeInfo = null;
			financeInfo = obFinanceInstrDao.findByID(1, 1, 1);
			if (financeInfo != null)
			{
				log4j.info("not null!");
				log4j.info(financeInfo.getNote() + "");
			}*/
			/*	FinanceInfo info = new FinanceInfo();
				info = obFinanceInstrDao.findByID(1, 1, 1);
			
				if (info != null)
				{
					info.setInterestIncome(0.25);
					if (obFinanceInstrDao.add(info) > 0)
					{
						log4j.info("===ok==");
					}
				}
				*/
			/*FinanceInfo info = null;
			info = obFinanceInstrDao.findByID(1, 1, 1);
			if (info != null)
			{
				log4j.info("===not null==");
				log4j.info("=====:" + info.getCommissionStart());
			}
			info.setCommission(0.998);
			info.setRealInterestReceiveable(0.77);
			info.setRealInterestIncome(0.33);
			info.setInterestReceiveable(0.12);
			info.setInterestTax(0.56);
			info.setRealInterestTax(0.23);
			info.setInterestTax(0.37);
			info.setInterestIncome(100);
			if (obFinanceInstrDao.update(info) > 0)
			{
				log4j.info("===update success==");
			}
			*/
			long a=1;
			//obFinanceInstrDao.findByID( a );
			ClientAccountInfo accountInfo = 
				obFinanceInstrDao.findAccountInfoByClient(291, 1, -1, 1);
			if (accountInfo!=null)
			{
				log4j.print("payAccountNo="+accountInfo.payAccountNo);
				log4j.print("receiveAccountNo="+accountInfo.receiveAccountNo);
				log4j.print("receiveAccountName="+accountInfo.receiveAccountName);
				log4j.print("payAccountID="+accountInfo.payAccountID);
				log4j.print("getBalance="+accountInfo.getBalance());
				log4j.print("getCanUseBalance="+accountInfo.getCanUseBalance());
				log4j.print("getLimitAmount="+accountInfo.getLimitAmount());
			}
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param lID  指令号
	 * @param msg  错误信息
	 * @throws Exception
	 */
	public void setReturnMsg(long lID,String msg) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		int nIndex=1;
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append("UPDATE OB_FinanceInstr SET sReturnMsg = ? where ID=?");
			log4j.info(sb.toString());
			ps = con.prepareStatement(sb.toString());
			nIndex = 1;
			ps.setString(nIndex++, msg);
			ps.setLong(nIndex++, lID);
			ps.executeUpdate();
			
			// 关闭数据库资源
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
	}

	public ClientAccountInfo findAccountInfoByClient(long lUserID,long lClientID,long accountID,long lCurrencyID) throws Exception
	{
		ClientAccountInfo info = null;
		String sql="";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			conn=Database.getConnection() ;
			
			 sql = " select distinct b.saccountno ,b.ID as nAccountID "
		         + " from SETT_account b ,OB_AccountOwnedByUser oba"
		         + " where oba.sAccountNo=b.sAccountNo "
				 +" and b.nAccountTypeID=1 "
		         + " and b.nStatusID=1 "
		         + " and b.nCheckStatusID =4 " ;
			 if (accountID>0)
			 {
			 	sql+="and b.ID="+accountID;
			 }
			 else
			 {
			 	sql = sql+" and oba.nUserID="+lUserID+" and b.nclientid ="+lClientID;
				if (lCurrencyID>0)
					sql+="  and b.ncurrencyid ="+lCurrencyID;
			 }
			 
			 sql += " order by b.saccountno  "; 
			 log4j.print(sql);
			ps=conn.prepareStatement( sql);
			rs=ps.executeQuery() ;
			if (rs.next() )
			{
				info=new ClientAccountInfo();
				info.setPayAccountID( rs.getLong("nAccountID"));
				info.setPayAccountNo( rs.getString("sAccountNo"));
				Sett_FilialeAccountSetDAO biz = new Sett_FilialeAccountSetDAO();
				FilialeAccountInfo[] bInfo = biz.findRefFilialeAccountInfoBySettAccountId( info.getPayAccountID() );
				if (bInfo!=null && bInfo.length ==1)
				{	
					double dCPF2Amount = 0;
					
					info.setReceiveAccountNo( bInfo[0].getBankAccountNo() );
					info.setReceiveAccountName( bInfo[0].getBankAccountName() );
					AccountBean bean = new AccountBean();
					long subAccountID=bean.getCurrentSubAccoutIDByAccoutID(info.getPayAccountID() );
					log4j.print("subAccountID"+subAccountID);
					SubAccountAssemblerInfo assInfo=bean.getBalanceBySubAccountID( info.getPayAccountID(),subAccountID);
					log4j.print("assInfo:"+assInfo);
					if (assInfo!=null)
					{
						SubAccountCurrentInfo subInfo=assInfo.getSubAccountCurrenctInfo() ;
						log4j.print("accountInfo:"+assInfo);
						
						dCPF2Amount = 0;
						
						if (subInfo!=null)
						{
							info.setBalance( subInfo.getBalance() );
							
							dCPF2Amount = getUsableBalanceByAccountID(info.getPayAccountID(), lCurrencyID, -1);
							
							//info.setCanUseBalance( subInfo.getBalance() -subInfo.getDailyUncheckAmount() -  );
							info.setCanUseBalance( subInfo.getBalance() -  dCPF2Amount);
							log4j.print("dCPF2Amount:"+dCPF2Amount);
							double limitAmount=0;
							if (subInfo.getFirstLimitTypeID()==SETTConstant.AccountOverDraftType.ALL )
							{
								limitAmount=subInfo.getFirstLimitAmount() ;
							}
							else if (subInfo.getSecondLimitTypeID() ==SETTConstant.AccountOverDraftType.ALL)
							{
								limitAmount=subInfo.getSecondLimitAmount() ;
							}
							else if (subInfo.getThirdLimitTypeID() ==SETTConstant.AccountOverDraftType.ALL)
							{
								limitAmount=subInfo.getThirdLimitAmount() ;
							}
							info.setLimitAmount( limitAmount );
						}
					}
					
					info.setCanUseBalance(info.getLimitAmount()+ info.getBalance()- dCPF2Amount);
				}
			}
			if (rs!=null)
			{
				rs.close();
				rs=null;
			}
			if (ps!=null)
			{
				ps.close() ;
				ps=null;
			}
			if (conn!=null)
			{
				conn.close() ;
				conn=null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs!=null)
				{
					rs.close();
					rs=null;
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}

		return info;
	}
	
	/**
	 * 换开定期存单：返回符合条件的值
	 * @return Collection
	 * @throws Exception
	 */
	public Collection getTransOpenFixdDePosit(FinanceInfo financeInfo) throws Exception
	{
		FinanceInfo info = null;
		
		StringBuffer strSQL = new StringBuffer();
		ArrayList col = new ArrayList();				
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			strSQL.append(" select * \n");
			strSQL.append(" from OB_FinanceInStr  \n");
			strSQL.append(" where \n");
			strSQL.append("  (ndepositbillstatusid is null \n");
			strSQL.append("  or ndepositbillstatusid =" + OBConstant.SettInstrStatus.DELETE + ") \n");		
			//strSQL.append(" and b.id=a.npayeeacctid \n");
			//strSQL.append(" and c.id=a.npayeracctid \n");
			strSQL.append(" and ntranstype=" + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n");
			strSQL.append(" and nstatus=" + OBConstant.SettInstrStatus.FINISH + " ");
			strSQL.append(" and nclientid=" + financeInfo.getClientID());
			if(!financeInfo.getOrderByCode().equals(""))
			{
				if(financeInfo.getOrderByCode().equals("id"))
					strSQL.append(" order by id");
				else
					strSQL.append(" order by " + financeInfo.getOrderByCode());
			}
			log4j.print("strSQL="+strSQL.toString());
			conn=Database.getConnection() ;
			ps=conn.prepareStatement(strSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new FinanceInfo();
				info.setID(rs.getLong("ID")); // 指令序号
				info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID	
				//info.setPayerAcctNo(rs.getString("payerno"));//付款方账号
				//info.setPayerName(rs.getString("payername"));//付款方名称 
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				//info.setPayeeAcctNo(rs.getString("payeeno"));//收款方账号
				//info.setPayeeName(rs.getString("payeename"));//收款方名称 
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
				info.setContractID(rs.getLong("NContractID")); // 贷款合同ID
				info.setLoanNoteID(rs.getLong("NLoanNoteID")); //放款通知单ID
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setSubAccountID(rs.getLong("nSubAccountID")); //子账户ID
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setClearInterest(rs.getTimestamp("dtClearInterest")); //结息日
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setNoticeDay(rs.getLong("nnoticeday")); //通知存款品种
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(rs.getString("CPF_STRANSNO")); //CPF-交易号
				info.setFinishDate(rs.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
				info.setReject(rs.getString("CPF_SREJECT")); //CPF-拒绝原因
				info.setIsCanAccept(rs.getLong("NISCANACCEPT")); //能否接受
				info.setDefaultTransType(rs.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户
				info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //结息日期
				info.setInterestStart(rs.getTimestamp("DTINTERESTSTART"));
				info.setCompoundStart(rs.getTimestamp("DTCOMPOUNDINTERESTSTART"));
				info.setCompoundRate(rs.getDouble("MCOMPOUNDRATE"));
				info.setOverDueStart(rs.getTimestamp("DTOVERDUESTART"));
				info.setOverDueRate(rs.getDouble("MOVERDUERATE"));
				info.setSuretyStart(rs.getTimestamp("DTSURETYFEESTART"));
				info.setSuretyRate(rs.getDouble("MSURETYFEERATE"));
				info.setCommissionStart(rs.getTimestamp("DTCOMMISSIONSTART"));
				info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
				info.setInterestRate(rs.getDouble("MLOANREPAYMENTRATE"));
				info.setCompoundAmount(rs.getDouble("MCOMPOUNDAMOUNT"));
				info.setOverDueAmount(rs.getDouble("MOVERDUEAMOUNT"));
				info.setInterestReceiveable(rs.getDouble("MINTERESTRECEIVEABLE"));
				info.setInterestIncome(rs.getDouble("MINTERESTINCOME"));
				info.setRealInterestReceiveable(rs.getDouble("MREALINTERESTRECEIVEABLE"));
				info.setRealInterestIncome(rs.getDouble("MREALINTERESTINCOME"));
				info.setInterestTax(rs.getDouble("MINTERESTTAX"));
				info.setRealInterestTax(rs.getDouble("MREALINTERESTTAX"));
				info.setReturnMsg(rs.getString("sReturnMsg"));
				//modify by lxr for budget 增加 budgetitemID 字段
				info.setBudgetItemID(rs.getLong("budgetItemID"));
				info.setSDepositBillNo(rs.getString("sDepositBillNo"));
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
				//获得收付款方信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
				info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				info.setPayeeProv(payeeInfo.getProv()); // 汇入省
				info.setPayeeCity(payeeInfo.getCity()); // 汇入市
				info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
				info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
				info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
				info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
				info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
				info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
				info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
				col.add(info);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs!=null)
				{
					rs.close();
					rs=null;
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
				log4j.error(e.toString());
			}
		}
		log4j.print(col.size()+"");
		col.addAll(this.getTransOpenFixdDePositForSett(financeInfo));
		return col;		
	}
	
	/**
	 * 查询来自结算的换开定期存单
	 * @param financeInfo
	 * @return
	 * @throws Exception
	 */
	private Collection getTransOpenFixdDePositForSett(FinanceInfo financeInfo) throws Exception
	{
		FinanceInfo info = null;
		
		StringBuffer strSQL = new StringBuffer();
		ArrayList col = new ArrayList();				
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			strSQL.append(" select * \n");
			strSQL.append(" from sett_TransOpenFixedDeposit  \n");
			strSQL.append(" where \n");
			strSQL.append(" ntransactiontypeid=" + SETTConstant.TransactionType.OPENFIXEDDEPOSIT + " \n");
			strSQL.append(" and nstatusid=" + SETTConstant.TransactionStatus.CHECK + " \n");		
			strSQL.append(" and ndepositbillstatusid=-1 \n");
			strSQL.append(" and sinstructionno is null \n");
			strSQL.append(" and nclientid=" + financeInfo.getClientID() + " \n");
			//strSQL.append(" and b.id=a.naccountid \n");
			//strSQL.append(" and c.id=a.ncurrentaccountid  \n");
			if(!financeInfo.getOrderByCode().equals(""))
			{
				if(financeInfo.getOrderByCode().equals("id"))
					strSQL.append(" order by stransno");
				if(financeInfo.getOrderByCode().equals("nFixedDepositTime"))
					strSQL.append(" order by ndepositterm");
				if(financeInfo.getOrderByCode().equals("sNote"))
					strSQL.append(" order by sabstract");
				else
					strSQL.append(" order by " + financeInfo.getOrderByCode());
			}
			log4j.print("strSQL="+strSQL.toString());
			conn=Database.getConnection() ;
			ps=conn.prepareStatement(strSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new FinanceInfo();
				info.setID(rs.getLong("stransno")); // 指令序号
				info.setPayerAcctID(rs.getLong("ncurrentaccountid"));//付款方账户ID
				info.setPayeeAcctID(rs.getLong("naccountid"));//收款方账户ID
				//info.setPayerAcctNo(rs.getString("payerno"));//付款方账号
				//info.setPayerName(rs.getString("payername"));//付款方名称 
				//info.setPayeeAcctNo(rs.getString("payeeno"));//收款方账号
				//info.setPayeeName(rs.getString("payeename"));//收款方名称 
				info.setFixedDepositTime(rs.getLong("ndepositterm"));//定期存款期限（月）
				info.setAmount(rs.getDouble("mamount")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("dtexecute")); // 执行日
				info.setNote(rs.getString("sabstract")); // 汇款用途/摘要
				//info.setRemitType(-1);
				//获得收付款方信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				//info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				//info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				
				col.add(info);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs!=null)
				{
					rs.close();
					rs=null;
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
				log4j.error(e.toString());
			}
		}
		log4j.print(col.size()+"");
		return col;		
	}
	
	/**
	 * 换开定期存单保存、暂存、删除、修改操作
	 * @param financeInfo
	 * @return 修改条数
	 * @throws Exception
	 */
	public long TransOpenFixdDePositUpdtae(FinanceInfo financeInfo) throws Exception
	{		
		StringBuffer strSQL = new StringBuffer();			
		Connection conn=null;
		Statement ps=null;
		int getAmount=0;
		
		try
		{
			strSQL.append(" update ob_FinanceInStr set ndepositbillstatusid=" + financeInfo.getNDepositBillStatusId()+ " \n");
			//if(!financeInfo.getSDepositBillNo().equals(""))
			if(financeInfo.getNDepositBillStatusId() == OBConstant.SettInstrStatus.SAVE)
			{
				/**
				 * 为结算方便接收而加入
				 */
				strSQL.append(" ,sdepositbillno='---' \n");
				if(financeInfo.getNDepositBillInputuserId() != 0 )
					strSQL.append(" ,ndepositbillinputuserid=" + financeInfo.getNDepositBillInputuserId() + " \n");
				if(financeInfo.getDtDepositBillInputdate() != null)
					strSQL.append(" ,dtdepositbillinputdate=sysdate \n");
				if(!financeInfo.getSDepositBillAbstract().equals(""))
					strSQL.append(",sdepositbillabstract='" + financeInfo.getSDepositBillAbstract() + "' \n");
//				if(financeInfo.getNDepositBillCheckuserId() != 0)
//					strSQL.append(" ,ndepositbillcheckuserid=" + financeInfo.getNDepositBillCheckuserId() + " \n");
//				if(financeInfo.getDtDepositBillCheckdate() != null)
//					strSQL.append(" ,dtdepositbillcheckdate=sysdate \n");
//				if(!financeInfo.getSDepositBillCheckAbstract().equals(""))
//					strSQL.append(" ,sdepositbillcheckabstract='" + financeInfo.getSDepositBillCheckAbstract() + "' \n");
			}
			strSQL.append(" where id=" + financeInfo.getID() + " \n");
			log4j.print("SQL=\n" + strSQL.toString());
			conn=Database.getConnection() ;
			ps=conn.createStatement();
			getAmount=ps.executeUpdate(strSQL.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
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
				log4j.error(e.toString());
			}
		}
		if(getAmount ==0)
			getAmount = this.getTransFromSett(financeInfo);
		return getAmount;		
	}
	/**
	 * 把结算的定期开立信息插入到网银的表中，并生成指令号和结算ID号，且回写于结算表中
	 * @param financeInfo
	 * @return
	 */
	private int getTransFromSett(FinanceInfo financeInfo)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int getAmount=0;
		
		long lMaxID = -1;
		StringBuffer eBankSQL = new StringBuffer();
		StringBuffer settSQL = new StringBuffer();
		try
		{
			//调用BizCapital方法，得到最大流水号
			lMaxID = Long.parseLong(OBOperation.createInstrCode(OBConstant.SubModuleType.SETTLEMENT));
			eBankSQL.append("insert into ob_financeinstr \n");
			eBankSQL.append("( \n");
			eBankSQL.append("id,NCLIENTID,NCURRENCYID,NTRANSTYPE,NSTATUS,NISCANACCEPT, \n");
			eBankSQL.append("MAMOUNT,DTEXECUTE,SNOTE,NFIXEDDEPOSITTIME,NCONFIRMUSERID,DTCONFIRM,\n" 
					+ "NCHECKUSERID,CPF_NOFFICEID,CPF_NDEFAULTTRANSTYPE,CPF_NDEALUSERID,CPF_DTDEAL,\n"
					+ "CPF_STRANSNO,CPF_DTFINISH,NPAYERACCTID,NPAYEEACCTID,SETTFINID \n");
			eBankSQL.append(",NDEPOSITBILLSTATUSID,SDEPOSITBILLNO,NDEPOSITBILLINPUTUSERID,DTDEPOSITBILLINPUTDATE,SDEPOSITBILLABSTRACT \n");
			eBankSQL.append(" )  \n");
			eBankSQL.append(" values( \n");
			eBankSQL.append(lMaxID);
			eBankSQL.append("," + financeInfo.getClientID());
			eBankSQL.append("," + financeInfo.getCurrencyID());
			eBankSQL.append("," + OBConstant.SettInstrType.OPENFIXDEPOSIT);
			eBankSQL.append("," + OBConstant.SettInstrStatus.FINISH);
			eBankSQL.append(",2 \n");
			eBankSQL.append(this.getSettInfo(financeInfo.getID()));
			eBankSQL.append("," + financeInfo.getNDepositBillStatusId());
			eBankSQL.append(",'---'");
			eBankSQL.append("," + financeInfo.getNDepositBillInputuserId());
			eBankSQL.append(",to_date('" + financeInfo.getDtDepositBillInputdate().toString().substring(0,10) + "','yyyy-mm-dd')");
			eBankSQL.append(",'" + financeInfo.getSDepositBillAbstract() + "'");
			eBankSQL.append(") \n");
			
			log4j.print("eBankSQL=\n" + eBankSQL.toString());
			
			con = Database.getConnection();
			ps = con.prepareStatement(eBankSQL.toString());
			getAmount = ps.executeUpdate();
			if(getAmount>0)
			{
				try
				{
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
					settSQL.append("update sett_TransOpenFixedDeposit set SINSTRUCTIONNO='" + lMaxID + "' where STRANSNO='" + financeInfo.getID() + "'");
					
					log4j.print("settSQL=\n" + settSQL.toString());
					
					con = Database.getConnection();
					ps = con.prepareStatement(settSQL.toString());
					getAmount = ps.executeUpdate();
				}
				catch (Exception e)
				{
					log4j.error(e.toString());
				}
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
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
				log4j.error(e.toString());
			}
		}
		return getAmount;
	}
	
	/**
	 * 得到结算的相关信息, 用以加入插入到网银指令表
	 * @param id
	 */
	private String getSettInfo(long id)
	{	
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strInfo = new StringBuffer();
		
		try
		{
			strSQL.append("select MAMOUNT,DTEXECUTE,SABSTRACT,NDEPOSITTERM,NINPUTUSERID,DTINPUT,\n"
					+ "NCHECKUSERID,NOFFICEID,NTRANSACTIONTYPEID,NOFFICEID,DTINTERESTSTART,STRANSNO,\n"
					+ "DTEXECUTE,ncurrentaccountid,naccountid,id \n");
			strSQL.append("from sett_TransOpenFixedDeposit where STRANSNO='" + id + "' \n");
			
			log4j.print("getSettInfo(long)'s strSQL=\n" + strSQL.toString());
			
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				strInfo.append("," + new Double(rs.getDouble("MAMOUNT")).toString());
				strInfo.append(",to_date('" + rs.getTimestamp("DTEXECUTE").toString().substring(0,10) + "','yyyy-mm-dd') \n");
				if(rs.getString("SABSTRACT") != null)
					strInfo.append(",'" + rs.getString("SABSTRACT") + "'");
				else
					strInfo.append(",''");
				//strInfo.append(",'" + rs.getString("SABSTRACT") != null ? rs.getString("SABSTRACT") : "" + "'");
				strInfo.append("," + new Long(rs.getLong("NDEPOSITTERM")).toString());
				strInfo.append("," + new Long(rs.getLong("NINPUTUSERID")).toString());
				strInfo.append(",to_date('" + rs.getTimestamp("DTINPUT").toString().substring(0,10) + "','yyyy-mm-dd') \n");
				strInfo.append("," + new Long(rs.getLong("NCHECKUSERID")).toString());
				strInfo.append("," + new Long(rs.getLong("NOFFICEID")).toString());
				strInfo.append("," + new Long(rs.getLong("NTRANSACTIONTYPEID")).toString());
				strInfo.append("," + new Long(rs.getLong("NOFFICEID")).toString());
				strInfo.append(",to_date('" + rs.getTimestamp("DTINTERESTSTART").toString().substring(0,10) + "','yyyy-mm-dd') \n");
				strInfo.append(",'" + rs.getString("STRANSNO") + "'");
				strInfo.append(",to_date('" + rs.getTimestamp("DTEXECUTE").toString().substring(0,10) + "','yyyy-mm-dd') \n");
				strInfo.append("," + new Long(rs.getLong("ncurrentaccountid")).toString());
				strInfo.append("," + new Long(rs.getLong("naccountid")).toString());
				strInfo.append("," + new Long(rs.getLong("id")).toString());
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if(rs !=null)
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
				log4j.error(e.toString());
			}
		}
		return strInfo.toString();		
	}
	/**
	 * 换开定期存单复核查询操作
	 * @param financeInfo
	 * @return ID
	 * @throws Exception
	 */
	public Collection isTransOpenFixdDePosit(FinanceInfo financeInfo) throws Exception
	{		
		FinanceInfo info = null;
		
		ArrayList col = new ArrayList();
		StringBuffer strSQL = new StringBuffer();					
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		//long id = -1;
		
		try
		{
			strSQL.append(" select * \n");
			strSQL.append(" from ob_financeinstr where \n ");
			strSQL.append(" nclientid=" + financeInfo.getClientID() + " \n ");
			//strSQL.append(" and a.sdepositbillno='" + financeInfo.getSDepositBillNo() + "' \n ");
			strSQL.append(" and npayeracctid=" + financeInfo.getPayerAcctID() + " \n ");
			strSQL.append(" and nfixeddeposittime=" + financeInfo.getFixedDepositTime() + " \n ");
			strSQL.append(" and mamount=" + financeInfo.getAmount() + " \n ");
			strSQL.append(" and dtexecute=to_date('" + financeInfo.getExecuteDate().toString().substring(0,10) + "','yyyy-mm-dd') \n ");
			//strSQL.append(" and a.npayeracctid=b.id \n ");
			//strSQL.append(" and a.npayeeacctid=c.id \n ");
			strSQL.append(" and ndepositbillstatusid is not null \n ");
			strSQL.append(" and ndepositbillstatusid=" + OBConstant.SettInstrStatus.SAVE + " \n ");
			strSQL.append(" and ndepositbillinputuserid <> " + financeInfo.getUserID());
			log4j.print("换开定期存单复核查询操作SQL=\n" + strSQL.toString());
			conn=Database.getConnection() ;
			ps=conn.prepareStatement(strSQL.toString());
			rs=ps.executeQuery();
			while(rs.next())
			{
				info = new FinanceInfo();
				info.setID(rs.getLong("ID")); // 指令序号
				info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID	
				//info.setPayerAcctNo(rs.getString("payerno"));//付款方账号
				//info.setPayerName(rs.getString("payername"));//付款方名称 
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				//info.setPayeeAcctNo(rs.getString("payeeno"));//收款方账号
				//info.setPayeeName(rs.getString("payeename"));//收款方名称 
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
				info.setContractID(rs.getLong("NContractID")); // 贷款合同ID
				info.setLoanNoteID(rs.getLong("NLoanNoteID")); //放款通知单ID
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setSubAccountID(rs.getLong("nSubAccountID")); //子账户ID
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setClearInterest(rs.getTimestamp("dtClearInterest")); //结息日
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setNoticeDay(rs.getLong("nnoticeday")); //通知存款品种
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(rs.getString("CPF_STRANSNO")); //CPF-交易号
				info.setFinishDate(rs.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
				info.setReject(rs.getString("CPF_SREJECT")); //CPF-拒绝原因
				info.setIsCanAccept(rs.getLong("NISCANACCEPT")); //能否接受
				info.setDefaultTransType(rs.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户
				info.setClearInterest(rs.getTimestamp("DTCLEARINTEREST")); //结息日期
				info.setInterestStart(rs.getTimestamp("DTINTERESTSTART"));
				info.setCompoundStart(rs.getTimestamp("DTCOMPOUNDINTERESTSTART"));
				info.setCompoundRate(rs.getDouble("MCOMPOUNDRATE"));
				info.setOverDueStart(rs.getTimestamp("DTOVERDUESTART"));
				info.setOverDueRate(rs.getDouble("MOVERDUERATE"));
				info.setSuretyStart(rs.getTimestamp("DTSURETYFEESTART"));
				info.setSuretyRate(rs.getDouble("MSURETYFEERATE"));
				info.setCommissionStart(rs.getTimestamp("DTCOMMISSIONSTART"));
				info.setCommissionRate(rs.getDouble("MCOMMISSIONRATE"));
				info.setInterestRate(rs.getDouble("MLOANREPAYMENTRATE"));
				info.setCompoundAmount(rs.getDouble("MCOMPOUNDAMOUNT"));
				info.setOverDueAmount(rs.getDouble("MOVERDUEAMOUNT"));
				info.setInterestReceiveable(rs.getDouble("MINTERESTRECEIVEABLE"));
				info.setInterestIncome(rs.getDouble("MINTERESTINCOME"));
				info.setRealInterestReceiveable(rs.getDouble("MREALINTERESTRECEIVEABLE"));
				info.setRealInterestIncome(rs.getDouble("MREALINTERESTINCOME"));
				info.setInterestTax(rs.getDouble("MINTERESTTAX"));
				info.setRealInterestTax(rs.getDouble("MREALINTERESTTAX"));
				info.setReturnMsg(rs.getString("sReturnMsg"));
				//modify by lxr for budget 增加 budgetitemID 字段
				info.setBudgetItemID(rs.getLong("budgetItemID"));
				info.setSDepositBillNo(rs.getString("sDepositBillNo"));
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
				//获得收付款方信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
				info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				info.setPayeeProv(payeeInfo.getProv()); // 汇入省
				info.setPayeeCity(payeeInfo.getCity()); // 汇入市
				info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
				info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
				info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
				info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
				info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
				info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
				info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
				col.add(info);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if(rs !=null)
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
				log4j.error(e.toString());
			}
		}
		return col;		
	}
	/**
	 * 换开定期存单复核
	 * @param instructionID
	 * @param checkUserID
	 * @return
	 * @throws Exception 
	 */
	public long billcheck(long lInstructionID, long lCheckUserID,String sdepositbillcheckabstract,boolean cancelCheck) throws Exception
	{		//检查此交易指令在复核前是否被修改或删除,只有确认的指令才能被复核
		boolean bStatus = false;
		Connection con = null;
		PreparedStatement ps = null;
		long lConfirmUserIDTest=-1;
		ResultSet rs = null;
		long Status = -1;
		try
		{
			//判断是否被删除
			bStatus = isDelete(lInstructionID);
			if (bStatus)
			{
				throw new IException("OB_EC13");
			}
			//登录人是否为确认人
			if (lCheckUserID == lConfirmUserIDTest)
			{
				log4j.info("复核人不能为确认人");
				
				throw new IException("OB_EC14");
			}
			if(this.getStatus(lInstructionID) == OBConstant.SettInstrStatus.FINISH)
			{
				if(!cancelCheck)
					Status = updateStatus(lInstructionID, OBConstant.SettInstrStatus.FINISH, lCheckUserID, "billcheck" + sdepositbillcheckabstract);
				if(cancelCheck)
					Status = updateStatus(lInstructionID, OBConstant.SettInstrStatus.FINISH, lCheckUserID, "billcancelCheck" + sdepositbillcheckabstract);
			}else
			{
				log4j.info("这个业务不为换开定期存单");
			}
		}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return Status;
	}
	/**
	 * 判断是否换开定期开立
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long isBill(long id) throws Exception
	{
		boolean bStatus = false;
		Connection con = null;
		PreparedStatement ps = null;
		long lStatusTest = -1;
		ResultSet rs = null;
		try
		{
			//判断是否被删除
			bStatus = isDelete(id);
			if (bStatus)
			{
				throw new IException("OB_EC13");
			}
			//add by sxyao@isoftstone.com 2003-01-24
			//判断用户是否有此项操作的权力
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			sbSQL.append("SELECT ndepositbillstatusid FROM ob_financeinstr WHERE id=?");
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lStatusTest = rs.getLong("ndepositbillstatusid");
			}
			else
			{	
				throw new IException("Gen_E003");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;}
		catch (IException ie)
		{
			log4j.error(ie.toString());
			throw ie;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
			}
		}
		return lStatusTest;		
	}

	/**
	 * 根据财务交易指令ID，查询指令信息(换开定期存单) Create Date: 2006-5-9
	 * @param instructionID ID号
	 * @param lClientID 客户
	 * @param lCurrencyID 币种
	 * @return
	 */
	public FinanceInfo findByIDForSett(long instructionID, long lClientID,long lCurrencyID) throws Exception
	{
		FinanceInfo info = null;
		
		StringBuffer strSQL = new StringBuffer();
		ArrayList col = new ArrayList();				
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try
		{
			strSQL.append(" select * \n");
			strSQL.append(" from sett_TransOpenFixedDeposit  \n");
			strSQL.append(" where \n");
			strSQL.append("nclientid=" + lClientID + " \n");
			//strSQL.append(" and b.id=a.naccountid \n");
			//strSQL.append(" and c.id=a.ncurrentaccountid  \n");
			strSQL.append(" and stransno=" + instructionID + "  \n");
			
			log4j.print("根据财务交易指令ID，查询指令信息(换开定期存单)SQL=\n" + strSQL.toString());

			conn = Database.getConnection();	
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			if (rs.next())
			{
				info = new FinanceInfo();
				info.setID(rs.getLong("stransno")); // 指令序号
				info.setPayerAcctID(rs.getLong("ncurrentaccountid")); // 付款方账户ID	
				//info.setPayerAcctNo(rs.getString("payerno"));//付款方账号
				//info.setPayerName(rs.getString("payername"));//付款方名称 	
				info.setPayeeAcctID(rs.getLong("naccountid")); //收款方账户ID
				//info.setPayeeAcctNo(rs.getString("payeeno"));//收款方账号
				//info.setPayeeName(rs.getString("payeename"));//收款方名称 		
				info.setFixedDepositTime(rs.getLong("ndepositterm"));//定期存款期限（月）
				info.setAmount(rs.getDouble("mamount")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("dtexecute")); // 执行日
				info.setNote(rs.getString("sabstract")); // 汇款用途/摘要
				info.setRemitType(-1);
				// 获得账户的当前余额 
				AccountBalanceInfo abInfo = new AccountBalanceInfo();
				abInfo = getCurrBalanceByAccountID(info.getPayerAcctID(), lCurrencyID, instructionID);
				info.setCurrentBalance(abInfo.getCurrentBalance());
				info.setIsCycleAccount(abInfo.getIsCycleAccount());
				info.setMaxUsableAmount(abInfo.getMaxUsableAmount());
				info.setOverdraftAmount(abInfo.getOverdraftAmount());
				info.setUsableBalance(abInfo.getUsableBalance());
				// 获得收付方信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
				info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				info.setPayeeProv(payeeInfo.getProv()); // 汇入省
				info.setPayeeCity(payeeInfo.getCity()); // 汇入市
				info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
				info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
				info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
				info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
				info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
				info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
				info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
			}
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try
			{
				if (rs!=null)
				{
					rs.close();
					rs=null;
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
				log4j.error(e.toString());
			}
		}
		log4j.print(col.size()+"");
		return info;		
	}

    /**
     * 校验指令申请中的业务申请编号是否重复
     * 如果重复，抛出RepeatedApplyCodeException异常
     * 
     * @param financeInfo 
     * @throws RepeatedApplyCodeException 业务编号重复，抛出此异常
     * @throws Exception
     */
    public void CheckApplyCodeRepetition(FinanceInfo financeInfo) 
        throws RepeatedApplyCodeException,IException 
    {
        initDAO();
        StringBuffer sbSQL = new StringBuffer();
        String applyCode = null;
        String strDate = "";
        long lReturn = -1;
        try
        {
            sbSQL.append(" SELECT SAPPLYCODE FROM OB_FinanceInstr WHERE 1=1 \n");
            sbSQL.append("   and (SAPPLYCODE is not null and SAPPLYCODE like '" + financeInfo.getApplyCode() + "') \n");
            sbSQL.append("   and ( NSTATUS!= " + OBConstant.SettInstrStatus.DELETE 
                                + " and NSTATUS != " + OBConstant.SettInstrStatus.REFUSE + ") \n");

            if (financeInfo.getID() > 0)
            {
                sbSQL.append("   and ID != " + financeInfo.getID());
            }
            
            prepareStatement(sbSQL.toString());
            ResultSet resultSet = executeQuery();
            
            if(resultSet.next())
            {
               throw new RepeatedApplyCodeException();
            }
            
        }catch(RepeatedApplyCodeException ex1){
            throw ex1;
        }catch(Exception ex2){
            throw new ITreasuryDAOException("判断业务申请编号时出错，" + ex2.getMessage(), ex2);
        }finally{
            finalizeDAO();
        }
    }
    
    
    
    /********************************************************************************************************************
     * modify by leiyang 20081202
     * 网上银行逐笔付款优化
     */
    
	/**
	 * 根据财务交易指令ID，查询指令信息
	 * Create Date: 2003-8-13
	 * @param lInstructionID 财务交易指令ID
	 * @param lUserID 登录人ID
	 * @param lCurrencyID 币种ID
	 * @return FinanceInfo
	 * @exception Exception
	 */
	public FinanceInfo findByInstructionID(long lInstructionID, long lCurrencyID)
		throws ITreasuryDAOException
	{
		FinanceInfo info = null;
		StringBuffer sbSQL = null;
		StringBuffer sbPayerSQL = null;
		StringBuffer sbPayeeSQL = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/
	        
	        try{
	        	//付款方信息
	        	sbPayerSQL = new StringBuffer();
	        	sbPayerSQL.append(" SELECT account_ab.ID, account_ab.SACCOUNTNO, account_ab.SNAME, account_ab.SDEPOSITNO, nvl(account_ab.mBalance, 0) - nvl(account_ab.muncheckpaymentamount, 0) mCurrBalance, nvl(nvl(account_ab.mBalance, 0) - ");
	        	sbPayerSQL.append(" nvl((SELECT (SUM(mAmount)+SUM(MREALINTEREST)+SUM(MREALCOMPOUNDINTEREST)+SUM(MREALOVERDUEINTEREST)+SUM(MREALSURETYFEE)+SUM(MREALCOMMISSION))  aa  ");
	        	sbPayerSQL.append("   FROM ob_FinanceInStr WHERE 1=1 AND ncurrencyid = 1 AND npayeracctID = c.npayeracctID AND (nStatus = 1 OR nStatus = 10 OR nStatus = 20 OR nStatus = 2 OR ");
	        	sbPayerSQL.append("   (nStatus = 3) OR (nStatus = 4 and  cpf_stransno is null)) AND ID != "+lInstructionID+") ,0), 0) mUsableBalance");
	        	sbPayerSQL.append(" FROM (select a.ID,a.SACCOUNTNO,a.SNAME,nvl(c.AF_SDEPOSITNO, 'ZIYING') SDEPOSITNO,c.mBalance,c.muncheckpaymentamount from ");
	        	sbPayerSQL.append("       SETT_Account a, SETT_Accounttype b, SETT_SubAccount c");
	        	sbPayerSQL.append("       WHERE a.nStatusID = " + Constant.RecordStatus.VALID);
	        	sbPayerSQL.append("       AND a.nCurrencyID = " + lCurrencyID);
	        	sbPayerSQL.append("       AND b.naccountgroupid in(" + SETTConstant.AccountGroupType.CURRENT + "," + SETTConstant.AccountGroupType.FIXED + "," + SETTConstant.AccountGroupType.NOTIFY + ")");
	        	sbPayerSQL.append("       AND a.naccounttypeid = b.id");
	        	sbPayerSQL.append("       AND a.id = c.naccountid) account_ab, ");
	        	sbPayerSQL.append("      (SELECT oba.npayeracctID, nvl(oba.SDEPOSITNO, 'ZIYING') SDEPOSITNO, SUM(nvl(oba.mAmount, 0)) mBalance FROM ob_FinanceInStr oba");
	        	sbPayerSQL.append("       WHERE oba.ncurrencyid = " + lCurrencyID);
	        	sbPayerSQL.append("       AND oba.nStatus in (");
	        	sbPayerSQL.append("  " + OBConstant.SettInstrStatus.SAVE + ",");
	        	sbPayerSQL.append("  " + OBConstant.SettInstrStatus.APPROVALING + ",");
				sbPayerSQL.append("  " + OBConstant.SettInstrStatus.APPROVALED + ",");
				sbPayerSQL.append("  " + OBConstant.SettInstrStatus.CHECK + ",");
				sbPayerSQL.append("  " + OBConstant.SettInstrStatus.DEAL + ")");
	        	sbPayerSQL.append("       group by oba.npayeracctID, oba.SDEPOSITNO) c");
	        	sbPayerSQL.append(" WHERE account_ab.ID = c.nPayeracctID(+)");
	        	
	        	//收款方信息(银行帐户 or 内部帐户)
	        	sbPayeeSQL  = new StringBuffer();
	        	sbPayeeSQL.append(" select t1.id, 'bank' payeeType, t1.SPAYEEACCTNO ACCOUNTNO, t1.SPAYEENAME NAME, t1.SPAYEEPROV PROV, t1.SPAYEECITY CITY, t1.SPAYEEBANKNAME BANKNAME,t1.spayeebankexchangeno bankexchangeno,t1.spayeebankcnapsno bankcnapsno,t1.spayeebankorgno bankorgno from OB_PAYEEINFO t1");
	        	sbPayeeSQL.append(" union");
	        	sbPayeeSQL.append(" select t2.id, 'system' payeeType, t2.SACCOUNTNO ACCOUNTNO, t2.SNAME NAME, '' PROV, '' CITY, '' BANKNAME,'' bankexchangeno,'' bankcnapsno,'' bankorgno from SETT_ACCOUNT t2");
	        	
		        sbSQL = new StringBuffer();
		        sbSQL.append(" select obfin.*,");
				sbSQL.append(" payerInfo.SACCOUNTNO payerACCOUNTNO,");
				sbSQL.append(" payerInfo.SNAME payerNAME,");
				sbSQL.append(" payerInfo.mCurrBalance mCurrBalance,");
				sbSQL.append(" payerInfo.mUsableBalance mUsableBalance,");
				sbSQL.append(" payeeInfo.ACCOUNTNO payeeACCOUNTNO,");
				sbSQL.append(" payeeInfo.NAME payeeNAME,");
				sbSQL.append(" payeeInfo.PROV payeePROV,");
				sbSQL.append(" payeeInfo.CITY payeeCITY,");
				sbSQL.append(" payeeInfo.BANKNAME payeeBANKNAME,");
				sbSQL.append(" payeeInfo.Bankexchangeno payeeBankexchangeno, ");
				sbSQL.append(" payeeInfo.Bankcnapsno payeeBankcnapsno, ");
				sbSQL.append(" payeeInfo.Bankorgno payeeBankorgno, ");
				
				sbSQL.append(" interestPayeeInfo.ACCOUNTNO interestPayeeACCOUNTNO,");
				sbSQL.append(" interestPayeeInfo.NAME interestPayeeNAME,");
				sbSQL.append(" interestPayeeInfo.PROV interestPayeePROV,");
				sbSQL.append(" interestPayeeInfo.CITY interestPayeeCITY,");
				sbSQL.append(" interestPayeeInfo.BANKNAME interestPayeeBANKNAME,");
				sbSQL.append(" interestPayeeInfo.Bankexchangeno interestPayeeBankexchangeno, ");
				sbSQL.append(" interestPayeeInfo.Bankcnapsno interestPayeeBankcnapsno, ");
				sbSQL.append(" interestPayeeInfo.Bankorgno interestPayeeBankorgno ");
				sbSQL.append(" FROM ");
				sbSQL.append(" (SELECT fin.*,");
				sbSQL.append(" nvl(fin.SDEPOSITNO, 'ZIYING') DEPOSITNO,");
				sbSQL.append(" cfmUser.sname confirmUserName,");
				sbSQL.append(" checkUser.sname checkUserName,");
				sbSQL.append(" delUser.sname delUserName,");
				sbSQL.append(" off.sname officename,");
				sbSQL.append(" use.sname DealUserName,");
				sbSQL.append(" decode(fin.NREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') payeeType,");
				sbSQL.append(" decode(fin.NINTERESTREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') interestPayeeType");
				sbSQL.append(" FROM ob_FinanceInStr fin,");
				sbSQL.append(" OB_USER cfmUser,");
				sbSQL.append(" OB_USER checkUser,");
				sbSQL.append(" OB_USER delUser,");
				sbSQL.append(" office off,");
				sbSQL.append(" userinfo use");
				sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
				sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
				sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
				sbSQL.append(" AND fin.CPF_nOfficeid=off.id(+) ");
				sbSQL.append(" AND fin.CPF_nDealUserId=use.id(+)");
				sbSQL.append(" AND fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + " \n");
				sbSQL.append(" AND fin.id=?) obfin,");
				sbSQL.append(" ("+ sbPayerSQL.toString() +") payerInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") payeeInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") interestPayeeInfo");
				sbSQL.append(" where obfin.NPAYERACCTID = payerInfo.id(+)");
				sbSQL.append(" AND obfin.DEPOSITNO = payerInfo.SDEPOSITNO");
				sbSQL.append(" AND obfin.NPAYEEACCTID = payeeInfo.id(+)");
				sbSQL.append(" AND obfin.payeeType = payeeInfo.payeeType(+)");
				sbSQL.append(" AND obfin.NINTERESTPAYEEACCTID = interestPayeeInfo.id(+)");
				sbSQL.append(" AND obfin.interestPayeeType = interestPayeeInfo.payeeType(+)");
	
				prepareStatement(sbSQL.toString());
				transPS.setLong(1, lInstructionID);
				
				log4j.info("OBFinanceInstrDAO.findByInstructionID()\n");
				log4j.info("lInstructionID = " + lInstructionID + "\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());
				

				executeQuery();
	
				info = new FinanceInfo();
				if(transRS.next())
				{
					info.setID(transRS.getLong("ID")); // 指令序号
					info.setChildClientID(transRS.getLong("nChildClientid")); //下属单位
					info.setClientID(transRS.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(transRS.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(transRS.getLong("NTRANSTYPE")); // 网上交易类型
					info.setPayerAcctID(transRS.getLong("NPAYERACCTID")); // 付款方账户ID
					info.setPayerAcctNo(transRS.getString("payerACCOUNTNO")); // 付款方账号
					info.setPayerName(transRS.getString("payerNAME")); // 付款方名称
					info.setCurrentBalance(transRS.getDouble("mCurrBalance")); //当前余额
					info.setUsableBalance(transRS.getDouble("mUsableBalance")); //可用余额
					info.setPayeeAcctID(transRS.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setPayeeAcctNo(transRS.getString("payeeACCOUNTNO")); // 收款方账号
					info.setPayeeName(transRS.getString("payeeNAME")); // 收款方名称	
					info.setPayeeProv(transRS.getString("payeePROV")); // 汇入省
					info.setPayeeCity(transRS.getString("payeeCITY")); // 汇入市
					info.setPayeeBankName(transRS.getString("payeeBANKNAME")); // 汇入行名称
					info.setRemitType(transRS.getLong("NREMITTYPE")); // 汇款方式
					info.setAmount(transRS.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(transRS.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(transRS.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(transRS.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
					info.setContractID(transRS.getLong("NContractID")); // 贷款合同ID
					info.setLoanNoteID(transRS.getLong("NLoanNoteID")); //放款通知单ID
					info.setPayDate(transRS.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(transRS.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setSubAccountID(transRS.getLong("nSubAccountID")); //子账户ID
					info.setDepositStart(transRS.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(transRS.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(transRS.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(transRS.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setClearInterest(transRS.getTimestamp("dtClearInterest")); //结息日
					info.setInterestPayeeAcctID(transRS.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestPayeeAcctNo(transRS.getString("interestPayeeACCOUNTNO")); // 利息收款方账号
					info.setInterestPayeeName(transRS.getString("interestPayeeNAME")); //利息收款方名称
					info.setInterestPayeeProv(transRS.getString("interestPayeePROV")); // 利息汇入省
					info.setInterestPayeeCity(transRS.getString("interestPayeeCITY")); // 利息汇入市
					info.setInterestPayeeBankName(transRS.getString("interestPayeeBANKNAME")); // 利息汇入行名称
					info.setInterestRemitType(transRS.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(transRS.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(transRS.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(transRS.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(transRS.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(transRS.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(transRS.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(transRS.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(transRS.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(transRS.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(transRS.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(transRS.getLong("NSTATUS")); // 指令状态
					info.setConfirmDate(transRS.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(transRS.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(transRS.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(transRS.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(transRS.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(transRS.getString("checkUserName")); // 复核人姓名
					info.setNoticeDay(transRS.getLong("nnoticeday")); //通知存款品种
					info.setDeleteDate(transRS.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(transRS.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(transRS.getString("delUserName")); // 删除人姓名
					info.setDealDate(transRS.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(transRS.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(transRS.getString("DealUserName")); // CPF-处理人姓名	
					info.setOfficeID(transRS.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(transRS.getString("CPF_STRANSNO")); //CPF-交易号
					info.setFinishDate(transRS.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
					info.setReject(transRS.getString("CPF_SREJECT")); //CPF-拒绝原因
					info.setIsCanAccept(transRS.getLong("NISCANACCEPT")); //能否接受
					info.setDefaultTransType(transRS.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
					info.setSubAccountID(transRS.getLong("NSUBACCOUNTID")); //子账户
					info.setClearInterest(transRS.getTimestamp("DTCLEARINTEREST")); //结息日期
					info.setInterestStart(transRS.getTimestamp("DTINTERESTSTART"));
					info.setCompoundStart(transRS.getTimestamp("DTCOMPOUNDINTERESTSTART"));
					info.setCompoundRate(transRS.getDouble("MCOMPOUNDRATE"));
					info.setOverDueStart(transRS.getTimestamp("DTOVERDUESTART"));
					info.setOverDueRate(transRS.getDouble("MOVERDUERATE"));
					info.setSuretyStart(transRS.getTimestamp("DTSURETYFEESTART"));
					info.setSuretyRate(transRS.getDouble("MSURETYFEERATE"));
					info.setCommissionStart(transRS.getTimestamp("DTCOMMISSIONSTART"));
					info.setCommissionRate(transRS.getDouble("MCOMMISSIONRATE"));
					info.setInterestRate(transRS.getDouble("MLOANREPAYMENTRATE"));
					info.setCompoundAmount(transRS.getDouble("MCOMPOUNDAMOUNT"));
					info.setOverDueAmount(transRS.getDouble("MOVERDUEAMOUNT"));
					info.setInterestReceiveable(transRS.getDouble("MINTERESTRECEIVEABLE"));
					info.setInterestIncome(transRS.getDouble("MINTERESTINCOME"));
					info.setRealInterestReceiveable(transRS.getDouble("MREALINTERESTRECEIVEABLE"));
					info.setRealInterestIncome(transRS.getDouble("MREALINTERESTINCOME"));
					info.setInterestTax(transRS.getDouble("MINTERESTTAX"));
					info.setRealInterestTax(transRS.getDouble("MREALINTERESTTAX"));
					info.setReturnMsg(transRS.getString("sReturnMsg"));
					info.setBudgetItemID(transRS.getLong("budgetItemID"));
					info.setIsFixContinue(transRS.getLong("ISFIXCONTINUE"));
					info.setFixEdremark(transRS.getString("FIXEDREMARK"));
					info.setMamOuntForTrans(transRS.getDouble("MAMOUNTFORTRANS"));
					info.setSDepositBillNo(transRS.getString("sDepositBillNo"));
					info.setNDepositBillStatusId(transRS.getLong("nDepositBillStatusId"));
					info.setNDepositBillInputuserId(transRS.getLong("nDepositBillInputuserId"));
					info.setNDepositBillCheckuserId(transRS.getLong("nDepositBillCheckuserId"));
					info.setDtDepositBillInputdate(transRS.getTimestamp("dtDepositBillInputdate"));
					info.setDtDepositBillCheckdate(transRS.getTimestamp("dtDepositBillCheckdate"));
					info.setSDepositBillAbstract(transRS.getString("sDepositBillAbstract"));
					info.setSDepositBillCheckAbstract(transRS.getString("sDepositBillCheckAbstract"));
					info.setSDepositBillStartDate(transRS.getTimestamp("FIXEDNEXTSTARTDATE"));
					info.setSDepositBillEndDate(transRS.getTimestamp("FIXEDNEXTENDDATE"));
					info.setSDepositBillPeriod(transRS.getLong("FIXEDNEXTPERIOD"));
					info.setSDepositInterestDeal(transRS.getLong("FIXEDINTERESTDEAL"));
					info.setSDepositInterestToAccountID(transRS.getLong("FIXEDINTERESTTOACCOUNTID"));
					info.setSignatureValue(transRS.getString("SIGNATUREVALUE"));
					info.setActionStatus(transRS.getLong("ACTIONSTATUS"));
	                info.setSource(transRS.getLong("LSOURCE"));
	                info.setApplyCode(transRS.getString("SAPPLYCODE"));
	                info.setSBatchNo(transRS.getString("sbatchno"));
	                info.setDtModify(transRS.getTimestamp("DTMODIFY"));
	                
	                //Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
	                info.setRemitArea(transRS.getLong("remitArea"));	//汇款区域
	                info.setRemitSpeed(transRS.getLong("remitSpeed"));	//汇款速度
	                
	                //财企接口新增
	                info.setSPayeeBankCNAPSNO(transRS.getString("payeeBankcnapsno"));
	                info.setSPayeeBankExchangeNO(transRS.getString("payeeBankexchangeno"));
	                info.setSPayeeBankOrgNO(transRS.getString("payeeBankorgno"));
	                info.setSInterestPayeeBankCNAPSNO(transRS.getString("interestPayeeBankcnapsno"));
	                info.setSInterestPayeeBankExchangeNO(transRS.getString("interestPayeeBankexchangeno"));
	                info.setSInterestPayeeBankOrgNO(transRS.getString("interestPayeeBankorgno"));
				}
				
				//下一步做优化
				//下一级审批级别和是否曾经被拒绝过 added by yanliu 2007/05/21
				/*if(info.getID()>0)
				{
					//下一级审批级别
					InutApprovalRecordBiz biz = new InutApprovalRecordBiz();
					InutApprovalRecordInfo qInfo = new InutApprovalRecordInfo();
					qInfo.setTransID(String.valueOf(info.getID()));
					qInfo.setStatusID(Constant.RecordStatus.VALID);
					qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					Collection c = biz.queryByCondition(qInfo);
					if(c!=null && c.size()>0)
					{
						InutApprovalRecordInfo tempInfo = (InutApprovalRecordInfo)(c.toArray()[0]);
						info.setNextLevel(tempInfo.getNextLevel());
					}
					else
					{
						info.setNextLevel(0);
					}
					
					//是否被拒绝过,有无效状态的审批记录时则表明曾经被拒绝过
					qInfo = new InutApprovalRecordInfo();
					qInfo.setTransID(String.valueOf(info.getID()));
					qInfo.setStatusID(Constant.RecordStatus.INVALID);//无效状态
					qInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
					c = biz.queryByCondition(qInfo);
					if(c!=null && c.size()>0)
					{
						info.setRefused(true);
					}
					else
					{
						info.setRefused(false);
					}	
				}*/
	        }
		    catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
		    
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
        }
        catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
          finalizeDAO();
        }
        return info;
	}
	
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息, 用于交易指令查询
	 * @param QueryCapForm 查询条件表单类
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryCollectionByQuery(QueryCapForm queryCapForm)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer sbSQL = null;
		StringBuffer sbPayeeSQL = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/

	        try {
	        	//收款方信息(银行帐户 or 内部帐户)
	        	sbPayeeSQL  = new StringBuffer();
	        	sbPayeeSQL.append(" select t1.id, 'bank' payeeType, t1.SPAYEEACCTNO ACCOUNTNO, t1.SPAYEENAME NAME, t1.SPAYEEPROV PROV, t1.SPAYEECITY CITY, t1.SPAYEEBANKNAME BANKNAME from OB_PAYEEINFO t1");
	        	sbPayeeSQL.append(" union");
	        	sbPayeeSQL.append(" select t2.id, 'system' payeeType, t2.SACCOUNTNO ACCOUNTNO, t2.SNAME NAME, '' PROV, '' CITY, '' BANKNAME from SETT_ACCOUNT t2");
	        	
		        sbSQL = new StringBuffer();
		        sbSQL.append(" select obfin.*,");
				sbSQL.append(" payerInfo.SACCOUNTNO payerACCOUNTNO,");
				sbSQL.append(" payerInfo.SNAME payerNAME,");
				sbSQL.append(" payeeInfo.ACCOUNTNO payeeACCOUNTNO,");
				sbSQL.append(" payeeInfo.NAME payeeNAME,");
				sbSQL.append(" payeeInfo.PROV payeePROV,");
				sbSQL.append(" payeeInfo.CITY payeeCITY,");
				sbSQL.append(" payeeInfo.BANKNAME payeeBANKNAME,");
				sbSQL.append(" interestPayeeInfo.ACCOUNTNO interestPayeeACCOUNTNO,");
				sbSQL.append(" interestPayeeInfo.NAME interestPayeeNAME,");
				sbSQL.append(" interestPayeeInfo.PROV interestPayeePROV,");
				sbSQL.append(" interestPayeeInfo.CITY interestPayeeCITY,");
				sbSQL.append(" interestPayeeInfo.BANKNAME interestPayeeBANKNAME");
				sbSQL.append(" FROM ");
				sbSQL.append(" (SELECT fin.*,");
				sbSQL.append(" cfmUser.sname confirmUserName,");
				sbSQL.append(" checkUser.sname checkUserName,");
				sbSQL.append(" delUser.sname delUserName,");
				sbSQL.append(" off.sname officename,");
				sbSQL.append(" use.sname DealUserName,");
				sbSQL.append(" decode(fin.NREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') payeeType,");
				sbSQL.append(" decode(fin.NINTERESTREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') interestPayeeType,");
				sbSQL.append(" nvl(bs.n_statusid,-1) n_statusid, \n");
				sbSQL.append(" bs.s_cancelcomments \n");
				sbSQL.append(" FROM ob_FinanceInStr fin,");
				sbSQL.append(" OB_USER cfmUser,");
				sbSQL.append(" OB_USER checkUser,");
				sbSQL.append(" OB_USER delUser,");
				sbSQL.append(" office off,");
				sbSQL.append(" userinfo use,");
				sbSQL.append(" (select * from BS_BANKINSTRUCTIONINFO bs where bs.n_id in ( \n");
				sbSQL.append("  select max(bs.n_id)\n");
				sbSQL.append("  from BS_BANKINSTRUCTIONINFO bs \n");
				sbSQL.append("  group by bs.s_transactionno) \n");
				sbSQL.append("  ) bs \n");
				sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
				sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
				sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
				sbSQL.append(" AND fin.CPF_nOfficeid=off.id(+) ");
				sbSQL.append(" AND fin.CPF_nDealUserId=use.id(+)");
				sbSQL.append(" AND fin.nCurrencyID=" + queryCapForm.getCurrencyID());
				sbSQL.append(" AND fin.nClientID=" + queryCapForm.getClientID());
				sbSQL.append(" and bs.s_transactionno(+)='1'||to_char(fin.CPF_STRANSNO) \n");		

	
				//查询交易指令状态
				if (queryCapForm.getStatus() > 0){
					sbSQL.append(" AND fin.nstatus = " + queryCapForm.getStatus() + " \n");
				}
				else {
					sbSQL.append(" AND fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + " \n");
				}
				//查询交易指令类型
				if(queryCapForm.getTransType() > 0){
					if(queryCapForm.getTransType() == OBConstant.QueryInstrType.CAPTRANSFER)
					{
						sbSQL.append(" AND fin.ntranstype in (" + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY + "," + OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
					}
					else if(queryCapForm.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
					{
						sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
						sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + ") \n");		
						sbSQL.append(" and fin.settfinid is null");
					}
					else
					{
						sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
						sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
					}
				}
				if (queryCapForm.getDepositID() != -1)
				{
					sbSQL.append(" AND fin.NSUBACCOUNTID = " + queryCapForm.getDepositID() + " \n");
				}
				//银行指令状态
				//撤销
				if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.CANCEL)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.CANCEL);
				}
				//以保存未发送
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.SAVED)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SAVED);
				}
				//支付处理中
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTING)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTING);
				}
				//支付成功
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.SUBMITTED)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.SUBMITTED);
				}
				//支付失败
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.FAILED)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.FAILED);
				}
				//支付未知
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.UNKNOWENED)
				{
					sbSQL.append(" and n_statusid ="+OBConstant.BankInstructionStatus.UNKNOWENED);
				}
				//无状态
				else if(queryCapForm.getNEbankStatus()==OBConstant.BankInstructionStatus.NONE)
				{
					sbSQL.append(" and nvl(n_statusid,-1) ="+OBConstant.BankInstructionStatus.UNSEND);
				}
				// 提交日期-从
				if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
				}
				// 提交日期-到
				if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
				}
				// 执行日期-从
				if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
				}
				// 执行日期-到
				if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				// 交易金额-从
				if (queryCapForm.getMinAmount() > 0.0)
				{
					sbSQL.append(" AND mAmount >= ? \n");
				}
				// 交易金额-到
				if (queryCapForm.getMaxAmount() > 0.0)
				{
					sbSQL.append(" AND mAmount <= ? \n");
				}
				sbSQL.append(" ) obfin,");
				sbSQL.append(" SETT_ACCOUNT payerInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") payeeInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") interestPayeeInfo");
				sbSQL.append(" where obfin.NPAYERACCTID = payerInfo.id(+)");
				sbSQL.append(" AND obfin.NPAYEEACCTID = payeeInfo.id(+)");
				sbSQL.append(" AND obfin.payeeType = payeeInfo.payeeType(+)");
				sbSQL.append(" AND obfin.NINTERESTPAYEEACCTID = interestPayeeInfo.id(+)");
				sbSQL.append(" AND obfin.interestPayeeType = interestPayeeInfo.payeeType(+)");
				
				if (queryCapForm.isOrderBy() == true)
				{
					sbSQL.append(" order by obfin.dtconfirm desc ,obfin.nPayerAcctID \n");
				}
				else
				{
					sbSQL.append(" order by obfin.dtconfirm asc ,obfin.nPayerAcctID \n");
				}
				
				prepareStatement(sbSQL.toString());
				int nIndex = 1;
				
				// 提交日期-从
				if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartSubmit().trim() + " 00:00:00"));
				}
				// 提交日期-到 
				if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndSubmit().trim() + " 23:59:59"));
				}
				// 执行日期-从
				if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartExe().trim() + " 00:00:00"));
				}
				// 执行日期-到
				if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndExe().trim() + " 23:59:59"));
				}
				// 交易金额-从
				if (queryCapForm.getMinAmount() > 0.0)
				{
					transPS.setDouble(nIndex++, queryCapForm.getMinAmount());
				}
				// 交易金额-到
				if (queryCapForm.getMaxAmount() > 0.0)
				{
					transPS.setDouble(nIndex++, queryCapForm.getMaxAmount());
				}
				
				log4j.info("OBFinanceInstrDAO.queryOBFinanceInstr()\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());
				
				executeQuery();
				
				coll = new ArrayList();
				while(transRS.next())
				{
					FinanceInfo info = new FinanceInfo();
					info.setID(transRS.getLong("ID")); // 指令序号
					info.setChildClientID(transRS.getLong("nChildClientid")); //下属单位
					info.setClientID(transRS.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(transRS.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(transRS.getLong("NTRANSTYPE")); // 网上交易类型
					info.setPayerAcctID(transRS.getLong("NPAYERACCTID")); // 付款方账户ID
					info.setPayerAcctNo(transRS.getString("payerACCOUNTNO")); // 付款方账号
					info.setPayerName(transRS.getString("payerNAME")); // 付款方名称
					info.setPayeeAcctID(transRS.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setPayeeAcctNo(transRS.getString("payeeACCOUNTNO")); // 收款方账号
					info.setPayeeName(transRS.getString("payeeNAME")); // 收款方名称	
					info.setPayeeProv(transRS.getString("payeePROV")); // 汇入省
					info.setPayeeCity(transRS.getString("payeeCITY")); // 汇入市
					info.setPayeeBankName(transRS.getString("payeeBANKNAME")); // 汇入行名称
					info.setRemitType(transRS.getLong("NREMITTYPE")); // 汇款方式
					info.setAmount(transRS.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(transRS.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(transRS.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(transRS.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
					info.setContractID(transRS.getLong("NContractID")); // 贷款合同ID
					info.setLoanNoteID(transRS.getLong("NLoanNoteID")); //放款通知单ID
					info.setPayDate(transRS.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(transRS.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setSubAccountID(transRS.getLong("nSubAccountID")); //子账户ID
					info.setDepositStart(transRS.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(transRS.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(transRS.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(transRS.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setClearInterest(transRS.getTimestamp("dtClearInterest")); //结息日
					info.setInterestPayeeAcctID(transRS.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestPayeeAcctNo(transRS.getString("interestPayeeACCOUNTNO")); // 利息收款方账号
					info.setInterestPayeeName(transRS.getString("interestPayeeNAME")); //利息收款方名称
					info.setInterestPayeeProv(transRS.getString("interestPayeePROV")); // 利息汇入省
					info.setInterestPayeeCity(transRS.getString("interestPayeeCITY")); // 利息汇入市
					info.setInterestPayeeBankName(transRS.getString("interestPayeeBANKNAME")); // 利息汇入行名称
					info.setInterestRemitType(transRS.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(transRS.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(transRS.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(transRS.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(transRS.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(transRS.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(transRS.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(transRS.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(transRS.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(transRS.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(transRS.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(transRS.getLong("NSTATUS")); // 指令状态
					info.setConfirmDate(transRS.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(transRS.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(transRS.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(transRS.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(transRS.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(transRS.getString("checkUserName")); // 复核人姓名
					info.setNoticeDay(transRS.getLong("nnoticeday")); //通知存款品种
					info.setDeleteDate(transRS.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(transRS.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(transRS.getString("delUserName")); // 删除人姓名
					info.setDealDate(transRS.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(transRS.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(transRS.getString("DealUserName")); // CPF-处理人姓名	
					info.setOfficeID(transRS.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(transRS.getString("CPF_STRANSNO")); //CPF-交易号
					info.setFinishDate(transRS.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
					info.setReject(transRS.getString("s_cancelcomments")); //CPF-拒绝原因
					info.setIsCanAccept(transRS.getLong("NISCANACCEPT")); //能否接受
					info.setDefaultTransType(transRS.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
					info.setSubAccountID(transRS.getLong("NSUBACCOUNTID")); //子账户
					info.setClearInterest(transRS.getTimestamp("DTCLEARINTEREST")); //结息日期
					info.setInterestStart(transRS.getTimestamp("DTINTERESTSTART"));
					info.setCompoundStart(transRS.getTimestamp("DTCOMPOUNDINTERESTSTART"));
					info.setCompoundRate(transRS.getDouble("MCOMPOUNDRATE"));
					info.setOverDueStart(transRS.getTimestamp("DTOVERDUESTART"));
					info.setOverDueRate(transRS.getDouble("MOVERDUERATE"));
					info.setSuretyStart(transRS.getTimestamp("DTSURETYFEESTART"));
					info.setSuretyRate(transRS.getDouble("MSURETYFEERATE"));
					info.setCommissionStart(transRS.getTimestamp("DTCOMMISSIONSTART"));
					info.setCommissionRate(transRS.getDouble("MCOMMISSIONRATE"));
					info.setInterestRate(transRS.getDouble("MLOANREPAYMENTRATE"));
					info.setCompoundAmount(transRS.getDouble("MCOMPOUNDAMOUNT"));
					info.setOverDueAmount(transRS.getDouble("MOVERDUEAMOUNT"));
					info.setInterestReceiveable(transRS.getDouble("MINTERESTRECEIVEABLE"));
					info.setInterestIncome(transRS.getDouble("MINTERESTINCOME"));
					info.setRealInterestReceiveable(transRS.getDouble("MREALINTERESTRECEIVEABLE"));
					info.setRealInterestIncome(transRS.getDouble("MREALINTERESTINCOME"));
					info.setInterestTax(transRS.getDouble("MINTERESTTAX"));
					info.setRealInterestTax(transRS.getDouble("MREALINTERESTTAX"));
					info.setReturnMsg(transRS.getString("sReturnMsg"));
					info.setBudgetItemID(transRS.getLong("budgetItemID"));
					info.setIsFixContinue(transRS.getLong("ISFIXCONTINUE"));
					info.setFixEdremark(transRS.getString("FIXEDREMARK"));
					info.setMamOuntForTrans(transRS.getDouble("MAMOUNTFORTRANS"));
					info.setSDepositBillNo(transRS.getString("sDepositBillNo"));
					info.setNDepositBillStatusId(transRS.getLong("nDepositBillStatusId"));
					info.setNDepositBillInputuserId(transRS.getLong("nDepositBillInputuserId"));
					info.setNDepositBillCheckuserId(transRS.getLong("nDepositBillCheckuserId"));
					info.setDtDepositBillInputdate(transRS.getTimestamp("dtDepositBillInputdate"));
					info.setDtDepositBillCheckdate(transRS.getTimestamp("dtDepositBillCheckdate"));
					info.setSDepositBillAbstract(transRS.getString("sDepositBillAbstract"));
					info.setSDepositBillCheckAbstract(transRS.getString("sDepositBillCheckAbstract"));
					info.setSDepositBillStartDate(transRS.getTimestamp("FIXEDNEXTSTARTDATE"));
					info.setSDepositBillEndDate(transRS.getTimestamp("FIXEDNEXTENDDATE"));
					info.setSDepositBillPeriod(transRS.getLong("FIXEDNEXTPERIOD"));
					info.setSDepositInterestDeal(transRS.getLong("FIXEDINTERESTDEAL"));
					info.setSDepositInterestToAccountID(transRS.getLong("FIXEDINTERESTTOACCOUNTID"));
					info.setSignatureValue(transRS.getString("SIGNATUREVALUE"));
					info.setActionStatus(transRS.getLong("ACTIONSTATUS"));
	                info.setSource(transRS.getLong("LSOURCE"));
	                info.setApplyCode(transRS.getString("SAPPLYCODE"));
	                info.setSBatchNo(transRS.getString("sbatchno"));
	                info.setEbankStatus(transRS.getLong("n_statusid")); //银行申请指令状态
					coll.add(info);
				}
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
        return (coll.size() > 0 ? coll : null);
	}
	/**
	 * 得到哪些业务存在审批流并且自动复核
	 * @param queryCapForm
	 * @return
	 */
	private String checkTransType(QueryCapForm queryCapForm)
	{
		String strReturn = "";
		boolean bisAutoCheck=false;
		try {
			bisAutoCheck = OBFSWorkflowManager.isAutoCheck();
			InutApprovalRelationInfo pInfo = new InutApprovalRelationInfo();
	    	pInfo.setModuleID(Constant.ModuleType.SETTLEMENT);
	    	pInfo.setCurrencyID(queryCapForm.getCurrencyID());
	    	pInfo.setClientID(queryCapForm.getClientID());
			// 是否是否设置关联下级单位
	    	pInfo.setIslowerunit(OBConstant.IsLowerun.ISNO); 
	    	InutApprovalRelationBiz biz = new InutApprovalRelationBiz();
	    	Collection coll = biz.queryByConditions(pInfo);
	    	if(coll!=null && coll.size()>0)
	    	{
	    		Iterator it = coll.iterator();
	    		while(it.hasNext())
	    		{
	    			InutApprovalRelationInfo tempInfo =(InutApprovalRelationInfo)it.next();
	    			if(bisAutoCheck)
	    			{
	    				strReturn = strReturn+tempInfo.getTransTypeID()+",";
	    			}
	    		}
	    	}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strReturn.length()>0?strReturn.substring(0, strReturn.length()-1):"";
	}
	/**
	 * 取消复核查询，如果存在业务挂审批流并且是自动复核，这里不查询到此业务信息
	 * @param queryCapForm
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public Collection queryCheckCollectionByQuery(QueryCapForm queryCapForm)
	throws ITreasuryDAOException
{
	Collection coll = null;
	StringBuffer sbSQL = null;
	StringBuffer sbPayeeSQL = null;
	
	try
	{
        /*-----------------init DAO --------------------*/
        try {
          initDAO();
        }
        catch (ITreasuryDAOException e) {
           throw new ITreasuryDAOException("创建连接时异常",e);
        }
        /*-----------------end DAO --------------------*/

        try {
        	//收款方信息(银行帐户 or 内部帐户)
        	sbPayeeSQL  = new StringBuffer();
        	sbPayeeSQL.append(" select t1.id, 'bank' payeeType, t1.SPAYEEACCTNO ACCOUNTNO, t1.SPAYEENAME NAME, t1.SPAYEEPROV PROV, t1.SPAYEECITY CITY, t1.SPAYEEBANKNAME BANKNAME from OB_PAYEEINFO t1");
        	sbPayeeSQL.append(" union");
        	sbPayeeSQL.append(" select t2.id, 'system' payeeType, t2.SACCOUNTNO ACCOUNTNO, t2.SNAME NAME, '' PROV, '' CITY, '' BANKNAME from SETT_ACCOUNT t2");
        	
	        sbSQL = new StringBuffer();
	        sbSQL.append(" select obfin.*,");
			sbSQL.append(" payerInfo.SACCOUNTNO payerACCOUNTNO,");
			sbSQL.append(" payerInfo.SNAME payerNAME,");
			sbSQL.append(" payeeInfo.ACCOUNTNO payeeACCOUNTNO,");
			sbSQL.append(" payeeInfo.NAME payeeNAME,");
			sbSQL.append(" payeeInfo.PROV payeePROV,");
			sbSQL.append(" payeeInfo.CITY payeeCITY,");
			sbSQL.append(" payeeInfo.BANKNAME payeeBANKNAME,");
			sbSQL.append(" interestPayeeInfo.ACCOUNTNO interestPayeeACCOUNTNO,");
			sbSQL.append(" interestPayeeInfo.NAME interestPayeeNAME,");
			sbSQL.append(" interestPayeeInfo.PROV interestPayeePROV,");
			sbSQL.append(" interestPayeeInfo.CITY interestPayeeCITY,");
			sbSQL.append(" interestPayeeInfo.BANKNAME interestPayeeBANKNAME");
			sbSQL.append(" FROM ");
			sbSQL.append(" (SELECT fin.*,");
			sbSQL.append(" cfmUser.sname confirmUserName,");
			sbSQL.append(" checkUser.sname checkUserName,");
			sbSQL.append(" delUser.sname delUserName,");
			sbSQL.append(" off.sname officename,");
			sbSQL.append(" use.sname DealUserName,");
			sbSQL.append(" decode(fin.NREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') payeeType,");
			sbSQL.append(" decode(fin.NINTERESTREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') interestPayeeType");
			sbSQL.append(" FROM ob_FinanceInStr fin,");
			sbSQL.append(" OB_USER cfmUser,");
			sbSQL.append(" OB_USER checkUser,");
			sbSQL.append(" OB_USER delUser,");
			sbSQL.append(" office off,");
			sbSQL.append(" userinfo use");
			sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
			sbSQL.append(" AND fin.CPF_nOfficeid=off.id(+) ");
			sbSQL.append(" AND fin.CPF_nDealUserId=use.id(+)");
			sbSQL.append(" AND fin.nCurrencyID=" + queryCapForm.getCurrencyID());
			sbSQL.append(" AND fin.nClientID=" + queryCapForm.getClientID());
			sbSQL.append(" AND fin.nCheckUserID=" + queryCapForm.getUserID());

			//查询交易指令状态
			if (queryCapForm.getStatus() > 0){
				sbSQL.append(" AND fin.nstatus = " + queryCapForm.getStatus() + " \n");
			}
			else {
				sbSQL.append(" AND fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + " \n");
			}
			//查询交易指令类型
			if(queryCapForm.getTransType() > 0){
				if(queryCapForm.getTransType() == OBConstant.QueryInstrType.CAPTRANSFER)
				{
					sbSQL.append(" AND fin.ntranstype in (" + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY + "," + OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
				}
				else if(queryCapForm.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
					sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + ") \n");		
					sbSQL.append(" and fin.settfinid is null");
				}
				else
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
					sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
				}
			}
			else
			{
				String tempAutoTransTypes = checkTransType(queryCapForm);
				if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
				{
					sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
				}
			}
			// 提交日期-从
			if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
			}
			// 提交日期-到
			if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
			}
			// 执行日期-从
			if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
			}
			// 执行日期-到
			if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
			}
			// 交易金额-从
			if (queryCapForm.getMinAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount >= ? \n");
			}
			// 交易金额-到
			if (queryCapForm.getMaxAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount <= ? \n");
			}
			sbSQL.append(" ) obfin,");
			sbSQL.append(" SETT_ACCOUNT payerInfo,");
			sbSQL.append(" ("+ sbPayeeSQL.toString() +") payeeInfo,");
			sbSQL.append(" ("+ sbPayeeSQL.toString() +") interestPayeeInfo");
			sbSQL.append(" where obfin.NPAYERACCTID = payerInfo.id(+)");
			sbSQL.append(" AND obfin.NPAYEEACCTID = payeeInfo.id(+)");
			sbSQL.append(" AND obfin.payeeType = payeeInfo.payeeType(+)");
			sbSQL.append(" AND obfin.NINTERESTPAYEEACCTID = interestPayeeInfo.id(+)");
			sbSQL.append(" AND obfin.interestPayeeType = interestPayeeInfo.payeeType(+)");
			
			if (queryCapForm.isOrderBy() == true)
			{
				sbSQL.append(" order by obfin.dtconfirm desc ,obfin.nPayerAcctID \n");
			}
			else
			{
				sbSQL.append(" order by obfin.dtconfirm asc ,obfin.nPayerAcctID \n");
			}
			
			prepareStatement(sbSQL.toString());
			int nIndex = 1;
			
			// 提交日期-从
			if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
			{
				transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartSubmit().trim() + " 00:00:00"));
			}
			// 提交日期-到 
			if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
			{
				transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndSubmit().trim() + " 23:59:59"));
			}
			// 执行日期-从
			if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
			{
				transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartExe().trim() + " 00:00:00"));
			}
			// 执行日期-到
			if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
			{
				transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndExe().trim() + " 23:59:59"));
			}
			// 交易金额-从
			if (queryCapForm.getMinAmount() > 0.0)
			{
				transPS.setDouble(nIndex++, queryCapForm.getMinAmount());
			}
			// 交易金额-到
			if (queryCapForm.getMaxAmount() > 0.0)
			{
				transPS.setDouble(nIndex++, queryCapForm.getMaxAmount());
			}
			
			log4j.info("OBFinanceInstrDAO.queryOBFinanceInstr()\n");
			log4j.info("sbSQL=\n" + sbSQL.toString());
			
			executeQuery();
			
			coll = new ArrayList();
			while(transRS.next())
			{
				FinanceInfo info = new FinanceInfo();
				info.setID(transRS.getLong("ID")); // 指令序号
				info.setChildClientID(transRS.getLong("nChildClientid")); //下属单位
				info.setClientID(transRS.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(transRS.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(transRS.getLong("NTRANSTYPE")); // 网上交易类型
				info.setPayerAcctID(transRS.getLong("NPAYERACCTID")); // 付款方账户ID
				info.setPayerAcctNo(transRS.getString("payerACCOUNTNO")); // 付款方账号
				info.setPayerName(transRS.getString("payerNAME")); // 付款方名称
				info.setPayeeAcctID(transRS.getLong("NPAYEEACCTID")); //收款方账户ID
				info.setPayeeAcctNo(transRS.getString("payeeACCOUNTNO")); // 收款方账号
				info.setPayeeName(transRS.getString("payeeNAME")); // 收款方名称	
				info.setPayeeProv(transRS.getString("payeePROV")); // 汇入省
				info.setPayeeCity(transRS.getString("payeeCITY")); // 汇入市
				info.setPayeeBankName(transRS.getString("payeeBANKNAME")); // 汇入行名称
				info.setRemitType(transRS.getLong("NREMITTYPE")); // 汇款方式
				info.setAmount(transRS.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(transRS.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(transRS.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(transRS.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
				info.setContractID(transRS.getLong("NContractID")); // 贷款合同ID
				info.setLoanNoteID(transRS.getLong("NLoanNoteID")); //放款通知单ID
				info.setPayDate(transRS.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(transRS.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setSubAccountID(transRS.getLong("nSubAccountID")); //子账户ID
				info.setDepositStart(transRS.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(transRS.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(transRS.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(transRS.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setClearInterest(transRS.getTimestamp("dtClearInterest")); //结息日
				info.setInterestPayeeAcctID(transRS.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestPayeeAcctNo(transRS.getString("interestPayeeACCOUNTNO")); // 利息收款方账号
				info.setInterestPayeeName(transRS.getString("interestPayeeNAME")); //利息收款方名称
				info.setInterestPayeeProv(transRS.getString("interestPayeePROV")); // 利息汇入省
				info.setInterestPayeeCity(transRS.getString("interestPayeeCITY")); // 利息汇入市
				info.setInterestPayeeBankName(transRS.getString("interestPayeeBANKNAME")); // 利息汇入行名称
				info.setInterestRemitType(transRS.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(transRS.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(transRS.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(transRS.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(transRS.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(transRS.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(transRS.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(transRS.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(transRS.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(transRS.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(transRS.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(transRS.getLong("NSTATUS")); // 指令状态
				info.setConfirmDate(transRS.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(transRS.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setConfirmUserName(transRS.getString("confirmUserName")); // 确认人姓名
				info.setCheckDate(transRS.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(transRS.getLong("NCHECKUSERID")); //复核人ID
				info.setCheckUserName(transRS.getString("checkUserName")); // 复核人姓名
				info.setNoticeDay(transRS.getLong("nnoticeday")); //通知存款品种
				info.setDeleteDate(transRS.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(transRS.getLong("NDELETEUSERID")); //删除人ID
				info.setDeleteUserName(transRS.getString("delUserName")); // 删除人姓名
				info.setDealDate(transRS.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(transRS.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setDealUserName(transRS.getString("DealUserName")); // CPF-处理人姓名	
				info.setOfficeID(transRS.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(transRS.getString("CPF_STRANSNO")); //CPF-交易号
				info.setFinishDate(transRS.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
				info.setReject(transRS.getString("CPF_SREJECT")); //CPF-拒绝原因
				info.setIsCanAccept(transRS.getLong("NISCANACCEPT")); //能否接受
				info.setDefaultTransType(transRS.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
				info.setSubAccountID(transRS.getLong("NSUBACCOUNTID")); //子账户
				info.setClearInterest(transRS.getTimestamp("DTCLEARINTEREST")); //结息日期
				info.setInterestStart(transRS.getTimestamp("DTINTERESTSTART"));
				info.setCompoundStart(transRS.getTimestamp("DTCOMPOUNDINTERESTSTART"));
				info.setCompoundRate(transRS.getDouble("MCOMPOUNDRATE"));
				info.setOverDueStart(transRS.getTimestamp("DTOVERDUESTART"));
				info.setOverDueRate(transRS.getDouble("MOVERDUERATE"));
				info.setSuretyStart(transRS.getTimestamp("DTSURETYFEESTART"));
				info.setSuretyRate(transRS.getDouble("MSURETYFEERATE"));
				info.setCommissionStart(transRS.getTimestamp("DTCOMMISSIONSTART"));
				info.setCommissionRate(transRS.getDouble("MCOMMISSIONRATE"));
				info.setInterestRate(transRS.getDouble("MLOANREPAYMENTRATE"));
				info.setCompoundAmount(transRS.getDouble("MCOMPOUNDAMOUNT"));
				info.setOverDueAmount(transRS.getDouble("MOVERDUEAMOUNT"));
				info.setInterestReceiveable(transRS.getDouble("MINTERESTRECEIVEABLE"));
				info.setInterestIncome(transRS.getDouble("MINTERESTINCOME"));
				info.setRealInterestReceiveable(transRS.getDouble("MREALINTERESTRECEIVEABLE"));
				info.setRealInterestIncome(transRS.getDouble("MREALINTERESTINCOME"));
				info.setInterestTax(transRS.getDouble("MINTERESTTAX"));
				info.setRealInterestTax(transRS.getDouble("MREALINTERESTTAX"));
				info.setReturnMsg(transRS.getString("sReturnMsg"));
				info.setBudgetItemID(transRS.getLong("budgetItemID"));
				info.setIsFixContinue(transRS.getLong("ISFIXCONTINUE"));
				info.setFixEdremark(transRS.getString("FIXEDREMARK"));
				info.setMamOuntForTrans(transRS.getDouble("MAMOUNTFORTRANS"));
				info.setSDepositBillNo(transRS.getString("sDepositBillNo"));
				info.setNDepositBillStatusId(transRS.getLong("nDepositBillStatusId"));
				info.setNDepositBillInputuserId(transRS.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(transRS.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(transRS.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(transRS.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(transRS.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(transRS.getString("sDepositBillCheckAbstract"));
				info.setSDepositBillStartDate(transRS.getTimestamp("FIXEDNEXTSTARTDATE"));
				info.setSDepositBillEndDate(transRS.getTimestamp("FIXEDNEXTENDDATE"));
				info.setSDepositBillPeriod(transRS.getLong("FIXEDNEXTPERIOD"));
				info.setSDepositInterestDeal(transRS.getLong("FIXEDINTERESTDEAL"));
				info.setSDepositInterestToAccountID(transRS.getLong("FIXEDINTERESTTOACCOUNTID"));
				info.setSignatureValue(transRS.getString("SIGNATUREVALUE"));
				info.setActionStatus(transRS.getLong("ACTIONSTATUS"));
                info.setSource(transRS.getLong("LSOURCE"));
                info.setApplyCode(transRS.getString("SAPPLYCODE"));
                info.setSBatchNo(transRS.getString("sbatchno"));
				coll.add(info);
			}
        }
        catch (Exception e) {
	        throw new ITreasuryDAOException("查询异常", e);
	    }
		
	    /*----------------finalize Dao-----------------*/
	    try {
	        finalizeDAO();
	    }
	    catch (ITreasuryDAOException e) {
	        throw new ITreasuryDAOException("关闭连接时异常",e);
	    }
	    /*----------------end of finalize---------------*/
	}
    catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        throw new ITreasuryDAOException("查询异常",e);
    }
    finally {
    	finalizeDAO();
    }
    return (coll.size() > 0 ? coll : null);
}
	/**
	 * 根据查询条件表单类，查询出符合查询条件的指令信息状态, 用于交易指令汇总查询
	 * @param QueryCapForm 查询条件表单类
	 * @return Map  所有符合条件交易指令汇总信息
	 * @exception Exception
	 */
	public Map queryMapByQueryStatus(QueryCapForm queryCapForm)
		throws ITreasuryDAOException
	{
		Map map = null;
		StringBuffer sbSQL = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/

	        try {
		        sbSQL = new StringBuffer();
				sbSQL.append(" SELECT fin.nstatus status, count(fin.nstatus) statusNum, sum(fin.mamount) amounts");
				sbSQL.append(" FROM ob_FinanceInStr fin");
				sbSQL.append(" WHERE fin.nCurrencyID=" + queryCapForm.getCurrencyID());
				sbSQL.append(" AND fin.nClientID=" + queryCapForm.getClientID());
				
				//查询交易指令状态
				if (queryCapForm.getStatus() > 0){
					sbSQL.append(" AND fin.nstatus = " + queryCapForm.getStatus() + " \n");
				}
				else {
					sbSQL.append(" AND fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + " \n");
				}
				//查询交易指令类型
				if(queryCapForm.getTransType() > 0){
					if(queryCapForm.getTransType() == OBConstant.QueryInstrType.CAPTRANSFER)
					{
						sbSQL.append(" AND fin.ntranstype in (" + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY + "," + OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT + ") \n");
					}
					else if(queryCapForm.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
					{
						sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
						sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + ") \n");		
						sbSQL.append(" and fin.settfinid is null");
					}
					else
					{
						sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(queryCapForm.getTransType()) + " \n");
						sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
					}
				}
				// 提交日期-从
				if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
				}
				// 提交日期-到
				if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
				}
				// 执行日期-从
				if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
				}
				// 执行日期-到
				if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
				{
					sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				}
				// 交易金额-从
				if (queryCapForm.getMinAmount() > 0.0)
				{
					sbSQL.append(" AND mAmount >= ? \n");
				}
				// 交易金额-到
				if (queryCapForm.getMaxAmount() > 0.0)
				{
					sbSQL.append(" AND mAmount <= ? \n");
				}
	
				sbSQL.append(" group by fin.nstatus \n");
				
				prepareStatement(sbSQL.toString());
				int nIndex = 1;
				// 提交日期-从
				if (queryCapForm.getStartSubmit() != null && queryCapForm.getStartSubmit().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartSubmit().trim() + " 00:00:00"));
				}
				// 提交日期-到 
				if (queryCapForm.getEndSubmit() != null && queryCapForm.getEndSubmit().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndSubmit().trim() + " 23:59:59"));
				}
				// 执行日期-从
				if (queryCapForm.getStartExe() != null && queryCapForm.getStartExe().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getStartExe().trim() + " 00:00:00"));
				}
				// 执行日期-到
				if (queryCapForm.getEndExe() != null && queryCapForm.getEndExe().trim().length() > 0)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(queryCapForm.getEndExe().trim() + " 23:59:59"));
				}
				// 交易金额-从
				if (queryCapForm.getMinAmount() > 0.0)
				{
					transPS.setDouble(nIndex++, queryCapForm.getMinAmount());
				}
				// 交易金额-到
				if (queryCapForm.getMaxAmount() > 0.0)
				{
					transPS.setDouble(nIndex++, queryCapForm.getMaxAmount());
				}
				
				log4j.info("OBFinanceInstrDAO.queryOBFinanceInstrStatus()\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());
				
				executeQuery();
				
				map = new HashMap();
				long status = -1;
				long statuNum = -1;
				double amounts = 0.0;
				while(transRS.next())
				{
					status = transRS.getLong("status");
					statuNum = transRS.getLong("statusNum");
					amounts = amounts + transRS.getLong("amounts");
					map.put(String.valueOf(status), String.valueOf(statuNum));
				}
				map.put("amounts", String.valueOf(amounts));
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
        return (map.size() > 0 ? map : null);
	}
	
	/**
	 * 根据查询条件类，查询出符合查询条件的指令信息, 用于交易指令复核查询
	 * @param FinanceInfo 查询条件
	 * @return Collection  所有符合条件交易指令信息
	 * @exception Exception
	 */
	public Collection queryCollectionByCheck(FinanceInfo financeInfo)
		throws ITreasuryDAOException
	{
		Collection coll = null;
		StringBuffer sbSQL = null;
		StringBuffer sbPayeeSQL = null;
		
		try
		{
	        /*-----------------init DAO --------------------*/
	        try {
	          initDAO();
	        }
	        catch (ITreasuryDAOException e) {
	           throw new ITreasuryDAOException("创建连接时异常",e);
	        }
	        /*-----------------end DAO --------------------*/

	        try {
	        	//收款方信息(银行帐户 or 内部帐户)
	        	sbPayeeSQL  = new StringBuffer();
	        	sbPayeeSQL.append(" select t1.id, 'bank' payeeType, t1.SPAYEEACCTNO ACCOUNTNO, t1.SPAYEENAME NAME, t1.SPAYEEPROV PROV, t1.SPAYEECITY CITY, t1.SPAYEEBANKNAME BANKNAME from OB_PAYEEINFO t1");
	        	sbPayeeSQL.append(" union");
	        	sbPayeeSQL.append(" select t2.id, 'system' payeeType, t2.SACCOUNTNO ACCOUNTNO, t2.SNAME NAME, '' PROV, '' CITY, '' BANKNAME from SETT_ACCOUNT t2");
	        	
		        sbSQL = new StringBuffer();
		        sbSQL.append(" select obfin.*,");
				sbSQL.append(" payerInfo.SACCOUNTNO payerACCOUNTNO,");
				sbSQL.append(" payerInfo.SNAME payerNAME,");
				sbSQL.append(" payeeInfo.ACCOUNTNO payeeACCOUNTNO,");
				sbSQL.append(" payeeInfo.NAME payeeNAME,");
				sbSQL.append(" payeeInfo.PROV payeePROV,");
				sbSQL.append(" payeeInfo.CITY payeeCITY,");
				sbSQL.append(" payeeInfo.BANKNAME payeeBANKNAME,");
				sbSQL.append(" interestPayeeInfo.ACCOUNTNO interestPayeeACCOUNTNO,");
				sbSQL.append(" interestPayeeInfo.NAME interestPayeeNAME,");
				sbSQL.append(" interestPayeeInfo.PROV interestPayeePROV,");
				sbSQL.append(" interestPayeeInfo.CITY interestPayeeCITY,");
				sbSQL.append(" interestPayeeInfo.BANKNAME interestPayeeBANKNAME");
				sbSQL.append(" FROM ");
				sbSQL.append(" (SELECT fin.*,");
				sbSQL.append(" cfmUser.sname confirmUserName,");
				sbSQL.append(" checkUser.sname checkUserName,");
				sbSQL.append(" delUser.sname delUserName,");
				sbSQL.append(" off.sname officename,");
				sbSQL.append(" use.sname DealUserName,");
				sbSQL.append(" decode(fin.NREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') payeeType,");
				sbSQL.append(" decode(fin.NINTERESTREMITTYPE, "+ OBConstant.SettRemitType.BANKPAY +", 'bank', 'system') interestPayeeType");
				sbSQL.append(" FROM ob_FinanceInStr fin,");
				sbSQL.append(" OB_USER cfmUser,");
				sbSQL.append(" OB_USER checkUser,");
				sbSQL.append(" OB_USER delUser,");
				sbSQL.append(" office off,");
				sbSQL.append(" userinfo use");
				sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
				sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
				sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
				sbSQL.append(" AND fin.CPF_nOfficeid=off.id(+) ");
				sbSQL.append(" AND fin.CPF_nDealUserId=use.id(+)");
				sbSQL.append(" AND fin.nCurrencyID=" + financeInfo.getCurrencyID());
				sbSQL.append(" AND fin.nClientID=" + financeInfo.getClientID());
				sbSQL.append(" AND fin.nstatus = " + financeInfo.getStatus() + " \n");
				sbSQL.append(" AND fin.nConfirmUserID !=" + financeInfo.getUserID());
				sbSQL.append(" AND fin.NPAYERACCTID = " + financeInfo.getPayerAcctID());
				sbSQL.append(" AND fin.NTRANSTYPE = " + financeInfo.getTransType());
				sbSQL.append(" AND fin.NREMITTYPE = " + financeInfo.getRemitType());
				sbSQL.append(" AND fin.NPAYEEACCTID = " + financeInfo.getPayeeAcctID());
				sbSQL.append(" AND fin.MAMOUNT = " + financeInfo.getAmount());
				sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
				sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
				sbSQL.append(" ) obfin,");
				sbSQL.append(" SETT_ACCOUNT payerInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") payeeInfo,");
				sbSQL.append(" ("+ sbPayeeSQL.toString() +") interestPayeeInfo");
				sbSQL.append(" where obfin.NPAYERACCTID = payerInfo.id(+)");
				sbSQL.append(" AND obfin.NPAYEEACCTID = payeeInfo.id(+)");
				sbSQL.append(" AND obfin.payeeType = payeeInfo.payeeType(+)");
				sbSQL.append(" AND obfin.NINTERESTPAYEEACCTID = interestPayeeInfo.id(+)");
				sbSQL.append(" AND obfin.interestPayeeType = interestPayeeInfo.payeeType(+)");
				
				prepareStatement(sbSQL.toString());
				int nIndex = 1;
				
				if (financeInfo.getExecuteDate() != null)
				{
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(financeInfo.getFormatExecuteDate() + " 00:00:00"));
					transPS.setTimestamp(nIndex++, Timestamp.valueOf(financeInfo.getFormatExecuteDate() + " 23:59:59"));
				}
				
				log4j.info("OBFinanceInstrDAO.queryCollectionByCheck()\n");
				log4j.info("sbSQL=\n" + sbSQL.toString());
				
				executeQuery();
				
				coll = new ArrayList();
				while(transRS.next())
				{
					FinanceInfo info = new FinanceInfo();
					info.setID(transRS.getLong("ID")); // 指令序号
					info.setChildClientID(transRS.getLong("nChildClientid")); //下属单位
					info.setClientID(transRS.getLong("NCLIENTID")); // 交易提交单位
					info.setCurrencyID(transRS.getLong("NCURRENCYID")); // 交易币种
					info.setTransType(transRS.getLong("NTRANSTYPE")); // 网上交易类型
					info.setPayerAcctID(transRS.getLong("NPAYERACCTID")); // 付款方账户ID
					info.setPayerAcctNo(transRS.getString("payerACCOUNTNO")); // 付款方账号
					info.setPayerName(transRS.getString("payerNAME")); // 付款方名称
					info.setPayeeAcctID(transRS.getLong("NPAYEEACCTID")); //收款方账户ID
					info.setPayeeAcctNo(transRS.getString("payeeACCOUNTNO")); // 收款方账号
					info.setPayeeName(transRS.getString("payeeNAME")); // 收款方名称	
					info.setPayeeProv(transRS.getString("payeePROV")); // 汇入省
					info.setPayeeCity(transRS.getString("payeeCITY")); // 汇入市
					info.setPayeeBankName(transRS.getString("payeeBANKNAME")); // 汇入行名称
					info.setRemitType(transRS.getLong("NREMITTYPE")); // 汇款方式
					info.setAmount(transRS.getDouble("MAMOUNT")); // 交易金额
					info.setExecuteDate(transRS.getTimestamp("DTEXECUTE")); // 执行日
					info.setNote(transRS.getString("SNOTE")); // 汇款用途/摘要
					info.setFixedDepositTime(transRS.getLong("NFIXEDDEPOSITTIME")); // 定期存款期限（月）
					info.setContractID(transRS.getLong("NContractID")); // 贷款合同ID
					info.setLoanNoteID(transRS.getLong("NLoanNoteID")); //放款通知单ID
					info.setPayDate(transRS.getTimestamp("DTPAY")); // 贷款放款日期
					info.setDepositNo(transRS.getString("SDEPOSITNO")); //定期（通知）存款单据号
					info.setSubAccountID(transRS.getLong("nSubAccountID")); //子账户ID
					info.setDepositStart(transRS.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
					info.setDepositRate(transRS.getDouble("MDEPOSITRATE")); //定期存单利率
					info.setDepositAmount(transRS.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
					info.setDepositBalance(transRS.getDouble("MDEPOSITBALANCE")); //存单余额
					info.setClearInterest(transRS.getTimestamp("dtClearInterest")); //结息日
					info.setInterestPayeeAcctID(transRS.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
					info.setInterestPayeeAcctNo(transRS.getString("interestPayeeACCOUNTNO")); // 利息收款方账号
					info.setInterestPayeeName(transRS.getString("interestPayeeNAME")); //利息收款方名称
					info.setInterestPayeeProv(transRS.getString("interestPayeePROV")); // 利息汇入省
					info.setInterestPayeeCity(transRS.getString("interestPayeeCITY")); // 利息汇入市
					info.setInterestPayeeBankName(transRS.getString("interestPayeeBANKNAME")); // 利息汇入行名称
					info.setInterestRemitType(transRS.getLong("NINTERESTREMITTYPE")); //利息付款方式
					info.setInterest(transRS.getDouble("MINTEREST")); //应付贷款利息
					info.setCompoundInterest(transRS.getDouble("MCOMPOUNDINTEREST")); //应付复利
					info.setOverdueInterest(transRS.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
					info.setSuretyFee(transRS.getDouble("MSURETYFEE")); //应付担保费
					info.setCommission(transRS.getDouble("MCOMMISSION")); //应付手续费
					info.setRealInterest(transRS.getDouble("MREALINTEREST")); //实付贷款利息
					info.setRealCompoundInterest(transRS.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
					info.setRealOverdueInterest(transRS.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
					info.setRealSuretyFee(transRS.getDouble("MREALSURETYFEE")); //实付担保费
					info.setRealCommission(transRS.getDouble("MREALCOMMISSION")); //实付手续费
					info.setStatus(transRS.getLong("NSTATUS")); // 指令状态
					info.setConfirmDate(transRS.getTimestamp("DTCONFIRM")); //确认日期
					info.setConfirmUserID(transRS.getLong("NCONFIRMUSERID")); //确认人ID	
					info.setConfirmUserName(transRS.getString("confirmUserName")); // 确认人姓名
					info.setCheckDate(transRS.getTimestamp("DTCHECK")); //复核日期
					info.setCheckUserID(transRS.getLong("NCHECKUSERID")); //复核人ID
					info.setCheckUserName(transRS.getString("checkUserName")); // 复核人姓名
					info.setNoticeDay(transRS.getLong("nnoticeday")); //通知存款品种
					info.setDeleteDate(transRS.getTimestamp("DTDELETE")); //删除日期
					info.setDeleteUserID(transRS.getLong("NDELETEUSERID")); //删除人ID
					info.setDeleteUserName(transRS.getString("delUserName")); // 删除人姓名
					info.setDealDate(transRS.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
					info.setDealUserID(transRS.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
					info.setDealUserName(transRS.getString("DealUserName")); // CPF-处理人姓名	
					info.setOfficeID(transRS.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
					info.setTransNo(transRS.getString("CPF_STRANSNO")); //CPF-交易号
					info.setFinishDate(transRS.getTimestamp("CPF_DTFINISH")); //CPF-完成时间
					info.setReject(transRS.getString("CPF_SREJECT")); //CPF-拒绝原因
					info.setIsCanAccept(transRS.getLong("NISCANACCEPT")); //能否接受
					info.setDefaultTransType(transRS.getLong("CPF_NDEFAULTTRANSTYPE")); // 默认交易类型
					info.setSubAccountID(transRS.getLong("NSUBACCOUNTID")); //子账户
					info.setClearInterest(transRS.getTimestamp("DTCLEARINTEREST")); //结息日期
					info.setInterestStart(transRS.getTimestamp("DTINTERESTSTART"));
					info.setCompoundStart(transRS.getTimestamp("DTCOMPOUNDINTERESTSTART"));
					info.setCompoundRate(transRS.getDouble("MCOMPOUNDRATE"));
					info.setOverDueStart(transRS.getTimestamp("DTOVERDUESTART"));
					info.setOverDueRate(transRS.getDouble("MOVERDUERATE"));
					info.setSuretyStart(transRS.getTimestamp("DTSURETYFEESTART"));
					info.setSuretyRate(transRS.getDouble("MSURETYFEERATE"));
					info.setCommissionStart(transRS.getTimestamp("DTCOMMISSIONSTART"));
					info.setCommissionRate(transRS.getDouble("MCOMMISSIONRATE"));
					info.setInterestRate(transRS.getDouble("MLOANREPAYMENTRATE"));
					info.setCompoundAmount(transRS.getDouble("MCOMPOUNDAMOUNT"));
					info.setOverDueAmount(transRS.getDouble("MOVERDUEAMOUNT"));
					info.setInterestReceiveable(transRS.getDouble("MINTERESTRECEIVEABLE"));
					info.setInterestIncome(transRS.getDouble("MINTERESTINCOME"));
					info.setRealInterestReceiveable(transRS.getDouble("MREALINTERESTRECEIVEABLE"));
					info.setRealInterestIncome(transRS.getDouble("MREALINTERESTINCOME"));
					info.setInterestTax(transRS.getDouble("MINTERESTTAX"));
					info.setRealInterestTax(transRS.getDouble("MREALINTERESTTAX"));
					info.setReturnMsg(transRS.getString("sReturnMsg"));
					info.setBudgetItemID(transRS.getLong("budgetItemID"));
					info.setIsFixContinue(transRS.getLong("ISFIXCONTINUE"));
					info.setFixEdremark(transRS.getString("FIXEDREMARK"));
					info.setMamOuntForTrans(transRS.getDouble("MAMOUNTFORTRANS"));
					info.setSDepositBillNo(transRS.getString("sDepositBillNo"));
					info.setNDepositBillStatusId(transRS.getLong("nDepositBillStatusId"));
					info.setNDepositBillInputuserId(transRS.getLong("nDepositBillInputuserId"));
					info.setNDepositBillCheckuserId(transRS.getLong("nDepositBillCheckuserId"));
					info.setDtDepositBillInputdate(transRS.getTimestamp("dtDepositBillInputdate"));
					info.setDtDepositBillCheckdate(transRS.getTimestamp("dtDepositBillCheckdate"));
					info.setSDepositBillAbstract(transRS.getString("sDepositBillAbstract"));
					info.setSDepositBillCheckAbstract(transRS.getString("sDepositBillCheckAbstract"));
					info.setSDepositBillStartDate(transRS.getTimestamp("FIXEDNEXTSTARTDATE"));
					info.setSDepositBillEndDate(transRS.getTimestamp("FIXEDNEXTENDDATE"));
					info.setSDepositBillPeriod(transRS.getLong("FIXEDNEXTPERIOD"));
					info.setSDepositInterestDeal(transRS.getLong("FIXEDINTERESTDEAL"));
					info.setSDepositInterestToAccountID(transRS.getLong("FIXEDINTERESTTOACCOUNTID"));
					info.setSignatureValue(transRS.getString("SIGNATUREVALUE"));
					info.setActionStatus(transRS.getLong("ACTIONSTATUS"));
	                info.setSource(transRS.getLong("LSOURCE"));
	                info.setApplyCode(transRS.getString("SAPPLYCODE"));
	                info.setSBatchNo(transRS.getString("sbatchno"));
	                
	                //Boxu Add 2010-12-01 增加"本地/异地"和"是否加急"
	                info.setRemitArea(transRS.getLong("remitArea"));	//汇款区域
	                info.setRemitSpeed(transRS.getLong("remitSpeed"));	//汇款速度
	                
					coll.add(info);
				}
	        }
	        catch (Exception e) {
		        throw new ITreasuryDAOException("查询异常", e);
		    }
			
		    /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (ITreasuryDAOException e) {
		        throw new ITreasuryDAOException("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ITreasuryDAOException("查询异常",e);
        }
        finally {
        	finalizeDAO();
        }
        return (coll.size() > 0 ? coll : null);
	}
	
	public boolean findByFinance(String strPayerAcctNo, long lRemitType, double dAmount,Timestamp tsExecute,String strPayeeAcctNo,String sPayeeProv,String sPayeeCity,String sPayeeBankName,String sPayeeAcctNoZoom,String sPayeeNameZoomAcct,long lInstructionID){
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT fin.* ,");
			sbSQL.append(" cfmUser.sname confirmUserName,");
			sbSQL.append(" checkUser.sname checkUserName,");
			sbSQL.append(" signUser.sname signUserName,");
			sbSQL.append(" delUser.sname delUserName,");
			sbSQL.append(" office.sname officename,");
			sbSQL.append(" cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM ob_FinanceInStr fin,");
			sbSQL.append(" OB_USER cfmUser,");
			sbSQL.append(" OB_USER checkUser,");
			sbSQL.append(" OB_USER signUser,");
			sbSQL.append(" OB_USER delUser,");
			sbSQL.append(" office ,");
			sbSQL.append(" userinfo cpfUser");
			sbSQL.append(" WHERE fin.nconfirmuserid=cfmUser.id(+)");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+)");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+)");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+)");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) ");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+)");
			sbSQL.append(" AND fin.dtexecute=?");
			sbSQL.append(" AND fin.mamount=?");
			sbSQL.append(" AND fin.nremittype=?");
			sbSQL.append(" AND fin.id!=?");
			sbSQL.append(" AND fin.nstatus!="+OBConstant.SettInstrStatus.DELETE);
			ps = con.prepareStatement(sbSQL.toString());
			ps.setTimestamp(1, tsExecute);
			ps.setDouble(2, dAmount);
			ps.setLong(3, lRemitType);
			ps.setLong(4, lInstructionID);
			rs = ps.executeQuery();
			while (rs.next()) {
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(rs.getLong("NPAYERACCTID"),
						rs.getLong("NREMITTYPE"), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(rs.getLong("NPAYEEACCTID"),
						lRemitType, OBConstant.PayerOrPayee.PAYEE);
				if (payerInfo != null && payerInfo.getAccountNo() != null
						&& payerInfo.getAccountNo().equals(strPayerAcctNo)) {
					if (lRemitType == OBConstant.SettRemitType.INTERNALVIREMENT) {
						if (payeeInfo != null
								&& payeeInfo.getAccountNo() != null
								&& payeeInfo.getAccountNo().equals(
										strPayeeAcctNo)) {
							flag = true;
							break;
						}
					} else if (lRemitType == OBConstant.SettRemitType.BANKPAY) {
						if (payeeInfo != null
								&& payeeInfo.getAccountNo() != null
								&& payeeInfo.getAccountNo().equals(
										sPayeeAcctNoZoom)) {
							if (payeeInfo.getAccountName() != null
									&& payeeInfo.getAccountName().equals(
											sPayeeNameZoomAcct)) {
								if (payeeInfo.getProv() != null
										&& payeeInfo.getProv().equals(
												sPayeeProv)) {
									if (payeeInfo.getCity() != null
											&& payeeInfo.getCity().equals(
													sPayeeCity)) {
										if (payeeInfo.getBankName() != null
												&& payeeInfo.getBankName()
														.equals(sPayeeBankName)) {
											flag = true;
											break;
										}
									}
								}
							}
							//payeeInfo.								
						}
					}
				}
			}
		} catch (Exception e) {
			//log4j.error(e.toString());
			e.printStackTrace();
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
				//log4j.error(e.toString());
				e.printStackTrace();
			}
		}
		return flag;
		
	}
//	批量复核翻页查询
	public PageLoader queryInfo_Check(Query_FinanceInfo info) throws Exception
	{
		getCheckInfoSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT1,
			"com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public void getCheckInfoSQL(Query_FinanceInfo info)
	{
		try{
				m_sbSelect = new StringBuffer();
				m_sbSelect.append(" * \n");
				m_sbFrom = new StringBuffer();				
				m_sbFrom.append(" ( select B.mAmount,B.id,B.DTMODIFY,B.dtexecute,B.DTCONFIRM,B.ntranstype,B.SNOTE,B.NSTATUS,S.saccountno,S.SNAME NBSNAME," +
					     "  decode(nremittype,102,P.spayeebankname,'') spayeebankname  ," +
					     "  decode(nremittype,102,P.spayeename,103,S1.SNAME,S1.SNAME) spayeename  ," +
					      " decode(nremittype,102,P.spayeeacctno,103,S1.SACCOUNTNO,S1.SACCOUNTNO) spayeeacctno, \n");	
				m_sbFrom.append(" B.npayeracctid npayeracctid,B.NCONFIRMUSERID confirmUserID,B.SIGNATUREVALUE signatureValue, ");
				
				m_sbFrom.append(" B.SDEPOSITNO DepositNo,B.Npayeeacctid ,B.NINTERESTPAYEEACCTID InterestPayeeAcctID, ");
				m_sbFrom.append(" B.NREMITTYPE RemitType,B.NINTERESTREMITTYPE InterestRemitType,B.NNOTICEDAY noticeday, ");
				m_sbFrom.append(" B.SDEPOSITBILLNO depositBillNo,B.fixednextperiod depositBillPeriod, ");
				m_sbFrom.append(" B.FIXEDNEXTSTARTDATE depositBillStartDate,B.fixedinterestdeal depositInterestDeal, ");
				m_sbFrom.append(" B.FIXEDINTERESTTOACCOUNTID depositInterestToAccountID,B.NFIXEDDEPOSITTIME fixedDepositTime ");

				m_sbFrom.append(" from ob_financeinstr B ,SETT_ACCOUNT S,SETT_ACCOUNT S1,OB_PAYEEINFO P \n");
				
				
			
			m_sbFrom.append(" where ");
			m_sbFrom.append(" B.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			m_sbFrom.append(" AND B.npayeracctid=S.id(+) \n");
			m_sbFrom.append(" AND B.nclientid="+info.getClientID()+ "\n");
			m_sbFrom.append(" AND B. npayeeacctid=P.id(+) \n");
			m_sbFrom.append(" AND B. npayeeacctid=S1.id(+) \n");
			m_sbFrom.append(" AND B.nConfirmUserID != " + info.getNUserID() + " \n");
			m_sbFrom.append(" and B.sbatchno is null" + " \n");
			
			 //提交日期-从
			if (info.getStartSubmit() != null && info.getStartSubmit().trim().length() > 0 )
			{
				m_sbFrom.append(" AND B.DTCONFIRM >= to_date('"+info.getStartSubmit()+"','yyyy-mm-dd') \n");
				
			}
			
			// 提交日期-到
			if (info.getEndSubmit() != null && info.getEndSubmit().trim().length() > 0)
			{
				m_sbFrom.append(" AND B.DTCONFIRM <= to_date('"+info.getEndSubmit()+"','yyyy-mm-dd')+1 \n");
			}
			
			// 执行日期-从
			if (info.getStartExe() != null && info.getStartExe().trim().length() > 0)
			{
				m_sbFrom.append(" AND B.DTEXECUTE >= to_date('"+info.getStartExe()+"','yyyy-mm-dd') \n");
			}
			// 执行日期-到
			if (info.getEndExe() != null && info.getEndExe().trim().length() > 0 )
			{
				m_sbFrom.append(" AND B.DTEXECUTE <= to_date('"+info.getEndExe()+"','yyyy-mm-dd') \n");
			}
			
			// 交易金额-从
			if (info.getMinAmount() > 0.0)
			{
				m_sbFrom.append(" AND B.mAmount >= " + info.getMinAmount() + " \n");
			}
			// 交易金额-到
			if (info.getMaxAmount() > 0.0)
			{
				m_sbFrom.append(" AND B.mAmount <= " + info.getMaxAmount() + " \n");
			}
			//业务复核查询，查询未复核的记录:
			if (info.getNSTATUS() ==-1)
			{
				
				m_sbFrom.append(" AND B.nstatus in ("+OBConstant.SettInstrStatus.SAVE+","+OBConstant.SettInstrStatus.APPROVALED+") \n");
				m_sbFrom.append(" and ((B.nstatus = 1 and b.ntranstype not in ( select b.transtypeid from OB_APPROVALRELATIONNEW b where  clientid ="+info.getClientID()+ " and  officeid = "+info.getOfficeID()+" and currencyid ="+info.getCurrencyID()+" and b.approvalid >0 and islowerunit = 2 group by b.transtypeid))or B.nstatus = 20)\n");
			
			}
			if (info.getNSTATUS() ==OBConstant.SettInstrStatus.SAVE)
			{
				
				m_sbFrom.append(" AND B.nstatus ="+OBConstant.SettInstrStatus.SAVE+" \n");
				m_sbFrom.append(" and ((B.nstatus = 1 and b.ntranstype not in ( select b.transtypeid from OB_APPROVALRELATIONNEW b where  clientid ="+info.getClientID()+ " and  officeid = "+info.getOfficeID()+" and currencyid ="+info.getCurrencyID()+" and b.approvalid >0 and islowerunit = 2 group by b.transtypeid))or B.nstatus = 20)\n");
			
			}
		
			if (info.getNSTATUS() == OBConstant.SettInstrStatus.APPROVALED)
			{
				
				m_sbFrom.append(" AND B.nstatus = " + OBConstant.SettInstrStatus.APPROVALED + " \n");
				
			
			}   
			    if (info.getNtranstype() == 0 && info.getSign().equals("current"))
			    {
			    	m_sbFrom.append(" AND B.ntranstype in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
			    }
			    if (info.getNtranstype() == 0 && info.getSign().equals("fixd"))
			    {
			    	m_sbFrom.append(" AND B.ntranstype not in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
			    }
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_CAPTRANSFER_DOUBLE)		//逐笔付款
				{
					m_sbFrom.append(" AND B.ntranstype in( " + OBConstant.SettInstrType.CAPTRANSFER_BANKPAY+ "," +OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT+ ") \n");
					
				}
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_OPENFIXDEPOSIT)   		//定期开立
				{
					m_sbFrom.append(" AND B.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT+ " \n");
					
				}
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_FIXEDTOCURRENTTRANSFER)        //定期支取
				{
					m_sbFrom.append(" AND B.ntranstype = " + OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER+ " \n");
					
				}
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_OPENNOTIFYACCOUNT)     //通知开立
				{
					m_sbFrom.append(" AND B.ntranstype = " + OBConstant.SettInstrType.OPENNOTIFYACCOUNT+ " \n");
					
				}
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_NOTIFYDEPOSITDRAW)            //通知支取
				{
					m_sbFrom.append(" AND B.ntranstype = " + OBConstant.SettInstrType.NOTIFYDEPOSITDRAW+ " \n");
					
				}
				if (info.getNtranstype() == OBConstant.SettInstrType.BANK_DRIVEFIXDEPOSIT)            //到期续存
				{
					m_sbFrom.append(" AND B.ntranstype = " + OBConstant.SettInstrType.DRIVEFIXDEPOSIT+ " \n");
					
				}
				if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
				{
					m_sbFrom.append(" and B.SIGNATUREVALUE is not null ");
				}
				m_sbFrom.append(" ) \n");
				m_sbWhere = new StringBuffer();
				m_sbWhere.append(" 1=1");
				
				
			m_sbOrderBy = new StringBuffer();
			m_sbOrderBy.append(" order by DTCONFIRM desc,ID");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public Collection query_Uncheck(QueryCapForm qcf) throws Exception
	{
		FinanceInfo info = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector vReturn = new Vector();
		StringBuffer sbSQL = new StringBuffer();
		try
		{
			con = Database.getConnection();
			sbSQL.append(" SELECT fin.*, \n");
			sbSQL.append(" cfmUser.sname confirmUserName, checkUser.sname checkUserName, \n");
			sbSQL.append(" signUser.sname signUserName,delUser.sname delUserName, \n");
			sbSQL.append(" office.sname officename,cpfUser.sname DealUserName \n");
			sbSQL.append(" FROM OB_FinanceInstr fin, OB_User cfmUser, \n");
			sbSQL.append(" OB_User checkUser , OB_User signUser, \n");
			sbSQL.append(" OB_User delUser,office ,userinfo cpfUser \n");
			sbSQL.append(" WHERE fin.nStatus >= " + OBConstant.SettInstrStatus.SAVE + "\n");
			sbSQL.append(" AND fin.nconfirmuserid=cfmUser.id(+) \n");
			sbSQL.append(" AND fin.nsignuserid=signuser.id(+) \n");
			sbSQL.append(" AND fin.nDeleteuserid=delUser.id(+) \n");
			sbSQL.append(" AND fin.nCheckUserID=checkUser.id(+) \n");
			sbSQL.append(" AND fin.CPF_nOfficeid=office.id(+) \n");
			sbSQL.append(" AND fin.CPF_nDealUserId=cpfUser.id(+) \n");			
			if ( qcf.getCurrencyID() >0)
				sbSQL.append(" AND fin.nCurrencyID=" + qcf.getCurrencyID() + " \n");
			sbSQL.append(" AND fin.nClientID=" + qcf.getClientID() + " \n");
			//交易指令类型
			if (qcf.getTransType() == 0 && qcf.getSign().equals("current"))
			{
				sbSQL.append(
						" AND fin.ntranstype in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
			}	
			if (qcf.getTransType() == 0 && qcf.getSign().equals("fixd"))
			{
				sbSQL.append(
						" AND fin.ntranstype not in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
			}	
			if (qcf.getTransType() > 0)
			{
				if (qcf.getTransType() == 1)
				{
					sbSQL.append(
						" AND fin.ntranstype in ("
							+ OBConstant.SettInstrType.CAPTRANSFER_SELF
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_BANKPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_OTHER
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_FINCOMPANYPAY
							+ ","
							+ OBConstant.SettInstrType.CAPTRANSFER_PAYSUBACCOUNT
							+ ","
							+ OBConstant.SettInstrType.BANKPAY_DOWNTRANSFER
							+ ") \n");
				}
				else if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.SettInstrType.OPENFIXDEPOSIT + " \n ");
					sbSQL.append(" AND fin.ndepositbillstatusid is not null \n ");		
				}
				else if(qcf.getTransType() == OBConstant.QueryInstrType.OPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
					sbSQL.append(" AND (fin.ndepositbillstatusid is null or fin.ndepositbillstatusid=");		
					sbSQL.append(OBConstant.SettInstrStatus.SAVE);	
					sbSQL.append(")\n ");
					sbSQL.append(" and fin.settfinid is null");
				}
				else
				{
					sbSQL.append(" AND fin.ntranstype = " + OBConstant.getTransTypeByQueryType(qcf.getTransType()) + " \n");
					sbSQL.append(" AND fin.ndepositbillstatusid is null \n ");		
				}
			}
			else
			{
				if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
				{
					//取消复核查询，不能查询出挂接审批流并且是自动复核的记录，原因：挂接审批流并且自动复核的记录只有在取消审批菜单处做动作
					String tempAutoTransTypes = checkTransType(qcf);
					if(tempAutoTransTypes!=null && tempAutoTransTypes.length()>0)
					{
						sbSQL.append(" AND fin.ntranstype not in( " + tempAutoTransTypes + ") \n");
					}
				}
			}
			//业务复核查询，查询未复核的记录：非登录人录入的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SAVE) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SAVE + " \n");
				}
				else
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.SAVE + " \n");
				sbSQL.append(" AND fin.nConfirmUserID != " + qcf.getUserID() + " \n");
			}
			//业务复核查询，查询已复核的记录：登录人复核的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.CHECK))
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
					sbSQL.append(" AND fin.ndepositbillcheckuserid = " + qcf.getUserID() + " \n");
				}
				else
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.CHECK + " \n");
					sbSQL.append(" AND fin.nCheckUserID = " + qcf.getUserID() + " \n");
				}
			}
			//业务签认查询，查询未签认的记录：指定登录人签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.CHECK) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{    sbSQL.append(" AND fin.sbatchno is  null\n");
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.CHECK + " \n");
				}
				else
					sbSQL.append(" AND ((fin.nStatus = " 
							+ OBConstant.SettInstrStatus.CHECK 
							+ ") or (fin.ntranstype="
							+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
							+ " and fin.nstatus="+OBConstant.SettInstrStatus.FINISH 
							+ " and fin.ndepositbillstatusid="
							+ OBConstant.SettInstrStatus.CHECK 
							+ ")) \n");
				sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) >= " + this.getMinSignAmount(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID())+ " \n");
				sbSQL.append(" AND (NVL(fin.mAmount,0)+NVL(fin.mRealInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealCompoundInterest,0)+NVL(fin.mRealOverdueInterest,0)+ \n");
				sbSQL.append(" 	NVL(fin.mRealSuretyFee,0)+NVL(fin.mRealCommission,0)) < " + this.getMaxSignAmount(qcf.getUserID(), qcf.getClientID(), qcf.getCurrencyID()) + " \n");
				sbSQL.append(" AND fin.nIsCanAccept != 1 \n");
			}
			//业务签认查询，查询已签认的记录：指定登录人签认并且已经签认的本单位交易指令
			if ((qcf.getStatus() == OBConstant.SettInstrStatus.SIGN) && (qcf.getOperationTypeID() == OBConstant.QueryOperationType.SIGN))
			{   
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" AND fin.nstatus = " + OBConstant.SettInstrStatus.FINISH + " \n");
					sbSQL.append(" and fin.ndepositbillstatusid= " + OBConstant.SettInstrStatus.SIGN + " \n");
					sbSQL.append(" AND fin.ndepositbillsignuserid = " + qcf.getUserID() + " \n");
				}
				else
				{
					sbSQL.append(" AND ((fin.nstatus = " 
							+ OBConstant.SettInstrStatus.SIGN 
							+ ") or (fin.ntranstype="
							+ OBConstant.SettInstrType.OPENFIXDEPOSIT 
							+ " and fin.nstatus="
							+ OBConstant.SettInstrStatus.FINISH 
							+ " and fin.ndepositbillstatusid="
							+ OBConstant.SettInstrStatus.SIGN 
							+ ")) \n");
					sbSQL.append(" AND fin.nSignUserID = " + qcf.getUserID() + " \n");
				}
				sbSQL.append(" AND fin.nIsCanAccept = 1 \n");
				sbSQL.append(" AND fin.sbatchno is  null\n");
			}
			//交易申请查询，查询所有状态
			if (qcf.getStatus() > 0 && qcf.getOperationTypeID() == OBConstant.QueryOperationType.QUERY)
			{
				if(qcf.getTransType() == OBConstant.QueryInstrType.BILLOPENFIXDEPOSIT)
				{
					sbSQL.append(" and fin.ndepositbillstatusid= " + qcf.getStatus() + " \n");
				}
				else
				{
					//交易申请查询，查询所有状态
					sbSQL.append(" AND fin.nstatus = " + qcf.getStatus() + " \n");
				}
			}
			// 提交日期-从1
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM >= ? \n");
			}
			// 提交日期-到
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTCONFIRM <= ? \n");
			}
			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE >= ? \n");
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				sbSQL.append(" AND fin.DTEXECUTE <= ? \n");
			}
			// 交易金额-从
			if (qcf.getMinAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount >= " + qcf.getMinAmount() + " \n");
			}
			// 交易金额-到
			if (qcf.getMaxAmount() > 0.0)
			{
				sbSQL.append(" AND mAmount <= " + qcf.getMaxAmount() + " \n");
			}
			// 合同ID
			if (qcf.getContractID() != -1)
			{
				sbSQL.append(" AND nContractID = " + qcf.getContractID() + " \n");
			}
			// 放款通知单ID
			if (qcf.getDepositID() != -1)
			{
				sbSQL.append(" AND fin.NSUBACCOUNTID = " + qcf.getDepositID() + " \n");
			}
			//下属单位
			if (qcf.getChildClientID() > 0)
			{
				sbSQL.append(" AND nChildClientid=" + qcf.getChildClientID() + " \n");
			}
			if (qcf.getChildClientID() == -2)
			{
				sbSQL.append(" AND nChildClientid > 0 \n");
			}
			
			if(!Config.getProperty(ConfigConstant.GLOBAL_TROY_NAME,Constant.GlobalTroyName.NotUseCertificate).equals(Constant.GlobalTroyName.NotUseCertificate))
			{
				sbSQL.append(" and fin.signaturevalue is not null \n");
			}			

			if (qcf.isOrderBy())
			{
				sbSQL.append(" order by  fin.id desc ,TO_CHAR(fin.dtconfirm,'YYYY-MM-DD') desc ,fin.nPayerAcctID \n");
			}
			else
			{
				sbSQL.append(" ORDER BY fin.id desc ,fin.dtconfirm ASC ,fin.nPayerAcctID \n");
			}
			log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			// 提交日期-从
			if (qcf.getStartSubmit() != null && qcf.getStartSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartSubmit().trim() + " 00:00:00"));
			}
			// 提交日期-到 
			if (qcf.getEndSubmit() != null && qcf.getEndSubmit().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndSubmit().trim() + " 23:59:59"));
			}
			// 执行日期-从
			if (qcf.getStartExe() != null && qcf.getStartExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getStartExe().trim() + " 00:00:00"));
			}
			// 执行日期-到
			if (qcf.getEndExe() != null && qcf.getEndExe().trim().length() > 0)
			{
				ps.setTimestamp(nIndex++, Timestamp.valueOf(qcf.getEndExe().trim() + " 23:59:59"));
			}
			rs = ps.executeQuery();
			while (rs.next())
			{
				info = new FinanceInfo();
				info.setID(rs.getLong("ID")); // 指令序号
				info.setChildClientID(rs.getLong("nChildClientid")); //下属单位
				//				下属单位
				if (info.getChildClientID() > 0)
				{
					info.setChildClientName(NameRef.getClientNameByID(info.getChildClientID()));
					info.setChildClientNo(NameRef.getClientCodeByID(info.getChildClientID()));
				}
				info.setClientID(rs.getLong("NCLIENTID")); // 交易提交单位
				info.setCurrencyID(rs.getLong("NCURRENCYID")); // 交易币种
				info.setTransType(rs.getLong("NTRANSTYPE")); // 网上交易类型
				info.setRemitType(rs.getLong("NREMITTYPE")); // 汇款方式
				// 收款方名称
				info.setPayerAcctID(rs.getLong("NPAYERACCTID")); // 付款方账户ID				
				info.setPayeeAcctID(rs.getLong("NPAYEEACCTID")); //收款方账户ID
				info.setAmount(rs.getDouble("MAMOUNT")); // 交易金额
				info.setExecuteDate(rs.getTimestamp("DTEXECUTE")); // 执行日
				info.setNote(rs.getString("SNOTE")); // 汇款用途/摘要
				info.setFixedDepositTime(rs.getLong("NFIXEDDEPOSITTIME"));
				info.setSubAccountID(rs.getLong("NSUBACCOUNTID")); //子账户ID
				info.setContractID(rs.getLong("NCONTRACTID")); // 贷款合同id
				info.setLoanContractNo(NameRef.getContractNoByID(info.getContractID())); // 贷款合同编号
				info.setLoanNoteID(rs.getLong("NLOANNOTEID")); //放款通知单id
				info.setLetOutCode(NameRef.getPayFormNoByID(info.getLoanNoteID())); //放款通知单号
				info.setPayDate(rs.getTimestamp("DTPAY")); // 贷款放款日期
				info.setDepositNo(rs.getString("SDEPOSITNO")); //定期（通知）存款单据号
				info.setDepositStart(rs.getTimestamp("DTDEPOSITSTART")); //定期（通知）存款起始日
				info.setDepositRate(rs.getDouble("MDEPOSITRATE")); //定期存单利率
				info.setDepositAmount(rs.getDouble("MDEPOSITAMOUNT")); //存单金额（开立金额）
				info.setDepositBalance(rs.getDouble("MDEPOSITBALANCE")); //存单余额
				info.setInterestPayeeAcctID(rs.getLong("NINTERESTPAYEEACCTID")); //利息收款方账户ID
				info.setInterestRemitType(rs.getLong("NINTERESTREMITTYPE")); //利息付款方式
				info.setInterest(rs.getDouble("MINTEREST")); //应付贷款利息
				info.setCompoundInterest(rs.getDouble("MCOMPOUNDINTEREST")); //应付复利
				info.setOverdueInterest(rs.getDouble("MOVERDUEINTEREST")); //应付逾期罚息
				info.setSuretyFee(rs.getDouble("MSURETYFEE")); //应付担保费
				info.setCommission(rs.getDouble("MCOMMISSION")); //应付手续费
				info.setRealInterest(rs.getDouble("MREALINTEREST")); //实付贷款利息
				info.setRealCompoundInterest(rs.getDouble("MREALCOMPOUNDINTEREST")); //实付复利
				info.setRealOverdueInterest(rs.getDouble("MREALOVERDUEINTEREST")); //实付逾期罚息
				info.setRealSuretyFee(rs.getDouble("MREALSURETYFEE")); //实付担保费
				info.setRealCommission(rs.getDouble("MREALCOMMISSION")); //实付手续费
				info.setStatus(rs.getLong("NSTATUS")); // 指令状态
				info.setOfficeID(rs.getLong("CPF_NOFFICEID")); //CPF-默认办事处ID
				info.setTransNo(rs.getString("CPF_STRANSNO")); //交易号
				info.setConfirmDate(rs.getTimestamp("DTCONFIRM")); //确认日期
				info.setConfirmUserID(rs.getLong("NCONFIRMUSERID")); //确认人ID	
				info.setConfirmUserName(rs.getString("confirmUserName")); // 确认人姓名
				info.setCheckDate(rs.getTimestamp("DTCHECK")); //复核日期
				info.setCheckUserID(rs.getLong("NCHECKUSERID")); //复核人ID
				info.setCheckUserName(rs.getString("checkUserName")); // 复核人姓名
				info.setSignDate(rs.getTimestamp("DTSIGN")); //签认日期
				info.setSignUserID(rs.getLong("NSIGNUSERID")); //签认人ID
				info.setSignUserName(rs.getString("signUserName")); // 签认人姓名
				info.setDeleteDate(rs.getTimestamp("DTDELETE")); //删除日期
				info.setDeleteUserID(rs.getLong("NDELETEUSERID")); //删除人ID
				info.setDeleteUserName(rs.getString("delUserName")); // 删除人姓名
				info.setDealDate(rs.getTimestamp("CPF_DTDEAL")); //CPF-处理日期
				info.setDealUserID(rs.getLong("CPF_NDEALUSERID")); //CPF-处理人ID
				info.setDealUserName(rs.getString("DealUserName")); // CPF-处理人姓名
				info.setReject(rs.getString("CPF_SREJECT")); // CPF-拒绝原因
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setSDepositBillNo(rs.getString("sDepositBillNo"));
				info.setNDepositBillStatusId(rs.getLong("nDepositBillStatusId"));
				info.setNDepositBillInputuserId(rs.getLong("nDepositBillInputuserId"));
				info.setNDepositBillCheckuserId(rs.getLong("nDepositBillCheckuserId"));
				info.setDtDepositBillInputdate(rs.getTimestamp("dtDepositBillInputdate"));
				info.setDtDepositBillCheckdate(rs.getTimestamp("dtDepositBillCheckdate"));
				info.setSDepositBillAbstract(rs.getString("sDepositBillAbstract"));
				info.setSDepositBillCheckAbstract(rs.getString("sDepositBillCheckAbstract"));
				info.setDtModify(rs.getTimestamp("dtmodify"));//上次修改时间  ---  add   by  zhanglei  2010.06.03
				info.setSignatureValue(rs.getString("signaturevalue"));
				info.setSignatureOriginalValue(rs.getString("signatureoriginalvalue"));
				
				//获取收款方和付款方的详细信息
				PayerOrPayeeInfo payerInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo payeeInfo = new PayerOrPayeeInfo();
				PayerOrPayeeInfo interestpPayeeInfo = new PayerOrPayeeInfo();
				payerInfo = getPayerOrPayeeInfoByID(info.getPayerAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYER);
				payeeInfo = getPayerOrPayeeInfoByID(info.getPayeeAcctID(), info.getRemitType(), OBConstant.PayerOrPayee.PAYEE);
				interestpPayeeInfo = getPayerOrPayeeInfoByID(info.getInterestPayeeAcctID(), info.getInterestRemitType(), OBConstant.PayerOrPayee.PAYEE);
				info.setPayerBankNo(payerInfo.getBankNo()); // 付款方银行编号				
				info.setPayerAcctNo(payerInfo.getAccountNo()); // 付款方账号
				info.setPayerName(payerInfo.getAccountName()); // 付款方名称
				info.setPayeeName(payeeInfo.getAccountName()); // 收款方名称
				info.setPayeeBankNo(payeeInfo.getBankNo()); // 收款方银行编号				
				info.setPayeeAcctNo(payeeInfo.getAccountNo()); // 收款方账号
				info.setPayeeProv(payeeInfo.getProv()); // 汇入省
				info.setPayeeCity(payeeInfo.getCity()); // 汇入市
				info.setPayeeBankName(payeeInfo.getBankName()); // 汇入行名称
				info.setInterestPayeeName(interestpPayeeInfo.getAccountName()); //利息收款方名称
				info.setInterestPayeeBankNo(interestpPayeeInfo.getBankNo()); // 利息收款方银行编号				
				info.setInterestPayeeAcctNo(interestpPayeeInfo.getAccountNo()); // 利息收款方账号
				info.setInterestPayeeProv(interestpPayeeInfo.getProv()); // 利息汇入省
				info.setInterestPayeeCity(interestpPayeeInfo.getCity()); // 利息汇入市
				info.setInterestPayeeBankName(interestpPayeeInfo.getBankName()); // 利息汇入行名称
				info.setSBatchNo(rs.getString("SBATCHNO"));//批量付款批次号 modify by xwhe 2008-11-10
				
				info.setSDepositBillPeriod(rs.getLong("fixednextperiod"));  //定期存款期限
				info.setSDepositBillStartDate(rs.getTimestamp("FIXEDNEXTSTARTDATE"));  //存单起始日
				info.setSDepositInterestDeal(rs.getLong("fixedinterestdeal"));//利息处理方式
				info.setSDepositInterestToAccountID(rs.getLong("FIXEDINTERESTTOACCOUNTID")); //利息转至活期账户id
				info.setNoticeDay(rs.getLong("NNOTICEDAY"));  //通知存款品种
				
				vReturn.add(info);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			log4j.info("query result : " + vReturn.size());
		}
		catch (Exception e)
		{
			log4j.info("FinanceInstrEJB.query():SQL==" + sbSQL.toString());
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return vReturn.size() > 0 ? vReturn : null;
	}
	public ArrayList selectUserAuthority(long lUserID,long currencyType) throws Exception
	{
		ArrayList list = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			con = Database.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.saccountno ");
			sql.append(" from OB_AccountOwnedByUser a, Sett_Account ai ");
			sql.append(" where ai.nStatusID=1 ");
			sql.append(" and a.saccountno=ai.saccountno ");
			sql.append(" and a.nUserID="+lUserID);
			sql.append(" and ai.ncurrencyid="+currencyType);
			
			
			log4j.info("lUserID=" + lUserID);
			log4j.info("currencyType=" + currencyType);
			log4j.info("SQL=\n" + sql.toString());
			
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			list = new ArrayList();
			String temp = "";
			while(rs.next())
			{
				temp=rs.getString("saccountno");
				
				list.add(temp);
			
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
			
		}
		catch(Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			if(rs!=null)
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
		return list.size()>0?list:null;
	}
	
	/**
	 * 获得收款方账户进行某项交易的权限
	 * Create Date: 2010-10-4
	 * @param lPayeeAcctID 收款方账户ID
	 * @param lInstrTypeID 指令类型
	 * @param lCurrencyID 币种ID
	 * @param lClientID 客户ID
	 * @return boolean 是否有权限
	 * @exception Exception
	 */
	public boolean getPayeeAcctPrvg(long lPayeeAcctID, long lInstrTypeID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean bIsPrvg = false;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT * FROM OB_AccountPrvg  ";
			strSQL += " WHERE nAccountID =" + lPayeeAcctID;
			strSQL += " AND ntranstype =" + lInstrTypeID;
			log4j.info("*******判断账户权限*************");
			log4j.info("getAccountPrvg:SQL=");
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				bIsPrvg = true;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return bIsPrvg;
	}
	/**
	 * 逐笔付款 SAP指令号是否重复
	 * @param sApplyCode
	 * @param lSource
	 * @return
	 * @throws Exception
	 */
	public boolean isSameApplyCode(long lID,String sApplyCode,long lSource) throws Exception
	{
		boolean isSameApplyCode = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		try
		{
			 con = Database.getConnection();
			 sql = new StringBuffer();
			 sql.append(" select * from ob_FinanceInStr ");
			 sql.append(" where sapplycode ='"+sApplyCode+"'");
			 sql.append(" and lsource ="+lSource);
			 sql.append(" and id !="+lID);
			 sql.append(" and nstatus ="+Constant.RecordStatus.VALID);
			 
			 
			 log4j.print("sql="+sql.toString());
			 ps = con.prepareStatement(sql.toString());
			 rs = ps.executeQuery();
			 if(rs!=null&&rs.next())
			 {
				 isSameApplyCode = true;
				 
			 }
			 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return isSameApplyCode;
	}
	
	public BranchbankInfo findBranchBankByBankCode(BranchbankInfo queryInfo) throws Exception
	{
		BranchbankInfo bankInfo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		try
		{
			sql = new StringBuffer();
			con = Database.getConnection();
			sql.append(" select b.sname BranchName, b.scode BranchNo, b.id BranchID ");
			sql.append(" from EP_BRANCHMATCHSETTING e, sett_Branch b ");
			sql.append(" where e.bankmatch = b.id ");
			sql.append(" and e.status ="+Constant.RecordStatus.VALID);
			sql.append(" and b.nstatusid ="+Constant.RecordStatus.VALID);
			sql.append(" and e.banktype ="+queryInfo.getBranchID());
			if(queryInfo.getOfficeID()>0)
			{
				sql.append(" and e.officeid ="+queryInfo.getOfficeID());
			}
			log.info("sql="+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				bankInfo = new BranchbankInfo();
				bankInfo.setBranchID(rs.getLong("BranchID"));
				bankInfo.setBranchCode(rs.getString("BranchNo"));
				bankInfo.setBranchName(rs.getString("BranchName"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}		
		return bankInfo;
	}
	
	public long findOfficeByAccountId(long lAccountId) throws Exception
	{
		long lOfficeId = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;		
		try
		{
			sql = new StringBuffer();
			con = Database.getConnection();			
			sql.append(" select s.nofficeid from sett_account s ");
			sql.append(" where s.id ="+lAccountId);
			log4j.info("sql="+sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();		
			if(rs.next())
			{
				lOfficeId = rs.getLong("nofficeid");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			log4j.error(e.toString());
			throw new IException("获取机构id错误!",e);
		}
		finally
		{
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
			
		}
		return lOfficeId;
	}
	
    public ArrayList findOfficeInformation(long clientId) throws Exception
    {
    	ArrayList list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;	
		OfficeInfo info = null;
    	try
    	{
    		sql = new StringBuffer();
    		con = Database.getConnection();	
    		sql.append(" select distinct o.id, o.sname ");
    		sql.append(" from office o, sett_account s ");
    		sql.append(" where s.nofficeid = o.id ");
    		sql.append(" and s.nstatusid > 0 ");
    		sql.append(" and o.nstatusid = 1 ");
    		sql.append(" and s.nclientid ="+clientId);
    		sql.append(" order by o.id ");
    		log4j.info(sql.toString());
    		ps = con.prepareStatement(sql.toString());
    		rs = ps.executeQuery();
    		while(rs.next())
    		{
    			info = new OfficeInfo();
    			info.setM_lID(rs.getLong("id"));
    			info.setM_strName(rs.getString("sname"));
    			list.add(info);
    			
    		}
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		log4j.error(e.toString());
    		throw new IException("查询机构出错!",e);
    	}
		finally
		{
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
		}    	
    	return list;
    }
    /**
	 * 获得账户的网上交易金额（网上银行模块种此账户的所有已经保存确认复核签认的付款指令金额值和）
	 * Create Date: 2012-05-24
	 * @param lAccountID 账户ID
	 * @param lCurrencyID 币种
	 * @param strTransactionNo 当前交易号
	 * @param DepositNo 存单号
	 * @return double
	 * @exception Exception
	 */
	public double getUsableBalanceByAccountID(long lAccountID, long lCurrencyID, long lInstructionID,String DepositNo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double dReturn = 0.0;
		try
		{
			con = Database.getConnection();
			String strSQL = "";
			strSQL = " SELECT (SUM(mAmount)+SUM(MREALINTEREST)+SUM(MREALCOMPOUNDINTEREST)+SUM(MREALOVERDUEINTEREST)+SUM(MREALSURETYFEE)+SUM(MREALCOMMISSION))  aa ";
			strSQL += " FROM ob_FinanceInStr";
			strSQL += " WHERE 1=1"; 
			if (lCurrencyID>0){	
				strSQL+=" AND ncurrencyid = " + lCurrencyID;
			}
			if(DepositNo!=null&&DepositNo.trim().length()>0){
				strSQL+=" AND sdepositno = '" + DepositNo+"'";
				strSQL += " AND NTRANSTYPE IN ("+OBConstant.SettInstrType.FIXEDTOCURRENTTRANSFER+","+OBConstant.SettInstrType.NOTIFYDEPOSITDRAW+")";
			}
			strSQL += " AND npayeracctID = '" + lAccountID + "'";
			strSQL += " AND (nStatus = " + OBConstant.SettInstrStatus.SAVE;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.APPROVALING;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.APPROVALED;
			strSQL += " OR nStatus = " + OBConstant.SettInstrStatus.CHECK;
			strSQL += " OR (nStatus = " + OBConstant.SettInstrStatus.SIGN+ ")";
			strSQL += " OR (nStatus = " + OBConstant.SettInstrStatus.DEAL + " and  cpf_stransno is null))";
			if (lInstructionID != -1)
			{
				strSQL += " AND ID != " + lInstructionID;
			}
			log4j.info("SQL= \n" + strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				dReturn = rs.getDouble(1);
			}
			rs.close();
			log4j.print("----strSQL----="+strSQL);
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
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
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dReturn;
	}
	
}

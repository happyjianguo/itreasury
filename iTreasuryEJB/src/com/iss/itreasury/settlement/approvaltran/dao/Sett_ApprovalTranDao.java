package com.iss.itreasury.settlement.approvaltran.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.bizdelegation.TransCurrentDepositDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransDiscountDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransGeneralLedgerDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransLoanDelegation;
import com.iss.itreasury.settlement.bizdelegation.TransSpecialOperationDelegation;
import com.iss.itreasury.settlement.capitaltransfer.dao.CapitalTransferApplyDao;
import com.iss.itreasury.settlement.transcurrentdeposit.dao.Sett_TransCurrentDepositDAO;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.transdiscount.dao.Sett_TransGrantDiscountDAO;
import com.iss.itreasury.settlement.transdiscount.dataentity.TransGrantDiscountInfo;
import com.iss.itreasury.settlement.transgeneralledger.dao.Sett_TransGeneralLedgerDAO;
import com.iss.itreasury.settlement.transgeneralledger.dataentity.TransGeneralLedgerInfo;
import com.iss.itreasury.settlement.transloan.dao.Sett_TransGrantLoanDAO;
import com.iss.itreasury.settlement.transloan.dataentity.TransGrantLoanInfo;
import com.iss.itreasury.settlement.transspecial.dao.Sett_TransSpecialOperationDAO;
import com.iss.itreasury.settlement.transspecial.dataentity.TransSpecialOperationInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.bizdelegation.ApprovalDelegation;
import com.iss.itreasury.system.approval.dataentity.ApprovalTracingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Env;
public class Sett_ApprovalTranDao extends SettlementDAO
{

    public Sett_ApprovalTranDao(){
    	super("loan_approvaltracing",false);
        super.setUseMaxID();    
    }
    /**
	 *  根据条件查询需要登录人审批的记录(银行付款、内部转帐、委托存款)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
	public Collection findByAppBankConditions(TransCurrentDepositInfo info,long userID) throws Exception
	{
		Collection res =  null;
		String sql="";
		try{
			initDAO();
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid from sett_TransCurrentDeposit c";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+info.getTransactionTypeID()+"";
			sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+SETTConstant.TransactionStatus.APPROVALING+"";
			if(info.getPayClientID()>0){
				sql+=" and c.NPAYCLIENTID ="+info.getPayClientID()+" \n";				
			}
			if(info.getPayAccountID()>0){
				sql+=" and c.NPAYACCOUNTID ="+info.getPayAccountID()+" \n";						
			}
			if(info.getReceiveClientID()>0){
				sql+=" and c.NRECEIVECLIENTID ="+info.getReceiveClientID()+" \n";						
			}
			if(info.getReceiveAccountID()>0){
				sql+=" and c.NRECEIVEACCOUNTID ="+info.getReceiveAccountID()+" \n";	
			}
			if(info.getAmount()>0){
				sql+=" and c.MAMOUNT="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and c.STRANSNO='"+info.getTransNo()+"' \n";
			}
		    if(info.getBankID()>0){
		    	sql+=" and c.NBANKID="+info.getBankID()+" \n";
		    }	  	
		    if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND c.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}		       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND c.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
		    sql+=") union ( ";
//		    sql+=" f.NINPUTUSERID="+userID+" and f.nstatusid="+SETTConstant.TransactionStatus.APPROVAL+"";
//		    if(info.getPayClientID()>0){
//				sql+=" and f.NPAYCLIENTID ="+info.getPayClientID()+" \n";				
//			}
//			if(info.getPayAccountID()>0){
//				sql+=" and f.NPAYACCOUNTID ="+info.getPayAccountID()+" \n";						
//			}
//			if(info.getReceiveClientID()>0){
//				sql+=" and f.NRECEIVECLIENTID ="+info.getReceiveClientID()+" \n";						
//			}
//			if(info.getReceiveAccountID()>0){
//				sql+=" and f.NRECEIVEACCOUNTID ="+info.getReceiveAccountID()+" \n";	
//			}
//			if(info.getAmount()>0){
//				sql+=" and f.MAMOUNT="+info.getAmount()+" \n";				
//			}
//			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
//				sql+=" and f.STRANSNO='"+info.getTransNo()+"' \n";
//			}
//		    if(info.getBankID()>0){
//		    	sql+=" and f.NBANKID="+info.getBankID()+" \n";
//		    }	  	
//		    if (info.getInterestStartDate() != null){
//				String time = (info.getInterestStartDate()).toString();
//				time = time.substring(0, 10);
//				sql+=" AND f.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
//			}		       
//		    if(info.getTransactionTypeID()>0){
//		    	sql+=" AND f.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
//		    }
//		    sql+=")";
		    
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from sett_transcurrentdeposit aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.mamount>rr.moneysegment and rr.currencyid =" +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getPayClientID()>0){
				sql+=" and aa.NPAYCLIENTID ="+info.getPayClientID()+" \n";				
			}
			if(info.getPayAccountID()>0){
				sql+=" and aa.NPAYACCOUNTID ="+info.getPayAccountID()+" \n";						
			}
			if(info.getReceiveClientID()>0){
				sql+=" and aa.NRECEIVECLIENTID ="+info.getReceiveClientID()+" \n";						
			}
			if(info.getReceiveAccountID()>0){
				sql+=" and aa.NRECEIVEACCOUNTID ="+info.getReceiveAccountID()+" \n";	
			}
			if(info.getAmount()>0){
				sql+=" and aa.MAMOUNT="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getTransNo()+"' \n";
			}
		    if(info.getBankID()>0){
		    	sql+=" and aa.NBANKID="+info.getBankID()+" \n";
		    }	  	
		    if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}		       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND aa.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from sett_transcurrentdeposit aa,loan_approvalrelation rr";
			//增加关于币种的判断-mhjin-东方电气
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.mamount>rr.moneysegment and rr.currencyid =" +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getPayClientID()>0){
				sql+=" and aa.NPAYCLIENTID ="+info.getPayClientID()+" \n";				
			}
			if(info.getPayAccountID()>0){
				sql+=" and aa.NPAYACCOUNTID ="+info.getPayAccountID()+" \n";						
			}
			if(info.getReceiveClientID()>0){
				sql+=" and aa.NRECEIVECLIENTID ="+info.getReceiveClientID()+" \n";						
			}
			if(info.getReceiveAccountID()>0){
				sql+=" and aa.NRECEIVEACCOUNTID ="+info.getReceiveAccountID()+" \n";	
			}
			if(info.getAmount()>0){
				sql+=" and aa.MAMOUNT="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getTransNo()+"' \n";
			}
		    if(info.getBankID()>0){
		    	sql+=" and aa.NBANKID="+info.getBankID()+" \n";
		    }	  	
		    if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}		       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND aa.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql +=")";
			System.out.println("查询语句SQL^^^^^^^^^^^"+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();		
			Sett_TransCurrentDepositDAO seDao=new Sett_TransCurrentDepositDAO();
			//res = seDao.getInfoFromResultSet(transRS);	
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}	
		return res;
	}
	/**
	 *  根据条件查询需要登录人审批的记录(总帐类)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
	public Collection findByAppGenerallConditions(TransGeneralLedgerInfo info,long userID) throws Exception
	{
		Collection res =  null;
		String sql="";
		try{
			
			initDAO();
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid from SETT_TRANSGENERALLEDGER c ";														
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+info.getTransActionTypeID()+"";
			sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID ) ) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+SETTConstant.TransactionStatus.APPROVALING+"";		
			if(info.getClientID()>0){
				sql+=" and c.NCLIENTID ="+info.getClientID()+" \n";				
			}
			if(info.getAccountID()>0){
				sql+=" and c.NACCOUNTID ="+info.getAccountID()+" \n";						
			}
			if(info.getAmount()>0){ 
				sql+=" and (c.mamount+c.mone+c.mtwo+c.MTHREE)/2="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and c.STRANSNO='"+info.getTransNo()+"' \n";
			}
			if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND c.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}					
			sql+=" and c.ntransactiontypeid="+SETTConstant.TransactionType.GENERALLEDGER+"";
			sql+=") union (";
			
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from SETT_TRANSGENERALLEDGER aa,loan_approvalrelation rr";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and (aa.mamount+aa.mone+aa.mtwo+aa.MTHREE)/2>rr.moneysegment and rr.currencyid = " +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getClientID()>0){
				sql+=" and aa.NCLIENTID ="+info.getClientID()+" \n";				
			}
			if(info.getAccountID()>0){
				sql+=" and aa.NACCOUNTID ="+info.getAccountID()+" \n";						
			}
			if(info.getAmount()>0){ 
				sql+=" and (aa.mamount+aa.mone+aa.mtwo+aa.MTHREE)/2="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getTransNo()+"' \n";
			}
			if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}		       
		    
		    sql +=" and aa.ntransactiontypeid="+SETTConstant.TransactionType.GENERALLEDGER+"";
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from SETT_TRANSGENERALLEDGER aa,loan_approvalrelation rr";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and (aa.mamount+aa.mone+aa.mtwo+aa.MTHREE)/2>rr.moneysegment and rr.currencyid = " +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getClientID()>0){
				sql+=" and aa.NCLIENTID ="+info.getClientID()+" \n";				
			}
			if(info.getAccountID()>0){
				sql+=" and aa.NACCOUNTID ="+info.getAccountID()+" \n";						
			}
			if(info.getAmount()>0){ 
				sql+=" and (aa.mamount+aa.mone+aa.mtwo+aa.MTHREE)/2="+info.getAmount()+" \n";				
			}
			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getTransNo()+"' \n";
			}
			if (info.getInterestStartDate() != null){
				String time = (info.getInterestStartDate()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}		       
		    
		    sql +=" and aa.ntransactiontypeid="+SETTConstant.TransactionType.GENERALLEDGER+"";
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			
//			if(info.getClientID()>0){
//				sql+=" and f.NCLIENTID ="+info.getClientID()+" \n";				
//			}
//			if(info.getAccountID()>0){
//				sql+=" and f.NACCOUNTID ="+info.getAccountID()+" \n";						
//			}
//			if(info.getAmount()>0){ 
//				sql+=" and f.MAMOUNT="+info.getAmount()+" \n";				
//			}
//			if (info.getTransNo() != null && info.getTransNo().compareToIgnoreCase("") != 0){			
//				sql+=" and f.STRANSNO='"+info.getTransNo()+"' \n";
//			}
//			if (info.getInterestStartDate() != null){
//				String time = (info.getInterestStartDate()).toString();
//				time = time.substring(0, 10);
//				sql+=" AND f.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
//			}
			//sql+=" and aa.ntransactiontypeid="+SETTConstant.TransactionType.GENERALLEDGER+"";
			sql+= ")";
			
			System.out.println("查询语句SQL^^^^^^^^^^^"+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();		
			Sett_TransGeneralLedgerDAO seDao=new Sett_TransGeneralLedgerDAO();
			//res = seDao.getInfoFromResultSet(transRS);	
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}	
		return res;
	}
	/**
	 *  根据条件查询需要登录人审批的记录(特殊交易)--add by 刘洋
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
	public Vector findByAppCorpsConditions(TransSpecialOperationInfo info,long typeID,long userID) throws Exception
	{
		Vector v =  new Vector();
		String sql="";
		try{
			
			initDAO();
			sql="(SELECT c.* from sett_transspecialoperation c ";														
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+typeID+"";
			sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID ) ) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+SETTConstant.TransactionStatus.APPROVALING+"";		
			if(info.getNpayclientid()>0){
				sql+=" and c.NPAYCLIENTID ="+info.getNpayclientid()+" \n";				
			}
			if(info.getNpayaccountid()>0){
				sql+=" and c.NPAYACCOUNTID ="+info.getNpayaccountid()+" \n";						
			}
			if(info.getNpaybankid()>0){
				sql+=" and c.NPAYBANKID ="+info.getNpaybankid()+" \n";					
			}
			if(info.getNreceiveclientid()>0){
				sql+=" and c.NRECEIVECLIENTID ="+info.getNreceiveclientid()+" \n";				
			}
			if(info.getNreceiveaccountid()>0){
				sql+=" and c.NRECEIVEACCOUNTID ="+info.getNreceiveaccountid()+" \n";						
			}
			if(info.getNreceivebankid()>0){
				sql+=" and c.NRECEIVEBANKID ="+info.getNreceivebankid()+" \n";					
			}
			if(info.getMpayamount()>0){ 
				sql+=" and c.MPAYAMOUNT="+info.getMpayamount()+" \n";				
			}
			if (info.getStransno() != null && info.getStransno().compareToIgnoreCase("") != 0){			
				sql+=" and c.STRANSNO='"+info.getStransno()+"' \n";
			}
			if (info.getDtintereststart() != null){
				String time = (info.getDtintereststart()).toString();
				time = time.substring(0, 10);
				sql+=" AND c.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}					
			if(info.getNoperationtypeid()>0){
				sql+=" AND c.noperationtypeid = "+info.getNoperationtypeid()+" \n";				
			}			
			sql+=") union (";
			sql += " select sett.* from sett_transspecialoperation sett,(";
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from sett_transspecialoperation aa,loan_approvalrelation rr";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and (aa.MPAYAMOUNT>rr.moneysegment or aa.mreceiveamount >rr.moneysegment) and rr.currencyid = " +info.getNcurrencyid()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getNpayclientid()>0){
				sql+=" and aa.NPAYCLIENTID ="+info.getNpayclientid()+" \n";				
			}
			if(info.getNpayaccountid()>0){
				sql+=" and aa.NPAYACCOUNTID ="+info.getNpayaccountid()+" \n";						
			}
			if(info.getNpaybankid()>0){
				sql+=" and aa.NPAYBANKID ="+info.getNpaybankid()+" \n";					
			}
			if(info.getNreceiveclientid()>0){
				sql+=" and aa.NRECEIVECLIENTID ="+info.getNreceiveclientid()+" \n";				
			}
			if(info.getNreceiveaccountid()>0){
				sql+=" and aa.NRECEIVEACCOUNTID ="+info.getNreceiveaccountid()+" \n";						
			}
			if(info.getNreceivebankid()>0){
				sql+=" and aa.NRECEIVEBANKID ="+info.getNreceivebankid()+" \n";					
			}
			if(info.getMpayamount()>0){ 
				sql+=" and aa.MPAYAMOUNT="+info.getMpayamount()+" \n";				
			}
			if (info.getStransno() != null && info.getStransno().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getStransno()+"' \n";
			}
			if (info.getDtintereststart() != null){
				String time = (info.getDtintereststart()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}					
			if(info.getNoperationtypeid()>0){
				sql+=" AND aa.noperationtypeid = "+info.getNoperationtypeid()+" \n";				
			}
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from sett_transspecialoperation aa,loan_approvalrelation rr";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and (aa.MPAYAMOUNT>rr.moneysegment or aa.mreceiveamount >rr.moneysegment) and rr.currencyid = " +info.getNcurrencyid()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getNpayclientid()>0){
				sql+=" and aa.NPAYCLIENTID ="+info.getNpayclientid()+" \n";				
			}
			if(info.getNpayaccountid()>0){
				sql+=" and aa.NPAYACCOUNTID ="+info.getNpayaccountid()+" \n";						
			}
			if(info.getNpaybankid()>0){
				sql+=" and aa.NPAYBANKID ="+info.getNpaybankid()+" \n";					
			}
			if(info.getNreceiveclientid()>0){
				sql+=" and aa.NRECEIVECLIENTID ="+info.getNreceiveclientid()+" \n";				
			}
			if(info.getNreceiveaccountid()>0){
				sql+=" and aa.NRECEIVEACCOUNTID ="+info.getNreceiveaccountid()+" \n";						
			}
			if(info.getNreceivebankid()>0){
				sql+=" and aa.NRECEIVEBANKID ="+info.getNreceivebankid()+" \n";					
			}
			if(info.getMpayamount()>0){ 
				sql+=" and aa.MPAYAMOUNT="+info.getMpayamount()+" \n";				
			}
			if (info.getStransno() != null && info.getStransno().compareToIgnoreCase("") != 0){			
				sql+=" and aa.STRANSNO='"+info.getStransno()+"' \n";
			}
			if (info.getDtintereststart() != null){
				String time = (info.getDtintereststart()).toString();
				time = time.substring(0, 10);
				sql+=" AND aa.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
			}					
			if(info.getNoperationtypeid()>0){
				sql+=" AND aa.noperationtypeid = "+info.getNoperationtypeid()+" \n";				
			}
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
			sql += " ) se where se.id = sett.id ";
//			if(info.getNpayclientid()>0){
//				sql+=" and f.NPAYCLIENTID ="+info.getNpayclientid()+" \n";				
//			}
//			if(info.getNpayaccountid()>0){
//				sql+=" and f.NPAYACCOUNTID ="+info.getNpayaccountid()+" \n";						
//			}
//			if(info.getNpaybankid()>0){
//				sql+=" and f.NPAYBANKID ="+info.getNpaybankid()+" \n";					
//			}
//			if(info.getNreceiveclientid()>0){
//				sql+=" and f.NRECEIVECLIENTID ="+info.getNreceiveclientid()+" \n";				
//			}
//			if(info.getNreceiveaccountid()>0){
//				sql+=" and f.NRECEIVEACCOUNTID ="+info.getNreceiveaccountid()+" \n";						
//			}
//			if(info.getNreceivebankid()>0){
//				sql+=" and f.NRECEIVEBANKID ="+info.getNreceivebankid()+" \n";					
//			}
//			if(info.getMpayamount()>0){ 
//				sql+=" and f.MPAYAMOUNT="+info.getMpayamount()+" \n";				
//			}
//			if (info.getStransno() != null && info.getStransno().compareToIgnoreCase("") != 0){			
//				sql+=" and f.STRANSNO='"+info.getStransno()+"' \n";
//			}
//			if (info.getDtintereststart() != null){
//				String time = (info.getDtintereststart()).toString();
//				time = time.substring(0, 10);
//				sql+=" AND f.DTINTERESTSTART = to_date('" + time + "','yyyy-mm-dd') \n";
//			}					
//			if(info.getNoperationtypeid()>0){
//				sql+=" AND f.noperationtypeid = "+info.getNoperationtypeid()+" \n";				
//			}			
			sql+= ")";
			
			System.out.println("查询语句特殊交易SQL^^^^^^^^^^^"+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();		
			Sett_TransSpecialOperationDAO seDao=new Sett_TransSpecialOperationDAO();
			while(transRS.next()){
				TransSpecialOperationInfo datainfo=new TransSpecialOperationInfo();
				seDao.getTableInfo(transRS, datainfo);				
				v.add(datainfo);			
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}	
		return v;
	}
	/**
	 *  根据条件查询需要登录人审批的记录(自营贷款、委托贷款)--add by 刘川
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
	public Collection findByTrustConditions(TransGrantLoanInfo info,long userID) throws Exception
	{
	    ArrayList list = new ArrayList();
		String sql="";
		try{
			initDAO();
			sql="SELECT c.*,-1 moneysegment,-1 approvalid from SETT_TRANSGRANTLOAN c";
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+info.getTransactionTypeID()+"";
			sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID )) d";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+SETTConstant.TransactionStatus.APPROVALING+"";
			if(info.getLoanAccountID()>0){
				sql+=" and NLOANACCOUNTID ="+info.getLoanAccountID()+" \n";				
			}
			if(info.getLoanContractID()>0){
				sql+=" and NLOANCONTRACTID ="+info.getLoanContractID()+" \n";						
			}
			if(info.getLoanNoteID()>0){
				sql+=" and NLOANNOTEID ="+info.getLoanNoteID()+" \n";	
			}
			if(info.getDepositAccountID()>0){
				sql+=" and NDEPOSITACCOUNTID="+info.getDepositAccountID()+" \n";				
			}
			if (info.getBankID() > 0){			
				sql+=" and NBANKID='"+info.getBankID()+"' \n";
			}
		    if(info.getPayInterestAccountID()>0){
		    	sql+=" and NPAYINTERESTACCOUNTID="+info.getPayInterestAccountID()+" \n";
		    }	  	
		    if(info.getAmount()>0){
				sql+=" and MAMOUNT="+info.getAmount()+" \n";				
			}	       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
		    sql+=" union (" ;
		    sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from SETT_TRANSGRANTLOAN aa,loan_approvalrelation rr";
			//增加关于币种的判断
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.mamount>rr.moneysegment and rr.currencyid =" +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getLoanAccountID()>0){
				sql+=" and aa.NLOANACCOUNTID ="+info.getLoanAccountID()+" \n";				
			}
			if(info.getLoanContractID()>0){
				sql+=" and aa.NLOANCONTRACTID ="+info.getLoanContractID()+" \n";						
			}
			if(info.getLoanNoteID()>0){
				sql+=" and aa.NLOANNOTEID ="+info.getLoanNoteID()+" \n";	
			}
			if(info.getDepositAccountID()>0){
				sql+=" and aa.NDEPOSITACCOUNTID="+info.getDepositAccountID()+" \n";				
			}
			if (info.getBankID() > 0){			
				sql+=" and aa.NBANKID='"+info.getBankID()+"' \n";
			}
		    if(info.getPayInterestAccountID()>0){
		    	sql+=" and aa.NPAYINTERESTACCOUNTID="+info.getPayInterestAccountID()+" \n";
		    }	  	
		    if(info.getAmount()>0){
				sql+=" and aa.MAMOUNT="+info.getAmount()+" \n";				
			}	       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND aa.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from SETT_TRANSGRANTLOAN aa,loan_approvalrelation rr";
			//增加关于币种的判断
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.mamount>rr.moneysegment and rr.currencyid = " +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			if(info.getLoanAccountID()>0){
				sql+=" and aa.NLOANACCOUNTID ="+info.getLoanAccountID()+" \n";				
			}
			if(info.getLoanContractID()>0){
				sql+=" and aa.NLOANCONTRACTID ="+info.getLoanContractID()+" \n";						
			}
			if(info.getLoanNoteID()>0){
				sql+=" and aa.NLOANNOTEID ="+info.getLoanNoteID()+" \n";	
			}
			if(info.getDepositAccountID()>0){
				sql+=" and aa.NDEPOSITACCOUNTID="+info.getDepositAccountID()+" \n";				
			}
			if (info.getBankID() > 0){			
				sql+=" and aa.NBANKID='"+info.getBankID()+"' \n";
			}
		    if(info.getPayInterestAccountID()>0){
		    	sql+=" and aa.NPAYINTERESTACCOUNTID="+info.getPayInterestAccountID()+" \n";
		    }	  	
		    if(info.getAmount()>0){
				sql+=" and aa.MAMOUNT="+info.getAmount()+" \n";				
			}	       
		    if(info.getTransactionTypeID()>0){
		    	sql+=" AND aa.NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
		    }
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
//		        +"select f.* from SETT_TRANSGRANTLOAN f where f.NINPUTUSERID="+userID+" and f.nstatusid="+SETTConstant.TransactionStatus.APPROVAL+"";
//		    if(info.getLoanAccountID()>0){
//				sql+=" and NLOANACCOUNTID ="+info.getLoanAccountID()+" \n";				
//			}
//			if(info.getLoanContractID()>0){
//				sql+=" and NLOANCONTRACTID ="+info.getLoanContractID()+" \n";						
//			}
//			if(info.getLoanNoteID()>0){
//				sql+=" and NLOANNOTEID ="+info.getLoanNoteID()+" \n";	
//			}
//			if(info.getDepositAccountID()>0){
//				sql+=" and NDEPOSITACCOUNTID="+info.getDepositAccountID()+" \n";				
//			}
//			if (info.getBankID() > 0){			
//				sql+=" and NBANKID='"+info.getBankID()+"' \n";
//			}
//		    if(info.getPayInterestAccountID()>0){
//		    	sql+=" and NPAYINTERESTACCOUNTID="+info.getPayInterestAccountID()+" \n";
//		    }	  	
//		    if(info.getAmount()>0){
//				sql+=" and MAMOUNT="+info.getAmount()+" \n";				
//			}	       
//		    if(info.getTransactionTypeID()>0){
//		    	sql+=" AND NTransactionTypeID = "+info.getTransactionTypeID()+" \n";
//		    }
		    sql+=")";
			System.out.println("查询语句SQL^^^^^^^^^^^"+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();		
			while(transRS.next()){
			    TransGrantLoanInfo tInfo = new TransGrantLoanInfo();
			    tInfo.setID(transRS.getLong(1));
			    tInfo.setOfficeID(transRS.getLong(2));
			    tInfo.setCurrencyID(transRS.getLong(3));
			    tInfo.setTransNo(transRS.getString(4) == null ? "" : transRS.getString(4));
				tInfo.setTransactionTypeID(transRS.getLong(5));
				tInfo.setLoanAccountID(transRS.getLong(6));
				tInfo.setLoanContractID(transRS.getLong(7));
				tInfo.setLoanNoteID(transRS.getLong(8));
				tInfo.setExtendFormID(transRS.getLong(9));
				tInfo.setConsignDepositAccountID(transRS.getLong(10));
				tInfo.setKeepAccount(transRS.getLong(11) == 1 ? true : false);
				tInfo.setPayInterestAccountID(transRS.getLong(12));
				tInfo.setReceiveInterestAccountID(transRS.getLong(13));
				tInfo.setPaySuretyFeeAccountID(transRS.getLong(14));
				tInfo.setReceiveSuretyFeeAccountID(transRS.getLong(15));
				tInfo.setPayCommisionAccountID(transRS.getLong(16));
				tInfo.setInterestTaxRate(transRS.getDouble(17));
				tInfo.setInterestTaxRateVauleDate(transRS.getTimestamp(18));
				tInfo.setDepositAccountID(transRS.getLong(19));
				tInfo.setPayTypeID(transRS.getLong(20));
				tInfo.setBankID(transRS.getLong(21));
				tInfo.setExtAcctNo(transRS.getString(22));
				tInfo.setExtAcctName(transRS.getString(23));
				tInfo.setBankName(transRS.getString(24) == null ? "" : transRS.getString(24));
				tInfo.setProvince(transRS.getString(25));
				tInfo.setCity(transRS.getString(26) == null ? "" : transRS.getString(26));
				tInfo.setAmount(transRS.getDouble(27));
				tInfo.setCashFlowID(transRS.getLong(28));
				tInfo.setInterestStart(transRS.getTimestamp(29));
				tInfo.setExecute(transRS.getTimestamp(30));
				tInfo.setModify(transRS.getTimestamp(31));
				tInfo.setInputDate(transRS.getTimestamp(32));
				tInfo.setInputUserID(transRS.getLong(33));
				tInfo.setCheckUserID(transRS.getLong(34));
				tInfo.setAbstractID(transRS.getLong(35));
				tInfo.setAbstract(transRS.getString(36));
				tInfo.setCheckAbstract(transRS.getString(37));
				tInfo.setStatusID(transRS.getLong(38));
				tInfo.setExtBankNo(transRS.getString(39));
				tInfo.setInterestTaxPlanId(transRS.getLong("nInterestTaxPlanId"));
				
				list.add(tInfo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}	
		return list;
	}

	/**
	 * 根据模块ID，业务类型ID，金额查询所属的审批流ID
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getApprovalId(long moduleid,long typeId,long currencyID,long officeID,double amount){
		long lReturn=-1;
		try{
			initDAO();
			String sql="select approvalid from loan_approvalrelation ";
			sql+=" where moneysegment = (select max(Moneysegment) from loan_approvalrelation";
			sql+=" where moduleid="+moduleid+" and loantypeid="+typeId+" and moneysegment<="+amount+"" ;
			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +")";
			sql+=" and officeID="+officeID+" and currencyid="+ currencyID +" and loantypeid="+typeId+"";
			sql+=" and approvalid in (select id from loan_approvalsetting where nstatusid=2 and nofficeid="+officeID+" and ncurrencyid="+ currencyID +" )";
			log.print("查询审批流IDSQL====="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				lReturn=transRS.getLong("approvalid");	
			}
				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
	/**
	 * 根据审批流ID，用户ID查出此用户的审批级别
	 * @param moduleid
	 * @param typeId
	 * @param amount
	 * @return
	 */
	public long getLevelId(long approvalID,long userID){
		long lReturn=-1;
		try{
			initDAO();
			String sql="select nlevel from loan_approvalitem";
			sql+="  where napprovalid="+approvalID+" and nuserid="+userID+"";			
			log.print("查询审批级别IDSQL====="+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
				lReturn=transRS.getLong("nlevel");	
			}				
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}		
		return lReturn;				
	}
	/**
	 * 根据审批结果来更新结算业务记录的状态,同时处理审批记录表记录
	 * @param lResultID 审批结果ID
	 * @param tranTypeId 业务类型ID
	 * @param settID 业务记录ID
	 * @return
	 */
	public long updateDataStatusID(ApprovalTracingInfo info,long lResultID,long tranTypeId,long settID){
		long lReturn=-1;
		try{
			long settStatusID=-1;//结算表记录状态
            Timestamp dtexcute = Env.getSystemDate(info.getOfficeID(),info.getCurrencyID());
			//先处理审批记录表内容
			ApprovalDelegation appbiz=new ApprovalDelegation();					
			//无论是拒绝\返回修改\审批通过\审核完成都新增一条记录
			lReturn = appbiz.saveApprovalTracing(info); //保存审批信息
			//审核拒绝,逻辑删除本条记录的所有的审核记录			
			if(lResultID==Constant.ApprovalDecision.REFUSE||lResultID==Constant.ApprovalDecision.RETURN){				
				lReturn=appbiz.deleteApprovalTracing(info.getModuleID(),tranTypeId,info.getActionID(),info.getOfficeID(),info.getCurrencyID(),settID,2);
			}
			//根据审批结果来判断结算表记录状态
			
			if(lResultID==Constant.ApprovalDecision.PASS){//审核通过,状态为审批中
				settStatusID=SETTConstant.TransactionStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.REFUSE){//审批拒绝，状态为删除
				settStatusID=SETTConstant.TransactionStatus.REFUSE;				
			}else if(lResultID==Constant.ApprovalDecision.RETURN){//审批返回，状态为未审批
				settStatusID=SETTConstant.TransactionStatus.APPROVALING;				
			}else if(lResultID==Constant.ApprovalDecision.FINISH){//审批完成，状态为保存
				settStatusID=SETTConstant.TransactionStatus.SAVE;				
			}
			System.out.println(lResultID+"&&&&&&&&&&&&&"+settStatusID);
			//根据业务类型和审批结果来更新结算表记录状态信息
			//业务类型为银行付款、内部转帐、委托存款
			System.out.println("^^^^^^^^^^^^^^^^"+tranTypeId);
			if(tranTypeId==SETTConstant.TransactionType.BANKPAY||tranTypeId==SETTConstant.TransactionType.INTERNALVIREMENT||tranTypeId==SETTConstant.TransactionType.CONSIGNSAVE){				
				Sett_TransCurrentDepositDAO bankdao=new Sett_TransCurrentDepositDAO();				
				if(lResultID==Constant.ApprovalDecision.REFUSE){
					TransCurrentDepositDelegation bankbiz = new TransCurrentDepositDelegation();
					//如果审核结果为拒绝，逻辑删除原来的记录（为了把生成的那条子帐户记录也逻辑删除）
					TransCurrentDepositInfo bankinfo=new TransCurrentDepositInfo();
					//根据ID先查询此记录的详细信息
					bankinfo=bankdao.findByID(settID);
					TransCurrentDepositAssembler ass=new TransCurrentDepositAssembler(bankinfo);
					//调用删除方法
					lReturn=bankbiz.delete(ass);
                    //lReturn=bankdao.updateApp(settID,settStatusID,dtexcute);
				}else{
					//修改原记录的状态
					
					//lReturn=bankdao.updateApp(settID,settStatusID,dtexcute);
					System.out.println(settID+"^^^^^^^^^^^^^^^^"+settStatusID);
				}
			}else if(tranTypeId==SETTConstant.TransactionType.GENERALLEDGER){//总帐类
				Sett_TransGeneralLedgerDAO geDao=new Sett_TransGeneralLedgerDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE){
					TransGeneralLedgerDelegation gebiz = new TransGeneralLedgerDelegation();
					//如果审核结果为拒绝，逻辑删除原来的记录（为了把生成的那条子帐户记录也逻辑删除）
					TransGeneralLedgerInfo geinfo=new TransGeneralLedgerInfo();
					//根据ID先查询此记录的详细信息
					geinfo=geDao.findByID(settID);
					//调用删除方法
					lReturn=gebiz.delete(geinfo);
                    //lReturn=geDao.updateApp(settID,settStatusID,dtexcute);
				}else{
					//改变原记录的状态
                    //lReturn=geDao.updateApp(settID,settStatusID,dtexcute);
				}
				//自营贷款发放、委托贷款发放
			}else if(tranTypeId==SETTConstant.TransactionType.TRUSTLOANGRANT || tranTypeId==SETTConstant.TransactionType.CONSIGNLOANGRANT){
				Sett_TransGrantLoanDAO loanDao=new Sett_TransGrantLoanDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE){
					TransLoanDelegation loanbiz = new TransLoanDelegation();		
					//如果审核结果为拒绝，逻辑删除原来的记录（为了把生成的那条子帐户记录也逻辑删除）
					TransGrantLoanInfo loaninfo=new TransGrantLoanInfo();
					//根据ID先查询此记录的详细信息
					loaninfo=loanDao.findByID(settID);
					//调用删除方法
					lReturn=loanbiz.grantDelete(loaninfo);
                    //lReturn=loanDao.updateApp(settID,settStatusID,dtexcute);
				}else{
					//改变原记录的状态
                    //lReturn=loanDao.updateApp(settID,settStatusID,dtexcute);
				}
				//贴现发放
			}else if(tranTypeId==SETTConstant.TransactionType.DISCOUNTGRANT){
				Sett_TransGrantDiscountDAO disDao=new Sett_TransGrantDiscountDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE){
					TransDiscountDelegation disbiz = new TransDiscountDelegation();		
					//如果审核结果为拒绝，逻辑删除原来的记录（为了把生成的那条子帐户记录也逻辑删除）
					TransGrantDiscountInfo disinfo=new TransGrantDiscountInfo();
					//根据ID先查询此记录的详细信息
					disinfo=disDao.findByID(settID);
					//调用删除方法
					lReturn=disbiz.grantDelete(disinfo);
                    //lReturn=disDao.updateApp(settID,settStatusID,-1,disinfo.getAbstract(),disinfo.getCheckAbstract(),dtexcute);
				}else{
					//改变原记录的状态
				    TransGrantDiscountInfo disinfo=new TransGrantDiscountInfo();
					//根据ID先查询此记录的详细信息
					disinfo=disDao.findByID(settID);
					//lReturn=disDao.updateStatus(settID,settStatusID,-1,disinfo.getAbstract(),disinfo.getCheckAbstract());
                    //lReturn=disDao.updateApp(settID,settStatusID,-1,disinfo.getAbstract(),disinfo.getCheckAbstract(),dtexcute);
				}
				//特殊交易
			}else if(tranTypeId==SETTConstant.TransactionType.SPECIALOPERATION){
				Sett_TransSpecialOperationDAO operDao=new Sett_TransSpecialOperationDAO();
				if(lResultID==Constant.ApprovalDecision.REFUSE){
					TransSpecialOperationDelegation operbiz = new TransSpecialOperationDelegation();		
					//如果审核结果为拒绝，逻辑删除原来的记录（为了把生成的那条子帐户记录也逻辑删除）
					TransSpecialOperationInfo operinfo=new TransSpecialOperationInfo();
					//根据ID先查询此记录的详细信息
					operinfo=operDao.findByID(settID);
					//调用删除方法
					if (operbiz.delete(operinfo) == true){
						lReturn=1;
					}
                    java.sql.Timestamp tsDtmodify = null;
                    
                    tsDtmodify = Env.getSystemDateTime();
                    //改变原记录的状态
                    //lReturn=operDao.updateApp(settID,dtexcute,settStatusID);
				}else{
					java.sql.Timestamp tsDtmodify = null;
					
					tsDtmodify = Env.getSystemDateTime();
					//改变原记录的状态
                    //lReturn=operDao.updateApp(settID,dtexcute,settStatusID);
				}
				
			}else if(tranTypeId == SETTConstant.TransactionType.CAPITALTRANSFER)
			{
				System.out.println("资金调拨审批==========");
				CapitalTransferApplyDao dao = new CapitalTransferApplyDao();
				
				lReturn = dao.updateStatusID(settID,settStatusID);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lReturn;				
	}
	/**
	 *  根据条件查询需要登录人审批的记录(贴现)--add by 刘川
	 * @param sett_TransCurrentDepositInfo
	 * @param orderByType
	 * @param isDesc
	 * @return
	 * @throws Exception
	 */
	public Collection findByDISCOUNTGRANTConditions(TransGrantDiscountInfo info,long userID) throws Exception
	{
	    ArrayList list = new ArrayList();
		String sql="";
		try{
			
			initDAO();
			sql="(SELECT c.*,-1 moneysegment,-1 approvalid from Sett_TransGrantDiscount c ";														
			sql+=" ,(select a.NAPPROVALCONTENTID from loan_approvaltracing a,(select NAPPROVALCONTENTID,max(ID) as ID from loan_approvaltracing group by NAPPROVALCONTENTID) b";
			sql+=" where (a.NNEXTUSERID="+userID+" and a.NLOANTYPEID="+info.getTransactionTypeID()+"";
			sql+="  and a.NMODULEID="+Constant.ModuleType.SETTLEMENT+" and nstatusid="+Constant.RecordStatus.VALID+""; 
			sql+=" and a.ID(+)= b.ID and a.NAPPROVALCONTENTID(+) = b.NAPPROVALCONTENTID ) ) d,sett_account g";
			sql+=" where c.id =d.NAPPROVALCONTENTID and c.nstatusid="+SETTConstant.TransactionStatus.APPROVALING+"";
			sql+=" and c.nDiscountAccountID =g.id";
//			if(info.getClientID()>0){
//				sql+=" and g.nClientID ="+info.getClientID()+" \n";				
//			}
			if(info.getDiscountAccountID()>0){
				sql+=" and c.nDiscountAccountID ="+info.getDiscountAccountID()+" \n";						
			}
			if (info.getDiscountContractID() >0){			
				sql+=" and c.nDiscountContractID="+info.getDiscountContractID()+" \n";
			}
			if (info.getDiscountNoteID() > 0){
			    sql+=" and c.nDiscountNoteID="+info.getDiscountNoteID()+" \n";
			}					
			sql+=" and c.ntransactiontypeid="+SETTConstant.TransactionType.DISCOUNTGRANT+"";
			sql+=") union (" ;
			sql += " select d.* from (";
			sql += " select aaa.* from (";
			sql += " select aa.*,rr.moneysegment,rr.approvalid from Sett_TransGrantDiscount aa,loan_approvalrelation rr,sett_account g";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.MDISCOUNTAMOUNT>rr.moneysegment and rr.currencyid = " +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			sql += " and aa.nDiscountAccountID =g.id";
//			if(info.getClientID()>0){
//				sql+=" and g.nClientID ="+info.getClientID()+" \n";				
//			}
			if(info.getDiscountAccountID()>0){
				sql+=" and aa.nDiscountAccountID ="+info.getDiscountAccountID()+" \n";						
			}
			if (info.getDiscountContractID() >0){			
				sql+=" and aa.nDiscountContractID="+info.getDiscountContractID()+" \n";
			}
			if (info.getDiscountNoteID() > 0){
			    sql+=" and aa.nDiscountNoteID="+info.getDiscountNoteID()+" \n";
			}					
			sql+=" and aa.ntransactiontypeid="+SETTConstant.TransactionType.DISCOUNTGRANT+"";
			sql += " ) aaa,(";
			sql += " select aa.id,max(rr.moneysegment) maxamount from Sett_TransGrantDiscount aa,loan_approvalrelation rr,sett_account g";
			sql += " where aa.ntransactiontypeid=rr.loantypeid and rr.moduleid="+Constant.ModuleType.SETTLEMENT+" and aa.MDISCOUNTAMOUNT>rr.moneysegment and rr.currencyid = " +info.getCurrencyID()+ " and aa.nstatusid="+SETTConstant.TransactionStatus.APPROVALING;
			sql += " and aa.nDiscountAccountID =g.id";
//			if(info.getClientID()>0){
//				sql+=" and g.nClientID ="+info.getClientID()+" \n";				
//			}
			if(info.getDiscountAccountID()>0){
				sql+=" and aa.nDiscountAccountID ="+info.getDiscountAccountID()+" \n";						
			}
			if (info.getDiscountContractID() >0){			
				sql+=" and aa.nDiscountContractID="+info.getDiscountContractID()+" \n";
			}
			if (info.getDiscountNoteID() > 0){
			    sql+=" and aa.nDiscountNoteID="+info.getDiscountNoteID()+" \n";
			}					
			sql+=" and aa.ntransactiontypeid="+SETTConstant.TransactionType.DISCOUNTGRANT+"";
			sql += " group by aa.id ) bbb";
			sql += " where aaa.id = bbb.id and aaa.moneysegment = bbb.maxamount) d,";	
			sql += " loan_approvalsetting e,loan_approvalitem f";
			sql += " where d.approvalid = e.id and f.napprovalid=e.id and f.nlevel=1 and f.nuserid="+userID;
//			    +"select f.* from Sett_TransGrantDiscount f,sett_account g where g.id = f.nDiscountAccountID and f.NINPUTUSERID="+userID+" and f.nstatusid="+SETTConstant.TransactionStatus.APPROVAL+"";
//			if(info.getClientID()>0){
//				sql+=" and g.nClientID ="+info.getClientID()+" \n";				
//			}
//			if(info.getDiscountAccountID()>0){
//				sql+=" and c.nDiscountAccountID ="+info.getDiscountAccountID()+" \n";						
//			}
//			if (info.getDiscountContractID() >0){			
//				sql+=" and c.nDiscountContractID="+info.getDiscountContractID()+" \n";
//			}
//			if (info.getDiscountNoteID() > 0){
//			    sql+=" and c.nDiscountNoteID="+info.getDiscountNoteID()+" \n";
//			}
//			sql+=" and f.ntransactiontypeid="+SETTConstant.TransactionType.DISCOUNTGRANT+"";
			sql+= ")";
			
			System.out.println("查询语句SQL^^^^^^^^^^^"+sql);
			transPS=transConn.prepareStatement(sql);
			transRS = transPS.executeQuery();
			while(transRS.next()){
			    
			    TransGrantDiscountInfo tInfo = new TransGrantDiscountInfo();
			    tInfo.setID(transRS.getLong("ID"));
			    tInfo.setOfficeID(transRS.getLong("nOfficeID"));
			    tInfo.setCurrencyID(transRS.getLong("nCurrencyID"));
			    tInfo.setTransNo(transRS.getString("sTransNo"));
			    tInfo.setTransactionTypeID(transRS.getLong("nTransactionTypeID"));
			    tInfo.setDiscountAccountID(transRS.getLong("nDiscountAccountID"));
			    tInfo.setDiscountContractID(transRS.getLong("nDiscountContractID"));
			    tInfo.setDiscountNoteID(transRS.getLong("nDiscountNoteID"));
			    tInfo.setDiscountBillAmount(transRS.getDouble("mDiscountBillAmount"));
			    tInfo.setDiscountAmount(transRS.getDouble("mDiscountAmount"));
			    tInfo.setDepositAccountID(transRS.getLong("nDepositAccountID"));
			    tInfo.setPayTypeID(transRS.getLong("nPayTypeID"));
			    tInfo.setBankID(transRS.getLong("nBankID"));
			    tInfo.setExtAcctNo(transRS.getString("sExtAcctNo"));
			    tInfo.setExtAcctName(transRS.getString("sExtAcctName"));
			    tInfo.setBankName(transRS.getString("sBankName"));
			    tInfo.setProvince(transRS.getString("sProvince"));
			    tInfo.setCity(transRS.getString("sCity"));
			    tInfo.setCashFlowID(transRS.getLong("nCashFlowID"));
			    tInfo.setInterest(transRS.getDouble("mInterest"));
			    tInfo.setInterestStartDate(transRS.getTimestamp("dtInterestStart"));
			    tInfo.setExecuteDate(transRS.getTimestamp("dtExecute"));
			    tInfo.setModifyDate(transRS.getTimestamp("dtModify"));
			    tInfo.setInputDate(transRS.getTimestamp("dtInput"));
			    tInfo.setInputUserID(transRS.getLong("nInputUserID"));
			    tInfo.setCheckUserID(transRS.getLong("nCheckUserID"));
			    tInfo.setAbstract(transRS.getString("sAbstract"));
			    tInfo.setCheckAbstract(transRS.getString("sCheckAbstract"));
			    tInfo.setStatusID(transRS.getLong("nStatusID"));
			    tInfo.setMBankAcceptanceAmount(transRS.getDouble("mBankAcceptanceAmount"));
			    tInfo.setMTradeAcceptanceAmount(transRS.getDouble("mTradeAcceptanceAmount"));
			    tInfo.setNAbstract(transRS.getLong("nAbstractID"));
			    tInfo.setSignBillAccountID(transRS.getLong("nSignBillAccountID"));
			    tInfo.setInterestOfSign(transRS.getDouble("mInterestOfSign"));
			    tInfo.setInterestOfDiscount(transRS.getDouble("mInterestOfDiscount"));
			    
			    list.add(tInfo);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			try{
				finalizeDAO();
			}catch(Exception es){es.printStackTrace();}	
		}	
		return list;
	}
	public static  void main(String args[]){
		try{
			Sett_ApprovalTranDao dao=new Sett_ApprovalTranDao();
			/*long ss=-1;
			ApprovalTracingInfo info=new ApprovalTracingInfo();
			info.setApprovalContentID(282);
			info.setLoanTypeID(2);
			info.setModuleID(1);
			info.setUserID(2);
			info.setNextUserID(3);
			info.setNextLevel(2);			
			info.setCurrencyID(1);
			info.setOfficeID(1);
			info.setLevel(1);
			info.setResultID(3);
			info.setStatusID(1);
			info.setOpinion("同意么");
			ss=dao.updateDataStatusID(info,2,2,282);*/

			//TransCurrentDepositInfo infos=new TransCurrentDepositInfo();
			//infos.setTransactionTypeID(2);
			TransGeneralLedgerInfo infos=new TransGeneralLedgerInfo();
//			infos.setTransactionTypeID(2);
			//infos.setClientID(1);
			//infos.setAccountID(2);
			Collection col=null;
			col= dao.findByAppGenerallConditions(infos,1);
			//col= dao.findByAppBankConditions(infos,40);
//			if(!col.isEmpty()){
//				for(Iterator iter=col.iterator();iter.hasNext();){
//					TransCurrentDepositInfo info=(TransCurrentDepositInfo)iter.next();
//					System.out.println(info.getId());
//					System.out.println(info.getAmount()+"^^^^^^^^");
//					System.out.println(info.getTransNo());
//				}
//			}
			
		}catch(Exception ex){ex.printStackTrace();}
		
	}
	
}

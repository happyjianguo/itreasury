package com.iss.itreasury.settlement.accountsystem.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingInfo;
import com.iss.itreasury.settlement.accountsystem.dataentity.AccountRelationSettingViewInfo;
import com.iss.itreasury.settlement.accountsystem.util.AccountSystemConstant;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;

public class AccountRelationDAO extends ITreasuryDAO {
	
	public final static String TABLENAME_SET = "sett_accountsystemsetting";

	public final static String TABLENAME_RELATION = "sett_accountrelationsetting";
	
	public AccountRelationDAO() {
		super(AccountRelationDAO.TABLENAME_RELATION);
	}
	
	/**
	 * 根据父帐户ID查找relation表中的子休息，并且为不删除状态
	 * 录入 或 审核 时使用此方法
	 * nAccountId 是一个可选参数
	 * 
	 * @param nAccountSystemId
	 * @param nAccountId
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException 
	 */	
	public Collection findCollByAccountSystemId(long nAccountSystemId, long nAccountId) throws ITreasuryDAOException{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select a.*,b.saccountno accountcode, b.sname accountname, c.sCode clientcode, c.sName clientname \n");
			buffer.append("from " + strTableName + " a, sett_account b, client c \n");
			buffer.append("WHERE a.nstatusid != " + AccountSystemConstant.RelationStatus.DELETED + " \n");
			if(nAccountSystemId > 0){
				buffer.append("and a.nAccountSystemId = " + nAccountSystemId + " \n");
			}
			if(nAccountId > 0){
				buffer.append("and a.nAccountId = " + nAccountId + " \n");
			}
			buffer.append("and a.naccountid = b.id \n");
			buffer.append("and b.nclientid = c.id");
	
			prepareStatement( buffer.toString());		
			executeQuery();
	
			Collection coll = new ArrayList();
			
			while(transRS.next()){
				AccountRelationSettingViewInfo arsViewInfo = new AccountRelationSettingViewInfo();
				arsViewInfo.setId(transRS.getLong("id"));
				arsViewInfo.setNAccountSystemId(transRS.getLong("nAccountSystemId"));
				arsViewInfo.setNAccountId(transRS.getLong("nAccountId"));
				arsViewInfo.setNStatusId(transRS.getLong("nStatusId"));
				arsViewInfo.setNOfficeId(transRS.getLong("nOfficeId"));
				arsViewInfo.setNCurrencyId(transRS.getLong("nCurrencyId"));
				arsViewInfo.setAccountCode(transRS.getString("accountcode"));
				arsViewInfo.setAccountName(transRS.getString("accountname"));
				arsViewInfo.setClientCode(transRS.getString("clientcode"));
				arsViewInfo.setClientName(transRS.getString("clientname"));
				coll.add(arsViewInfo);
			}
			
			if(coll.size() == 0){
				return null;
			}
			else {
				return coll;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("根据父帐户ID查找relation表中的子休息，并且为不删除状态失败",sqle);
		}finally{
			finalizeDAO();
		}
		
	}
	/**
	 * @param lAccountID
	 * @param lTransTypeID
	 * @return
	 * @throws SQLException
	 */
	public Collection queryAccountCapitalTransferStat(long lAccountSystemID,long lAccountID,Timestamp beginDate,Timestamp endDate,long lTransTypeID,long lOfficeID,long lCurrencyID) throws ITreasuryDAOException{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("SELECT deposit.id, deposit.nofficeid, deposit.ncurrencyid, deposit.stransno, deposit.ntransactiontypeid, deposit.nreceiveclientid,\n");
			buffer.append("deposit.nreceiveaccountid, deposit.npayclientid, deposit.npayaccountid, deposit.nbankid, \n");
			buffer.append("deposit.mamount, deposit.dtintereststart, deposit.dtexecute, deposit.dtmodify, deposit.dtinput, deposit.ncashflowid, deposit.ninputuserid, \n");
			buffer.append("deposit.ncheckuserid, deposit.nsignuserid, deposit.nconfirmuserid, deposit.nconfirmofficeid, deposit.sabstract, \n");
			buffer.append("deposit.scheckabstract, deposit.sconfirmabstract, deposit.nstatusid, deposit.nsinglebankaccounttypeid,\n");
			buffer.append("deposit.nconsignreceivetypeid, deposit.sinstructionno, deposit.sconsignvoucherno, deposit.sconsignpassword,\n");
			buffer.append("deposit.sbankchequeno, deposit.sdeclarationno, deposit.sbillno, deposit.nbilltypeid, deposit.nbillbankid, deposit.sextbankno,\n");
			buffer.append("deposit.sExtAccountNo, deposit.sExtClientName, deposit.sRemitInProvince, deposit.sRemitInCity, deposit.sRemitInBank,\n");
			buffer.append("deposit.nSubClientID, deposit.nabstractid, deposit.nReckoningTypeID, deposit.sReckoningBillTypeDesc, deposit.BudgetItemID, deposit.mCommissionAmount, deposit.nCommissionType, deposit.sCommissionTransNo  FROM \n");
			
			//buffer.append("select deposit.* FROM" + " \n");
			buffer.append("  Sett_Transcurrentdeposit deposit " + " \n");
			buffer.append(" where deposit.nStatusid = "+ SETTConstant.TransactionStatus.CHECK + " \n");
			buffer.append(" 	and deposit.nofficeid = " + lOfficeID + " \n");
			buffer.append(" 	and deposit.ncurrencyid = " + lCurrencyID + " \n");
			buffer.append(" 	and deposit.nTransActionTypeID = " + lTransTypeID + " \n");
			if(lTransTypeID == SETTConstant.TransactionType.CAPITALDOWN){
				buffer.append(" and deposit.nreceiveaccountid = " + lAccountID + " \n");
				
				buffer.append(" and deposit.Npayaccountid = ( " + " \n ");
				buffer.append(" 	select s.nupaccountid from Sett_Accountsystemsetting s where id =" + lAccountSystemID +" \n ");
				buffer.append(" )" + " \n ");
			}else if(lTransTypeID == SETTConstant.TransactionType.CAPITALUP){
				buffer.append(" and deposit.Npayaccountid = " + lAccountID + " \n");
				
				buffer.append(" and deposit.nreceiveaccountid = ( " + " \n ");
				buffer.append(" 	select s.nupaccountid from Sett_Accountsystemsetting s where id =" + lAccountSystemID +" \n ");
				buffer.append(" )" + " \n ");
			} 
			if(beginDate != null){
				buffer.append(" and deposit.dtInput >= to_date('"+DataFormat.getDateString(beginDate)+"','yyyy-mm-dd')	"  + " \n");
			}
			if(endDate != null){
				buffer.append(" and deposit.dtInput <= to_date('"+DataFormat.getDateString(endDate)+"','yyyy-mm-dd')	" + " \n");
			}
			prepareStatement( buffer.toString());		
			executeQuery();

			Collection coll = getInfoFromResultSet(transRS);

			
			if(coll.size() == 0){
				return null;
			}
			else {
				return coll;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("根据父帐户ID查找relation表中的子休息，并且为不删除状态失败",sqle);
		}catch(Exception e){
			e.printStackTrace();
			throw new ITreasuryDAOException("根据父帐户ID查找relation表中的子休息，并且为不删除状态失败",e);
		}finally{
			finalizeDAO();
		}
	}

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Collection getInfoFromResultSet(ResultSet rs) throws Exception
	{
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			TransCurrentDepositInfo info = new TransCurrentDepositInfo();
			info.setId(rs.getLong("id"));
			info.setOfficeID(rs.getLong("nofficeid"));
			info.setCurrencyID(rs.getLong("ncurrencyid"));
			info.setTransNo(rs.getString("stransno"));
			info.setTransactionTypeID(rs.getLong("ntransactiontypeid"));
			info.setReceiveClientID(rs.getLong("nreceiveclientid"));
			info.setReceiveAccountID(rs.getLong("nreceiveaccountid"));
			info.setPayClientID(rs.getLong("npayclientid"));
			info.setPayAccountID(rs.getLong("npayaccountid"));
			//info.setExternalAccountID(rs.getLong("nexternalaccountid"));
			info.setBankID(rs.getLong("nbankid"));
			info.setAmount(rs.getDouble("mamount"));
			info.setInterestStartDate(rs.getTimestamp("dtintereststart"));
			info.setExecuteDate(rs.getTimestamp("dtexecute"));
			info.setModifyTime(rs.getTimestamp("dtmodify"));
			info.setInputDate(rs.getTimestamp("dtinput"));
			info.setCashFlowID(rs.getLong("ncashflowid"));
			info.setInputUserID(rs.getLong("ninputuserid"));
			info.setCheckUserID(rs.getLong("ncheckuserid"));
			info.setCheckUserID(rs.getLong("nsignuserid"));
			info.setSignUserID(rs.getLong("nconfirmuserid"));
			info.setConfirmOfficeID(rs.getLong("nconfirmofficeid"));
			info.setAbstractStr(rs.getString("sabstract"));
			info.setCheckAbstractStr(rs.getString("scheckabstract"));
			info.setConfirmAbstractStr(rs.getString("sconfirmabstract"));
			info.setStatusID(rs.getLong("nstatusid"));
			info.setSingleBankAccountTypeID(rs.getLong("nsinglebankaccounttypeid"));
			info.setConsignReceiveTypeID(rs.getLong("nconsignreceivetypeid"));
			info.setInstructionNo(rs.getString("sinstructionno"));
			info.setConsignVoucherNo(rs.getString("sconsignvoucherno"));
			info.setConsignPassword(rs.getString("sconsignpassword"));
			info.setBankChequeNo(rs.getString("sbankchequeno"));
			info.setDeclarationNo(rs.getString("sdeclarationno"));
			info.setBillNo(rs.getString("sbillno"));
			info.setBillTypeID(rs.getLong("nbilltypeid"));
			info.setBillBankID(rs.getLong("nbillbankid"));
			info.setExtBankNo(rs.getString("sextbankno"));
			info.setAbstractID(rs.getLong("nabstractid"));
			//外部客户新增字段
			info.setExtAccountNo(rs.getString("sExtAccountNo"));
			info.setExtClientName(rs.getString("sExtClientName"));
			info.setRemitInProvince(rs.getString("sRemitInProvince"));
			info.setRemitInCity(rs.getString("sRemitInCity"));
			info.setRemitInBank(rs.getString("sRemitInBank"));
			//下属单位客户ID
			info.setSubClientID(rs.getLong("nSubClientID"));
			
			info.setReckoningTypeID(rs.getLong("nReckoningTypeID"));//清算类型
			info.setReckoningBillTypeDesc(rs.getString("sReckoningBillTypeDesc"));//清算表单的凭证类型
			
			//预算新增
			info.setBudgetItemID(rs.getLong("BudgetItemID"));//预算项目ID
            
            //手续费
            info.setCommissionAmount(rs.getDouble("mCommissionAmount"));
            info.setCommissionType(rs.getLong("nCommissionType"));
            info.setCommissionTransNo(rs.getString("sCommissionTransNo"));
			                                 
			list.add(info);
		}
		return list;
	}
	
	/**
	 * 根据体系ID 查找有效的交易记录，（企业资金上拨、下划）
	 * added by leiyang 2008/03/31
	 */
	public Collection findTranscurrentDepositColl(long nAccountSystemId) throws ITreasuryDAOException{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select e.systemcode systemcode, e.systemname systemname, e.accountcode upAccountCode, e.accountname upAccountName, a.*,b.saccountno accountcode, b.sname accountname, c.sCode clientcode, c.sName clientname,d.id ntransid, d.stransno transno, d.ntransactiontypeid ntransactiontypeid, d.mamount amount \n");
			buffer.append("from " + strTableName + " a, sett_account b, client c, sett_transcurrentdeposit d, \n");
			buffer.append("(select t1.id id, t1.systemcode systemcode, t1.systemname systemname, t2.saccountno accountcode, t2.sname accountname \n");
			buffer.append("from sett_accountsystemsetting t1, sett_account t2 \n");
			buffer.append("where t1.nupaccountid = t2.id and t1.nstatusid in ("+ AccountSystemConstant.SettingStatus.CHECKED +","+ AccountSystemConstant.SettingStatus.DELETING +","+ AccountSystemConstant.SettingStatus.MODIFING +")");
			buffer.append("and t1.id = "+ nAccountSystemId +") e \n");
			buffer.append("where a.nstatusid in ("+ AccountSystemConstant.RelationStatus.CHECKED +", "+ AccountSystemConstant.RelationStatus.DELETING +") \n");
			buffer.append("and a.naccountsystemid = e.id \n");
			buffer.append("and a.naccountid = b.id \n");
			buffer.append("and b.nclientid = c.id \n");
			buffer.append("and a.naccountid = decode(d.ntransactiontypeid,"+ SETTConstant.TransactionType.CAPITALUP +",d.npayaccountid,"+ SETTConstant.TransactionType.CAPITALDOWN +",d.nreceiveaccountid) \n");
			buffer.append("and d.ntransactiontypeid in ("+ SETTConstant.TransactionType.CAPITALUP +","+ SETTConstant.TransactionType.CAPITALDOWN +") \n");
			buffer.append("and d.nstatusid = "+ SETTConstant.TransactionStatus.CHECK +" \n");

			prepareStatement( buffer.toString());		
			executeQuery();

			Collection coll = new ArrayList();
			
			while(transRS.next()){
				AccountRelationSettingViewInfo arsViewInfo = new AccountRelationSettingViewInfo();
				arsViewInfo.setId(transRS.getLong("id"));
				arsViewInfo.setNAccountSystemId(transRS.getLong("nAccountSystemId"));
				arsViewInfo.setNAccountId(transRS.getLong("nAccountId"));
				arsViewInfo.setNStatusId(transRS.getLong("nStatusId"));
				arsViewInfo.setNOfficeId(transRS.getLong("nOfficeId"));
				arsViewInfo.setNCurrencyId(transRS.getLong("nCurrencyId"));
				arsViewInfo.setSystemCode(transRS.getString("systemcode"));
				arsViewInfo.setSystemName(transRS.getString("systemname"));
				arsViewInfo.setUpAccountCode(transRS.getString("upAccountCode"));
				arsViewInfo.setUpAccountName(transRS.getString("upAccountName"));
				arsViewInfo.setAccountCode(transRS.getString("accountcode"));
				arsViewInfo.setAccountName(transRS.getString("accountname"));
				arsViewInfo.setClientCode(transRS.getString("clientcode"));
				arsViewInfo.setClientName(transRS.getString("clientname"));
				arsViewInfo.setNTransId(transRS.getLong("ntransid"));
				arsViewInfo.setTransNo(transRS.getString("transno"));
				arsViewInfo.setTransactionTypeID(transRS.getLong("ntransactiontypeid"));
				arsViewInfo.setMbalance(transRS.getDouble("amount"));
				coll.add(arsViewInfo);
			}
			
			if(coll.size() == 0){
				return null;
			}
			else {
				return coll;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("查询根据体系ID 查找有效的交易记录，（企业资金上拨、下划）信息失败",sqle);
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 企业撤销资金上划、下拨 时使用此方法
	 * 
	 * @param nAccountSystemId
	 * @param nId
	 * @param nTransactionTypeId
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findAccountRelationColl(long nOfficeId, long nCurrencyId, long nTransactionTypeId, Timestamp dtExecute) throws ITreasuryDAOException{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select e.systemcode systemcode, e.systemname systemname, e.accountcode upAccountCode, e.accountname upAccountName, a.*,b.saccountno accountcode, b.sname accountname, c.sCode clientcode, c.sName clientname,d.id ntransid, d.stransno transno, d.ntransactiontypeid ntransactiontypeid, d.dtexecute dtexecute, d.mamount amount \n");
			buffer.append("from " + strTableName + " a, sett_account b, client c, sett_transcurrentdeposit d, \n");
			buffer.append("(select t1.id id, t1.systemcode systemcode, t1.systemname systemname, t2.saccountno accountcode, t2.sname accountname \n");
			buffer.append("from sett_accountsystemsetting t1, sett_account t2 \n");
			buffer.append("where t1.nupaccountid = t2.id and t1.nstatusid in ("+ AccountSystemConstant.SettingStatus.CHECKED +","+ AccountSystemConstant.SettingStatus.DELETING +","+ AccountSystemConstant.SettingStatus.MODIFING +")) e \n");
			buffer.append("WHERE a.nstatusid in ("+ AccountSystemConstant.RelationStatus.CHECKED +", "+ AccountSystemConstant.RelationStatus.DELETING +") \n");
			buffer.append("and a.nofficeid = " + nOfficeId + " \n");
			buffer.append("and a.ncurrencyid = " + nCurrencyId + " \n");
			buffer.append("and a.naccountsystemid = e.id \n");
			buffer.append("and a.naccountid = b.id \n");
			buffer.append("and b.nclientid = c.id \n");
			if(nTransactionTypeId == SETTConstant.TransactionType.CAPITALUP){
				buffer.append("and a.naccountid = d.npayaccountid \n");
			}
			else if(nTransactionTypeId == SETTConstant.TransactionType.CAPITALDOWN){
				buffer.append("and a.naccountid = d.nreceiveaccountid \n");
			}
			buffer.append("and d.dtexecute = to_date('"+ DataFormat.formatDate(dtExecute) +"','yyyy-mm-dd') \n");
			buffer.append("and d.ntransactiontypeid = "+ nTransactionTypeId +" \n");
			buffer.append("and d.nstatusid = "+ SETTConstant.TransactionStatus.CHECK +" \n");
	
			prepareStatement( buffer.toString());		
			executeQuery();
	
			Collection coll = new ArrayList();
			
			while(transRS.next()){
				AccountRelationSettingViewInfo arsViewInfo = new AccountRelationSettingViewInfo();
				arsViewInfo.setId(transRS.getLong("id"));
				arsViewInfo.setNAccountSystemId(transRS.getLong("nAccountSystemId"));
				arsViewInfo.setNAccountId(transRS.getLong("nAccountId"));
				arsViewInfo.setNStatusId(transRS.getLong("nStatusId"));
				arsViewInfo.setNOfficeId(transRS.getLong("nOfficeId"));
				arsViewInfo.setNCurrencyId(transRS.getLong("nCurrencyId"));
				arsViewInfo.setSystemCode(transRS.getString("systemcode"));
				arsViewInfo.setSystemName(transRS.getString("systemname"));
				arsViewInfo.setUpAccountCode(transRS.getString("upAccountCode"));
				arsViewInfo.setUpAccountName(transRS.getString("upAccountName"));
				arsViewInfo.setAccountCode(transRS.getString("accountcode"));
				arsViewInfo.setAccountName(transRS.getString("accountname"));
				arsViewInfo.setClientCode(transRS.getString("clientcode"));
				arsViewInfo.setClientName(transRS.getString("clientname"));
				arsViewInfo.setNTransId(transRS.getLong("ntransid"));
				arsViewInfo.setTransNo(transRS.getString("transno"));
				arsViewInfo.setTransactionTypeID(transRS.getLong("ntransactiontypeid"));
				arsViewInfo.setMbalance(transRS.getDouble("amount"));
				coll.add(arsViewInfo);
			}
			
			if(coll.size() == 0){
				return null;
			}
			else {
				return coll;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("查询上拨、下划休息失败",sqle);
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 企业资金上划、下拨 时使用此方法
	 * 
	 * @param nAccountSystemId
	 * @param nId
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public Collection findAccountRelationColl(long nId, long nAccountSystemId, long nOfficeId, long nCurrencyId) throws ITreasuryDAOException{
		try{
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select a.*,b.saccountno accountcode, b.sname accountname, c.sCode clientcode, c.sName clientname, d.mbalance \n");
			buffer.append("from " + strTableName + " a, sett_account b, client c,sett_subaccount d \n");
			buffer.append("WHERE a.nstatusid in ("+ AccountSystemConstant.RelationStatus.CHECKED +", "+ AccountSystemConstant.RelationStatus.DELETING +") \n");
			buffer.append("and a.nofficeid = " + nOfficeId + " \n");
			buffer.append("and a.ncurrencyid = " + nCurrencyId + " \n");
			if(nId > 0){
				buffer.append("and a.id = " + nId + " \n");
			}
			if(nAccountSystemId > 0){
				buffer.append("and a.nAccountSystemId = " + nAccountSystemId + " \n");
			}
			buffer.append("and a.naccountid = b.id \n");
			buffer.append("and b.id = d.naccountid \n");
			buffer.append("and b.nclientid = c.id");
	
			prepareStatement( buffer.toString());		
			executeQuery();
	
			Collection coll = new ArrayList();
			
			while(transRS.next()){
				AccountRelationSettingViewInfo arsViewInfo = new AccountRelationSettingViewInfo();
				arsViewInfo.setId(transRS.getLong("id"));
				arsViewInfo.setNAccountSystemId(transRS.getLong("nAccountSystemId"));
				arsViewInfo.setNAccountId(transRS.getLong("nAccountId"));
				arsViewInfo.setNStatusId(transRS.getLong("nStatusId"));
				arsViewInfo.setNOfficeId(transRS.getLong("nOfficeId"));
				arsViewInfo.setNCurrencyId(transRS.getLong("nCurrencyId"));
				arsViewInfo.setAccountCode(transRS.getString("accountcode"));
				arsViewInfo.setAccountName(transRS.getString("accountname"));
				arsViewInfo.setClientCode(transRS.getString("clientcode"));
				arsViewInfo.setClientName(transRS.getString("clientname"));
				arsViewInfo.setMbalance(transRS.getDouble("mbalance"));
				coll.add(arsViewInfo);
			}
			
			if(coll.size() == 0){
				return null;
			}
			else {
				return coll;
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("查询上拨、下划休息失败",sqle);
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 获取最大ID
	 * 
	 * @return
	 * @throws ITreasuryDAOException
	 * @throws SQLException
	 */
	public long getMaxID()  throws ITreasuryDAOException, SQLException{
		long id = -1;
		initDAO();
		prepareStatement("select nvl(max(ID)+1,1) ID from " + strTableName);
		executeQuery();
		if(transRS.next()){
			id = transRS.getLong("ID");
		}
		finalizeDAO();			
		return id;		
	}

	public void add(AccountRelationSettingInfo arsInfo) throws ITreasuryDAOException{
		try{
			initDAO();
					
			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO "+ strTableName+" (");
			buffer.append("ID, NACCOUNTSYSTEMID, NACCOUNTID, NSTATUSID, NOFFICEID, NCURRENCYID) ");
			buffer.append("VALUES(?,?,?,?,?,?)");				
			
			prepareStatement(buffer.toString());
			this.transPS.setLong(1, arsInfo.getId());
			this.transPS.setLong(2, arsInfo.getNAccountSystemId());
			this.transPS.setLong(3, arsInfo.getNAccountId());
			this.transPS.setLong(4, arsInfo.getNStatusId());
			this.transPS.setLong(5, arsInfo.getNOfficeId());
			this.transPS.setLong(6, arsInfo.getNCurrencyId());
			
			executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
			throw new ITreasuryDAOException("添加体系帐户失败",sqle);
		}finally{
			finalizeDAO();
		}
	}

}

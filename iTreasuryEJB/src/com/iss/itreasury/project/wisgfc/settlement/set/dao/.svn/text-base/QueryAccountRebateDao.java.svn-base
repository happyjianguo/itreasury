package com.iss.itreasury.project.wisgfc.settlement.set.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.archivesmanagement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.QueryAcountWhereInfo;
import com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SetAcountRebateInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;

public class QueryAccountRebateDao extends ITreasuryDAO{
	public final static int OrderBy_AccountNo = 1;
	public final static int OrderBy_acountID = 2;
	public final static int OrderBy_AccountName = 3;
	public final static int OrderBy_accountTypeId = 4;
	public final static int OrderBy_Rebate = 5;
	public final static int OrderBy_setUserID = 6;
	public final static int OrderBy_setDate = 7;
	//
	public StringBuffer m_sbSelect = null;
	public StringBuffer m_sbFrom = null;
	public StringBuffer m_sbWhere = null;
	public StringBuffer m_sbOrderBy = null;	
	public QueryAccountRebateDao(){
		this.strTableName ="sett_accountrebate";
	}
	public QueryAccountRebateDao(Connection conn) {
		super(conn);
		this.strTableName ="sett_accountrebate";
	}

	public PageLoader queryAccountInfoForSetRebate(QueryAcountWhereInfo qaci) throws Exception{
		getAccountInfoForSetRebateSQL(qaci);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SetAcountRebateInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	public PageLoader queryAllAccountInfoForSetRebate(QueryAcountWhereInfo qaci) throws Exception{
		getAllAccountInfoForSetRebateSQL(qaci);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
			new AppContext(),
			m_sbFrom.toString(),
			m_sbSelect.toString(),
			m_sbWhere.toString(),
			(int) Constant.PageControl.CODE_PAGELINECOUNT,
			"com.iss.itreasury.project.wisgfc.settlement.set.dataentity.SetAcountRebateInfo",
			null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}

	private void getAccountInfoForSetRebateSQL(QueryAcountWhereInfo qaci) {
		// TODO Auto-generated method stub
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("b.ID id, \n");
		m_sbSelect.append("a.ID acountID, \n");
		m_sbSelect.append("a.SACCOUNTNO accountNO, \n");
		m_sbSelect.append("a.NCLIENTID clientId, \n");
		m_sbSelect.append("a.NACCOUNTTYPEID accountTypeId, \n");
		m_sbSelect.append("b.rebete rebate, \n");
		m_sbSelect.append("b.inputuserid setUserID, \n");
		m_sbSelect.append("b.inputdate setDate \n");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_account a,\n");
		m_sbFrom.append(" sett_accountrebate b\n");
		
		m_sbWhere = new StringBuffer();
		if(qaci.getStatus()==1){
			m_sbWhere.append(" a.id = b.accountid\n");
			m_sbWhere.append(" and b.STATESID>0 \n");
		}else if(qaci.getStatus() ==2){
			m_sbWhere.append(" a.id = b.accountid(+)\n");
			m_sbWhere.append(" and a.id not in (select accountid from  sett_accountrebate where STATESID>0)\n");
		}else{
			m_sbWhere.append(" a.id = b.accountid(+)\n");
		}
		m_sbWhere.append(" and a.NSTATUSID="+SETTConstant.AccountStatus.NORMAL);
		m_sbWhere.append(" and a.NCHECKSTATUSID = 4");
		if(qaci.getClientId()>0){
			m_sbWhere.append("\n and NCLIENTID="+qaci.getClientId());
		}
		if(qaci.getCurrenyId()>0){
			m_sbWhere.append("\n and NCURRENCYID="+qaci.getCurrenyId());
		}
		if(qaci.getOfficeId()>0){
			m_sbWhere.append("\n and NOFFICEID="+qaci.getOfficeId());
		}
		if(qaci.getAccountNO() != null && qaci.getAccountNO().trim().length()>0){
			m_sbWhere.append("\n and SACCOUNTNO='"+qaci.getAccountNO()+"'");
		}
		if(qaci.getAccountType()!=null && qaci.getAccountType().trim().length()>0){
			m_sbWhere.append("\n and NACCOUNTTYPEID in("+qaci.getAccountType()+")");
		}
		m_sbOrderBy = new StringBuffer();
		String sDest = qaci.getDesc()==1?"Desc":"";
		switch ((int)qaci.getOrderField()) {
		case OrderBy_AccountNo:
			m_sbOrderBy.append("\n order by SACCOUNTNO "+sDest);
			break;
		case OrderBy_AccountName:
			m_sbOrderBy.append("\n order by SNAME "+sDest);
			break;
		case OrderBy_accountTypeId:
			m_sbOrderBy.append("\n order by NACCOUNTTYPEID "+sDest);
			break;
		case OrderBy_acountID:
			m_sbOrderBy.append("\n order by acountID "+sDest);
			break;
		case OrderBy_setUserID:
			m_sbOrderBy.append("\n order by inputuserid "+sDest);
			break;
		case OrderBy_Rebate:
			m_sbOrderBy.append("\n order by rebate "+sDest);
			break;
		case OrderBy_setDate:
			m_sbOrderBy.append("\n order by inputdate "+sDest);
			break;
		default:
			m_sbOrderBy.append("\n order by SACCOUNTNO ");
			break;
		}
	}
	
	public void setOrderBy(long lOrder,long lDesc){
		m_sbOrderBy = new StringBuffer();
		String sDest = lDesc==1?"Desc":"";
		switch ((int)lOrder) {
		case OrderBy_AccountNo:
			m_sbOrderBy.append("\n order by SACCOUNTNO "+sDest);
			break;
		case OrderBy_AccountName:
			m_sbOrderBy.append("\n order by SNAME "+sDest);
			break;
		case OrderBy_accountTypeId:
			m_sbOrderBy.append("\n order by NACCOUNTTYPEID "+sDest);
			break;
		case OrderBy_acountID:
			m_sbOrderBy.append("\n order by acountID "+sDest);
			break;
		case OrderBy_setUserID:
			m_sbOrderBy.append("\n order by inputuserid "+sDest);
			break;
		case OrderBy_Rebate:
			m_sbOrderBy.append("\n order by rebate "+sDest);
			break;
		case OrderBy_setDate:
			m_sbOrderBy.append("\n order by inputdate "+sDest);
			break;
		default:
			m_sbOrderBy.append("\n order by SACCOUNTNO ");
			break;
		}
	}
	public StringBuffer getOrderBy(){
		return m_sbOrderBy;
	}
	
	public void save(SetAcountRebateInfo[] setAcountRebateInfos) throws ITreasuryDAOException, SQLException{
		try {
			initDAO();
			transConn.setAutoCommit(false);
			setUseMaxID();
			// 设置空值到DataEntity的已使用表,使得setPrepareStatementByDataEntity会ID进行付值
			for(int i =0;i<setAcountRebateInfos.length;i++){
				SetAcountRebateInfo setAcountRebateInfo =setAcountRebateInfos[i];
				setAcountRebateInfo.setId(-1);
				StringBuffer buffer = new StringBuffer();
				buffer.append("INSERT INTO " + strTableName + " (\n");
				String[] buffers = getAllFieldNameBuffer(setAcountRebateInfo,
						DAO_OPERATION_ADD);
				buffer.append(buffers[0]);
				buffer.append("\n) " + "VALUES (\n");
				buffer.append(buffers[1] + ") \n");
				String strSQL = buffer.toString();
				log.debug(strSQL);
				prepareStatement(strSQL);
				setPrepareStatementByDataEntity(setAcountRebateInfo, DAO_OPERATION_ADD,
						buffers[0].toString());
				transPS.execute();
			}
			transConn.commit();
		} catch (ITreasuryDAOException ide) {
			transConn.rollback();
			throw ide;
		} finally{
			finalizeDAO();
		}
	}
	
	public void update(SetAcountRebateInfo[] setAcountRebateInfos) throws SQLException, ITreasuryDAOException{
		try{
			initDAO();
			transConn.setAutoCommit(false);
			for(int i =0;i<setAcountRebateInfos.length;i++){
				SetAcountRebateInfo setAcountRebateInfo =setAcountRebateInfos[i];
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE " + strTableName + " SET \n");
			String[] buffers = getAllFieldNameBuffer(setAcountRebateInfo,
					DAO_OPERATION_UPDATE);
			buffer.append(buffers[0]);
			buffer.append(" WHERE ID = " + setAcountRebateInfo.getId());
	
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(setAcountRebateInfo, DAO_OPERATION_UPDATE,
					buffers[0].toString());
			transPS.execute();
			}
			transConn.commit();
		}catch(Exception e){
			transConn.rollback();
			throw new RuntimeException(e);
		}finally{
			finalizeDAO();
		}
	}
	
	//查询已设置或未设置的交易手续费折扣率
	private void getAllAccountInfoForSetRebateSQL(QueryAcountWhereInfo qaci) {
		// TODO Auto-generated method stub
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("b.ID id, \n");
		m_sbSelect.append("a.ID acountID, \n");
		m_sbSelect.append("a.SACCOUNTNO accountNO, \n");
		m_sbSelect.append("a.NCLIENTID clientId, \n");
		m_sbSelect.append("a.NACCOUNTTYPEID accountTypeId, \n");
		m_sbSelect.append("b.rebete rebate, \n");
		m_sbSelect.append("b.inputuserid setUserID, \n");
		m_sbSelect.append("b.inputdate setDate \n");
		m_sbFrom = new StringBuffer();
		m_sbFrom.append(" sett_account a,\n");
		m_sbFrom.append(" sett_accountrebate b\n");
		
		m_sbWhere = new StringBuffer();
		if(qaci.getStatus()==1){
			m_sbWhere.append(" a.id = b.accountid\n");
			m_sbWhere.append(" and b.STATESID>0 \n");
		}else if(qaci.getStatus() ==2){
			m_sbWhere.append(" a.id = b.accountid(+)\n");
		}else{
			m_sbWhere.append(" a.id = b.accountid(+)\n");
		}
		m_sbWhere.append(" and a.NSTATUSID="+SETTConstant.AccountStatus.NORMAL);
		if(qaci.getClientId()>0){
			m_sbWhere.append("\n and NCLIENTID="+qaci.getClientId());
		}
		if(qaci.getCurrenyId()>0){
			m_sbWhere.append("\n and NCURRENCYID="+qaci.getCurrenyId());
		}
		if(qaci.getOfficeId()>0){
			m_sbWhere.append("\n and NOFFICEID="+qaci.getOfficeId());
		}
		if(qaci.getAccountNO() != null && qaci.getAccountNO().trim().length()>0){
			m_sbWhere.append("\n and SACCOUNTNO='"+qaci.getAccountNO()+"'");
		}
		if(qaci.getAccountType()!=null && qaci.getAccountType().trim().length()>0){
			m_sbWhere.append("\n and NACCOUNTTYPEID in("+qaci.getAccountType()+")");
		}
		m_sbOrderBy = new StringBuffer();
		String sDest = qaci.getDesc()==1?"Desc":"";
		switch ((int)qaci.getOrderField()) {
		case OrderBy_AccountNo:
			m_sbOrderBy.append("\n order by SACCOUNTNO "+sDest);
			break;
		case OrderBy_AccountName:
			m_sbOrderBy.append("\n order by SNAME "+sDest);
			break;
		case OrderBy_accountTypeId:
			m_sbOrderBy.append("\n order by NACCOUNTTYPEID "+sDest);
			break;
		case OrderBy_acountID:
			m_sbOrderBy.append("\n order by acountID "+sDest);
			break;
		case OrderBy_setUserID:
			m_sbOrderBy.append("\n order by inputuserid "+sDest);
			break;
		case OrderBy_Rebate:
			m_sbOrderBy.append("\n order by rebate "+sDest);
			break;
		case OrderBy_setDate:
			m_sbOrderBy.append("\n order by inputdate "+sDest);
			break;
		default:
			m_sbOrderBy.append("\n order by SACCOUNTNO ");
			break;
		}
	}
}

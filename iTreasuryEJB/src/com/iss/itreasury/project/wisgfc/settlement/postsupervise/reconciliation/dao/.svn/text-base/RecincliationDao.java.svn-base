package com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.system.dao.PageLoader;
/**
 * 2010-11-04    操作调节表回收率表
 * @author yongchen
 *
 */
public class RecincliationDao extends ITreasuryDAO {
	
	public final static int OrderBy_Date = 1;
	public final static int OrderBy_Recover = 2;
	public final static int OrderBy_SumNumber = 3;
	public final static int OrderBy_CheckNumber = 4;
	public final static int OrderBy_DepositsTotal = 5;
	public final static int OrderBy_LoansTotal =6;
	
	private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
	
	public RecincliationDao(){
		super("SETT_RECONCILIATION");
	}
	
	public RecincliationDao(Connection conn){
		super("SETT_RECONCILIATION",conn);
	}
	/**
	 * 分页查询调节表回收率信息 2010-11-04
	 * @return
	 * @throws Exception
	 */
	 public PageLoader queryReconciliationInfo(long dateYeay,long dateMonth)
	    throws Exception {
	    // 获取SQL字句
	    	getRecincliationInfoSQL(dateYeay,dateMonth);
	    // 获取PageLoader对象
	    PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.
	        getBaseObject("com.iss.system.dao.PageLoader");

	    // 初始化PageLoader对象、实现查询和分页
	    pageLoader.initPageLoader(
	        new AppContext(),
	        m_sbFrom.toString(),
	        m_sbSelect.toString(),
	        m_sbWhere.toString(),
	        (int) Constant.PageControl.CODE_PAGELINECOUNT,
	        "com.iss.itreasury.project.wisgfc.settlement.postsupervise.reconciliation.dataentity.ReconcliationInfo",
	        null);
	    pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
	    return pageLoader;
	}
	
	private void getRecincliationInfoSQL(long dateYeay,long dateMonth) {
        // create select
        m_sbSelect = new StringBuffer();
        m_sbSelect.append("\n ID id,YEAR year, \n");
        m_sbSelect.append("MONTH month, \n");
		m_sbSelect.append("RECOVER recover, \n");
		m_sbSelect.append("NVL(DEPOSITSTOTAL,0.0) depositsTotal, \n");
        m_sbSelect.append("NVL(LOANSTOTAL,0.0) loansTotal, \n");
		m_sbSelect.append("INPUTUSERID inputUserID, \n");
		m_sbSelect.append("INPUTDATE inputDate, \n");
		m_sbSelect.append("SUMNUMBER sumNumber, \n");
		m_sbSelect.append("CHECKNUMBER checkNumber \n");
        m_sbFrom = new StringBuffer();
        
    	m_sbFrom.append("Sett_Reconciliation");
        m_sbWhere = new StringBuffer();
        m_sbWhere.append(" 1=1 ");
        if(dateYeay>0){
        	m_sbWhere.append(" and YEAR ="+dateYeay);
        }
        if(dateMonth >0){
        	m_sbWhere.append(" and MONTH ="+dateMonth);
        }
        if(m_sbOrderBy ==null){
        	m_sbOrderBy = new StringBuffer();
        	m_sbOrderBy.append("order by year,month desc");
        }
	}
	public long addRecincliation(ReconcliationInfo reconcliationInfo) throws SQLException {
		long id = -1;
		if(isRepeat(reconcliationInfo)){
			throw new RuntimeException(reconcliationInfo.getYear()+"年"+reconcliationInfo.getMonth()+"月已经做过调节表回收率计算");
		}
		try {
			setUseMaxID();
			reconcliationInfo.setId(-1);

			StringBuffer buffer = new StringBuffer();
			buffer.append("INSERT INTO " + strTableName + " (\n");
			String[] buffers = getAllFieldNameBuffer(reconcliationInfo,
					DAO_OPERATION_ADD);
			buffer.append(buffers[0]);
			buffer.append("\n) " + "VALUES (\n");
			buffer.append(buffers[1] + ") \n");

			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);

			id = setPrepareStatementByDataEntity(reconcliationInfo, DAO_OPERATION_ADD,
					buffers[0].toString());

			executeUpdate();
		} catch (ITreasuryDAOException e) {
			throw new RuntimeException(e);
		}finally{
			if(transPS!=null){
				transPS.close();
			}
		}
		return id;
	}
	/**
	 * 判断年份和月份是否已经做过回收率计算
	 * @param reconcliationInfo
	 * @return
	 * @throws SQLException 
	 */
	private boolean isRepeat(ReconcliationInfo reconcliationInfo) throws SQLException{
		boolean isRepeat = false;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from ");
			sql.append(strTableName);
			sql.append(" where YEAR=? ");
			sql.append("and MONTH=?");
			prepareStatement(sql.toString());
			transPS.setLong(1, reconcliationInfo.getYear());
			transPS.setLong(2,reconcliationInfo.getMonth());
			transRS = executeQuery();
			if(transRS.next()){
				if(transRS.getLong(1)>0){
					isRepeat = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}finally{
			if(transRS!=null){
				transRS.close();
			}
			if(transPS!=null){
				transPS.close();
			}
		}
		return isRepeat;
	}
	
	public boolean isExist(long year,long month) throws ITreasuryDAOException{
		boolean isRepeat = false;
		try {
			initDAO();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from ");
			sql.append(strTableName);
			sql.append(" where YEAR=? ");
			sql.append("and MONTH=?");
			prepareStatement(sql.toString());
			transPS.setLong(1, year);
			transPS.setLong(2,month);
			transRS = executeQuery();
			if(transRS.next()){
				if(transRS.getLong(1)>0){
					isRepeat = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}finally{
			finalizeDAO();
		}
		return isRepeat;
	}
	
	/**
	 * 排序信息
	 * @return
	 */
	public StringBuffer getOrderBy() {
		return m_sbOrderBy;
	}
	
	/**
	 * 
	 * @param qaawi
	 */
	public void setOrderBy(long field, long desc) {
		// create orderby
		m_sbOrderBy = new StringBuffer();
		String sDesc = desc == 1 ? " desc " : "";
		switch ((int) field) {
		case OrderBy_Date:
			m_sbOrderBy.append(" \n order by year,month" + sDesc);
			break;
		case OrderBy_Recover:
			m_sbOrderBy.append(" \n order by recover" + sDesc);
			break;
		case OrderBy_CheckNumber:
			m_sbOrderBy.append(" \n order by checkNumber" + sDesc);
			break;
		case OrderBy_SumNumber:
			m_sbOrderBy.append(" \n order by sumNumber" + sDesc);
			break;
		case OrderBy_DepositsTotal:
			m_sbOrderBy.append(" \n order by depositsTotal" + sDesc);
			break;
		case OrderBy_LoansTotal:
			m_sbOrderBy.append(" \n order by loansTotal" + sDesc);
			break;
		default:
			m_sbOrderBy.append(" \n order by year,month" + sDesc);
			break;
		}
	}
	
	
	public void update(ReconcliationInfo reconcliationInfo) throws ITreasuryDAOException, SQLException{
		StringBuffer buffer = new StringBuffer();
		buffer.append("UPDATE " + strTableName + " SET \n");
		try{
			String[] buffers = getAllFieldNameBuffer(reconcliationInfo,
					DAO_OPERATION_UPDATE);
			buffer.append(buffers[0]);
			buffer.append(" WHERE ID = " + reconcliationInfo.getId());
	
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			setPrepareStatementByDataEntity(reconcliationInfo, DAO_OPERATION_UPDATE,
					buffers[0].toString());
	
			executeUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(transPS!=null){
				transPS.close();
			}
		}
	}
	
	public void deleteReconcliationInfo(long id) throws ITreasuryDAOException, SQLException{
		String strSQL = "delete from " + strTableName + " where id = " + id;
		log.debug(strSQL);
		try{
		prepareStatement(strSQL);
		executeUpdate();
		}catch(ITreasuryDAOException e){
			throw new RuntimeException(e);
		}finally{
			if(transPS!=null){
				transPS.close();
			}
		}
	}
}

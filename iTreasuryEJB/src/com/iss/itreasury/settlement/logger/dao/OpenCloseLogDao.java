/*
 * Created on 2007-6-27
 */
package com.iss.itreasury.settlement.logger.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Vector;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.logger.dataentity.ErrorBussinessInfo;
import com.iss.itreasury.settlement.logger.dataentity.OpenCloseLogDetailInfo;
import com.iss.itreasury.settlement.logger.dataentity.OpenCloseLogInfo;
import com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog;
import com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;
import com.iss.system.BaseObjectFactory;

/**
 * @author leiyang
 *
 */
public class OpenCloseLogDao extends ITreasuryDAO {
	
	/**
	 * 自己管理Connection
	 * @param conn
	 */
	public OpenCloseLogDao(Connection conn){
		super("opencloselog",conn);
	}
	
	/**
	 * 系统管理Connection
	 *
	 */
	public OpenCloseLogDao(){
		super("opencloselog");
	}

	/**
	 * 向OpenColoseLog添加一条记录
	 * @param info
	 * @throws Exception
	 */
	public long addLog(OpenCloseLogInfo info, Timestamp systemDate) throws ITreasuryDAOException {
		long logId = -1;
		try {
			logId = this.getMaxId();
			
			initDAO();
			StringBuffer strSQL = new StringBuffer("insert into opencloselog values(?,?,?,?,?,?,?,?,?,?,?)");
			prepareStatement(strSQL.toString());

			transPS.setLong(1,logId);
			transPS.setLong(2,info.getOfficeId());
			transPS.setLong(3,info.getCurrencyId());
			transPS.setLong(4,info.getOpenCloseUserId());
			transPS.setTimestamp(5,systemDate);
			transPS.setLong(6,info.getStatus());
			transPS.setLong(7,info.getOpenCloseType());
			transPS.setLong(8,info.getLogType());
			transPS.setLong(9,info.getBatchNo());
			transPS.setString(10, info.getContent());
			transPS.setString(11, info.getCode());
			
			executeUpdate();
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		}
		finally{
			finalizeDAO();
		}
		return logId;
	}
	
    /**
     * 得到最大ID
     * @param tableName
     * @return
     * @throws IException
     * @throws SQLException 
     */
    private long getMaxId() throws ITreasuryDAOException {
    	long lMaxID = -1;
    	try {
    		initDAO();
        	String strSql = "SELECT SEQ_opencloselog.nextval nextid from dual";
			prepareStatement(strSql);
			executeQuery();
			
            if(transRS.next()) {
            	lMaxID = transRS.getLong("nextid");
            }
            
            System.out.println(strSql);
        }
        catch(SQLException e) {
             throw new ITreasuryDAOException("数据库执行异常",e);
        }
		finally{
			finalizeDAO();
		}
        return lMaxID;
    }
	
	/**
	 * 向OpenColoseLogDetail添加一条记录
	 * @param detailInfo
	 * @throws Exception
	 */
	public void addDetailLog(OpenCloseLogDetailInfo detailInfo) throws ITreasuryDAOException {
		try {
			initDAO();
			StringBuffer strSQL = new StringBuffer("insert into opencloselogdetail values(SEQ_opencloselogdetail.nextval,?,?,?,?,?,?)");
			prepareStatement(strSQL.toString());
			
			transPS.setLong(1,detailInfo.getOpenCloseLogId());
			transPS.setLong(2,detailInfo.getTransTypeId());
			transPS.setLong(3,detailInfo.getTransId());
			transPS.setString(4,detailInfo.getTransNo());
			transPS.setLong(5,detailInfo.getLogType());
			transPS.setLong(6,detailInfo.getBatchNo());
			
			executeUpdate();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		} 
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * 查询操作次数
	 * @param info
	 * @return
	 * @throws SQLException 
	 * @throws ITreasuryDAOException 
	 */
	public long getBatchNo(long officeId,long currencyId,long openCloseType,Timestamp systemData) throws ITreasuryDAOException{
		long batchNo = -1;
		try {
			initDAO();
			StringBuffer strSQL = new StringBuffer();
			strSQL.append("select nvl(count(code)+1,1) id from ");
			strSQL.append("(select t.code from opencloselog t ");
			strSQL.append("where to_char(t.openclosedate,'yyyy-mm-dd')  = '"+DataFormat.getDateString(systemData)+"' ");
			strSQL.append("and t.officeid = ? ");
			strSQL.append("and t.currencyid = ? ");
			strSQL.append("and t.openclosetype = ? ");
			strSQL.append("group by t.code)");
			prepareStatement(strSQL.toString());
			
			transPS.setLong(1,officeId);
			transPS.setLong(2,currencyId);
			transPS.setLong(3,openCloseType);
			executeQuery();
			
			while(transRS.next()) {
				batchNo = transRS.getLong("id");
			}
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		}
		finally{
			finalizeDAO();
		}
		return batchNo;
	}
	
	/**
	 * 返回关机日志集合
	 * @param officeId
	 * @param currencyId
	 * @param openCloseUserId
	 * @return
	 * @throws SQLException
	 */
	public Collection getCloseLogInfo(String code) throws ITreasuryDAOException {
		Vector v = new Vector();
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select * from opencloselog");
			strSQL.append(" where code = '"+ code +"'");
			strSQL.append(" order by id");
			prepareStatement(strSQL.toString());
			
			executeQuery();
			while(transRS.next()) {
				OpenCloseLogInfo logInfo = new OpenCloseLogInfo();
				
				long lId = transRS.getLong("id");  //ID
				long lOfficeId = transRS.getLong("officeId");  //办事处ID
				long lCurrencyId = transRS.getLong("currencyId");  //币种ID
				long lOpenCloseUserId = transRS.getLong("openCloseUserId");  //操作用户ID
				Timestamp dtOpenCloseDate = transRS.getTimestamp("openCloseDate");  //操作日期
				long lStatus = transRS.getLong("status");  //状态
				long lOpenCloseType = transRS.getLong("openCloseType");  //开关机类型
				long lLogType = transRS.getLong("logType");  //LOG类型ID
				long lBatchNo = transRS.getLong("batchNo");  //操作次数
				String strContent = transRS.getString("content");  //开关机信息
				String strCode = transRS.getString("code");  //开关机信息
				
				logInfo.setId(lId);
				logInfo.setOfficeId(lOfficeId);
				logInfo.setCurrencyId(lCurrencyId);
				logInfo.setOpenCloseUserId(lOpenCloseUserId);
				logInfo.setOpenCloseDate(dtOpenCloseDate);
				logInfo.setStatus(lStatus);
				logInfo.setOpenCloseType(lOpenCloseType);
				logInfo.setLogType(lLogType);
				logInfo.setBatchNo(lBatchNo);
				logInfo.setContent(strContent);
				logInfo.setCode(strCode);
				
				v.add(logInfo);
			}
			
			finalizeDAO();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		} 
		finally{
			finalizeDAO();
		}
		if(v.size() == 0) {
			return null;
		}
		else {
			return v;
		}
	}
	
	/**
	 * 返回关机日志集合
	 * @param officeId
	 * @param currencyId
	 * @param openCloseUserId
	 * @return
	 * @throws SQLException
	 */
	public Collection getCloseLogInfo(long officeId,long currencyId,long openCloseUserId) throws ITreasuryDAOException {
		Vector v = new Vector();
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select * from opencloselog");
			strSQL.append(" where batchno = ( select max(batchno) from opencloselog");
			strSQL.append(" where officeid = ?");
			strSQL.append(" and currencyid = ?");
			strSQL.append(" and opencloseuserid = ?");
			strSQL.append(" and openclosetype = ? )");
			strSQL.append(" and officeid = ?");
			strSQL.append(" and currencyid = ?");
			strSQL.append(" and opencloseuserid = ?");
			strSQL.append(" and openclosetype = ?");
			strSQL.append(" order by id");
			prepareStatement(strSQL.toString());
			
			transPS.setLong(1,officeId);
			transPS.setLong(2,currencyId);
			transPS.setLong(3,openCloseUserId);
			transPS.setLong(4,SETTConstant.OpenCloseType.CLOSE);
			transPS.setLong(5,officeId);
			transPS.setLong(6,currencyId);
			transPS.setLong(7,openCloseUserId);
			transPS.setLong(8,SETTConstant.OpenCloseType.CLOSE);
			executeQuery();
			
			while(transRS.next()) {
				OpenCloseLogInfo logInfo = new OpenCloseLogInfo();
				
				long lId = transRS.getLong("id");  //ID
				long lOfficeId = transRS.getLong("officeId");  //办事处ID
				long lCurrencyId = transRS.getLong("currencyId");  //币种ID
				long lOpenCloseUserId = transRS.getLong("openCloseUserId");  //操作用户ID
				Timestamp dtOpenCloseDate = transRS.getTimestamp("openCloseDate");  //操作日期
				long lStatus = transRS.getLong("status");  //状态
				long lOpenCloseType = transRS.getLong("openCloseType");  //开关机类型
				long lLogType = transRS.getLong("logType");  //LOG类型ID
				long lBatchNo = transRS.getLong("batchNo");  //操作次数
				String strContent = transRS.getString("content");  //开关机信息
				String strCode = transRS.getString("code");  //开关机信息
				
				logInfo.setId(lId);
				logInfo.setOfficeId(lOfficeId);
				logInfo.setCurrencyId(lCurrencyId);
				logInfo.setOpenCloseUserId(lOpenCloseUserId);
				logInfo.setOpenCloseDate(dtOpenCloseDate);
				logInfo.setStatus(lStatus);
				logInfo.setOpenCloseType(lOpenCloseType);
				logInfo.setLogType(lLogType);
				logInfo.setBatchNo(lBatchNo);
				logInfo.setContent(strContent);
				logInfo.setCode(strCode);
				
				v.add(logInfo);
			}
			
			finalizeDAO();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		} 
		finally{
			finalizeDAO();
		}
		if(v.size() == 0) {
			return null;
		}
		else {
			return v;
		}
	}
	
	/**
	 * 返回关机日志业务校验错误信息集合
	 * @param openCloseLogId
	 * @return
	 * @throws SQLException
	 * @throws ITreasuryDAOException 
	 */
	public Collection getCloseSystemErrorBussinessInfo(long openCloseLogId) throws ITreasuryDAOException {
		Vector v = new Vector();
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select transactiontypeid,statusid,count(statusid) quantity");
			strSQL.append(" from (select distinct tab2.id,tab2.transno,tab2.transactiontypeid,tab2.statusid");
			strSQL.append(" from opencloselogdetail tab1, sett_vtransaction tab2");
			strSQL.append(" where tab1.opencloselogid = " + openCloseLogId);
			strSQL.append(" and tab1.transid = tab2.id");
			strSQL.append(" and tab1.transtypeid = tab2.transactiontypeid )");
			strSQL.append(" group by transactiontypeid,statusid");
			prepareStatement(strSQL.toString());
			executeQuery();
			
			while(transRS.next()) {
				ErrorBussinessInfo errorInfo = new ErrorBussinessInfo();
				
				long transTypeId = transRS.getLong("transactiontypeid");
				String transTypeName = SETTConstant.TransactionType.getName(transTypeId);
				long statusId = transRS.getLong("statusid");
				String statusName = SETTConstant.TransactionStatus.getName(statusId);
				long quantity = transRS.getLong("quantity");

				errorInfo.setTransTypeId(transTypeId);
				errorInfo.setTransTypeName(transTypeName);
				errorInfo.setStatusId(statusId);
				errorInfo.setStatusName(statusName);
				errorInfo.setQuantity(quantity);
				
				v.add(errorInfo);
			}
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		}
		finally{
			finalizeDAO();
		}
		if(v.size() == 0) {
			return null;
		}
		else {
			return v;
		}
	}
	
	public Collection getColseLogTransInfo(long openCloseLogId) throws ITreasuryDAOException {
		Vector v = new Vector();
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select transtypeid from opencloselogdetail");
			strSQL.append(" where opencloselogid = " + openCloseLogId);
			strSQL.append(" group by transtypeid");
			prepareStatement(strSQL.toString());
			executeQuery();
			
			while(transRS.next()) {
				ErrorBussinessInfo errorInfo = new ErrorBussinessInfo();
				
				long transTypeId = transRS.getLong("transtypeid");
				String transTypeName = SETTConstant.TransactionType.getName(transTypeId);

				errorInfo.setTransTypeId(transTypeId);
				errorInfo.setTransTypeName(transTypeName);
				
				v.add(errorInfo);
			}
			
			finalizeDAO();
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		} 
		finally{
			finalizeDAO();
		}
		if(v.size() == 0) {
			return null;
		}
		else {
			return v;
		}
	}
    
    public Collection getOpenCLoseLogInfo(QueryOpenCloseLog searchInfo) throws ITreasuryDAOException {
		Vector v = new Vector();
		
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select * from opencloselog");
			strSQL.append(" where officeid = ?");
			strSQL.append(" and currencyid = ?");
			strSQL.append(" and openclosetype = ?");
			strSQL.append(" and to_char(openclosedate,'yyyy-mm-dd') = ?");
			strSQL.append(" order by id,batchno");
			prepareStatement(strSQL.toString());
		
			transPS.setLong(1,searchInfo.getOfficeId());
			transPS.setLong(2,searchInfo.getCurrencyId());
			transPS.setLong(3,searchInfo.getOpenCloseType());
			transPS.setString(4, searchInfo.getOcDate());
			executeQuery();
			
			while(transRS.next()) {
				OpenCloseLogInfo logInfo = new OpenCloseLogInfo();
				
				long lId = transRS.getLong("id");  //ID
				long lOfficeId = transRS.getLong("officeId");  //办事处ID
				long lCurrencyId = transRS.getLong("currencyId");  //币种ID
				long lOpenCloseUserId = transRS.getLong("openCloseUserId");  //操作用户ID
				Timestamp dtOpenCloseDate = transRS.getTimestamp("openCloseDate");  //操作日期
				long lStatus = transRS.getLong("status");  //状态
				long lOpenCloseType = transRS.getLong("openCloseType");  //开关机类型
				long lLogType = transRS.getLong("logType");  //LOG类型ID
				long lBatchNo = transRS.getLong("batchNo");  //操作次数
				String strContent = transRS.getString("content");  //开关机信息
				
				logInfo.setId(lId);
				logInfo.setOfficeId(lOfficeId);
				logInfo.setCurrencyId(lCurrencyId);
				logInfo.setOpenCloseUserId(lOpenCloseUserId);
				logInfo.setOpenCloseDate(dtOpenCloseDate);
				logInfo.setStatus(lStatus);
				logInfo.setOpenCloseType(lOpenCloseType);
				logInfo.setLogType(lLogType);
				logInfo.setBatchNo(lBatchNo);
				logInfo.setContent(strContent);
				
				v.add(logInfo);
			}
			
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		}
		finally{
			finalizeDAO();
		}
		if(v.size() == 0) {
			return null;
		}
		else {
			return v;
		}	
    }
    
    /**
     * 查询单编号的日志信息
     * @param code
     * @return
     * @throws SQLException 
     * @throws ITreasuryDAOException 
     */
    public QueryOpenCloseLog findOpenCloseLogInfo(String code) throws ITreasuryDAOException {
		QueryOpenCloseLog logInfo = null;
		
		try {
			initDAO();
			StringBuffer strSQL=new StringBuffer();
			strSQL.append("select distinct tab1.officeid,tab1.currencyid,tab1.openclosetype,tab1.opencloseuserid,tab2.sname,to_char(tab1.openclosedate,'yyyy-mm-dd') ocdate,tab1.batchno,tab1.code");
			strSQL.append(" from opencloselog tab1,userinfo tab2");
			strSQL.append(" where tab1.opencloseuserid = tab2.id");
			strSQL.append(" and tab1.code = '"+ code +"'");
			prepareStatement(strSQL.toString());
			executeQuery();
			
			while(transRS.next()) {
				logInfo = new QueryOpenCloseLog();
				
				long lOfficeId = transRS.getLong("officeId");  //办事处ID
				long lCurrencyId = transRS.getLong("currencyId");  //币种ID
				long lOpenCloseType = transRS.getLong("openCloseType");  //开关机类型
				long lOpenCloseUserId = transRS.getLong("openCloseUserId");  //操作用户ID
				String sName = transRS.getString("sname"); //操作用户名称
				String ocDate = transRS.getString("ocdate");  //操作时间
				long lBatchNo = transRS.getLong("batchNo");  //操作次数
				
				logInfo.setOfficeId(lOfficeId);
				logInfo.setCurrencyId(lCurrencyId);
				logInfo.setOpenCloseType(lOpenCloseType);
				logInfo.setOpenCloseUserId(lOpenCloseUserId);
				logInfo.setSName(sName);
				logInfo.setOcDate(ocDate);
				logInfo.setBatchNo(lBatchNo);
				logInfo.setCode(code);
			}
		} catch (SQLException e) {
			throw new ITreasuryDAOException("数据库执行异常",e);
		}
		finally{
			finalizeDAO();
		}
		return logInfo;
    }
    
	/**
	 * 查询开关机日志信息
	 */
	public PageLoader findOpenCloseLogInfo(QueryOpenCloseLog searchInfo) throws RemoteException
	{
		 PageLoader pageLoader =null;
		 StringBuffer sbSelect = new StringBuffer();
		 StringBuffer sbFrom = new StringBuffer();
		 StringBuffer sbWhere = new StringBuffer();
		 String sbOrderby = "";
		 try{
			 sbSelect.append(" distinct tab1.officeid,tab1.currencyid,tab1.openclosetype,tab1.opencloseuserid,tab2.sname,to_char(tab1.openclosedate,'yyyy-mm-dd') ocdate,tab1.batchno,tab1.code");

			 sbFrom.append(" opencloselog tab1,userinfo tab2");

			 sbWhere.append(" tab1.officeid =" + searchInfo.getOfficeId());
			 sbWhere.append(" and tab1.currencyid =" + searchInfo.getCurrencyId());
			 sbWhere.append(" and tab1.openclosetype =" + searchInfo.getOpenCloseType());
			 if(searchInfo.getOpenCloseStartDate() != null && !searchInfo.getOpenCloseStartDate().equals("")) { 
				sbWhere.append(" and to_char(tab1.openclosedate,'yyyy-mm-dd') >= '" + searchInfo.getOpenCloseStartDate() + "'");
			 }
			 if(searchInfo.getOpenCloseEndDate() != null && !searchInfo.getOpenCloseEndDate().equals("")) {
				sbWhere.append(" and to_char(tab1.openclosedate,'yyyy-mm-dd') <= '" + searchInfo.getOpenCloseEndDate() + "'");
			 }
			 sbWhere.append(" and tab1.opencloseuserid = tab2.id");
				
			 sbOrderby = " order by ocdate,tab1.batchno";
				
			 pageLoader = (PageLoader) BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			 pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.logger.dataentity.QueryOpenCloseLog",
				null);
			 pageLoader.setOrderBy(sbOrderby);
			 
		 }
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return pageLoader;
	}
	
	/**
	 * 2011-03-28 马现福 改写此方法
	 * 根据交易类型查询关机中业务校验的详细日志信息
	 */
	public PageLoader findOpenCloseLogDetailInfo(OpenCloseLogDetailInfo searchInfo) throws RemoteException
	{
		 PageLoader pageLoader =null;
		 StringBuffer sbSelect = new StringBuffer();
		 StringBuffer sbFrom = new StringBuffer();
		 StringBuffer sbWhere = new StringBuffer();
		 try{
			 sbSelect.append(" distinct tab2.id,tab2.transno,tab1.transtypeid,tab2.statusid,tab2.serialno");

			 sbFrom.append(" opencloselogdetail tab1, ");
			 sbFrom.append(" (select * from ( ");
			 sbFrom.append("	select vt.id,vt.serialno,vt.transno,vt.transactiontypeid,vt.statusid  ");
			 sbFrom.append("	from sett_vtransaction vt ");
			 sbFrom.append("	union ");
			 sbFrom.append("	select a.id,-1 nserialno,a.af_sdepositno transno,901 transactiontypeid,a.nstatusid statusid ");
			 sbFrom.append("	from sett_subaccount a, sett_account b  ");
			 sbFrom.append("	where a.naccountid = b.id ) ");
			 sbFrom.append("  ) tab2 ");

			 sbWhere.append(" tab1.opencloselogid =" + searchInfo.getOpenCloseLogId());
			 sbWhere.append(" and tab1.transid = tab2.id");
			 sbWhere.append(" and tab1.transtypeid = tab2.transactiontypeid");
			 if(searchInfo.getTransTypeId() != -1) {
				 sbWhere.append(" and tab1.transtypeid =" + searchInfo.getTransTypeId());
			 }
				
				
			 pageLoader = (PageLoader) BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			 pageLoader.initPageLoader(
				new AppContext(),
				sbFrom.toString(),
				sbSelect.toString(),
				sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.logger.dataentity.ErrorBussinessInfo",
				null);
		 }
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
		return pageLoader;
	}
	/**
	 * 开关机日志查询 ——新UI SQL
	 */
public String findOpenCloseLogInfoNew(QueryOpenCloseLog searchInfo){
		
	 StringBuffer sbSelect = new StringBuffer();
	 try{
		 sbSelect.append("select distinct tab1.officeid,tab1.currencyid,tab1.openclosetype,tab1.opencloseuserid,tab2.sname,to_char(tab1.openclosedate,'yyyy-mm-dd') ocdate, '第'||tab1.batchno||'次' batchno,tab1.code");

		 sbSelect.append(" from opencloselog tab1,userinfo tab2");

		 sbSelect.append(" where tab1.officeid =" + searchInfo.getOfficeId());
		 sbSelect.append(" and tab1.currencyid =" + searchInfo.getCurrencyId());
		 sbSelect.append(" and tab1.openclosetype =" + searchInfo.getOpenCloseType());
		 if(searchInfo.getOpenCloseStartDate() != null && !searchInfo.getOpenCloseStartDate().equals("")) { 
			 sbSelect.append(" and to_char(tab1.openclosedate,'yyyy-mm-dd') >= '" + searchInfo.getOpenCloseStartDate() + "'");
		 }
		 if(searchInfo.getOpenCloseEndDate() != null && !searchInfo.getOpenCloseEndDate().equals("")) {
			 sbSelect.append(" and to_char(tab1.openclosedate,'yyyy-mm-dd') <= '" + searchInfo.getOpenCloseEndDate() + "'");
		 }
		 sbSelect.append(" and tab1.opencloseuserid = tab2.id order by ocdate,batchno");
			
	 }
	catch (Exception e)
	{
		e.printStackTrace();
	}
	return sbSelect.toString();
		
	}
	
/**
 * 开关机日志查询详细信息 ——新UI SQL
 * @author songwenxiao
 */
public String findOpenCloseLogDetailNewUI(String code){
	 StringBuffer strSQL=new StringBuffer();
 try{
		strSQL.append("select * from opencloselog");
		strSQL.append(" where code = '"+ code +"'");
		strSQL.append(" order by id");
 }
catch (Exception e)
{
	e.printStackTrace();
}
return strSQL.toString();
	
}
	
}

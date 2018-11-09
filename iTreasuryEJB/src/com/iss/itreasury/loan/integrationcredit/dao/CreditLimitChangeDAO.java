package com.iss.itreasury.loan.integrationcredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitChangeInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;


public class CreditLimitChangeDAO extends LoanDAO{

	 protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	  public CreditLimitChangeDAO() {
	    super("loan_creditLimitdetail");
	  }

	  public CreditLimitChangeDAO(Connection conn) {
	    super("loan_creditLimitdetail", conn);
	  }
	  
	  /**
	   * 保存授信额度变更信息
	   * @param info
	   * @return
	 * @throws SQLException 
	   * @throws Exception
	   */
	  public void saveCreditChange(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{

		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  long id = getMaxID("SEQ_LOAN_CREDITLIMITDETAIL");
	      StringBuffer sqlStr = new StringBuffer();
		  int index = 1;
	      try{
			  conn = Database.getConnection();
			  
			  sqlStr.append(" insert into LOAN_CREDITLIMITDETAIL (ID,CREDITLIMITID,CHANGETYPEID,CHANGEAMOUNT,INPUTUSERID,INPUTDATE,STATUSID) values  \n");
			  sqlStr.append(" (?,?,?,?,?,?,?) ");
			  System.out.println(sqlStr.toString());
			  ps = conn.prepareStatement(sqlStr.toString());
			  ps.setLong(index++, id);
			  ps.setLong(index++, info.getCreditLimitID());
			  ps.setLong(index++, info.getChangeTypeID());
			  ps.setDouble(index++, info.getChangeAmount());
			  ps.setLong(index++, info.getInputUserID());
			  ps.setTimestamp(index++, info.getInputDate());
			  ps.setLong(index++, info.getStatusID());
			  ps.executeUpdate();
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.toString();
			  throw new ITreasuryDAOException("保存变更信息异常",e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
	  }
	  
	  /**
	   * 保存授信额度信息到LOAN_CREDITLIMITDETAIL
	   * @param info
	   * @return
	 * @throws SQLException 
	   * @throws Exception
	   */
	  public long saveCreditLimitDetail(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{

		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  long id = getMaxID("SEQ_LOAN_CREDITLIMITDETAIL");
		  //long lID = -1;
	      StringBuffer sqlStr = new StringBuffer();
		  int index = 1;
		  try{

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
            String applyCode = formatter.format(Env.getSystemDateTime());
	       
			  conn = Database.getConnection();
			  
			  sqlStr.append(" insert into LOAN_CREDITLIMITDETAIL (ID,CREDITLIMITID,CHANGETYPEID,CHANGEAMOUNT,INPUTUSERID,INPUTDATE,STATUSID," +
			  		"APPLYCODE,OFFICEID,CURRENCYID,CLIENTID,CREDITMODEID,CREDITTYPEID,STARTDATE,ENDDATE,ACTIVESTATUSID) values  \n");
			  sqlStr.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			  System.out.println(sqlStr.toString());
			  ps = conn.prepareStatement(sqlStr.toString());
			  ps.setLong(index++, id);
			  ps.setLong(index++, info.getCreditLimitID());
			  ps.setLong(index++, info.getChangeTypeID());
			  ps.setDouble(index++, info.getChangeAmount());
			  ps.setLong(index++, info.getInputUserID());
			  ps.setTimestamp(index++, info.getInputDate());
			  ps.setLong(index++, info.getStatusID());
			  ps.setString(index++, applyCode);
			  ps.setLong(index++, info.getOfficeID());
			  ps.setLong(index++, info.getCurrencyID());
			  ps.setLong(index++, info.getClientID());
			  ps.setLong(index++, info.getCreditModeID());
			  ps.setLong(index++, info.getCreditTypeID());
			  ps.setTimestamp(index++, info.getStartDate());
			  ps.setTimestamp(index++, info.getEndDate());
			  ps.setLong(index++, info.getActiveStatusID());
			  ps.executeUpdate();
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.toString();
			  throw new ITreasuryDAOException("保存变更信息异常",e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return id;
	  }
	  /**
	   * 查询客户某一时间段内是否做过授信额度设置
	   * @param Info
	   * @return
	   * @throws ITreasuryDAOException
	   */
	   public Collection findByDateOption(CreditLimitChangeInfo Info) throws
	       ITreasuryDAOException {
	     Collection c = null;
	     String strSQL = "";
	     if (Info.getStartDate() == null && Info.getEndDate() == null) {
	     	return c;
	     }
	     try {
	       /*-----------------init DAO --------------------*/
	       try {
	         initDAO();
	       }
	       catch (ITreasuryDAOException e) {
	          throw new ITreasuryDAOException("创建连接时异常",e);
	       }
	       /*----------------end of init------------------*/
	       try {
	         strSQL = "select * from loan_creditLimitDetail ";
	         strSQL += " where clientID =" + Info.getClientID();
	         strSQL += " and statusID > 0";
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;
	         //保存时，判断授信产品类型
	         if(Info.getCreditTypeID() > 0){
	         	//授信产品类型为商票
	         	if(Info.getCreditTypeID()==LOANConstant.CreditProduct.SP){
	         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
	         	//授信产品类型为自营
	         	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZY){
	         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.ZHSX+" )";
	         	//综合授信
	         	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZHSX){
	         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
	         	}
	         }
	         if (Info.getId()>0 ){
	 	    	strSQL+="and   id !="+Info.getId();
	 	    }
	         if(Info.getOfficeID()>0){
	         	strSQL+=" and officeid="+Info.getOfficeID();
	         }
	         if(Info.getCurrencyID()>0){
	         	strSQL+=" and currencyid="+Info.getCurrencyID();
	         }
	         strSQL += " and ((STARTDATE<=to_date('" +
	             Info.getStartDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd')";
	         strSQL += " and ENDDATE>=to_date('" +
	             Info.getEndDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd'))";

	         strSQL += " or ( STARTDATE>=to_date('" +
	             Info.getStartDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd')";
	         strSQL += " and STARTDATE<=to_date('" +
	             Info.getEndDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd'))";

	         strSQL += " or ( ENDDATE>=to_date('" +
	             Info.getStartDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd')";
	         strSQL += " and ENDDATE<=to_date('" +
	             Info.getEndDate().toString().substring(0, 10);
	         strSQL += "','yyyy-mm-dd')))";
	 	       
	         System.out.println("sql= \n" + strSQL);
	         prepareStatement(strSQL);
	         executeQuery();
	         c = getDataEntitiesFromResultSet(CreditLimitInfo.class);

	       }
	       catch (Exception e) {
	         throw new ITreasuryDAOException("查询授信设置出错", e);
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
	     return (c.size() > 0 ? c : null);
	   }
	  /**
	   * 获得最大ID
	   * @param seqencesName
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public long getMaxID(String seqencesName) throws ITreasuryDAOException, SQLException{
	
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  long signForID=-1;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();
			  
			  sqlStr.append("select "+seqencesName+".nextval ID from dual ");
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  while(rs.next()){
				  signForID = rs.getLong("ID");
			  }
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.toString();
			  throw new ITreasuryDAOException(e.getMessage(),e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }  
		  return  signForID;
	  }
	  
	  /**
	   *  删除授信额度变更信息（逻辑删除）
	   * @param info
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public void deleteCreditChange(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();
			  
			  sqlStr.append(" update LOAN_CREDITLIMITDETAIL  \n");
			  sqlStr.append(" set ");
			  sqlStr.append(" STATUSID= ? \n");
			  sqlStr.append(" where ID=? ");
			  ps = conn.prepareStatement(sqlStr.toString());
			  ps.setLong(1, LOANConstant.CreditStatus.DELETE);
			  ps.setLong(2, info.getId());
			  ps.executeUpdate();
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException("删除异常",e) ;
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }	  
	  }
	  
	  /**
	   * 更新授信额度变更信息
	   * @param info
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public void updateLimitChange(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();
			  sqlStr.append(" update LOAN_CREDITLIMITDETAIL  \n");
			  sqlStr.append(" set CHANGEAMOUNT=? ,INPUTUSERID=?, \n");
			  sqlStr.append(" INPUTDATE=? ,CHANGETYPEID=?  \n");
			  sqlStr.append(" where ID=? ");
			  ps = conn.prepareStatement(sqlStr.toString());
			  ps.setDouble(1, info.getChangeAmount());
			  ps.setLong(2, info.getInputUserID());
			  ps.setTimestamp(3, info.getInputDate());
			  ps.setLong(4, info.getChangeTypeID());
			  ps.setLong(5, info.getId());
			  ps.executeUpdate();
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException("更新额度信息异常",e) ;
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  } 
	  }
	  
	  /**
	   * 查询授信额度变更后金额信息(激活/取消激活)
	   * @param info
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public Collection findCreditChangeAmount(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{
		  Collection coll = new ArrayList();
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();
			  
			  sqlStr.append(" select * from loan_creditlimitdetail \n");
			  sqlStr.append(" where 1=1 and statusid>0 ");
			  if(info.getCreditLimitID() > 0){
				  sqlStr.append(" and creditlimitid ="+info.getCreditLimitID());
			  }
			  //激活授信额度变更信息操作
			  if(info.getOperate()==1){
				  sqlStr.append(" and STATUSID="+LOANConstant.CreditStatus.ACTIVE);
				  if(info.getId()>0){
					  sqlStr.append("\n union \n");
					  sqlStr.append(" select * from loan_creditlimitdetail where 1=1 ");
					  sqlStr.append(" and id="+info.getId());
				  }
			  //取消激活授信额度变更信息操作
			  }else if(info.getOperate() ==2){
				  sqlStr.append(" and STATUSID="+LOANConstant.CreditStatus.ACTIVE);
				  if(info.getId()>0){
					  sqlStr.append(" and id not in("+info.getId()+") ");					  
				  }
			  }
			  
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  CreditLimitChangeInfo  creditLimitChangeInfo = null;
			  while(rs.next()){
				  creditLimitChangeInfo  = new CreditLimitChangeInfo();
				  creditLimitChangeInfo.setChangeTypeID(rs.getLong("CHANGETYPEID"));
				  creditLimitChangeInfo.setChangeAmount(rs.getDouble("CHANGEAMOUNT"));
				  coll.add(creditLimitChangeInfo);
			  }
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			 e.printStackTrace();
			 throw new ITreasuryDAOException("查询变更信息异常",e);
		  }finally{
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return coll;
	  }
	  
	  /**
	   * 根据授信ID查询此额度是否存在已保存的变更信息
	   * @param info
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public boolean findIsExist(CreditLimitChangeInfo info) throws ITreasuryDAOException, SQLException{
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  boolean flag = false;
	      try{
			  conn = Database.getConnection();
			  
			  sqlStr.append(" select * from loan_creditlimitdetail \n");
			  sqlStr.append(" where 1=1 ");
			  if(info.getCreditLimitID() > 0){
				  sqlStr.append(" and creditlimitid ="+info.getCreditLimitID());
			  }
			  sqlStr.append(" and statusid="+LOANConstant.CreditStatus.SAVE);
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  while(rs.next()){
			        flag = true; 
			  } 
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException("查询异常",e);
		  }finally{
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return flag;
	  }  
	  
	  /**
	   * 查询授信额度变更信息(激活/取消激活)
	   * @param info
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  /*public Collection findCreditChange(CreditLimitQueryInfo info) throws ITreasuryDAOException, SQLException{
		  Collection coll = new ArrayList();
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();	  
			  sqlStr.append(" select lc.*,dd.changeamount,dd.changetypeid ,dd.statusID as cstatusID ,dd.ID as cID \n");
			  sqlStr.append(" from loan_creditLimit lc,CLIENT_CLIENTINFO cc ,LOAN_CREDITLIMITDETAIL dd");
			  sqlStr.append(" where 1=1 and lc.statusid="+LOANConstant.CreditStatus.ACTIVE+" and dd.statusID in("+LOANConstant.CreditStatus.SAVE+","+LOANConstant.CreditStatus.ACTIVE+")");
			  sqlStr.append(" and cc.id = lc.Clientid  and dd.creditlimitid(+)=lc.id ");
	          if(info.getOfficeId() > 0){
	        	  sqlStr.append(" and lc.officeid="+info.getOfficeId());
	          }
	          if(info.getCurrencyID() >0){
	        	  sqlStr.append(" and lc.currencyid="+info.getCurrencyID());
	          }
			  if (info.getClientID() > 0) {  //客户ID
	        	  sqlStr.append(" and lc.clientID=" + info.getClientID());
	            }
              if (info.getCreditTypeID() > 0) {//授信品种ID
            	  sqlStr.append( " and lc.creditTypeID=" + info.getCreditTypeID());
              }
              if (info.getCurrentAmount() > 0) {//授信额度
            	  sqlStr.append( " and lc.amount=" + info.getCurrentAmount());
              } 
              //授信日期
              if (info.getStartDate() != null && info.getEndDate() != null ) {
            	  sqlStr.append(" and lc.enddate>=to_date('" + DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')");
            	  sqlStr.append(" and lc.startdate<=to_date('"+DataFormat.formatDate(info.getEndDate())+"','yyyy-mm-dd')") ;
              }
              //授信开始日期
              if (info.getStartDate() != null && info.getEndDate() == null ) {
            	  sqlStr.append(" and lc.enddate>=to_date('" + DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')"); 
              }
              //授信结束日期
              if (info.getStartDate() == null && info.getEndDate() != null ) { 
            	  sqlStr.append(" and lc.startdate<=to_date('"+DataFormat.formatDate(info.getEndDate())+"','yyyy-mm-dd')"); 
              } 
              //录入日期开始
              if (info.getStartInputDate() != null &&
            	  info.getStartInputDate().length() > 0) {
            	  sqlStr.append(" and lc.INPUTDATE>=to_date('" + info.getStartInputDate());
            	  sqlStr.append("','yyyy-mm-dd')");
              }
	             //录入日期结束
	          if (info.getEndInputDate() != null &&
				  info.getEndInputDate().length() > 0) {
				  sqlStr.append(" and lc.INPUTDATE<=to_date('" + info.getEndInputDate());
				  sqlStr.append("','yyyy-mm-dd')");
	          }
	          sqlStr.append(" order by lc.officeid,lc.clientid,lc.credittypeid");
	          sqlStr.append(",cid");
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  CreditLimitInfo  creditLimitInfo  = null;
			  while(rs.next())
			  {
				  creditLimitInfo  = new CreditLimitInfo();
				  creditLimitInfo.setActiveUserID(rs.getLong("ACTIVEUSERID"));
				  creditLimitInfo.setAmount(rs.getDouble("AMOUNT"));
				  creditLimitInfo.setChangeTypeID(rs.getLong("CHANGETYPEID"));
				  creditLimitInfo.setChangeAmount(rs.getDouble("CHANGEAMOUNT"));
				  creditLimitInfo.setClientID(rs.getLong("CLIENTID"));
				  creditLimitInfo.setStatusID(rs.getLong("statusID"));
				  creditLimitInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				  creditLimitInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				  creditLimitInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				  creditLimitInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				  creditLimitInfo.setCreditTypeID(rs.getLong("creditTypeID"));
				  creditLimitInfo.setCstatusID(rs.getLong("cstatusID"));
				  creditLimitInfo.setCID(rs.getLong("CID"));
				  creditLimitInfo.setId(rs.getLong("ID"));
				  creditLimitInfo.setOfficeID(rs.getLong("officeid"));
				  coll.add(creditLimitInfo);
			  }
			  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException("查询异常",e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return coll;
	  }*/
	  
	  /**
	   * 查询授信额度变更历史信息
	   * @param ID
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  /*public Collection findHistoryChange(long historyClientID,long ID) throws ITreasuryDAOException, SQLException{
		  Collection coll = new ArrayList();
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();	  
			  sqlStr.append(" select lc.*,dd.changeamount,dd.changetypeid ,dd.statusID as cstatusID ,dd.ID as cID ,dd.ACTIVEUSERID as ACTIVEUSERID \n");
			  sqlStr.append(" from loan_creditLimit lc,CLIENT_CLIENTINFO cc ,LOAN_CREDITLIMITDETAIL dd");
			  sqlStr.append(" where 1=1 and lc.statusid="+LOANConstant.CreditStatus.ACTIVE+
					  		" and lc.Clientid ="+historyClientID+" and dd.statusID="+LOANConstant.CreditStatus.ACTIVE);
			  sqlStr.append(" and cc.id = lc.Clientid  and dd.creditlimitid(+)=lc.id ");
			  sqlStr.append(" and lc.id = "+ID);
	          sqlStr.append(" order by dd.id,lc.startDate");
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  CreditLimitInfo  creditLimitInfo  = null;
			  while(rs.next())
			  {
				  creditLimitInfo  = new CreditLimitInfo();
				  creditLimitInfo.setCActiveUserID(rs.getLong("ACTIVEUSERID"));
				  creditLimitInfo.setAmount(rs.getDouble("AMOUNT"));
				  creditLimitInfo.setChangeTypeID(rs.getLong("CHANGETYPEID"));
				  creditLimitInfo.setChangeAmount(rs.getDouble("CHANGEAMOUNT"));
				  creditLimitInfo.setClientID(rs.getLong("CLIENTID"));
				  creditLimitInfo.setStatusID(rs.getLong("statusID"));
				  creditLimitInfo.setStartDate(rs.getTimestamp("STARTDATE"));
				  creditLimitInfo.setEndDate(rs.getTimestamp("ENDDATE"));
				  creditLimitInfo.setInputUserID(rs.getLong("INPUTUSERID"));
				  creditLimitInfo.setInputDate(rs.getTimestamp("INPUTDATE"));
				  creditLimitInfo.setCreditTypeID(rs.getLong("creditTypeID"));
				  creditLimitInfo.setCstatusID(rs.getLong("cstatusID"));
				  creditLimitInfo.setCID(rs.getLong("CID"));
				  creditLimitInfo.setId(rs.getLong("ID"));
				  creditLimitInfo.setOfficeID(rs.getLong("officeid"));
				  coll.add(creditLimitInfo);
			  }	  
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException("查询变更历史信息异常",e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return coll;
	  } */
	  
	  /**
	   * 查询包含最大ID的授信变更信息
	   * @param ID
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */  
	  public Collection findCreditChangeMaxId(CreditLimitQueryInfo info) throws ITreasuryDAOException, SQLException{
		  Collection coll = new ArrayList();
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
	      StringBuffer sqlStr = new StringBuffer();
		  try{
			  conn = Database.getConnection();	  
			  sqlStr.append(" select lc.clientid,max(dd.id) cid \n");
			  sqlStr.append(" from loan_creditLimit lc,CLIENT_CLIENTINFO cc ,LOAN_CREDITLIMITDETAIL dd  ");
			  sqlStr.append(" where 1=1 and cc.id = lc.Clientid  and dd.creditlimitid(+)=lc.id ");
			  sqlStr.append(" and lc.statusid="+LOANConstant.CreditStatus.ACTIVE+" and dd.statusID="+LOANConstant.CreditStatus.ACTIVE);
	          if(info.getOfficeId() > 0){
	        	  sqlStr.append(" and lc.officeid="+info.getOfficeId());
	          }
	          if(info.getCurrencyID() >0){
	        	  sqlStr.append(" and lc.currencyid="+info.getCurrencyID());
	          }
			  sqlStr.append(" group by lc.officeid,lc.clientid,lc.credittypeid ");  
			  ps = conn.prepareStatement(sqlStr.toString());
			  rs = ps.executeQuery();
			  CreditLimitInfo  creditLimitInfo  = null;
			  while(rs.next())
			  {
				  creditLimitInfo =  new CreditLimitInfo();
				  creditLimitInfo.setClientID(rs.getLong("clientid"));
				  creditLimitInfo.setCID(rs.getLong("cid"));
				  coll.add(creditLimitInfo);
			  }
			    
		  }catch(Exception e){
			  e.printStackTrace();
			  throw new ITreasuryDAOException(e.getMessage(),e);
		  }finally{
			  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
		  }
		  return coll;
	  }
	   
	  	/**
		 * 根据授信ID返回授信额度变更ID(授信额度设置)
		 * @param creditLimitID
		 * @return
	  	 * @throws SQLException 
		 * @throws Exception
		 */
	public String findIDByCreditID(String creditLimitID) throws ITreasuryDAOException, SQLException{
			
			String changeTypeID="";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			 String strSQL = "";
			  try 
			     {
				  conn = Database.getConnection();
			      strSQL ="select changeTypeID from loan_creditlimitdetail where creditLimitID=?";
			      ps = conn.prepareStatement(strSQL);
			      ps.setString(1, creditLimitID);
			      rs = ps.executeQuery();
			      if (rs.next())
			      {	  
			    	  changeTypeID=String.valueOf(rs.getLong("changeTypeID"));
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
			      if (conn != null) 
			      {
			         conn.close();
			         conn = null;
			      }	 
		      } 
		      catch (Exception e)
		      {	    	  
		    	  e.printStackTrace();
		    	  throw new ITreasuryDAOException("根据授信品种ID返回授信额度变更ID异常", e);
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
			      if (conn != null) 
			      {
			         conn.close();
			         conn = null;
			      }	 
		      }
		      return changeTypeID;
		  }
	  
	  
	  
	  /**
	   * 根据授信品种id计算查询变更明细信息
	   * @param id
	   * @return
	 * @throws SQLException 
	   * @throws Exception
	   */
	  public Collection findLimitChangeInfoByID(long  id) throws ITreasuryDAOException, SQLException {
		  Collection coll = new ArrayList();
		  CreditLimitInfo linfo=null;
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  String strSQL = "";
		  try 
		     {
			  conn = Database.getConnection();
		      strSQL ="select * from loan_creditLimit lc" +
	                  " where lc.statusID in ("+LOANConstant.CreditStatus.ACTIVE + ")";
		      strSQL+=" and lc.id ="+id ;
		      strSQL+=" order by lc.id";
		      System.out.println("SQL＝＝"+strSQL);
		      ps = conn.prepareStatement(strSQL);
		      rs = ps.executeQuery();
		      while (rs.next())
		      {	  
		    	  linfo = new CreditLimitInfo();
		    	  linfo.setId(id);
		    	  linfo.setChangeTypeID(rs.getLong("changeTypeID"));
		    	  linfo.setAmount(rs.getDouble("changeAmount"));
		    	  linfo.setInputUserID(rs.getLong("inputUserID"));
		    	  linfo.setInputDate(DataFormat.getDateTime(rs.getDate("inputDate")+""));
		    	  linfo.setStatusID(rs.getLong("statusID"));
		    	  linfo.setActiveUserID(rs.getLong("activeUserID"));
		    	  linfo.setActiveDate(DataFormat.getDateTime(rs.getDate("activeDate")+""));
		    	  linfo.setOfficeID(rs.getLong("officeID"));
		    	  linfo.setCurrencyID(rs.getLong("currencyID"));
		    	  linfo.setClientID(rs.getLong("clientID"));
		    	  linfo.setCreditModeID(rs.getLong("creditModeID"));
		    	  linfo.setCreditTypeID(rs.getLong("creditTypeID"));
		    	  linfo.setStartDate(DataFormat.getDateTime(rs.getDate("startDate")+""));
		    	  linfo.setEndDate(DataFormat.getDateTime(rs.getDate("endDate")+""));
		    	  coll.add(linfo);
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
		      if (conn != null) 
		      {
		         conn.close();
		         conn = null;
		      }	 
	      } 
	      catch (Exception e)
	      {
	    	  
	    	  throw new ITreasuryDAOException("根据授信品种id计算查询变更明细错误", e);
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
		      if (conn != null) 
		      {
		         conn.close();
		         conn = null;
		      }	 
	      }
	      return coll;
	  }
	  
	  
	    /**
	     * 数据库更新操作操作　　
	     * @param info
	     * @return
	     * @throws SQLException 
	     */     
	    public void updateAciveDate(CreditLimitChangeInfo info) throws ITreasuryDAOException,SQLException
	    {
	    	  Connection conn = null;
			  PreparedStatement ps = null;
			  ResultSet rs = null;
		      StringBuffer sqlStr = new StringBuffer();
		      int index = 1;
	        try 
	        {
	        	conn = Database.getConnection();
	        	sqlStr.append("UPDATE \n");
	        	sqlStr.append( "loan_creditlimitdetail");
	        	sqlStr.append(" SET statusid=?,activeDate = ?,activeUserID=?");
	        	sqlStr.append("\n  WHERE ID = ?");
	            ps = conn.prepareStatement(sqlStr.toString());
				ps.setLong(index++, info.getStatusID());
	            ps.setTimestamp(index++,info.getCheckDate());
	            ps.setLong(index++,info.getActiveUserID());
	            ps.setLong(index++,info.getId());
	            ps.executeUpdate();
	            
	      	  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
	            
	        }catch(Exception e){
				  e.printStackTrace();
				  throw new ITreasuryDAOException("更新异常",e);
			}finally{
				  if(rs!=null){
					  rs.close();
					  rs = null;
				  }
				  if(ps!=null){
					  ps.close();
					  ps = null;
				  }
				  if(conn!=null){
					  conn.close();
					  conn = null;
				  }	  
			  } 
	      
		  }
	    /**
	     * 状态更新操作操作　　
	     * @param info
	     * @return
	     * @throws SQLException 
	     */     
	    public void updateStatusID(CreditLimitChangeInfo info) throws ITreasuryDAOException,SQLException
	    {
	    	  Connection conn = null;
			  PreparedStatement ps = null;
			  ResultSet rs = null;
		      StringBuffer sqlStr = new StringBuffer();
		      int index = 1;
	        try 
	        {
	        	conn = Database.getConnection();
	        	sqlStr.append("UPDATE \n");
	        	sqlStr.append( "loan_creditlimitdetail");
	        	sqlStr.append(" SET statusid=?");
	        	sqlStr.append("\n  WHERE ID = ?");
	            ps = conn.prepareStatement(sqlStr.toString());
				ps.setLong(index++, info.getStatusID());
	            ps.setLong(index++,info.getId());
	            ps.executeUpdate();
	            
	      	  if(rs!=null){
				  rs.close();
				  rs = null;
			  }
			  if(ps!=null){
				  ps.close();
				  ps = null;
			  }
			  if(conn!=null){
				  conn.close();
				  conn = null;
			  }	  
	            
	        }catch(Exception e){
				  e.printStackTrace();
				  throw new ITreasuryDAOException("更新异常",e);
			}finally{
				  if(rs!=null){
					  rs.close();
					  rs = null;
				  }
				  if(ps!=null){
					  ps.close();
					  ps = null;
				  }
				  if(conn!=null){
					  conn.close();
					  conn = null;
				  }	  
			  } 
	      
		  }     
	   public boolean isExistGroupCreditInUpClients(CreditLimitChangeInfo info) throws ITreasuryDAOException,SQLException
	   {
		return false;
		   
	   }
}

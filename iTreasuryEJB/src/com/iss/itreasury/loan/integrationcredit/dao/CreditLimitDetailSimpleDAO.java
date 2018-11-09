package com.iss.itreasury.loan.integrationcredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.LoanDAO;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitInfo;
import com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitQueryInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.system.dao.PageLoader;


public class CreditLimitDetailSimpleDAO extends LoanDAO{

	 protected Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	  public CreditLimitDetailSimpleDAO() {
	    super("loan_creditLimitdetail");
	  }

	  public CreditLimitDetailSimpleDAO(Connection conn) {
	    super("loan_creditLimitdetail", conn);
	  }
	  
	  /**
	   * 保存授信额度变更信息
	   * @param info
	   * @return
	 * @throws SQLException 
	   * @throws Exception
	   */
	  public void saveCreditChange(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{

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
	  public long saveCreditLimitDetail(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{

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
			  		"APPLYCODE,OFFICEID,CURRENCYID,CLIENTID,CREDITMODEID,CREDITTYPEID,STARTDATE,ENDDATE) values  \n");
			  sqlStr.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
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
	   * 查询某个客户时间段内是否做过授信额度设置
	   * @param Info
	   * @return
	   * @throws ITreasuryDAOException
	   */
	   public Collection findByDateOption(CreditLimitDetailInfo Info, long queryId, boolean checkCreditMode) throws
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
	         strSQL += " where statusID in ("+ LOANConstant.CreditStatus.SAVE + "," + LOANConstant.CreditStatus.CHECK + "," + LOANConstant.CreditStatus.APPROVALING +")";
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
	         strSQL += " and clientID =" + Info.getClientID();
	        	 
	         //保存时，判断授信产品类型
	         if(Info.getCreditTypeID()>0){
	        	strSQL+=" and credittypeid in("+LOANConstant.queryCreditProduct.getQueryString(queryId, Info.getCreditTypeID())+")";
	         }
	         if (Info.getId()>0 ){
	 	    	strSQL+="and id != "+ Info.getId();
	 	     }
	         /*if(Info.getCreditLimitID()>0){
		        	strSQL+=" and CREDITLIMITID = "+Info.getCreditLimitID();
		     }*/
	         if(Info.getChangeTypeID()>0){
	        	 if(Info.getChangeTypeID() != LOANConstant.changeType.XINZENG) {
		        	//strSQL+=" and CHANGETYPEID = " + LOANConstant.changeType.XINZENG;
	        		 strSQL+=" and CREDITLIMITID != "+Info.getCreditLimitID();
	        		 //strSQL+=" and CHANGETYPEID in (" + LOANConstant.changeType.JIA + ", " + LOANConstant.changeType.JIAN + ")";
	        		 //strSQL += " and ACTIVESTATUSID !=" + LOANConstant.CreditStatus.ACTIVE;
	        	 }
		     }
	         if(Info.getOfficeID()>0){
	         	strSQL+=" and officeid="+Info.getOfficeID();
	         }
	         if(Info.getCurrencyID()>0){
	         	strSQL+=" and currencyid="+Info.getCurrencyID();
	         }
	         if(Info.getCreditModeID()>0 && checkCreditMode == true){
		        strSQL+=" and creditmodeId="+Info.getCreditModeID();
		     }
	         if(Info.getStartDate() != null && Info.getEndDate() != null) {
	        	 strSQL += " and ((STARTDATE >= to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and STARTDATE  <= to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')) or";
	        	 strSQL += "      (ENDDATE >= to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and ENDDATE <= to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')) or";
	        	 strSQL += "      (STARTDATE < to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and ENDDATE > to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')))";

	         }
	 	       
	         System.out.println("sql= \n" + strSQL);
	         prepareStatement(strSQL);
	         executeQuery();
	         c = getDataEntitiesFromResultSet(CreditLimitDetailInfo.class);

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
	   * 检查客户在做集团授信时客户的所有下级客户是否已作过授信
	   * @param Info
	   * @return
	   * @throws ITreasuryDAOException
	   */
	   public Collection findByDateOptionAndChildClient(CreditLimitDetailInfo Info, long queryId) throws
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
	         strSQL += " where statusID in ("+ LOANConstant.CreditStatus.SAVE + "," + LOANConstant.CreditStatus.CHECK + "," + LOANConstant.CreditStatus.APPROVALING +")";
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
	         strSQL += " and clientID in (select b.id from client_clientinfo b,client_clientinfo a where b.levelcode like a.levelcode || '%' and a.id = "+ Info.getClientID() +" and b.levelid = a.levelid + 1 )";
	         strSQL += " and clientID != " + Info.getClientID();
	        
	         //保存时，判断授信产品类型
	         if(Info.getCreditTypeID() > 0){
	        	strSQL+=" and credittypeid in("+LOANConstant.queryCreditProduct.getQueryString(queryId, Info.getCreditTypeID())+")";
	         }
	         if (Info.getId()>0){
	 	    	strSQL+="and id !="+ Info.getId();
	 	     }
	         if(Info.getCreditLimitID()>0){
		        	strSQL+=" and CREDITLIMITID = "+Info.getCreditLimitID();
		     }
	         if(Info.getChangeTypeID()>0){
	        	 if(Info.getChangeTypeID() == LOANConstant.changeType.XINZENG) {
		        	strSQL+=" and CHANGETYPEID = " + LOANConstant.changeType.XINZENG;
	        	 }
	        	 else {
	        		 strSQL+=" and CHANGETYPEID in (" + LOANConstant.changeType.JIA + ", " + LOANConstant.changeType.JIAN + ")";
	        		 strSQL += " and ACTIVESTATUSID !=" + LOANConstant.CreditStatus.ACTIVE;
	        	 }
		     }
	         if(Info.getOfficeID()>0){
	         	strSQL+=" and officeid="+Info.getOfficeID();
	         }
	         if(Info.getCurrencyID()>0){
	         	strSQL+=" and currencyid="+Info.getCurrencyID();
	         }
	         if(Info.getStartDate() != null && Info.getEndDate() != null){
	        	 strSQL += " and ((STARTDATE >= to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and STARTDATE  <= to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')) or";
	        	 strSQL += "      (ENDDATE >= to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and ENDDATE <= to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')) or";
	        	 strSQL += "      (STARTDATE < to_date('"+ DataFormat.getDateString(Info.getStartDate()) +"', 'yyyy-mm-dd')";
	        	 strSQL += "      and ENDDATE > to_date('"+ DataFormat.getDateString(Info.getEndDate()) +"', 'yyyy-mm-dd')))";
	         }
	         
	         System.out.println("sql= \n" + strSQL);
	         prepareStatement(strSQL);
	         executeQuery();
	         c = getDataEntitiesFromResultSet(CreditLimitDetailInfo.class);

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
	  public void deleteCreditChange(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{
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
	  public void updateLimitChange(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{
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
	  /*public Collection findCreditChangeAmount(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{
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
			  CreditLimitDetailInfo  creditLimitChangeInfo = null;
			  while(rs.next()){
				  creditLimitChangeInfo  = new CreditLimitDetailInfo();
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
	  }*/
	  
	  /**
	   * 根据授信ID查询此额度是否存在已保存的变更信息
	   * @param info
	   * @return
	   * @throws SQLException 
	   * @throws Exception
	   */
	  public boolean findIsExist(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{
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
	  /*public Collection findLimitChangeInfoByID(long  id) throws ITreasuryDAOException, SQLException {
		  Collection coll = new ArrayList();
		  CreditLimitInfo linfo=null;
		  Connection conn = null;
		  PreparedStatement ps = null;
		  ResultSet rs = null;
		  String strSQL = "";
		  try 
		     {
			  conn = Database.getConnection();
		      strSQL ="select lc.*,dd.changeamount,dd.changetypeid ,dd.statusid as cstatusid,dd.id as cid,dd.ACTIVEUSERID as CACTIVEUSERID from loan_creditLimit lc ,LOAN_CREDITLIMITDETAIL dd" +
	                  " where dd.creditlimitid(+)=lc.id and dd.statusid(+)!=0";
		      strSQL+=" and lc.id(+)="+id ;
		      strSQL+=" order by dd.id";
		      System.out.println("SQL＝＝"+strSQL);
		      ps = conn.prepareStatement(strSQL);
		      rs = ps.executeQuery();
		      while (rs.next())
		      {	  
		    	  linfo = new CreditLimitInfo();
		    	  linfo.setClientID(rs.getLong("clientID"));
		    	  linfo.setCreditTypeID(rs.getLong("creditTypeID"));
		    	  linfo.setStatusID(rs.getLong("statusID"));
		    	  linfo.setStartDate(DataFormat.getDateTime(rs.getDate("startDate")+""));
		    	  linfo.setEndDate(DataFormat.getDateTime(rs.getDate("endDate")+""));
		    	  linfo.setAmount(rs.getDouble("amount"));
		    	  linfo.setCstatusID(rs.getLong("cStatusID"));
		    	  linfo.setId(id);
		    	  linfo.setCID(rs.getLong("cid"));
		    	  linfo.setChangeAmount(rs.getDouble("changeAmount"));
		    	  linfo.setChangeTypeID(rs.getLong("changeTypeID"));
		    	  linfo.setCActiveUserID(rs.getLong("CACTIVEUSERID"));
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
	  }*/
	  
	  
	    /**
	     * 数据库更新操作操作　　
	     * @param info
	     * @return
	     * @throws SQLException 
	     */     
	   /* public void updateAciveDate(CreditLimitDetailInfo info) throws ITreasuryDAOException,SQLException
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
	      
		  }*/
	  
	    /**
	     * 状态更新操作操作　　
	     * @param info
	     * @return
	     * @throws SQLException 
	     */     
	    public void updateStatusID(CreditLimitDetailInfo info) throws ITreasuryDAOException,SQLException
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
	   
	   
		  /**
		   * 查询授信信息
		   * @param qInfo
		   * @return
		   * @throws ITreasuryDAOException
		 * @throws SQLException 
		   */
		  /*public Collection findByMultiOption(CreditLimitQueryInfo qInfo) throws
		      ITreasuryDAOException, SQLException {
			  System.out.println("welcome to Collection findByMultiOption");
			  Collection coll = new ArrayList();
		      String strSQL = "";

		    long queryPurpose = qInfo.getQueryPurpose();

		    try {
		      /*-----------------init DAO --------------------*/
		      /*     initDAO();
		      	 
		          strSQL = "select lc.* from loan_creditLimitdetail lc,CLIENT_CLIENTINFO cc " +
		                	" where cc.id = lc.Clientid  ";
		        
		          if (queryPurpose == 1) { //for 链接查找
		        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.SAVE+")";
		          }
		          else if (queryPurpose == 2) { //激活
		        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.CHECK+")";
		        	  strSQL += " and lc.activeStatusID = -1";
		          }
		          else if(queryPurpose == 3){///取消激活
		        	  strSQL += " and lc.activeStatusID in("+LOANConstant.CreditStatus.ACTIVE+")";
		          }
		          else if(queryPurpose == 4) { //冻结
		        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.ACTIVE+")";
		          }
		          else if(queryPurpose == 5) { //取消冻结
		        	  strSQL += " and lc.statusID in("+LOANConstant.CreditStatus.FREEZE+")";
		          }
		          else if(queryPurpose == 6){ //激活和冻结状态
		        	  strSQL += " and lc.activeStatusID in ("+LOANConstant.CreditStatus.ACTIVE+")";
		          }
		          if(qInfo.getOfficeId() > 0){ //办事处ID
		        	  strSQL += " and cc.OFFICEID = "+qInfo.getOfficeId();
		          }
		          if (qInfo.getStartClientID() > 0) { //客户ID开始
		            strSQL += " and lc.ClientID>=" + qInfo.getStartClientID();
		          }
		          if (qInfo.getEndClientID() > 0) { //客户ID结束
		            strSQL += " and lc.clientID<=" + qInfo.getEndClientID();
		          }
		          if (qInfo.getClientID() > 0) {  //客户ID
		            strSQL += " and lc.clientID=" + qInfo.getClientID();
		          }
		          if (qInfo.getId() > 0) { 
		              strSQL += " and lc.id=" + qInfo.getId();
		            }
		          if (qInfo.getCreditTypeID() > 0) {//授信品种ID
		              strSQL += " and lc.creditTypeID=" + qInfo.getCreditTypeID();
		            }
		          if (qInfo.getCurrentAmount() > 0) {//授信额度
		              strSQL += " and lc.changeamount=" + qInfo.getCurrentAmount();
		            }  
		          if (qInfo.getStartAmount() > 0) { //授信额度开始
		            strSQL += " and lc.changeamount>=" + qInfo.getStartAmount();
		          }
		          if (qInfo.getEndAmount() > 0) { //授信额度结束
		            strSQL += " and lc.changeamount<=" + qInfo.getEndAmount();
		          }
		          //授信日期
		          if (qInfo.getStartDate() != null && qInfo.getEndDate() != null ) {
		              strSQL +=" and lc.enddate>=to_date('" + DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')" 
		                     +" and lc.startdate<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')" ;
		          }
		          //授信开始日期
		          if (qInfo.getStartDate() != null && qInfo.getEndDate() == null ) {
		              strSQL +=" and lc.enddate>=to_date('" + DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd')"; 
		          }
		          //授信结束日期
		          if (qInfo.getStartDate() == null && qInfo.getEndDate() != null ) { 
		        	  strSQL +=" and lc.startdate<=to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd')" ;
		          } 
		          //录入日期开始
		          if (qInfo.getStartInputDate() != null &&
		              qInfo.getStartInputDate().length() > 0) {
		            strSQL += " and lc.INPUTDATE>=to_date('" + qInfo.getStartInputDate().toString().substring(0,10);
		            strSQL += "','yyyy-mm-dd')";
		          }
		          //录入日期结束
		          if (qInfo.getEndInputDate() != null &&
		              qInfo.getEndInputDate().length() > 0) {
		            strSQL += " and lc.INPUTDATE<=to_date('" + qInfo.getEndInputDate().toString().subSequence(0, 10);
		            strSQL += "','yyyy-mm-dd')";
		          }
//		          if (qInfo.getStatusID() > 0) {
//		            strSQL += " and lc.statusID=" + qInfo.getStatusID();
//		          }
		          ////////////////////////////排序处理//////////////////////////////////////////////////////////////////////
		          int nIndex = 0;
		          String orderParamString = qInfo.getOrderParamString();

		          nIndex = orderParamString.indexOf(".");

		          if (nIndex > 0) {
		            if (orderParamString.substring(0,nIndex).toLowerCase().equals("loan_creditdetaillimit")) {
		              strSQL += " order by lc." + orderParamString.substring(nIndex + 1);
		            }
		          }
		          else {
		            strSQL += " order by lc.ID";
		          }

		          if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC) {
		            strSQL += " desc";
		          }
		    	  
		        log4j.info("sql= \n" + strSQL);

		        prepareStatement(strSQL);
		        executeQuery();
		        coll = getDataEntitiesFromResultSet(CreditLimitDetailInfo.class);
		    }catch (Exception e) {
		        e.printStackTrace();  
		    	throw new ITreasuryDAOException("查询授信设置出错", e);
		      }
		     finally {
		    	 finalizeDAO();  
		     }
		         return coll;
		  }*/
		  
		  
		  /**
		   * 查询授信信息
		   * @param qInfo
		   * @return
		   * @throws ITreasuryDAOException
		 * @throws SQLException 
		   */
		  public Collection findByMultiOption(CreditLimitDetailInfo qInfo) throws
		      ITreasuryDAOException, SQLException {
			  Collection c = null;
		      String strSQL = "";

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
		      	 
		          strSQL += " select * from loan_creditLimitdetail";
		          strSQL += " where 1=1";
		          //授信品种
		          if(qInfo.getCreditTypeID()>0){
		        	  strSQL += " and creditTypeID=" + qInfo.getCreditTypeID();
		          }
		          else {
		        	  strSQL += " and creditTypeID in("+ LOANConstant.CreditProduct.ZY +","+ LOANConstant.CreditProduct.BH +","+ LOANConstant.CreditProduct.SP +","+ LOANConstant.CreditProduct.ZHSX +")";
		          }
		          //激活类型
		          
		          if(qInfo.getChangeTypeID() == LOANConstant.changeType.BIANGENG){
		        	  strSQL += " and CHANGETYPEID in (" + LOANConstant.changeType.JIA +","+LOANConstant.changeType.JIAN + ")";
		          }
		          else if(qInfo.getChangeTypeID() > 0 && qInfo.getChangeTypeID() != LOANConstant.changeType.BIANGENG){
		        	  strSQL += " and CHANGETYPEID = " + qInfo.getChangeTypeID();
		          }
		          //状态
		          if(qInfo.getStatusID() == LOANConstant.CreditStatus.CHECK){
		        	  strSQL += " and STATUSID = " + LOANConstant.CreditStatus.CHECK;
		        	  strSQL += " and ACTIVESTATUSID != " + LOANConstant.CreditStatus.ACTIVE;
		        	  strSQL += " and ACTIVESTATUSID != " + LOANConstant.CreditStatus.FREEZE;
		          }
		          if(qInfo.getStatusID() == LOANConstant.CreditStatus.ACTIVE){
		        	  strSQL += " and STATUSID = " + LOANConstant.CreditStatus.CHECK;
		        	  strSQL += " and ACTIVESTATUSID = " + LOANConstant.CreditStatus.ACTIVE;
		          }
		          //办事处ID
		          if(qInfo.getOfficeID()>0){
		        	  strSQL += " and officeid = " + qInfo.getOfficeID();
		          }
		          //币种ID
		          if(qInfo.getCurrencyID()>0){
		        	  strSQL += " and currencyid = " + qInfo.getCurrencyID();
		          }
		          //客户ID
		          if (qInfo.getClientID() > 0) {  
			            strSQL += " and clientID = " + qInfo.getClientID();
			      }
		          //授信额度
		          if (qInfo.getChangeAmount() > 0) {
		              strSQL += " and changeamount <= " + qInfo.getChangeAmount();
		          }
		          //开始日、结束日
		          if(qInfo.getStartDate() != null && qInfo.getEndDate() == null){
		        	  strSQL += " and STARTDATE >= to_date('"+ DataFormat.getDateString(qInfo.getStartDate()) +"', 'yyyy-mm-dd')";
		          }
		          if(qInfo.getEndDate() != null && qInfo.getStartDate() == null){
		        	  strSQL += " and ENDDATE <= to_date('"+ DataFormat.getDateString(qInfo.getEndDate()) +"', 'yyyy-mm-dd')";
		          }
		          if(qInfo.getStartDate() != null && qInfo.getEndDate() != null){
			         strSQL += " and STARTDATE >= to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd') ";
			         strSQL += " and ENDDATE <= to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd') ";
		          }
		          
		          strSQL += " order by inputdate";
		    	  
		          log4j.info("sql= \n" + strSQL);

			      prepareStatement(strSQL);
			      executeQuery();
			      c = getDataEntitiesFromResultSet(CreditLimitDetailInfo.class);
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
		    }catch (Exception e) {
		        e.printStackTrace();  
		    	throw new ITreasuryDAOException("查询授信设置出错", e);
		      }
		     finally {
		    	 finalizeDAO();  
		     }
		     return (c.size() > 0 ? c : null);
		  }  
		  public String getCreditLimitDetailOrderSQL(long lOrder, long lDesc)
			{
				//order
				String strDesc = lDesc == 1 ? " desc " : "";
				StringBuffer oBuf = new StringBuffer();
				switch ((int) lOrder)
				{
					case 1 : 
						oBuf.append(" \n order by clientID" + strDesc);
						break;
					case 2 :
						oBuf.append(" \n order by creditModeID" + strDesc);
						break;
					case 3 :
						oBuf.append(" \n order by creditTypeID" + strDesc);
						break;
					case 4 :
						oBuf.append(" \n order by changeTypeID" + strDesc);
						break;
					case 5 :
						oBuf.append(" \n order by startDate" + strDesc);
						break;
					case 6 :
						oBuf.append(" \n order by endDate" + strDesc);
						break;
					case 7 :
						oBuf.append(" \n order by inputUserID" + strDesc);
						break;
					case 8 :
						oBuf.append(" \n order by inputDate" + strDesc);
						break;
					default :
						oBuf.append(" \n order by inputDate" + strDesc);
						break;
				}
				return (oBuf.toString());
			}
		  /**
		   * 查询授信记录信息
		   * @param qInfo
		   * @return
		   * @throws ITreasuryDAOException
		 * @throws SQLException 
		   */
		  public PageLoader findByHistoryMultiOption(CreditLimitDetailInfo qInfo) throws
		      ITreasuryDAOException, SQLException {
			  StringBuffer sql = new StringBuffer();
			  sql.append ( " select * from loan_creditLimitdetail ");
			  sql.append ( " where 1=1 ");
		      if(qInfo.getCreditTypeID()>0){
		    	  sql.append ( " and creditTypeID= " + qInfo.getCreditTypeID() + " \n");
		      }
		      else {
	        	  sql.append (  " and creditTypeID in("+ LOANConstant.CreditProduct.ZY +","+ LOANConstant.CreditProduct.BH +","+ LOANConstant.CreditProduct.SP +","+ LOANConstant.CreditProduct.ZHSX + ") \n");
		          }
	         
	          //状态
	          if(qInfo.getStatusID() == LOANConstant.CreditStatus.CHECK){
	        	  sql.append (" and STATUSID = " + LOANConstant.CreditStatus.CHECK + " \n");;
	        	  sql.append (" and ACTIVESTATUSID != " + LOANConstant.CreditStatus.ACTIVE + " \n");;
	        	  sql.append ( " and ACTIVESTATUSID != " + LOANConstant.CreditStatus.FREEZE + " \n");;
	          }
	          if(qInfo.getStatusID() == LOANConstant.CreditStatus.ACTIVE){
	        	  sql.append (" and STATUSID = " + LOANConstant.CreditStatus.CHECK + " \n");;
	        	  sql.append (" and ACTIVESTATUSID = " + LOANConstant.CreditStatus.ACTIVE + " \n");;
	          }
	          if(qInfo.getStatusID() == LOANConstant.CreditStatus.APPROVALING){
	        	  sql.append ( " and STATUSID = " + LOANConstant.CreditStatus.APPROVALING + " \n");;
	          }if(qInfo.getStatusID() == LOANConstant.CreditStatus.SAVE){
	        	  sql.append ( " and STATUSID = " + LOANConstant.CreditStatus.SAVE + " \n");;
	          }
	          else {
	        	  sql.append ( " and STATUSID in( " + LOANConstant.CreditStatus.SAVE +","+ LOANConstant.CreditStatus.CHECK + "," + LOANConstant.CreditStatus.APPROVALING + ","+ LOANConstant.CreditStatus.ACTIVE +") \n");;
	          }
	          //办事处ID
	          if(qInfo.getOfficeID()>0){
	        	  sql.append ( " and officeid = " + qInfo.getOfficeID() + " \n");;
	          }
	          //币种ID
	          if(qInfo.getCurrencyID()>0){
	        	  sql.append (" and currencyid = " + qInfo.getCurrencyID() + " \n");;
	          }
	          //客户ID
	          if (qInfo.getClientID() > 0) {  
	        	  sql.append ( " and clientID = " + qInfo.getClientID() + " \n");;
		      }
	          //授信额度
	          //开始日、结束日
	          if(qInfo.getStartDate() != null && qInfo.getEndDate() == null){
	        	  sql.append ( " and STARTDATE >= to_date('"+ DataFormat.getDateString(qInfo.getStartDate()) +"', 'yyyy-mm-dd')" + " \n");;
	          }
	          if(qInfo.getEndDate() != null && qInfo.getStartDate() == null){
	        	  sql.append ( " and ENDDATE <= to_date('"+ DataFormat.getDateString(qInfo.getEndDate()) +"', 'yyyy-mm-dd')" + " \n");;
	          }
	          if(qInfo.getStartDate() != null && qInfo.getEndDate() != null){
	        	  sql.append (" and STARTDATE >= to_date('"+DataFormat.formatDate(qInfo.getStartDate())+"','yyyy-mm-dd') " + " \n");;
	        	  sql.append (" and ENDDATE <= to_date('"+DataFormat.formatDate(qInfo.getEndDate())+"','yyyy-mm-dd') " + " \n");;
	          }
	          
	         // sql.append (" order by inputdate ");
	          System.out.println(sql);
	          try{
	  			
	  			PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
	  			pageLoader.initPageLoader(new AppContext(), "("+sql.toString()+")","*", "1=1", (int) Constant.PageControl.CODE_PAGELINECOUNT,
	  					"com.iss.itreasury.loan.integrationcredit.dataentity.CreditLimitDetailInfo", null);
	  			
	  			return pageLoader;
	  		}catch(Exception ex)
	  		{
	  			throw new ITreasuryDAOException(ex.getMessage(), ex);
	  		}
	  	    

	  }
		     
		  /**
		   * 更新授信额度变更信息
		   * @param info
		   * @return
		   * @throws SQLException 
		   * @throws Exception
		   */
		  public void updateCreditLimitDetailInfo(CreditLimitDetailInfo info) throws ITreasuryDAOException, SQLException{
			  Connection conn = null;
			  PreparedStatement ps = null;
			  ResultSet rs = null;
		      StringBuffer sqlStr = new StringBuffer();
			  try{
				  conn = Database.getConnection();
				  sqlStr.append(" update LOAN_CREDITLIMITDETAIL  \n");
				  sqlStr.append(" set ACTIVEUSERID=?, \n");
				  sqlStr.append(" ACTIVEDATE=? ,ACTIVESTATUSID=?  \n");
				  sqlStr.append(" where ID=? ");
				  ps = conn.prepareStatement(sqlStr.toString());
				  System.out.println(sqlStr);
				  ps.setLong(1, info.getActiveUserID());
				  ps.setTimestamp(2, info.getActiveDate());
				  ps.setLong(3, info.getActiveStatusID());
				  ps.setLong(4, info.getId());
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
	   
}

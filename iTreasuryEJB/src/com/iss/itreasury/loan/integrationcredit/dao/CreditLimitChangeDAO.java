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
	   * �������Ŷ�ȱ����Ϣ
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
			  throw new ITreasuryDAOException("��������Ϣ�쳣",e);
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
	   * �������Ŷ����Ϣ��LOAN_CREDITLIMITDETAIL
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
			  throw new ITreasuryDAOException("��������Ϣ�쳣",e);
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
	   * ��ѯ�ͻ�ĳһʱ������Ƿ��������Ŷ������
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
	          throw new ITreasuryDAOException("��������ʱ�쳣",e);
	       }
	       /*----------------end of init------------------*/
	       try {
	         strSQL = "select * from loan_creditLimitDetail ";
	         strSQL += " where clientID =" + Info.getClientID();
	         strSQL += " and statusID > 0";
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.DELETE;
	         strSQL += " and statusID !=" + LOANConstant.CreditStatus.OVERTIME;
	         //����ʱ���ж����Ų�Ʒ����
	         if(Info.getCreditTypeID() > 0){
	         	//���Ų�Ʒ����Ϊ��Ʊ
	         	if(Info.getCreditTypeID()==LOANConstant.CreditProduct.SP){
	         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.SP+","+LOANConstant.CreditProduct.ZHSX+" )";
	         	//���Ų�Ʒ����Ϊ��Ӫ
	         	}else if(Info.getCreditTypeID()==LOANConstant.CreditProduct.ZY){
	         		strSQL+=" and credittypeid in("+LOANConstant.CreditProduct.ZY+","+LOANConstant.CreditProduct.ZHSX+" )";
	         	//�ۺ�����
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
	         throw new ITreasuryDAOException("��ѯ�������ó���", e);
	       }

	       /*----------------finalize Dao-----------------*/
	       try {
	         finalizeDAO();
	       }
	       catch (ITreasuryDAOException e) {
	         throw new ITreasuryDAOException("�ر�����ʱ�쳣",e);
	       }
	       /*----------------end of finalize---------------*/
	     }
	     catch (Exception e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	       throw new ITreasuryDAOException("��ѯ�쳣",e);
	     }
	     finally {
	       finalizeDAO();
	     }
	     return (c.size() > 0 ? c : null);
	   }
	  /**
	   * ������ID
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
	   *  ɾ�����Ŷ�ȱ����Ϣ���߼�ɾ����
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
			  throw new ITreasuryDAOException("ɾ���쳣",e) ;
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
	   * �������Ŷ�ȱ����Ϣ
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
			  throw new ITreasuryDAOException("���¶����Ϣ�쳣",e) ;
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
	   * ��ѯ���Ŷ�ȱ��������Ϣ(����/ȡ������)
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
			  //�������Ŷ�ȱ����Ϣ����
			  if(info.getOperate()==1){
				  sqlStr.append(" and STATUSID="+LOANConstant.CreditStatus.ACTIVE);
				  if(info.getId()>0){
					  sqlStr.append("\n union \n");
					  sqlStr.append(" select * from loan_creditlimitdetail where 1=1 ");
					  sqlStr.append(" and id="+info.getId());
				  }
			  //ȡ���������Ŷ�ȱ����Ϣ����
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
			 throw new ITreasuryDAOException("��ѯ�����Ϣ�쳣",e);
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
	   * ��������ID��ѯ�˶���Ƿ�����ѱ���ı����Ϣ
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
			  throw new ITreasuryDAOException("��ѯ�쳣",e);
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
	   * ��ѯ���Ŷ�ȱ����Ϣ(����/ȡ������)
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
			  if (info.getClientID() > 0) {  //�ͻ�ID
	        	  sqlStr.append(" and lc.clientID=" + info.getClientID());
	            }
              if (info.getCreditTypeID() > 0) {//����Ʒ��ID
            	  sqlStr.append( " and lc.creditTypeID=" + info.getCreditTypeID());
              }
              if (info.getCurrentAmount() > 0) {//���Ŷ��
            	  sqlStr.append( " and lc.amount=" + info.getCurrentAmount());
              } 
              //��������
              if (info.getStartDate() != null && info.getEndDate() != null ) {
            	  sqlStr.append(" and lc.enddate>=to_date('" + DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')");
            	  sqlStr.append(" and lc.startdate<=to_date('"+DataFormat.formatDate(info.getEndDate())+"','yyyy-mm-dd')") ;
              }
              //���ſ�ʼ����
              if (info.getStartDate() != null && info.getEndDate() == null ) {
            	  sqlStr.append(" and lc.enddate>=to_date('" + DataFormat.formatDate(info.getStartDate())+"','yyyy-mm-dd')"); 
              }
              //���Ž�������
              if (info.getStartDate() == null && info.getEndDate() != null ) { 
            	  sqlStr.append(" and lc.startdate<=to_date('"+DataFormat.formatDate(info.getEndDate())+"','yyyy-mm-dd')"); 
              } 
              //¼�����ڿ�ʼ
              if (info.getStartInputDate() != null &&
            	  info.getStartInputDate().length() > 0) {
            	  sqlStr.append(" and lc.INPUTDATE>=to_date('" + info.getStartInputDate());
            	  sqlStr.append("','yyyy-mm-dd')");
              }
	             //¼�����ڽ���
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
			  throw new ITreasuryDAOException("��ѯ�쳣",e);
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
	   * ��ѯ���Ŷ�ȱ����ʷ��Ϣ
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
			  throw new ITreasuryDAOException("��ѯ�����ʷ��Ϣ�쳣",e);
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
	   * ��ѯ�������ID�����ű����Ϣ
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
		 * ��������ID�������Ŷ�ȱ��ID(���Ŷ������)
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
		    	  throw new ITreasuryDAOException("��������Ʒ��ID�������Ŷ�ȱ��ID�쳣", e);
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
	   * ��������Ʒ��id�����ѯ�����ϸ��Ϣ
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
		      System.out.println("SQL����"+strSQL);
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
	    	  
	    	  throw new ITreasuryDAOException("��������Ʒ��id�����ѯ�����ϸ����", e);
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
	     * ���ݿ���²�����������
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
				  throw new ITreasuryDAOException("�����쳣",e);
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
	     * ״̬���²�����������
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
				  throw new ITreasuryDAOException("�����쳣",e);
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
